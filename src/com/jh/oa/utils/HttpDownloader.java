package com.jh.oa.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.util.Log;


public class HttpDownloader {
	private URL url = null;

	/**
	 * 根据URL下载文件，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容
	 * 1.创建一个URL对象
	 * 2.通过URL对象，创建一个HttpURLConnection对象
	 * 3.得到InputStram
	 * 4.从InputStream当中读取数据
	 * @param urlStr
	 * @return
	 */
	public String download(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// 创建一个URL对象
			url = new URL(urlStr);
			// 创建一个Http连接
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setConnectTimeout(20000);
			// 使用IO流读取数据
			buffer = new BufferedReader(new InputStreamReader(urlConn
					.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("download", e.toString());
			return "0";
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public String downloadByPost(String urlStr, String username, String password) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// 创建一个URL对象
			url = new URL(urlStr);
			// 创建一个Http连接
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setConnectTimeout(20000);
			urlConn.setRequestMethod("POST");
			urlConn.setDoOutput(true);
			PrintWriter out = new PrintWriter(urlConn.getOutputStream());
			String account = "username=" + URLEncoder.encode(username,"UTF-8");
			String passwd = "password=" + URLEncoder.encode(password,"UTF-8");
			out.print(account + "&" + passwd);
			out.close();
			// 使用IO流读取数据
			buffer = new BufferedReader(new InputStreamReader(urlConn
					.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("download", e.toString());
			return "0";
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 根据URL得到输入流
	 * 
	 * @param urlStr
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public InputStream getInputStreamFromUrl(String urlStr)
			throws MalformedURLException, IOException {
		url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}
	
	public InputStream getInputStreamFromUrl(String urlStr, String username, String password, String type) 
			throws MalformedURLException, IOException {
		url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setConnectTimeout(5000);
		urlConn.setRequestMethod("POST");
		urlConn.setDoOutput(true);
		PrintWriter out = new PrintWriter(urlConn.getOutputStream());
		String _account = "stu_no=" + URLEncoder.encode(username,"UTF-8");
		String _passwd = "stu_password=" + URLEncoder.encode(password,"UTF-8");
		String _type = "type=" + URLEncoder.encode(type,"UTF-8");
		out.print(_account + "&" + _passwd + "&" + _type );
		out.close();
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}
	
	public InputStream getInputStreamFromUrl(String urlStr, String username, String type) 
			throws MalformedURLException, IOException {
		url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setConnectTimeout(5000);
		urlConn.setRequestMethod("POST");
		urlConn.setDoOutput(true);
		PrintWriter out = new PrintWriter(urlConn.getOutputStream());
		String _account = "stu_no=" + URLEncoder.encode(username,"UTF-8");
		String _type = "type=" + URLEncoder.encode(type,"UTF-8");
		out.print(_account + "&" + _type );
		out.close();
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}
}
