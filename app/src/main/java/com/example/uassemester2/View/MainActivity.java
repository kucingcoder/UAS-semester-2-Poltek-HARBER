package com.example.uassemester2.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uassemester2.Controller.AdapterData;
import com.example.uassemester2.Controller.ParsingData;
import com.example.uassemester2.Model.Produk;
import com.example.uassemester2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton reloader;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    LinearLayout loading;
    AdapterData adapterData;
    ParsingData parsingData;
    ArrayList<Produk> daftar_produknya;
    TextView judul;

    public static void selectionSort(ArrayList<Produk> produkList) {
        int n = produkList.size();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (produkList.get(j).getHarga() < produkList.get(minIndex).getHarga()) {
                    minIndex = j;
                }
            }
            Produk temp = produkList.get(minIndex);
            produkList.set(minIndex, produkList.get(i));
            produkList.set(i, temp);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading = findViewById(R.id.load);
        recyclerView = findViewById(R.id.rvData);
        daftar_produknya = new ArrayList<>();
        reloader = findViewById(R.id.reload_btn);
        judul = findViewById(R.id.judul);
        reloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                judul.setVisibility(View.GONE);
                reloader.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                MyAsyncTask task = new MyAsyncTask(MainActivity.this);
                task.execute();
            }
        });

        MyAsyncTask task = new MyAsyncTask(this);
        task.execute();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private final Context mContext;

        public MyAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                parsingData = new ParsingData();
                daftar_produknya = new ArrayList<Produk>();
                daftar_produknya = parsingData.getDaftar_produk();
                selectionSort(daftar_produknya);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapterData = new AdapterData(mContext, daftar_produknya);
            recyclerView.setAdapter(adapterData);
            adapterData.notifyDataSetChanged();
            loading.setVisibility(View.GONE);
            judul.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            reloader.setVisibility(View.VISIBLE);
        }
    }
}