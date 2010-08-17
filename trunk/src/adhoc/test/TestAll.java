package adhoc.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	ForwardRouteEntryTest.class,
	ForwardTableTest.class,
	RouteRequestTableTest.class,
	SequenceNumberTests.class
})

public class TestAll {

}
