package com.example.chesserschedulingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chesserschedulingapp.Entity.AssessmentEntity;
import com.example.chesserschedulingapp.R;
import java.text.SimpleDateFormat;
import java.util.List;


public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentView> {

    private final LayoutInflater inflater;
    private final Context context;
    private List<AssessmentEntity> assessmentList;

    public AssessmentAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    class AssessmentView extends RecyclerView.ViewHolder {
        private final TextView assessmentListView;

        private AssessmentView(View assessmentItemView) {
            super(assessmentItemView);
            assessmentListView = assessmentItemView.findViewById(R.id.assessmentTextView);
            assessmentItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final AssessmentEntity selectedAssessment = assessmentList.get(position);
                    Intent intent = new Intent(context, AssessmentDetailsActivity.class);
                    intent.putExtra("assessmentId", selectedAssessment.getAssessmentId());
                    intent.putExtra("assessmentName", selectedAssessment.getAssessmentName());
                    intent.putExtra("assessmentStartGoal", selectedAssessment.getGoalStartDate());
                    intent.putExtra("assessmentEndGoal", selectedAssessment.getGoalEndDate());
                    intent.putExtra("courseId", selectedAssessment.getCourseId());
                    context.startActivity(intent);
                }
            });
        }
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public AssessmentAdapter.AssessmentView onCreateViewHolder(ViewGroup parent, int viewType) {
        View assessmentItemView = inflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentAdapter.AssessmentView(assessmentItemView);
    }

    @Override
    public void onBindViewHolder(AssessmentAdapter.AssessmentView holder, int position) {
        if (assessmentList != null) {
            final AssessmentEntity selectedAssessment = assessmentList.get(position);
            holder.assessmentListView.setText(selectedAssessment.getAssessmentName() + " Start Goal: " + dateFormat.format(selectedAssessment.getGoalStartDate()) + " End Goal: " + dateFormat.format(selectedAssessment.getGoalEndDate()));
        }
        else {
            String message = "You currently have no assessments. Try adding some to populate this list!";
            holder.assessmentListView.setText(message);
        }
    }

    @Override
    public int getItemCount() {
        if (assessmentList != null) {
            Log.d("Assessments","Assessment list size: " + assessmentList.size());
            return assessmentList.size();
        }
        else {Log.d("Assessments","Assessment list is null"); return 0; }
    }


    public void setAssessments(List<AssessmentEntity> assessments) {
        assessmentList = assessments;
        notifyDataSetChanged();
    }
}
