package org.mariotaku.menucomponent.internal.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListPopupWindow;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ListPopupWindowNative extends ListPopupWindow implements IListPopupWindow {

	/**
	 * Create a new, empty popup window capable of displaying items from a
	 * ListAdapter. Backgrounds should be set using
	 * {@link #setBackgroundDrawable(Drawable)}.
	 * 
	 * @param context Context used for contained views.
	 */
	public ListPopupWindowNative(final Context context) {
		this(context, null);
	}

	/**
	 * Create a new, empty popup window capable of displaying items from a
	 * ListAdapter. Backgrounds should be set using
	 * {@link #setBackgroundDrawable(Drawable)}.
	 * 
	 * @param context Context used for contained views.
	 * @param attrs Attributes from inflating parent views used to style the
	 *            popup.
	 */
	public ListPopupWindowNative(final Context context, final AttributeSet attrs) {
		this(context, attrs, android.R.attr.listPopupWindowStyle);
	}

	/**
	 * Create a new, empty popup window capable of displaying items from a
	 * ListAdapter. Backgrounds should be set using
	 * {@link #setBackgroundDrawable(Drawable)}.
	 * 
	 * @param context Context used for contained views.
	 * @param attrs Attributes from inflating parent views used to style the
	 *            popup.
	 * @param defStyleAttr Default style attribute to use for popup content.
	 */
	public ListPopupWindowNative(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * Create a new, empty popup window capable of displaying items from a
	 * ListAdapter. Backgrounds should be set using
	 * {@link #setBackgroundDrawable(Drawable)}.
	 * 
	 * @param context Context used for contained views.
	 * @param attrs Attributes from inflating parent views used to style the
	 *            popup.
	 * @param defStyleAttr Style attribute to read for default styling of popup
	 *            content.
	 * @param defStyleRes Style resource ID to use for default styling of popup
	 *            content.
	 */
	public ListPopupWindowNative(final Context context, final AttributeSet attrs, final int defStyleAttr,
			final int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public boolean isNativeImplementation() {
		return true;
	}

}
