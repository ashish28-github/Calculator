package com.synopsys.calculator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * This class will take an expression as input and return the result.
 * Command line arguments should be as follows.
 * 
 * java -jar -Dlog4j.LEVEL=INFO calculator-0.0.1-SNAPSHOT.jar "add(1, mult(2, 3))"
 * 
 * -Dlog4j.LEVEL - To set up the logging level. Possible values INFO,DEBUG,ERROR
 * 
 *  calculator-0.0.1-SNAPSHOT.jar - Name of the artifact that is produced after the Maven build. 
 *  								Please provide full location if running from different location where this is produced
 * "add(1, mult(2, 3))" is the expression you want to evaluate
 *
 */
public class Calculator 
{
	private static final Logger logger = Logger.getLogger(Calculator.class);
	public static void main( String[] args )
	{
		initializeLogger();
		if(args != null && args.length == 1){
			String input = args[0];
			if("".equalsIgnoreCase(input)){
				logger.error("Invalid expression. Please enter a valid expression");
			}else{
				System.out.println("Output : "+performAction(input));
			}
		}else{
			logger.error("Please provide correct input.");
		}
	}

	public static int performAction(String input){
		try{
			if(logger.isDebugEnabled()){
				logger.debug("Expression : "+input);
			}
			input = input.trim();
			if(StringUtils.isNumeric(input)){
				return new Integer(input);
			}
			String action = input.substring(0, 3);
			String[] elements = Utilities.getElements(action, input);
			if(logger.isDebugEnabled()){
				logger.debug("Action : "+action);
			}
			if(Constants.ADD.equalsIgnoreCase(action)){
				return performAction(elements[0]) + performAction(elements[1]);
			}else if(Constants.SUBSTRACT.equalsIgnoreCase(action)){
				return performAction(elements[0]) - performAction(elements[1]);
			}else if(Constants.MULTYPLY.equalsIgnoreCase(action)){
				return performAction(elements[0]) * performAction(elements[1]);
			}else if(Constants.DIVISION.equalsIgnoreCase(action)){
				if(performAction(elements[1]) != 0){
					return performAction(elements[0]) / performAction(elements[1]);
				}else{
					logger.error("Division by Zero(0) not allowed.");
					throw new Exception("Division by Zero(0) not allowed.");
				}
			}else if(Constants.LET.equalsIgnoreCase(action)){
				String variableName = elements[0].trim();
				if(!StringUtils.isAlpha(variableName)){
					logger.error("Invalid expression : "+variableName);
					throw new Exception("Invalid expression : "+variableName);
				}
				String variableValue = elements[1].trim();
				String regEx = "\\b"+variableName+"\\b";
				if(StringUtils.isNumeric(variableValue)){
					String finalExpression = elements[2];
					finalExpression = finalExpression.replaceAll(regEx, variableValue);
					return performAction(finalExpression);
				}else{
					variableValue = performAction(elements[1])+"";
					String finalExpression = elements[2];
					finalExpression = finalExpression.replaceAll(regEx, variableValue);
					return performAction(finalExpression);
				}
			}else{
				logger.error("Invalid expression : "+input);
				throw new Exception("Invalid expression : "+input);
			}
		}catch(Exception e){
			logger.error("Error while evaluating expession : "+e.getMessage());
			System.exit(0);
		}
		return 0;
	}

	private static void initializeLogger() {
		if(Constants.LEVEL_INFO.equalsIgnoreCase(System.getProperty("log4j.LEVEL"))) {
			logger.setLevel(Level.INFO);
		}else if (Constants.LEVEL_DEBUG.equalsIgnoreCase(System.getProperty("log4j.LEVEL"))) {
			logger.setLevel(Level.DEBUG);
		}else if (Constants.LEVEL_ERROR.equalsIgnoreCase(System.getProperty("log4j.LEVEL"))) {
			logger.setLevel(Level.ERROR);
		}else{
			logger.setLevel(Level.INFO); // Setting default level to INFO
		}
		//Adding console Appender
		ConsoleAppender console = new ConsoleAppender(); 
		//configure the logging format
		String PATTERN = "%d [%p|%c] %m%n";
		console.setLayout(new PatternLayout(PATTERN)); 
		console.activateOptions();
		//add appender to Logger
		logger.addAppender(console);
	}
}

