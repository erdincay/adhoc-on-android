package adhoc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adhoc.aodv.exception.AodvException;
import adhoc.aodv.exception.NoSuchRouteException;
import adhoc.aodv.exception.RouteNotValidException;
import adhoc.aodv.routes.ForwardRouteEntry;
import adhoc.aodv.routes.ForwardRouteTable;

public class ForwardTableTest {
	ForwardRouteTable ft;
	ForwardRouteEntry fe1, fe2, fe3, fe4, fe5;
	ArrayList<Integer> precursors;
	
	@Before
	public void setUp() throws Exception {
		ft = new ForwardRouteTable();
		
		precursors = new ArrayList<Integer>();
		precursors.add(4);
		
		fe1 = new ForwardRouteEntry(0, 0, 1, 1, precursors);
		fe2 = new ForwardRouteEntry(1, 0, 1, 1, precursors);
		
		fe3 = new ForwardRouteEntry(0, 1, 1, 1, precursors);
		fe4 = new ForwardRouteEntry(0, 1, 2, 1, precursors);
		fe5 = new ForwardRouteEntry(0, 1, 2, 4, precursors);
	}

	@After
	public void tearDown() throws Exception {
		ft = null;
		fe1 = null; fe2= null; fe3 = null; fe4= null; fe5 = null;
	}
	
	/**
	 * Testing an empty forward table
	 */
	@Test public void isForwardTableEmpty(){
		assertTrue(ft.isEmpty());
		ft.addForwardRouteEntry(fe1);
		assertFalse(ft.isEmpty());
	}
	
	/**
	 * Testing that the same entry is not accepted twice
	 */
	@Test public void addSameEntry(){
		assertTrue(ft.addForwardRouteEntry(fe1));
		assertFalse(ft.addForwardRouteEntry(fe1));
	}
	
	/**
	 * Testing that a forward route is uniquely defined by the destination address parameter
	 */
	@Test public void uniqueForwardEntryTest(){
		assertTrue(ft.addForwardRouteEntry(fe1));
		assertTrue(ft.addForwardRouteEntry(fe2));
		
		assertFalse(ft.addForwardRouteEntry(fe3));
		assertFalse(ft.addForwardRouteEntry(fe4));
		assertFalse(ft.addForwardRouteEntry(fe5));
	}
	
	/**
	 * Testing getLastKnownDestSeqNumb method of the forward table
	 */
	@Test public void LastKnownDestSeqNumbTest(){
		try {
			ft.getLastKnownDestSeqNumber(0);
			assertTrue(false);
		} catch (NoSuchRouteException e) {
			assertTrue(true);
		}
		ft.addForwardRouteEntry(fe1);
		try {
			assertEquals(1, ft.getLastKnownDestSeqNumber(0));
		} catch (NoSuchRouteException e) {
			assertTrue(false);
		}
	}

	/**
	 * Testing the getForwardRouteEntry() method of the forward table
	 */
	@Test public void getForwardRouteEntryTest(){
		try {
			ft.getForwardRouteEntry(0);
			assertTrue(false);
		} catch (NoSuchRouteException e) {
			assertTrue(true);
		} catch (RouteNotValidException e) {
			assertTrue(false);
		}
		ft.addForwardRouteEntry(fe1);
		try {
			assertEquals(ft.getForwardRouteEntry(0),fe1);
		} catch (AodvException e) {
			assertTrue(false);
		}
	}
	
	/**
	 * Testing getPrecursors method of forward table
	 */
	@Test public void getPrecursorsTest(){
		assertEquals(true,ft.getPrecursors(0).isEmpty());
		ft.addForwardRouteEntry(fe1);
		assertEquals(false, ft.getPrecursors(0).isEmpty());
		assertTrue(ft.getPrecursors(0).contains(4));
	}
	
	@Test public void getNextRouteToExpire(){
		ft.addForwardRouteEntry(fe1);
		try {
			assertEquals(fe1,ft.getNextRouteToExpire());
		} catch (NoSuchRouteException e) {
			assertTrue(false);
		}
	}
	
	@Test public void getNextRouteToExpire2(){
		ft.addForwardRouteEntry(fe1);
		fe1.setValid(false);
		try {
			assertEquals(fe1,ft.getNextRouteToExpire());
		} catch (NoSuchRouteException e) {
			assertTrue(false);
		}
	}
	
	@Test public void getNextRouteToExpire3(){
		ft.addForwardRouteEntry(fe1);
		ft.addForwardRouteEntry(fe2);
		try {
			assertEquals(fe1,ft.getNextRouteToExpire());
		} catch (NoSuchRouteException e) {
			assertTrue(false);
		}
		ft.removeEntry(fe1.getDestinationAddress());
		try {
			assertEquals(fe2,ft.getNextRouteToExpire());
		} catch (NoSuchRouteException e) {
			assertTrue(false);
		}
	}
}
