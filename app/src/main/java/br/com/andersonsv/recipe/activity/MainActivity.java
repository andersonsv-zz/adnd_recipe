package br.com.andersonsv.recipe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Recipe;
import br.com.andersonsv.recipe.network.RecipeService;
import br.com.andersonsv.recipe.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
    }

    private void loadData(){
        RecipeService service = RetrofitClientInstance.getRetrofitInstance().create(RecipeService.class);

        Call<ArrayList<Recipe>> call = service.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Toast.makeText(MainActivity.this, "in response", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "SUCCESS " + response.code());
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.e(TAG, "ERROR " + t.getLocalizedMessage());
            }
        });
    }
}
