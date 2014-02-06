package com.cse01.ds;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SimulationExperiment2 extends Activity {

	private EditText Scr;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simulation_experiment2);
		Scr = (EditText) findViewById(R.id.calcScreen);
		Scr.setEnabled(false);
			
	}
	public void editExpression(View v){
			Log.i("Click", "Click");
			String ScrCurrent = Scr.getText().toString();
			switch(v.getId()){
			case R.id.btnC:	 //clear screen
				Scr.setText("0");
				break;
				
			case R.id.btnPlusMinus:
				
				ScrCurrent = "-(" + ScrCurrent + ")";
				Scr.setText(ScrCurrent);
				break;
		
			case R.id.btnOneByX:
				ScrCurrent = "1/(" + ScrCurrent + ")";
				Scr.setText(ScrCurrent);
				break;
			
			case R.id.BtnBackspace:
				ScrCurrent = ScrCurrent.substring(0, ScrCurrent.length()-1);
				Scr.setText(ScrCurrent);
				break;
				
			case R.id.btnEqualTo:
				if(ExpressionEvaluation.ValidateExpression(ScrCurrent))
				{
					float result = ExpressionEvaluation.CalculateExpression(ScrCurrent);
					Scr.setText(String.valueOf(result));
				}
				else
					Scr.setText("Invalid Expression");
				break;
			
			default:
				String numb = ((Button) v).getText().toString();
				ScrCurrent += numb;
				Scr.setText(ScrCurrent);
				break;
		
			}
		}
}
