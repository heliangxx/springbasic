package com.pactera.springbasic;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.pactera.common.excel.ColumnType;
import com.pactera.common.excel.ExcelColumn;

public class Order {
    private String id;
    @ExcelColumn(name = "客户参考号", type = ColumnType.STRING, width = 100, index = 0)
    private String refId;

    @ExcelColumn(name = "目标公司中文名称", type = ColumnType.STRING, width = 200, index = 2)
    private String chineseName;

    @ExcelColumn(name = "目标公司英文名称", type = ColumnType.STRING, width = 200, index = 1)
    private String englishName;

    @ExcelColumn(name = "报告类型", type = ColumnType.STRING, width = 50, index = 3)
    private String reportType;

    @ExcelColumn(name = "数量", type = ColumnType.INT, width = 30, index = 5)
    private int amount;

    @ExcelColumn(name = "日期", type = ColumnType.DATE_YMD_HMS, width = 100, index = 4)
    private Date orderDate;

    @ExcelColumn(name = "价格", type = ColumnType.DOUBLE, width = 80, index = 6)
    private Double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTest() {
        return "JSON Test";
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }
}
