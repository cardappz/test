package com.ylczjqnfc.test;

import java.util.ArrayList;
import java.util.List;

public class TestGenericMethod {

	//����һ�����ͷ���
	static <T> void getPerson(T[] a,List<T> lists){
		for(T o:a){
			lists.add(o);
		}
	}
	
	public static void main(String[] args) {
		Object[] object = new Object[100];
		List<Object> lists = new ArrayList<Object>();
		getPerson(object,lists);
	}
}
