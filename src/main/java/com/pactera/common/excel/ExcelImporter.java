package com.pactera.common.excel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.util.StringUtils;

/**
 * @Description：Excel 导入功能
 * @author zfh
 * @date 2019年6月7日
 * @version 1.0.0
 */
public class ExcelImporter {
	private Workbook workbook;
	private Sheet sheet;

	/** 类中的字段与Excel列的对应关系 */
	private List<Entry<Field, ExcelColumn>> excelColumns;

	public ExcelImporter(String excelFile) throws Exception {
		try (InputStream input = new FileInputStream(excelFile)) {
			workbook = WorkbookFactory.create(input);
		}

		// 缺省取第一个sheet
		setSheetName(null);
	}

	/**
	 * @Description: 设置Sheet，一次操作中，可对多个Sheet进行操作
	 * @param sheetName ：为null时，为第一个Sheet
	 * @author zfh
	 * @date 2019年6月7日
	 */
	public void setSheetName(String sheetName) {
		sheet = (sheetName != null) ? workbook.getSheet(sheetName) : workbook.getSheetAt(0);
	}

	/**
	 * @Description: 获取单元格中的文本
	 * @param rowIndex：行号，0开始
	 * @param colIndex：列号，0开始
	 * @return String
	 * @author zfh
	 * @date 2019年6月7日
	 */
	public String getCellStringValue(int rowIndex, int colIndex) {
		Cell cell = getCell(rowIndex, colIndex);
		return getCellStringValue(cell);
	}

	/**
	 * @Description: 获取单元格中的整数
	 * @param rowIndex：行号，0开始
	 * @param colIndex：列号，0开始
	 * @author zfh
	 * @date 2019年6月7日
	 */
	public Integer getCellIntegerValue(int rowIndex, int colIndex) {
		Cell cell = getCell(rowIndex, colIndex);
		return getCellIntegerValue(cell);
	}

	/**
	 * @Description: 获取单元格中的浮点数
	 * @param rowIndex：行号，0开始
	 * @param colIndex：列号，0开始
	 * @author zfh
	 * @date 2019年6月7日
	 */
	public Double getCellDoubleValue(int rowIndex, int colIndex) {
		Cell cell = getCell(rowIndex, colIndex);
		return getCellDoubleValue(cell);
	}

	/**
	 * @Description: 获取单元格中的日期
	 * @param rowIndex：行号，0开始
	 * @param colIndex：列号，0开始
	 * @author zfh
	 * @date 2019年6月7日
	 */
	public Date getCellDateValue(int rowIndex, int colIndex) {
		Cell cell = getCell(rowIndex, colIndex);
		return getCellDateValue(cell);
	}

	/**
	 * Excel导入<br>
	 * 数据中间不能存在空行、空列<br>
	 * 
	 * @param excelFile : Excel 文件名
	 * @param sheetName : Excel 的Sheet名。为null时，取第一个Sheet
	 * @param headIndex : Excel 表头起始行，首行为0
	 * @return 对象T的列表
	 * @throws Exception
	 */
	public <T> List<T> importList(int headIndex, Class<T> clazz) throws Exception {
		List<T> records = new ArrayList<T>();

		initExcelColumns(clazz);

		Row headerRow = sheet.getRow(headIndex);
		String[] headerNames = getExcelHeaderNames(headerRow);
		Field[] fields = getExcelHeaderRelatedFields(headerNames,clazz);
		setFieldsAccesible(fields, true);

		int minColIndex = headerRow.getFirstCellNum();
		int maxColIndex = headerNames.length;
//			int maxColIndex = headerRow.getLastCellNum();

		for (int rowIndex = headIndex + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if (isRowBlank(row)) {
				continue;
			}

			T obj = clazz.newInstance();

			for (int colIndex = minColIndex; colIndex < maxColIndex; colIndex++) {
				Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				setFieldValueByCell(obj, fields[colIndex], cell);
			}
			records.add(obj);
		}
		setFieldsAccesible(fields, false);

		return records;
	}

	private Cell getCell(int rowIndex, int colIndex) {
		Row row = sheet.getRow(rowIndex);
		return row.getCell(colIndex);
	}

	private String getCellStringValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue().trim();
	}

	private Integer getCellIntegerValue(Cell cell) {
		if (cell == null) {
			return null;
		}

		cell.setCellType(CellType.NUMERIC);
		return (int)cell.getNumericCellValue();
	}

	private Double getCellDoubleValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		
		cell.setCellType(CellType.NUMERIC);
		return cell.getNumericCellValue();
	}

	private Date getCellDateValue(Cell cell) {
		if (cell == null) {
			return null;
		}

		return cell.getDateCellValue();
	}

	/** 从类中根据注解提取Excel各列的信息 */
	private <T> void initExcelColumns(Class<T> clazz) {
		// 提取类中具有ExcelColumn注解的字段及对应的注解
		Map<Field, ExcelColumn> excelColumnsMap = new LinkedHashMap<Field, ExcelColumn>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
			if (excelColumn != null) {
				excelColumnsMap.put(field, excelColumn);
			}
		}

		// 根据注解ExcelColumn中的index，按升序进行排序
		excelColumns = new ArrayList<Entry<Field, ExcelColumn>>(excelColumnsMap.entrySet());
		Collections.sort(excelColumns, new Comparator<Map.Entry<Field, ExcelColumn>>() {
			// 升序排序
			@Override
			public int compare(Entry<Field, ExcelColumn> o1, Entry<Field, ExcelColumn> o2) {
				int index1 = o1.getValue().index();
				int index2 = o2.getValue().index();
				if (index1 < index2) {
					return -1;
				}
				if (index1 == index2) {
					return 0;
				}
				return 1;
			}
		});
	}

	/**
	 * @Description: 判断行是否为空<br>
	 *               只判断了第一个单元的值
	 * @param row
	 * @return boolean
	 * @author zfh
	 * @date 2019年5月30日
	 */
	private boolean isRowBlank(Row row) {
		Cell cell = row.getCell(row.getFirstCellNum());
		String cellValue = getCellStringValue(cell);
		return StringUtils.isEmpty(cellValue);
	}

//	private static String getCellStringValue1(Cell cell) {
//		switch (cell.getCellType()) {
//		case Cell.CELL_TYPE_BOOLEAN:
//			return Boolean.toString(cell.getBooleanCellValue());
//		case Cell.CELL_TYPE_NUMERIC:
//			if (DateUtil.isCellDateFormatted(cell)) {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				return sdf.format(cell.getDateCellValue());
//			}
//			return String.valueOf(cell.getNumericCellValue());
//		case Cell.CELL_TYPE_STRING:
//			return cell.getStringCellValue().trim();
//		default:
//			return "";
//		}
//	}

	/** 把Excel中的Cell单元的值 cell 赋给对象 obj 中的字段 field */
	private void setFieldValueByCell(Object obj, Field field, Cell cell) throws Exception {
		Type type = field.getGenericType();

		switch (type.getTypeName()) {
		case "int":
		case "java.lang.Integer":
			field.set(obj, (int) cell.getNumericCellValue());
			break;
		case "long":
		case "java.lang.Long":
			field.set(obj, (long) cell.getNumericCellValue());
			break;
		case "double":
		case "java.lang.Double":
			field.set(obj, cell.getNumericCellValue());
			break;
		case "java.util.Date":
			field.set(obj, cell.getDateCellValue());
			break;
		case "java.lang.String":
		case "string":
			field.set(obj, getCellStringValue(cell));
			break;
		default:
			String cellValue;
			if (cell.getCellType() == CellType.STRING) {
				cellValue = String.valueOf(cell.getNumericCellValue());
			} else {
				cellValue = cell.getStringCellValue().trim();
			}
			field.set(obj, cellValue);
			break;
		}
	}

	/** 获取Excel表头的每列描述 */
	private static String[] getExcelHeaderNames(Row headerRow) {
		short minColIndex = headerRow.getFirstCellNum();
		short maxColIndex = headerRow.getLastCellNum();
		List<String> headerNames = new ArrayList<String>();

		for (short colIndex = minColIndex; colIndex < maxColIndex; colIndex++) {
			Cell cell = headerRow.getCell(colIndex);
			if (cell == null) {
				break;
			}

			String haederName = cell.getStringCellValue().trim();
			if (StringUtils.isEmpty(haederName)) {
				break;
			}
			headerNames.add(haederName);
		}
		return headerNames.toArray(new String[headerNames.size()]);
	}

	/** 找到类T中有ExcelColumn注解且其名称为headerName的字段 */
	private <T> Field getClassFieldByExcelHeader(String headerName,Class<T> clazz) throws ExcelImportException {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			ExcelColumn excelColumn = field.getDeclaredAnnotation(ExcelColumn.class);
			if ((excelColumn != null) && (excelColumn.name().equals(headerName))) {
				return field;
			}
		}
		String msg = String.format("Excel列 [%s] 没有对应的导入字段", headerName);
		throw new ExcelImportException(msg);
	}

	/**
	 * @Description: 根据Excel文件中每列抬头获取类中对应的字段<br>
	 *               对应的字段是在类中通过注解来标识的<br>
	 * @param headerNames
	 * @return
	 * @throws ExcelImportException Field[]
	 * @author zfh
	 * @date 2019年5月30日
	 */
	private <T> Field[] getExcelHeaderRelatedFields(String[] headerNames,Class<T> clazz) throws ExcelImportException {
		Field[] fields = new Field[headerNames.length];
		for (int i = 0; i < headerNames.length; i++) {
			fields[i] = getClassFieldByExcelHeader(headerNames[i],clazz);
		}
		return fields;
	}

	private void setFieldsAccesible(Field[] fields, boolean flag) {
		for (Field field : fields) {
			field.setAccessible(flag);
		}
	}
}
