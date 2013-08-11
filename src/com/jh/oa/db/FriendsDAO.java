package com.jh.oa.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jh.oa.beans.FriendBasicInfo;
import com.jh.oa.beans.UserInfo;

public class FriendsDAO {

	private DBHelper dbHelper;
	private static FriendsDAO friendDao;
	
	private FriendsDAO(Context context){
		dbHelper = new DBHelper(context);
	}
	
	public static FriendsDAO getFriendsDAOInstance(Context context){
		if(friendDao == null){
			friendDao = new FriendsDAO(context);
		}
		return friendDao;
	}
	
	public List<UserInfo> getAllFriends(){
		SQLiteDatabase  db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from friends order by py ASC", null);
		List<UserInfo> friends = new ArrayList<UserInfo>();
		while(cursor.moveToNext()){
			UserInfo f = new UserInfo();
			
			f.setId(cursor.getInt(cursor.getColumnIndex("id")));
			f.setAcademy(cursor.getString(cursor.getColumnIndex("academy")));
			f.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
			f.setCampus(cursor.getString(cursor.getColumnIndex("campus")));
			f.setCourse(cursor.getString(cursor.getColumnIndex("course")) );
			f.setDepartment(cursor.getString(cursor.getColumnIndex("department")));
			f.setEmail(cursor.getString(cursor.getColumnIndex("email")));
			f.setIntroduction(cursor.getString(cursor.getColumnIndex("introduction")));
			f.setJhID(cursor.getString(cursor.getColumnIndex("jh_id")));
			f.setLongPhoneNumber(cursor.getString(cursor.getColumnIndex("long_phone")));
			f.setPy(cursor.getString(cursor.getColumnIndex("py")));
			f.setSex(cursor.getString(cursor.getColumnIndex("sex")));
			f.setQQ(cursor.getString(cursor.getColumnIndex("qq")));
			f.setRealName(cursor.getString(cursor.getColumnIndex("name")));
			f.setShortPhoneNumber(cursor.getString(cursor.getColumnIndex("short_phone")));
			f.setStudentID(cursor.getString(cursor.getColumnIndex("student_id")));
			
			friends.add(f);
		}
		cursor.close();
		db.close();
		return friends;
	}
	
	public List<FriendBasicInfo> getAllBasicFriends(){
		SQLiteDatabase  db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from friends order by py ASC", null);
		List<FriendBasicInfo> friends = new ArrayList<FriendBasicInfo>();
		while(cursor.moveToNext()){
			FriendBasicInfo f = new FriendBasicInfo();
			
			f.setId(cursor.getInt(cursor.getColumnIndex("id")));
			f.setDepartment(cursor.getString(cursor.getColumnIndex("department")));
			f.setLongPhoneNumber(cursor.getString(cursor.getColumnIndex("long_phone")));
			f.setRealName(cursor.getString(cursor.getColumnIndex("name")));
			f.setShortPhoneNumber(cursor.getString(cursor.getColumnIndex("short_phone")));
			//Log.i("info:","BasicFriends name "+ f.getRealName());
			friends.add(f);
		}
		cursor.close();
		db.close();
		//Log.i("info:","getAllBasicFriends "+friends.size()	);
		return friends;
	}
	
	public List<UserInfo> findFriends(String name){
		SQLiteDatabase  db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from friends where name like ", new String[]{"%"+name+"%"});
		List<UserInfo> friends = new ArrayList<UserInfo>();
		while(cursor.moveToNext()){
			UserInfo f = new UserInfo();
			
			f.setId(cursor.getInt(cursor.getColumnIndex("id")));
			f.setAcademy(cursor.getString(cursor.getColumnIndex("academy")));
			f.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
			f.setCampus(cursor.getString(cursor.getColumnIndex("campus")));
			f.setCourse(cursor.getString(cursor.getColumnIndex("course")) );
			f.setDepartment(cursor.getString(cursor.getColumnIndex("department")));
			f.setEmail(cursor.getString(cursor.getColumnIndex("email")));
			f.setIntroduction(cursor.getString(cursor.getColumnIndex("introduction")));
			f.setJhID(cursor.getString(cursor.getColumnIndex("jh_id")));
			f.setLongPhoneNumber(cursor.getString(cursor.getColumnIndex("long_phone")));
			f.setPy(cursor.getString(cursor.getColumnIndex("py")));
			f.setSex(cursor.getString(cursor.getColumnIndex("sex")));
			f.setQQ(cursor.getString(cursor.getColumnIndex("qq")));
			f.setRealName(cursor.getString(cursor.getColumnIndex("name")));
			f.setShortPhoneNumber(cursor.getString(cursor.getColumnIndex("short_phone")));
			f.setStudentID(cursor.getString(cursor.getColumnIndex("student_id")));
			
			friends.add(f);
		}
		cursor.close();
		db.close();
		return friends;
	}
	
	public UserInfo findFriendById(Integer  id){
		SQLiteDatabase  db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from friends where id=?", new String[]{id.toString()});
		UserInfo f = new UserInfo();
		if(cursor.moveToFirst()){
			
			f.setId(cursor.getInt(cursor.getColumnIndex("id")));
			f.setAcademy(cursor.getString(cursor.getColumnIndex("academy")));
			f.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
			f.setCampus(cursor.getString(cursor.getColumnIndex("campus")));
			f.setCourse(cursor.getString(cursor.getColumnIndex("course")) );
			f.setDepartment(cursor.getString(cursor.getColumnIndex("department")));
			f.setEmail(cursor.getString(cursor.getColumnIndex("email")));
			f.setIntroduction(cursor.getString(cursor.getColumnIndex("introduction")));
			f.setJhID(cursor.getString(cursor.getColumnIndex("jh_id")));
			f.setLongPhoneNumber(cursor.getString(cursor.getColumnIndex("long_phone")));
			f.setPy(cursor.getString(cursor.getColumnIndex("py")));
			f.setSex(cursor.getString(cursor.getColumnIndex("sex")));
			f.setQQ(cursor.getString(cursor.getColumnIndex("qq")));
			f.setRealName(cursor.getString(cursor.getColumnIndex("name")));
			f.setShortPhoneNumber(cursor.getString(cursor.getColumnIndex("short_phone")));
			f.setStudentID(cursor.getString(cursor.getColumnIndex("student_id")));
			
		}
		cursor.close();
		db.close();
		return f;
	}
	
	public UserInfo getFriend(){
		return null;
	}
	
	public void add(List<UserInfo> friends){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		//Log.i("info:","add "+friends.size()	);
		StringBuffer sb = new StringBuffer();
		try{
			for(UserInfo f: friends){
				sb.append(f.getShortPhoneNumber()+",");
				db.execSQL("insert into friends(student_id,name,department,email,short_phone,long_phone,academy,campus," +
						"jh_id,sex,birthday,qq,introduction,course,py) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
						new Object[]{f.getStudentID(),f.getRealName(),f.getDepartment(),f.getEmail(),f.getShortPhoneNumber(),
						f.getLongPhoneNumber(),f.getAcademy(),f.getCampus(),f.getJhID(),f.getSex(),f.getBirthday(),f.getQQ(),
						f.getIntroduction(),f.getCourse(),f.getPy()} );
			}
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
		//Log.i("info", sb.toString());
		db.close();
	}
	
	public void save(UserInfo f){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		try{
			
			db.execSQL("insert into friends(student_id,name,department,email,short_phone,long_phone,academy,campus," +
					"jh_id,sex,birthday,qq,introduction,course,py) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					new Object[]{f.getStudentID(),f.getRealName(),f.getDepartment(),f.getEmail(),f.getShortPhoneNumber(),
					f.getLongPhoneNumber(),f.getAcademy(),f.getCampus(),f.getJhID(),f.getSex(),f.getBirthday(),f.getQQ(),
					f.getIntroduction(),f.getCourse(),f.getPy()} );
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
		db.close();
	}
	
	public void update(UserInfo f, int id){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		try{
			
			db.execSQL("update friends set student_id=?,name=?,department=?,email=?,short_phone=?," +
					"long_phone=?,academy=?,campus=?,jh_id=?,sex=?,birthday=?,qq=?,introduction=? ,course=?, py=? where id=?",
					new Object[]{f.getStudentID(),f.getRealName(),f.getDepartment(),f.getEmail(),f.getShortPhoneNumber(),
					f.getLongPhoneNumber(),f.getAcademy(),f.getCampus(),f.getJhID(),f.getSex(),f.getBirthday(),f.getQQ(),
					f.getIntroduction(), f.getCourse(), f.getPy(), id} );
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
		db.close();
	}
	
	public void delete(int id){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("delete from friends where id=?", new Object[]{id});
		db.close();
	}
	
	public void deleteAll(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("friends", null, null);
		//Log.i("info:","deleteAll ");
		Cursor cursor = db.rawQuery("select * from friends order by py ASC", null);
		
		//Log.i("info:","deleteAll cursor count:"+cursor.getCount());
		db.close();
	}
	
}
