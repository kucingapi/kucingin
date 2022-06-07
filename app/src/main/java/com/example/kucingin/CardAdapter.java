package com.example.kucingin;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kucingin.Dataset.Card;
import com.example.kucingin.databinding.CardItemBinding;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Card[] localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardItemBinding cardItemBinding;
        private Context context;
        public ViewHolder(CardItemBinding cardItemBinding) {
            super(cardItemBinding.getRoot());
            // Define click listener for the ViewHolder's View
            context = cardItemBinding.getRoot().getContext();
            this.cardItemBinding = cardItemBinding;
        }

    }

    public CardAdapter() {
        this.localDataSet = new Card[0];
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CardAdapter(Card[] dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        CardItemBinding view = CardItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setLocalDataSet(Card[] localDataSet){
        this.localDataSet = localDataSet;
        this.notifyDataSetChanged();
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Card data = localDataSet[position];
        ImageView cardImage = viewHolder.cardItemBinding.cardImage;
        TextView cardTitle = viewHolder.cardItemBinding.cardTitle;
        TextView cardDescription = viewHolder.cardItemBinding.cardDescription;
        TextView cardMoreInfo = viewHolder.cardItemBinding.cardMoreInfo;
        if(data.imageUri != null)
            Glide.with(viewHolder.context)
                    .load(data.imageUri)
                    .centerCrop()
                    .placeholder(R.drawable.cat_angora)
                    .into(cardImage);
        else {
            cardImage.setImageResource(data.imageId);
        }

        String description = data.description.length() <= 100 ?
                data.description :
                data.description.substring(0, 100) + "...";
        cardTitle.setText(data.title);
        cardDescription.setText(description);
        cardMoreInfo.setOnClickListener(moreInfoListener(data, viewHolder.context));
    }

    private View.OnClickListener moreInfoListener(Card data, Context context){
        Intent intent = new Intent(context, DetailCat.class);
        intent.putExtra("title", data.title);
        intent.putExtra("description", data.description);
        intent.putExtra("image_id", data.imageId);
        intent.putExtra("type", data.type);

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(intent);
            }
        };
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}
