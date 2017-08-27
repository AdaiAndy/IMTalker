package net.adaiandy.imtalker.helper;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

/**
 * @author pjt
 *         用于处理底部导航栏的点击事件
 */

@SuppressWarnings("unused")
public class NavHelper<T> {

    private final SparseArray<Tab<T>> mTabs = new SparseArray<>();//缓存已经添加过的tab

    private final Context mContext;
    private final FragmentManager mFragmentManager;
    private final onTabChangedListener<T> mTabChangedListener;
    private final int containerId;//主界面id

    private Tab<T> mCurrentTab;//当前的tab

    public NavHelper(Context context, FragmentManager fragmentManager, onTabChangedListener<T> tabChangedListener, int containerId) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mTabChangedListener = tabChangedListener;
        this.containerId = containerId;
    }


    /**
     * 添加menu Tab,允许添加多个一样的tab
     *
     * @param menuId 对应tab的menuid
     * @param tab    tab
     * @return
     */
    public NavHelper<T> add(int menuId, Tab<T> tab) {
        mTabs.put(menuId, tab);
        return this;
    }

    public Tab<T> getCurrentTab() {
        return mCurrentTab;
    }


    public boolean performItemClick(int menuId) {
        //集合中寻找tab，有就处理
        Tab<T> tab = mTabs.get(menuId);
        if (tab != null) {
            doSelect(tab);
            return true;
        }
        return false;
    }

    /**
     * 进行正在的tab切换
     *
     * @param tab
     */
    private void doSelect(Tab<T> tab) {
        Tab<T> oldTab = null;
        if (mCurrentTab != null) {
            oldTab = mCurrentTab;
            if (tab == oldTab) { //重复点了
                notifyTabReSelect(tab);
                return;
            }
        }
        mCurrentTab = tab;
        doTabChange(oldTab, mCurrentTab);


    }

    /**
     * 进行fragment真实的操作
     *
     * @param oldTab
     * @param currentTab
     */
    private void doTabChange(Tab<T> oldTab, Tab<T> currentTab) {
        FragmentTransaction fgt = mFragmentManager.beginTransaction();

        if (oldTab != null) { //旧的要移除
            if (oldTab.fragment != null) {
                fgt.detach(oldTab.fragment);//只是暂时移除，还在缓存
            }
        }

        if (currentTab != null) {
            if (currentTab.fragment == null) {
                //创建fragment
                Fragment fragment = Fragment.instantiate(mContext, mCurrentTab.clx.getName(), null);
                //缓存起来
                currentTab.fragment = fragment;
                //添加
                fgt.add(containerId, fragment, currentTab.clx.getName());

            } else {
                fgt.attach(currentTab.fragment);
            }
        }

        fgt.commit();

        notifyTabReSelect(oldTab, currentTab);

    }

    private void notifyTabReSelect(Tab<T> oldTab, Tab<T> currentTab) {

        if (mTabChangedListener != null) {
            mTabChangedListener.onTabChanged(currentTab, oldTab);
        }
    }

    /**
     * 重复点击同一个tab
     *
     * @param tab
     */
    private void notifyTabReSelect(Tab<T> tab) {
    }


    /**
     * 我们所有的Tab基础属性
     *
     * @param <T>
     */
    public static class Tab<T> {
        public Class<?> clx; //Fragment对应的class信息
        public T extra;//额外字段，用户自己设定需要使用

        public Tab(Class<?> clx, T extra) {
            this.clx = clx;
            this.extra = extra;
        }

        Fragment fragment;//内部缓存的fragment，外部无法访问

    }


    /**
     * 外部回调tab 切换接口
     *
     * @param <T>
     */
    public interface onTabChangedListener<T> {
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }
}
