package com.example.shitenshi.livres;

import java.io.Serializable;

/**
 * Created by masuken111 on 2017/11/15.
 */

public class DbContainer implements Serializable {
    public DbContainer( String category, String productname, int price, int remainingmoney,long time){
        this.category = category;
        this.productname = productname;
        this.price = price;
        this.remainingmoney = remainingmoney;
        this.time = time;

    }
    String category;
    String productname;
    int price;
    int remainingmoney;
    long time;

}

