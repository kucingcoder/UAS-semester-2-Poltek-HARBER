package com.example.uassemester2.Controller;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uassemester2.Model.Produk;
import com.example.uassemester2.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {

    ArrayList<Produk> daftar_item;
    LayoutInflater inflater;
    Locale negara = new Locale.Builder().setLanguage("US").setRegion("US").build();
    NumberFormat dolar = NumberFormat.getCurrencyInstance(negara);

    public AdapterData(Context context, ArrayList<Produk> daftar_item) {
        this.daftar_item = daftar_item;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterData.HolderData holder, int position) {
        try{
            new AmbilGambar(holder.ivThumbnail).execute(daftar_item.get(position).getThumbnail());
            holder.tvTitle.setText(daftar_item.get(position).getJudul());
            holder.tvPrice.setText(dolar.format(daftar_item.get(position).getHarga()));
            holder.tvRating.setText(String.valueOf(daftar_item.get(position).getRating()));
            holder.tvDescription.setText(daftar_item.get(position).getDeskripsi());
            holder.tvstock.setText("Stok\t\t\t\t: " + daftar_item.get(position).getStok());
            holder.tvbrand.setText("Merek\t\t\t: " + daftar_item.get(position).getMerek());
            holder.tvcategory.setText("Kategori\t: " + daftar_item.get(position).getKategori());
            LinearLayout linearLayout = holder.gImages.findViewById(R.id.gImages);

            if (linearLayout.getChildCount() == 0){
                String[] gambar = daftar_item.get(position).getDaftar_gambar().toArray(new String[0]);
                for (String item : gambar) {
                    ImageView imageView = new ImageView(holder.gImages.getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 200);
                    layoutParams.setMargins(8, 0, 8, 0);

                    imageView.setLayoutParams(layoutParams);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    linearLayout.addView(imageView);
                    new AmbilGambar(imageView).execute(item);

                    imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            int tinggi = imageView.getHeight();
                            float rasio = (float) tinggi / 200;
                            int lebar = (int) (rasio * tinggi);
                            layoutParams.width = lebar;
                            imageView.setLayoutParams(layoutParams);
                        }
                    });
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return daftar_item.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        ImageView ivThumbnail;
        LinearLayout gImages;
        TextView tvTitle, tvPrice, tvRating,tvDescription, tvstock, tvbrand, tvcategory;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvstock = itemView.findViewById(R.id.tvstock);
            tvbrand = itemView.findViewById(R.id.tvbrand);
            tvcategory = itemView.findViewById(R.id.tvcategory);
            gImages = itemView.findViewById(R.id.gImages);
        }
    }
}
