package com.example.countriesinfo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countriesinfo.Model.MainData;
import com.example.countriesinfo.Model.RoomDB;
import com.example.countriesinfo.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyHolder> {
    private ArrayList<MainData> mainData;
    private Context context;
    private RoomDB database;

    public MainAdapter(Context context, ArrayList<MainData> mainData){
        this.context = context;
        this.mainData = mainData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        database = RoomDB.getInstance(context);
        holder.countryName.setText(mainData.get(position).getName());
        holder.capital.setText(mainData.get(position).getCapital());
        holder.region.getEditText().setText(mainData.get(position).getRegion());
        holder.subRegion.getEditText().setText(mainData.get(position).getSubregion());
        holder.population.getEditText().setText(String.valueOf(mainData.get(position).getPopulation()));
        holder.language.getEditText().setText(mainData.get(position).getLanguages());
        holder.borders.getEditText().setText(mainData.get(position).getBorders());
        String html = "<html><body><img src=\"" + mainData.get(position).getFlag() + "\" width=\"100%\" height=\"100%\"\"/></body></html>";
        holder.imageView.loadData(html, "text/html", null);
    }

    @Override
    public int getItemCount() {
        return mainData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        WebView imageView;
        TextView countryName, capital;
        TextInputLayout region, subRegion, population, borders, language;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (WebView) itemView.findViewById(R.id.imageview);
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            capital = (TextView) itemView.findViewById(R.id.capital);
            region = (TextInputLayout) itemView.findViewById(R.id.Region);
            subRegion = (TextInputLayout) itemView.findViewById(R.id.Subregion);
            population = (TextInputLayout) itemView.findViewById(R.id.Population);
            borders = (TextInputLayout) itemView.findViewById(R.id.Borders);
            language = (TextInputLayout) itemView.findViewById(R.id.Languages);
        }
    }
}
