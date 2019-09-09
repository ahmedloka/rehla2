package com.NativeTech.rehla.adapters;


public class filterAreaModelRecycler {


    public static final int TEXT_TYPE=0;




//    public String MerchantLogo;
    public final String CityName;
    public final String flag;
    private final String data;
    public int id;
//    public String categoryname;
//    public String fristDesc;
   // public String getLocation;




    public filterAreaModelRecycler(String CityName, String flag, String data)
    {
        this.CityName          =      CityName;
        this.flag              =      flag;
        this.data              =      data;

    }
    public filterAreaModelRecycler(String CityName, String flag, String data, int id)
    {
        this.CityName       =      CityName;
        this.flag              =      flag;
        this.data              =      data;
        this.id              =      id;

    }


}
