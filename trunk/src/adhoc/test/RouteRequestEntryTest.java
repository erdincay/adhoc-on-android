package adhoc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adhoc.aodv.Constants;
import adhoc.aodv.exception.RouteNotValidException;
import adhoc.aodv.routes.RouteRequestEntry;


public class RouteRequestEntryTest {
	RouteRequestEntry r1;
	
	@Before public void setUp() throws Exception {
		//creating a valid rreq entry
		r1 = new RouteRequestEntry(0, 0, 1, 1, 0);
	}

	@After public void tearDown() throws Exception {
		//set all fields to null
		r1 = null;
	}
	
	@Test public void createFaultyRouteRequest(){
		//dest address test
		try {
			new RouteRequestEntry(Constants.FIRST_BROADCAST_ID -1, 1, 1, 1, 1);
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		try {
			new RouteRequestEntry(Constants.MAX_BROADCAST_ID +1, 1, 1, 1, 1);
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		//source address test	
		try {
			new RouteRequestEntry(1, Constants.MIN_VALID_NODE_ADDRESS -1, 1, 1, 1);
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		try {
			new RouteRequestEntry(1, Constants.MAX_VALID_NODE_ADDRESS +1, 1, 1, 1);
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		//dest seq numb test
		try {
			new RouteRequestEntry(1, 1, Constants.FIRST_SEQUENCE_NUMBER -1, 1, 1);
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		try {
			new RouteRequestEntry(1, 1, Constants.MAX_SEQUENCE_NUMBER +1, 1, 1);
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		//hop-count test
		try {
			new RouteRequestEntry(0,0,1,-1,0);
			new RouteRequestEntry(0,0,1,0,0);
			new RouteRequestEntry(0,0,1,1,0);
			new RouteRequestEntry(0,0,1,Integer.MAX_VALUE,0);
			new RouteRequestEntry(0,0,1,Integer.MIN_VALUE,0);
			//the hop-count can be set to any integer
			assertTrue(true);
		} catch (RouteNotValidException e) {
			assertTrue(false);
		}
		//dest address test
		try {
			new RouteRequestEntry(1, 1, 1, 1, Constants.MIN_VALID_NODE_ADDRESS -1);
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		try {
			new RouteRequestEntry(1, 1, 1, 1, Constants.MAX_VALID_NODE_ADDRESS +1);
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
	}
	
	@Test public void timeToLiveTest(){
		//rreq entry: ensuring that TTL is a reasonable value
		assertTrue(r1.getAliveTimeLeft()>0 && r1.getAliveTimeLeft() <= System.currentTimeMillis()+Constants.PATH_DESCOVERY_TIME);
	}
	
	@Test public void setBroadcastIdTest(){
		assertFalse(r1.setBroadcastID(Constants.FIRST_BROADCAST_ID-1));
		assertFalse(r1.setBroadcastID(Constants.MAX_BROADCAST_ID+1));
	}
}
