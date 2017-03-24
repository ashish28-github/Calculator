package com.synopsys.calculator;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 * @author Ashish Kumar
 *
 */
public class Utilities {


	/**
	 * This function extracts the operands from the expression and returns them into array.
	 * If the operand is an integer,it is return otherwise call recursive action on the element.
	 * such as if input is add(1,2), then it will return array with elements as 1 and 2.
	 * if input is add(1, mult(2, 3)), it will return array with elements 1 and mult(2,3).Then mult(2,3) is called
	 * recursively and result is passed to add.
	 * let expression returns array of 3 elements and the same logic is applied.
	 * @param action
	 * @param operands
	 * @return
	 */
	public static String[] getElements(String action, String operands){
		String[] elements = null;
		if(!Constants.LET.equalsIgnoreCase(action)){
			elements = new String[2];
			int firstIndex = operands.indexOf("(");
			int lastIndex = operands.lastIndexOf(")");
			String element = operands.substring(firstIndex+1, lastIndex);
			int start = element.indexOf("(");
			int commaSep = element.indexOf(Constants.SEPERATOR);
			if((start < 0 && commaSep > 0) || (commaSep < start)){
				String firstElement = element.substring(0,commaSep).trim();
				if(StringUtils.isNumeric(firstElement)){
					elements[0] = element.substring(0, commaSep);
					elements[1] = element.substring(commaSep+1);
				}
			}else if(start >=0){
				int breakIndex = findBreakingIndex(element, start);
				elements[0] = element.substring(0, breakIndex+1);
				elements[1] = element.substring(breakIndex+2);
			}
		}else{
			elements = new String[3];
			int firstIndex = operands.indexOf("(");
			int lastIndex = operands.lastIndexOf(")");
			String element = operands.substring(firstIndex+1, lastIndex);
			int firstCommaSep = element.indexOf(Constants.SEPERATOR);
			elements[0] = element.substring(0, firstCommaSep);
			element = element.substring(firstCommaSep+1);
			int secondCommaSep = element.indexOf(Constants.SEPERATOR);
			int start = element.indexOf("(");
			if((start < 0 && secondCommaSep > 0) || (secondCommaSep < start)){
				String firstElement = element.substring(0,secondCommaSep).trim();
				if(StringUtils.isNumeric(firstElement)){
					elements[1] = element.substring(0, secondCommaSep);
					elements[2] = element.substring(secondCommaSep+1);
				}
			}else if(start >=0){
				int breakIndex = findBreakingIndex(element, start);
				elements[1] = element.substring(0, breakIndex+1);
				elements[2] = element.substring(breakIndex+2);
			}
		}
		return elements;
	}

	/**
	 * This method is used to determine how to find the element which is an expression.
	 * for example if input is mult(add(2, 2), div(9, 3)), 
	 * then it returns index of ")" which appears just after second 2.
	 * @param expression
	 * @param start
	 * @return
	 */
	public static int findBreakingIndex(String expression, int start){
		int breakIndex = 0;
		int begin=1;
		char[] arr = expression.toCharArray();
		for(int i=start+1;i<arr.length;i++){
			if(arr[i] == '('){
				begin++;
			}else if(arr[i] == ')'){
				begin--;
			}
			if(begin == 0){
				breakIndex = i;
				break;
			}
		}
		return breakIndex;
	}
}
