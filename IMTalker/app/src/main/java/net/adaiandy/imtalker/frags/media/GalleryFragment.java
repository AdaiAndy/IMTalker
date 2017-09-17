package net.adaiandy.imtalker.frags.media;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import net.adaiandy.common.tools.UiTool;
import net.adaiandy.common.widget.GalleryView;
import net.adaiandy.imtalker.R;

/**
 * 选择头像DialogFragment
 */
public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.SelectedChangeListener {
    private IOnSelectedListener mSelectedListener;
    private GalleryView mGalleryView;


    public GalleryFragment() {
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGalleryView = root.findViewById(R.id.galleyView);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGalleryView.setUp(getLoaderManager(),this);
    }

    public GalleryFragment setOnSelectedListener(IOnSelectedListener listener){
        mSelectedListener = listener;
        return this;
    }

    /**
     * 选择图片触发
     * @param count
     */
    @Override
    public void onSelectedChange(int count) {
        if(count>0){
            dismiss();
            String paths[] = mGalleryView.getSelectPaths();
            if(paths.length>0&&mSelectedListener!=null){
                mSelectedListener.onSelectImage(paths[0]);
            }
            mSelectedListener = null;
        }
        
    }

    /**
     * 选择图片后listener
     */
    public interface IOnSelectedListener{
        void onSelectImage(String path);//path为本地路径
    }
    
    
    
    public class TransStatusBottomSheetDialog extends BottomSheetDialog{

        public TransStatusBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStatusBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
            super(context, theme);
        }

        protected TransStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            //对窗口进行设置

            final  Window window = getWindow();
            if(window==null){
                return;
            }

            // 得到屏幕高度
            int screenHeight = UiTool.getScreenHeight(getOwnerActivity());
            // 得到状态栏的高度
            int statusHeight = UiTool.getStatusBarHeight(getOwnerActivity());

            // 计算dialog的高度并设置

            int dialogHeight = screenHeight - statusHeight;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    dialogHeight <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        }
    }
    
    

}
