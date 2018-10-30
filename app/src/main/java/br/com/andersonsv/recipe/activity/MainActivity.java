package br.com.andersonsv.recipe.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.adapter.RecipeRecyclerViewAdapter;
import br.com.andersonsv.recipe.data.Recipe;
import br.com.andersonsv.recipe.network.RecipeService;
import br.com.andersonsv.recipe.network.RetrofitClientInstance;
import br.com.andersonsv.recipe.util.UiUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeRecyclerViewAdapter.RecipeRecyclerOnClickHandler{

    private static final String TAG = MainActivity.class.getName();

    @BindView(R.id.rvRecipe) RecyclerView mRvRecipe;

    private RecipeRecyclerViewAdapter mRecipeAdapter;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;

        /*if (!NetworkUtils.isNetworkConnected(this)) {
            mLlInternetAccessError.setVisibility(View.VISIBLE);
            mErrorMessageDisplay.setVisibility(View.INVISIBLE);

            Snackbar snackbar = Snackbar.make(movieActivity, R.string.offline_no_internet, Snackbar.LENGTH_LONG)
                    .setAction(R.string.offline_no_internet_retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadMovieData(movieSearch);
                        }
                    });
            snackbar.show();

        } else {
            mLoadingIndicator.setVisibility(View.VISIBLE);

            Bundle bundleForLoader = new Bundle();

            if(movieSearch != null){
                bundleForLoader.putInt(Intent.EXTRA_KEY_EVENT, movieSearch.ordinal());
            }

            LoaderManager.LoaderCallbacks<List<Movie>> callback = MovieActivity.this;

            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, bundleForLoader, callback);
        }*/

        loadData();
    }

    private void loadData(){
        RecipeService service = RetrofitClientInstance.getRetrofitInstance().create(RecipeService.class);

        Call<ArrayList<Recipe>> call = service.getRecipes();


        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Log.i(TAG, "SUCCESS " + response.code());
                Log.i(TAG, "RESPONSE" + response.body());

                mRecipeAdapter = new RecipeRecyclerViewAdapter(context, response.body(), MainActivity.this);

                //code copied from https://stackoverflow.com/questions/33575731/gridlayoutmanager-how-to-auto-fit-columns
                int mNoOfColumns = UiUtils.calculateNoOfColumns(getApplicationContext());
                GridLayoutManager glm = new GridLayoutManager(context, 1);

                mRvRecipe.setLayoutManager(glm);
                mRvRecipe.setHasFixedSize(true);

                mRvRecipe.setAdapter(mRecipeAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.e(TAG, "ERROR " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onClick(Recipe recipe) {
        Class destiny = RecipeActivity.class;

        Intent intent = new Intent(MainActivity.this, destiny);
        intent.putExtra(Intent.EXTRA_INTENT, recipe);

        startActivity(intent);
    }
}
