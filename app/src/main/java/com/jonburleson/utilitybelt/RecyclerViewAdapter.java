package com.jonburleson.utilitybelt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Note> data;

    public RecyclerViewAdapter(Context context, List<Note> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.cardview_notes,parent,false);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle;
        ImageView noteThumbnail;
        ImageView noteBookmark;

        public ViewHolder(View itemView) {
            super(itemView);

            noteTitle = (TextView)itemView.findViewById(R.id.note_title);
            noteThumbnail = (ImageView)itemView.findViewById(R.id.note_img);
            noteBookmark = (ImageView)itemView.findViewById(R.id.bookmark);
        }
    }

}
