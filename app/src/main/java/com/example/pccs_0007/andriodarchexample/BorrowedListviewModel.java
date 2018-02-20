package com.example.pccs_0007.andriodarchexample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.pccs_0007.andriodarchexample.db.AppDatabase;
import com.example.pccs_0007.andriodarchexample.db.BorrowModel;

import java.util.List;

/**
 * Created by PCCS-0007 on 20-Feb-18.
 */

public class BorrowedListviewModel extends AndroidViewModel {

   AppDatabase appDatabase;
    private final LiveData<List<BorrowModel>> itemAndPersonList;

    public BorrowedListviewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
        itemAndPersonList = appDatabase.itemAndPersonModel().getAllBorrowedItems();

    }

    public LiveData<List<BorrowModel>> getItemAndPersonList(){
        return itemAndPersonList;
    }


    public void deleteItem(BorrowModel borrowModel)
    {
        new DeleteAsyncTask(appDatabase).execute(borrowModel);
    }

    private static class DeleteAsyncTask extends AsyncTask<BorrowModel,Void,Void>{
        AppDatabase appDatabase;
        DeleteAsyncTask(AppDatabase appDatabase){
            this.appDatabase = appDatabase;
        }
        @Override
        protected Void doInBackground(BorrowModel... borrowModels) {
            appDatabase.itemAndPersonModel().deleteBorrow(borrowModels[0]);
            return null;
        }
    }

}
