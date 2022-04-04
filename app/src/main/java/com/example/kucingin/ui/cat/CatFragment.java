package com.example.kucingin.ui.cat;

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
import com.example.kucingin.databinding.FragmentCatBinding;

public class CatFragment extends Fragment {

    private CatViewModel dashboardViewModel;
    private FragmentCatBinding binding;
    private RecyclerView popular;
    private CardType[] dataset;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(CatViewModel.class);

        binding = FragmentCatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        initDataset();
        CardAdapter customAdapter = new CardAdapter(dataset);
        popular = binding.popularRecycleView;
        popular.setAdapter(customAdapter);
        popular.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return root;
    }

    private void initDataset(){
        CatDataset catDataset = new CatDataset();
        dataset = catDataset.getCats();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}