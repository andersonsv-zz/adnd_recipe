package br.com.andersonsv.recipe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipe;

    @BindView(R.id.flt_recipe_detail)
    FrameLayout mRecipeDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        final Bundle extras = getIntent().getExtras();

        if(extras != null){
           recipe = extras.getParcelable(Intent.EXTRA_INTENT);

           setTitle(recipe.getName());
        }

        recipe.getIngredients();

    }
}
