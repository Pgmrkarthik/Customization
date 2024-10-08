package com.dev.customization.configuration;

public class AppConstants {
	
	public static final String PAGE_NUMBER = "0";
	public static final String PAGE_SIZE = "2";
	public static final String SORT_CATEGORIES_BY = "categoryId";
	public static final String SORT_PRODUCTS_BY = "productId";
	public static final String SORT_USERS_BY = "userId";
	public static final String SORT_ORDERS_BY = "totalAmount";
	public static final String SORT_DIR = "asc";
	public static final Long ADMIN_ID = 101L;
	public static final Long USER_ID = 102L;
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	public static final String[] PUBLIC_URLS = { "/api/auth/login","/api/auth/signup", "/api/public/**","/api/auth/copy" };
	public static final String[] USER_URLS = { "/api/s3/upload","/api/custom_space/upload","/api/custom_space/publish","/api/template","/api/template/create","/api/template/all","/api/template/**",
			"/api/virtual_space/**","/api/custom_space/create","/api/custom_space/create","/api/custom_space/all" };
	public static final String[] ADMIN_URLS = { "/api/bucket/**" };
	public static final String[] API_URLS  = { "/api/register","/api/login", "/api/public/**","/swagger-ui/**" };
	
}
