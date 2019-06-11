package com.pactera.common.excel;

/**
 * 
 * 自定义Excel模板异常类.
 * @author solutionNC 2018年2月5日
 */
public class ExcelImportException extends Exception{
	
	private static final long serialVersionUID = 4578338686463106663L;

	public ExcelImportException(String msg) {
		super(msg);
	}
}