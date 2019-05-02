package com.onethree.home.common.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PageVO {
	private String searchType;
	private String keyword;
	private int page= 1;
	private int pageBlockCount = 10;
	private int rowCount=10;
	private int startRow = 0;
	private String paramStr;
	private long totalCount = 0;
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public String getEncodeKeyword() throws Exception {
		String returnStr = "";
		if(keyword!=null && !"".equals(keyword)){
			returnStr = URLEncoder.encode(keyword,"UTF-8");
		}
		return returnStr;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getStartRow() {
		return ((page-1)*rowCount);
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public String getParamStr() throws Exception {
		String paramStr = "page="+page;
		
		paramStr += getSubParamStr();
		
		return paramStr;
	}
	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}
	public String getSubParamStr() throws Exception{
		String paramStr="";
		if(searchType != null && !"".equals(searchType))
		{
			paramStr += "&amp;searchType="+searchType;
		}

		if(keyword != null && !"".equals(keyword))
		{
			paramStr += "&amp;keyword="+getEncodeKeyword();
		}
		
		return paramStr;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageBlockCount() {
		return pageBlockCount;
	}
	public void setPageBlockCount(int pageBlockCount) {
		this.pageBlockCount = pageBlockCount;
	}
	
	
}
