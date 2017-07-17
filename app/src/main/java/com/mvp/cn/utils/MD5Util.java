package com.mvp.cn.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 * 
 * @author 
 * 
 */
public class MD5Util {

	public static void main(String[] args) {
//		System.out.println(md5("123456"));
//		System.out.println(sha1("123456"));
//		System.out.println(md5(sha1("123456")));
//
		int a=10;
		int b=20;
		method(a,b);
		System.out.println("a="+a);
		System.out.println("b="+b);
	}
	public static void method(int a,int b){
		for (int i=0;i<10;i++){
			a+=10;
			b+=20;
		}
	}
	/**
	 * md5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		return encrypt(str,"md5");
	}

	/**
	 * sha加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String sha1(String str) {
		return encrypt(str, "sha-1");
	}
	
	
	/**
	 * md5或者sha-1加密
	 * 
	 * @param inputText
	 *            要加密的内容
	 * @param algorithmName
	 *            加密算法名称：md5或者sha-1，不区分大小写
	 * @return
	 */
	private static String encrypt(String inputText, String algorithmName) {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			// m.digest(inputText.getBytes("UTF8"));
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}
	
	/**
	 * 返回十六进制字符串
	 * 
	 * @param arr
	 * @return
	 */
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
}
