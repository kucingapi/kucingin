package com.example.kucingin.ui.catFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kucingin.CardAdapter;
import com.example.kucingin.Dataset.Card;
import com.example.kucingin.Dataset.CatFoodDataSet;
import com.example.kucingin.databinding.FragmentCatFoodBinding;

public class CatFoodFragment extends Fragment {

    private FragmentCatFoodBinding binding;
    private RecyclerView popular;
    private Card[] dataset;
    private Intent intent;
    private TextView username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCatFoodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        intent = getActivity().getIntent();
        username = binding.usernameTextview;

        username.setText(intent.getStringExtra("username"));

        initDataset();
        CardAdapter customAdapter = new CardAdapter(dataset);
        popular = binding.popularRecycleView;
        popular.setAdapter(customAdapter);
        popular.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return root;
    }

    private void initDataset(){
        CatFoodDataSet catFoodDataset = new CatFoodDataSet();
        dataset = catFoodDataset.getCatFoods();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}