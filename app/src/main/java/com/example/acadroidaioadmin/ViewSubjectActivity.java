package com.example.acadroidaioadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewSubjectActivity extends AppCompatActivity {

    RecyclerView subjectNameRecList;
    Spinner selectClassSpinner, selectYearSpinner;
    TextView classText, gradeText, signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subject);

        subjectNameRecList = findViewById(R.id.subjectNameRecList);

        //HOOKS FOR SPINNER COMPONENTS
        selectClassSpinner = findViewById(R.id.selectClassSpinner);
        selectYearSpinner = findViewById(R.id.selectYearSpinner);
        classText = findViewById(R.id.classText);
        gradeText = findViewById(R.id.gradeText);

        //SPINNER CODE FOR CLASS AND GRADE
        ArrayAdapter<CharSequence> adapter4Class = ArrayAdapter.createFromResource(getApplicationContext(), R.array.class_array, android.R.layout.simple_spinner_item);
        adapter4Class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectClassSpinner.setAdapter(adapter4Class);
        selectClassSpinner.setOnItemSelectedListener(new ClassSpinner());

        ArrayAdapter<CharSequence> adapter4Grade = ArrayAdapter.createFromResource(getApplicationContext(), R.array.grade_array, android.R.layout.simple_spinner_item);
        adapter4Grade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectYearSpinner.setAdapter(adapter4Grade);
        selectYearSpinner.setOnItemSelectedListener(new GradeSpinner());
    }

    private class ClassSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            classText.setText(itemSpinner);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }

    private class GradeSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            gradeText.setText(itemSpinner);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }

}