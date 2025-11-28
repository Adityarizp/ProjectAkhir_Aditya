package com.adet.projectakhir_aditya.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adet.projectakhir_aditya.ListMasyarakatActivity;
import com.adet.projectakhir_aditya.R;
import com.adet.projectakhir_aditya.UpdateActivity;
import com.adet.projectakhir_aditya.db.DbHelper;
import com.adet.projectakhir_aditya.model.Masyarakat;

import java.io.Serializable;
import java.util.ArrayList;

public class MasyarakatAdapter extends RecyclerView.Adapter<MasyarakatAdapter.MasyarakatViewHolder> {
    private ArrayList<Masyarakat> listMasyarakat = new ArrayList<>();
    private Activity activity;
    private DbHelper dbHelper;

    public MasyarakatAdapter(Activity activity) {
        this.activity = activity;
        dbHelper = new DbHelper(activity);
    }

    public static class MasyarakatViewHolder extends RecyclerView.ViewHolder {
        final TextView txtNIK, txtNama;
        final Button btnUbah, btnHapus;

        public MasyarakatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNIK = itemView.findViewById(R.id.txt_item_nik);
            txtNama = itemView.findViewById(R.id.txt_item_nama);
            btnUbah = itemView.findViewById(R.id.btn_ubah);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
        }
    }

    public ArrayList<Masyarakat> getListMasyarakat() {
        return listMasyarakat;
    }

    public void setListMasyarakat(ArrayList<Masyarakat> listMasyarakat) {
        if (listMasyarakat.size() > 0) {
            this.listMasyarakat.clear();
        }
        this.listMasyarakat.addAll(listMasyarakat);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MasyarakatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MasyarakatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MasyarakatViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtNIK.setText(listMasyarakat.get(position).getNIK());
        holder.txtNama.setText(listMasyarakat.get(position).getNama());

        holder.btnUbah.setOnClickListener((View v) -> {
            Intent intent = new Intent(activity, UpdateActivity.class);
            intent.putExtra("user", (Serializable) listMasyarakat.get(position));
            activity.startActivity(intent);
        });

        holder.btnHapus.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Konfirmasi hapus");
            builder.setMessage("Apakah yakin akan dihapus?");
            builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbHelper.deleteUser(listMasyarakat.get(position).getId());
                    Toast.makeText(activity, "Hapus berhasil!", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(activity, ListMasyarakatActivity.class);
                    activity.startActivity(myIntent);
                    activity.finish();
                }
            });
            builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    public int getItemCount() {
        return listMasyarakat.size();
    }
}