package com.mobile.laboratory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.util.ArrayList;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.PlannerViewHolder> {
    private final Context context;
    private final ArrayList<Planner> plannerList;
    private final ArrayList<Planner> allTasksList;

    public PlannerAdapter(Context context, ArrayList<Planner> plannerList, ArrayList<Planner> allTasksList) {
        this.context = context;
        this.plannerList = plannerList;
        this.allTasksList = allTasksList;
    }

    @NonNull
    @Override
    public PlannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.planner, parent, false);
        return new PlannerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PlannerViewHolder holder, int position) {
        Planner planner = plannerList.get(position);
        if (planner == null) {
            Log.e("PlannerAdapter", "Planner object is null at position: " + position);
            return;
        }

        holder.textView1.setText(planner.title);
        holder.textView2.setText(planner.date);
        holder.textView3.setText(planner.time);
        holder.priorityTextView.setText("Priority: " + planner.priority);

        switch (planner.priority) {
            case "High":
                holder.priorityCircle.setBackgroundColor(Color.RED);
                break;
            case "Medium":
                holder.priorityCircle.setBackgroundColor(Color.YELLOW);
                break;
            case "Low":
                holder.priorityCircle.setBackgroundColor(Color.GREEN);
                break;
            default:
                holder.priorityCircle.setBackgroundColor(Color.GRAY);
                break;
        }

        holder.edit.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), UpdateActivity.class);
            intent.putExtra("Title", planner.title);
            intent.putExtra("Date", planner.date);
            intent.putExtra("Time", planner.time);
            intent.putExtra("Priority", planner.priority);
            v.getContext().startActivity(intent);
        });

        holder.delete.setOnClickListener(v -> {
            try {
                delete(holder.itemView, planner);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return plannerList.size();
    }

    private void delete(View itemView, Planner planner) throws IOException {
        int position = plannerList.indexOf(planner);
        Log.d("PlannerAdapter", "Deleting task: " + planner.title + " at position: " + position);
        if (position != -1) {
            plannerList.remove(position);
            allTasksList.remove(planner);
            XMLOperator.writeXml(itemView.getContext(), allTasksList);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, plannerList.size());
        } else {
            Log.e("PlannerAdapter", "Task not found in plannerList: " + planner.title);
        }
    }

    public static class PlannerViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3, priorityTextView;
        View priorityCircle;
        Button edit, delete;

        public PlannerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.titleEvent);
            textView2 = itemView.findViewById(R.id.dateEvent);
            textView3 = itemView.findViewById(R.id.timeEvent);
            priorityTextView = itemView.findViewById(R.id.priorityEvent);
            priorityCircle = itemView.findViewById(R.id.priorityCircle);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}