package com.sglbl.abroadguideforstudents.ui.transportation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransportationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TransportationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}