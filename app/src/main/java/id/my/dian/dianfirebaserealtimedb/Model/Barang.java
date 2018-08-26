package id.my.dian.dianfirebaserealtimedb.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;


@IgnoreExtraProperties
public class Barang implements Serializable{

    private String nama;
    private String makan;
    private String harga;
    private String porsi;
    private String totharga;
    private String bayar;
    private String kembali;
    private String key;

    public Barang(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMerk() {
        return makan;
    }

    public void setMerk(String merk) {
        this.makan = merk;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTotharga() {
        return totharga;
    }

    public void setTotharga(String totharga) { this.totharga = totharga; }

    public String getPorsi() {
        return porsi;
    }

    public void setPorsi(String porsi) {
        this.porsi = porsi;
    }

    public String getBayar() {
        return bayar;
    }

    public void setBayar(String bayar) {
        this.bayar = bayar;
    }

    public String getKembali() {
        return kembali;
    }

    public void setKembali(String kembali) {
        this.kembali = kembali;
    }




    @Override
    public String toString() {
        return " "+nama+"\n" +
                " "+makan +"\n" +
                " "+porsi+"\n" +
                " "+totharga +"\n" +
                " "+bayar+"\n" +
                " "+kembali+"\n" +
                " "+harga;
    }

    public Barang(String nm, String mrk, String hrg, String prs, String tot, String byr, String kbl){
        nama = nm;
        makan = mrk;
        harga = hrg;
        porsi = prs;
        totharga = tot;
        bayar = byr;
        kembali = kbl;
    }
}
