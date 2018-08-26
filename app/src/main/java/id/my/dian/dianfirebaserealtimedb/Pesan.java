package id.my.dian.dianfirebaserealtimedb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import id.my.dian.dianfirebaserealtimedb.Model.Barang;

public class Pesan extends AppCompatActivity {
    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference database;
    // variable fields EditText dan Button
    private Button btSubmit;
    private EditText etNama;
    private EditText etMerk;
    private EditText etHarga;
    private EditText etporsi;
    private EditText ettotharga;

    private Button btnProses;
    private TextView txttotalbelanja;
    private TextView txtuangkembali;
    private TextView txtketerangan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        // inisialisasi fields EditText dan Button
        etNama = (EditText) findViewById(R.id.et_nobarang);
        etMerk = (EditText) findViewById(R.id.et_namabarang);
        etHarga = (EditText) findViewById(R.id.et_hargabarang);
        etporsi = (EditText ) findViewById(R.id.et_totalbarang);
        ettotharga =(EditText) findViewById(R.id.et_totalharga);

        btnProses = (Button) findViewById(R.id.tombol1);
        txttotalbelanja= (TextView) findViewById(R.id.totalbelanja);
        txtuangkembali = (TextView) findViewById(R.id.uangkembali);
        txtketerangan = (TextView) findViewById(R.id.keterangan);
        btSubmit = (Button) findViewById(R.id.bt_submit);

    btnProses.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            String porsi = etporsi.getText().toString().trim();
            String harga= etporsi.getText().toString().trim();
            String totharga = etporsi.getText().toString().trim();

            double po = Double.parseDouble(porsi);
            double h = Double.parseDouble(harga);
            double th = Double.parseDouble(totharga);
            double total = (po * h);
            txttotalbelanja.setText("total harga : " + total);

                    double UangKembalian = (th - total);

             if (th < total){
             txtketerangan.setText("Keterangan : uang bayar kurang Rp"+ (-UangKembalian));
             txtuangkembali.setText("uang Kembali : Rp 0");
        }else {
                 txtketerangan.setText("Keterangan : Terimakasih");
                 txtuangkembali.setText("Uang Kembali : " + UangKembalian);

             }
        }
    });
        // mengambil referensi ke Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        final Barang barang = (Barang) getIntent().getSerializableExtra("data");

        if (barang != null) {
            etNama.setText(barang.getNama());
            etMerk.setText(barang.getMerk());
            etHarga.setText(barang.getHarga());
            etporsi.setText(barang.getPorsi());
            ettotharga.setText(barang.getTotharga());
            txttotalbelanja.setText(barang.getBayar());
            txtuangkembali.setText(barang.getKembali());
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barang.setNama(etNama.getText().toString());
                    barang.setMerk(etMerk.getText().toString());
                    barang.setHarga(etHarga.getText().toString());
                    barang.setPorsi(etporsi.getText().toString());
                    barang.setTotharga(ettotharga.getText().toString());
                    barang.setBayar(txttotalbelanja.getText().toString());
                    barang.setKembali(txtuangkembali.getText().toString());

                    updateBarang(barang);
                }
            });
        } else {
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isEmpty(etNama.getText().toString()) && !isEmpty(etMerk.getText().toString()) && !isEmpty(etHarga.getText().toString()))
                        submitBarang(new Barang(etNama.getText().toString(), etMerk.getText().toString(), etHarga.getText().toString(),etporsi.getText().toString(),ettotharga.getText().toString(), txttotalbelanja.getText().toString()  , txtuangkembali.getText().toString()));
                    else
                        Snackbar.make(findViewById(R.id.bt_submit), "Data barang tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            etNama.getWindowToken(), 0);
                }
            });
        }
    }

    private boolean isEmpty(String s){
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void updateBarang(Barang barang) {
        /**
         * Baris kode yang digunakan untuk mengupdate data barang
         * yang sudah dimasukkan di Firebase Realtime Database
         */
        database.child("barang") //akses parent index, ibaratnya seperti nama tabel
                .child(barang.getKey()) //select barang berdasarkan key
                .setValue(barang) //set value barang yang baru
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        /**
                         * Baris kode yang akan dipanggil apabila proses update barang sukses
                         */
                        Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil diupdatekan", Snackbar.LENGTH_LONG).setAction("Oke", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        }).show();
                    }
                });
    }

    private void submitBarang(Barang barang) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        database.child("barang").push().setValue(barang).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etNama.setText("");
                etMerk.setText("");
                etHarga.setText("");
                etporsi.setText("");
                ettotharga.setText("");
                txttotalbelanja.setText("");
                txtuangkembali.setText("");
                txtketerangan.setText("");
                Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, Pesan.class);
    }
}