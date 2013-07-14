package com.jh.oa.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.jh.oa.beans.UserInfo;
import com.jh.oa.db.FriendsDAO;
import com.jh.oa.db.SharedPreferenceHelper;
import com.jh.oa.utils.HttpDownloader;
import com.jh.oa.utils.JsonUtils;

public class LoginService extends IntentService{

	public static String TYPE = "0";        //�������ݸ�ʽ 0��xml��ʽ  1��json��ʽ
	
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
		boolean isKeepPassword = intent.getBooleanExtra("isKeepPassword",false);
		
		int flag  = 0;
		UserInfo user = null;
		List<UserInfo> friends = null;
		
		try {
			InputStream inputStream = new HttpDownloader().getInputStreamFromUrl("http://oa2.zjut.com/global/open_CheckUser", username, password, TYPE);
			user = new JsonUtils().parseUser(inputStream);
			
//			InputStream inputStream2 = new HttpDownloader().getInputStreamFromUrl("http://oa2.zjut.com/jhoa/global/open_getContacts", username, TYPE);
//			friends = new JsonUtils().parseFriends(inputStream2);
			
			friends = new JsonUtils().parseFriends();
//			if(friends!=null)
//				Log.i("info","friends is not null");
		} catch (IOException e) {
			Log.e(getClass().getName(),"Exception Message: " + e.toString());
		}
	
		if(user != null && friends != null){
//			Log.i("info","ok"+friends.size());
			preferenceHelper.saveUser(user);
			if(isKeepPassword){
				preferenceHelper.setLogined(true);
			}else{
				preferenceHelper.setLogined(false);
			}
			preferenceHelper.saveLoginInfo(username, password);
			preferenceHelper.setFriendsExist(true);
			friendDao.deleteAll();
			friendDao.add(friends);
			flag = 1;
		}

		Bundle extras = intent.getExtras(); 
        if(extras != null){ 
            Messenger mesenger = (Messenger)extras.get("Messenger"); 
           //ʹ��Message.obtain()���һ���յ�Message����
            Message msg = Message.obtain( );  
           //���message����Ϣ��  
            msg.arg1 = flag; 
          //ͨ��Messenger��ʹ����Ϣ���ͳ�ȥ��  
            try{ 
                mesenger.send(msg);  
            }catch(Exception e){ 
                Log.w(getClass().getName(),"Exception Message: " + e.toString());
            }
        }
	}
	
}
