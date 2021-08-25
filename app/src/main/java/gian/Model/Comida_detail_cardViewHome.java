package gian.Model;

public class Comida_detail_cardViewHome {

    private String nombre_comidaHome;
    private String imagen_comidaHome;
    private String calorias_comidaHome;


    public Comida_detail_cardViewHome(String nombre_comidaHome, String imagen_comidaHome, String calorias_comidaHome) {
        this.nombre_comidaHome = nombre_comidaHome;
        this.imagen_comidaHome = imagen_comidaHome;
        this.calorias_comidaHome = calorias_comidaHome;
    }

    public String getNombre_comidaHome() {
        return nombre_comidaHome;
    }

    public void setNombre_comidaHome(String nombre_comidaHome) {
        this.nombre_comidaHome = nombre_comidaHome;
    }

    public String getImagen_comidaHome() {
        return imagen_comidaHome;
    }

    public void setImagen_comidaHome(String imagen_comidaHome) {
        this.imagen_comidaHome = imagen_comidaHome;
    }

    public String getCalorias_comidaHome() {
        return calorias_comidaHome;
    }

    public void setCalorias_comidaHome(String calorias_comidaHome) {
        this.calorias_comidaHome = calorias_comidaHome;
    }
}
