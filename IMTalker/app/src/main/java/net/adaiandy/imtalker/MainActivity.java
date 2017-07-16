package net.adaiandy.imtalker;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.adaiandy.common.app.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author
 * @version 1.0
 * @date 2017/7/5
 */

public class MainActivity extends BaseActivity {
    
    @BindView(R.id.txt_display)
     TextView mTextView;
    
    @BindView(R.id.edit_content)
     EditText mEditText;


    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_main;
    }

    @OnClick(R.id.btn_submmit)
    void onSubmmit(){
        mTextView.setText(mEditText.getText());
    }

    @Override
    protected void initData() {
        super.initData();
        
    }
}


