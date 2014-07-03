package org.mariotaku.menucomponent.widget;

import org.mariotaku.menucomponent.R;
import org.mariotaku.menucomponent.internal.menu.MenuAdapter;
import org.mariotaku.menucomponent.internal.menu.MenuUtils;
import org.mariotaku.menucomponent.internal.widget.IListPopupWindow;

import android.content.Context;
import android.support.v4.widget.ListPopupWindowCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

public class PopupMenu implements OnDismissListener, OnItemClickListener, OnTouchListener {

	private OnMenuItemClickListener mItemClickListener;
	private OnDismissListener mDismissListener;

	private final Context mContext;
	private final View mView;
	private final IListPopupWindow mWindow;

	private final MenuAdapter mAdapter;

	private boolean mDidAction;

	/**
	 * Constructor for default vertical layout
	 * 
	 * @param context Context
	 */
	public PopupMenu(final Context context, final View view) {
		mContext = context;
		mView = view;
		mAdapter = new MenuAdapter(context);
		mWindow = IListPopupWindow.InstanceHelper.getInstance(context);
		mWindow.setInputMethodMode(IListPopupWindow.INPUT_METHOD_NOT_NEEDED);
		mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		mWindow.setAnchorView(mView);
		mWindow.setWidth(mContext.getResources().getDimensionPixelSize(R.dimen.mc__popup_window_width));
		mWindow.setAdapter(mAdapter);
		mWindow.setOnItemClickListener(this);
		mWindow.setModal(true);
	}

	/**
	 * Dismiss the popup window.
	 */
	public void dismiss() {
		if (isShowing()) {
			mWindow.dismiss();
		}
	}

	public View.OnTouchListener getDragToOpenListener() {
		if (mWindow.isNativeImplementation()) return ListPopupWindowCompat.createDragToOpenListener(mWindow, mView);
		return null;
	}

	public Menu getMenu() {
		return mAdapter.getMenu();
	}

	public MenuInflater getMenuInflater() {
		return mAdapter.getMenuInflater();
	}

	public void inflate(final int menuRes) {
		mAdapter.inflate(menuRes);
	}

	public void invalidateMenu() {
		mAdapter.notifyDataSetChanged();
	}

	public boolean isShowing() {
		return mWindow != null && mWindow.isShowing();
	}

	@Override
	public void onDismiss() {
		if (!mDidAction && mDismissListener != null) {
			mDismissListener.onDismiss(this);
		}
	}

	@Override
	public void onItemClick(final AdapterView<?> adapter, final View view, final int position, final long id) {
		mDidAction = true;
		dismiss();
		final MenuItem item = mAdapter.getItem(position);
		if (item.hasSubMenu()) {
			if (item.getSubMenu().size() == 0) return;
			setMenu(item.getSubMenu());
			show();
		} else {
			if (mItemClickListener != null) {
				mItemClickListener.onMenuItemClick(item);
			}
		}
	}

	@Override
	public boolean onTouch(final View v, final MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
			mWindow.dismiss();
			return true;
		}

		return false;
	}

	public void setMenu(final Menu menu) {
		mAdapter.setMenu(menu);
	}

	/**
	 * Set listener for window dismissed. This listener will only be fired if
	 * the quickaction dialog is dismissed by clicking outside the dialog or
	 * clicking on sticky item.
	 */
	public void setOnDismissListener(final PopupMenu.OnDismissListener listener) {
		mWindow.setOnDismissListener(listener != null ? this : null);
		mDismissListener = listener;
	}

	/**
	 * Set listener for action item clicked.
	 * 
	 * @param listener Listener
	 */
	public void setOnMenuItemClickListener(final OnMenuItemClickListener listener) {
		mItemClickListener = listener;
	}

	public void show() {
		if (isShowing()) {
			dismiss();
		}
		try {
			mWindow.show();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}


	public static PopupMenu getInstance(final Context context, final View view) {
		return new PopupMenu(context, view);
	}

	/**
	 * Listener for window dismiss
	 */
	public static interface OnDismissListener {
		public void onDismiss(PopupMenu PopupMenu);
	}

}
