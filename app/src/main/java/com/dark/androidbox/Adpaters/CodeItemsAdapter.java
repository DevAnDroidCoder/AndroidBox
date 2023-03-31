package com.dark.androidbox.Adpaters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dark.androidbox.R;
import com.dark.androidbox.System.NodeEvents;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint("InflateParams")
public class CodeItemsAdapter extends RecyclerView.Adapter<CodeItemsAdapter.ViewHolder> {

    ArrayList<HashMap<String, Object>> list_data;
    Activity ctx;

    public CodeItemsAdapter(Activity ctx, ArrayList<HashMap<String, Object>> list_data) {
        this.list_data = list_data;
        this.ctx = ctx;
    }

    public static int dpToPx(float dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //SetUp View
        LayoutInflater _inflater = ctx.getLayoutInflater();
        View root = _inflater.inflate(R.layout.code_items_list, null);

        RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(50f, ctx));
        _lp.setMargins(12, 12, 12, 12);
        root.setLayoutParams(_lp);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") int pos) {
        View rootView = viewHolder.itemView;
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView label = rootView.findViewById(R.id.label_codeBlock);

        label.setText(list_data.get(pos).get("data").toString());

    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }
}
