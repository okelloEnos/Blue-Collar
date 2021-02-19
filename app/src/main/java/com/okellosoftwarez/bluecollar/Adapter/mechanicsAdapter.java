package com.okellosoftwarez.bluecollar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.okellosoftwarez.bluecollar.Model.MechanicModel;
import com.okellosoftwarez.bluecollar.R;
import com.okellosoftwarez.bluecollar.viewScreen.MechanicDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class mechanicsAdapter extends RecyclerView.Adapter<mechanicsAdapter.mechanicsViewHolder> {
    private static final String TAG = "productAdapter";
    List<MechanicModel> mechanicList;
    private Context mcontext;

    public mechanicsAdapter(Context context, List<MechanicModel> list) {

        mcontext = context;
        mechanicList = list;
    }

    @NonNull
    @Override
    public mechanicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new View...");
        View view = LayoutInflater.from(mcontext).inflate(R.layout.recycler_layout, parent, false);
        mechanicsViewHolder mechanicView = new mechanicsViewHolder(view);

        Log.d(TAG, "onCreateViewHolder: view created...");
        return mechanicView;
    }

    @Override
    public void onBindViewHolder(@NonNull mechanicsViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: align to recycler...");
        final MechanicModel currentMechanic = mechanicList.get(position);
        holder.recName.setText("Name : " + currentMechanic.getName());
        holder.recLocation.setText("Location : " + currentMechanic.getLocation());
        holder.recSpec.setText("Specialization : " + currentMechanic.getSpeciality());
//        holder.recMail.setText("Email : " + currentMechanic.getEmail());
        Picasso.get()
                .load(currentMechanic.getImageUrl())
                .placeholder(R.drawable.ic_image_black_24dp)
                .fit()
                .centerCrop()
                .into(holder.recImage);


            holder.clickedLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent passIntent = new Intent(mcontext, MechanicDetails.class);
                    passIntent.putExtra("name", currentMechanic.getName());
                    passIntent.putExtra("location", currentMechanic.getLocation());
                    passIntent.putExtra("mail", currentMechanic.getEmail());
                    passIntent.putExtra("image", currentMechanic.getImageUrl());
                    passIntent.putExtra("phone", currentMechanic.getPhone());
                    passIntent.putExtra("speciality", currentMechanic.getSpeciality());

                    mcontext.startActivity(passIntent);
                    Log.d(TAG, "onClick: detail view...");
                }
            });

        Log.d(TAG, "onBindViewHolder: done binding....");
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: Counting method...");
        if (mechanicList != null) {
            Log.d(TAG, "getItemCount: Done counting for list...");
            return mechanicList.size();
        }
        Log.d(TAG, "getItemCount: Done counting for non-list...");
        return 0;
    }

    public class mechanicsViewHolder extends RecyclerView.ViewHolder {

        ImageView recImage;
        TextView recName, recLocation, recSpec, recMail;
        LinearLayout clickedLayout;

        public mechanicsViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "productViewHolder: Initializing recycler items...");
            recImage = itemView.findViewById(R.id.recycleImage);
            recName = itemView.findViewById(R.id.recycleName);
            recLocation = itemView.findViewById(R.id.recycleLocation);
            recSpec = itemView.findViewById(R.id.recycleSpeciality);
//            recMail = itemView.findViewById(R.id.recycleEmail);
            clickedLayout = itemView.findViewById(R.id.rec);
        }
    }
}
