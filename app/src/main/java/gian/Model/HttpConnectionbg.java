package gian.Model;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class HttpConnectionbg extends AsyncTask<String, Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... strings) {
        String myImageUrl = strings[0];

        URLConnection connection = null;
        boolean esImagen=false;
        try {
            connection = new URL(myImageUrl).openConnection();
            String contentType = connection.getHeaderField("Content-Type");
            if(contentType!=null){
                if(!contentType.isEmpty()){
                    if(contentType.startsWith("image/")){
                        esImagen=true;
                    }else{
                        esImagen=false;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return esImagen;
    }

}
