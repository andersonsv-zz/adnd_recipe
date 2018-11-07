package br.com.andersonsv.recipe.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Step;
import br.com.andersonsv.recipe.fragment.RecipeStepFragment;
import br.com.andersonsv.recipe.ui.StepListener;

import static br.com.andersonsv.recipe.util.Extras.EXTRA_RECIPE_NAME;
import static br.com.andersonsv.recipe.util.Extras.EXTRA_STEP_INDEX;
import static br.com.andersonsv.recipe.util.Extras.EXTRA_STEP_LIST;

public class RecipeStepActivity extends AppCompatActivity implements StepListener {

    private List<Step> steps;
    private String recipeName;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        Intent intent = getIntent();

        if(intent != null){
            checkIntent(intent);
        }

        if(savedInstanceState != null){
            steps = savedInstanceState.getParcelableArrayList(EXTRA_STEP_LIST);
            index = savedInstanceState.getInt(EXTRA_STEP_INDEX);
            recipeName = savedInstanceState.getString(EXTRA_RECIPE_NAME, "");
        }else{
            insertFragment(index);
        }

        if (index == -1 || steps == null) {
            finish();
        }

        setTitle(recipeName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void checkIntent(Intent intent){
        if (intent.hasExtra(EXTRA_STEP_INDEX)){
            index = intent.getIntExtra(EXTRA_STEP_INDEX, -1);
        }

        if (intent.hasExtra(EXTRA_STEP_LIST)){
            steps = intent.getParcelableArrayListExtra(EXTRA_STEP_LIST);
        }

        if (intent.hasExtra(EXTRA_RECIPE_NAME)){
            recipeName = intent.getStringExtra(EXTRA_RECIPE_NAME);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_STEP_INDEX, index);
        outState.putParcelableArrayList(EXTRA_STEP_LIST, new ArrayList<>(steps));
        outState.putString(EXTRA_RECIPE_NAME, recipeName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertFragment(int stepIdx) {
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setIndex(stepIdx);

        Step step = steps.get(stepIdx);

        recipeStepFragment.setStep(step);
        //recipeStepFragment.isPrevEnabled(listIndex > 0);
        //recipeStepFragment.isNextEnabled(listIndex < steps.size() - 1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_view, recipeStepFragment)
                .commit();
    }

    @Override
    public void onNext() {
        if (index < steps.size() - 1) {
            index++;
            insertFragment(index);
        }
    }

    @Override
    public void onPrevious() {
        if (index > 0) {
            index--;
            insertFragment(index);
        }
    }
}
