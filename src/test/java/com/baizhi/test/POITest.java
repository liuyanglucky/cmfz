package com.baizhi.test;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class POITest {
    @Autowired
    private UserDao userDao;
    @Test
    public void Test1() throws Exception {
        //------------------------------------------------------------------------------------------------------
        //创建EXCEL表格
        HSSFWorkbook workbook = new HSSFWorkbook();
        //表格名称。    后续需要写入到本地磁盘中
        HSSFSheet sheet = workbook.createSheet("用户信息表");
       //得到EXCEL第一行
        HSSFRow row = sheet.createRow(0);
        //得到EXCEL第一行第一列的单元格
        HSSFCell cell = row.createCell(0);
        //对此单元格书写值
        cell.setCellValue("154用户信息详情表");
        //设置第一行前12列合并单元格
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 11);
        sheet.addMergedRegion(cellAddresses);
        //对EXCEL表格样式的修改（此处演示居中和日期格式的设定）
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
        //对EXCEL设置日期格式
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd HH:mm:ss");
        cellStyle.setDataFormat(format);
        //得到EXCEL第二行
        HSSFRow row1 = sheet.createRow(1);
        //对EXCEL表头进行创建
        String[] s = {"编号","电话号","姓名","密码","密码盐","法名","省份","城市","宣言","照片","性别","日期"};
        //因为创建方式一致，直接遍历数组创建
        for (int i = 0; i <s.length ; i++) {
            //得到第i列
            HSSFCell cell1 = row1.createCell(i);
            //对第i列赋值
            cell1.setCellValue(s[i]);
        }
        //------------------------------------------------------------------------------------------------------
        //导出用户数据到EXCEL表中
          //查询所有的用户
        List<User> users = userDao.selectAll();
        //遍历得到每个用户（因为需要确定行数所以用基本的循环遍历，不用forEach遍历）
        for (int i = 0; i <users.size() ; i++) {
            //创建行(已经创建了表信息行，表头。占用2行所以从第3行创建)
            HSSFRow row2 = sheet.createRow(2 + i);
            //得到每一个用户信息
            User user = users.get(i);
            //利用反射得到类对象
            Class<?> userClass = Class.forName("com.baizhi.entity.User");
            //利用类对象得到所有属性
            Field[] fields = userClass.getDeclaredFields();
            //通过Field[]可以知道属性的个数，或者直接根据表头提示字段直接循环
            for (int j = 0; j <fields.length ; j++) {
                HSSFCell row2Cell = row2.createCell(j);
                //根据属性可获取属性名称
                String fieldName = fields[j].getName();
                //因为是导出到EXCEL表中，所以我们需要获取对象中每个属性所对应的值，用getter方法
                //利用反射的实现拼接getter方法
                String methodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                //System.out.println(methodName);
                //因为实体类的属性的数据类型中存在Data类型，在poi中不同类型调用方法不一致，所以需要判断
                if ("createDate".equals(fieldName)){
                    //通过方法的名称得到方法。
                    Method method = userClass.getDeclaredMethod(methodName);
                    //执行get方法，get方法不需要传参数
                    //user.getId                  调用方法者      传递参数
                    Date invoke = (Date) method.invoke(user);
                    row2Cell.setCellValue(invoke);
                    row2Cell.setCellStyle(cellStyle);
                }else {
                    Method method = userClass.getDeclaredMethod(methodName);
                    //执行get方法，get方法不需要传参数
                    //user.getId                  调用方法者      传递参数
                    String invoke = (String) method.invoke(user);
                    //System.out.println(invoke);
                    row2Cell.setCellValue(invoke);
                }
            }
        }
        //写入到本地磁盘中
        workbook.write(new File("E:/用户信息表.xls"));

    }
}
