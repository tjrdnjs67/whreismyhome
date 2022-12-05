package com.ssafy.vue.model.service;

import java.util.Map;

public interface JwtService {

//	<T> String create(String key, T data, String subject);
//	Map<String, Object> get(String key);
//	String getUserId();
//	boolean isUsable(String jwt);
	
	<T> String createAccessToken(String key, T data);
	<T> String createRefreshToken(String key, T data);
	<T> String create(String key, T data, String subject, long expir);
	Map<String, Object> get(String key);
	String getUserId();
	boolean checkToken(String jwt);
	
}