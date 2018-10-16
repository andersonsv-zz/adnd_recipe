package br.com.andersonsv.recipe.network;

import java.util.ArrayList;

import br.com.andersonsv.recipe.data.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();
}
