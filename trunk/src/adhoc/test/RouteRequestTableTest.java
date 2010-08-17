package adhoc.test;
 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adhoc.aodv.exception.NoSuchRouteException;
import adhoc.aodv.routes.RouteRequestEntry;
import adhoc.aodv.routes.RouteRequestTable;

public class RouteRequestTableTest {
	RouteRequestTable rt;
	RouteRequestEntry re1, re2, re3, re4, re5, re6;

	@Before public void setUp() throws Exception {
		rt = new RouteRequestTable();
		
		re1 = new RouteRequestEntry(0, 0, 1, 1, 0);
		re2 = new RouteRequestEntry(1, 0, 1, 1, 0);
		re3 = new RouteRequestEntry(0, 1, 1, 1, 0);
		re4 = new RouteRequestEntry(0, 0, 1, 1, 1);
		
		re5 = new RouteRequestEntry(0, 0, 2, 1, 1);
		re6 = new RouteRequestEntry(0, 0, 1, 2, 1);
	}

	@After public void tearDown() throws Exception {
		rt = null;
		re1 = null; re2 = null; re3 = null; re4 = null; re5 = null; re6 = null;
	}
	
	/**
	 * Testing an empty request table
	 */
	@Test public void isRouteRequestTableEmpty(){
		assertTrue(rt.isEmpty());
		rt.addRouteRequestEntry(re1, false);
		assertTrue(rt.isEmpty());
	}
	
	/**
	 * Testing an empty request table
	 */
	@Test public void isRouteRequestTableEmpty2(){
		assertTrue(rt.isEmpty());
		rt.addRouteRequestEntry(re1, true);
		assertFalse(rt.isEmpty());
	}
	
	/**
	 * Testing that the same entry is not accepted twice
	 */
	@Test public void addSameEntry(){
		assertTrue(rt.addRouteRequestEntry(re1, true));
		assertFalse(rt.addRouteRequestEntry(re1, true));
	}
	
	/**
	 * Testing combinations of the second boolean parameter "setTimer"
	 * to test if it has any impact adding the entry element twice
	 */
	@Test public void addSameReqEntry2(){
		assertTrue(rt.addRouteRequestEntry(re1, false));
		assertFalse(rt.addRouteRequestEntry(re1, false));
	}
	
	/**
	 * Testing combinations of the second boolean parameter "setTimer"
	 * to test if it has any impact adding the entry element twice
	 */
	@Test public void addSameReqEntry3(){
		assertTrue(rt.addRouteRequestEntry(re1, true));
		assertFalse(rt.addRouteRequestEntry(re1, false));
	}
	
	/**
	 * Testing combinations of the second boolean parameter "setTimer"
	 * to test if it has any impact adding the entry element twice
	 */
	@Test public void addSameReqEntry4(){
		assertTrue(rt.addRouteRequestEntry(re1, false));
		assertFalse(rt.addRouteRequestEntry(re1, true));
	}
	
	/**
	 * Testing that a req entry is uniquely defined by the (broadcastID,sourceAddress) pair
	 */
	@Test public void uniqueReqEntryTest(){
		assertTrue(rt.addRouteRequestEntry(re1, true));
		assertTrue(rt.addRouteRequestEntry(re2, true));
		assertTrue(rt.addRouteRequestEntry(re3, true));
		
		assertFalse(rt.addRouteRequestEntry(re4, true));
		assertFalse(rt.addRouteRequestEntry(re5, true));
		assertFalse(rt.addRouteRequestEntry(re6, true));
	}
	
	@Test public void routeRequestEntryExistsTest(){
		assertFalse(rt.routeRequestEntryExists(re1.getSourceAddress(), re1.getBroadcastID()));
		rt.addRouteRequestEntry(re1, true);
		assertTrue(rt.routeRequestEntryExists(re1.getSourceAddress(), re1.getBroadcastID()));
	}
	
	@Test public void getRouteRequestEntryTest(){
		try {
			rt.getRouteRequestEntry(0, 0, false);
			assertTrue(false);
		} catch (NoSuchRouteException e) {
			assertTrue(true);
		}
		try {
			rt.getRouteRequestEntry(0, 0, true);
			assertTrue(false);
		} catch (NoSuchRouteException e) {
			assertTrue(true);
		}
		try {
			rt.getRouteRequestEntry(1, 0, true);
			assertTrue(false);
		} catch (NoSuchRouteException e) {
			assertTrue(true);
		}
		try {
			rt.getRouteRequestEntry(1, 0, false);
			assertTrue(false);
		} catch (NoSuchRouteException e) {
			assertTrue(true);
		}
		rt.addRouteRequestEntry(re1, true);
		try {
			assertEquals(re1,rt.getRouteRequestEntry(re1.getSourceAddress(), re1.getBroadcastID(), false));
		} catch (NoSuchRouteException e) {
			assertTrue(false);
		}
		try {
			assertEquals(re1,rt.getRouteRequestEntry(re1.getSourceAddress(), re1.getBroadcastID(), true));
		} catch (NoSuchRouteException e) {
			assertTrue(false);
		}
		try {
			rt.getRouteRequestEntry(re1.getSourceAddress(), re1.getBroadcastID(), false);
			assertTrue(false);
		} catch (NoSuchRouteException e) {
			assertTrue(true);
		}
	}
	
	@Test public void removeEntryTest(){
		rt.addRouteRequestEntry(re1, true);
		assertTrue(rt.removeEntry(re1.getSourceAddress(), re1.getBroadcastID()));
	}

	@Test public void removeEntryTest2(){
		rt.addRouteRequestEntry(re1, false);
		assertTrue(rt.removeEntry(re1.getSourceAddress(), re1.getBroadcastID()));
	}
	
	@Test public void getNextRouteToExpire(){
		try {
			rt.getNextRouteToExpire();
			assertTrue(false);
		} catch (NoSuchRouteException e) {
			assertTrue(true);
		}
	}
	@Test public void getNextRouteToExpire2(){
		rt.addRouteRequestEntry(re1, false);
		try {
			rt.getNextRouteToExpire();
			assertTrue(false);
		} catch (NoSuchRouteException e) {
			assertTrue(true);
		}
	}
	@Test public void getNextRouteToExpire3(){
		rt.addRouteRequestEntry(re1, true);
		try {
			assertEquals(re1,rt.getNextRouteToExpire());
			assertTrue(true);
		} catch (NoSuchRouteException e) {
			assertTrue(false);
		}	
	}
	@Test public void getNextRouteToExpire4(){
		rt.addRouteRequestEntry(re1, true);
		rt.addRouteRequestEntry(re2, true);
		try {
			assertEquals(re1,rt.getNextRouteToExpire());
		} catch (NoSuchRouteException e) {
			assertTrue(false);
		}
		rt.removeEntry(re1.getSourceAddress(), re1.getBroadcastID());
		rt.addRouteRequestEntry(re1, false);
		try {
			assertEquals(re2,rt.getNextRouteToExpire());
		} catch (NoSuchRouteException e) {
			assertTrue(false);
		}		
	}
	
	@Test public void setRouteRequestTimerTest(){
		assertTrue(rt.isEmpty());
		try {
			rt.setRouteRequestTimer(1, 1);
			assertTrue(false);
		} catch (NoSuchRouteException e) {
			assertTrue(true);
		}
		rt.addRouteRequestEntry(re1, false);
		assertTrue(rt.isEmpty());
		try {
			rt.setRouteRequestTimer(re1.getSourceAddress(), re1.getDestinationAddress());
			assertTrue(true);
		} catch (NoSuchRouteException e) {
			assertTrue(false);
		}
		assertFalse(rt.isEmpty());
	}
}
