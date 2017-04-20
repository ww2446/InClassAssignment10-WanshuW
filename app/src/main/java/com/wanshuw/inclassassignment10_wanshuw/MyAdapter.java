package com.wanshuw.inclassassignment10_wanshuw;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Addie on 2017/4/13.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<BlogPost> mDataset;
    Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
       public TextView mTitle;
        public TextView mTime;
        public TextView mBody;


        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView)v.findViewById(R.id.title);
            mTime = (TextView)v.findViewById(R.id.time);
            mBody = (TextView)v.findViewById(R.id.body);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<BlogPost> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, padding and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//         holder.mTextView.setText(mDataset[position]);
            holder.mTitle.setText(mDataset.get(position).getTitle());
            holder.mTime.setText(mDataset.get(position).toReadableTime());
            holder.mBody.setText(mDataset.get(position).getBody());
        holder.mTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mContext,DetailActivity.class);
                intent.putExtra("A blogpost", mDataset.get(holder.getAdapterPosition()));
                mContext.startActivity(intent);
            }

        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
