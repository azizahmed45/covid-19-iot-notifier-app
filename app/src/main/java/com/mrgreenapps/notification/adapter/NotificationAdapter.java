package com.mrgreenapps.notification.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mrgreenapps.notification.R;
import com.mrgreenapps.notification.model.NotificationModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<NotificationModel> notificationModels ;

    public NotificationAdapter(List<NotificationModel> notificationModels ) {
        this.notificationModels = notificationModels;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView body;
        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.titleId);
            date = (TextView) view.findViewById(R.id.dateId);
            body =  view.findViewById(R.id.bodyId);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.title.setText(notificationModels.get(position).getTitle());
        viewHolder.body.setText(notificationModels.get(position).getBody());
        viewHolder.date.setText(notificationModels.get(position).getDatTime());
    }
    @Override
    public int getItemCount() {
        return notificationModels.size();
    }
}

