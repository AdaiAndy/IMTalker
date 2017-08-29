package net.adaiandy.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.adaiandy.common.R;
import net.adaiandy.common.widget.recycler.CommonRecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class GalleryView extends RecyclerView {
    
    private Adapter mAdapter = new Adapter();
    private static int LOADER_ID  = 0x1001;
    private LoaderCallback mLoaderCallback = new LoaderCallback();
    private static int MIN_IMAGE_SIZE = 10*1024; //10kb
    private static int MAX_SELECT_COUNT = 3;
    private List<Image> mSelectImages = new LinkedList<>();
    private SelectedChangeListener mSelectedChangeListener;
    


    public GalleryView(Context context) {
        super(context);
        init();
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 供外面setup
     * @param loaderManager
     * @return 返回loaderId，供外面销毁使用
     */
    public int setUp(LoaderManager loaderManager,SelectedChangeListener listener){
        loaderManager.initLoader(LOADER_ID, null, mLoaderCallback);
        mSelectedChangeListener = listener;
        return LOADER_ID;
    }
    
    
    
    private class  LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor>{
        
        private final String[] IMAGE_PROJECTION  = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED
        };

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if(id==LOADER_ID){
                return new CursorLoader(getContext(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI,IMAGE_PROJECTION,null,null,
                        IMAGE_PROJECTION[2]+" DESC");
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            List<Image> images = new ArrayList<>();
            if(data!=null){
                if(data.getCount()>0){
                    if(data.moveToFirst()) {
                        int indextId = data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]);
                        int indexPath = data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]);
                        int indexDate = data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]);
                        
                        do {
                            
                            int id = data.getInt(indextId);
                            String path = data.getString(indexPath);
                            long dataTime = data.getLong(indexDate);

                            File file = new File(path);
                            if(!file.exists()||(file.length()<MIN_IMAGE_SIZE)){
                                continue;
                            }

                            
                            Image image = new Image();
                            image.path = path;
                            image.id = id;
                            image.date = dataTime;
                            
                            images.add(image);
                            
                        } while (data.moveToNext());
                    }

                }
                
            }
            updateSource(images);

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
             updateSource(null);
        }
    }
    
    private  void updateSource(List<Image> images){
        mAdapter.replace(images);
    }

    private void init(){
        setLayoutManager(new GridLayoutManager(getContext(),4));
        setAdapter(mAdapter);
        mAdapter.setListener(new CommonRecyclerAdapter.AdapterListenerImp<Image>() {
            @Override
            public void onItemClick(CommonRecyclerAdapter.ViewHolder holder, Image image) {
                if(onItemClickSelect(image)){
                    holder.updateData(image);
                }
            }
        });
    }

    private boolean onItemClickSelect(Image image) {
        boolean notifyRefresh;
        if(mSelectImages.contains(image)){
            mSelectImages.remove(image);
            image.isSelected = false;
            notifyRefresh = true;
        }else {
            if(mSelectImages.size()>=MAX_SELECT_COUNT){
                Toast.makeText(getContext(),getContext().getString(R.string.label_gallery_select_max_size,MAX_SELECT_COUNT),Toast.LENGTH_SHORT).show();
                notifyRefresh = false;
            }else {
                mSelectImages.add(image);
                image.isSelected = true;
                notifyRefresh  = true;
            }
        }
        
        if(notifyRefresh) {
            notifySelectChanged();
        }
        
        return notifyRefresh;
    }


    /**
     * 通知选中状态改变
     */
    private void notifySelectChanged() {
        if(mSelectedChangeListener!=null){
            mSelectedChangeListener.onSelectedChange(mSelectImages.size());
        }
    }
    
    public String[] getSelectPaths(){
        String[] result = new String[mSelectImages.size()];
        int index = 0;
        for (Image image: mSelectImages){
            result[index++] = image.path;
        }
        return result;
    }
    
    public void clear(){
        for (Image image:mSelectImages) {
            image.isSelected = false;
        }
        mSelectImages.clear();
        mAdapter.notifyDataSetChanged();
    }
    

    
    
    public class Image{
        
        int id;//图片id
        String path;//本地图片路径
        long date;//图片创建日期，用于排序
        boolean isSelected;

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Image image = (Image) o;

            return path != null ? path.equals(image.path) : image.path == null;

        }

        @Override
        public int hashCode() {
            return path != null ? path.hashCode() : 0;
        }
    }
    
    
    private class Adapter extends CommonRecyclerAdapter<Image>{

        @Override
        protected int getItemViewType(int position, Image image) {
            return R.layout.cell_galley;
        }

        @Override
        protected ViewHolder<Image> onCreateViewHolder(View root, int viewType) {
            return new GalleryView.ViewHolder(root);
        }
    }
    
    private class ViewHolder extends CommonRecyclerAdapter.ViewHolder<Image>{

        private ImageView mPic;
        private View mShade;
        private CheckBox mSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            mPic = (ImageView) itemView.findViewById(R.id.im_image);
            mShade = itemView.findViewById(R.id.view_shade);
            mSelected = (CheckBox) itemView.findViewById(R.id.cb_select);
        }

        @Override
        protected void onBind(Image image) {

            Glide.with(getContext())
                    .load(image.path) // 加载路径
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 不使用缓存，直接从原图加载
                    .centerCrop() // 居中剪切
                    .placeholder(R.color.grey_200) // 默认颜色
                    .into(mPic);

            mShade.setVisibility(image.isSelected ? VISIBLE : INVISIBLE);
            mSelected.setChecked(image.isSelected);
            mSelected.setVisibility(VISIBLE);
            
        }
        
    }
    
    
    public interface SelectedChangeListener{
        void onSelectedChange(int count);
    }
    
    
    
}
