package com.example.kucingin.ui.cat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kucingin.CardAdapter;
import com.example.kucingin.Dataset.Card;
import com.example.kucingin.Dataset.CatDataset;
import com.example.kucingin.databinding.FragmentCatBinding;

public class CatFragment extends Fragment {

    private FragmentCatBinding binding;
    private RecyclerView popular;
    private Card[] dataset;
    private Intent intent;
    private TextView username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCatBinding.inflate(inflater, container, false);
        username = binding.usernameTextview;

        intent = getActivity().getIntent();
        username.setText(intent.getStringExtra("username"));

        initDataset();
        CardAdapter customAdapter = new CardAdapter(dataset);
        popular = binding.popularRecycleView;
        popular.setAdapter(customAdapter);
        popular.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        View root = binding.getRoot();
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