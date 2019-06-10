package com.pactera.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * 类名称：Page
 * 类描述：封装Page
 * 创建人：lee
 * 创建时间：2016年11月29日 下午4:35:31
 * @version 1.0.0
 */
public class Page<T> extends PageImpl<T> {

	private static final long serialVersionUID = 1L;

	//分页展示页数计算数量
	private Integer liststep;
	
	//分页展示页数开始页
	private Integer listbegin;
	
	//分页展示页数结束页
	private Integer listend;
	
	//分页当前页数
	private Integer currentPage;
	
	//分页总页数
	private Integer totalPage;
	
	//分页页数
	private Integer pageSize;
	
	//当前页首行行数
	private Integer beginRow;
	
	//当前页末行行数
	private Integer endRow;
	
	//显示页数list
	private List<Integer> listPages;
	
	public Page(List<T> content) {
		super(content);
		liststep = 8;
	}
	
	public Page(List<T> content,Pageable pagealbe,Long total) {
		super(content, pagealbe, total);
		this.liststep = 8;
	}
	
	public Integer getListstep() {
		return liststep;
	}
	
	public Integer getListbegin() {
		int currentPage = this.getNumber()+1;
		listbegin = (currentPage - (int) Math.ceil((double) liststep / 2));//从第几页开始显示页码
		if (listbegin < 1)
		{
		 	listbegin = 1;
		}
		return listbegin;
	}

	public Integer getListend() {
		int currentPage =  this.getNumber() + 1; //当前页
		int totalPage = this.getTotalPages(); //总页数
		listend = currentPage + liststep / 2;//页码信息显示到第几页 
		if (listend > totalPage)
		{
		 	listend = totalPage + 1;
		}
		return listend;
	}
	
	public Integer getCurrentPage() {
		currentPage = this.getNumber()+1;
		return currentPage;
	}

	public Integer getTotalPage() {
		totalPage = this.getTotalPages();
		return totalPage;
	}

	public void setListstep(Integer liststep) {
		this.liststep = liststep;
	}

	public void setListbegin(Integer listbegin) {
		this.listbegin = listbegin;
	}

	public void setListend(Integer listend) {
		this.listend = listend;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getBeginRow() {
		beginRow = (getCurrentPage()-1) * getPageSize() +1;
		return beginRow;
	}

	public void setBeginRow(Integer beginRow) {
		this.beginRow = beginRow;
	}

	public Integer getEndRow() {
		int totalRows = (int)this.getTotalElements();
		endRow = getCurrentPage() * getPageSize();
		endRow = (endRow > totalRows)? totalRows : endRow;
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public Integer getPageSize() {
		pageSize = this.getSize();
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<Integer> getListPages() {
		listPages = new ArrayList<Integer>();
		
		int begin = this.getListbegin();
		int end = this.getListend();
		
		for(int i = begin; i < end; i++){
			listPages.add(i);
		}
		return listPages;
	}

	public void setListPages(List<Integer> listPages) {
		this.listPages = listPages;
	}
}
