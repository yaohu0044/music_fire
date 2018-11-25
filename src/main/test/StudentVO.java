import com.musicfire.common.utiles.ExcelVOAttribute;
import lombok.Data;

@Data
public class StudentVO  {

    @ExcelVOAttribute(name = "序号", column = "A")
    private int id;

    @ExcelVOAttribute(name = "姓名", column = "B", isExport = true)
    private String name;

    @ExcelVOAttribute(name = "年龄", column = "C", prompt = "年龄保密哦!", isExport = false)
    private int age;

    @ExcelVOAttribute(name = "班级", column = "D", combo = { "五期提高班", "六期提高班",
            "七期提高班" })
    private String clazz;

    @ExcelVOAttribute(name = "公司", column = "F")
    private String company;

}
