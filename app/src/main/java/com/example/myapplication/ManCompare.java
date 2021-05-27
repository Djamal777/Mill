package com.example.myapplication;

import java.util.Comparator;

public class ManCompare implements Comparator<Man> {

    @Override
    public int compare(Man o1, Man o2) {
        if(o1.getMoney()> o2.getMoney())
            return -1;
        else if(o1.getMoney()< o2.getMoney())
            return 1;
        else
            return 0;
    }
}
