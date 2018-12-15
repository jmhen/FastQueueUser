package fq.campus.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.view.View;

import fq.campus.FeedActivity;
import fq.campus.NotificationActivity;
import fq.campus.ProfileActivity;
import fq.campus.R;

public class tabActivity extends baseActivity {
    protected TabLayout fqTab;
    protected Activity mActivity;
    protected TabLayout.OnTabSelectedListener onFqTabSelectedListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        onFqTabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int idx = tab.getPosition();
                Intent proceed = new Intent();
                switch (idx) {
                    case 0:
                        proceed.setClass(mActivity, FeedActivity.class);
                        startActivity(proceed);
                        break;
                    case 1:
                        proceed.setClass(mActivity, NotificationActivity.class);
                        startActivity(proceed);
                        break;
                    case 2:
                        proceed.setClass(mActivity, ProfileActivity.class);
                        startActivity(proceed);
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }


    protected void tabIni(){
        if(mActivity.getClass().equals(FeedActivity.class)) {
            fqTab.getTabAt(0).select();
            fqTab.getTabAt(0).setIcon(R.drawable.feed);

        }
        else if(mActivity.getClass().equals(NotificationActivity.class)) {
            fqTab.getTabAt(1).select();
            fqTab.getTabAt(1).setIcon(R.drawable.alert);
        }
        else if(mActivity.getClass().equals(ProfileActivity.class)) {
            fqTab.getTabAt(2).select();
            fqTab.getTabAt(2).setIcon(R.drawable.profile);
        }

    }


//    public void tabClick(View view) {
//        int id = view.getId();
//        Intent proceed = new Intent();
//        switch (id){
//            case R.id.feed:
//                proceed.setClass(this, FeedActivity.class);
//                startActivity(proceed);
//                break;
//            case R.id.notification:
//                proceed.setClass(this, NotificationActivity.class);
//                startActivity(proceed);
//                break;
//            case R.id.profile:
//                proceed.setClass(this, ProfileActivity.class);
//                startActivity(proceed);
//                break;
//            default: break;
//
//        }
//
//    }
}
