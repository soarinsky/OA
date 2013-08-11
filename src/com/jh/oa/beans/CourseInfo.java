package com.jh.oa.beans;

import com.jh.oa.utils.StringUtils;

public class CourseInfo {

	//一周天数
	public static final int DAYS_EVERYWEEK = 7;
	//一天课的节数
	public static final int LESSONS_EVERYDAY = 11;
	
	//课程字符串 
	private String courseStr;   
	//一周空课表数组
	boolean courses[][] = new boolean[LESSONS_EVERYDAY][DAYS_EVERYWEEK];
	
	public CourseInfo(String courseinfo){
		courseStr = courseinfo;
		setCourses(courseinfo);
	}
	
	public void setCourses(boolean[][] courses) {
		this.courses = courses;
	}

	public void setCourses(String courseinfo) {
		if(StringUtils.isEmpty(courseinfo)){
			return;
		}
		
		char[] c = courseinfo.toCharArray();
		for(int i=0; i<LESSONS_EVERYDAY; i++){
			for(int j=0; j<DAYS_EVERYWEEK; j++){
				int index = i*DAYS_EVERYWEEK+j;
				if(c[index] == '0'){
					courses[i][j] = false;
				}
				else{
					courses[i][j] = true;
				}
			}
		}
	}
	
	public boolean[][] getCourses() {
		return courses;
	}

	public boolean getCourse(int day, int lesson){
		return courses[lesson][day];
	}

	@Override
	public String toString() {
		return courseStr;
	}
	
	
}
