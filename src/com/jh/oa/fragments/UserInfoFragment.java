package com.jh.oa.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jh.oa.R;
import com.jh.oa.beans.UserInfo;
import com.jh.oa.db.SharedPreferenceHelper;
import com.jh.oa.ui.CourseActivity;
import com.jh.oa.utils.StringUtils;

public class UserInfoFragment extends BaseFragment{

	private ImageButton imgbtn_left;
	private ImageButton imgbtn_course;
	private TextView txv_title;
	private TextView tvx_name;
	private TextView tvx_id;
	private TextView txv_stuendtID;
	private TextView txv_academy;
	private TextView txv_phone;
	private TextView txv_qq;
	private TextView txv_email;
	private TextView txv_place;
	private TextView txv_birthday;
	private TextView txv_department;
	private TextView txv_introduction;
	
	private UserInfo user;
	private SharedPreferenceHelper preferenceHelper = SharedPreferenceHelper.getSharedPreferenceHelper(getActivity());;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.acty_user_data, null);
		initViews(v);
		setUserInfo();
		return v;
	}
	
	public void initViews(View v){
		//title bar widgets
		imgbtn_left = (ImageButton) v.findViewById(R.id.title_imgbtn_left);
		txv_title = (TextView) v.findViewById(R.id.title_txv_title_main);
		txv_title.setText(R.string.menu_userInfo);
		
		//user detial info widgets
		tvx_name = (TextView) v.findViewById(R.id.user_data_name);
		tvx_id = (TextView) v.findViewById(R.id.user_data_id);
		txv_stuendtID = (TextView) v.findViewById(R.id.user_data_txv_studentID);
		txv_academy = (TextView) v.findViewById(R.id.user_data_txv_academy);
		txv_phone = (TextView) v.findViewById(R.id.user_data_txv_phone);
		txv_qq = (TextView) v.findViewById(R.id.user_data_txv_qq);
		txv_email = (TextView) v.findViewById(R.id.user_data_txv_email);
		txv_place = (TextView) v.findViewById(R.id.user_data_txv_place);
		txv_birthday = (TextView) v.findViewById(R.id.user_data_txv_birthday);
		txv_department = (TextView) v.findViewById(R.id.user_data_txv_department);
		txv_introduction = (TextView) v.findViewById(R.id.user_data_txv_introduction);
		imgbtn_course = (ImageButton) v.findViewById(R.id.user_data_imgbtn_class_schedule);
		
			
		imgbtn_course.setOnClickListener(this);
		imgbtn_left.setOnClickListener(this);
	}
	
	public void setUserInfo(){
		user = preferenceHelper.getUser();
		tvx_name.setText(user.getRealName());
		tvx_id.setText(user.getJhID());
		txv_academy.setText(user.getAcademy());
		txv_stuendtID.setText(user.getStudentID());
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
		
		case R.id.title_imgbtn_left:
			toggle();
			break;
		case R.id.user_data_imgbtn_class_schedule:
			Intent i = new Intent(getActivity(), CourseActivity.class);
			i.putExtra("courseInfo", user.getCourse());
			i.putExtra("name", "Œ“");
			startActivity(i);
			break;
		}
	}
	
}
