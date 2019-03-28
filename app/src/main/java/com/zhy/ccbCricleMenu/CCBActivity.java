package com.zhy.ccbCricleMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.UIChat.MsgActivity;
import com.zhy.view.CircleMenuLayout;
import com.zhy.view.CircleMenuLayout.OnMenuItemClickListener;
/**
 * <pre>
 * @author zhy 
 * http://blog.csdn.net/lmj623565791/article/details/43131133
 * </pre>
 */
public class CCBActivity extends AppCompatActivity
{
	private DrawerLayout mDrawerLayout;

	private CircleMenuLayout mCircleMenuLayout;
	private TextView textView;

	private String[] mItemTexts = new String[] { "安全中心", "特色服务", "投资理财",
			"转账汇款", "我的账户", "信用卡" };
	private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal };


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//自已切换布局文件看效果
//		setContentView(R.layout.activity_main02);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mDrawerLayout = findViewById(R.id.drawer_layout);
		NavigationView navView = findViewById(R.id.nav_view);
		ActionBar actionBar = getSupportActionBar();
		if(actionBar != null){
			actionBar.setDisplayHomeAsUpEnabled(true);  //显示导航栏
			actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);  //设置导航按钮图标
		}
		navView.setCheckedItem(R.id.nav_call);  //将call默认为选中
		navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
				mDrawerLayout.closeDrawers();
				return true;
			}
		});

		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
		
		

		mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			
			@Override
			public void itemClick(View view, int pos)
			{
                //OnClick onClick = new OnClick(pos);
                //onClick.onClick(view);
                if(mItemTexts[pos].equals("特色服务")){
                    Intent intent = new Intent();
                    Log.i("safe4","44444 "+pos);
                    //listener(pos);  //设置监听机制
                    intent.setClass(CCBActivity.this,MsgActivity.class);
                    Log.i("safe3","23333");
                    startActivity(intent);
                    Log.i("safe10","10101010");
                }

				Toast.makeText(CCBActivity.this, mItemTexts[pos],
						Toast.LENGTH_SHORT).show();

			}
			
			@Override
			public void itemCenterClick(View view)
			{
				Toast.makeText(CCBActivity.this,
						"you can do something just like ccb  ",
						Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.toolbar, menu);  //加载Toolbar.xml这个文件
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item){  //处理各方法的点击事件
		switch (item.getItemId()){
			case android.R.id.home:  //这里HomeAsUp按钮的id永远是android.R.id.home
				mDrawerLayout.openDrawer(GravityCompat.START);  //这个方法是为了显示华东菜单的展示。
				break;
			case R.id.backup:
				Toast.makeText(this, "you clicked backup", Toast.LENGTH_SHORT).show();
				break;
			case R.id.delete:
				Toast.makeText(this, "you clicked deleted", Toast.LENGTH_SHORT).show();
				break;
			case R.id.settings:
				Toast.makeText(this, "you clicked settings", Toast.LENGTH_SHORT).show();
				break;
			default:
		}
		return true;
	}

}
