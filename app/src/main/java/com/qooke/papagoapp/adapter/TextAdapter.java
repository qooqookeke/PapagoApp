package com.qooke.papagoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qooke.papagoapp.R;
import com.qooke.papagoapp.model.History;

import java.util.ArrayList;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder> {

    Context context;
    ArrayList<History> historyArrayList;

    // 위의 멤버변수 생성자 만들기
    public TextAdapter(Context context, ArrayList<History> historyArrayList) {
        this.context = context;
        this.historyArrayList = historyArrayList;
    }

    @NonNull
    @Override
    public TextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_row, parent, false);
        return new TextAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history = historyArrayList.get(position);
        // 홀더에 연결
        holder.txtText.setText(history.sentence);
        holder.txtResult.setText(history.result);
        holder.txtTarget.setText(history.target);

    }

    @Override
    public int getItemCount() {
        return historyArrayList.size();
    }

    // 리사이클러뷰 뷰홀더 셋팅
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtText;
        TextView txtResult;
        TextView txtTarget;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtText = itemView.findViewById(R.id.txtText);
            txtResult = itemView.findViewById(R.id.txtResult);
            txtTarget = itemView.findViewById(R.id.txtTarget);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }
}
