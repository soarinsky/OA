package com.jh.oa.fragments;


import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

import com.jh.oa.ui.MainActivity;

public class BaseFragment extends Fragment implements OnClickListener{

	@Override
	public void onClick(View v) {
	}

	protected void toggle(){

		if(isAttachMainActivity()){
			MainActivity ma = (MainActivity) getActivity();
			ma.getSlidingMenu().toggle();
		}
	} 
	
	// the meat of switching the above fragment
	protected void switchFragment(Fragment fragment) {
		
		if(isAttachMainActivity()){
			MainActivity ma = (MainActivity) getActivity();
			ma.switchContent(fragment);
		}
		
	}
	
	//return is this fragement attach to MainActivity
	private boolean isAttachMainActivity(){
		
		if(getActivity() == null)
			return false;
		if(getActivity() instanceof MainActivity){
			return true;
		}
		
		return false;
	}
}
