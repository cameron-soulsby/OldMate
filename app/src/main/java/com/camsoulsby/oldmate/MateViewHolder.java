package com.camsoulsby.oldmate;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.camsoulsby.oldmate.R;
public class MateViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView notes;

    public MateViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.name);
        notes = (TextView)itemView.findViewById(R.id.notes);
    }
}