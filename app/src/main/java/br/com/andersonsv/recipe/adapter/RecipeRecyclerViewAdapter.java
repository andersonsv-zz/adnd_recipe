package br.com.andersonsv.recipe.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.ImageRecipe;
import br.com.andersonsv.recipe.data.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private List<Recipe> mData;
    private final RecipeRecyclerOnClickHandler mClickHandler;

    public RecipeRecyclerViewAdapter(RecipeRecyclerOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_recipe, parent, false);

        return new ViewHolder(view);
    }

    public void swapData(List<Recipe> recipes) {
        this.mData = recipes;
        notifyDataSetChanged();
    }

    public List<Recipe> getData() {
        return mData;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = mData.get(position);

        ImageRecipe imageRecipe = ImageRecipe.getById(recipe.getId());

        Picasso.get()
                .load(imageRecipe.getImageRecipe())
                .into(holder.mRecipeImage);

        holder.mRecipeName.setText(recipe.getName());

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public interface RecipeRecyclerOnClickHandler {
        void onClick(Recipe recipe);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivRecipe)
        ImageView mRecipeImage;


        @BindView(R.id.tvRecipe)
        TextView mRecipeName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mData.get(adapterPosition);
            mClickHandler.onClick(recipe);
        }
    }
}
