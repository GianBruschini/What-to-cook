package gian.Model;

import com.google.gson.annotations.SerializedName;

public class TotalNutrients {

    @SerializedName("ENERC_KCAL")
    public ENERCKCAL eNERC_KCAL;
    @SerializedName("FAT")
    public FAT fAT;
    @SerializedName("FASAT")
    public FASAT fASAT;
    @SerializedName("FATRN")
    public FATRN fATRN;
    @SerializedName("FAMS")
    public FAMS fAMS;
    @SerializedName("FIBTG")
    public FIBTG fIBTG;
    @SerializedName("SUGAR")
    public SUGAR sUGAR;
    @SerializedName("PROCNT")
    public PROCNT pROCNT;
    @SerializedName("CHOLE")
    public CHOLE cHOLE;
    @SerializedName("NA")
    public NA nA;
    @SerializedName("CA")
    public CA cA;
    @SerializedName("K")
    public K k;
    @SerializedName("VITC")
    public VITC vITC;
    @SerializedName("VITD")
    public VITD vITD;


    public ENERCKCAL geteNERC_KCAL() {
        return eNERC_KCAL;
    }

    public void seteNERC_KCAL(ENERCKCAL eNERC_KCAL) {
        this.eNERC_KCAL = eNERC_KCAL;
    }

    public FAT getfAT() {
        return fAT;
    }

    public void setfAT(FAT fAT) {
        this.fAT = fAT;
    }

    public FASAT getfASAT() {
        return fASAT;
    }

    public void setfASAT(FASAT fASAT) {
        this.fASAT = fASAT;
    }

    public FATRN getfATRN() {
        return fATRN;
    }

    public void setfATRN(FATRN fATRN) {
        this.fATRN = fATRN;
    }

    public FAMS getfAMS() {
        return fAMS;
    }

    public void setfAMS(FAMS fAMS) {
        this.fAMS = fAMS;
    }

    public FIBTG getfIBTG() {
        return fIBTG;
    }

    public void setfIBTG(FIBTG fIBTG) {
        this.fIBTG = fIBTG;
    }

    public SUGAR getsUGAR() {
        return sUGAR;
    }

    public void setsUGAR(SUGAR sUGAR) {
        this.sUGAR = sUGAR;
    }

    public PROCNT getpROCNT() {
        return pROCNT;
    }

    public void setpROCNT(PROCNT pROCNT) {
        this.pROCNT = pROCNT;
    }

    public CHOLE getcHOLE() {
        return cHOLE;
    }

    public void setcHOLE(CHOLE cHOLE) {
        this.cHOLE = cHOLE;
    }

    public NA getnA() {
        return nA;
    }

    public void setnA(NA nA) {
        this.nA = nA;
    }

    public CA getcA() {
        return cA;
    }

    public void setcA(CA cA) {
        this.cA = cA;
    }

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    public VITC getvITC() {
        return vITC;
    }

    public void setvITC(VITC vITC) {
        this.vITC = vITC;
    }

    public VITD getvITD() {
        return vITD;
    }

    public void setvITD(VITD vITD) {
        this.vITD = vITD;
    }
}
