package com.pactera.springbasic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pactera.common.excel.ExcelExporter;

import org.junit.Test;

public class ExcelUtilsTest {

	@Test
	public void testExportExcel() {
		List<Order> orders = new ArrayList<Order>();
		Order order = new Order();
		order.setChineseName("订单1");
		order.setEnglishName("Order1");
		order.setPrice(893.23);
		orders.add(order);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		order = new Order();
		order.setChineseName("订单2");
		order.setReportType("Type2");
		order.setOrderDate(new Date());
		order.setAmount(7);
		orders.add(order);
		ExcelExporter excelHelper = new ExcelExporter();
		try {
			excelHelper.exportToExcel(orders, "/Users/heliang/Desktop/exportedOrder.xlsx", null, Order.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}