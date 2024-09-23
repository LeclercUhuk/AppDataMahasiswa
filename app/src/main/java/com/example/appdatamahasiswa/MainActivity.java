package com.example.appdatamahasiswa;

import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etNim, etIpk, etCourse;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etNim = findViewById(R.id.etNim);
        etIpk = findViewById(R.id.etIpk);
        etCourse = findViewById(R.id.etCourse);
        recyclerView = findViewById(R.id.recyclerView);

        databaseHelper = new DatabaseHelper(this);
        studentList = new ArrayList<>();
        studentAdapter = new StudentAdapter(studentList, databaseHelper);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(studentAdapter);

        etIpk.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (source != null && source.toString().contains(",")) {
                            return source.toString().replace(",", ".");
                        }
                        return null;
                    }
                }
        });

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        loadStudents();
    }

    private void addStudent() {
        String name = etName.getText().toString();
        String nimStr = etNim.getText().toString();
        String ipkStr = etIpk.getText().toString().replace(",", "."); // Replace comma with dot
        String course = etCourse.getText().toString();

        if (name.isEmpty() || nimStr.isEmpty() || ipkStr.isEmpty() || course.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int nim;
        double ipk;
        try {
            nim = Integer.parseInt(nimStr);
            ipk = Double.parseDouble(ipkStr);
            if (nim <= 0 || ipk <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid NIM or IPK", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.addStudent(name, nim, ipk, course)) {
            Toast.makeText(this, "Student Added", Toast.LENGTH_SHORT).show();
            etName.setText("");
            etNim.setText("");
            etIpk.setText("");
            etCourse.setText("");
            loadStudents();
        } else {
            Toast.makeText(this, "Error Adding Student", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadStudents() {
        studentList.clear();
        Cursor cursor = databaseHelper.getAllStudents();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String nim = cursor.getString(2);
                double ipk = cursor.getDouble(3);
                String ipkStr = String.valueOf(ipk).replace(".", ",");
                String course = cursor.getString(4);
                studentList.add(new Student(id, name, nim, ipk, course));
            } while (cursor.moveToNext());
        }
        cursor.close();
        studentAdapter.notifyDataSetChanged();
    }
}
