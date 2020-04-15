package com.example.android_mvvm;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_mvvm.room_repository.Note;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    Context context;
    List<Note> notes ;
    OnDeleteListener onDeleteListener;
    public  NoteAdapter(Context context,OnDeleteListener onDeleteListener)
    {
        this.context=context;
        this.onDeleteListener=onDeleteListener;
    }
    @NonNull
    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.each_note,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.MyViewHolder holder, final int position) {

        if(notes==null)
        {
            holder.title.setText("no data");
        }
        else
        {
            Note note=notes.get(position);
            holder.title.setText(note.getTitle());
            if(note.getPriority()==2)
            {
                holder.priority_icon.getDrawable().mutate().setTint(Color.parseColor("#FA5B29"));
            }
            else if(note.getPriority()==3)
            {
                holder.priority_icon.getDrawable().mutate().setTint(Color.parseColor("#FFEA00"));
            }
            else
            {
                holder.priority_icon.getDrawable().mutate().setTint(Color.parseColor("#F10231"));
            }
        }
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dailog);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button bt=dialog.findViewById(R.id.dailog_bt);
                TextView tv_title=dialog.findViewById(R.id.dailog_title);
                TextView tv_desc=dialog.findViewById(R.id.dailog_desc);
                tv_title.setText(notes.get(position).getTitle());
                tv_desc.setText(notes.get(position).getDesc());
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });
        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,UpdateNote.class);
                intent.putExtra("id",notes.get(position).getId());
              ( (Activity)context).startActivityForResult(intent,MainActivity.UPDATE_NOTE_REQUEST);
            }
        });
        holder.img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteListener.DeleteListener(notes.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(notes==null)
            return 0;
        else
            return notes.size();

    }
    public void setNoteList(List<Note> list)
    {
        notes=list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        ImageView img_edit,priority_icon;
        ImageView img_del;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card_note);
            title=itemView.findViewById(R.id.tv_title);
            img_edit=itemView.findViewById(R.id.item_edit);
            img_del=itemView.findViewById(R.id.item_del);

            priority_icon=itemView.findViewById(R.id.priority_icon);
        }
    }
    public interface OnDeleteListener{
        void DeleteListener(Note note);
    }
}
