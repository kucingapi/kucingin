package com.example.kucingin.ui.catFood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kucingin.CardAdapter;
import com.example.kucingin.Dataset.CardType;
import com.example.kucingin.Dataset.CatDataset;
import com.example.kucingin.Dataset.CatFoodDataSet;
import com.example.kucingin.databinding.FragmentCatBinding;
import com.example.kucingin.databinding.FragmentCatFoodBinding;

public class CatFoodFragment extends Fragment {

    private FragmentCatFoodBinding binding;
    private RecyclerView popular;
    private CardType[] dataset;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCatFoodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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