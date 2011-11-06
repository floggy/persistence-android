package org.floggy.persistence.android.test;

import org.floggy.persistence.android.PersistableManager;

import android.test.AndroidTestCase;

public abstract class FloggyBaseTest extends AndroidTestCase {

	/**
	 * DOCUMENT ME!
	 */
	protected PersistableManager manager;

	public void setUp() {
		if (manager == null) {
			manager = PersistableManager.getInstance(getContext());
		}
	}
}
