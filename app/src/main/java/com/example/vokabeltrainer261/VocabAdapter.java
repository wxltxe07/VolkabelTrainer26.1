package com.example.vokabeltrainer261;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vokabeltrainer261.R;
import com.example.vokabeltrainer261.Vocab;

import java.util.List;

public class VocabAdapter extends RecyclerView.Adapter<VocabAdapter.ViewHolder> {

    private List<Vocab> vocabList;

    public VocabAdapter(List<Vocab> vocabList){
        this.vocabList = vocabList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView otherWord;
        TextView germanWord;

        public ViewHolder(View itemView){
            super(itemView);

            otherWord = itemView.findViewById(R.id.otherWord);
            germanWord = itemView.findViewById(R.id.germanWord);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vocab_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Vocab vocab = vocabList.get(position);

        holder.otherWord.setText(vocab.getOther());
        holder.germanWord.setText(vocab.getGerman());
    }

    @Override
    public int getItemCount() {
        return vocabList.size();
    }
}