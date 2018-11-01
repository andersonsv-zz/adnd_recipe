package br.com.andersonsv.recipe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Step;

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
}
