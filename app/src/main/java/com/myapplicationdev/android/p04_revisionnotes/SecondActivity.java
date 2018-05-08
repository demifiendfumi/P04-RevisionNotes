package com.myapplicationdev.android.p04_revisionnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
	ListView lv;
	ArrayAdapter aa;
	ArrayList<Note> notes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lv = (ListView) this.findViewById(R.id.lv);

        notes = new ArrayList<Note>();
		aa = new RevisionNotesArrayAdapter(SecondActivity.this, R.layout.row, notes);
        lv.setAdapter(aa);

		DBHelper db = new DBHelper(SecondActivity.this);

		// Insert a task
		ArrayList<Note> data = db.getTasks();
        notes.clear();
        notes.addAll(data);
		db.close();

		for (int j = 0; j < data.size(); j++) {
            Log.d("Database Content", data.get(j).getId() +". "+data.get(j).getNoteContent()+ ". " + data.get(j).getStars() + "\n");
		}


	}


}
