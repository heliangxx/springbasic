package com.pactera.common.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @Description：Excel 导出功能
 * @author zfh
 * @date 2019年6月7日
 * @version 1.0.0
 */
public class ExcelExporter {
    /** 类中的字段与Excel列的对应关系 */
    private List<Entry<Field, ExcelColumn>> excelColumns;

    /**
     * 导出对象列表到Excel文件中.</br>
     * 对象字段与Excel列的对应关系在对象的类中通过ExcelColum注解来定义 考虑可能多线程操作，未实现为静态方法
     * 
     * @param records       记录集
     * @param excelFileName excel目标文件名
     * @param sheetName     工作表名，为null时，默认为Sheet1
     * @throws Exception
     * 
     */
    public <T> void exportToExcel(List<T> records, String excelFileName, String sheetName, Class<T> clazz)
            throws Exception {
        initExcelColumns(clazz);

        XSSFWorkbook wb = new XSSFWorkbook();
        String realSheetName = (sheetName != null) ? sheetName : "Sheet1";
        XSSFSheet sheet = wb.createSheet(realSheetName);
        createExcelHeader(wb, sheet);
        createExcelBody(wb, sheet, records);

        // 保存到文件
        File excelFile = new File(excelFileName);
        try (OutputStream os = new FileOutputStream(excelFile);) {
            wb.write(os);
        }
    }

    /**
     * 创建excel 抬头.
     * 
     * @param excelColumns excel 列的信息
     * @param wb           HSSFWorkbook
     * @param sheet        HSSFSheet
     * 
     */
    private void createExcelHeader(XSSFWorkbook wb, Sheet sheet) {
        Row row = sheet.createRow(0);

        // 设置样式
        CellStyle style = wb.createCellStyle();
        
        style.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < excelColumns.size(); i++) {
            String headerName = excelColumns.get(i).getValue().name();
            int cellWidth = excelColumns.get(i).getValue().width();

            Cell cell = row.createCell(i);
            cell.setCellValue(headerName);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, 32 * cellWidth);
        }
    }

    /**
     * 填充excel记录行.
     * 
     * @param excelCellList 单元格数据集合
     * @param wb            XSSFWorkbook
     * @param sheet         XSSFSheet
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * 
     */
    private <T> void createExcelBody(XSSFWorkbook wb, XSSFSheet sheet, List<T> records)
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        setFieldsAccesible(true);

        // 数据从第一行开始，第0行是表头
        int rowIndex = 1;
        for (T obj : records) {
            Row row = sheet.createRow(rowIndex);

            for (int colIndex = 0; colIndex < excelColumns.size(); colIndex++) {
                // 获取对应字段的值
                Field field = excelColumns.get(colIndex).getKey();
                Object value = field.get(obj);

                // 获取Cell的数据类型
                ColumnType cellType = excelColumns.get(colIndex).getValue().type();
                Cell cell = row.createCell(colIndex);
                setCellValue(cell, cellType, value);
            }

            rowIndex++;
        }
        setFieldsAccesible(false);
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

    private void setCellValue(Cell cell, ColumnType cellType, Object value) {
        if (value == null) {
            cell.setCellValue("");
        } else {
            switch (cellType) {
            case STRING:
                cell.setCellValue((String) value);
                break;
            case INT:
                cell.setCellValue((int) value);
                break;
            case DOUBLE:
                cell.setCellValue((double) value);
                break;
            case DATE_YMD:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cell.setCellValue(sdf.format((Date) value));
                break;
            case DATE_YMD_HM:
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                cell.setCellValue(sdf.format((Date) value));
                break;
            case DATE_YMD_HMS:
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cell.setCellValue(sdf.format((Date) value));
                break;
            default:
                break;
            }
        }
    }

    private void setFieldsAccesible(boolean flag) {
        for (Entry<Field, ExcelColumn> entry : excelColumns) {
            entry.getKey().setAccessible(flag);
        }
    }

}