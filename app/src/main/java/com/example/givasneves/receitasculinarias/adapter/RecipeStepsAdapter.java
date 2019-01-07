package com.example.givasneves.receitasculinarias.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.givasneves.receitasculinarias.R;
import com.example.givasneves.receitasculinarias.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder> {


    private OnStepClickListener itemClickCallBack;

    private List<Step> steps;
    public RecipeStepsAdapter(List<Step> steps) {
        this.steps = steps;
    }

    public interface OnStepClickListener {
        void onItemClick(Step step);
    }

    @NonNull
    @Override
    public RecipeStepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_step, viewGroup, false);

        try {
            itemClickCallBack = (OnStepClickListener) viewGroup.getContext();
        } catch (ClassCastException e) {
            throw new ClassCastException(viewGroup.getContext().toString()
                    + "Must implement OnStepClickListener");
        }

        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsViewHolder recipeStepsViewHolder, int i) {
        recipeStepsViewHolder.position = i;
        recipeStepsViewHolder.prepare(steps.get(i));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class RecipeStepsViewHolder extends RecyclerView.ViewHolder {

        public int position;
        @BindView(R.id.tv_step_title) TextView tvStepTitle;

        @Nullable
        @BindView(R.id.tv_step_description) TextView tvStepDescription;

        @Nullable
        @BindView(R.id.btn_play_video) ImageButton btnPlayVideo;

        public RecipeStepsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Optional
        @OnClick(R.id.btn_play_video)
        void handlePlayVideo() {
            itemClickCallBack.onItemClick(steps.get(position));
        }

        @Optional
        @OnClick(R.id.item_step)
        void handleItemClick() {
            itemClickCallBack.onItemClick(steps.get(position));
        }

        public void prepare(Step step) {
            if(tvStepDescription == null && step.id != 0)
                this.tvStepTitle.setText(step.id + " - " + step.shortDescription);
            else
                this.tvStepTitle.setText(step.shortDescription);

            if(tvStepDescription != null) {
                tvStepDescription.setText(step.description);
                if(!step.videoURL.isEmpty())
                    btnPlayVideo.setVisibility(View.VISIBLE);
            }
        }
    }
}
