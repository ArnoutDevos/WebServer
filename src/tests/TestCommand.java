package tests;

import static org.junit.Assert.*;
import initLine.Command;
import initLine.Head;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestCommand {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHead() {
		String[] args = new String[2];
		args[0] = "HEAD";
		args[1] = "/example.html";
		Command head = new Head(args);
		System.out.println(head.execute());
		assertTrue(true);
		
	}

}
