package br.com.andersonsv.recipe.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Recipe;
import br.com.andersonsv.recipe.data.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepRecyclerViewAdapter extends RecyclerView.Adapter<StepRecyclerViewAdapter.ViewHolder> {

    private List<Step> mData;

    private final StepRecyclerViewAdapter.StepRecyclerOnClickHandler mClickHandler;

    public StepRecyclerViewAdapter(StepRecyclerViewAdapter.StepRecyclerOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    public interface StepRecyclerOnClickHandler {
        void onClick(int position);
    }

    @Override
    @NonNull
    public StepRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_step, parent, false);

        return new StepRecyclerViewAdapter.ViewHolder(view);
    }

    public void swapData(List<Step> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public List<Step> getData() {
        return mData;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Step step = mData.get(position);

        holder.mStepNumber.setText(step.getId().toString());
        holder.mShortDescription.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tvStepNumber)
        TextView mStepNumber;

        @BindView(R.id.tvShortDescription)
        TextView mShortDescription;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(getAdapterPosition());
        }
    }
}
