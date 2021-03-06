package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mNeighbours;
    // DECLARATION DE L'INTERFACE
    private Context mContext;
    private String mFragment;


    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items, String mFragment) {
        this.mNeighbours = items;
        this.mFragment = mFragment;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
            }
        });

        holder.neighbourItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CETTE METHODE ON CLICK PERMET DE PASSER AU DETAIL DU VOISIN
                // SUR LEQUEL ON A CLIQUE ( UNE DES CELLULES )
                // ICI ON VA PASSER UN INTENT A UNE AUTRE ACTIVITE (DETAILS)
                // IL CONTIENT LA POSITION DU VOISIN SUR LEQUEL ON A CLIQUE
                // C EST EN FAITE L'ID DU VOISIN

                Intent intent = new Intent(v.getContext(), DetailsNeighbourActivity.class);
                intent.putExtra("neighbourPosition", position);
                intent.putExtra("mFragment", mFragment);
                v.getContext().startActivity(intent);



            }
        });



    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;
        @BindView(R.id.neighbourItem)
        public ConstraintLayout neighbourItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
