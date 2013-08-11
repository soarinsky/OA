package com.jh.oa.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jh.oa.R;
import com.jh.oa.db.FriendsDAO;
import com.jh.oa.db.SharedPreferenceHelper;
import com.jh.oa.ui.LoginActivity;

public class MenuFragment extends BaseFragment{

	Button btn_userInfo;
	Button btn_friends;
	Button btn_find_friend;
	Button btn_logout;
	Button btn_about_us;
	Button btn_exit;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.acty_menu, null);
		initViews(v);
		return v;
	}

	public void initViews(View v){
		btn_userInfo = (Button) v.findViewById(R.id.menu_btn_userInfo);
		btn_friends = (Button) v.findViewById(R.id.menu_btn_friend_list);
		btn_exit = (Button) v.findViewById(R.id.menu_btn_exit);
		btn_logout = (Button) v.findViewById(R.id.menu_btn_logout);
		btn_about_us = (Button) v.findViewById(R.id.menu_btn_about_us);
		
		btn_userInfo.setOnClickListener(this);
		btn_friends.setOnClickListener(this);
		btn_exit.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		btn_about_us.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		
		Fragment f = null;
		switch(v.getId()){
		
		case R.id.menu_btn_userInfo:
			f = new UserInfoFragment();
			switchFragment(f);
			break;
		case R.id.menu_btn_friend_list:
			f = new FriendsFragment();
			switchFragment(f);
			break;
		case R.id.menu_btn_about_us:
			f = new AboutUsFragment();
			switchFragment(f);
			break;
		case R.id.menu_btn_logout:
			SharedPreferenceHelper preferenceHelper = SharedPreferenceHelper.getSharedPreferenceHelper(getActivity());
			FriendsDAO friendDao = FriendsDAO.getFriendsDAOInstance(getActivity());
			
			preferenceHelper.setLogined(false);
			preferenceHelper.setFriendsExist(false);
			preferenceHelper.setUserExist(false);
			friendDao.deleteAll();
			finish();
			Intent i = new Intent(getActivity(),LoginActivity.class);
			getActivity().startActivity(i);
			break;
		case R.id.menu_btn_exit:
			finish(); 
			break;
			
		}
		
	}

	private void finish(){
		getActivity().finish();
	}
	
}
