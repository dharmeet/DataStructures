package edu.vlabs.ds.expr2;

import java.util.Stack;

import android.util.Log;

/**
 * 
 * 
 *
 */

/**
 * @author Dharmeet
 * Class to validate and evaluate Expression, according to following regular expression
 * E --> E + T | E-T | T
 * T --> T * F | T/F | F
 * F --> (E) | n
 * E - Expression, T - Term and F - Factor
 */

public class ExpressionEvaluation {
	
	static Stack<Float> sValues = new Stack<Float>();
	
	String mExpr;
	
	static Stack<Character> sOps = new Stack<Character>();
	
	public float evalExpr() {
		
		float mTermValue=evalTerm();
		
		return mTermValue;
	}
	
	public float evalTerm() {
		float factor=0;
		return factor;
	}
	
	public float evalFactor() {
		int i=0;
		float number = 0;
		if((this.mExpr.charAt(i)>='0' && this.mExpr.charAt(i)<='9') || this.mExpr.charAt(i)=='.')
		{
			while((this.mExpr.charAt(i)>='0' && this.mExpr.charAt(i)<='9') || this.mExpr.charAt(i)=='.')
				i++;
			String numb = mExpr.substring(0, i-1);
			number = Float.parseFloat(numb);
			this.mExpr = this.mExpr.substring(i);
			return number;
		}
		else if(this.mExpr.charAt(i)=='(')
		{
			this.mExpr = this.mExpr.substring(i+1);
			number = this.evalExpr();
			if(this.mExpr.charAt(0)==')')
				return number;
			
		}
		return number;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static boolean isOperator(char op)
	{
		if(op=='+' || op=='-' || op=='*' || op=='/' || op=='^' ||
				op=='%')
			return true;
		else
			return false;
	}
	
	public static boolean isDigit(char digit)
	{
		if(digit>='0' && digit<='9')
			return true;
		else
			return false;
	}
	
	public static boolean ValidateExpression(String expression) {
		char[] tokens = expression.toCharArray();
		int len = tokens.length;
		int mBracketCount=0;
		for(int i=0; i<len; i++)
		{
			Log.i(Character.toString(tokens[i]), Character.toString(tokens[i]));
			if(isOperator(tokens[i]))
			{
				if(i>=len-1)
					return false;
				else if(!(tokens[i+1]=='(' || isDigit(tokens[i+1])))
				{
					return false;
				}
			}
			else if(isDigit(tokens[i]))
			{
				if(i<len-1)
				{
					if(!(isDigit(tokens[i+1]) || isOperator(tokens[i+1]) 
							|| tokens[i+1]=='.' || tokens[i+1]==')')) {
						return false;
					}
				}
			}
			else if(tokens[i]=='.')
			{
				if((i==len-1) && !(isDigit(tokens[i+1]))){
					return false;
				}
				else if(i<len-1)
				{
					if(!(isDigit(tokens[i+1]) || isOperator(tokens[i+1]) 
							|| tokens[i+1]==')')){
						return false;
					}
				}
			}
			else if(tokens[i]=='(')
			{
				if(i==len-1)
					return false;
				else if(!(isDigit(tokens[i+1]) || tokens[i+1]=='.'))
					return false;
				mBracketCount++;
			}
			else if(tokens[i]==')')
			{
				mBracketCount--;
			}
			
		}
		if(mBracketCount!=0)
			return false;
		else
			return true;
	}
	
	public static float CalculateExpression(String expression){
		//String Expression = Scr.getText().toString();
		
		char[] tokens = expression.toCharArray();
		int flag=0;
		int count=0;
		int len = tokens.length;
		for (int i = 0; i < len; i++)
        {
			Log.i(Character.toString(tokens[i]), Character.toString(tokens[i]));
             // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;
 
            // Current token is a number, push it to stack for numbers
            if (isDigit(tokens[i]) || tokens[i]=='.')
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while ( i < len && ( isDigit(tokens[i]) || tokens[i]=='.') )
                    sbuf.append(tokens[i++]);
                i--;
                sValues.push(Float.parseFloat(sbuf.toString()));
            }
 
            // Current token is an opening brace, push it to 'sOps'
            else if (tokens[i] == '(')
                sOps.push(tokens[i]);
            
         // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')')
            {
                while (sOps.peek() != '(')
                  sValues.push(applyOp(sOps.pop(), sValues.pop(), sValues.pop()));
                sOps.pop();
                if(flag==1 && count>0)
                {
                	float a = sValues.pop();
                	sValues.push(-1*a);
                	count--;
                	if(count==0)
                		flag=0;
                }
            }
 
            // Current token is an operator.
            else if (isOperator(tokens[i]))
            {
                // While top of 'sOps' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'sOps'
                // to top two elements in sValues stack
            	if(tokens[i]=='-' && tokens[i+1]=='(')
            	{
            		i++;
            		count++;
            		flag = 1;
            	}
            	else {
            		while (!sOps.empty() && hasPrecedence(tokens[i], sOps.peek()))
            			sValues.push(applyOp(sOps.pop(), sValues.pop(), sValues.pop()));
            		
            	}
                // Push current token to 'sOps'.
                sOps.push(tokens[i]);
            }
        }
		// Entire expression has been parsed at this point, apply remaining
        // sOps to remaining sValues
        while (!sOps.empty())
            sValues.push(applyOp(sOps.pop(), sValues.pop(), sValues.pop()));
       
        // Top of 'sValues' contains result, return it
        return sValues.pop();
        	
	}
	public static boolean hasPrecedence(char op1, char op2)
	{
		if (op2 == '(' || op2 == ')')
			return false;
		if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
			return false;
		if((op1 == '%' || op1 == '^') && (op2=='+' || op2=='-' || op2=='*' || op2=='/'))
			return false;
		else
			return true;
	}

	public static float applyOp(char op, float b, float a)
	{
		switch (op)
		{
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		case '/':
			if (b == 0)
				return 0;
			else
            //throw new
            //UnsupportedOperationException("Cannot divide by zero");
				return a / b;
		case '%':
			return a%b;
		case '^':
			return (float)Math.pow((double)a, (double)b);
		}
		return 0;

	
	}
}
