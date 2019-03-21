package com.musicfire.modular.quartz;

import com.musicfire.common.utiles.Conf;
import com.musicfire.common.utiles.ExcelUtil;
import com.musicfire.modular.merchant.entity.Merchant;
import com.musicfire.modular.merchant.service.IMerchantService;
import com.musicfire.modular.order.dto.OrderExport;
import com.musicfire.modular.order.page.OrderPage;
import com.musicfire.modular.order.service.IOrderService;
import com.musicfire.modular.system.entity.User;
import com.musicfire.modular.system.service.IUserService;
import org.hibernate.validator.constraints.pl.REGON;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestQuartz extends QuartzJobBean {

    //默认编码
    private static final String DEFAULT_ENCODING = "UTF-8";

    //记录日志
    private Logger logger = LoggerFactory.getLogger(TestQuartz.class);

    @Resource
    private JavaMailSender mailSender;

    //本身邮件的发送者，来自邮件配置
    @Value("${spring.mail.username}")
    private String userName;

    @Resource
    private IOrderService orderService;

    @Resource
    private IUserService userService;

    @Resource
    private IMerchantService merchantService;

    /**
     * 执行定时任务
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        OrderPage orderPage = new OrderPage();
        LocalDate now = LocalDate.now();
        orderPage.setState(1);
        orderPage.setStartTime(now.getYear() + "-" + now.getMonthValue() + "-" + (now.getDayOfMonth() - 1) + " 09:00:00");
        orderPage.setEndTime(now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth() + " 09:00:00");
        List<OrderExport> orderExports = orderService.exportOrder(orderPage);
        if(orderExports.size()>0){
            //所有订单，发送给系统管理员。
            List<List<OrderExport>> lists = new ArrayList<>(orderExports.stream().collect(Collectors.groupingBy(OrderExport::getMerchantName)).values());
            String name = System.currentTimeMillis()+"";
            name = name.substring(7,13);
            String fileName = "订单信息" + name + ".xls";
            try {
                FileOutputStream out = new FileOutputStream(Conf.getValue("excelImportAddr")+"/"+fileName);
                ExcelUtil<OrderExport> util = new ExcelUtil<>(OrderExport.class);// 创建工具类.
                util.exportExcels(lists, "订单新", 65536, out);// 导出
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //获取系统管理员
            String[] strArray = userService.queryAllAdmin().stream().filter(user -> null != user.getEmail()).map(User::getEmail).toArray(String[]::new);
            this.sendSimpleTextMailActual("系统昨日报表", "系统昨日报表在附件请下载",strArray , null, null, new String[]{Conf.getValue("excelImportAddr") + "/" + fileName});

            //获取所有的代理
            for (Merchant merchant : merchantService.agent()) {
                User user = userService.selectById(merchant.getUserId());
                if(null != user && null != user.getEmail()){
                    orderPage.setAgents(true);
                    orderPage.setUserId(merchant.getUserId());
                    List<OrderExport> orderExports1 = orderService.exportOrder(orderPage);
                    List<List<OrderExport>> lists1 = new ArrayList<>(orderExports1.stream().collect(Collectors.groupingBy(OrderExport::getMerchantName)).values());
                    String name1 = System.currentTimeMillis() + "";
                    name = name.substring(7, 13);
                    String fileName1 = "订单信息" + name1 + ".xls";
                    try {
                        FileOutputStream out = new FileOutputStream(Conf.getValue("excelImportAddr") + "/" + fileName1);
                        ExcelUtil<OrderExport> util = new ExcelUtil<>(OrderExport.class);// 创建工具类.
                        util.exportExcels(lists, "订单新", 65536, out);// 导出
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    this.sendSimpleTextMailActual("系统昨日报表", "系统昨日报表在附件请下载",new String[]{user.getEmail()} , null, null, new String[]{Conf.getValue("excelImportAddr") + "/" + fileName1});
                }
                System.err.println("发送接收");
            }
        }
    }
    /**
     * 发送一个简单的文本邮件，可以附带附件：文本邮件发送的基本方法
     *
     * @param subject：邮件主题，即邮件的邮件名称
     * @param content：邮件内容
     * @param toWho：需要发送的人
     * @param ccPeoples：需要抄送的人
     * @param bccPeoples：需要密送的人
     * @param attachments：需要附带的附件，附件请保证一定要存在，否则将会被忽略掉
     */
    private void sendSimpleTextMailActual(String subject, String content, String[] toWho, String[] ccPeoples, String[] bccPeoples, String[] attachments) {

        //检验参数：邮件主题、收件人、邮件内容必须不为空才能够保证基本的逻辑执行
        if (subject == null || toWho == null || toWho.length == 0 || content == null) {

            logger.error("邮件-> {} 无法继续执行，因为缺少基本的参数：邮件主题、收件人、邮件内容", subject);

            throw new RuntimeException("模板邮件无法继续发送，因为缺少必要的参数！");
        }

        logger.info("开始发送简单文本邮件：主题->{}，收件人->{}，抄送人->{}，密送人->{}，附件->{}", subject, toWho, ccPeoples, bccPeoples, attachments);

        //附件处理，需要处理附件时，需要使用二进制信息，使用 MimeMessage 类来进行处理
        if (attachments != null && attachments.length > 0) {

            try {
                //附件处理需要进行二进制传输
                MimeMessage mimeMessage = mailSender.createMimeMessage();

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, DEFAULT_ENCODING);

                //设置邮件的基本信息：这些函数都会在后面列出来
                boolean continueProcess = handleBasicInfo(helper, subject, content, toWho, ccPeoples, bccPeoples);

                //如果处理基本信息出现错误
                if (!continueProcess) {

                    logger.error("邮件基本信息出错: 主题->{}", subject);

                    return;
                }

                //处理附件
                handleAttachment(helper, subject, attachments);

                //发送该邮件
                mailSender.send(mimeMessage);

                logger.info("发送邮件成功: 主题->{}", subject);

            } catch (MessagingException e) {
                e.printStackTrace();

                logger.error("发送邮件失败: 主题->{}", subject);
            }
        } else {

            //创建一个简单邮件信息对象
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            //设置邮件的基本信息
            handleBasicInfo(simpleMailMessage, subject, content, toWho, ccPeoples, bccPeoples);

            //发送邮件
            mailSender.send(simpleMailMessage);

            logger.info("发送邮件成功: 主题->{}", subject, toWho, ccPeoples, bccPeoples, attachments);
        }
    }

    /**
     * 处理二进制邮件的基本信息，比如需要带附件的文本邮件、图片邮件、模板邮件等等
     *
     * @param mimeMessageHelper ：二进制文件的包装类
     * @param subject           ：邮件主题
     * @param content           ：邮件内容
     * @param toWho             ：收件人
     * @param ccPeoples         ：抄送人
     * @param bccPeoples        ：暗送人
     * @return ：返回这个过程中是否出现异常，当出现异常时会取消邮件的发送
     */
    private boolean handleBasicInfo(MimeMessageHelper mimeMessageHelper, String subject,
                                    String content, String[] toWho, String[] ccPeoples, String[] bccPeoples) {

        try {
            //设置必要的邮件元素

            //设置发件人
            mimeMessageHelper.setFrom(userName);
            //设置邮件的主题
            mimeMessageHelper.setSubject(subject);
            //设置邮件的内容，区别是否是HTML邮件
            mimeMessageHelper.setText(content, false);
            //设置邮件的收件人
            mimeMessageHelper.setTo(toWho);

            //设置非必要的邮件元素，在使用helper进行封装时，这些数据都不能够为空

            if (ccPeoples != null)
                //设置邮件的抄送人：MimeMessageHelper # Assert.notNull(cc, "Cc address array must not be null");
                mimeMessageHelper.setCc(ccPeoples);

            if (bccPeoples != null)
                //设置邮件的密送人：MimeMessageHelper # Assert.notNull(bcc, "Bcc address array must not be null");
                mimeMessageHelper.setBcc(bccPeoples);

            return true;
        } catch (MessagingException e) {
            e.printStackTrace();

            logger.error("邮件基本信息出错->{}", subject);
        }


        return false;
    }

    /**
     * 用于填充简单文本邮件的基本信息
     *
     * @param simpleMailMessage：文本邮件信息对象
     * @param subject：邮件主题
     * @param content：邮件内容
     * @param toWho：收件人
     * @param ccPeoples：抄送人
     * @param bccPeoples：暗送人
     */
    private void handleBasicInfo(SimpleMailMessage simpleMailMessage, String subject, String content, String[] toWho, String[] ccPeoples, String[] bccPeoples) {

        //设置发件人
        simpleMailMessage.setFrom(userName);
        //设置邮件的主题
        simpleMailMessage.setSubject(subject);
        //设置邮件的内容
        simpleMailMessage.setText(content);
        //设置邮件的收件人
        simpleMailMessage.setTo(toWho);
        //设置邮件的抄送人
        simpleMailMessage.setCc(ccPeoples);
        //设置邮件的密送人
        simpleMailMessage.setBcc(bccPeoples);
    }

    /**
     * 用于处理附件信息，附件需要 MimeMessage 对象
     *
     * @param mimeMessageHelper：处理附件的信息对象
     * @param subject：邮件的主题，用于日志记录
     * @param attachmentFilePaths：附件文件的路径，该路径要求可以定位到本机的一个资源
     */
    private void handleAttachment(MimeMessageHelper mimeMessageHelper, String subject, String[] attachmentFilePaths) {

        //判断是否需要处理邮件的附件
        if (attachmentFilePaths != null && attachmentFilePaths.length > 0) {

            FileSystemResource resource;

            String fileName;

            //循环处理邮件的附件
            for (String attachmentFilePath : attachmentFilePaths) {

                //获取该路径所对应的文件资源对象
                resource = new FileSystemResource(new File(attachmentFilePath));

                //判断该资源是否存在，当不存在时仅仅会打印一条警告日志，不会中断处理程序。
                // 也就是说在附件出现异常的情况下，邮件是可以正常发送的，所以请确定你发送的邮件附件在本机存在
                if (!resource.exists()) {

                    logger.warn("邮件->{} 的附件->{} 不存在！", subject, attachmentFilePath);

                    //开启下一个资源的处理
                    continue;
                }

                //获取资源的名称
                fileName = resource.getFilename();

                try {

                    //添加附件
                    mimeMessageHelper.addAttachment(fileName, resource);

                } catch (MessagingException e) {

                    e.printStackTrace();

                    logger.error("邮件->{} 添加附件->{} 出现异常->{}", subject, attachmentFilePath, e.getMessage());
                }
            }
        }
    }
}