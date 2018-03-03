package com.getinfocia.infocia.item;

public class ItemCategory {
	
	private int CategoryId; 
	private String CategoryName;
	private String CategoryImageUrl;
	 
	public ItemCategory(int categoryid,String title,String catimageurl) {
		this.CategoryId=categoryid;
		this.CategoryName = title;
		this.CategoryImageUrl=catimageurl;
	}
	
	public ItemCategory()
	{
		
	}
	public int getCategoryId() {
		return CategoryId;
	}

	public void setCategoryId(int categoryid) {
		this.CategoryId = categoryid;
	}
	
	
	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryname) {
		this.CategoryName = categoryname;
	}
	
	public String getCategoryImageurl()
	{
		return CategoryImageUrl;
		
	}
	
	public void setCategoryImageurl(String catimageurl)
	{
		this.CategoryImageUrl=catimageurl;
	}

}
