package cardapp.com.android.housingsociety.ui.menu;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.bean.EstateInfo;
import cardapp.com.android.housingsociety.bean.EstateInfoListFetchedEvent;
import cardapp.com.android.housingsociety.control.DataControl;
import cardapp.com.android.housingsociety.debug.AppLogger;
import cardapp.com.android.housingsociety.ui.BaseActivity;
import cardapp.com.android.housingsociety.util.AppUtil;
import cardapp.com.android.housingsociety.util.MyNetUtil;

/**
 * 报告问题UI
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_report_problem)
public class ReportProblemActivity extends BaseActivity {

	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.reportContactBtn)
	Button reportContactBtn;
	@ViewById(R.id.reportAddress)
	EditText reportAddress;
	@ViewById(R.id.reportName)
	EditText reportName;
	@ViewById(R.id.reportEmail)
	EditText reportEmail;
	@ViewById(R.id.reportPhone)
	EditText reportPhone;
	@ViewById(R.id.reportProblemDetail)
	EditText reportProblemDetail;

	@Bean
	DataControl dataControl;
	long classId = 1L; // 1:联络我们 2:设置中联络我们
	private boolean hasVisitServer = false;

	@AfterViews
	void afterViews() {
		title.setText(getString(R.string.reportProblems));
	}

	/**
	 * 接收Bus数据后更新界面
	 * 
	 * @param shopList
	 */
	@UiThread
	public void onEvent(EstateInfoListFetchedEvent e) {
		reportContactBtn.setEnabled(true);
		dismissProgressDialog();
		AppLogger.d("onEvent");
		if (e != null && e.getList() != null && e.getList().size() != 0) {
			StringBuilder sb = new StringBuilder();
			for (EstateInfo t : e.getList()) {
				String phone = AppUtil.getRightStringByLanguage(
						myApplication.getAppLan(), t.getChiPhone(),
						t.getChiPhone(), t.getEngPhone());
				String des = AppUtil.getRightStringByLanguage(
						myApplication.getAppLan(), t.getChiTitle(),
						t.getChiTitle(), t.getEngTitle());
				sb.append("*").append(des).append("\n    ");
				if (!TextUtils.isEmpty(phone)) {
					sb.append(phone).append("\n");
				}
			}
			// toast(sb.toString());
			View view =  View.inflate(this,
					R.layout.textview_layout, null);
			TextView text = (TextView) view.findViewById(R.id.text);
			text.setText(sb.toString());
			TextView botton = (TextView) view.findViewById(R.id.ok);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			final AlertDialog dialog = builder.setView(view).create();
			dialog.show();
			botton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		} else {
			toast(getString(R.string.dataEmpty));
		}
	}

	/**
	 * 接收Bus提示
	 * 
	 * @param shopList
	 */
	@UiThread
	public void onEvent(String e) {
		reportContactBtn.setEnabled(true);
		dismissProgressDialog();
		toast(e);
	}

	@Click({ R.id.reportCommit, R.id.reportContactBtn })
	void click(View v) {
		switch (v.getId()) {
		case R.id.reportCommit: // 提交问题

			break;
		case R.id.reportContactBtn: // 联系管理处
			reportContactBtn.setEnabled(false);
			if (MyNetUtil.serverEnable(this) && !hasVisitServer ) {
				showLoadingProgressDialog();
				hasVisitServer = true;
				dataControl.getEstateInfoListAsync(myApplication.getString(R.string.estate),
						classId);
			} else {
				dataControl.getEstateInfoList(myApplication.getString(R.string.estate),
						classId);
			}
			break;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		myApplication.BUS.register(this);
	}

	@Override
	public void onStop() {
		myApplication.BUS.unregister(this);
		super.onStop();
	}
}
