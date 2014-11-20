package cardapp.com.android.housingsociety.ui.common;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cardapp.com.android.housingsociety.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * 
 *
 * @author CardApp@ZuoQing
 *
 */
public class GoogleMapActivity extends FragmentActivity {
    public static final String GOOGLEMMAPLOCATION = "GoogleMapActivity.GOOGLEMMAPLOCATION";
    public static final String GOOGLEMMAPLOCATIONTITLE = "GoogleMapActivity.GOOGLEMMAPLOCATIONTITLE";

	private GoogleMap mMap;
    
    CameraPosition SYDNEY = null;

    private String beginLatitue = "22.323023";

    private String beginLongtitue = "114.160674";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        
        // get params
        Intent intent = getIntent();
        if (intent == null) {
        	return;
        } 

        // set title
        String tit = intent.getStringExtra(GOOGLEMMAPLOCATIONTITLE);
        if (!TextUtils.isEmpty(tit)) {
            TextView title = (TextView) findViewById(R.id.title);
        	title.setText(tit);
        }
        // set location
        String location = intent.getStringExtra(GOOGLEMMAPLOCATION);
        if (TextUtils.isEmpty(location)) {
        	return;
        }
        String[] arr = location.split(",");
        if (arr.length == 2) {
        	beginLatitue = arr[0];
        	beginLongtitue = arr[1];
        } else {
        	return ;
        }
        
        SYDNEY = new CameraPosition.Builder()
                .target(new LatLng(Double.valueOf(beginLatitue), Double.valueOf(beginLongtitue))).zoom(15.5f)
                .bearing(0).tilt(25).build();
        
        // Try to obtain the map from the SupportMapFragment.
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        // Check if we were successful in obtaining the map.
        if (mMap != null) {
            setUpMap();
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(
                new LatLng(Double.valueOf(beginLatitue), Double.valueOf(beginLongtitue))).title(""));

        // We will provide our own zoom controls.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        onGoToSydney(null);
    }

    /**
     * Called when the Animate To Sydney button is clicked.
     */
    public void onGoToSydney(View view) {
        changeCamera(CameraUpdateFactory.newCameraPosition(SYDNEY), new CancelableCallback() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onCancel() {
            }
        });
    }

    /**
     * Change the camera position by moving or animating the camera depending on
     * the state of the animate toggle button.
     */
    private void changeCamera(CameraUpdate update, CancelableCallback callback) {
        mMap.animateCamera(update, callback);
    }
    
    /**
     * 销毁UI
     * @param v
     */
    public void finishActivity(View v) {
    	this.finish();
    }

}
