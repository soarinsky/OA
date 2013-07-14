package com.jh.oa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jh.oa.R;
import com.jh.oa.beans.CourseInfo;

public class ListViewCourseAdapter extends BaseAdapter{

	private Context 					context;//运行上下文
	private CourseInfo 		            course;//数据集合
	private LayoutInflater 				listContainer;//视图容器
	private int 						itemViewResource;//自定义项视图源

	static class ListItemView{				//自定义控件集合  
			public ImageView monday;  
	        public ImageView tuesday;  
		    public ImageView wendnesday;
		    public ImageView thirday;
		    public ImageView friday;
		    public ImageView saturday;
		    public ImageView sunday;
		    public TextView classNum;
	 }  
	
	public ListViewCourseAdapter(Context context, String courses){
		this(context, courses, R.layout.lstv_item_course);
	}

	public ListViewCourseAdapter(Context context, String courses, int resource){
		this.context = context;
		this.itemViewResource = resource;
		this.listContainer = LayoutInflater.from(context);
		this.course = new CourseInfo(courses);
	}
	
	@Override
	public int getCount() {
		return CourseInfo.LESSONS_EVERYDAY;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ListItemView listItemView = null;
		
		if(convertView == null){
			//获取list_item布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);
			
			listItemView = new ListItemView();
			
			listItemView.monday = (ImageView) convertView.findViewById(R.id.course_item_imgv_monday);
			listItemView.tuesday = (ImageView) convertView.findViewById(R.id.course_item_imgv_tuesday);
			listItemView.wendnesday = (ImageView) convertView.findViewById(R.id.course_item_imgv_wednesday);
			listItemView.thirday = (ImageView) convertView.findViewById(R.id.course_item_imgv_thirday);
			listItemView.friday = (ImageView) convertView.findViewById(R.id.course_item_imgv_friday);
			listItemView.saturday = (ImageView) convertView.findViewById(R.id.course_item_imgv_saturday);
			listItemView.sunday = (ImageView) convertView.findViewById(R.id.course_item_imgv_sunday);
			listItemView.classNum = (TextView) convertView.findViewById(R.id.course_item_txv_class);
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else{
			listItemView = (ListItemView)convertView.getTag();
		}
		
		listItemView.classNum.setText(position+1+"");
		if(position == 4){
			setClassEmpty(listItemView.monday);
			setClassEmpty(listItemView.tuesday);
			setClassEmpty(listItemView.wendnesday);
			setClassEmpty(listItemView.thirday);
			setClassEmpty(listItemView.friday);
			setClassEmpty(listItemView.saturday);
			setClassEmpty(listItemView.sunday);
		}else{
			if(course.getCourse(0, position))
				setClassFull(listItemView.monday);
			else
				setClassEmpty(listItemView.monday);
			if(course.getCourse(1, position))
				setClassFull(listItemView.tuesday);
			else
				setClassEmpty(listItemView.tuesday);
			if(course.getCourse(2, position))
				setClassFull(listItemView.wendnesday);
			else
				setClassEmpty(listItemView.wendnesday);
			if(course.getCourse(3, position))
				setClassFull(listItemView.thirday);
			else
				setClassEmpty(listItemView.thirday);
			if(course.getCourse(4, position))
				setClassFull(listItemView.friday);
			else
				setClassEmpty(listItemView.friday);
			if(course.getCourse(5, position))
				setClassFull(listItemView.saturday);
			else
				setClassEmpty(listItemView.saturday);
			if(course.getCourse(6, position))
				setClassFull(listItemView.sunday);
			else
				setClassEmpty(listItemView.sunday);
		}
		return convertView;
	}
	
	private void setClassFull(ImageView day){
		day.setImageResource(R.drawable.ic_btn_checkbox_pressed);
	}

	private void setClassEmpty(ImageView day){
		day.setImageResource(R.drawable.ic_btn_checkbox_normal);
	}
}
