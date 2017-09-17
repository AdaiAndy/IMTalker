package net.adaiandy.imtalker.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import net.adaiandy.common.app.BaseActivity;
import net.adaiandy.imtalker.R;
import net.adaiandy.imtalker.frags.account.UpdateInfoFragment;

public class AccountActivity extends BaseActivity {
    
    private Fragment mCurrrentFragment;

    public static  void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        mCurrrentFragment = new UpdateInfoFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container,mCurrrentFragment)
                .commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCurrrentFragment.onActivityResult(requestCode,resultCode,data);
    }
}
