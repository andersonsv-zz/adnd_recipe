package br.com.andersonsv.recipe.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.adapter.IngredientRecyclerViewAdapter;
import br.com.andersonsv.recipe.adapter.StepRecyclerViewAdapter;
import br.com.andersonsv.recipe.data.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static br.com.andersonsv.recipe.util.Extras.EXTRA_RECIPE;
public class RecipeInfoFragment extends Fragment  implements StepRecyclerViewAdapter.StepRecyclerOnClickHandler{

    private Unbinder unbinder;
    private Recipe recipe;

    @BindView(R.id.rvIngredient)
    RecyclerView mRvIngredient;

    @BindView(R.id.rvStep)
    RecyclerView mRvStep;

    private IngredientRecyclerViewAdapter ingredientAdapter;
    private StepRecyclerViewAdapter stepAdapter;

    private StepClickListener stepClickListener;


    public RecipeInfoFragment() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_RECIPE, recipe);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        configureAdapters();

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(EXTRA_RECIPE);
        }

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
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    @Override
    public void onClick(int position) {
        stepClickListener.onStepClicked(position);
    }

    public interface StepClickListener {
        void onStepClicked(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            stepClickListener = (StepClickListener) context;
        }
        catch (ClassCastException cce) {
            throw new ClassCastException(context.toString() + " implements interface StepClickListener");
        }
    }
}
