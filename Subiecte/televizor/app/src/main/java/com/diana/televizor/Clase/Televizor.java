package com.diana.televizor.Clase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "televizoare")
public class Televizor implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "nr_inventar")
    private Integer nrInventar;

    @ColumnInfo(name = "producator")
    private String producator;

    @ColumnInfo(name = "diagonala")
    private Integer diagonala;

    @ColumnInfo(name = "proprietar")
    private String proprietar;

    @ColumnInfo(name = "data_intrarii")
    private Date dataIntrarii;

    public Televizor(long id, Integer nrInventar, String producator, Integer diagonala, String proprietar, Date dataIntrarii) {
        this.id = id;
        this.nrInventar = nrInventar;
        this.producator = producator;
        this.diagonala = diagonala;
        this.proprietar = proprietar;
        this.dataIntrarii = dataIntrarii;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Ignore
    public Televizor(Integer nrInventar, String producator, Integer diagonala, String proprietar, Date dataIntrarii) {
        this.nrInventar = nrInventar;
        this.producator = producator;
        this.diagonala = diagonala;
        this.proprietar = proprietar;
        this.dataIntrarii = dataIntrarii;
    }

    public Integer getNrInventar() {
        return nrInventar;
    }

    public void setNrInventar(Integer nrInventar) {
        this.nrInventar = nrInventar;
    }

    public String getProducator() {
        return producator;
    }

    public void setProducator(String producator) {
        this.producator = producator;
    }

    public Integer getDiagonala() {
        return diagonala;
    }

    public void setDiagonala(Integer diagonala) {
        this.diagonala = diagonala;
    }

    public String getProprietar() {
        return proprietar;
    }

    public void setProprietar(String proprietar) {
        this.proprietar = proprietar;
    }

    public Date getDataIntrarii() {
        return dataIntrarii;
    }

    public void setDataIntrarii(Date dataIntrarii) {
        this.dataIntrarii = dataIntrarii;
    }

    @Override
    public String toString() {
        return "Televizor{" +
                "nrInventar=" + nrInventar +
                ", producator='" + producator + '\'' +
                ", diagonala=" + diagonala +
                ", proprietar='" + proprietar + '\'' +
                ", dataIntrarii=" + dataIntrarii +
                '}';
    }
}
