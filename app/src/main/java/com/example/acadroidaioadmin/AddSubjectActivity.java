package com.example.acadroidaioadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;

public class AddSubjectActivity extends AppCompatActivity {

    Spinner selectClassSpinner, selectYearSpinner;
    TextView classText, gradeText, signInBtn;
    TextInputLayout subjectName;
    ProgressDialog dialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        dialog = new ProgressDialog(this);

        subjectName = findViewById(R.id.subjectName);
        signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grade = gradeText.getText().toString();
                String classTxt = classText.getText().toString();
                String subj = subjectName.getEditText().getText().toString().toUpperCase(Locale.ROOT);
                String subjUID = classTxt.toUpperCase()+grade.toUpperCase()+subj.substring(0,4).toUpperCase();
                if (subj.isEmpty()){
                    Toast.makeText(AddSubjectActivity.this, "Field's is empty!!!", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.setMessage("please wait...");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    HashMap subjMap = new HashMap();
                    subjMap.put("Class",classTxt);
                    subjMap.put("Semester",grade);
                    subjMap.put("SubjName",subj);
                    subjMap.put("SubjUID",subjUID);
                    db.collection("Subjects").document(subjUID).set(subjMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddSubjectActivity.this, "Subject Added", Toast.LENGTH_LONG).show();
                                subjectName.getEditText().getText().clear();
                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(AddSubjectActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

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