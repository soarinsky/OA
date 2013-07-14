package com.jh.oa.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jh.oa.R;

public class AboutUsFragment extends BaseFragment{

	ImageButton imgbtn_left;
	TextView txv_title;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.acty_about_us, null);
		initView(v);
		return v;
	}
	
	public void initView(View v){
		imgbtn_left = (ImageButton) v.findViewById(R.id.title_imgbtn_left);
		txv_title = (TextView) v.findViewById(R.id.title_txv_title_main);
		txv_title.setText(R.string.menu_about_us);
		
		imgbtn_left.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {

		switch(v.getId()){
		
		case R.id.title_imgbtn_left:
			toggle();break;
		}
	}
}
