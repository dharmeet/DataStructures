package edu.vlabs.ds.expr2;

import java.io.IOException;
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

public class ExpressionEvaluation extends IOException {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String mExpr;
		
	ExpressionEvaluation(String mExpr) {
        this.mExpr = mExpr;
    }
	
    public float evalExpr() throws IOException {
    	
    	Log.i("Entering evalExpr() with expression length", String.valueOf(this.mExpr.length()));
    	float mTermValue=this.evalTerm();
		
    	if (mTermValue==Float.MIN_VALUE)
			return mTermValue;
    	
    	else if (this.mExpr.length() > 0  &&  this.mExpr.charAt(0) == '+') {
			this.mExpr = this.mExpr.substring(1);
			//Float mTempTerm = this.evalTerm();
			
			//if (mTempTerm == Float.MIN_VALUE)
				//return mTempTerm;
			
			mTermValue = mTermValue + this.evalTerm();
			
			if (this.mExpr.length() > 0) {
				if (this.mExpr.charAt(0)=='-') {
					this.mExpr = this.mExpr.substring(1);
					return mTermValue - this.evalExpr();
				}
				else if (this.mExpr.charAt(0)=='+') {
					this.mExpr = this.mExpr.substring(1);
					return mTermValue + this.evalExpr();
				}
			}
			else
				return mTermValue;
		}
		else if (this.mExpr.length() > 0  &&  this.mExpr.charAt(0) == '-') {
			this.mExpr = this.mExpr.substring(1);
			Float mTempTerm = this.evalTerm();
			//if (mTempTerm==Float.MIN_VALUE)
				//return mTempTerm;
			
			mTermValue = mTermValue + (-1)*mTempTerm;
			
			if (this.mExpr.length() > 0) {
				if (this.mExpr.charAt(0)=='-') {
					this.mExpr = this.mExpr.substring(1);
					return mTermValue - this.evalExpr();
				}
				else if (this.mExpr.charAt(0)=='+') {
					this.mExpr = this.mExpr.substring(1);
					return mTermValue + this.evalExpr();
				}
			}
			else
				return mTermValue;
		}		
		//else if (this.mExpr.length() > 0)
			//throw new IOException(this.mExpr);
		//else
		    return mTermValue;
	}
	
	public float evalTerm() throws IOException {
        
		Log.i("Entering evalTerm() with expression length", String.valueOf(this.mExpr.length()));
		float mFactor= this.evalFactor();
		
		if (this.mExpr.length() > 0) {
			
		    if (this.mExpr.charAt(0) == '*') {
			this.mExpr = this.mExpr.substring(1);
			return mFactor * this.evalTerm(); 
		    }
		
		    else if (this.mExpr.charAt(0) == '/') {
			this.mExpr = this.mExpr.substring(1);
			return mFactor / this.evalTerm();
		    }
		
		    else if (this.mExpr.charAt(0) == '%') {
			this.mExpr = this.mExpr.substring(1);
			return mFactor % this.evalFactor();
		    }
		
		    else if (this.mExpr.charAt(0) == '^') {
			this.mExpr = this.mExpr.substring(1);
			return (float) Math.pow((double)mFactor, (double)this.evalFactor());
		    }
		}
		return mFactor;
	}

	
	public float evalFactor() throws IOException {
		
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
			else {
				throw new IOException(this.mExpr);
			}
		}
		else
		    throw new IOException(this.mExpr);
	}
}