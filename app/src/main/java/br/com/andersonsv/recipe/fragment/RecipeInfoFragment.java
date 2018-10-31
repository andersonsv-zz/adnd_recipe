package br.com.andersonsv.recipe.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.adapter.IngredientRecyclerViewAdapter;
import br.com.andersonsv.recipe.adapter.StepRecyclerViewAdapter;
import br.com.andersonsv.recipe.data.Recipe;
import br.com.andersonsv.recipe.data.Step;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeInfoFragment extends Fragment  implements StepRecyclerViewAdapter.StepRecyclerOnClickHandler{

    private Unbinder unbinder;
    private Recipe recipe;

    @BindView(R.id.rvIngredient)
    RecyclerView mRvIngredient;

    @BindView(R.id.rvStep)
    RecyclerView mRvStep;

    private IngredientRecyclerViewAdapter ingredientAdapter;
    private StepRecyclerViewAdapter stepAdapter;

    public RecipeInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recipe.getId();
        View view = inflater.inflate(R.layout.fragment_recipe_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        configureAdapters();

        if (recipe != null) {
            ingredientAdapter.swapData(recipe.getIngredients());
            stepAdapter.swapData(recipe.getSteps());
        }

        return view;
    }

    private void configureAdapters() {
        mRvIngredient.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvIngredient.setHasFixedSize(true);
        mRvIngredient.setNestedScrollingEnabled(false);
        ingredientAdapter = new IngredientRecyclerViewAdapter();
        mRvIngredient.setAdapter(ingredientAdapter);

        mRvStep.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvStep.setHasFixedSize(true);
        mRvStep.setNestedScrollingEnabled(false);
        stepAdapter = new StepRecyclerViewAdapter(this);
        mRvStep.setAdapter(stepAdapter);
    }


    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onClick(Step step) {
        Log.d("","");
    }
}
