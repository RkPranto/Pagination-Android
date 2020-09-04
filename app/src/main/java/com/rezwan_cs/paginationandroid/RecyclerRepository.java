package com.rezwan_cs.paginationandroid;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class RecyclerRepository {

    IRecyclerRepository listener;
    public RecyclerRepository(IRecyclerRepository listener){
        this.listener = listener;
    }

    public void getDataFromBackend(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ObjectClass> objectClassList = new ArrayList<>();
                for(int i = 0;i<10;i++){
                    objectClassList.add(new ObjectClass(i+1+"", Math.floor(Math.random()*100)+""));
                }
                listener.getObjectAdded(objectClassList);
            }
        }, 5000);
    }

}
