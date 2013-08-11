package com.jh.oa.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.jh.oa.beans.Organization;
import com.jh.oa.beans.PageInfo;
import com.jh.oa.beans.UserInfo;
import com.jh.oa.beans.UserSelfInfo;

public class JsonUtils {

	private List<UserInfo> list = null;    // 保存解析friends json数据的user对象
	private UserSelfInfo user = null;          // 保存解析user json数据的user对象
	private PageInfo page = null;
	int i =0;
	
	/**
	 * 解析user json数据
	 * @param userinfo
	 * @return
	 * @throws IOException
	 */
	public UserSelfInfo parseUser(InputStream userinfo) throws IOException {
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
		//Log.i("info:", "parseFriends method:" + i++);
//		//Log.i("info",convertStreamToString(userinfo));
		beaginRead(reader);
		reader.close();
		return list;
	}
	/**
	 * 返回解析的page对象
	 * 必须先调用parseFriends()方法
	 * @return
	 */
	public PageInfo getPage(){
		return page;
	}
	
//	/**
//	 * 固定json数据解析demo method
//	 * 
//	 * @return
//	 * @throws IOException
//	 */
//	public UserSelfInfo parseFriends() throws IOException {
//		
//		String data3 = "{\"result\":{\"state\":\"ok\",\"user\":{\"studentID\":\"201126740113\", \"name\":\"李加贝\", \"email\":\"1111111@qq.com\", \"short_phone\":\"666666\", \"long_phone\":\"13758386666\", \"academy\":\"计算机、软件学院\", \"campus\":\"屏峰\", \"jhID\":\"论坛ID\", \"sex\":\"1\", \"birthday\":\"1990-01-01\", \"qq\":\"1111111\", \"organizations\":[{\"organization_name\":\"精弘网络\",  \"organization_code\":\".1.\",  \"organization_topcode\":\".1.\"}, {\"organization_name\":\"XX社团\",  \"organization_code\":\".2.\",  \"organization_topcode\":\".2.\"}],  \"introduction\":\"\",  \"course\":\"00011110001111111111111111111111111111111111111111111111111111111111111111111\"}  }}";
//		return new JsonUtils().parseUser(new ByteArrayInputStream(data3.getBytes("UTF-8")));
//	}
//	
//	public static String convertStreamToString(InputStream is) {
//	    Scanner s = new Scanner(is).useDelimiter("\\A");
//	    return s.hasNext() ? s.next() : "";
//	}

	private void beaginRead(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("result")) {
				readResult(reader);
			} else if(name.equals("friends")) {
				readFriends(reader);
				readPage(reader);
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
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();

	}

	private void readFriends(JsonReader reader) throws IOException {

		list = new ArrayList<UserInfo>();
		reader.beginArray();
		while (reader.hasNext()) {
			
			UserInfo user = readFriendUser(reader);
			list.add(user);
		}
		reader.endArray();
	}

	private void readPage(JsonReader reader) throws IOException {
		
		page = new PageInfo();
		while(reader.hasNext()){
			String name = reader.nextName();
			if (name.equals("pageNo")) {
				page.setPageNo(reader.nextInt());
			} else if(name.equals("pageSize")){
				page.setPageSize(reader.nextInt());
			} else if(name.equals("records")){
				page.setRecords(reader.nextInt());
			} else if(name.equals("totalPages")){
				page.setTotalPages(reader.nextInt());
			} else {
				reader.skipValue();
			}
		}

	}
	
	private UserSelfInfo readUser(JsonReader reader) throws IOException {
		user = new UserSelfInfo();

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
			} else if (name.equals("department")) {
				user.setDepartment((reader.nextString()));
			}else if (name.equals("sex")) {
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
			} else if (name.equals("organizations")) {
				readOrganization(reader);
			}else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return user;
	}
	
	private UserInfo readFriendUser(JsonReader reader) throws IOException {
		user = new UserSelfInfo();

//		//Log.i("info","readFriendUser begin");
		reader.beginObject();
		while (reader.hasNext()) {
			
			String name = reader.nextName();
			if (name.equals("name")) {
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
			} else if (name.equals("department")) {
				user.setDepartment((reader.nextString()));
			}else if (name.equals("sex")) {
				if (reader.nextInt() == 1) {
					user.setSex("男");
				} else {
					user.setSex("女");
				}
			} else if (name.equals("birthday")) {
				user.setBirthday(reader.nextString());
			} else if (name.equals("qq")) {
				user.setQQ(reader.nextString());
			} else if (name.equals("course")) {
				user.setCourse(reader.nextString());
			}else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return user;
	}
	
	private void readOrganization(JsonReader reader) throws IOException {
	
		List organizations = new ArrayList<Organization>();
	
		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			Organization org = null;
			while (reader.hasNext()) {
				String name = reader.nextName();
				org = new Organization();
				if (name.equals("organization_name")) {
					org.setOrganizationName(reader.nextString());
				} else if (name.equals("organization_code")) {
					org.setOrganizationCode(reader.nextString());
				} else if (name.equals("organization_topcode")) {
					org.setOrganizationTopcode(reader.nextString());
				} else {
					reader.skipValue();
				}
			}
			reader.endObject();
			organizations.add(org);
		}
		reader.endArray();
		user.setOrganizations(organizations);
	}
	
//	public static void main(String args[]) throws IOException{
//		JsonUtils ju = new JsonUtils();
//		UserSelfInfo u = ju.parseFriends();
//		if(u != null){
//			System.out.println(u.getOrganizations().size());
//		}else{
//			System.out.println("error");
//		}
//		
//	}
}
