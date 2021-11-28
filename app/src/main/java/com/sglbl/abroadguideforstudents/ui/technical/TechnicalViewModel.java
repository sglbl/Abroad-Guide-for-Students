package com.sglbl.abroadguideforstudents.ui.technical;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TechnicalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TechnicalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}