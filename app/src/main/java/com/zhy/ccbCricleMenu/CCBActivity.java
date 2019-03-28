package com.zhy.ccbCricleMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
		
		

		mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			
			@Override
			public void itemClick(View view, int pos)
			{
                //OnClick onClick = new OnClick(pos);
                //onClick.onClick(view);
                if(mItemTexts[pos] == "特色服务"){
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
	public void listener(int pos){
		OnClick onClick = new OnClick(pos);
		Log.i("safe1","111111");
		Log.i("teext",textView.getText().toString());
		textView.setOnClickListener(onClick);
		Log.i("safe6","66666");
	}

	public class OnClick implements View.OnClickListener{
		public int pos;
		public OnClick(int pos){
			this.pos = pos;
		}
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			Log.i("safe5","55555");
			switch (v.getId()){
				case R.id.id_circle_menu_item_image:
					Log.i("safe2","222222");
					intent.setClass(CCBActivity.this,MsgActivity.class);
					break;
				default:
					intent = null;
					break;
			}
			startActivity(intent);
		}
	}

}
