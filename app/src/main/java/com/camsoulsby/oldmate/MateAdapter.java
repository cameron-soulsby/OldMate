package com.camsoulsby.oldmate;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.camsoulsby.oldmate.FeedReaderDbHelper;
import com.camsoulsby.oldmate.Mate;
import com.camsoulsby.oldmate.MateViewHolder;
import com.camsoulsby.oldmate.R;
import android.database.sqlite.SQLiteDatabase;


import java.util.List;

public class MateAdapter extends RecyclerView.Adapter<MateViewHolder>{
    private Context context;
    private List<Mate> listMates;
    private FeedReaderDbHelper mDatabase;
    public MateAdapter(Context context, List<Mate> listMates) {
        this.context = context;
        this.listMates = listMates;
        mDatabase = new FeedReaderDbHelper(context);
    }
    @Override
    public MateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_mate, parent, false);
        return new MateViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MateViewHolder holder, int position) {
        final Mate singleMate = listMates.get(position);
        holder.name.setText(singleMate.getName());
        holder.notes.setText(singleMate.getNotes());

    }
    @Override
    public int getItemCount() {
        return listMates.size();
    }

}