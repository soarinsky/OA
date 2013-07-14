package com.jh.oa.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jh.oa.R;
import com.jh.oa.beans.UserInfo;
import com.jh.oa.utils.PinyinUtils;
import com.jh.oa.utils.StringUtils;
import com.jh.oa.utils.SysIntentUtils;

public class ListViewFriendsAdapter extends BaseAdapter{

	private Context 					context;//运行上下文
	private List<UserInfo> 				listItems;//数据集合
	private LayoutInflater 				listContainer;//视图容器
	private int 						itemViewResource;//自定义项视图源
	private boolean                     isCatalogVisible = true;

	static class ListItemView{				//自定义控件集合  
			public ImageView userface;  
	        public TextView name;  
		    public TextView department;
		    public TextView longPhoneNumber;
		    public TextView shortPhoneNumber;
		    public TextView catalog;
	 }  
	
	public ListViewFriendsAdapter(Context context, List<UserInfo> userInfos){
		this(context, userInfos, R.layout.lstv_item_friend);
	}
	
	public ListViewFriendsAdapter(Context context, List<UserInfo> userInfos, boolean catalogVisble){
		this(context, userInfos, R.layout.lstv_item_friend);
		isCatalogVisible = catalogVisble;
	}

	public ListViewFriendsAdapter(Context context, List<UserInfo> userInfos, int resource){
		this.context = context;
		this.itemViewResource = resource;
		this.listContainer = LayoutInflater.from(context);
		this.listItems = userInfos;
	}
	
	@Override
	public int getCount() {
		return listItems.size();
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
			
			listItemView.userface = (ImageView) convertView.findViewById(R.id.friend_item_imgv_avatar);
			listItemView.department = (TextView) convertView.findViewById(R.id.friend_item_txv_department);
			listItemView.longPhoneNumber = (TextView) convertView.findViewById(R.id.friend_item_txv_long_phone);
			listItemView.name = (TextView) convertView.findViewById(R.id.friend_item_txv_name);
			listItemView.shortPhoneNumber = (TextView) convertView.findViewById(R.id.friend_item_txv_short_phone);
			listItemView.catalog = (TextView) convertView.findViewById(R.id.friend_item_txv_catalog);
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else{
			listItemView = (ListItemView)convertView.getTag();
		}
		
		if(isCatalogVisible){
			String catalog = listItems.get(position).getPy().substring(0, 1).toUpperCase();
			
			if (position == 0) {
				listItemView.catalog.setVisibility(View.VISIBLE);
				listItemView.catalog.setText(catalog);
			} else {
				String lastCatalog = listItems.get(position - 1).getPy().substring(0,1).toUpperCase();
				
				if (catalog.equals(lastCatalog)) {
					listItemView.catalog.setVisibility(View.GONE);
				} else {
					listItemView.catalog.setVisibility(View.VISIBLE);
					listItemView.catalog.setText(catalog);
				}
			}
		}else{
			listItemView.catalog.setVisibility(View.GONE);
		}
		
		UserInfo user = listItems.get(position);
		listItemView.department.setText(user.getDepartment());
		listItemView.longPhoneNumber.setText(user.getLongPhoneNumber());
		listItemView.name.setText(user.getRealName());
		listItemView.shortPhoneNumber.setText(user.getShortPhoneNumber());
		listItemView.userface.setImageResource(R.drawable.ic_normal_avatar);
		listItemView.userface.setTag(user);
		listItemView.userface.setOnClickListener(faceClickListener);
		
		return convertView;
	}
	
	private View.OnClickListener faceClickListener = new View.OnClickListener(){
		public void onClick(View v) {
			//call sb
			UserInfo user = (UserInfo) v.getTag();
			Log.e("call", user.getLongPhoneNumber());
			SysIntentUtils.call(context, user.getShortPhoneNumber(), user.getLongPhoneNumber());
		}
	};

}
