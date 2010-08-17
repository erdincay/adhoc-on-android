package adhoc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adhoc.aodv.Constants;
import adhoc.aodv.exception.RouteNotValidException;
import adhoc.aodv.routes.ForwardRouteEntry;


public class ForwardRouteEntryTest {
	ForwardRouteEntry f1;

	@Before public void setUp() throws Exception {
		//create a valid forward route
		f1 = new ForwardRouteEntry(0, 0, 1, 1, new  ArrayList<Integer>());
	}

	@After public void tearDown() throws Exception {
		//set all fields to null
		f1 = null;
	}
	
	@Test public void createFaultyForwardEntryTest(){
		//dest address test
		try {
			new ForwardRouteEntry(Constants.MIN_VALID_NODE_ADDRESS -1, 1, 1, 1, new ArrayList<Integer>());
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		try {
			new ForwardRouteEntry(Constants.MAX_VALID_NODE_ADDRESS +1, 1, 1, 1, new ArrayList<Integer>());
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		//next-hop test	
		try {	
			new ForwardRouteEntry(1, Constants.MIN_VALID_NODE_ADDRESS -1, 1, 1, new ArrayList<Integer>());
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		try {		
			new ForwardRouteEntry(1, Constants.MAX_VALID_NODE_ADDRESS +1, 1, 1, new ArrayList<Integer>());
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		//seq numb test
		try {
			//Trying to create a forward route with an an Constants.UNKNOWN_SEQ_NUMB
			new ForwardRouteEntry(1, 1, 1, Constants.FIRST_SEQUENCE_NUMBER -1, new ArrayList<Integer>());
			assertTrue(true);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		try {
			new ForwardRouteEntry(1, 1, 1, Constants.MAX_SEQUENCE_NUMBER +1, new ArrayList<Integer>());
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
		//hop-count test
		try {
			new ForwardRouteEntry(1, 1, -1, 1, new ArrayList<Integer>());
			new ForwardRouteEntry(1, 1, 0, 1, new ArrayList<Integer>());
			new ForwardRouteEntry(1, 1, 1, 1, new ArrayList<Integer>());
			new ForwardRouteEntry(1, 1, Integer.MAX_VALUE, 1, new ArrayList<Integer>());
			new ForwardRouteEntry(1, 1, Integer.MIN_VALUE, 1, new ArrayList<Integer>());
			//the hop-count can thus be set to any integer
			assertTrue(true);
		} catch (RouteNotValidException e) {
			assertTrue(false);
		}
		//create route with with a 'null' instead of an ArrayList object
		try {
			new ForwardRouteEntry(1, 1, 1, 1, null);
			assertTrue(false);
		} catch (RouteNotValidException e) {
			assertEquals(e.getMessage(), "RouteEntry: invalid parameters given");
		}
	}
	
	@Test public void addPrecursorsToForwardEntryTest(){
		assertTrue(f1.getPrecursors().isEmpty());
		assertFalse(f1.addPrecursorAddress(Constants.MIN_VALID_NODE_ADDRESS-1));
		assertFalse(f1.addPrecursorAddress(Constants.MAX_VALID_NODE_ADDRESS+1));
		assertTrue(f1.getPrecursors().isEmpty());
		assertTrue(f1.addPrecursorAddress(0));
		assertFalse(f1.addPrecursorAddress(0));
	}
	
	@Test public void setSeqNumbTest(){
		assertFalse(f1.setSeqNum(Constants.FIRST_SEQUENCE_NUMBER-1));
		assertFalse(f1.setSeqNum(Constants.MAX_SEQUENCE_NUMBER+1));
	}
	
	@Test public void timeToLiveTest(){
		//forward entry: ensuring that TTL is a reasonable value
		assertTrue(f1.getAliveTimeLeft()>0 && f1.getAliveTimeLeft() <= System.currentTimeMillis()+Constants.ROUTE_ALIVETIME);
	}

	/**
	 * Testing if the entry returns a COPY of the list of precursors
	 * so that synchronization issues are evaded
	 */
	@Test public void getCopyPrecursorsTest(){
		f1.addPrecursorAddress(1);
		f1.addPrecursorAddress(2);
		ArrayList<Integer> copy = f1.getPrecursors();
		copy.remove(0);
		copy.remove(0);
		assertTrue(copy.isEmpty());
		assertFalse(f1.getPrecursors().isEmpty());
	}
}
