package com.musicfire.common.utiles;

import com.musicfire.modular.order.dto.OrderExport;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author &#x8a00;&#x66cc;
 * @date 2017/12/24 &#x4e0b;&#x5348;9:08
 */
/*
 * ExcelUtil工具类实现功能:
 * 导出时传入list<T>,即可实现导出为一个excel,其中每个对象Ｔ为Excel中的一条记录.
 * 导入时读取excel,得到的结果是一个list<T>.T是自己定义的对象.
 * 需要导出的实体对象只需简单配置注解就能实现灵活导出,通过注解您可以方便实现下面功能:
 * 1.实体属性配置了注解就能导出到excel中,每个属性都对应一列.
 * 2.列名称可以通过注解配置.
 * 3.导出到哪一列可以通过注解配置.
 * 4.鼠标移动到该列时提示信息可以通过注解配置.
 * 5.用注解设置只能下拉选择不能随意填写功能.
 * 6.用注解设置是否只导出标题而不导出内容,这在导出内容作为模板以供用户填写时比较实用.
 * 本工具类以后可能还会加功能,请关注我的博客: http://blog.csdn.net/lk_blog
 */
public class ExcelUtil<T> {
	private Class<T> clazz;


	private int startRow = 1;
	private int endRow;

	public ExcelUtil(Class<T> clazz) {
		this.clazz = clazz;
	}

	public List<T> importExcel(String sheetName, InputStream input) {
		List<T> list = new ArrayList<T>();
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(input);
			HSSFSheet sheet = workbook.getSheet(sheetName);
			if (!sheetName.trim().equals("")) {
				sheet = workbook.getSheet(sheetName);// 如果指定sheet名,则取指定sheet中的内容.
			}
			if (sheet == null) {
				sheet = workbook.getSheetAt(0); // 如果传入的sheet名不存在则默认指向第1个sheet.
			}
			int rows = sheet.getPhysicalNumberOfRows();
			if (rows > 0) {// 有数据时才处理
				Field[] allFields = clazz.getDeclaredFields();// 得到类的所有field.
				Map<Integer, Field> fieldsMap = new HashMap<>();// 定义一个map用于存放列的序号和field.
				for (Field field : allFields) {
					// 将有注解的field存放到map中.
					if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
						ExcelVOAttribute attr = field
								.getAnnotation(ExcelVOAttribute.class);
						int col = getExcelCol(attr.column());// 获得列号
						// System.out.println(col + "====" + field.getName());
						field.setAccessible(true);// 设置类的私有字段属性可访问.
						fieldsMap.put(col, field);
					}
				}
				for (int i = 1; i < rows; i++) {// 从第2行开始取数据,默认第一行是表头.
					HSSFRow row = sheet.getRow(i);
					int cellNum = row.getLastCellNum();
					T entity = null;
					for (int j = 0; j < cellNum; j++) {
						HSSFCell cell = row.getCell(j);
						if (cell == null) {
							continue;
						}
						String value;
						switch (cell.getCellType()) {
							case NUMERIC: // 数字
								//如果为时间格式的内容
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									//注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
									value = sdf.format(HSSFDateUtil.getJavaDate(cell.
											getNumericCellValue())).toString();
									break;
								} else {
									value = new DecimalFormat("0").format(cell.getNumericCellValue());
								}
								break;
							case STRING: // 字符串
								value = cell.getStringCellValue();
								break;
							case BOOLEAN: // Boolean
								value = cell.getBooleanCellValue() + "";
								break;
							case FORMULA: // 公式
								value = cell.getCellFormula() + "";
								break;
							case BLANK: // 空值
								value = "";
								break;
							case ERROR: // 故障
								value = "非法字符";
								break;
							default:
								value = "未知类型";
								break;
						}
						System.out.println(value);
						if (value.equals("")) {
							continue;
						}
						entity = (entity == null ? clazz.newInstance() : entity);// 如果不存在实例则新建.
						// System.out.println(cells[j].getContents());
						Field field = fieldsMap.get(j);// 从map中得到对应列的field.
						// 取得类型,并根据对象类型设置值.
						Class<?> fieldType = field.getType();
						if (String.class == fieldType) {
							field.set(entity, String.valueOf(value));
						} else if ((Integer.TYPE == fieldType)
								|| (Integer.class == fieldType)) {
							field.set(entity, Integer.parseInt(value));
						} else if ((Long.TYPE == fieldType)
								|| (Long.class == fieldType)) {
							field.set(entity, Long.valueOf(value));
						} else if ((Float.TYPE == fieldType)
								|| (Float.class == fieldType)) {
							field.set(entity, Float.valueOf(value));
						} else if ((Short.TYPE == fieldType)
								|| (Short.class == fieldType)) {
							field.set(entity, Short.valueOf(value));
						} else if ((Double.TYPE == fieldType)
								|| (Double.class == fieldType)) {
							field.set(entity, Double.valueOf(value));
						} else if (Character.TYPE == fieldType) {
							if ((value != null) && (value.length() > 0)) {
								field.set(entity, Character.valueOf(value.charAt(0)));
							}
						}
					}
					if (entity != null) {
						list.add(entity);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 对list数据源将其里面的数据导入到excel表单
	 *
	 * @param sheetName 工作表的名称
	 * @param sheetSize 每个sheet中数据的行数,此数值必须小于65536
	 * @param output    java输出流
	 */
	public boolean exportExcel(List<T> list, String sheetName, int sheetSize,
							   OutputStream output) {
		Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段
		List<Field> fields = new ArrayList<Field>();
		// 得到所有field并存放到一个list中.
		for (Field field : allFields) {
			if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
				fields.add(field);
			}
		}
		HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象
		// excel2003中每个sheet中最多有65536行,为避免产生错误所以加这个逻辑.
		if (sheetSize > 65536 || sheetSize < 1) {
			sheetSize = 65536;
		}
		double sheetNo = Math.ceil(list.size() / sheetSize);// 取出一共有多少个sheet.
		for (int index = 0; index <= sheetNo; index++) {
			HSSFSheet sheet = workbook.createSheet();// 产生工作表对象
			if (sheetNo == 0) {
				workbook.setSheetName(index, sheetName);
			} else {
				workbook.setSheetName(index, sheetName + index);// 设置工作表的名称.
			}
			HSSFRow row;
			HSSFCell cell;// 产生单元格
			row = sheet.createRow(0);// 产生一行
			// 写入各个字段的列头名称
			for (Field field : fields) {
				ExcelVOAttribute attr = field
						.getAnnotation(ExcelVOAttribute.class);
				int col = getExcelCol(attr.column());// 获得列号
				cell = row.createCell(col);// 创建列
				cell.setCellType(CellType.STRING);// 设置列中写入内容为String类型
				cell.setCellValue(attr.name());// 写入列名
				// 如果设置了提示信息则鼠标放上去提示.
				if (!attr.prompt().trim().equals("")) {
					setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);// 这里默认设了2-101列提示.
				}
				// 如果设置了combo属性则本列只能选择不能输入
				if (attr.combo().length > 0) {
					setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);// 这里默认设了2-101列只能选择不能输入.
				}
			}
			int startNo = index * sheetSize;
			int endNo = Math.min(startNo + sheetSize, list.size());
			// 写入各条记录,每条记录对应excel表中的一行
			for (int i = startNo; i < endNo; i++) {
				row = sheet.createRow(i + 1 - startNo);
				T vo = (T) list.get(i); // 得到导出对象.
				for (Field field : fields) {
					field.setAccessible(true);// 设置实体类私有属性可访问
					ExcelVOAttribute attr = field
							.getAnnotation(ExcelVOAttribute.class);
					try {
						// 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
						if (attr.isExport()) {
							cell = row.createCell(getExcelCol(attr.column()));// 创建cell
							cell.setCellType(CellType.STRING);
							cell.setCellValue(field.get(vo) == null ? ""
									: String.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			output.flush();
			workbook.write(output);
			output.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Output is closed ");
			return false;
		}
	}

	/**
	 * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
	 *
	 * @param col
	 */
	private static int getExcelCol(String col) {
		col = col.toUpperCase();
		// 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
		int count = -1;
		char[] cs = col.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
		}
		return count;
	}

	/**
	 * 设置单元格上提示
	 *
	 * @param sheet         要设置的sheet.
	 * @param promptTitle   标题
	 * @param promptContent 内容
	 * @param firstRow      开始行
	 * @param endRow        结束行
	 * @param firstCol      开始列
	 * @param endCol        结束列
	 * @return 设置好的sheet.
	 */
	public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,
										  String promptContent, int firstRow, int endRow, int firstCol,
										  int endCol) {
		// 构造constraint对象
		DVConstraint constraint = DVConstraint
				.createCustomFormulaConstraint("DD1");
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow,
				endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_view = new HSSFDataValidation(
				regions, constraint);
		data_validation_view.createPromptBox(promptTitle, promptContent);
		sheet.addValidationData(data_validation_view);
		return sheet;
	}

	/**
	 * 设置某些列的值只能输入预制的数据,显示下拉框.
	 *
	 * @param sheet    要设置的sheet.
	 * @param textlist 下拉框显示的内容
	 * @param firstRow 开始行
	 * @param endRow   结束行
	 * @param firstCol 开始列
	 * @param endCol   结束列
	 * @return 设置好的sheet.
	 */
	private static void setHSSFValidation(HSSFSheet sheet,
										  String[] textlist, int firstRow, int endRow, int firstCol,
										  int endCol) {
		// 加载下拉列表内容
		DVConstraint constraint = DVConstraint
				.createExplicitListConstraint(textlist);
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow,
				endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_list = new HSSFDataValidation(
				regions, constraint);
		sheet.addValidationData(data_validation_list);
	}
	//发送响应流方法
	public static void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(),"ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 对list数据源将其里面的数据导入到excel表单
	 *
	 * @param sheetName 工作表的名称
	 * @param sheetSize 每个sheet中数据的行数,此数值必须小于65536
	 * @param output    java输出流
	 */
	public boolean exportExcels(List<List<T>> list, String sheetName, int sheetSize,
								OutputStream output) {
		Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段
		List<Field> fields = new ArrayList<Field>();
		// 得到所有field并存放到一个list中.
		for (Field field : allFields) {
			if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
				fields.add(field);
			}
		}
		HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象
		// excel2003中每个sheet中最多有65536行,为避免产生错误所以加这个逻辑.
		if (sheetSize > 65536 || sheetSize < 1) {
			sheetSize = 65536;
		}
		// 取出一共有多少个sheet.
		double sheetNo = Math.ceil(list.get(0).size() / sheetSize);
		for (int index = 0; index <= sheetNo; index++) {
			HSSFSheet sheet = workbook.createSheet();// 产生工作表对象
			if (sheetNo == 0) {
				workbook.setSheetName(index, sheetName);
			} else {
				workbook.setSheetName(index, sheetName + index);// 设置工作表的名称.
			}
			HSSFRow row;
			HSSFCell cell;// 产生单元格
			row = sheet.createRow(0);// 产生一行
			// 写入各个字段的列头名称
			for (Field field : fields) {
				ExcelVOAttribute attr = field
						.getAnnotation(ExcelVOAttribute.class);
				int col = getExcelCol(attr.column());// 获得列号
				cell = row.createCell(col);// 创建列
				cell.setCellType(CellType.STRING);// 设置列中写入内容为String类型
				cell.setCellValue(attr.name());// 写入列名
			}
			for (int j = 0; j < list.size(); j++) {
				endRow += list.get(j).size();
				if(startRow<endRow){
					setMergeColumns(sheet, startRow, endRow, 0, 0);
					setMergeColumns(sheet, startRow, endRow, fields.size()-1, fields.size()-1);
					setMergeColumns(sheet, startRow, endRow, fields.size()-2, fields.size()-2);
				}
				int startNo = index * sheetSize;
				int endNo = Math.min(startNo + sheetSize, list.get(j).size());
				// 写入各条记录,每条记录对应excel表中的一行
				String sumstring ="SUM(";
				String sumstring3 ="SUM(";
				String sumstring1 ="";
				String sumstring2 ="";
				BigDecimal bigDecimal = new BigDecimal(0);
				for (int i = startNo; i < endNo; i++) {
					row = sheet.createRow(startRow+i);
					T vo = (T) list.get(j).get(i); // 得到导出对象.
					for (Field field : fields) {
						field.setAccessible(true);// 设置实体类私有属性可访问
						ExcelVOAttribute attr = field
								.getAnnotation(ExcelVOAttribute.class);
						try {
							// 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
							if (attr.isExport()) {
								cell = row.createCell(getExcelCol(attr.column()));// 创建cell
								cell.setCellType(CellType.STRING);
								cell.setCellValue(field.get(vo) == null ? ""
										: String.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.
							}
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					sumstring1 += "E"+(startRow+i+1)+"+";
					sumstring2 += "K"+(startRow+i+1)+"+";
				}
				if(sumstring1.endsWith("+")){
					sumstring1=sumstring1+0;
				}
				if(sumstring2.endsWith("+")){
					sumstring2=sumstring2+0;
				}
				sumstring =sumstring+ sumstring1+")";//求和公式
				sumstring3 =sumstring3+ sumstring2+")";//求和公式
				sheet.getRow(startRow).getCell(fields.size()-2).setCellFormula(sumstring);
				sheet.getRow(startRow).getCell(fields.size()-1).setCellFormula(sumstring+"-"+sumstring3);
				startRow += endNo;
			}

		}
		try {
			output.flush();
			workbook.write(output);
			output.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Output is closed ");
			return false;
		}
	}

	/**
	 * 设置某些列的值只能输入预制的数据,显示下拉框.
	 *
	 * @param sheet    要设置的sheet.
	 * @param firstRow 开始行
	 * @param endRow   结束行
	 * @param firstCol 开始列
	 * @param endCol   结束列
	 * @return 设置好的sheet.
	 */
	private static void setMergeColumns(HSSFSheet sheet, int firstRow, int endRow, int firstCol, int endCol) {
		CellRangeAddress region = new CellRangeAddress(firstRow, endRow, firstCol, endCol);
		sheet.addMergedRegion(region);
	}
}