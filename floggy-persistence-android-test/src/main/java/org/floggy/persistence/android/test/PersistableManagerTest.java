package org.floggy.persistence.android.test;

import org.floggy.persistence.android.PersistableManager;
import org.floggy.persistence.android.core.impl.PersistableManagerAndroid;

import android.test.AndroidTestCase;

public class PersistableManagerTest extends AndroidTestCase {

	public void testGetInstanceNotNullContext() {
		PersistableManager manager = PersistableManager.getInstance(getContext());

		assertNotNull(manager);
		assertEquals(PersistableManagerAndroid.class, manager.getClass());
	}

	public void testGetInstanceNullContext() {
		try {
			PersistableManager.getInstance(null);
			fail("It mus throw an IllegalArgumentException");
		} catch (Exception ex) {
			assertEquals(IllegalArgumentException.class, ex.getClass());
		}
	}
}
