package com.cse01.ds;

import java.util.Stack;

import android.util.Log;

public class ExpressionEvaluation {
	
	static Stack<Float> mValues = new Stack<Float>();
	
	static Stack<Character> mOps = new Stack<Character>();
	
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
				if(i>=len)
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
            if ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i]=='.')
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < len && ((tokens[i] >= '0' && tokens[i] <= '9')||tokens[i]=='.'))
                    sbuf.append(tokens[i++]);
                i--;
                mValues.push(Float.parseFloat(sbuf.toString()));
            }
 
            // Current token is an opening brace, push it to 'mOps'
            else if (tokens[i] == '(')
                mOps.push(tokens[i]);
            
         // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')')
            {
                while (mOps.peek() != '(')
                  mValues.push(applyOp(mOps.pop(), mValues.pop(), mValues.pop()));
                mOps.pop();
                if(flag==1 && count>0)
                {
                	float a = mValues.pop();
                	mValues.push(-1*a);
                	count--;
                	if(count==0)
                		flag=0;
                }
            }
 
            // Current token is an operator.
            else if (isOperator(tokens[i]))
            {
                // While top of 'mOps' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'mOps'
                // to top two elements in mValues stack
            	if(tokens[i]=='-' && tokens[i+1]=='(')
            	{
            		i++;
            		count++;
            		flag = 1;
            	}
            	else {
            		while (!mOps.empty() && hasPrecedence(tokens[i], mOps.peek()))
            			mValues.push(applyOp(mOps.pop(), mValues.pop(), mValues.pop()));
            		
            	}
                // Push current token to 'mOps'.
                mOps.push(tokens[i]);
            }
        }
		// Entire expression has been parsed at this point, apply remaining
        // mOps to remaining mValues
        while (!mOps.empty())
            mValues.push(applyOp(mOps.pop(), mValues.pop(), mValues.pop()));
       
        // Top of 'mValues' contains result, return it
        return mValues.pop();
        	
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
