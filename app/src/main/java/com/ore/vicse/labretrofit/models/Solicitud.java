package com.ore.vicse.labretrofit.models;

public class Solicitud {

    private Integer id;
    private String correo;
    private String tipo;
    private String motivo;
    private String voucher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "id=" + id +
                ", correo='" + correo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", motivo='" + motivo + '\'' +
                ", voucher='" + voucher + '\'' +
                '}';
    }
}
