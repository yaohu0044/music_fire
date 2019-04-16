import com.musicfire.common.utiles.ExcelUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExportTest03 {
    public static void main(String[] args) {
        // 初始化数据
        List<StudentVO> list = new ArrayList<>();
        StudentVO vo = new StudentVO();
        vo.setId(1);
        vo.setName("李坤1");
        vo.setAge(26);
        vo.setClazz("五期提高班1");
        vo.setCompany("天融信1");
        list.add(vo);

        StudentVO vo2 = new StudentVO();
        vo2.setId(2);
        vo2.setAge(27);
        vo2.setName("曹贵生2");
        vo2.setClazz("五期提高班2");
        vo2.setCompany("中银2");
        list.add(vo2);

        List<StudentVO> list1 = new ArrayList<>();
        StudentVO vo3 = new StudentVO();
        vo3.setId(3);
        vo3.setAge(28);
        vo3.setName("曹贵生3");
        vo3.setClazz("五期提高班3");
        vo3.setCompany("中银3");
        list1.add(vo3);

        StudentVO vo4 = new StudentVO();
        vo4.setId(4);
        vo4.setAge(29);
        vo4.setName("曹贵生4");
        vo4.setClazz("五期提高班4");
        vo4.setCompany("中银4");
        list1.add(vo4);

        List<StudentVO> list2 = new ArrayList<>();
        StudentVO vo5 = new StudentVO();
        vo5.setId(3);
        vo5.setAge(28);
        vo5.setName("曹贵生3");
        vo5.setClazz("五期提高班3");
        vo5.setCompany("中银3");
        list2.add(vo5);


        List<List<StudentVO>> lists = new ArrayList<>();
        lists.add(list);
        lists.add(list1);
        lists.add(list2);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("d:\\success6.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ExcelUtil2<StudentVO> util = new ExcelUtil2<>(StudentVO.class);// 创建工具类.
        util.exportExcels(lists, "学生信息", 65536, out);// 导出
        System.out.println("----执行完毕----------");
//        ExcelUtil1<StudentVO> util1 = new ExcelUtil1<>(StudentVO.class);// 创建工具类.
//        util1.exportExcel(list1, "学生信息", 65536, out);// 导出
//        System.out.println("----执行完毕----------");
    }

}