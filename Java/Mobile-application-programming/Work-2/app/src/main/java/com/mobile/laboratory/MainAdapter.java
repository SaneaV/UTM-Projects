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

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private ArrayList<Object> dataList;
    private final ArrayList<Planner> plannerList;
    private final RecyclerView recyclerView;

    public MainAdapter(ArrayList<Object> dataList, ArrayList<Planner> plannerList, RecyclerView recyclerView) {
        this.dataList = dataList;
        this.plannerList = plannerList;
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position) instanceof String ? TYPE_HEADER : TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.header_item, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.planner, parent, false);
            return new PlannerViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_HEADER) {
            String header = (String) dataList.get(position);
            ((HeaderViewHolder) holder).headerTextView.setText(header);
        } else {
            Planner planner = (Planner) dataList.get(position);
            if (planner == null) {
                Log.e("MainAdapter", "Planner object is null at position: " + position);
                return;
            }

            PlannerViewHolder plannerViewHolder = (PlannerViewHolder) holder;
            String title = planner.title != null ? planner.title : "No Title";
            String date = planner.date != null ? planner.date : "No Date";
            String time = planner.time != null ? planner.time : "No Time";
            String priority = planner.priority != null ? planner.priority : "Medium";

            plannerViewHolder.titleTextView.setText(title);
            plannerViewHolder.dateTextView.setText(date);
            plannerViewHolder.timeTextView.setText(time);
            plannerViewHolder.priorityTextView.setText("Priority: " + priority);

            switch (priority) {
                case "Low":
                    plannerViewHolder.priorityCircle.setBackgroundColor(Color.parseColor("#00FF00"));
                    break;
                case "High":
                    plannerViewHolder.priorityCircle.setBackgroundColor(Color.parseColor("#FF0000"));
                    break;
                default:
                    plannerViewHolder.priorityCircle.setBackgroundColor(Color.parseColor("#FFFF00"));
                    break;
            }

            plannerViewHolder.editButton.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), UpdateActivity.class);
                intent.putExtra("Title", title);
                intent.putExtra("Date", date);
                intent.putExtra("Time", time);
                intent.putExtra("Priority", priority);
                v.getContext().startActivity(intent);
            });

            plannerViewHolder.deleteButton.setOnClickListener(v -> {
                try {
                    deleteTask(position);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private void deleteTask(int position) throws IOException {
        Object item = dataList.get(position);
        if (item instanceof Planner planner) {
            dataList.remove(position);
            plannerList.remove(planner);
            XMLOperator.writeXml(recyclerView.getContext(), plannerList);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataList.size());

            int headerPosition = findHeaderPosition(position);
            if (headerPosition != -1 && (headerPosition + 1 == dataList.size() || !(dataList.get(headerPosition + 1) instanceof Planner))) {
                dataList.remove(headerPosition);
                notifyItemRemoved(headerPosition);
                notifyItemRangeChanged(headerPosition, dataList.size());
            }

            notifyDataChanged(recyclerView.getContext());
        }
    }

    private int findHeaderPosition(int position) {
        for (int i = position - 1; i >= 0; i--) {
            if (dataList.get(i) instanceof String) {
                return i;
            }
        }
        return -1;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<Object> newData) {
        this.dataList = newData;
        notifyDataSetChanged();
    }

    private void notifyDataChanged(Context context) {
        context.sendBroadcast(new Intent("com.mobile.laboratory.DATA_CHANGED"));
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTextView;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTextView = itemView.findViewById(R.id.headerText);
        }
    }

    public static class PlannerViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, dateTextView, timeTextView, priorityTextView;
        View priorityCircle;
        Button editButton, deleteButton;

        public PlannerViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleEvent);
            dateTextView = itemView.findViewById(R.id.dateEvent);
            timeTextView = itemView.findViewById(R.id.timeEvent);
            priorityTextView = itemView.findViewById(R.id.priorityEvent);
            priorityCircle = itemView.findViewById(R.id.priorityCircle);
            editButton = itemView.findViewById(R.id.edit);
            deleteButton = itemView.findViewById(R.id.delete);
        }
    }
}