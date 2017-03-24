# Calculator
Java Homework:
Functional Requirements
Write a calculator program in Java that evaluates expressions in a very simple integer expression language. The program takes an input on the command line, computes the result, and prints it to the console. Example
Input	                                                                        Output
add(1, 2)                                                                     	3
add(1, mult(2, 3))	                                                            7
mult(add(2, 2), div(9, 3))	                                                    12
let(a, 5, add(a, a))	                                                          10
let(a, 5, let(b, mult(a, 10), add(b, a)))	                                      55
let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))	                          40

Solution:

Steps to run :
1. Download the Calculator project.
2. Run mvn clean install from the directory where pom file is located.
3. It will produce calculator-0.0.1-SNAPSHOT.jar under target directory.
4. Go inside targer directory or give the full path of this jar while running below command.
5. Run java -jar  -Dlog4j.LEVEL=INFO calculator-0.0.1-SNAPSHOT.jar "mult(add(2, 2), div(9, 3))"

Note : 
1.Environment variable log4j.LEVEL is used to set logging level. Possible values are INFO,DEBUG and ERROR
2. Expression that needs to be evaluated should be inside "".


