package SpoonacularClases;

import java.util.List;

public class UsedIngredient {

    public int id;
    public double amount;
    public String unit;
    public String unitLong;
    public String unitShort;
    public String aisle;
    public String name;
    public String original;
    public String originalString;
    public String originalName;
    public List<String> metaInformation;
    public List<String> meta;
    public String image;

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getUnitLong() {
        return unitLong;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public String getAisle() {
        return aisle;
    }

    public String getName() {
        return name;
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

    public List<String> getMetaInformation() {
        return metaInformation;
    }

    public List<String> getMeta() {
        return meta;
    }

    public String getImage() {
        return image;
    }
}
