package com.cse01.ds;

import java.util.Stack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SimulationExperiment2 extends Activity implements OnClickListener {

		private EditText Scr;
//		private float NumberBf; //number before pressing operator
//		private float Operand1;
//		private float Operand2;
//		private String Operator;
//		private String Opr;
		Button btnAdd;
		Button btnSub;
		Button btnMul;
		Button btnDiv;
		Button btnC;
		Button btnEq;
		Button btnOpen;
		Button btnClose;
		Button btnPower;
		Button btnPercentage;
		Button btnOneByX;
		Button btnPlusMinus;
		Button btnBackspace;
		Button btnZero; Button btnOne; Button btnTwo; Button btnThree; 
		Button btnFour; Button btnFive;
		Button btnSix; Button btnSeven;
		Button btnEight; Button btnNine;
		String number="";
		String numb;
		Stack<Character> ops = new Stack<Character>();
		
		Stack<Float> values = new Stack<Float>();
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simulation_experiment2);
		Scr = (EditText) findViewById(R.id.calcScreen);
		Scr.setEnabled(false);
		
		btnAdd = (Button) findViewById(R.id.btnPlus);
		btnSub = (Button) findViewById(R.id.btnSubtract);
		btnMul = (Button) findViewById(R.id.btnMultiply);
		btnDiv = (Button) findViewById(R.id.btnDivide);
		btnEq = (Button) findViewById(R.id.btnEqualTo);
		btnC = (Button) findViewById(R.id.btnC);
		btnOneByX = (Button) findViewById(R.id.btnOneByX);
		btnOpen = (Button) findViewById(R.id.btnOpen);
		btnClose = (Button) findViewById(R.id.btnClose);
		btnPower = (Button) findViewById(R.id.btnExponent);
		btnPercentage = (Button) findViewById(R.id.btnPercentage);
		btnPlusMinus = (Button) findViewById(R.id.btnPlusMinus);
		btnBackspace = (Button) findViewById(R.id.BtnBackspace);
		btnZero = (Button) findViewById(R.id.btnZero);
		btnOne = (Button) findViewById(R.id.btnOne);
		btnTwo = (Button) findViewById(R.id.btnTwo);
		btnThree = (Button) findViewById(R.id.btnThree);
		btnFour = (Button) findViewById(R.id.btnFour);
		btnFive = (Button) findViewById(R.id.btnFive);
		btnSix = (Button) findViewById(R.id.btnSix);
		btnSeven = (Button) findViewById(R.id.btnSeven);
		btnEight = (Button) findViewById(R.id.btnEight);
		btnNine = (Button) findViewById(R.id.btnNine);
		
		
		btnAdd.setOnClickListener(this);
		btnSub.setOnClickListener(this);
		btnMul.setOnClickListener(this);
		btnDiv.setOnClickListener(this);
		btnC.setOnClickListener(this);
		btnEq.setOnClickListener(this);
		btnOneByX.setOnClickListener(this);
		btnOpen.setOnClickListener(this);
		btnClose.setOnClickListener(this);
		btnPower.setOnClickListener(this);
		btnPercentage.setOnClickListener(this);
		btnPlusMinus.setOnClickListener(this);
		btnBackspace.setOnClickListener(this);
		btnZero.setOnClickListener(this);
		btnOne.setOnClickListener(this);
		btnTwo.setOnClickListener(this);
		btnThree.setOnClickListener(this);
		btnFour.setOnClickListener(this);
		btnFive.setOnClickListener(this);
		btnSix.setOnClickListener(this);
		btnSeven.setOnClickListener(this);
		btnEight.setOnClickListener(this);
		btnNine.setOnClickListener(this);
		
	}
	
	public void mResult(){
		String Expression = Scr.getText().toString();
		float result=0;
		char[] tokens = Expression.toCharArray();
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
    	Scr.setText(String.valueOf(result));
		
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

	public void onClick(View v){
			Log.i("Click", "Click");
			switch(v.getId()){
			case R.id.btnC:	 //clear screen
				Scr.setText("0");
//				NumberBf = 0;
	//			Operator = "";
				break;
				
			case R.id.btnPlusMinus:
				String ScrCrntPlusMinus = Scr.getText().toString();
				ScrCrntPlusMinus = "-(" + ScrCrntPlusMinus + ")";
				Scr.setText(ScrCrntPlusMinus);
				break;
		
			case R.id.btnOneByX:
				String ScrCrntByX = Scr.getText().toString();
				ScrCrntByX = "1/(" + ScrCrntByX + ")";
				Scr.setText(ScrCrntByX);
				break;
			
			case R.id.BtnBackspace:
				String ScrCrntBackspace = Scr.getText().toString();
				ScrCrntBackspace = ScrCrntBackspace.substring(0, ScrCrntBackspace.length()-1);
				Scr.setText(ScrCrntBackspace);
				break;
				
			case R.id.btnEqualTo:
				mResult();
				break;
			
			default:
				numb = ((Button) v).getText().toString();
				String ScrCurrent = Scr.getText().toString();
				ScrCurrent += numb;
				Scr.setText(ScrCurrent);
				break;
					
			}
			
		}
}
