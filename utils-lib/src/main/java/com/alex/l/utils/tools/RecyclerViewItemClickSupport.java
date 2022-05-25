package com.alex.l.utils.tools;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.l.utils.R;
import com.alex.l.utils.extens.ExtensKt;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * [RecyclerViewItemClickSupport]
 * -
 * Created by [Alex Y. Lan] on [2022-05-25]
 * Power by AndroidStudio™ IDE
 *
 * 使用在绑定监听 RecyclerView - item 点击事件，这是可选方法之一，MVVM 架构下几乎用不到
 * * 设置：RecyclerViewItemClickSupport.addTo(recyclerView).setOnItemClickListener(listener)
 * * 注销监听：RecyclerViewItemClickSupport.removeFrom(recyclerView);
 */
public class RecyclerViewItemClickSupport {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private boolean isAntiMultipleClick = false;
    private int longMillis = 600;
    private final ReentrantLock lock = new ReentrantLock(true);
    private ScheduledExecutorService scheduledThreadPool;

    /**
     * 防短时间内反复点击
     *
     * @param longMillis 设置时间间隔，默认 600 毫秒
     */
    @SuppressLint("UseSparseArrays")
    public RecyclerViewItemClickSupport antiMulClick(int longMillis) {
        this.isAntiMultipleClick = true;
        this.longMillis = longMillis;
        scheduledThreadPool = Executors.newScheduledThreadPool(3);
        return this;
    }

    /**
     * 防短时间内反复点击，默认间隔 600 毫秒
     */
    public RecyclerViewItemClickSupport antiMulClick() {
        return antiMulClick(longMillis);
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
            if (isAntiMultipleClick) { // 防双击
                if (!lock.isLocked()) {
                    lock.lock();
                    try {
                        if (holder != null && mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
                        }
                    } finally {
                        scheduledThreadPool.schedule(() -> {
                            v.post(() -> {
                                if (lock.isLocked())
                                    lock.unlock();
                            });
                        }, longMillis, TimeUnit.MILLISECONDS);
                    }
                }
            } else { // 不防双击
                if (holder != null && mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
                }
            }
        }
    };

    private final View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };
    private final RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            // every time a new child view is attached add click listeners to it
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {

        }
    };

    private RecyclerViewItemClickSupport(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        // the ID must be declared in XML, used to avoid
        // replacing the ItemClickSupport without removing
        // the old one from the RecyclerView
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static RecyclerViewItemClickSupport addTo(RecyclerView view) {
        // if there's already an ItemClickSupport attached
        // to this RecyclerView do not replace it, use it
        RecyclerViewItemClickSupport support = (RecyclerViewItemClickSupport) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new RecyclerViewItemClickSupport(view);
        }
        return support;
    }

    public static RecyclerViewItemClickSupport removeFrom(RecyclerView view) {
        RecyclerViewItemClickSupport support = (RecyclerViewItemClickSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public RecyclerViewItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public RecyclerViewItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
        ExtensKt.shutdown(scheduledThreadPool, this.getClass().getSimpleName());
    }

    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {

        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}
