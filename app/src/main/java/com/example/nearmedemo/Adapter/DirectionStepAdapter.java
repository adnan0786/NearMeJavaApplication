package com.example.nearmedemo.Adapter;

import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nearmedemo.Model.DirectionPlaceModel.DirectionStepModel;
import com.example.nearmedemo.databinding.StepItemLayoutBinding;

import java.util.List;

public class DirectionStepAdapter extends RecyclerView.Adapter<DirectionStepAdapter.ViewHolder> {

    private List<DirectionStepModel> directionStepModels;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        StepItemLayoutBinding binding = StepItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (directionStepModels != null) {
            DirectionStepModel stepModel = directionStepModels.get(position);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.binding.txtStepHtml.setText(Html.fromHtml(stepModel.getHtmlInstructions(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                holder.binding.txtStepHtml.setText(Html.fromHtml(stepModel.getHtmlInstructions()));
            }

            holder.binding.txtStepTime.setText(stepModel.getDuration().getText());
            holder.binding.txtStepDistance.setText(stepModel.getDistance().getText());
        }

    }

    @Override
    public int getItemCount() {

        if (directionStepModels != null)
            return directionStepModels.size();
        else
            return 0;
    }

    public void setDirectionStepModels(List<DirectionStepModel> directionStepModels) {
        this.directionStepModels = directionStepModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private StepItemLayoutBinding binding;

        public ViewHolder(@NonNull StepItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
