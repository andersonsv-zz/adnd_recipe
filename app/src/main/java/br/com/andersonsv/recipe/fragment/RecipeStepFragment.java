package br.com.andersonsv.recipe.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Step;

public class RecipeStepFragment extends Fragment {

    private int index;
    private Step step;

    public RecipeStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        return view;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setStep(Step step) {
        this.step = step;
    }
}
