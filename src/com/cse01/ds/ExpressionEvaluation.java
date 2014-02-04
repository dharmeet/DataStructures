package com.cse01.ds;

import java.util.Stack;

import android.util.Log;

public class ExpressionEvaluation {
	
	Stack<Float> values = new Stack<Float>();
	
	Stack<Character> ops = new Stack<Character>();
	
	public float EvaluateExpression(String expression){
		//String Expression = Scr.getText().toString();
		float result=0;
		char[] tokens = expression.toCharArray();
		int flag=0;
		int count=0;
		for (int i = 0; i < tokens.length; i++)
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
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9')||tokens[i]=='.'))
                    sbuf.append(tokens[i++]);
                i--;
                values.push(Float.parseFloat(sbuf.toString()));
            }
 
            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);
            
         // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                  values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
                if(flag==1 && count>0)
                {
                	float a = values.pop();
                	values.push(-1*a);
                	count--;
                	if(count==0)
                		flag=0;
                }
            }
 
            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                     tokens[i] == '*' || tokens[i] == '/' || 
                     tokens[i] == '%' || tokens[i] == '^')
            {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
            	if(tokens[i]=='-' && tokens[i+1]=='(')
            	{
            		i++;
            		count++;
            		flag = 1;
            	}
            	else {
            		while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
            			values.push(applyOp(ops.pop(), values.pop(), values.pop()));
            		
            	}
                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }
		// Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
       
        // Top of 'values' contains result, return it
        result=values.pop();
    //	Scr.setText(String.valueOf(result));
        return result;
		
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
            throw new
            UnsupportedOperationException("Cannot divide by zero");
        return a / b;
		case '%':
			return a%b;
		case '^':
			return (float)Math.pow((double)a, (double)b);
    }
    return 0;

	
}

}
