package com.diana.bilet_monitor.Clase;

import java.io.Serializable;
import java.util.Date;

public class Monitor implements Serializable {
    private Integer nrInventar;
    private String producator;
    private Integer diagonala;
    private String proprietar;
    private Date dataIntrarii;

    public Monitor() {}

    public Monitor(Integer nrInventar, String producator, Integer diagonala, String proprietar, Date dataIntrarii) {
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
        return "Monitor{" +
                "nrInventar=" + nrInventar +
                ", producator='" + producator + '\'' +
                ", diagonala=" + diagonala +
                ", proprietar='" + proprietar + '\'' +
                ", dataIntrarii=" + dataIntrarii +
                '}';
    }
}
