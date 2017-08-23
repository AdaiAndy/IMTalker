package net.adaiandy.common.widget.recycler;

/**
 * @author pjt
 */
@SuppressWarnings({"unchecked", "unused"})
public interface AdapterCallback<Data> {
    void update(Data data, CommonRecyclerAdapter.ViewHolder<Data> holder);
}
