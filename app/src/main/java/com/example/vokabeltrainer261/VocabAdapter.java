package com.example.vokabeltrainer261;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

        EditText otherWord;
        EditText germanWord;

        public ViewHolder(View itemView){
            super(itemView);

            germanWord = itemView.findViewById(R.id.germanWord);
            otherWord = itemView.findViewById(R.id.otherWord);
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

        holder.otherWord.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vocab.setOther(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        holder.germanWord.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vocab.setGerman(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    @Override
    public int getItemCount() {
        return vocabList.size();
    }
}