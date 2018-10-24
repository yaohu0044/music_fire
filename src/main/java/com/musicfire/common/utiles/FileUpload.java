package com.musicfire.common.utiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.*;


/**
 * 文件上传工具类
 *
 * @author liyao
 */
@RestController
public class FileUpload{

    // 文件最大大小
    private static long fileMaxSize = Long.parseLong("200000");

    // 文件缓存大小
    public static final int BUFSIZE = 1024 * 8;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    /**
     * 支持多文件上传
     * 支持表单数据解析
     * 支持小文件单独上传
     * 支持随调随用
     *
     * @throws Exception
     */
    @RequestMapping("upload")
    public static Map<String, Object> upload(HttpServletRequest request,
                                             HttpServletResponse response, boolean isback) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> filelist = new ArrayList<String>();
        List<String> errorlist = new ArrayList<String>();
        Map<String, Object> paramMap = new HashMap<String, Object>(); // 存放参数
        long startTime = System.currentTimeMillis();
        // 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            // 将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            @SuppressWarnings("rawtypes")
            Enumeration params = multiRequest.getParameterNames(); // 获取表单中上传的参数
            while (params.hasMoreElements()) {
                String name = (String) params.nextElement();
                String value = multiRequest.getParameter(name);
                paramMap.put(name, value);
            }
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //一次遍历所有文件
                String filename = iter.next().toString();
                MultipartFile file = multiRequest.getFile(filename);
                if (file != null) {
                    // 上传路径
                    String servicepath = Common.getFilePath() + File.separator;
                    String fwpath = "icon";
                    File mulu = new File(servicepath + fwpath);
                    if (!mulu.exists()) {
                        mulu.mkdirs();    //创建文件夹，服务器路径
                    }
                    String path = servicepath + fwpath + File.separator + startTime;    //服务器路径
                    //上传
                    file.transferTo(new File(path));
                    filelist.add(Common.getServiceFilePath(request) + "/" + fwpath + "/" + startTime);        //访问路径
                }
            }
            map.put("filelist", filelist);
            map.put("params", paramMap); // 表单上传的参数
            if (isback) {
                return map;
            } else {
              //  HttpUtils.doPost(request, response, JsonTool.ToJson(map));
                return null;
            }
        }
        return map;
    }


    /**
     * 大文件上传,优化版
     *
     * @throws Exception
     */
    @RequestMapping("bigupload")
    public @ResponseBody
    String bigFileUpload(HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            // 将request变成多部分request
            MultipartHttpServletRequest multiRequest = multipartResolver
                    .resolveMultipart(request);
            String uuid = multiRequest.getParameter("uuid");
            String type = multiRequest.getParameter("type");
            FileInfo info = getAttribute(uuid, FileInfo.class);
            if (info != null && !info.status) {
                FileInfo inf = new FileInfo();
                inf.clone(info);
                info = inf;
                setAttribute(uuid, info);
                info.start(); //重新启动一个
            }
            if (type == null || uuid == null)
                return "fail";
            if ("handshake".equals(type)) { // 握手信息

                long size = Long.parseLong(multiRequest.getParameter("size"));
                int chunks = Integer.parseInt(multiRequest
                        .getParameter("chunks"));
                String fileName = multiRequest.getParameter("fileName");
                if (size != 0 && fileName != null && chunks != 0) {
                    // 上传路径
                    String servicepath = request.getSession().getServletContext()
                            .getRealPath("")
                            + File.separator + "upload" + File.separator;
                    String fwpath = multiRequest.getParameter("path"); // 获取文件指定添加路径[因为是数据模式
                    // ，所以这里不能用request]
                    if (fwpath == null || "".equals(fwpath)) {
                        fwpath = "other";
                    }
                    File mulu = new File(servicepath + fwpath);
                    if (!mulu.exists()) {
                        mulu.mkdirs(); // 创建文件夹，服务器路径
                    }
                    String[] filename = fileName.split("\\.");
                    String extName = filename[filename.length - 1];    //文件格式
                    if (info == null) info = new FileInfo();    //文件基础信息
                    info.setSize(size);
                    info.setChunks(chunks);
                    info.setName(fileName);
                    info.setUUID(uuid);
                    info.setRealName(servicepath + fwpath + File.separator + uuid + "." + extName);
                    info.start();
                    setAttribute(uuid, info);
                    return "success";
                }
            } else if ("delete".equals(type)) { // 取消上传
                if (info != null) {
                    delAttribute(uuid);
                    File file = new File(info.getRealName());    //获取文件
                    file.delete();
                    return "success";
                }
            }
            int currentChunk = Integer.parseInt(multiRequest.getParameter("currentChunk"));
            info.setCurrentChunk(currentChunk);
            String md5 = multiRequest.getParameter("md5"); // 单块md5值
            MultipartFile file = multiRequest.getFile("file");
            Map<String, String> status = new HashMap<String, String>();
            if (file == null) {
                file = ((MultipartHttpServletRequest) request).getFile("file");
            }
            if (file != null) {
                try {
                    String trunkMd5 = getFileMD5(file.getInputStream());    //计算md5
                    if (!trunkMd5.toUpperCase().equals(md5.toUpperCase())) {    //校验MD5值是否正确
                        status.put("status", "fail");
                    } else {
                        boolean flag = appendFile(info.getOut(), file.getInputStream());    //如果校验成功，则把文件最佳到后面
                        if (flag) {
                            status.put("status", "success");    //添加成功
                            if (info.getChunks() == currentChunk) {
                                info.getOut().close();//关闭输出流
                            }
                        } else status.put("status", "fail");        //添加失败
                    }
                } catch (IllegalStateException | IOException e) {
                    status.put("status", "fail");
                    System.err.print(e.getMessage());
                }
                return JsonTool.ToJson(status);
            }
        }
        Map<String, String> status = new HashMap<String, String>();
        status.put("status", "fail");
        return JsonTool.ToJson(status);
    }

    /**
     * 向文件追加数据
     *
     * @param writer 源文件名
     * @param in     要追加的内容
     * @throws IOException
     */

    public static boolean appendFile(BufferedOutputStream writer, InputStream in) throws IOException {
        boolean flag = true;
        BufferedInputStream bfin = new BufferedInputStream(in);
        try {
            byte buffer[] = new byte[1024];
            int len;
            while ((len = bfin.read(buffer, 0, 1024)) != -1) {
                writer.write(buffer, 0, len);
            }
            bfin.close();
        } catch (IOException e) {
            System.err.print(e.getMessage());
            flag = false;
        } finally {
            if (in != null) in.close();
        }
        return flag;
    }

    /**
     * 文件合并
     *
     * @param outFile
     * @param files
     */
    public static void mergeFiles(String outFile, List<String> files) {
        FileChannel outChannel = null;
        try {
            outChannel = new FileOutputStream(outFile).getChannel();
            for (String f : files) {
                File file = new File(f);
                @SuppressWarnings("resource")
                FileChannel fc = new FileInputStream(file).getChannel();
                ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
                while (fc.read(bb) != -1) {
                    bb.flip();
                    outChannel.write(bb);
                    bb.clear();
                }
                fc.close();
                file.delete(); // 删除文件
            }
        } catch (IOException ioe) {
            System.err.print(ioe.getMessage());
        } finally {
            try {
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    /**
     * 获取文件md5
     *
     * @param in 输入流
     * @return
     * @throws FileNotFoundException
     */
    public static String getFileMD5(InputStream in) {
        String before = "00000000000000000000000000000000"; // 填充为0
        if (in == null)
            return null; // 如果输入流为空，则直接返回null
        BufferedInputStream bfin = new BufferedInputStream(in);
        MessageDigest digest = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            while ((len = bfin.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            bfin.close();
        } catch (Exception e) {
            System.err.print(e.getMessage());
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        String value = bigInt.toString(16);
        if (value.length() < 32)
            value = before.substring(0, 32 - value.length()) + value;
        return value;
    }

    /**
     * 获取文件md5
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static String getFileMD5(File file) {
        String before = "00000000000000000000000000000000"; // 填充为0
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            System.err.print(e.getMessage());
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        String value = bigInt.toString(16);
        if (value.length() < 32)
            value = before.substring(0, 32 - value.length()) + value;
        return value;
    }

    /**
     * 删除key
     *
     * @param key
     * @throws Exception
     */
    public void delAttribute(String key) throws Exception {
        request.removeAttribute(key);
    }

    /**
     * 握手信息存储到session里面
     *
     * @param key
     * @param cla
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key, Class<T> cla) throws Exception {
        Object obj = request.getSession().getAttribute(key);
        return (T) obj;
    }

    /**
     * 握手信息存储到session里面
     *
     * @param key
     * @param obj
     * @return
     * @throws Exception
     */
    public void setAttribute(String key, Object obj) throws Exception {
        request.getSession().setAttribute(key, obj);
    }

    /**
     * 文件信息类
     */
    class FileInfo extends Thread {

        /**
         * 文件大小
         */
        private long size;

        /**
         * 文件名称
         */
        private String name;

        /**
         * 真实名称
         */
        private String realName;

        /**
         * 唯一标识
         */
        private String UUID;

        /**
         * 分割份数
         */
        private int chunks;

        /**
         * 当前分段
         */
        private int currentChunk = 0;

        /**
         * 块文件名
         */
        private List<String> chunkName;

        /**
         * MD5校验值
         */
        private String MD5;

        private BufferedOutputStream out;

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getFileName() {
            return name;
        }

        public void setFileName(String name) {
            this.name = name;
        }

        public String getUUID() {
            return UUID;
        }

        public void setUUID(String uUID) {
            UUID = uUID;
        }

        public int getChunks() {
            return chunks;
        }

        public void setChunks(int chunks) {
            this.chunks = chunks;
        }

        public String getMD5() {
            return MD5;
        }

        public void setMD5(String mD5) {
            MD5 = mD5;
        }

        public List<String> getChunkName() {
            return chunkName;
        }

        public void setChunkName(List<String> chunkName) {
            this.chunkName = chunkName;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public BufferedOutputStream getOut() {
            return out;
        }

        public int getCurrentChunk() {
            return currentChunk;
        }

        public void setCurrentChunk(int currentChunk) {
            this.currentChunk = currentChunk;
        }

        public boolean status = true;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        /**
         * 输出文件目录
         *
         * @param fileName
         */
        public void setOut(String fileName) {
            try {
                this.out = new BufferedOutputStream(new FileOutputStream(
                        fileName, true));
            } catch (FileNotFoundException e) {
                System.err.print(e.getMessage());
            }
        }

        /**
         * 将新的数据拷过来
         *
         * @param info
         */
        public void clone(FileInfo info) {
            this.chunkName = info.getChunkName();
            this.chunks = info.getChunks();
            this.MD5 = info.getMD5();
            this.name = info.getName();
            this.realName = info.getRealName();
            this.UUID = this.getUUID();
        }

        @Override
        public void run() {
            int curr = 0;
            try {
                this.out = new BufferedOutputStream(new FileOutputStream(
                        this.realName, true)); // 重新开启
            } catch (FileNotFoundException e) {
                System.err.print(e.getMessage());
            }
            while (true) {
                try {
                    if (this.getOut() == null)
                        break;
                    if (!this.status)
                        break;
                    if (this.chunks == this.currentChunk)
                        break; // 如果已经上传完毕
                    Thread.sleep(60000); // 每六十秒检查一次
                    if (curr < this.currentChunk) {
                        curr = this.currentChunk;
                    } else {
                        try {
                            this.out.close();
                            this.status = false;
                            System.out.println("文件[" + this.getFileName()
                                    + "]由于长时间没有上传，输出流已经被强制关闭");
                            break;
                        } catch (IOException e) {
                            System.err.print(e.getMessage());
                        }
                    }
                } catch (InterruptedException e) {
                    System.err.print(e.getMessage());
                }
            }
        }
    }

}
