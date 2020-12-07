package com.example.collegecourses;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtCourse,txtGrade,txtCourseID;
    Button btnAdd,btnShow,btnDelete,btnUpdate,btnGPA;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        txtCourse =(EditText)findViewById(R.id.editTextCourse);
        txtGrade = (EditText)findViewById(R.id.editTextGrade);
        txtCourseID =(EditText)findViewById(R.id.editTextCourseID);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnShow =(Button)findViewById(R.id.btnShow);
        btnDelete =(Button)findViewById(R.id.btnDelete);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnGPA=(Button)findViewById(R.id.btnGPA);
        showCourses();
        InsertCourse();
        DropCourse();
        UpdateCourse();
        showGPA();
    }
    private void showCourses(){
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor acura = db.ViewCourses();
                if(acura.getCount()==0){
                    showMessage("Error","Cannot find any courses");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(acura.moveToNext()){
                    buffer.append("CourseID :"+acura.getString(0)+"\n");
                    buffer.append("Course :"+acura.getString(1)+"\n");
                    buffer.append("Grade :"+acura.getString(2)+"\n\n");
                }
                showMessage("Courses",buffer.toString());
            }
        });
    }
    private void showGPA(){
        btnGPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor acura = db.ShowGPA();
                if(acura.getCount()==0){
                    showMessage("Error","Unable to receive GPA");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(acura.moveToNext()){
                    buffer.append("Grade :"+acura.getString(0));
                }
                showMessage("GPA",buffer.toString());
            }
        });
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
        builder.setCancelable(true);
    }
    public void InsertCourse(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean CourseAdded = db.InsertCourse(txtCourse.getText().toString(),txtGrade.getText().toString());
                if(CourseAdded == true){
                    Toast.makeText(MainActivity.this,"Course is added",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Course unable to be added",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void DropCourse(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer CourseDropped = db.DeleteCourse(txtCourseID.getText().toString());
                if(CourseDropped > 0){
                    Toast.makeText(MainActivity.this,"Course is dropped",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Course unable to be dropped",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void UpdateCourse(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean CourseUpdated = db.UpdateCourse(txtCourseID.getText().toString(),txtCourse.getText().toString(),txtGrade.getText().toString());
                if(CourseUpdated == true){
                    Toast.makeText(MainActivity.this,"Course and details are updated",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Course details are unable to be updated",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}