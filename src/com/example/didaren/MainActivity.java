package com.example.didaren;

import com.example.didaren.NavDrawerFragment.NavDrawerCallbacks;
import com.example.didaren.modules.HotFragment;
import com.example.didaren.modules.ImportantNewsFragment;
import com.example.didaren.modules.NewsContentFragment;
import com.example.didaren.modules.NoticeFragment;
import com.example.didaren.modules.PreActivityFragment;

import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity 
		implements NavDrawerCallbacks{
	
	private SharedPreferences pref;
	public String userName;
	private long exitTime = 0;
	private DrawerLayout mDrawerLayout;
	private View mFragmentContainerView;
	
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavDrawerFragment mNavDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavDrawerFragment = (NavDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navdrawer);
        mTitle = getTitle();

        // Set up the drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFragmentContainerView = findViewById(R.id.navdrawer);
        mNavDrawerFragment.setUp(
                R.id.navdrawer,
                mDrawerLayout);
        
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
            	mTitle = getString(R.string.title_section5);
            	break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // if (!mNavDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.main, menu);
        	 getMenuInflater().inflate(R.menu.main, menu);
             pref = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
             boolean isRemember = pref.getBoolean("remember_LoginData", false);
             if (isRemember==true) {
 				String userNameString = pref.getString("userName", "");
 				Log.d("MainActivity conCreateOptionMenu",userNameString);
 				MenuItem item = menu.findItem(R.id.action_example);
 				item.setTitle(userNameString);
 			}else{
 				MenuItem item = menu.findItem(R.id.action_example);
 				item.setIcon(R.drawable.ic_action_person);
 			}
             restoreActionBar();
         //    return true;
       // }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
			case R.id.action_example:
				FragmentManager fragmentManager = MainActivity.this.getFragmentManager();
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				transaction.replace(R.id.container, LoginFragment.newInstance(),"Register");
				transaction.commit();
				if (mDrawerLayout != null) {
					mDrawerLayout.closeDrawer(mFragmentContainerView);		
				}
				mTitle = getString(R.string.user_data);
				invalidateOptionsMenu();
			    break;
		} 
    	return super.onOptionsItemSelected(item);
    }

	@Override
	public void onNavDrawerItemSelected(int position) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		switch (position) {
		case 0:
			transaction.replace(R.id.container, ImportantNewsFragment.newInstance(1), "News");
			break;
		case 1:
			transaction.replace(R.id.container, NoticeFragment.newInstance(2), "Notice");
			break;
		case 2:
			transaction.replace(R.id.container, PreActivityFragment.newInstance(3),"PreActivity");
			break;
		case 3:
			transaction.replace(R.id.container, HotFragment.newInstance(4),"Hot");
			break;
		case 4:
			transaction.replace(R.id.container, ScanCodeFragment.newInstance(5), "Register");
			break;
		}
		transaction.commit();
	}  
	
	private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
 	 
 	private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }
 	
 	public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
 	
 	 @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
 		 Fragment fragment = getFragmentManager().findFragmentById(R.id.container);
 		 if(!(fragment instanceof NewsContentFragment)) {
 			 if (keyCode == KeyEvent.KEYCODE_BACK) {
 				 exit();
 				 return false;
 			 }
 		 }
         return super.onKeyDown(keyCode, event);
     }

     public void exit() {
    	if ((System.currentTimeMillis() - exitTime) > 2000) {
    		Toast.makeText(getApplicationContext(), "再按一次退出程序",
    		Toast.LENGTH_SHORT).show();
    		exitTime = System.currentTimeMillis();
    	} else {
    		finish();
    		System.exit(0);
    	}
     }

}
