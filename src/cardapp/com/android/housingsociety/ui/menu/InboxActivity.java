package cardapp.com.android.housingsociety.ui.menu;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.widget.TextView;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.ui.BaseActivity;

/**
 * 报告问题UI
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_inbox)
public class InboxActivity extends BaseActivity {

	@ViewById(R.id.title)
	TextView title;
	
	@AfterViews
	void afterViews() {
		title.setText(getString(R.string.inbox));
	}
}
