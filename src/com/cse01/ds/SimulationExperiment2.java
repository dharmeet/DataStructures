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
			switch(v.getId()){
			case R.id.btnC:	 //clear screen
				Scr.setText("0");
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
				String FinalExpression = Scr.getText().toString();
				if(ExpressionEvaluation.ValidateExpression(FinalExpression))
				{
					float result = ExpressionEvaluation.CalculateExpression(FinalExpression);
					Scr.setText(String.valueOf(result));
				}
				else
					Scr.setText("Invalid Expression");
				break;
			
			default:
				String numb = ((Button) v).getText().toString();
				String ScrCurrent = Scr.getText().toString();
				ScrCurrent += numb;
				Scr.setText(ScrCurrent);
				break;
		
			}
		}
}
