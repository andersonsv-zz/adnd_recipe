package br.com.andersonsv.recipe.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder> {

    private List<Ingredient> mData;

    @Override
    @NonNull
    public IngredientRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_ingredient, parent, false);

        return new IngredientRecyclerViewAdapter.ViewHolder(view);
    }

    public void swapData(List<Ingredient> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public List<Ingredient> getData() {
        return mData;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = mData.get(position);

        if(position % 2 == 0) {
            holder.itemView.setBackgroundResource(R.color.colorEven);
        } else {
            holder.itemView.setBackgroundResource(R.color.colorOdd);
        }

        holder.mMeasureQuantity.setText(formatMeasureAndQuantity(ingredient));
        holder.mIngredient.setText(ingredient.getIngredient());
    }

    private String formatMeasureAndQuantity(Ingredient ingredient){
        DecimalFormat removeZeroRight = new DecimalFormat("#.#");

        String quantity = removeZeroRight.format(ingredient.getQuantity());

        return quantity + ingredient.getMeasure();
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvMeasureQuantity)
        TextView mMeasureQuantity;

        @BindView(R.id.tvIngredient)
        TextView mIngredient;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
