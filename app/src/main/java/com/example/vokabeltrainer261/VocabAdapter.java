package com.example.vokabeltrainer261;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VocabAdapter extends RecyclerView.Adapter<VocabAdapter.ViewHolder> {

    private Context context;
    private List<Vocab> vocabList;
    private DbHelper db;

    public VocabAdapter(Context context, List<Vocab> vocabList) {
        this.context = context;
        this.vocabList = vocabList;
        this.db = new DbHelper(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText otherWord;
        EditText germanWord;
        Button deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            otherWord = itemView.findViewById(R.id.otherWord);
            germanWord = itemView.findViewById(R.id.germanWord);
            deleteBtn = itemView.findViewById(R.id.button7);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vocab_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vocab vocab = vocabList.get(position);


        if (holder.otherWord.getTag() instanceof TextWatcher)
            holder.otherWord.removeTextChangedListener((TextWatcher) holder.otherWord.getTag());
        if (holder.germanWord.getTag() instanceof TextWatcher)
            holder.germanWord.removeTextChangedListener((TextWatcher) holder.germanWord.getTag());


        holder.otherWord.setText(vocab.getOther());
        holder.germanWord.setText(vocab.getGerman());


        TextWatcher otherWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                vocab.setOther(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        TextWatcher germanWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                vocab.setGerman(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        holder.otherWord.addTextChangedListener(otherWatcher);
        holder.otherWord.setTag(otherWatcher);

        holder.germanWord.addTextChangedListener(germanWatcher);
        holder.germanWord.setTag(germanWatcher);


        holder.deleteBtn.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                db.deleteVocab(vocabList.get(pos).getId());
                vocabList.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, vocabList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return vocabList.size();
    }
}