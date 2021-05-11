package SpoonacularClases;

import java.util.List;

public class ExtendedIngredient {
    public int id;
    public String aisle;
    public String image;
    public String consistency;
    public String name;
    public String nameClean;
    public String original;
    public String originalString;
    public String originalName;
    public double amount;
    public String unit;
    public List<Object> meta;
    public List<Object> metaInformation;
    public Measures measures;


    public int getId() {
        return id;
    }

    public String getAisle() {
        return aisle;
    }

    public String getImage() {
        return image;
    }

    public String getConsistency() {
        return consistency;
    }

    public String getName() {
        return name;
    }

    public String getNameClean() {
        return nameClean;
    }

    public String getOriginal() {
        return original;
    }

    public String getOriginalString() {
        return originalString;
    }

    public String getOriginalName() {
        return originalName;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public List<Object> getMeta() {
        return meta;
    }

    public List<Object> getMetaInformation() {
        return metaInformation;
    }

    public Measures getMeasures() {
        return measures;
    }
}
