package com.example.chesserschedulingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chesserschedulingapp.Entity.CourseEntity;
import com.example.chesserschedulingapp.R;
import java.text.SimpleDateFormat;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseView> {

    private final LayoutInflater inflater;
    private final Context context;
    private List<CourseEntity> courseList;

    public CourseAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    class CourseView extends RecyclerView.ViewHolder {
        private final TextView courseListView;

        private CourseView(View courseItemView) {
            super(courseItemView);
            courseListView = courseItemView.findViewById(R.id.courseTextView);
            courseItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final CourseEntity selectedCourse = courseList.get(position);
                    Intent intent = new Intent(context, CourseDetailsActivity.class);
                    intent.putExtra("courseId", selectedCourse.getCourseId());
                    intent.putExtra("courseName", selectedCourse.getCourseName());
                    intent.putExtra("courseStart", selectedCourse.getStartDate());
                    intent.putExtra("termEnd", selectedCourse.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public CourseAdapter.CourseView onCreateViewHolder(ViewGroup parent, int viewType) {
        View courseItemView = inflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseAdapter.CourseView(courseItemView);
    }

    @Override
    public void onBindViewHolder(CourseAdapter.CourseView holder, int position) {
        if (courseList != null) {
            final CourseEntity selectedCourse = courseList.get(position);
            holder.courseListView.setText(selectedCourse.getCourseName() + " Start Date: " + dateFormat.format(selectedCourse.getStartDate()) + " End Date: " + dateFormat.format(selectedCourse.getEndDate()));
        }
        else {
            String message = "You currently have no courses. Try adding some to populate this list!";
            holder.courseListView.setText(message);
        }
    }

    @Override
    public int getItemCount() {
        if (courseList != null) {
            Log.d("Courses","Course list size: " + courseList.size());
            return courseList.size();
        }
        else {Log.d("Courses","Course list is null"); return 0; }
    }


    public void setCourses(List<CourseEntity> courses) {
        courseList = courses;
        notifyDataSetChanged();
    }
}
