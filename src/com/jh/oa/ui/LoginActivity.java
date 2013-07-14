package com.jh.oa.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.jh.oa.R;
import com.jh.oa.db.SharedPreferenceHelper;
import com.jh.oa.services.LoginService;
import com.jh.oa.utils.NetworkUtils;
import com.jh.oa.utils.StringUtils;

public class LoginActivity extends Activity {

	private EditText edt_username;
	private EditText edt_password;
	private Button btn_login;
	private ProgressDialog progressDialog;
	private CheckBox ckb_remermberPassword;
	
	private Intent loginService;
	private SharedPreferenceHelper preferenceHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		preferenceHelper = SharedPreferenceHelper.getSharedPreferenceHelper(this);
		if(preferenceHelper.isLogined()){
			redirectTo();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acty_login);
		initView();
	}
	
	public void initView(){
		edt_username = (EditText) findViewById(R.id.user_login_edt_account);
		edt_password = (EditText) findViewById(R.id.user_login_edt_password);
		btn_login = (Button) findViewById(R.id.user_btn_login);
		ckb_remermberPassword =  (CheckBox) findViewById(R.id.user_login_ckb_rememberPassword);
		
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = edt_username.getText().toString();
				String password = edt_password.getText().toString();
				boolean isKeepPassword = true;
				
				if(!NetworkUtils.isNetWorkValiable(LoginActivity.this)){
					Toast.makeText(LoginActivity.this, "网络未连接！请连接后再试！", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(!ckb_remermberPassword.isChecked()){
					isKeepPassword = false;
				}
				
				if(StringUtils.isEmpty(username)){
					Toast.makeText(LoginActivity.this, "Account is null!", Toast.LENGTH_SHORT).show();
				}else if(StringUtils.isEmpty(password)){
					Toast.makeText(LoginActivity.this, "Password is null!", Toast.LENGTH_SHORT).show();
				}else{
					//login 
					loginService = new Intent(LoginActivity.this, LoginService.class);
					loginService.putExtra("username", username);
					loginService.putExtra("password", password);
					loginService.putExtra("isKeepPassword", isKeepPassword);
					loginService.putExtra("Messenger", new Messenger(loginHandler));
					startService(loginService);
					progressDialog = ProgressDialog.show(LoginActivity.this, "Loging...", "Please wait...", false, false); 
				}
			}
		});
	}
	
	private Handler loginHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			// 返回 1表示登录成功  0表示登录失败
			if(msg.arg1 == 1){
				redirectTo();
			}else{
				Toast.makeText(LoginActivity.this, "登录失败！账号或密码有误！", Toast.LENGTH_SHORT).show();
			}
			progressDialog.dismiss();
		}
		
	};
	
	private void redirectTo(){  
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}