package com.example.ynote.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ynote.Model.Note;
import com.example.ynote.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.InnerHolder> {

    Context mContext;
    List<Note> noteList;
    private OnItemClickListener   onItemClickListener;

    //回调接口
    public interface OnItemClickListener{
        void onItemClick(View view, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.onItemClickListener = mOnItemClickListener;
    }

    //构造函数 传入数据
    public RecyclerViewAdapter(Context mContext, List<Note> noteList) {
        this.mContext = mContext;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_recycler, null);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        holder.item_index.setText(Integer.toString(position + 1));
        holder.item_title.setText(noteList.get(position).getTitle());
        holder.item_content.setText(noteList.get(position).getContent());
        holder.item_time.setText(noteList.get(position).getDate().getDateString());
        if(onItemClickListener != null) {
            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(view, pos);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        //序号 标题 内容 时间
        TextView item_index;
        TextView item_title;
        TextView item_content;
        TextView item_time;
        Button btn_delete;

        //对item组件实例化
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            item_index = itemView.findViewById(R.id.item_index);
            item_title = itemView.findViewById(R.id.item_title);
            item_content = itemView.findViewById(R.id.item_content);
            item_time = itemView.findViewById(R.id.item_time);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }

    }
}
