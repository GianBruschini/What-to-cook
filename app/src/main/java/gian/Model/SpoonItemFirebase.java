package gian.Model;

public class SpoonItemFirebase {
    private String nombreReceta;
    private String id;
    private String imagenUrl;
    private String timeReady;

    public SpoonItemFirebase() {

    }

    public SpoonItemFirebase(String nombreReceta, String id, String imagenUrl,String timeReady) {
        this.nombreReceta = nombreReceta;
        this.id = id;
        this.imagenUrl = imagenUrl;
        this.timeReady = timeReady;
    }

    public String getTimeReady() {
        return timeReady;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public String getId() {
        return id;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }
}
