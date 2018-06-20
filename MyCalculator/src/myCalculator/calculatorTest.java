package myCalculator;

import static org.junit.Assert.*;

import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class calculatorTest {
	 Calculator calculator=new Calculator();
	 JTextField resultText = calculator.resultText;
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testHandleBackspace(){
		resultText.setText("21");
		calculator.handleBackspace();
		assertEquals("2",resultText.getText());	
		
	}
	@Test
	public void testHandleNumber(){
		resultText.setText("21");
		calculator.firstDigit = false;
		calculator.handleNumber("3");
		assertEquals("213",resultText.getText());	
		
	}
	@Test
	public void testHandleC() {
		
		assertEquals("0",resultText.getText());
	}
	
	@Test
	public void testHandleOperatorDev(){
		
		
		calculator.resultNum = 4 ;
		resultText.setText("2");
		calculator.handleOperator("/");
		assertEquals("2",resultText.getText());	
	}
	@Test
	public void testHandleOperatorLog(){
		
		
		calculator.resultNum = 1 ;
		
		calculator.handleOperator("ln");
		assertEquals("0",resultText.getText());	
	}
	@Test
	public void testHandleOperatorMul(){
		
		
		calculator.resultNum = 1 ;
		resultText.setText("2");
		calculator.handleOperator("*");
		assertEquals("2",resultText.getText());	
	}
	
	
	
	
	}
	
