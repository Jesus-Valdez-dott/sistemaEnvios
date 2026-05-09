/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moduloEnvios.dto;

public class EnvioDTO {

    private long idEnvio;
    private String destino;
    private String estado;

    public EnvioDTO() {
    }

    public EnvioDTO(long idEnvio, String destino, String estado) {
        this.idEnvio = idEnvio;
        this.destino = destino;
        this.estado = estado;
    }

    public long getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(long idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}