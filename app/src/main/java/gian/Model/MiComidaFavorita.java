package gian.Model;

public class MiComidaFavorita {
    private String calorias;
    private String imagenURL;
    private String nombre;

    public  MiComidaFavorita(){

    }


    public MiComidaFavorita(String calorias, String imagenURL, String nombre) {
        this.calorias = calorias;
        this.imagenURL = imagenURL;
        this.nombre = nombre;
    }

    public String getCalorias() {
        return calorias;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public String getNombre() {
        return nombre;
    }
}
