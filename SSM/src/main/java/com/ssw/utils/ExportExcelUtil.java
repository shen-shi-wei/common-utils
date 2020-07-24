package com.ssw.utils;

import com.ssw.entity.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Des  excel导出
 * @Author ssw
 * @Date 2020/3/13 15:22
 */

public class ExportExcelUtil {

    /**
     * 写入导出
     * @param path  模板路径
     * @param list  从数据库中获取到的list
     * @param names  模板第一行按顺序给出每一个字段的get方法到list
     * @param contentType excel的类型 xlsx对应的是application/vnd.openxmlformats-officedocument.spreadsheetml.sheet   xls对应的是application/vnd.ms-excel
     * @param <T>
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> void exportData(String path, List<T> list, List<String> names, String contentType) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        Workbook workbook = readExcel(in, contentType);
        Sheet sheet = workbook.getSheetAt(0);
        FileOutputStream out = new FileOutputStream(path);
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            Class<? extends Object> tClass = t.getClass();

            Row row = sheet.createRow(1+i); //对总行数减1,减去第一行表头，就是倒数行数加数据
            for (int j = 0; j < names.size(); j++) {
                row.createCell(j).setCellValue((String) tClass.getMethod(names.get(j)).invoke(t)); //设置第一个（从0开始）单元格的数据
            }
        }

        out.flush();
        workbook.write(out);
        out.close();
    }

    /**
     *  读取excel中的工作簿
     * @param in  excel文件流
     * @param contentType excel文件类型
     * @return
     */
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
     * 发送文件流到前端
     * @param fileName 导出后文件名
     * @param destFile 要导出的文件
     * @param response 响应
     */
    public void sendFileStream(String fileName, File destFile, HttpServletResponse response) {
        response.reset();
        response.setContentType("application/download");
        response.addHeader("Content-Type", "application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) destFile.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        BufferedInputStream bis = null;
        OutputStream os = null;
        byte[] buff = new byte[1024];
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(destFile));
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("chucuole");
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)  {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId("1");
            user.setUser_name("张三");
            user.setPassword("123456");
            user.setAge((20+i)+"");
            list.add(user);
        }

        try {
            exportData("C:\\Users\\Administrator\\Desktop\\a.xlsx",
                    list, new ArrayList<>(Arrays.asList("getId","getUser_name","getPassword","getAge")),
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
