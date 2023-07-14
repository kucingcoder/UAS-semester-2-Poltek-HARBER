package com.example.uassemester2.Controller;
import com.example.uassemester2.Model.Produk;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shohibulcoding.httpclient.Klien;
import org.shohibulcoding.httpclient.Metode;

public class ParsingData {
    private ArrayList<Produk> daftar_produk = new ArrayList<>();

    public ParsingData() throws Exception{
        Klien akses = new Klien();
        akses.permintaan.set_Link("https://dummyjson.com/products/category/smartphones");
        akses.permintaan.set_Permintaan(Metode.GET);
        akses.permintaan.tambah_Header("X-Cons-ID","harber123");
        akses.permintaan.tambah_Header("X-userkey","_tabc4XbR");
        akses.eksekusi();

        JSONObject full = new JSONObject(akses.respon.get_Data());
        JSONArray produk_produk = full.getJSONArray("products");
        for (int i = 0; i < produk_produk.length(); i++) {
            JSONObject objek_sementara = produk_produk.getJSONObject(i);
            Produk produk_sementara = new Produk();
            produk_sementara.setId(objek_sementara.getInt("id"));
            produk_sementara.setHarga(objek_sementara.getInt("price"));
            produk_sementara.setStok(objek_sementara.getInt("stock"));
            produk_sementara.setRating(objek_sementara.getDouble("rating"));
            produk_sementara.setDiskon(objek_sementara.getDouble("discountPercentage"));
            produk_sementara.setJudul(objek_sementara.getString("title"));
            produk_sementara.setDeskripsi(objek_sementara.getString("description"));
            produk_sementara.setMerek(objek_sementara.getString("brand"));
            produk_sementara.setThumbnail(objek_sementara.getString("thumbnail"));
            produk_sementara.setKategori(objek_sementara.getString("category"));

            JSONArray daftar_gambar_sementara = objek_sementara.getJSONArray("images");
            for (int j = 0; j < daftar_gambar_sementara.length(); j++) {
                produk_sementara.TambahGambar(daftar_gambar_sementara.getString(j));
            }

            daftar_produk.add(produk_sementara);
        }
    }

    public ArrayList<Produk> getDaftar_produk(){
        return daftar_produk;
    }
}
