package com.example.acadroidaioadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.textfield.TextInputLayout;

public class AddBookActivity extends AppCompatActivity {

    TextView selectFileName, selectPdfFile, uploadPdfFile;
    TextInputLayout selectFileSubject;
    //DatabaseReference uploadPdfRef;
    //StorageReference uploadPdfStorageRef;
    int PDF_CODE = 1;
    Uri pdfUri;
    //StorageTask uploadTask;
    String uploadPptId;
    ProgressDialog loadingBar;
    PDFView uploadPdfView;

    Spinner selectClassSpinner, selectYearSpinner;
    TextView classText, gradeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

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

        selectPdfFile = findViewById(R.id.selectPdfFile);
        selectPdfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (ContextCompat.checkSelfPermission(AddBookActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    SelectFileFromStorage();
                }
                else
                {
                    ActivityCompat.requestPermissions(AddBookActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
                /*if (ContextCompat.checkSelfPermission(AddPptActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    //SelectFileFromStorage();
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddPptActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE))
                    {
                        Toast.makeText(AddPptActivity.this, "permission granted...", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    ActivityCompat.requestPermissions(AddPptActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }*/
            }
        });

        uploadPdfFile = findViewById(R.id.uploadPdfFile);
        uploadPdfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (pdfUri != null)
                {
                    //UploadPdfToFirebaseStorage(pdfUri);
                }
                else
                {
                    Toast.makeText(AddBookActivity.this, "please select file", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectFileName = findViewById(R.id.selectFileName);
        selectFileSubject = findViewById(R.id.selectFileSubject);
        uploadPdfView = findViewById(R.id.uploadPdfView);

    }

    private void SelectFileFromStorage()
    {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PDF_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PDF_CODE && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            selectFileName.setText("You selected:- " + data.getData().getLastPathSegment());
            //uploadPdfView.fromAsset("client.pdf").load();
            uploadPdfView.fromUri(pdfUri).load();
        } else {
            Toast.makeText(this, "Please select file", Toast.LENGTH_SHORT).show();
        }
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