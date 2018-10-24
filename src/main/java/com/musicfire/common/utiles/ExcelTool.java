package com.musicfire.common.utiles;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.*;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 创建Excel工具类
 * 
 * @author liyao
 * 
 */
public class ExcelTool {
	// 创建Excle及工作表
	/**
	 * 
	 * @param cloumn
	 *            列数据
	 * @param data
	 *            数据
	 * @param title
	 *            标题
	 * @param filepath
	 *            路径
	 * @throws Exception
	 */
	public static void createExcel(List<ExcelEntity> cloumn,
			List<Map<String, Object>> data, String title, String filepath)
			throws Exception {
		Map<String, Integer> cloumnIndex = new HashMap<String, Integer>(); // 用来存储列序列
		// --------------------------------------------------创建工作
		WritableWorkbook workbook = Workbook.createWorkbook(new File(filepath));
		// --------------------------------------------------创建工作簿
		WritableSheet sheet = workbook.createSheet(title, 0); // 设置标题

		// 定义单元格样式 [Start]
		WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 9,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat titleCellformat = new WritableCellFormat(wf_title); // 单元格定义
		titleCellformat.setAlignment(Alignment.CENTRE); // 设置对齐方式
		titleCellformat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
		sheet.addCell(new Label(0, 0, title, titleCellformat)); // 标题行
		sheet.mergeCells(0, 0, cloumn.size() - 1, 0); // 标题
		sheet.setRowView(0, 600); // 设置标题行行距
		sheet.setRowView(1, 400); // 设置菜单行行距
		// --------------------------------------------------序列行
		for (int i = 0; i < cloumn.size(); i++) {
			if (cloumn.get(i).getColumnView() != 0)
				sheet.setColumnView(i, cloumn.get(i).getColumnView()); // 设置列的宽度

			sheet.addCell(new Label(i, 1, cloumn.get(i).getValue(),
					titleCellformat)); // Lable(列，行，数据)
			cloumnIndex.put(cloumn.get(i).getKey(), i); // 序列
		}
		// ---------------------------------------------------数据行
		for (int i = 0; i < data.size(); i++) {
			sheet.setRowView(i + 2, 400); // 设置行距
			for (Map.Entry<String, Object> m : data.get(i).entrySet()) {
				if (cloumnIndex.get(m.getKey()) != null)
					sheet.addCell(new Label(cloumnIndex.get(m.getKey()), i + 2,
							m.getValue().toString(), titleCellformat)); // 根据key取序列，i为行数，value为值
			}
		}
		// ----------------------------------------------------以上所写的内容都是写在缓存中的，下一句将缓存的内容写到文件中
		workbook.write();
		workbook.close();
	}

	/**
	 * 根据cloumn和data创建excel，并且通过response输出
	 * 
	 * @param cloumn
	 *            列字段集合
	 * @param data
	 *            数据
	 * @param title
	 *            标题
	 * @param response
	 *            response
	 * @throws Exception
	 */
	public static void createExcel(List<ExcelEntity> cloumn,
			List<Map<String, Object>> data, String title,
			HttpServletResponse response) throws Exception {
		response.reset();// 清空输出流
		String header = new String(title.getBytes("UTF-8"), "ISO8859-1");
		response.setHeader("Content-disposition", "attachment; filename="
				+ header + ".xls");
		// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型
		Map<String, Integer> cloumnIndex = new HashMap<String, Integer>(); // 用来存储列序列
		// --------------------------------------------------创建工作簿
		WritableWorkbook workbook = Workbook.createWorkbook(response
				.getOutputStream());
		// --------------------------------------------------创建工作簿
		WritableSheet sheet = workbook.createSheet(title, 0); // 设置标题
		// 定义单元格样式 [Start]
		WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 9,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat titleCellformat = new WritableCellFormat(wf_title); // 单元格定义
		titleCellformat.setAlignment(Alignment.CENTRE); // 设置对齐方式
		titleCellformat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
/*		sheet.addCell(new Label(0, 0, title, titleCellformat)); // 标题行
		sheet.mergeCells(0, 0, cloumn.size() - 1, 0); // 标题
		sheet.setRowView(0, 600); // 设置标题行行距
*/		sheet.setRowView(0, 400); // 设置菜单行行距
		// --------------------------------------------------序列行
		for (int i = 0; i < cloumn.size(); i++) {
			if (cloumn.get(i).getColumnView() != 0)
				sheet.setColumnView(i, cloumn.get(i).getColumnView()); // 设置列的宽度

			sheet.addCell(new Label(i, 0, cloumn.get(i).getValue(),
					titleCellformat)); // Lable(列，行，数据)
			cloumnIndex.put(cloumn.get(i).getKey(), i); // 序列
		}
		// ---------------------------------------------------数据行
		for (int i = 0; i < data.size(); i++) {
			sheet.setRowView(i + 1, 400); // 设置行距
			for (Map.Entry<String, Object> m : data.get(i).entrySet()) {
				if (cloumnIndex.get(m.getKey()) != null){
					String val = m.getValue()==null?"":m.getValue()+"";
					sheet.addCell(new Label(cloumnIndex.get(m.getKey()), i + 1,
							val, titleCellformat)); // 根据key取序列，i为行数，value为值
				}
			}
		}
		// ----------------------------------------------------以上所写的内容都是写在缓存中的，下一句将缓存的内容写到文件中
		workbook.write();
		workbook.close();

	}

	/**
	 * 
	 * @param cloumn
	 *            列字段集合
	 * @param head
	 *            表头
	 * @param data
	 *            数据
	 * @param title
	 *            标题
	 * @param response
	 *            response
	 * @throws Exception
	 */
	public static void createExcel(List<ExcelEntity> cloumn,
			List<ExcelGrid> head, List<Map<String, Object>> data, String title,
			HttpServletResponse response) throws Exception {
		response.reset();// 清空输出流
		String header = new String(title.getBytes("UTF-8"), "ISO8859-1");
		response.setHeader("Content-disposition", "attachment; filename="
				+ header + ".xls");
		// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型
		Map<String, Integer> cloumnIndex = new HashMap<String, Integer>(); // 用来存储列序列
		Map<Integer, String> feildsub = new HashMap<Integer, String>(); // 用来存储指定列需要拼装的字符串
		// --------------------------------------------------创建工作簿
		WritableWorkbook workbook = Workbook.createWorkbook(response
				.getOutputStream());
		// --------------------------------------------------创建工作簿
		WritableSheet sheet = workbook.createSheet(title, 0); // 设置标题
		// 定义单元格样式 [Start]
		WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat titleCellformat = new WritableCellFormat(wf_title); // 单元格定义
		titleCellformat.setAlignment(Alignment.CENTRE); // 设置对齐方式
		titleCellformat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
		titleCellformat.setBorder(Border.ALL, BorderLineStyle.THIN,
				Colour.BLACK); // 设置边框

		Integer[] in = new Integer[head.size()];
		int s = -1;
		for (ExcelGrid grid : head) {
			s++;
			sheet.addCell(new Label(grid.getLeft(), grid.getTop(), grid
					.getLabel(), titleCellformat)); // 添加单元格
			sheet.mergeCells(grid.getLeft(), grid.getTop(), grid.getWidth()
					+ grid.getLeft() - 1, grid.getHeigth() + grid.getTop() - 1);// 合并单元格
			in[s] = grid.getTop() + grid.getHeigth(); // 最高行
		}
		Arrays.sort(in);
		int maxTop = in[in.length - 1]; // 获取最高行
		sheet.setRowView(0, 600); // 设置标题行行距
		for (int i = 1; i < maxTop + 2; i++) {
			sheet.setRowView(i, 400); // 设置菜单行行距
		}
		// --------------------------------------------------序列行
		for (int i = 0; i < cloumn.size(); i++) {
			if (cloumn.get(i).getColumnView() != 0)
				sheet.setColumnView(i, cloumn.get(i).getColumnView()); // 设置列的宽度

			sheet.addCell(new Label(i, maxTop, cloumn.get(i).getValue(),
					titleCellformat)); // Lable(列，行，数据)
			cloumnIndex.put(cloumn.get(i).getKey(), i); // 序列
			feildsub.put(i, cloumn.get(i).getSubstring()); // 拼装序列
		}
		// ---------------------------------------------------数据行
		for (int i = 0; i < data.size(); i++) {
			sheet.setRowView(i + maxTop + 1, 400); // 设置行距
			for (Map.Entry<String, Object> m : data.get(i).entrySet()) {
				if (cloumnIndex.get(m.getKey()) != null) {
					Integer index = cloumnIndex.get(m.getKey());
					sheet.addCell(new Label(index, i + maxTop + 1, m.getValue()
							.toString() + feildsub.get(index), titleCellformat)); // 根据key取序列，i为行数，value为值
				}
			}
		}
		// ----------------------------------------------------以上所写的内容都是写在缓存中的，下一句将缓存的内容写到文件中
		workbook.write();
		workbook.close();
	}

	/**
	 * 根据数据创建excel并且通过指定的路径输出
	 * 
	 * @param data
	 *            数据
	 * @param title
	 *            标题
	 * @param path
	 *            路径
	 * @throws Exception
	 */
	public static void createExcel(List<String[]> data, String title,
			String path) throws Exception {
		FileOutputStream out = new FileOutputStream(path);
		// --------------------------------------------------创建工作簿
		WritableWorkbook workbook = Workbook.createWorkbook(out);
		// --------------------------------------------------创建工作簿
		WritableSheet sheet = workbook.createSheet(title, 0); // 设置标题

		// ---------------------------------------------------数据行
		for (int i = 0; i < data.size(); i++) {
			sheet.setRowView(i, 400); // 设置行距
			for (int j = 0; j < data.get(i).length; j++) {
				sheet.addCell(new Label(j, i, data.get(i)[j])); // 根据key取序列，i为行数，value为值
			}
		}
		// ----------------------------------------------------以上所写的内容都是写在缓存中的，下一句将缓存的内容写到文件中
		workbook.write();
		workbook.close();

	}

	/**
	 * 读取excel文档流
	 *
	 * @param request
	 *            路径
	 * @return 返回一个集合
	 * @throws BiffException
	 * @throws IOException
	 */
	public static List<String[]> readExcel(HttpServletRequest request) throws BiffException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		MultipartHttpServletRequest multiRequest = multipartResolver
				.resolveMultipart(request);
		MultipartFile file = multiRequest.getFile("file");
		if(file == null ){
			file = ((MultipartHttpServletRequest)request).getFile("file");
		}
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;
		// 获取Excel文件对象
		rwb = Workbook.getWorkbook(file.getInputStream());

		// 获取文件的指定工作表 默认的第一个
		Sheet sheet = rwb.getSheet(0);
		// 行数(表头的目录不需要，从1开始)
		getRows(list, sheet, cell.getContents().trim());
		return list;
	}

	/**
	 * 读取excel文档流
	 *
	 * @param stream
	 *            路径
	 * @return 返回一个集合
	 * @throws BiffException
	 * @throws IOException
	 */
	public static List<String[]> readExcel(InputStream stream) throws BiffException,
			IOException {
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;
		// 获取Excel文件对象
		rwb = Workbook.getWorkbook(stream);

		// 获取文件的指定工作表 默认的第一个
		Sheet sheet = rwb.getSheet(0);
		// 行数(表头的目录不需要，从1开始)
		getRows(list, sheet, cell.getContents());
		return list;
	}

	private static void getRows(List<String[]> list, Sheet sheet, String contents) {
		Cell cell;
		for (int i = 0; i < sheet.getRows(); i++) {
			// 创建一个数组 用来存储每一列的值
			String[] str = new String[sheet.getColumns()];
			// 列数
			for (int j = 0; j < sheet.getColumns(); j++) {
				// 获取第i行，第j列的值
				cell = sheet.getCell(j, i);
				str[j] = contents;
			}
			// 把刚获取的列存入list
			list.add(str);
		}
	}

	/**
	 * 读取excel文档
	 * 
	 * @param path
	 *            路径
	 * @return 返回一个集合
	 * @throws BiffException
	 * @throws IOException
	 */
	public static List<String[]> readExcel(String path) throws BiffException,
			IOException {
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;

		// 创建输入流
		InputStream stream = new FileInputStream(path);

		// 获取Excel文件对象
		rwb = Workbook.getWorkbook(stream);

		// 获取文件的指定工作表 默认的第一个
		Sheet sheet = rwb.getSheet(0);
		// 行数(表头的目录不需要，从1开始)
		getRows(list, sheet, cell.getContents());
		return list;
	}
	//过滤特殊字符
	public static String StringFilter(String str) throws PatternSyntaxException {
		String regEx="[`~!@#$%^&*+=|{}';'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim().replaceAll("[\u00A0]+", "");
	}

	/**
	 * 读取excel文档
	 * 
	 * @param path
	 *            路径
	 * @param Key 可以为空的列ID 多列用,号隔开 如4,5
	 * @return 返回一个集合
	 * @throws BiffException
	 * @throws IOException
	 */
	public static Map<String, Object> readExcel(String path, String Key)
			throws BiffException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> emptylist = new ArrayList<String>();
		List<String> errlist = new ArrayList<String>();
		String errStr = new String();
		String emptyStr = new String();
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;
		try {
			// 创建输入流
			InputStream stream = new FileInputStream(path);

			// 获取Excel文件对象
			rwb = Workbook.getWorkbook(stream);

			// 获取文件的指定工作表 默认的第一个
			Sheet sheet = rwb.getSheet(0);
			// 行数(表头的目录不需要，从1开始)
			for (int i = 0; i < sheet.getRows(); i++) {
				// 创建一个数组 用来存储每一列的值
				String[] str = new String[sheet.getColumns()];
				// 列数
				for (int j = 0; j < sheet.getColumns(); j++) {
					// 获取第i行，第j列的值
					cell = sheet.getCell(j, i);
					str[j] = cell.getContents();
					if(IsEmpty.Is(str[j])){
						str[j] = StringFilter(str[j]);
					}
					if (str[j].indexOf("insert") != -1
							|| str[j].indexOf("delete") != -1
							|| str[j].indexOf("select") != -1
							|| str[j].indexOf("create") != -1) {
						errlist.add((i + 1) + "行," + (j + 1) + "列");
						errStr+=((i + 1) + "行," + (j + 1) + "列"+";");
					}
					// 根据KEY判断那些列不能为空
					//if (("," + Key + ",").indexOf("," + j + ",") == -1) {
					if (("," + Key + ",").indexOf("," + j + ",") == -1) {
						if ("".equals(str[j])) {
							emptylist.add((i + 1) + "行," + (j + 1) + "列");
							emptyStr+=((i + 1) + "行," + (j + 1) + "列"+";");
						}
					}
				}
				// 把刚获取的列存入list
				list.add(str);
			}
			if (errlist.size() > 0) {
				map.put("str", errStr);
				map.put("list", errlist);
				map.put("msg", "该单元格包含特殊字符insert、delete、select、create");
				map.put("flag", "-2");
				return map;
			}
/*			if (emptylist.size() > 0) {
				map.put("str", emptyStr);
				map.put("list", emptylist);
				map.put("msg", "该单元格缺失数据");
				map.put("flag", "-2");
				return map;
			}*/
			map.put("msg", "成功");
			map.put("list", list);
			map.put("flag", "1");
		} catch (BiffException e) {
			map.put("msg", "读取excel时出错");
			map.put("flag", "-1");
			return map;
		} catch (IOException e) {
			map.put("msg", "操作文件出错");
			map.put("flag", "-1");
			return map;
		} catch (Exception e) {
			map.put("msg", e.getMessage());
			map.put("flag", "-1");
			return map;
		}
		return map;
	}
}
