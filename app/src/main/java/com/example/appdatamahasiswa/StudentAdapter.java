package com.example.appdatamahasiswa;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private DatabaseHelper databaseHelper;

    public StudentAdapter(List<Student> studentList, DatabaseHelper databaseHelper) {
        this.studentList = studentList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.tvName.setText("Nama : " + student.getName());
        holder.tvNim.setText("NIM : " + student.getNim());
        holder.tvIpk.setText("IPK : " + String.valueOf(student.getIpk()).replace(".", ","));
        holder.tvCourse.setText("Mata Kuliah : " + student.getCourse());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent(student.getId());
            }
        });
    }


    private void deleteStudent(int id) {
        if (databaseHelper.deleteStudent(id)) {
            for (Student student : studentList) {
                if (student.getId() == id) {
                    studentList.remove(student);
                    notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNim, tvIpk, tvCourse;
        Button btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvNim = itemView.findViewById(R.id.tvNim);
            tvIpk = itemView.findViewById(R.id.tvIpk);
            tvCourse = itemView.findViewById(R.id.tvCourse);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

