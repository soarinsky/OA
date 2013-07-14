package com.jh.oa.fragments;


import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jh.oa.R;
import com.jh.oa.adapter.ListViewFriendsAdapter;
import com.jh.oa.beans.UserInfo;
import com.jh.oa.db.FriendsDAO;
import com.jh.oa.db.SharedPreferenceHelper;
import com.jh.oa.services.LoginService;
import com.jh.oa.ui.FriendDetailActivity;
import com.jh.oa.ui.LoginActivity;
import com.jh.oa.utils.NetworkUtils;
import com.jh.oa.utils.StringUtils;
import com.jh.oa.widget.MySideBar;
import com.jh.oa.widget.MySideBar.OnTouchingLetterChangedListener;

public class FriendsFragment extends BaseFragment implements OnTouchingLetterChangedListener{

	private ImageButton imgbtn_left;
	private ImageButton imgbtn_right;
	private TextView txv_title;
	
	private ProgressDialog progressDialog; 
	
	// 显示点击的字母
	private TextView txv_overlay;
	
	private EditText edt_search;
	private ListView lsv_friend;
	
	private List<UserInfo> users;
	private List<UserInfo> results; 
	// listView右边的导航选项卡
	private MySideBar sideBar;
	private ListViewFriendsAdapter adapter;
	private OverlayThread overlayThread = new OverlayThread();
	
	private SharedPreferenceHelper sharedPreferenceHelper;
	private FriendsDAO friendDao;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		sharedPreferenceHelper = SharedPreferenceHelper.getSharedPreferenceHelper(getActivity());
		friendDao = FriendsDAO.getFriendsDAOInstance(getActivity());
		View v = inflater.inflate(R.layout.acty_friends, null);
		initViews(v);
		return v;
	}
	
	public void initViews(View v){
		
		imgbtn_left = (ImageButton) v.findViewById(R.id.title_imgbtn_left);
		imgbtn_right = (ImageButton) v.findViewById(R.id.title_imgbtn_right);
		imgbtn_right.setVisibility(View.VISIBLE);
		txv_title = (TextView) v.findViewById(R.id.title_txv_title_main);
		txv_title.setText(R.string.menu_friend_list);
		
		edt_search =  (EditText) v.findViewById(R.id.rlyt_search_context);
		lsv_friend =  (ListView) v.findViewById(R.id.friend_lstv);
		
		txv_overlay = (TextView) v.findViewById(R.id.friend_txv_letter);
		sideBar = (MySideBar) v.findViewById(R.id.friend_sideBar);
		
		initFriendsData();
//		Collections.sort(users, new PinyinComparator());
//		Log.i("info", users.size() + users.get(0).getRealName());
		adapter = new ListViewFriendsAdapter(getActivity(), users);

		lsv_friend.setAdapter(adapter);
		edt_search.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	results = null;
	        	String search  = s.toString();
//	        	Log.i("search", search);
	        	if(StringUtils.isEmpty(search)){
	        		lsv_friend.setAdapter(adapter);
	        		setListViewListener(users);
	        		sideBar.setVisibility(View.VISIBLE);
	        		return;
	        	}
	        	results = new ArrayList<UserInfo>();
	        	for(UserInfo user: users){
	        		if(user.getRealName().indexOf(search)>=0){
	        			Log.i("search", user.getRealName());
	        			results.add(user);
	        		}	
	        	}
//	        	Log.i("search", ""+result.size());
	        	lsv_friend.setAdapter(new ListViewFriendsAdapter(getActivity(),results,false));
	        	setListViewListener(results);
	        	sideBar.setVisibility(View.GONE);
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
		}); 
		
		imgbtn_left.setOnClickListener(this);
		imgbtn_right.setOnClickListener(this);
		sideBar.setOnTouchingLetterChangedListener(this);
		setListViewListener(users);
	}
	
	private void setListViewListener(final List<UserInfo> datas){
		lsv_friend.setOnItemClickListener((new OnItemClickListener() {  
            public void onItemClick(AdapterView<?> parent, View view,  
                    int position, long id) {  
                 
            	Intent i = new Intent(parent.getContext(), FriendDetailActivity.class);
            	i.putExtra("userId", datas.get(position).getId());
            	startActivity(i);
            } 
        }));
	}
	
	public void initFriendsData(){
		if(sharedPreferenceHelper.isFriendsExist()){
			users = friendDao.getAllFriends();
		}
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.title_imgbtn_left:
				toggle();break;
		case R.id.title_imgbtn_right:
			if(!NetworkUtils.isNetWorkValiable(getActivity())){
				Toast.makeText(getActivity(), "网络未连接！请连接后再试！", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent loginService = new Intent(getActivity(), LoginService.class);
			loginService.putExtra("username", sharedPreferenceHelper.getUsername());
			loginService.putExtra("password", sharedPreferenceHelper.getPassword());
			loginService.putExtra("Messenger", new Messenger(loginHandler));
			getActivity().startService(loginService);
			progressDialog = ProgressDialog.show(getActivity(), "更新中...", "请稍等...", false, false); 
			break;
		}
	}

	private Handler loginHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			if(msg.arg1 == 1){
				refreshListView();
				Toast.makeText(getActivity(), "更新成功！", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getActivity(), "更新失败，请稍后再试！", Toast.LENGTH_SHORT).show();
			}
			progressDialog.dismiss();
		}
		
	};
	
	private void refreshListView(){
		initFriendsData();
		adapter = new ListViewFriendsAdapter(getActivity(), users);
		lsv_friend.setAdapter(adapter);
		setListViewListener(users);
	}
	
	@Override
	public void onTouchingLetterChanged(String s) {
		
		txv_overlay.setText(s);
		txv_overlay.setVisibility(View.VISIBLE);
		handler.removeCallbacks(overlayThread);
		handler.postDelayed(overlayThread, 500);
		if (alphaIndexer(s) > 0) {
			int position = alphaIndexer(s);
			lsv_friend.setSelection(position);
		}
		
	}
	
	
	private Handler handler = new Handler() {
	};
	
	public int alphaIndexer(String s) {
		int position = 0;
		for (int i = 0; i < users.size(); i++) {

			if (users.get(i).getPy().startsWith(s)) {
				position = i;
				break;
			}
		}
		return position;
	}
	
	private class OverlayThread implements Runnable {

		public void run() {
			txv_overlay.setVisibility(View.GONE);
		}

	}
	
}
