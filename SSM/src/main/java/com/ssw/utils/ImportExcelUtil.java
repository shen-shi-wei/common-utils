package com.ssw.utils;


import com.ssw.exception.ExcelException;
import jxl.write.WritableSheet;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author ssw
 * @Description: 导入excel工具类
 * @CreateDate: 2020/01/10 09点26分
 * @Version: 1.0
 */
public class ImportExcelUtil {

    public static Workbook readExcel(InputStream in, String contentType) {
        Workbook workbook = null;
        try {
            if ("application/vnd.ms-excel".equalsIgnoreCase(contentType)) { // xls文件
                workbook = new HSSFWorkbook(in);
            } else if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(contentType)) { //xlsx文件
                workbook = new XSSFWorkbook(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return workbook; //获取到符合格式的excel数据集合
    }

    /**
     * @param in          ：承载着Excel的输入流
     * @param entityClass ：List中对象的类型（Excel中的每一行都要转化为该类型的对象）
     * @param fieldMap    ：Excel中的中文列头和类的英文属性的对应关系Map
     * @param contentType ：文件类型判断具体的xls/xlsx
     * @return ：List
     * @throws ExcelException
     * @MethodName : excelToList
     * @Description : 将Excel转化为List
     */
    public static <T> List<T> excelToList(InputStream in, Class<T> entityClass,
                                          LinkedHashMap<Integer, String> fieldMap, String contentType) throws ExcelException {

        //定义要返回的list
        List<T> resultList = new ArrayList<T>();

        try {

            //根据Excel数据源创建WorkBook
            Workbook wb = readExcel(in, contentType);
            //获取工作表
            Sheet sheet = wb.getSheetAt(0);
            //获取工作表中总行数，总列数
            int rowNum = sheet.getPhysicalNumberOfRows() + 1;
            if (rowNum <= 1){
                throw new ExcelException("Excel没有数据!");
            }
            Row firstRow = sheet.getRow(0);
            int columnNum = firstRow.getPhysicalNumberOfCells();
            if (columnNum < fieldMap.size()) {
                throw new ExcelException("Excel中缺少字段!");
            }

            //将sheet转换成list
            for (int i = 1; i < rowNum; i++) {
                //新建要转换的对象
                T entity = entityClass.newInstance();
                Row row = sheet.getRow(i);
                int num = 0;
                if (row != null ) {
                    //给对象设值
                    for (Map.Entry<Integer, String> entry : fieldMap.entrySet()) {
                        Integer number = entry.getKey();    //表格列号
                        String value = entry.getValue();    //英文字段名
                        Cell cell = row.getCell(number);
                        String cellValue = getCellValue(cell);   //表格字段值
                        //给对象赋值
                        setFieldValueByName(value, cellValue, entity);
                        if (cellValue == null || "".equals(cellValue)){
                            num ++;
                        }
                    }
                    resultList.add(entity);
                }
                if (num == columnNum){
                    throw new ExcelException("Excel中第"+(i+1)+"行是空行,如要删除，请在Excel中右键点击删除，告知delete没有用,另外还需重新导入!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //如果是ExcelException，则直接抛出
            if (e instanceof ExcelException) {
                throw (ExcelException) e;
                //否则将其它异常包装成ExcelException再抛出
            } else {
                e.printStackTrace();
                throw new ExcelException("导入Excel失败");
            }
        }
        return resultList;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                //short s = cell.getCellStyle().getDataFormat();
//                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
//                    SimpleDateFormat sdf = null;
//                    // 验证short值
//                    if (cell.getCellStyle().getDataFormat() == 14) {
//                        sdf = new SimpleDateFormat("yyyy/MM/dd");
//                    } else if (cell.getCellStyle().getDataFormat() == 21) {
//                        sdf = new SimpleDateFormat("HH:mm:ss");
//                    } else if (cell.getCellStyle().getDataFormat() == 22) {
//                        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                    } else {
//                        throw new RuntimeException("日期格式错误!!!");
//                    }
//                    Date date = cell.getDateCellValue();
//                    cellValue = sdf.format(date);
//                } else if (cell.getCellStyle().getDataFormat() == 0) {//处理数值格式
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    cellValue = String.valueOf(cell.getRichStringCellValue().getString());
//                }else {
//                    cellValue = String.valueOf(cell.toString());
//                }

                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                            .getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date date = cell.getDateCellValue();
                    cellValue = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil
                            .getJavaDate(value);
                    cellValue = sdf.format(date);
                } else {
                    double value = cell.getNumericCellValue();
                    CellStyle style = cell.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if (temp.equals("General")) {
                        format.applyPattern("#");
                    }
                    cellValue = format.format(value);
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = null;
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 根据字段名给对象的字段赋值
     *
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @param o          对象
     * @throws Exception 异常
     */
    public static void setFieldValueByName(String fieldName, Object fieldValue, Object o) throws Exception {

        Field field = getFieldByName(fieldName, o.getClass());
        if (field != null) {
            field.setAccessible(true);
            // 获取字段类型
            Class<?> fieldType = field.getType();

            // 根据字段类型给字段赋值
            if (String.class == fieldType) {
                field.set(o, String.valueOf(fieldValue));
            } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                field.set(o, Integer.parseInt(fieldValue.toString()));
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                field.set(o, Long.valueOf(fieldValue.toString()));
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                field.set(o, Float.valueOf(fieldValue.toString()));
            } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                field.set(o, Short.valueOf(fieldValue.toString()));
            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                field.set(o, Double.valueOf(fieldValue.toString()));
            } else if (Character.TYPE == fieldType) {
                if ((fieldValue != null) && (fieldValue.toString().length() > 0)) {
                    field.set(o, Character.valueOf(fieldValue.toString().charAt(0)));
                }
            } else if (Date.class == fieldType) {
                if (!fieldValue.toString().isEmpty()) {
                    if (fieldValue.toString().length() > 10) {

                        field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue.toString()));
                    } else {
                        field.set(o, new SimpleDateFormat("yyyy-MM-dd").parse(fieldValue.toString()));
                    }
                }
            } else {
                field.set(o, fieldValue);
            }
        } else {
            throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
        }
    }

    /**
     * @param sourceSheet
     * @param eSheet      错误列表
     * @param errorMap    错误原因
     * @throws Exception
     * @MethodName : addErrorRow
     * @Description : 添加一行错误列表
     */
//    private void addErrorRow(Sheet sourceSheet, WritableSheet eSheet, LinkedHashMap<Integer, String> errorMap) throws Exception {
//
//        // 复制错误的数据到错误列表
//        for (Map.Entry<Integer, String> entry : errorMap.entrySet()) {
//            int errorNo = entry.getKey();
//            String reason = entry.getValue();
//            int rows = eSheet.getRows();
//            for (int i = 0; i < sourceSheet.getColumns(); i++) {
//                System.out.println("错误列表当前列号" + i);
//                eSheet.addCell(new Label(i, rows, sourceSheet.getCell(i, errorNo).getContents()));
//            }
//
//            // 添加错误原因和所在行号
//            eSheet.addCell(new Label(sourceSheet.getColumns(), rows, reason));
//            eSheet.addCell(new Label(sourceSheet.getColumns() + 1, rows, String.valueOf(errorNo + 1)));
//        }
//    }

    /**
     * 设置工作表自动列宽和首行加粗
     *
     * @param ws        要设置格式的工作表
     * @param extraWith 额外的宽度
     */
    public static void setColumnAutoSize(WritableSheet ws, int extraWith) {
        // 获取本列的最宽单元格的宽度
        for (int i = 0; i < ws.getColumns(); i++) {
            int colWith = 0;
            for (int j = 0; j < ws.getRows(); j++) {
                String content = ws.getCell(i, j).getContents().toString();
                int cellWith = content.length();
                if (colWith < cellWith) {
                    colWith = cellWith;
                }
            }
            // 设置单元格的宽度为最宽宽度+额外宽度
            ws.setColumnView(i, colWith + extraWith);
        }

    }

    /**
     * 根据字段名获取字段
     *
     * @param fieldName 字段名
     * @param clazz     包含该字段的类
     * @return 字段
     */
    public static Field getFieldByName(String fieldName, Class<?> clazz) {
        // 拿到本类的所有字段
        Field[] selfFields = clazz.getDeclaredFields();

        // 如果本类中存在该字段，则返回
        for (Field field : selfFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        // 否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            return getFieldByName(fieldName, superClazz);
        }

        // 如果本类和父类都没有，则返回空
        return null;
    }

    /**
     * 根据实体拿到该实体的所有属性
     *
     * @param clazz 实体
     * @return 返回属性的list集合
     */
    public static List<String> getSuperClassFieldByClass(Class<?> clazz) {

        List<String> list = new ArrayList<String>();

        // 否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz = clazz.getSuperclass();

        Field[] superFields = superClazz.getDeclaredFields();
        for (Field field : superFields) {
            list.add(field.getName());
        }

        // 如果父类没有，则返回空
        return list;
    }


    /**
     * @param clazz       ：对象对应的类
     * @param equalFields ：复合业务主键对应的map
     * @return 查询到的对象
     * @MethodName : getObjByFields
     * @Description :根据复合业务主键查询对象
     */
    private <T> T getObjByFields(Class<T> clazz, LinkedHashMap<Object, Object> equalFields) {

        List<T> list = null;
        if (equalFields.size() != 0) {
            // list=commonBean.findResultListByEqual(clazz, equalFields);
        }

        return list == null || list.size() == 0 ? null : list.get(0);
    }

    /**
     * @param normalFieldMap 普通字段Map
     * @param referFieldMap  引用字段Map
     * @return 组合后的Map
     * @MethodName : combineFields
     * @Description : 组合普通和引用中英文字段Map
     */
    private LinkedHashMap<String, String> combineFields(LinkedHashMap<String, String> normalFieldMap, LinkedHashMap<LinkedHashMap<String, Class<?>>, LinkedHashMap<String, String>> referFieldMap) {

        LinkedHashMap<String, String> combineMap = new LinkedHashMap<String, String>();

        // 如果存在普通字段，则添加之
        if (normalFieldMap != null && normalFieldMap.size() != 0) {
            combineMap.putAll(normalFieldMap);
        }

        // 如果存在引用字段，则添加之
        if (referFieldMap != null && referFieldMap.size() != 0) {

            // 组建引用中英文字段Map
            LinkedHashMap<String, String> simpleReferFieldMap = new LinkedHashMap<String, String>();
            for (Map.Entry<LinkedHashMap<String, Class<?>>, LinkedHashMap<String, String>> entry : referFieldMap.entrySet()) {
                LinkedHashMap<String, Class<?>> keyMap = entry.getKey();
                LinkedHashMap<String, String> valueMap = entry.getValue();

                // 获取引用中文字段名
                String referField = "";
                for (Map.Entry<String, Class<?>> keyEntry : keyMap.entrySet()) {
                    referField = keyEntry.getKey();
                    break;
                }

                for (Map.Entry<String, String> valueEntry : valueMap.entrySet()) {
                    String enField = valueEntry.getValue();
                    String cnField = valueEntry.getKey();
                    // 拼接英文引用字段
                    String fullEnField = referField + "." + enField;

                    // 放入simpleReferFieldMap
                    simpleReferFieldMap.put(cnField, fullEnField);
                }

            }

            // 放入combineMap
            combineMap.putAll(simpleReferFieldMap);
        }

        return combineMap;

    }


}

