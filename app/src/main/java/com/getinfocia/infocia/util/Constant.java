package com.getinfocia.infocia.util;

import java.io.Serializable;

public class Constant implements Serializable {

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	
	public static final String HOST_URL="http://inkort.com/";
	
	public static final String IMAGE_PATH=HOST_URL + "upload/";
	
	public static final String GET_CATEGORY = HOST_URL+ "getCategories.php";
	
	public static final String GET_LATEST_NEWS = HOST_URL+ "getAllNews.php";
	
	public static final String GET_LATEST_NEWS_NEW =  HOST_URL+ "getAllLatestNews.php?guid=";
	
	public static final String GET_OLD_NEWS = HOST_URL+ "getAllOldNews.php?guid=";
	
	
	
	public static final String KEY_ARRAY = "posts";
	public static final String KEY_GUIDE = "guid";
	public static final String KEY_TITLE = "title";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_POSTDATE = "postdate";
	public static final String KEY_IMAGEURL = "imgurl";
	public static final String KEY_VIDEOURL = "video_id";
	public static final String KEY_READED = "readed";
	public static final String KEY_SOURCETITLE = "source_title";
	public static final String KEY_SOURCELINK = "source_link";
	
	public static final String CATEGORY_ARRAY_NAME="category";
	public static final String CATEGORY_NAME="category_name";
	public static final String CATEGORY_CID="id";
	public static final String CATEGORY_CIMAGE="category_image";
	
	
	
	
}
