package adhoc.test;


import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adhoc.aodv.Constants;
import adhoc.aodv.Receiver;

public class SequenceNumberTests {
	int min,max, interval;

	@Before
	public void setUp() throws Exception {
		min = Constants.FIRST_SEQUENCE_NUMBER;
		max = Constants.MAX_SEQUENCE_NUMBER;
		interval = Constants.SEQUENCE_NUMBER_INTERVAL;
	}

	@After
	public void tearDown() throws Exception {
		min = 0; max = 0;
	}
	
	
	/**
	 * Testing comparison of sequence numbers, and ensuring that rollover is considered correctly
	 */
	@Test public void getMaximumSeqNumTest(){
		assertEquals(min+1,Receiver.getMaximumSeqNum(min+1, min));
		assertEquals(min+1,Receiver.getMaximumSeqNum(min, min+1));
		
		//case where one of the two numbers has just experienced rollover
		assertEquals(min,Receiver.getMaximumSeqNum(min, max));
	}

}
