package com.jh.oa.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.jh.oa.R;
import com.jh.oa.adapter.ListViewCourseAdapter;

public class CourseActivity extends Activity{

	private ListView ls;
	private ImageButton imgbtn_left;
	private TextView txv_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acty_course);
		initViews();
	}
	
	public void initViews(){
		ls = (ListView) findViewById(R.id.course_lstv);
		imgbtn_left = (ImageButton) findViewById(R.id.title_imgbtn_back);
		txv_title = (TextView) findViewById(R.id.title_txv_title);
		String courseinfo = getIntent().getStringExtra("courseInfo");
		String name = getIntent().getStringExtra("name");
		txv_title.setText(name+"µÄ¿Î±í");
		
		ls.setAdapter(new ListViewCourseAdapter(this, courseinfo));
		imgbtn_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	
}
