package edu.vlabs.ds.mainactivity;

import edu.vlabs.ds.mainactivity.ExperimentsList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExperimentsList extends ListActivity {
	
	String classes[] ={"1. Number Systems", 
			"2. Expression Evaluation using Stacks", 
			"3. Sorting using Arrays", 
			"4. Polynomials via Linked Lists",
			"5. Expression Trees",
			"6. Search Trees",
			"7. Graph Traversals",
			"8. Shortest Paths in Graphs",
			"9. Minimum Spanning Trees"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(ExperimentsList.this, android.R.layout.simple_list_item_1, classes));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		switch(position) {
		case 0: 
			classes[position]="expr1.Experiment1";
			break;
		case 1:
			classes[position]="expr2.Experiment2";
			break;
		case 2:
			classes[position]="expr3.Experiment3";
			break;
		case 3:
			classes[position]="expr4.Experiment4";
			break;
		case 4:
			classes[position]="Experiment5";
			break;
		case 5:
			classes[position]="Experiment6";
			break;
		case 6:
			classes[position]="Experiment7";
			break;
		case 7:
			classes[position]="Experiment8";
			break;
		case 8:
			classes[position]="Experiment9";
			break;
		}
		
		String menuItem = classes[position];
		
		try{
		
			Class<?> ourClass = Class.forName("edu.vlabs.ds." + menuItem);
			Intent ourIntent = new Intent(ExperimentsList.this, ourClass);
			startActivity(ourIntent);
		
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
}
