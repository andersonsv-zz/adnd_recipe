package br.com.andersonsv.recipe.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Step;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeStepFragment extends Fragment {

    private int index;
    private Step step;

    @BindView(R.id.tvStepName)
    TextView mStepName;

    @BindView(R.id.tvStepNumber)
    TextView mStepNumber;

    @BindView(R.id.tvDescription)
    TextView mDescription;

    private Unbinder unbinder;

    public RecipeStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        unbinder = ButterKnife.bind(this, view);

        mStepNumber.setText(step.getStepNumber());
        mStepName.setText(step.getShortDescription());
        mDescription.setText(step.getDescription());

        return view;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
