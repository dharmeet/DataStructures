package com.cse01.ds;

import android.os.Bundle;

import android.app.ListActivity;
import android.content.Intent;

import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ListView;

public class StartingPage extends ListActivity {

	String classes[] = {"Introduction", "List of Experiments",
			"Target Audience", "Courses Aligned", 
			"Prerequisite"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(
				StartingPage.this, android.R.layout.simple_list_item_1, 
				classes));
	}
	
	@Override
	protected void onListItemClick(
			ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		if(classes[position]=="Target Audience")
			classes[position]="TargetAudience";
		
		if(classes[position]=="Courses Aligned")
			classes[position]="CoursesAligned";
		
		if(classes[position]=="List of Experiments")
			classes[position]="ExperimentsList";
		
		super.onListItemClick(l, v, position, id);
		String menuItem = classes[position];
		try{
		Class ourClass = Class.forName("com.cse01.ds." + menuItem);
		Intent ourIntent = new Intent(StartingPage.this, ourClass);
		startActivity(ourIntent);
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}
	
	
	
	


