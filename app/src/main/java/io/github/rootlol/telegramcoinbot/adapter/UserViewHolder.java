package io.github.rootlol.telegramcoinbot.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.github.rootlol.telegramcoinbot.R;

public class UserViewHolder extends RecyclerView.ViewHolder{
    public TextView numder;
    public TextView balance;

    public UserViewHolder(View itemView) {
        super(itemView);
        numder = itemView.findViewById(R.id.item_user_tv_nuber);
        balance = itemView.findViewById(R.id.item_user_tv_balance);
    }

    public void bind(User user){
        numder.setText(user.getNumber());
        balance.setText(user.getBalance());
    }
}
