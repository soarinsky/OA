package com.jh.oa.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.jh.oa.beans.UserInfo;

public class SharedPreferenceHelper {
	
	private static SharedPreferenceHelper sharedPreferenceHelper = null;
	private SharedPreferences sharedPreferences;  
	private SharedPreferences.Editor editor;   
	
	public static final String ACADEMY = "academy";
	public static final String BIRTHDAY = "birthday";
	public static final String CAMPUS = "campus";
	public static final String DEPARTMENT = "department";
	public static final String EMAIL = "email";
	public static final String INTRODUCTION = "introduction";
	public static final String JH_ID = "jh_id";
	public static final String LONG_PHONE = "long_phone";
	public static final String QQ = "qq";
	public static final String NAME = "name";
	public static final String SEX = "sex";
	public static final String STUDENT_ID = "student_id";
	public static final String SHORT_PHONE =" short_phone";
	public static final String COURSE = "course";
	public static final String SPACE = " ";
	
	public static final String FRIENDS_EXITS = "friends_exist";
	public static final String USER_EXITS = "user_exist";
	public static final String LOGINED = "logined";
	
	public static final String PATH = "config";
	
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	
	private SharedPreferenceHelper(Context context){
		sharedPreferences = context.getSharedPreferences(PATH, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
	}
	
	public static SharedPreferenceHelper getSharedPreferenceHelper(Context context){
		if(sharedPreferenceHelper == null){
			sharedPreferenceHelper = new SharedPreferenceHelper(context);
		}
		return sharedPreferenceHelper;
	}
	
	public void saveUser(UserInfo user){
		editor.putString(ACADEMY, user.getAcademy());
		editor.putString(BIRTHDAY, user.getBirthday());
		editor.putString(CAMPUS, user.getCampus());
		editor.putString(DEPARTMENT, user.getDepartment());
		editor.putString(EMAIL, user.getEmail());
		editor.putString(INTRODUCTION, user.getIntroduction());
		editor.putString(JH_ID, user.getJhID());
		editor.putString(LONG_PHONE, user.getLongPhoneNumber());
		editor.putString(QQ, user.getQQ());
		editor.putString(NAME, user.getRealName());
		editor.putString(SEX, user.getSex());
		editor.putString(SHORT_PHONE, user.getShortPhoneNumber());
		editor.putString(STUDENT_ID, user.getStudentID());
		editor.putString(COURSE, user.getCourse().toString());

		editor.commit();
	}
	
	public UserInfo getUser(){
		UserInfo user = new UserInfo();
		user.setAcademy(sharedPreferences.getString(ACADEMY, SPACE)); 
		user.setBirthday(sharedPreferences.getString(BIRTHDAY, SPACE));
		user.setCampus(sharedPreferences.getString(CAMPUS, SPACE));
		user.setCourse(sharedPreferences.getString(COURSE, SPACE));
		user.setDepartment(sharedPreferences.getString(DEPARTMENT, SPACE));
		user.setEmail(sharedPreferences.getString(EMAIL, SPACE));
		user.setIntroduction(sharedPreferences.getString(INTRODUCTION, SPACE));
		user.setJhID(sharedPreferences.getString(JH_ID, SPACE));
		user.setLongPhoneNumber(sharedPreferences.getString(LONG_PHONE, SPACE));
		user.setQQ(sharedPreferences.getString(QQ, SPACE));
		user.setRealName(sharedPreferences.getString(NAME, SPACE));
		user.setShortPhoneNumber(sharedPreferences.getString(SHORT_PHONE, SPACE));
		user.setStudentID(sharedPreferences.getString(STUDENT_ID, SPACE));
		return user;
	}
	
	public void saveLoginInfo(String username, String password){
		editor.putString(USERNAME, username);
		editor.putString(PASSWORD, password);
		editor.commit();
	}
	
	public String  getUsername(){
		return sharedPreferences.getString(USERNAME, SPACE);
	}
	
	public String  getPassword(){
		return sharedPreferences.getString(PASSWORD, SPACE);
	}
	
	public boolean isFriendsExist(){
		return sharedPreferences.getBoolean(FRIENDS_EXITS, false);
	}
	
	public void setFriendsExist(boolean falg){
		editor.putBoolean(FRIENDS_EXITS, falg);
		editor.commit();
	}
	
	public boolean isUserExist(){
		return sharedPreferences.getBoolean(USER_EXITS, false);
	}
	
	public void setUserExist(boolean flag){
		editor.putBoolean(USER_EXITS, flag);
		editor.commit();
	}
	
	public boolean isLogined(){
		return sharedPreferences.getBoolean(LOGINED, false);
	}
	
	public void setLogined(boolean flag){
		editor.putBoolean(LOGINED, flag);
		editor.commit();
	}
	
	public void updateUser(UserInfo user){
		saveUser(user);
	}
}
