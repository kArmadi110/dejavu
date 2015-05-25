package hu.unideb.inf.dejavu;

import static org.junit.Assert.*;
import org.junit.Test;

public class RandTest {

	@Test
	public void testGetRandomPos(){
		Rand random= new Rand(4);
		
		assertEquals(16,random.getrandomPos().size());
		Rand random1= new Rand(5);
		assertEquals(25,random1.getrandomPos().size());
	}
}
