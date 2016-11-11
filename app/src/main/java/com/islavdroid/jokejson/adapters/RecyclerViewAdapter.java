package com.islavdroid.jokejson.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.islavdroid.jokejson.Content;
import com.islavdroid.jokejson.R;
import com.islavdroid.jokejson.database.DBHelper;

import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private List<Content> list;
    private DBHelper helpher;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public CheckBox checkBox;
        public ImageButton copy,share;

        public MyViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.text);
            checkBox=(CheckBox) view.findViewById(R.id.star);
            copy =(ImageButton)view.findViewById(R.id.copy);
            share =(ImageButton)view.findViewById(R.id.share);

        }

    }


    public RecyclerViewAdapter(Context context,List<Content> list) {

        this.list = list;
        helpher = new DBHelper(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Content content = list.get(position);
        holder.text.setText(content.getText());
        //in some cases, it will prevent unwanted situations
        holder.checkBox.setOnCheckedChangeListener(null);

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String text = content.getText();
                sendIntent.putExtra(Intent.EXTRA_TEXT,text );
                sendIntent.setType("text/plain");
                holder.share.getContext().startActivity(sendIntent);
            }
        });




        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) holder.copy.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                String text = content.getText();
                ClipData  myClip = ClipData.newPlainText("text", text);
                clipboard.setPrimaryClip(myClip);
                Toast.makeText(holder.copy.getContext(),"текст скопирован в буфер обмена",Toast.LENGTH_SHORT).show();

            }
        });
        //if true, your checkbox will be selected, else unselected
        holder.checkBox.setChecked(list.get(position).isSelected());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                helpher.saveContent(content);
                list.get(holder.getAdapterPosition()).setSelected(isChecked);
                Toast.makeText(holder.checkBox.getContext(),"добавленно в избранное",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(){
        notifyItemRangeInserted(0,list.size());
    }
}
