package edu.vlabs.ds.expr2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * @author Dharmeet
 * Class to validate and evaluate Expression, according to following regular expression
 * E --> E + T | E-T | T
 * T --> T * F | T/F | F
 * F --> (E) | n
 * E - Expression, T - Term and F - Factor
 */

public class ExpressionEvaluation {
		
	public String mExpr;
		
	ExpressionEvaluation(String mExpr) {
        this.mExpr = mExpr;
    }
	
    public float evalExpr() {
    	
    	Log.i("Entering evalExpr() with expression length", String.valueOf(this.mExpr.length()));
    	float mTermValue=this.evalTerm();
				
		if (this.mExpr.length() > 0  &&  this.mExpr.charAt(0) == '+') {
			this.mExpr = this.mExpr.substring(1);
			return mTermValue + this.evalExpr();
		}
		
		else if (this.mExpr.length() > 0  &&  this.mExpr.charAt(0) == '-') {
			this.mExpr = this.mExpr.substring(1);
			mTermValue = mTermValue + (-1)*this.evalTerm();
			if (this.mExpr.length() > 0) {
				return mTermValue + this.evalExpr();
			}
			
			else
				return mTermValue;
		}
		
		else
		    return mTermValue;
	}
	
	public float evalTerm() {
        
		Log.i("Entering evalTerm() with expression length", String.valueOf(this.mExpr.length()));
		float mFactor= this.evalFactor();
				
		if (this.mExpr.length() > 0  &&  this.mExpr.charAt(0) == '*') {
			this.mExpr = this.mExpr.substring(1);
			return mFactor * this.evalFactor(); 
		}
		
		else if (this.mExpr.length() > 0  &&  this.mExpr.charAt(0) == '/') {
			this.mExpr = this.mExpr.substring(1);
			return mFactor / this.evalFactor();
		}
		
		else if (this.mExpr.length() > 0  &&  this.mExpr.charAt(0) == '%') {
			this.mExpr = this.mExpr.substring(1);
			return mFactor % this.evalFactor();
		}
		
		else if (this.mExpr.length() > 0  &&  this.mExpr.charAt(0) == '^') {
			this.mExpr = this.mExpr.substring(1);
			return (float) Math.pow((double)mFactor, (double)this.evalFactor());
		}
		else
			return mFactor;
	}
	
	public float evalFactor() {
		
		
		Log.i("Entering evalFactor() with the expression length", String.valueOf(this.mExpr.length())); 
		String numb = "";
		float mNumber = Float.MIN_VALUE;
		
		
		if (this.mExpr.length() != 0  &&  (this.mExpr.charAt(0) >= '0'  &&  this.mExpr.charAt(0) <= '9')  ||  this.mExpr.charAt(0) == '.') {
			Pattern pNumber = Pattern.compile("[0-9]*\\.?[0-9]+");
			Matcher mNumb = pNumber.matcher(this.mExpr);
			if (mNumb.find()) {
				numb = mNumb.group();
				mNumber = Float.parseFloat(numb);
			}
			
			if (this.mExpr.length() == numb.length()) {
				this.mExpr = "";
			}
			
			else {
			    this.mExpr = this.mExpr.substring(numb.length());
			}
			
			return mNumber;
		}
		else if (this.mExpr.charAt(0) == '(') {
			
			this.mExpr = this.mExpr.substring(1);
			mNumber = this.evalExpr();
			
			if (this.mExpr.charAt(0) == ')') {
				
				this.mExpr = this.mExpr.substring(1);
				return mNumber;
			}
			
		}
		return mNumber;
	}
}