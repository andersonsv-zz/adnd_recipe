package br.com.andersonsv.recipe.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Step;
import br.com.andersonsv.recipe.fragment.RecipeStepFragment;

import static br.com.andersonsv.recipe.util.Extras.EXTRA_RECIPE_INDEX;
import static br.com.andersonsv.recipe.util.Extras.EXTRA_RECIPE_NAME;
import static br.com.andersonsv.recipe.util.Extras.EXTRA_STEP_LIST;

public class RecipeStepActivity extends AppCompatActivity {

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

        setTitle(recipeName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        insertFragment(index);
    }

    public void checkIntent(Intent intent){
        if (intent.hasExtra(EXTRA_RECIPE_INDEX)){
            index = intent.getIntExtra(EXTRA_RECIPE_INDEX, -1);
        }

        if (intent.hasExtra(EXTRA_STEP_LIST)){
            steps = intent.getParcelableArrayListExtra(EXTRA_STEP_LIST);
        }

        if (intent.hasExtra(EXTRA_RECIPE_NAME)){
            recipeName = intent.getStringExtra(EXTRA_RECIPE_NAME);
        }
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
}
