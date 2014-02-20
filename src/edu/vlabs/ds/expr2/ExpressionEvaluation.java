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
	
	int mAdd = '+';
	int mSub = '-';
	int mMul = '*';
	int mDiv = '/';
	int mExp = '^';
	int mMod = '%';
 	int mZero = '0';
 	int mNine = '9';
 	int mOpBrckt = '(';
 	int mClBrckt = ')';
 	int mDot = '.';
 	
	ExpressionEvaluation(String mExpr) {
        this.mExpr = mExpr;
    }
	
    public float evalExpr() throws IOException {
    	
    	Log.i("Entering evalExpr() with expression", this.mExpr);
    	float mTermValue=this.evalTerm();
		    	
    	if (this.mExpr.length() > 0  &&  this.mExpr.charAt(0) == mAdd) {
			
    		this.mExpr = this.mExpr.substring(1);
						
			mTermValue = mTermValue + this.evalExpr();   // for expression like a + b*c
			
			/*if (this.mExpr.length() > 0) {
				if (this.mExpr.charAt(0)=='-') {
					this.mExpr = this.mExpr.substring(1);
					return mTermValue - this.evalExpr(); 
				}
				else if (this.mExpr.charAt(0)=='+') {
					this.mExpr = this.mExpr.substring(1);
					return mTermValue + this.evalExpr();
				}
				//else 
					//throw new IOException(this.mExpr);
			}
			else */
				return mTermValue;
		}
		else if (this.mExpr.length() > 0  &&  this.mExpr.charAt(0) == mSub) {
			
			this.mExpr = this.mExpr.substring(1);
						
			mTermValue = mTermValue + (-1)*this.evalTerm();
			
			if (this.mExpr.length() > 0) {   // for expression like a*b - c + d*e
				
				if (this.mExpr.charAt(0) == mSub) {
					this.mExpr = this.mExpr.substring(1);
					return mTermValue - this.evalExpr();
				}
				
				else if (this.mExpr.charAt(0) == mAdd) {
					this.mExpr = this.mExpr.substring(1);
					return mTermValue + this.evalExpr();
				}
				//else
					//throw new IOException(this.mExpr);
			}
		}
		//else if (this.mExpr.length() > 0)
			//throw new IOException(this.mExpr);
		return mTermValue;
	}
	
	public float evalTerm() throws IOException {
        
		Log.i("Entering evalTerm() with expression", this.mExpr);
		float mFactor= this.evalFactor();
		
		if (this.mExpr.length() > 0) {
			
		    if (this.mExpr.charAt(0) == mMul) {
			this.mExpr = this.mExpr.substring(1);
			mFactor = mFactor * this.evalTerm();
			return this.evalHPTerm(mFactor);   
		    }
		
		    else if (this.mExpr.charAt(0) == mDiv) {
			this.mExpr = this.mExpr.substring(1);
			float mDivisor = this.evalTerm();
			if (mDivisor == 0)
				throw new ArithmeticException();
			return mFactor / mDivisor;
		    }
		
		    else if (this.mExpr.charAt(0) == mMod) {
			this.mExpr = this.mExpr.substring(1);
			float mDivisor = this.evalFactor();
			if (mDivisor == 0)
				throw new ArithmeticException();
			mFactor = mFactor % mDivisor;
			return this.evalHPTerm(mFactor);
		    }
				
		    else if (this.mExpr.charAt(0) == mExp) {
			this.mExpr = this.mExpr.substring(1);
			// for expression of type a^b*c we should first calculate a^b
			mFactor = (float) Math.pow((double)mFactor, (double)this.evalFactor());
		    return this.evalHPTerm(mFactor);
		    }
		}
		return mFactor;
	}
	
	//Function to take care of expressions of type a^b*c or a%b*c 
	//where a^b and a%b are calculated and passed as argument
	public float evalHPTerm (float mFactor) throws IOException {
		
		Log.i("Entering evalHPTerm() with expression", this.mExpr);
		
		if (this.mExpr.length() > 0) {
			if (this.mExpr.charAt(0) == mAdd) {
				this.mExpr = this.mExpr.substring(1);
				return mFactor + this.evalExpr();
			}
			else if (this.mExpr.charAt(0) == mSub) {
				this.mExpr = this.mExpr.substring(1);
				mFactor = mFactor + (-1)*this.evalTerm();
				if (this.mExpr.length() > 0)
				{
					if (this.mExpr.charAt(0) == mAdd) {
						this.mExpr = this.mExpr.substring(1);
						return mFactor + this.evalExpr();
					}
					else if (this.mExpr.charAt(0) == mSub) {
						this.mExpr = this.mExpr.substring(1);
						return mFactor - this.evalExpr();
					}
				}
				else
					return mFactor;
			}
			else if (this.mExpr.charAt(0) == mMul) {
				this.mExpr = this.mExpr.substring(1);
				return mFactor * this.evalTerm();
			}
			else if (this.mExpr.charAt(0) == mDiv) {
				this.mExpr = this.mExpr.substring(1);
				float mDivisor = this.evalTerm();
				if (mDivisor == 0)
					throw new ArithmeticException();
				return mFactor / mDivisor;
			}
			else if (this.mExpr.charAt(0) == mMod) {
				this.mExpr = this.mExpr.substring(1);
				float mDivisor = this.evalFactor();
				if (mDivisor == 0)
					throw new ArithmeticException();
				mFactor = mFactor % mDivisor;
				return this.evalHPTerm(mFactor);
			}
			else if (this.mExpr.charAt(0) == mExp) {
				this.mExpr = this.mExpr.substring(1);
				mFactor = (float) Math.pow((double) mFactor, (double) this.evalFactor());
				return this.evalHPTerm(mFactor);
			}
			//else
				//throw new IOException(this.mExpr);
		}
		return mFactor;
	}
	
	public float evalFactor() throws IOException {
		
		Log.i("Entering evalFactor() with the expression ", this.mExpr); 
		String numb = "";
		float mNumber = Float.MIN_VALUE;
		if (this.mExpr.length() > 0) {
			if ((this.mExpr.charAt(0) >= mZero  &&  this.mExpr.charAt(0) <= mNine)  ||  this.mExpr.charAt(0) == mDot) {
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
		    else if (this.mExpr.charAt(0) == mOpBrckt) {
			    this.mExpr = this.mExpr.substring(1);
			    mNumber = this.evalExpr();
			
			    if (this.mExpr.charAt(0) == mClBrckt) {
				    this.mExpr = this.mExpr.substring(1);
				    Log.i("evalFactor", this.mExpr);
				    return mNumber;
			    }
			    else {
				    throw new IOException(this.mExpr);
			    }
		    }
		}
		throw new IOException(this.mExpr);
	}
}
	