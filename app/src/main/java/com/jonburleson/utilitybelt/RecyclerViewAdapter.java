package com.jonburleson.utilitybelt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private final List<Note> data;
    TinyDB tinydb;
    String key;

    public RecyclerViewAdapter(Context context, List<Note> data, String key) {
        this.context = context;
        this.data = data;
        this.key = key;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.cardview_notes, parent,false);
        tinydb = new TinyDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.noteTitle.setText(data.get(position).getTitle());
        holder.noteThumbnail.setImageResource(data.get(position).getThumbnail());

        if(data.get(position).isFlagged()) {
            holder.noteBookmark.setVisibility(View.VISIBLE);
        } else {
            holder.noteBookmark.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle;
        ImageView noteThumbnail;
        ImageView noteBookmark;

        public ViewHolder(View itemView) {
            super(itemView);

            noteTitle = (TextView)itemView.findViewById(R.id.note_title);
            noteThumbnail = (ImageView)itemView.findViewById(R.id.note_img);
            noteBookmark = (ImageView)itemView.findViewById(R.id.bookmark);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getLayoutPosition();
                    Intent intent = new Intent(context, NotesOpenedActivity.class);
                    tinydb.putInt(key, position);
                    context.startActivity(intent);
                }
            });
        }
    }

}
