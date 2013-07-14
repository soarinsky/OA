package com.jh.oa.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

public class SysIntentUtils {

	public static void call(Context context, String shortNumber,String longNumber){
		Intent intent; 
		if(!StringUtils.isEmpty(shortNumber)){
			intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+shortNumber));
		}else if(!StringUtils.isEmpty(longNumber)){
			intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+longNumber));  
		}else{
			return;
		}
        context.startActivity(intent);  
	}
	
	//insert contact directly
	public static void insertContact(Context context, String name, String phone, String email) {
			        
        ContentValues values = new ContentValues();
        //首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
        Uri rawContactUri = context.getContentResolver().insert(RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        
        //往data表入姓名数据
        values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        values.put(StructuredName.GIVEN_NAME, name);
        context.getContentResolver().insert(
                android.provider.ContactsContract.Data.CONTENT_URI, values);
        
        //往data表入电话数据
        values.clear();
        values.put(android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        values.put(Phone.NUMBER, "5554");
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        context.getContentResolver().insert(
                android.provider.ContactsContract.Data.CONTENT_URI, values);

        //往data表入Email数据
        if(StringUtils.isEmail(email)){
        	values.clear();
            values.put(android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
            values.put(Email.DATA, email);
            values.put(Email.TYPE, Email.TYPE_WORK);
            context.getContentResolver().insert(
                    android.provider.ContactsContract.Data.CONTENT_URI, values);
        }
        
	}
	
	public static void startInsertContact(Context context, String name, String shortPhone, String longPhone, String email) {
         Intent intent = new Intent(Intent.ACTION_INSERT,Uri.withAppendedPath(Uri.parse("content://com.android.contacts"), "contacts"));  
         if(!StringUtils.isEmpty(shortPhone))
        	 intent.putExtra(ContactsContract.Intents.Insert.PHONE, shortPhone);  
         if(!StringUtils.isEmpty(longPhone))
        	 intent.putExtra(ContactsContract.Intents.Insert.PHONE_ISPRIMARY, longPhone);
         intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
         if(StringUtils.isEmail(email))
        	 intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email); 
         context.startActivity(intent); 
	}
}
