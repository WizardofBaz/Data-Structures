package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {
	
	String expr;
	

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
	public Expression(String expr) {
		this.expr=expr;
	}
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	StringTokenizer tkn = new StringTokenizer (expr,delims); //fix
    	while(tkn.hasMoreTokens()) {
    		String current = tkn.nextToken();
    		int index = expr.indexOf(current);
    		
    		if (Character.isDigit(current.charAt(0))) {
    			continue;
    		}
    		if (index + current.length() < expr.length()) {
    			if (expr.charAt(index + current.length())== '[') {
    				Array a = new Array(current);
    				boolean exists = false;
    				for (Array as: arrays) {
    					if (as.name.equals(current)) {
    						exists = true;
    					}
    				}
    				if (exists) {
    			} else {
    				arrays.add(a);
    			}
    		} else {
    		
    			System.out.println(current + " symbol");
    		}
    	}
    }
    }
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    //crazy stuff
    private static float superEval(String expr) { //155
    	Stack<Character> parenStk= new Stack<Character>();
    	int x = 0;
    	
    	for(int i = 0; i < expr.length();i++) {
    		char c = expr.charAt(i);
    		
    	//base case
    	if(expr.length()==1) {
    		if(Character.isDigit(expr.charAt(0)))
    			return Character.getNumericValue(expr.charAt(0));
    		if(!parenStk.isEmpty()) {
    			if(c == '}' && parenStk.peek() == '{') {
    				char s = parenStk.peek();
    				parenStk.pop();
    				return 1;
    			}
    			if (c== ']' && parenStk.peek() == '[') {
    				parenStk.pop();
    				return 1;
    			}
    			
    			if (c == ')' && parenStk.peek() == '(') {
    				parenStk.pop();
    				return 1;
    			}
    		}
    	}
    	
    	if(Character.isAlphabetic(c)) { //BS CS
    		
    	}
    	
    	//find what x is equal to 
    	if (Character.isDigit(c)) {
    		if(i < expr.length()-1) {
    			if (!Character.isDigit(expr.charAt(i+1))) {
    				x = Character.getNumericValue(c);
    			}
    		}
    	}
    	
    	//checks stack
    	if (c == '[' || c == '{' || c == '(') {
    		parenStk.push(c);
    		return 0 + Float.parseFloat (expr.substring(1)); //fix this
    	}
    	
    	if (c == '}' && !parenStk.isEmpty()) {
    		char s = parenStk.peek();
    		parenStk.pop();
    	}
    	
    	if (c == ']' && !parenStk.isEmpty()) {
    		parenStk.pop();
    		
    	}
    	
    	if(c== ')' && !parenStk.isEmpty()) {
    		parenStk.pop();
    		
    	}
    	//check the operator
    	if (c == '+') {
    		return x + Float.parseFloat(expr.substring(1));
    	}
    	
    	if (c == '-') {
    		return x - Float.parseFloat(expr.substring(1));
    	}
    	
    	if (c == '/' ) {
    		return x / Float.parseFloat(expr.substring(1));
    	}
    	
    	if(c == '*') {
    		return x * Float.parseFloat(expr.substring(1));
    	}
    	
    	}
    	
    	boolean isAlpha = false;
    	
    	while (expr.contains("^")) {
    		int index = expr.indexOf('^');
    		int i = index;
    		String value = "";
    		int num = 0;
    		if (Character.isAlphabetic(expr.charAt(index - 1))) {
    			isAlpha = true;
    		}
    		while (i> 0) {
    			if(isAlpha) {
    				while(Character.isAlphabetic(expr.charAt(i))) {
    					value += expr.charAt(i);
    					i--;
    				}
    				
    			} else {
    				while(Character.isDigit(expr.charAt(i))) {
    					num += Character.getNumericValue(expr.charAt(i)) * Math.pow(10, index-i-1) + num;
    					i--;
    				}
    			}
    			i--;
    		}
    	}
    	
    	while (expr.contains("*")) {
    		int index = expr.indexOf('*');
    		int i = index;
    		String value = "";
    		int num = 0;
    		if(Character.isAlphabetic(expr.charAt(index -1))) {
    			isAlpha = true;
    		}
    		while(i >0) {
    			if(isAlpha) {
    				while(Character.isAlphabetic(expr.charAt(i))) {
    					value += expr.charAt(i);
    					i--;
    				}
    			} else {
    				while (Character.isDigit(expr.charAt(i))) {
    					num += Character.getNumericValue(expr.charAt(i)) * Math.pow(10, index-i-1) + num;
    					i--;
    				}
    			}
    			i--;
    		}
    	}
    	
    	while (expr.contains("/")) {
    		int index = expr.indexOf('/');
    		int i = index;
    		String value = "";
    		int num = 0;
    		if(Character.isAlphabetic(expr.charAt(index - 1))) {
    			isAlpha = true;
    		}
    		while(i >0) {
    			if(isAlpha) {
    				while(Character.isAlphabetic(expr.charAt(i))) {
    					value += expr.charAt(i);
    					i--;
    				}
    			} else {
    				while(Character.isDigit(expr.charAt(i))) {
    					num+= Character.getNumericValue(expr.charAt(i)) * Math.pow(10, index-i-1) + num;
    					i--;
    				}
    			}
    			i--;
    		}
    	}
    	while (expr.contains("+")) {
    		int index = expr.indexOf('+');
    		int i = index;
    		String value = "";
    		int num = 0;
    		if (Character.isAlphabetic(expr.charAt(index - 1))) {
    			isAlpha = true;
    		}
    		while (i > 0) {
    			if (isAlpha) {
    				while (Character.isAlphabetic(expr.charAt(i))) {
    					value += expr.charAt(i);
    					i--;
    				}
    			} else {
    				while(Character.isDigit(expr.charAt(i))) {
    					num += Character.getNumericValue(expr.charAt(i)) * Math.pow(10, index-i-1) + num;
    					i--;
    				}
    			}
    			i--;
    		}
    	}
    	while (expr.contains("-")) { 
    		int index = expr.indexOf('-');
    		int i = index;
    		String value = "";
    		int num = 0;
    		if(Character.isAlphabetic(expr.charAt(index - 1))) {
    			isAlpha = true;
    		}
    		while(i>0) {
    			if(isAlpha) {
    				System.out.println(i);
    				while(Character.isAlphabetic(expr.charAt(i))) {
    					value += expr.charAt(i); //359
    					i--;
    				}
    			} else {
    				while (Character.isDigit(expr.charAt(i))) {
    					num += Character.getNumericValue(expr.charAt(i)) * Math.pow(10, index-i-1) + num;
    					i--;
    				}
    			}
    			i--;
    		}
    	}
    	return 0;
    }
    
/*private float Eval(String expr) {
		if (expr.contains("+")) {
			return value + 
		}
	}/*
	
	
    //find num value
	//use operaor and ret value
    
  
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    public static float 
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	return superEval(expr);
}
}
