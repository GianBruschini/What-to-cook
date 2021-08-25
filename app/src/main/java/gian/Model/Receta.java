package gian.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Receta {

    private ArrayList<Receipe>hits;

    public ArrayList<Receipe> getHits() {
        return hits;
    }

    public void setHits(ArrayList<Receipe> hits) {
        this.hits = hits;
    }
}
