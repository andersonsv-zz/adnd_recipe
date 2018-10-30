package br.com.andersonsv.recipe.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.ResourceBundle;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.adapter.RecipeRecyclerViewAdapter;
import br.com.andersonsv.recipe.data.Recipe;
import br.com.andersonsv.recipe.network.RecipeService;
import br.com.andersonsv.recipe.network.RetrofitClientInstance;
import br.com.andersonsv.recipe.util.NetworkUtils;
import br.com.andersonsv.recipe.util.UiUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements RecipeRecyclerViewAdapter.RecipeRecyclerOnClickHandler{

    private static final String TAG = MainActivity.class.getName();

    @BindView(R.id.rvRecipe) RecyclerView mRvRecipe;
    @BindView(R.id.pgLoading) ProgressBar mLoading;
    @BindView(R.id.mainContext) ConstraintLayout mMainContext;

    private RecipeRecyclerViewAdapter mRecipeAdapter;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;

        if (!NetworkUtils.isNetworkConnected(this)) {
           // retry connect
            createSnackbarToReload(R.string.offline_no_internet);
        } else {
            loadData();
        }
    }

    private void loadData(){
        RecipeService service = RetrofitClientInstance.getRetrofitInstance().create(RecipeService.class);

        Call<ArrayList<Recipe>> call = service.getRecipes();

        mLoading.setVisibility(View.VISIBLE);

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

                mLoading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                mLoading.setVisibility(View.INVISIBLE);
                createSnackbarToReload(R.string.error_download_data);
                Log.e(TAG, "ERROR " + t.getLocalizedMessage());
            }
        });
    }

    private void createSnackbarToReload(int textResource){
        Snackbar snackbar = Snackbar.make(mMainContext, textResource, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry_download_data, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadData();
                    }
                });
        snackbar.show();
    }

    @Override
    public void onClick(Recipe recipe) {
        Class destiny = RecipeActivity.class;

        Intent intent = new Intent(MainActivity.this, destiny);
        intent.putExtra(Intent.EXTRA_INTENT, recipe);

        startActivity(intent);
    }
}
