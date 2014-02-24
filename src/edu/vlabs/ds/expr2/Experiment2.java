package edu.vlabs.ds.expr2;

//import edu.vlabs.ds.mainactivity.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Experiment2 extends ListActivity {
	
	String classes[] = {"Introduction", "Theory", "Objective",
			"Simulation", "Quizzes", "Further Readings"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_experiment1);
		setListAdapter(new ArrayAdapter<String>(Experiment2.this,
				android.R.layout.simple_list_item_1, classes));
			
	}
	
	@Override
	protected void onListItemClick(
			ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		classes[position] = classes[position] + "Experiment2"; 
		
		super.onListItemClick(l, v, position, id);
		String menuItem = classes[position];
		try{
		//	setContentView(R.layout.activity_introduction2);
		Class<?> ourClass = Class.forName("edu.vlabs.ds.expr2." + menuItem);
		Intent ourIntent = new Intent(Experiment2.this, ourClass);
		startActivity(ourIntent);
		}catch(ClassNotFoundException e){
		    e.printStackTrace();
		}
		}
}
