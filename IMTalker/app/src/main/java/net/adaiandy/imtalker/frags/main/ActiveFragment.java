package net.adaiandy.imtalker.frags.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.adaiandy.common.app.BaseFragment;
import net.adaiandy.common.widget.GalleryView;
import net.adaiandy.imtalker.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveFragment extends BaseFragment {

    @BindView(R.id.galleyView)
    GalleryView mGalley;
    
    public ActiveFragment() {
        // Required empty public constructor
    }
    
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }


    @Override
    protected void initData() {
        super.initData();
        mGalley.setUp(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedChange(int count) {
                
            }
        });
    }
}
