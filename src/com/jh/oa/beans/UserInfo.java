package com.jh.oa.beans;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.jh.oa.utils.PinyinUtils;

import android.util.Log;
import android.util.Xml;

public class UserInfo {
	
	private static final String UTF8 = "utf8";
	private int id;                           //id主键
	private String studentID;                 //学号
	private String realName;                  //姓名
	private String department;                //部门  --
	private String post;                      //部门职位
	private String py;	                      //姓名首字母   排序使用
	private String email;                     //邮箱
	private String shortPhoneNumber;          //手机短号
	private String longPhoneNumber;           //手机长号
	private String academy;                   //学院
	private String campus;                    //校区
	private String jhID;                      //精弘id
	private String sex;                       //性别
	private String birthday;                  //生日
	private String QQ;                        //qq号
	private String introduction;              //自我简介
	private String course;                    //空课表字符串    0表示没课，1表示有课
	
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		setPy(PinyinUtils.converterToFirstSpell(realName));
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getShortPhoneNumber() {
		return shortPhoneNumber;
	}
	public void setShortPhoneNumber(String shortPhoneNumber) {
		this.shortPhoneNumber = shortPhoneNumber;
	}
	public String getLongPhoneNumber() {
		return longPhoneNumber;
	}
	public void setLongPhoneNumber(String longPhoneNumber) {
		this.longPhoneNumber = longPhoneNumber;
	}
	public String getAcademy() {
		return academy;
	}
	public void setAcademy(String academy) {
		this.academy = academy;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public String getJhID() {
		return jhID;
	}
	public void setJhID(String jhID) {
		this.jhID = jhID;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	
	//解析旧版本的xml格式
	/*public static UserInfo parse(InputStream inputStream) throws IOException{
		UserInfo user = null;
		 //获得XmlPullParser解析器
        XmlPullParser xmlParser = Xml.newPullParser();
        try {        	
            xmlParser.setInput(inputStream, UTF8);
            //获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
            int evtType = xmlParser.getEventType();
			//一直循环，直到文档结束    
            while(evtType!=XmlPullParser.END_DOCUMENT){ 
	    		String tag = xmlParser.getName(); 
			    switch(evtType){ 
			    	case XmlPullParser.START_TAG:
			    		if(tag.equalsIgnoreCase("state") )
			    		{
			    			if(!xmlParser.nextText().equals("ok")){
			    				return null;
			    			}
			    		}
		    			else if(tag.equalsIgnoreCase("user"))
			    		{
			    			user = new UserInfo();
			    		}
			    		else if(user != null)
			    		{	
				            if(tag.equalsIgnoreCase("studentID"))
				            {			      
				            	user.setStudentID(xmlParser.nextText());
				            }
				            else if(tag.equalsIgnoreCase("name"))
				            {			            	
				            	user.setRealName(xmlParser.nextText());
				            	user.setPy(PinyinUtils.getAlpha(user.getRealName()));
				            }
				            else if(tag.equalsIgnoreCase("department"))
				            {			            	
				            	user.setDepartment(xmlParser.nextText());
				            }
				            else if(tag.equalsIgnoreCase("email"))
				            {			            	
				            	user.setEmail(xmlParser.nextText());
				            }
				            else if(tag.equalsIgnoreCase("short_phone"))
				            {			            	
				            	user.setShortPhoneNumber(xmlParser.nextText());		            	
				            }
				            else if(tag.equalsIgnoreCase("long_phone"))
				            {			            	
				            	user.setLongPhoneNumber(xmlParser.nextText());		            	
				            }
				            else if(tag.equalsIgnoreCase("academy"))
				            {			            	
				            	user.setAcademy(xmlParser.nextText());			            	
				            }
				            else if(tag.equalsIgnoreCase("campus"))
				            {			            	
				            	user.setCampus(xmlParser.nextText());      	
				            }	
				            else if(tag.equalsIgnoreCase("jhID"))
				            {			            	
				            	user.setJhID(xmlParser.nextText());			            	
				            }	
				            else if(tag.equalsIgnoreCase("sex"))
				            {			            	
				            	user.setSex(xmlParser.nextText());			            	
				            }	
				            else if(tag.equalsIgnoreCase("birthday"))
				            {			            	
				            	user.setBirthday(xmlParser.nextText());		            	
				            }
				            else if(tag.equalsIgnoreCase("qq"))
				            {	
				            	user.setQQ(xmlParser.nextText()); 
				            }
				            else if(tag.equalsIgnoreCase("introduction"))
				            {			            	
				            	user.setIntroduction(xmlParser.nextText()); 	
				            }
				            else if(tag.equalsIgnoreCase("course"))
				            {			            	
				            	user.setCourse(xmlParser.nextText()); 
				            }
			    		}
			    			
			    		
			    		
			    		break;
			    	case XmlPullParser.END_TAG:		
				       	break; 
			    }
			    //如果xml没有结束，则导航到下一个节点
			    evtType = xmlParser.next();
			}		
        } catch (XmlPullParserException e) {
        	Log.e("parse", e.toString());
			e.printStackTrace();
        } finally {
        	inputStream.close();	
        }      
        
		return user;
	}
	
	public static List<UserInfo> parseFriends(InputStream inputStream) throws IOException{
		
		List<UserInfo> friends = null;
		UserInfo user = null;
		 //获得XmlPullParser解析器
        XmlPullParser xmlParser = Xml.newPullParser();
        try {        	
            xmlParser.setInput(inputStream, UTF8);
            //获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
            int evtType = xmlParser.getEventType();
			//一直循环，直到文档结束    
            while(evtType!=XmlPullParser.END_DOCUMENT){ 
	    		String tag = xmlParser.getName(); 
			    switch(evtType){ 
			    	case XmlPullParser.START_TAG:
			    		if(tag.equalsIgnoreCase("state") )
			    		{
			    			if(!xmlParser.nextText().equals("ok")){
			    				return null;
			    			}
			    		}
		    			if(tag.equalsIgnoreCase("friends"))
			    		{
			    			friends = new ArrayList<UserInfo>();
			    		}
			    		else if(friends != null)
			    		{	
			    			if(tag.equalsIgnoreCase("friend")){
			    				user = new UserInfo();
			    			}
			    			else if(user != null){
					            if(tag.equalsIgnoreCase("studentID"))
					            {			      
					            	user.setStudentID(xmlParser.nextText());
					            }
					            else if(tag.equalsIgnoreCase("name"))
					            {			            	
					            	user.setRealName(xmlParser.nextText());
					            	user.setPy(PinyinUtils.getAlpha(user.getRealName()));
					            }
					            else if(tag.equalsIgnoreCase("department"))
					            {			            	
					            	user.setDepartment(xmlParser.nextText());
					            }
					            else if(tag.equalsIgnoreCase("email"))
					            {			            	
					            	user.setEmail(xmlParser.nextText());
					            }
					            else if(tag.equalsIgnoreCase("short_phone"))
					            {			            	
					            	user.setShortPhoneNumber(xmlParser.nextText());		            	
					            }
					            else if(tag.equalsIgnoreCase("long_phone"))
					            {			            	
					            	user.setLongPhoneNumber(xmlParser.nextText());		            	
					            }
					            else if(tag.equalsIgnoreCase("academy"))
					            {			            	
					            	user.setAcademy(xmlParser.nextText());			            	
					            }
					            else if(tag.equalsIgnoreCase("campus"))
					            {			            	
					            	user.setCampus(xmlParser.nextText());      	
					            }	
					            else if(tag.equalsIgnoreCase("jhID"))
					            {			            	
					            	user.setJhID(xmlParser.nextText());			            	
					            }	
					            else if(tag.equalsIgnoreCase("sex"))
					            {			            	
					            	user.setSex(xmlParser.nextText());			            	
					            }	
					            else if(tag.equalsIgnoreCase("birthday"))
					            {			            	
					            	user.setBirthday(xmlParser.nextText());		            	
					            }
					            else if(tag.equalsIgnoreCase("qq"))
					            {	
					            	user.setQQ(xmlParser.nextText()); 
					            }
					            else if(tag.equalsIgnoreCase("introduction"))
					            {			            	
					            	user.setIntroduction(xmlParser.nextText()); 	
					            }
					            else if(tag.equalsIgnoreCase("course"))
					            {			            	
					            	user.setCourse(xmlParser.nextText()); 
					            }
			    			}
			    		}
			    		
			    		
			    		break;
			    	case XmlPullParser.END_TAG:		
			    		if(tag.equalsIgnoreCase("friend") && user != null){
			    			friends.add(user);
			    		}
				       	break; 
			    }
			    //如果xml没有结束，则导航到下一个节点
			    evtType = xmlParser.next();
			}		
        } catch (XmlPullParserException e) {
			e.printStackTrace();
        } finally {
        	inputStream.close();	
        }      
        
		return friends;
	}*/

}
