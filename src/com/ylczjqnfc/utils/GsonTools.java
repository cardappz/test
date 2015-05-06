package com.ylczjqnfc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Gson:���ڽ���json���ݵĹ�����
 * @author Administrator
 *
 */
public class GsonTools {

	public GsonTools(){
		
	}
	/**
	 * ʹ��Gson���н�����������������Person�������
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> T getT(String jsonString,Class<T> cls){
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * ʹ��Gson���н�������������List<Person>����
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getListT(String jsonString,Class<T> cls){
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			//������jsonString����֮�󣬽�������TypeToken<List<T>>������У�Ȼ���T����ȡ�����е�����
			list = gson.fromJson(jsonString, new TypeToken<List<T>>(){}.getType());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * ʹ��Gson���н�������������List<String>���͵�����
	 */
	public static List<String> getListStr(String jsonString){
		List<String> listStr = new ArrayList<String>();
		try {
			Gson gson = new Gson();
			listStr = gson.fromJson(jsonString,new TypeToken<List<String>>(){}.getType());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listStr;
	}
	
	/**
	 * ʹ��Gson���н�������������List<Map<String,Object>>���͵�����
	 */
	public static List<Map<String,Object>> listKeyMaps(String jsonString){
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		try {
			Gson gson = new Gson();
			listMap = gson.fromJson(jsonString, new TypeToken<List<Map<String,Object>>>(){}.getType());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listMap;
	}
}

