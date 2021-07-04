package com.example.chesserschedulingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chesserschedulingapp.Entity.TermEntity;
import com.example.chesserschedulingapp.R;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.example.chesserschedulingapp.UI.TermDetailsActivity.*;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermView> {

    private final LayoutInflater inflater;
    private final Context context;
    private List<TermEntity> termList;

    public TermAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    class TermView extends RecyclerView.ViewHolder {
        private final TextView termListView;

        private TermView(View termItemView) {
            super(termItemView);
            termListView = termItemView.findViewById(R.id.termTextView);
            termItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final TermEntity selectedTerm = termList.get(position);
                    Intent intent = new Intent(context, TermDetailsActivity.class);
                    intent.putExtra("termId", selectedTerm.getTermId());
                    intent.putExtra("termName", selectedTerm.getTermName());
                    intent.putExtra("termStart", selectedTerm.getStartDate());
                    intent.putExtra("termEnd", selectedTerm.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public TermView onCreateViewHolder(ViewGroup parent, int viewType) {
        View termItemView = inflater.inflate(R.layout.term_list_item, parent, false);
        return new TermView(termItemView);
    }

    @Override
    public void onBindViewHolder(TermView holder, int position) {
        if (termList != null) {
            final TermEntity selectedTerm = termList.get(position);
            holder.termListView.setText(selectedTerm.getTermName() + " Start Date: " + dateFormat.format(selectedTerm.getStartDate()) + " End Date: " + dateFormat.format(selectedTerm.getEndDate()));
        }
        else {
            String message = "You currently have no terms. Try adding some to populate this list!";
            holder.termListView.setText(message);
        }
    }

    @Override
    public int getItemCount() {
        if (termList != null) {
            Log.d("Terms","Term list size: " + termList.size());
            return termList.size();
        }
        else {Log.d("Terms","Term list is null"); return 0; }
    }


    public void setTerms(List<TermEntity> terms) {
        termList = terms;
        notifyDataSetChanged();
    }
}
