package com.islavdroid.jokejson;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Content> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public MyViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.text);

        }
    }


    public RecyclerViewAdapter(List<Content> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Content content = list.get(position);
        holder.text.setText(content.getText());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
