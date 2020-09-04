package com.rezwan_cs.paginationandroid;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class RecyclerViewModel extends ViewModel implements IRecyclerRepository {
    RecyclerRepository recyclerRepository = new RecyclerRepository(this);
    MutableLiveData<List<ObjectClass>> listMutableLiveData = new MutableLiveData<>();
    IMainActivity viewListener;

    public void setUpViewListener(IMainActivity viewListener){
        this.viewListener = viewListener;
    }

    public void fetchNewData(){
        viewListener.setProgressBarVisibility(View.VISIBLE);
        recyclerRepository.getDataFromBackend();
    }

    @Override
    public void getObjectAdded(List<ObjectClass> objectClassList) {
        listMutableLiveData.setValue(objectClassList);
        viewListener.setProgressBarVisibility(View.GONE);
    }


    public MutableLiveData<List<ObjectClass>> getObjectList(){
        return listMutableLiveData;
    }
}
