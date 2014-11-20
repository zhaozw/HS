package cardapp.com.android.housingsociety.ui.common;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.jpush.android.api.JPushInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.ui.BaseActivity;

/**
 * 处理通知点击事件
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_receiver)
public class ReceiverActivity extends BaseActivity {

	@ViewById(R.id.content)
	TextView content;
	@ViewById(R.id.title)
	TextView title;

	@AfterViews
	void afterViews() {
		title.setText("推送");
		Intent intent = getIntent();
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			String t = intent.getStringExtra(JPushInterface.EXTRA_NOTIFICATION_TITLE);
			t = intent.getStringExtra(JPushInterface.EXTRA_ALERT);
			content.setText(t);
		}
	}
}
