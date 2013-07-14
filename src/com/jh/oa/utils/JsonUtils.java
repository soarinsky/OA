package com.jh.oa.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.stream.JsonReader;
import com.jh.oa.beans.UserInfo;

public class JsonUtils {

	private List<UserInfo> list = null;    // 保存解析friends json数据的user对象
	private UserInfo user = null;          // 保存解析user json数据的user对象

	/**
	 * 解析user json数据
	 * @param userinfo
	 * @return
	 * @throws IOException
	 */
	public UserInfo parseUser(InputStream userinfo) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(userinfo,
				"UTF8"));
		beaginRead(reader);
		reader.close();
		return user;
	}

	/**
	 * 解析friends json数据
	 * @param userinfo
	 * @return
	 * @throws IOException
	 */
	public List<UserInfo> parseFriends(InputStream userinfo) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(userinfo, "UTF8"));
		
//		Log.i("info",convertStreamToString(userinfo));
		beaginRead(reader);
		reader.close();
		return list;
	}
	
	/**
	 * 固定json数据解析demo method
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<UserInfo> parseFriends() throws IOException {
		
		String data3 = "{\"result\":{\"state\":\"ok\", \"organizations\":[ {\"organization_name\":\"精弘1\",  \"friends\":[{\"studentID\":\"111\",\"name\":\"阿宝\",\"post\":\"部员\",\"email\":\"saor@gmail.com\",\"short_phone\":\"520520\",\"long_phone\":\"18767122666\",\"academy\":\"计算机\",\"campus\":\"屏峰\",\"jhID\":\"小白\",\"sex\":\"1\",\"qq\":\"123456789\",\"introduction\":\"我是谁\"  },{\"studentID\":\"222\",\"name\":\"阿宝\",\"post\":\"部员\",\"email\":\"saor@gmail.com\",\"short_phone\":\"520520\",\"long_phone\":\"18767122666\",\"academy\":\"计算机\",\"campus\":\"屏峰\",\"jhID\":\"小白\",\"sex\":\"1\",\"qq\":\"123456789\",\"introduction\":\"我是谁\"  }]}, " +
				"{\"organization_name\":\"精弘2\",  \"friends\":[{\"studentID\":\"333\",\"name\":\"阿信\",\"post\":\"部员\",\"email\":\"saor@gmail.com\",\"short_phone\":\"520520\",\"long_phone\":\"18767122666\",\"academy\":\"计算机\",\"campus\":\"屏峰\",\"jhID\":\"小白\",\"sex\":\"1\",\"qq\":\"123456789\",\"introduction\":\"我是谁\"  }]}, " +
				"{\"organization_name\":\"精弘3\",  \"friends\":[{\"studentID\":\"444\",\"name\":\"凯哥\",\"post\":\"部员\",\"email\":\"saor@gmail.com\",\"short_phone\":\"520520\",\"long_phone\":\"18767122666\",\"academy\":\"计算机\",\"campus\":\"屏峰\",\"jhID\":\"小白\",\"sex\":\"1\",\"qq\":\"123456789\",\"introduction\":\"我是谁\"  }]} ]}}";
		return new JsonUtils().parseFriends(new ByteArrayInputStream(data3.getBytes("UTF-8")));
	}
	
	public static String convertStreamToString(InputStream is) {
	    Scanner s = new Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}

	private void beaginRead(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("result")) {
				readResult(reader);
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
	}

	private void readResult(JsonReader reader) throws IOException {
		boolean flag = false;

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (!flag && name.equals("state")) {
				if (reader.nextString().equals("ok")) {
					flag = true;
				} else {
					return;
				}
			} else if (name.equals("user")) {
				readUser(reader);
			} else if (name.equals("organizations")) {
				readOrganization(reader);
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();

	}

	private void readOrganization(JsonReader reader) throws IOException {
		String organization_name = null;
		list = new ArrayList<UserInfo>();

		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("organization_name")) {
					organization_name = reader.nextString();
				} else if (name.equals("friends")) {
					readFriends(reader, organization_name);
				} else {
					reader.skipValue();
				}
			}
			reader.endObject();
		}
		reader.endArray();
	}

	private void readFriends(JsonReader reader, String organization_name)
			throws IOException {

		reader.beginArray();
		while (reader.hasNext()) {
			UserInfo user = readUser(reader);
			user.setDepartment(organization_name);
			list.add(user);
		}
		reader.endArray();
	}

	private UserInfo readUser(JsonReader reader) throws IOException {
		user = new UserInfo();

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("studentID")) {
				user.setStudentID(reader.nextString());
			} else if (name.equals("name")) {
				user.setRealName(reader.nextString());
				user.setPy(PinyinUtils.getAlpha(user.getRealName()));
			} else if (name.equals("post")) {
				user.setPost(reader.nextString());
			} else if (name.equals("email")) {
				user.setEmail(reader.nextString());
			} else if (name.equals("short_phone")) {
				user.setShortPhoneNumber(reader.nextString());
			} else if (name.equals("long_phone")) {
				user.setLongPhoneNumber(reader.nextString());
			} else if (name.equals("academy")) {
				user.setAcademy(reader.nextString());
			} else if (name.equals("campus")) {
				user.setCampus(reader.nextString());
			} else if (name.equals("jhID")) {
				user.setJhID(reader.nextString());
			} else if (name.equals("sex")) {
				if (reader.nextInt() == 1) {
					user.setSex("男");
				} else {
					user.setSex("女");
				}
			} else if (name.equals("birthday")) {
				user.setBirthday(reader.nextString());
			} else if (name.equals("qq")) {
				user.setQQ(reader.nextString());
			} else if (name.equals("introduction")) {
				user.setIntroduction(reader.nextString());
			} else if (name.equals("course")) {
				user.setCourse(reader.nextString());
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return user;
	}
}
