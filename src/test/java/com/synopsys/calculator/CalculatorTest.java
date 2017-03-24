package com.synopsys.calculator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimpleAdd() {
		String expression = "add(1,2)";
		assertEquals(3, Calculator.performAction(expression));
	}
	
	@Test
	public void testSimpleAddWithExpression() {
		String expression = "add(1, mult(2, 3))";
		assertEquals(7, Calculator.performAction(expression));
	}
	
	@Test
	public void testMultWithExpression() {
		String expression = "mult(add(2, 2), div(9, 3))";
		assertEquals(12, Calculator.performAction(expression));
	}
	
	@Test
	public void testLetWithExpression() {
		String expression = "let(a, 5, let(b, mult(a, 10), add(b, a)))";
		assertEquals(55, Calculator.performAction(expression));
	}
	

}

