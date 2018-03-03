package com.getinfocia.infocia.item;

public class ItemOther {

	int ImgRes;
	String Name;
	
	
	public ItemOther(int img,String name) {
		// TODO Auto-generated constructor stub
		this.ImgRes=img;
		this.Name=name;
	}


	public int getImgRes()
	{
		return ImgRes;
	}

	public void setImgRes(int img)
	{
		this.ImgRes=img;
	}

	public String getName()
	{
		return Name;
	}

	public void setName(String name)
	{
		this.Name=name;
	}

}
