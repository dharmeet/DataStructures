package edu.vlabs.ds.expr2;

import java.io.IOException;

import edu.vlabs.ds.mainactivity.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SimulationExperiment2 extends Activity {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simulation_experiment2);
					
	}
	
	public void editExpression (View v){
			Log.i("Click", "Click");
			final EditText mInputExpr = (EditText) findViewById(R.id.calcScreen);
			String mCurrentInput = mInputExpr.getText().toString();
			Log.i(mCurrentInput, mCurrentInput);
			switch(v.getId()){
			case R.id.btnC:	 //clear screen
				mInputExpr.setText("0");
				break;
				
			case R.id.btnPlusMinus:
				mCurrentInput = "-(" + mCurrentInput + ")";
				mInputExpr.setText(mCurrentInput);
				break;
		
			case R.id.btnOneByX:
				mCurrentInput = "1/(" + mCurrentInput + ")";
				mInputExpr.setText(mCurrentInput);
				break;
			
			case R.id.BtnBackspace:
				mCurrentInput = mCurrentInput.substring(0, mCurrentInput.length()-1);
				mInputExpr.setText(mCurrentInput);
				break;
				
			case R.id.btnEqualTo:
				ExpressionEvaluation exprObj = new ExpressionEvaluation(mCurrentInput);
				try {
				     float result = exprObj.evalExpr();
				     if(exprObj.mExpr.length()==0)
					     mInputExpr.setText(String.valueOf(result));
				     else 
				    	 mInputExpr.setText("Invalid Expression1" + exprObj.mExpr);
				 } catch (IOException i) {
						mInputExpr.setText("Invalid Expression2" + exprObj.mExpr);
				 } catch (StringIndexOutOfBoundsException e) {
					    // if string is missing closing brackets or numbers after operators
					    mInputExpr.setText("Incomplete Expression");
				 }
				break;
			
			default:
				if(mCurrentInput.equals("0"))
					mCurrentInput = ((Button) v).getText().toString();
				else
				    mCurrentInput += ((Button) v).getText().toString();
				mInputExpr.setText(mCurrentInput);
				break;
		
			}
		}
}
