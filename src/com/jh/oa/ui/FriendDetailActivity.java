package com.jh.oa.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jh.oa.R;
import com.jh.oa.beans.UserInfo;
import com.jh.oa.db.FriendsDAO;
import com.jh.oa.utils.StringUtils;
import com.jh.oa.utils.SysIntentUtils;

public class FriendDetailActivity extends Activity implements OnClickListener{

	ImageButton imgbtn_left;
	ImageButton imgbtn_right;
	ImageButton imgbtn_course;
	TextView txv_title;
	TextView tvx_name;
	TextView tvx_id;
	TextView txv_stuendtID;
	TextView txv_academy;
	TextView txv_phone;
	TextView txv_qq;
	TextView txv_email;
	TextView txv_place;
	TextView txv_birthday;
	TextView txv_department;
	TextView txv_introduction;
	
	
	UserInfo user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acty_friend_data);
		
		int userid = getIntent().getIntExtra("userId", 0);
		user = FriendsDAO.getFriendsDAOInstance(this).findFriendById(userid);
//		Log.i("info", user.getAcademy());
		initViews();
		setFriendData();
	}
	
	public void initViews(){
		imgbtn_left = (ImageButton) findViewById(R.id.title_imgbtn_back);
		imgbtn_right = (ImageButton) findViewById(R.id.title_imgbtn_right);
		txv_title = (TextView) findViewById(R.id.title_txv_title);
		txv_title.setText(R.string.friend_info);
		imgbtn_right.setVisibility(View.VISIBLE);
		
		//user detail info widgets
		tvx_name = (TextView) findViewById(R.id.user_data_name);
		tvx_id = (TextView) findViewById(R.id.user_data_id);
		txv_stuendtID = (TextView) findViewById(R.id.user_data_txv_studentID);
		txv_academy = (TextView) findViewById(R.id.user_data_txv_academy);
		txv_phone = (TextView) findViewById(R.id.user_data_txv_phone);
		txv_qq = (TextView) findViewById(R.id.user_data_txv_qq);
		txv_email = (TextView) findViewById(R.id.user_data_txv_email);
		txv_place = (TextView) findViewById(R.id.user_data_txv_place);
		txv_birthday = (TextView) findViewById(R.id.user_data_txv_birthday);
		txv_department = (TextView) findViewById(R.id.user_data_txv_department);
		txv_introduction = (TextView) findViewById(R.id.user_data_txv_introduction);
		imgbtn_course = (ImageButton) findViewById(R.id.user_data_imgbtn_class_schedule);
		imgbtn_course.setVisibility(View.GONE);
		
		imgbtn_course.setOnClickListener(this);
		imgbtn_left.setOnClickListener(this);
		imgbtn_right.setOnClickListener(this);
	}
	
	public void setFriendData(){
		tvx_name.setText(user.getRealName());
		tvx_id.setText(user.getJhID());
		txv_stuendtID.setText(user.getStudentID());
		txv_academy.setText(user.getAcademy());
		txv_birthday.setText(user.getBirthday());
		txv_department.setText(user.getDepartment());
		txv_email.setText(user.getEmail());
		txv_place.setText(user.getCampus());
		txv_qq.setText(user.getQQ());
		txv_introduction.setText(user.getIntroduction());
		if(!StringUtils.isEmpty(user.getLongPhoneNumber()) && !StringUtils.isEmpty(user.getShortPhoneNumber())){
			txv_phone.setText(user.getLongPhoneNumber()+" ("+user.getShortPhoneNumber()+")");
		}else if(!StringUtils.isEmpty(user.getLongPhoneNumber())){
			txv_phone.setText(user.getLongPhoneNumber());
		}else{
			txv_phone.setText(user.getShortPhoneNumber());
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.title_imgbtn_back:
			finish();
			break;
		case R.id.title_imgbtn_right:
			SysIntentUtils.startInsertContact(this, user.getRealName(), 
					user.getShortPhoneNumber(), user.getLongPhoneNumber(), user.getEmail());
			break;
		case R.id.user_data_imgbtn_class_schedule:
			Intent i = new Intent(FriendDetailActivity.this, CourseActivity.class);
			i.putExtra("name", user.getRealName());
			i.putExtra("courseInfo", user.getCourse());
			startActivity(i);
			break;
		}
		
	}
}
