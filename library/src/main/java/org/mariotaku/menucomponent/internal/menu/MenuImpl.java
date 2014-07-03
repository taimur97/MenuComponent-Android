package org.mariotaku.menucomponent.internal.menu;

import java.util.Collection;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class MenuImpl implements Menu {

	private final List<MenuItem> menuItems;
	private final Context context;

	MenuImpl(final Context context) {
		this(context, null, null);
	}

	MenuImpl(final Context context, final Collection<MenuItem> items) {
		this(context, null, items);
	}

	MenuImpl(final Context context, final MenuAdapter adapter) {
		this(context, adapter, null);
	}

	MenuImpl(final Context context, final MenuAdapter adapter, final Collection<MenuItem> items) {
		this.context = context;
		menuItems = new MenusList(adapter, items);
	}

	@Override
	public MenuItem add(final CharSequence title) {
		return add(0, 0, 0, title);
	}

	@Override
	public MenuItem add(final int titleRes) {
		return add(0, 0, 0, titleRes);
	}

	@Override
	public MenuItem add(final int groupId, final int itemId, final int order, final CharSequence title) {
		final MenuItem item = new MenuItemImpl(context).setGroupId(groupId).setItemId(itemId).setOrder(order)
				.setTitle(title);
		if (order != 0) {
			menuItems.add(order, item);
		} else {
			menuItems.add(item);
		}
		return item;
	}

	@Override
	public MenuItem add(final int groupId, final int itemId, final int order, final int titleRes) {
		return add(groupId, itemId, order, context.getString(titleRes));
	}

	@Override
	public int addIntentOptions(final int groupId, final int itemId, final int order, final ComponentName caller,
			final Intent[] specifics, final Intent intent, final int flags, final MenuItem[] outSpecificItems) {
		return 0;
	}

	@Override
	public SubMenu addSubMenu(final CharSequence title) {
		return addSubMenu(0, 0, 0, title);
	}

	@Override
	public SubMenu addSubMenu(final int titleRes) {
		return addSubMenu(0, 0, 0, titleRes);
	}

	@Override
	public SubMenu addSubMenu(final int groupId, final int itemId, final int order, final CharSequence title) {
		final MenuItem item = new MenuItemImpl(context).setGroupId(groupId).setItemId(itemId).setOrder(order)
				.setTitle(title);
		final SubMenu subMenu = new SubMenuImpl(context, item);
		((MenuItemImpl) item).setSubMenu(subMenu);
		if (order != 0) {
			menuItems.add(order, item);
		} else {
			menuItems.add(item);
		}
		return subMenu;
	}

	@Override
	public SubMenu addSubMenu(final int groupId, final int itemId, final int order, final int titleRes) {
		return addSubMenu(groupId, itemId, order, context.getString(titleRes));
	}

	@Override
	public void clear() {
		menuItems.clear();
	}

	@Override
	public void close() {

	}

	@Override
	public MenuItem findItem(final int id) {
		for (final MenuItem item : menuItems) {
			if (item.getItemId() == id)
				return item;
			else if (item.hasSubMenu()) {
				final MenuItem possibleItem = item.getSubMenu().findItem(id);
				if (possibleItem != null) return possibleItem;
			}
		}
		return null;
	}

	@Override
	public MenuItem getItem(final int index) {
		return menuItems.get(index);
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	@Override
	public boolean hasVisibleItems() {
		for (final MenuItem item : menuItems) {
			if (item.isVisible()) return true;
		}
		return false;
	}

	@Override
	public boolean isShortcutKey(final int keyCode, final KeyEvent event) {
		return false;
	}

	@Override
	public boolean performIdentifierAction(final int id, final int flags) {
		return false;
	}

	@Override
	public boolean performShortcut(final int keyCode, final KeyEvent event, final int flags) {
		return false;
	}

	@Override
	public void removeGroup(final int groupId) {
		final MenuItem[] array = menuItems.toArray(new MenuItem[menuItems.size()]);
		for (final MenuItem item : array) {
			if (item.hasSubMenu()) {
				item.getSubMenu().removeGroup(groupId);
			} else if (item.getGroupId() == groupId) {
				menuItems.remove(item);
			}
		}
	}

	@Override
	public void removeItem(final int id) {
		final MenuItem[] array = menuItems.toArray(new MenuItem[menuItems.size()]);
		for (final MenuItem item : array) {
			if (item.hasSubMenu()) {
				item.getSubMenu().removeItem(id);
			} else if (item.getItemId() == id) {
				menuItems.remove(item);
			}
		}
	}

	@Override
	public void setGroupCheckable(final int group, final boolean checkable, final boolean exclusive) {
		for (final MenuItem item : menuItems) {
			if (item.getGroupId() == group) {
				item.setCheckable(checkable);
				if (exclusive) {
					break;
				}
			}
		}
	}

	@Override
	public void setGroupEnabled(final int group, final boolean enabled) {
		for (final MenuItem item : menuItems) {
			if (item.getGroupId() == group) {
				item.setEnabled(enabled);
			}
		}

	}

	@Override
	public void setGroupVisible(final int group, final boolean visible) {
		for (final MenuItem item : menuItems) {
			if (item.getGroupId() == group) {
				item.setVisible(visible);
			}
		}
	}

	@Override
	public void setQwertyMode(final boolean isQwerty) {

	}

	@Override
	public int size() {
		return menuItems.size();
	}

	@Override
	public String toString() {
		return menuItems.toString();
	}

}
