package br.com.andersonsv.recipe.activity;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Ingredient;
import br.com.andersonsv.recipe.data.Recipe;
import br.com.andersonsv.recipe.data.Step;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static br.com.andersonsv.recipe.util.Extras.EXTRA_RECIPE;
import static br.com.andersonsv.recipe.util.Extras.EXTRA_STEP_INDEX;
import static br.com.andersonsv.recipe.util.Extras.EXTRA_STEP_LIST;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    private final String RECIPE_INTRODUCTION = "Recipe introduction";

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityRule =
            new ActivityTestRule<RecipeActivity>(RecipeActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, RecipeActivity.class);

                    Recipe recipe = new Recipe();
                    recipe.setName("Nutella Pie");

                    Step step = new Step();
                    step.setDescription(RECIPE_INTRODUCTION);
                    step.setShortDescription(RECIPE_INTRODUCTION);
                    step.setId(0);
                    step.setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");

                    List<Ingredient> ingredients = new ArrayList<>();
                    Ingredient ingredient = new Ingredient();
                    ingredient.setIngredient("Graham Cracker crumbs");
                    ingredient.setMeasure("CUP");
                    ingredient.setQuantity(2.0);

                    ingredients.add(ingredient);

                    List<Step> steps = new ArrayList<>();
                    steps.add(step);

                    recipe.setIngredients(ingredients);
                    recipe.setSteps(steps);

                    result.putExtra(EXTRA_STEP_INDEX,0);
                    result.putParcelableArrayListExtra(EXTRA_STEP_LIST, (ArrayList<Step>) steps);
                    result.putExtra(EXTRA_RECIPE, recipe);
                    result.putExtra(Intent.EXTRA_INTENT, recipe);

                    return result;
                }
            };

    @Test
    public void testHasRecipeIntroItem() {
        onView(withId(R.id.rvStep))
                .check(matches(hasDescendant(withText(RECIPE_INTRODUCTION))));
    }

    @Test
    public void testClickOnRecipeIntro() {
        onView(withId(R.id.rvStep))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(RECIPE_INTRODUCTION)), click()));
    }

    @Test
    public void testHasIngredientsItem() {
        String INGREDIENTS = "Ingredients";
        onView(withId(R.id.tvIngredientTitle)).check(matches(withText(INGREDIENTS)));
    }
}




