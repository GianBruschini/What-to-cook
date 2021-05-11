package gian.Model;

public class SpoonItem {

    private String nombreReceta;
    private int id;
    private String imagenUrl;
    private int timeReady;

    public SpoonItem(){

    }


    public SpoonItem(int id, String nombreReceta, int timeReady,String imagenUrl) {
        this.id = id;
        this.nombreReceta = nombreReceta;
        this.timeReady = timeReady;
        this.imagenUrl = imagenUrl;
    }

    public int getId() {
        return id;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public int getTimeReady() {
        return timeReady;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }
}
