/**
 * Copyright (c) 2006-2011 Floggy Open Source Group. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.floggy.persistence.android.test;

import java.lang.reflect.Method;

import java.util.Arrays;

import org.floggy.persistence.android.Filter;
import org.floggy.persistence.android.FloggyException;
import org.floggy.persistence.android.ObjectSet;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public abstract class AbstractTest extends FloggyBaseTest {
	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*
	* @throws RuntimeException DOCUMENT ME!
	*/
	public Filter getFilter() {
		return new Filter() {
				public boolean matches(Object persistable) {
					Object temp = getValueForSetMethod();
					Object array = null;

					try {
						array = getX(persistable);
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}

					if (temp != null) {
						if (temp.getClass().isArray()) {
							Class clazz = temp.getClass().getComponentType();

							if (clazz == short.class) {
								return Arrays.equals((short[]) temp, (short[]) array);
							} else if (clazz == boolean.class) {
								return Arrays.equals((boolean[]) temp, (boolean[]) array);
							} else if (clazz == byte.class) {
								return Arrays.equals((byte[]) temp, (byte[]) array);
							} else if (clazz == char.class) {
								return Arrays.equals((char[]) temp, (char[]) array);
							} else if (clazz == double.class) {
								return Arrays.equals((double[]) temp, (double[]) array);
							} else if (clazz == int.class) {
								return Arrays.equals((int[]) temp, (int[]) array);
							} else if (clazz == float.class) {
								return Arrays.equals((float[]) temp, (float[]) array);
							} else if (clazz == long.class) {
								return Arrays.equals((long[]) temp, (long[]) array);
							} else {
								return Arrays.equals((Object[]) temp, (Object[]) array);
							}
						} else {
							return temp.equals(array);
						}
					} else

						return temp == array;
				}
			};
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public abstract Object getValueForSetMethod();

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public String getNameForGetMethod() {
		return "getX";
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public String getNameForSetMethod() {
		return "setX";
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public Object getNewValueForSetMethod() {
		return null;
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public abstract Object newInstance();

	/**
	* DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	public void testDelete() throws Exception {
		Object object = newInstance();
		long id = manager.save(object);
		manager.delete(object.getClass(), id);
		object = newInstance();

		try {
			manager.load(object, id);
			fail("This object must was deleted!");
		} catch (Exception e) {
			assertEquals(FloggyException.class, e.getClass());
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	public void testDeleteAll() throws Exception {
		Object object = newInstance();

		manager.deleteAll(object.getClass());
		assertEquals(0, manager.find(object.getClass(), null, null).size());

		manager.save(object);

		assertEquals(1, manager.find(object.getClass(), null, null).size());

		manager.deleteAll(object.getClass());
		assertEquals(0, manager.find(object.getClass(), null, null).size());
	}

	/**
	* DOCUMENT ME!
	*/
	public void testDeleteWithNullObject() {
		try {
			manager.delete(null, 0);
			fail("A IllegalArgumentException must be throw!");
		} catch (Exception e) {
			assertEquals(e.getClass(), IllegalArgumentException.class);
		}
	}

	/**
	* DOCUMENT ME!
	*/
	public void testDeleteWithoutSave() {
		Object object = newInstance();

		try {
			manager.delete(object.getClass(), 0);
			assertTrue("Nothing happend because nothing was saved!", true);
		} catch (Exception e) {
			fail("No exception should be throwed!");
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	public void testFind() throws Exception {
		Object object = newInstance();
		setX(object, getValueForSetMethod());

		long id = manager.save(object);

		try {
			ObjectSet set = manager.find(object.getClass(), null, null);
			assertEquals(1, set.size());

			set.close();
		} finally {
			manager.delete(object.getClass(), id);
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	public void testFindPersistableClassIsNotAValidPersistableClass()
		throws Exception {
		try {
			manager.find(String.class, null, null);
			fail("A IllegalArgumentException must be throw!");
		} catch (Exception e) {
			assertEquals(e.getClass(), IllegalArgumentException.class);
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	public void testFindPersistableClassIsNull() throws Exception {
		try {
			manager.find(null, getFilter(), null);
			fail("A IllegalArgumentException must be throw!");
		} catch (Exception e) {
			assertEquals(e.getClass(), IllegalArgumentException.class);
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	public void testFindWithFilter() throws Exception {
		Object object = newInstance();
		setX(object, getValueForSetMethod());

		long id = manager.save(object);

		try {
			ObjectSet set = manager.find(object.getClass(), getFilter(), null);
			assertEquals(1, set.size());
			assertEquals(id, set.getId(0));
			equals(getValueForSetMethod(), getX(set.get(0)));
			set.close();
		} finally {
			manager.delete(object.getClass(), id);
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	public void testGetIt() throws Exception {
		Object object = newInstance();

		long id = 0;

		try {
			id = manager.getId(object);
			assertEquals(-1, id);

			id = manager.save(object);
			assertTrue("Deveria ser diferente de -1!", id != -1);

			long temp = manager.getId(object);
			assertEquals(id, temp);
		} finally {
			manager.delete(object.getClass(), id);
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	public void testIsPersisted() throws Exception {
		Object object = newInstance();
		boolean isPersisted = manager.isPersisted(object);
		assertFalse(isPersisted);

		long id = manager.save(object);

		try {
			isPersisted = manager.isPersisted(object);
			assertTrue(isPersisted);
		} finally {
			manager.delete(object.getClass(), id);
		}
	}

	/**
	* DOCUMENT ME!
	*/
	public void testLoadWithNullObject() {
		try {
			manager.load(null, 0);
			fail("A IllegalArgumentException must be throw!");
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

	/**
	* DOCUMENT ME!
	*/
	public void testLoadWithRecordIdThatDontExist() {
		try {
			manager.load(newInstance(), 123234);
			fail("A FloggyException must be throwed!");
		} catch (Exception ex) {
			assertEquals(ex.getClass(), FloggyException.class);
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	public void testNotNullAttribute() throws Exception {
		Object object = newInstance();
		setX(object, getValueForSetMethod());

		long id = manager.save(object);

		try {
			assertTrue("Deveria ser diferente de -1!", id != -1);
			object = newInstance();
			manager.load(object, id);
			assertNotNull("Não deveria ser null!", getX(object));
			equals(getValueForSetMethod(), getX(object));
		} finally {
			manager.delete(object.getClass(), id);
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	public void testNullAttribute() throws Exception {
		Object object = newInstance();
		long id = manager.save(object);

		try {
			assertTrue("Deveria ser diferente de -1!", id != -1);
			object = newInstance();
			manager.load(object, id);
			assertNull("Deveria ser null!", getX(object));
		} finally {
			manager.delete(object.getClass(), id);
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	public void testSaveAndEdit() throws Exception {
		Object object = newInstance();
		long id = manager.save(object);

		try {
			assertTrue("Deveria ser diferente de -1!", id != -1);
			object = newInstance();
			manager.load(object, id);
			setX(object, getNewValueForSetMethod());

			long tempId = manager.save(object);
			assertEquals(id, tempId);
		} finally {
			manager.delete(object.getClass(), id);
		}
	}

	/**
	* DOCUMENT ME!
	*/
	public void testSaveWithNullObject() {
		try {
			manager.save(null);
			fail("A IllegalArgumentException must be throw!");
		} catch (Exception e) {
			assertEquals(e.getClass(), IllegalArgumentException.class);
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @param o1 DOCUMENT ME!
	* @param o2 DOCUMENT ME!
	*/
	protected void equals(Object o1, Object o2) {
		if ((o1 != null) && o2.getClass().isArray()) {
			Class clazz = o1.getClass().getComponentType();

			if (clazz == short.class) {
				assertTrue("Deveria ser igual(arrays) !",
					Arrays.equals((short[]) o1, (short[]) o2));
			} else if (clazz == boolean.class) {
				assertTrue("Deveria ser igual(o2s) !",
					Arrays.equals((boolean[]) o1, (boolean[]) o2));
			} else if (clazz == byte.class) {
				assertTrue("Deveria ser igual(arrays) !",
					Arrays.equals((byte[]) o1, (byte[]) o2));
			} else if (clazz == char.class) {
				assertTrue("Deveria ser igual(arrays) !",
					Arrays.equals((char[]) o1, (char[]) o2));
			} else if (clazz == double.class) {
				assertTrue("Deveria ser igual(arrays) !",
					Arrays.equals((double[]) o1, (double[]) o2));
			} else if (clazz == int.class) {
				assertTrue("Deveria ser igual(arrays) !",
					Arrays.equals((int[]) o1, (int[]) o2));
			} else if (clazz == float.class) {
				assertTrue("Deveria ser igual(arrays) !",
					Arrays.equals((float[]) o1, (float[]) o2));
			} else if (clazz == long.class) {
				assertTrue("Deveria ser igual(arrays) !",
					Arrays.equals((long[]) o1, (long[]) o2));
			} else {
				assertTrue("Deveria ser igual(arrays) !",
					Arrays.equals((Object[]) o1, (Object[]) o2));
			}
		} else {
			assertEquals("Deveria ser igual!", o1, o2);
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	protected abstract Class getParameterType();

	/**
	* DOCUMENT ME!
	*
	* @param object DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	protected Object getX(Object object) throws Exception {
		Method method =
			object.getClass().getMethod(getNameForGetMethod(), new Class[0]);

		return method.invoke(object, new Object[0]);
	}

	/**
	* DOCUMENT ME!
	*
	* @param object DOCUMENT ME!
	* @param param DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	protected void setX(Object object, Object param) throws Exception {
		Method method =
			object.getClass()
			 .getMethod(getNameForSetMethod(), new Class[] { getParameterType() });
		method.invoke(object, new Object[] { param });
	}
}
