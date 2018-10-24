package com.musicfire.common.utiles;
/**
 * ExcelTool专用
 * @author xiaoyaogege
 *
 */
public class ExcelEntity {
	private String key;
	private String value;
	private int columnView; 
	private String substring="";		//对字段的修改 
	public ExcelEntity(String key, String value) {
		super();
		this.key = key;
		this.value = value;
		
	}
	/**
	 * 
	 * @param key	字段名
	 * @param value	 字段内容
	 * @param columnView	表格宽度
	 */
	public ExcelEntity(String key, String value, int columnView) {
		super();
		this.key = key;
		this.value = value;
		this.columnView = columnView;
	}

	
	
	public ExcelEntity(String key, String value, int columnView,
			String substring) {
		super();
		this.key = key;
		this.value = value;
		this.columnView = columnView;
		this.substring = substring;
	}
	public String getSubstring() {
		return substring;
	}
	public void setSubstring(String substring) {
		this.substring = substring;
	}
	public int getColumnView() {
		return columnView;
	}

	public void setColumnView(int columnView) {
		this.columnView = columnView;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
