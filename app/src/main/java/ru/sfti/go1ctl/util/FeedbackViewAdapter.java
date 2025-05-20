package ru.sfti.go1ctl.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.sfti.go1ctl.R;

public class FeedbackViewAdapter
        extends RecyclerView.Adapter<FeedbackViewAdapter.ViewHolder>
{
    private String[] _fields;
    private String[] _values;


    public FeedbackViewAdapter(String[] fields) {
        this._fields = fields;
    }


    @NonNull
    @Override
    public ViewHolder
    onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.field_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getFieldNameView().setText(this._fields[i]);
        if (this._values != null)
            viewHolder.getFieldValueView().setText(this._values[i]);
    }


    @Override
    public int getItemCount() {
        return _fields.length;
    }


    public void
    setValues(String[] values) {
        if (values.length == this._fields.length)
            this._values = values;
    }


    public static class ViewHolder
            extends RecyclerView.ViewHolder
    {
        private final TextView _fieldName;
        private final TextView _fieldValue;

        public ViewHolder(View view)
        {
            super(view);
            this._fieldName  = view.findViewById(R.id.field_name_text_view);
            this._fieldValue = view.findViewById(R.id.field_value_text_view);
        }

        public TextView
        getFieldNameView() {
            return this._fieldName;
        }

        public TextView
        getFieldValueView() {
            return this._fieldValue;
        }
    }
}
