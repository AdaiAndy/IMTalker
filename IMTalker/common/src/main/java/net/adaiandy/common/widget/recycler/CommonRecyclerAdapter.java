package net.adaiandy.common.widget.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author pjt
 */

public class CommonRecyclerAdapter extends RecyclerView.Adapter {
    

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    
    public abstract static class ViewHodler<Data> extends RecyclerView.ViewHolder{
        
        private Data mData;

        public ViewHodler(View itemView) {
            super(itemView);
        }
        
        public void bind(Data data){
            this.mData = data;
            onBind(data);
        }

        /**
         * 当出发绑定数据时的回调。必须复写
         * @param data
         */
        protected abstract void onBind(Data data);
    }
}
