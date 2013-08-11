package com.jh.oa.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.jh.oa.beans.Organization;
import com.jh.oa.beans.UserInfo;
import com.jh.oa.beans.UserSelfInfo;
import com.jh.oa.db.FriendsDAO;
import com.jh.oa.db.SharedPreferenceHelper;
import com.jh.oa.utils.HttpDownloader;
import com.jh.oa.utils.JsonUtils;
import com.jh.oa.utils.StringUtils;

public class LoginService extends IntentService{

	public static int TYPE = 0;        //返回数据格式 0:json格式  1:xml格式
	public static int PAGE_NO = 1;
	public static int DEF_PAGE_SIZE = 50;
	
	public static String FRIENDS_URL = "http://oa2.zjut.com/global/open_getContacts";
	public static String USER_LOGIN_URL = "http://oa2.zjut.com/global/open_CheckUser";
	
	private SharedPreferenceHelper preferenceHelper;
	private FriendsDAO friendDao;
	
	
	public LoginService() {
		super("LoginService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		preferenceHelper = SharedPreferenceHelper.getSharedPreferenceHelper(this);
		friendDao = FriendsDAO.getFriendsDAOInstance(this);
		String username = intent.getStringExtra("username");
		String password = intent.getStringExtra("password");
		boolean isKeepPassword = intent.getBooleanExtra("isKeepPassword",true);
		
		int flag  = 0;
		UserSelfInfo user = null;
		
		try {
			InputStream inputStream = new HttpDownloader().getInputStreamFromUrl(USER_LOGIN_URL, username, password, TYPE);
			user = new JsonUtils().parseUser(inputStream);
			
			//登录成功后，下载好友数据并保存
			if(user != null){
				
				preferenceHelper.saveUserSelf(user);
				if(isKeepPassword){
					preferenceHelper.setLogined(true);
				}else{
					preferenceHelper.setLogined(false);
				}
				preferenceHelper.saveLoginInfo(username, password);
				preferenceHelper.setFriendsExist(true);
					
				friendDao.deleteAll();
				
				int pageSize = 1, records = 0, totalPage = 1;
				
				String codes[] = preferenceHelper.getDepCode().split(":");
				for(String code: codes){
					records = getTotalRecords(code);
					pageSize = computePageSize(records);
					totalPage = records / pageSize;
					
					//Log.i("info","pageSize="+pageSize+" totalPage="+totalPage+" records="+records);
					for(int pageNo = 1; pageNo <= totalPage; pageNo++){
						saveFriends(getPageFriends(pageNo, pageSize, code));
					}
					
				}
				
			}
			flag = 1;
			
		} catch (Exception e) {
			Log.e(getClass().getName(),"Exception Message: " + e.toString());
		}
	
		

		Bundle extras = intent.getExtras(); 
        if(extras != null){ 
            Messenger mesenger = (Messenger)extras.get("Messenger"); 
           //使用Message.obtain()获得一个空的Message对象
            Message msg = Message.obtain( );  
           //填充message的信息。  
            msg.arg1 = flag; 
          //通过Messenger信使将消息发送出去。  
            try{ 
                mesenger.send(msg);  
            }catch(Exception e){ 
                Log.w(getClass().getName(),"Exception Message: " + e.toString());
            }
        }
	}
	
	private void saveUserSlef(UserSelfInfo user){
		
	}
	
	private int getTotalRecords(String code) throws MalformedURLException, IOException {
		InputStream inputStream2 = new HttpDownloader().getInputStreamFromUrl(FRIENDS_URL, 1, 1, code);
		JsonUtils ju = new JsonUtils();
		ju.parseFriends(inputStream2);
		return ju.getPage().getRecords();
	}
	
	private int computePageSize(int totalRecords){
		int totalPage = totalRecords / DEF_PAGE_SIZE;
		if(totalPage > 5){
			return (totalRecords + 4) / 5;    //totalPage最大为5，大于5按5页计算
		}
		return DEF_PAGE_SIZE;
	}
	
	private List<UserInfo> getPageFriends(int pageNo, int pageSize, String code) throws MalformedURLException, IOException {
		InputStream inputStream2 = new HttpDownloader().getInputStreamFromUrl(FRIENDS_URL, pageNo, pageSize, code);
		return new JsonUtils().parseFriends(inputStream2);
	}
	
	private void saveFriends(List<UserInfo> friends){
		friendDao.add(friends);
	}
}
