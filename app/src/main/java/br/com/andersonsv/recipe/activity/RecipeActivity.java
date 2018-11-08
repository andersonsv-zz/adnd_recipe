package br.com.andersonsv.recipe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Recipe;
import br.com.andersonsv.recipe.fragment.RecipeInfoFragment;
import br.com.andersonsv.recipe.fragment.RecipeStepFragment;
import br.com.andersonsv.recipe.ui.StepListener;
import butterknife.ButterKnife;

import static br.com.andersonsv.recipe.util.Extras.EXTRA_RECIPE;
import static br.com.andersonsv.recipe.util.Extras.EXTRA_RECIPE_NAME;
import static br.com.andersonsv.recipe.util.Extras.EXTRA_STEP_INDEX;
import static br.com.andersonsv.recipe.util.Extras.EXTRA_STEP_LIST;

public class RecipeActivity extends AppCompatActivity implements RecipeInfoFragment.StepClickListener,
        StepListener {

    private Recipe recipe;
    private int index;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();

        if (extras != null) {
            recipe = extras.getParcelable(Intent.EXTRA_INTENT);

            setTitle(recipe.getName());
        }

        if(intent != null){
            checkIntent(intent);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecipeInfoFragment recipeDetailFragment = new RecipeInfoFragment();
        recipeDetailFragment.setRecipe(recipe);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flt_recipe_content, recipeDetailFragment)
                .commit();

        isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet){
            insertStepFragment(index);

        }
    }

    public void checkIntent(Intent intent){
        if (intent.hasExtra(EXTRA_STEP_INDEX)){
            index = intent.getIntExtra(EXTRA_STEP_INDEX, -1);
        }
        if (intent.hasExtra(EXTRA_RECIPE)){
            recipe = intent.getParcelableExtra(EXTRA_RECIPE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_STEP_INDEX, index);
        outState.putParcelable(EXTRA_RECIPE, recipe);
    }

    private void insertStepFragment(int index) {
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setIndex(index);
        recipeStepFragment.setStep(recipe.getSteps().get(index));

        getSupportFragmentManager().beginTransaction().
                replace(R.id.flt_step_content, recipeStepFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStepClicked(int position) {
        index = position;

        if(isTablet){
            insertStepFragment(index);
        }else {
            Intent intent = new Intent(this, RecipeStepActivity.class);
            intent.putExtra(EXTRA_STEP_INDEX, position);
            intent.putParcelableArrayListExtra(EXTRA_STEP_LIST, new ArrayList<Parcelable>(recipe.getSteps()));
            intent.putExtra(EXTRA_RECIPE_NAME, recipe.getName());
            startActivity(intent);
        }
    }

    @Override
    public void onNext() {
        if (index < recipe.getSteps().size() - 1) {
            index++;
            insertStepFragment(index);
        }
    }

    @Override
    public void onPrevious() {
        if (index > 0) {
            index--;
            insertStepFragment(index);
        }
    }
}
