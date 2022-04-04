package com.example.kucingin;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kucingin.Dataset.CardType;
import com.example.kucingin.databinding.CardItemBinding;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private CardType[] localDataSet;

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

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CardAdapter(CardType[] dataSet) {
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

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        CardType data = localDataSet[position];
        ImageView cardImage = viewHolder.cardItemBinding.cardImage;
        TextView cardTitle = viewHolder.cardItemBinding.cardTitle;
        TextView cardDescription = viewHolder.cardItemBinding.cardDescription;
        TextView cardMoreInfo = viewHolder.cardItemBinding.cardMoreInfo;

        String description = data.description.substring(0, 100) + "...";
        cardImage.setImageResource(data.imageId);
        cardTitle.setText(data.title);
        cardDescription.setText(description);
        cardMoreInfo.setOnClickListener(moreInfoListener(data, viewHolder.context));
    }

    private View.OnClickListener moreInfoListener(CardType data, Context context){
        Intent intent = new Intent(context, DetailCat.class);
        intent.putExtra("title", data.title);
        intent.putExtra("description", data.description);
        intent.putExtra("image_id", data.imageId);
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
