package com.example.conferencepro;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class ApplicantRepository {
    public AccessDao dao;
    private LiveData<List<EntrantData>>allData;

    ApplicantRepository(Application application) {
        ApplicantDatabase db = ApplicantDatabase.getDatabase(application);
        dao = db.dao();
        try {
            allData = new getAllDataAsyncTask(dao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public LiveData<List<EntrantData>> getAllData(){
        return allData;
    }
    public LiveData<List<AllDataClass>> getAllDataWithInfo() throws ExecutionException, InterruptedException {
        return new getAllEntrantDataAsyncTaskWithInfo(dao).execute().get();
    }
    public LiveData<List<EntrantData>> getSpecificEntrantData(String string) throws ExecutionException, InterruptedException {
        return new getSpecificEntrantDataAsyncTask(dao).execute(string).get();
    }

    public List<ApplicantInfo> getSpecificApplicantInfo(EntrantData ed) throws ExecutionException, InterruptedException {
        return new getSpecificApplicantDataAsyncTask(dao).execute(ed.getUserData()).get();
    }
    public List<ApplicantInfo> getSpecificApplicantInfo(int i) throws ExecutionException, InterruptedException {
        return new getSpecificApplicantDataAsyncTask(dao).execute(i).get();
    }

    private static class getAllDataAsyncTask extends AsyncTask<Void, Void, LiveData<List<EntrantData>>> {

        private AccessDao mAsyncTaskDao;

        getAllDataAsyncTask(AccessDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<EntrantData>> doInBackground(Void... voids) {

            return mAsyncTaskDao.getAllData();
        }
    }


    private static class getSpecificTeamDataAsyncTask extends AsyncTask<String, Void, LiveData<List<AllDataClass>>> {

        private AccessDao mAsyncTaskDao;

        getSpecificTeamDataAsyncTask(AccessDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<AllDataClass>> doInBackground(String... strings) {

            return mAsyncTaskDao.getAllApplicantData("%"+strings[0]+"%");
        }
    }


    private static class insertTask extends AsyncTask<Object, Void, Void> {

        private AccessDao mAsyncTaskDao;

        insertTask(AccessDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            if(objects[0] instanceof EntrantData && objects[1] instanceof ApplicantInfo){
           mAsyncTaskDao.insert((EntrantData)objects[0],(ApplicantInfo) objects[1]);}
            return null;
        }
    }
    private static class deleteTask extends AsyncTask<EntrantData, Void, Void> {

        private AccessDao mAsyncTaskDao;

        deleteTask(AccessDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(EntrantData... objects) {
            mAsyncTaskDao.delete(objects);
            return null;
        }
    }
    private static class getSpecificApplicantDataAsyncTask extends AsyncTask<Integer, Void,List<ApplicantInfo>> {

        private AccessDao mAsyncTaskDao;

        getSpecificApplicantDataAsyncTask(AccessDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<ApplicantInfo>doInBackground(Integer... keys) {

            return mAsyncTaskDao.getApplicantData(keys);
        }
    }

    private static class getSpecificEntrantDataAsyncTask extends AsyncTask<String, Void, LiveData<List<EntrantData>>> {

        private AccessDao mAsyncTaskDao;

        getSpecificEntrantDataAsyncTask(AccessDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<EntrantData>> doInBackground(String... keys) {

            return mAsyncTaskDao.getSpecificEntrantData(keys[0]);
        }
    }

    private static class getAllEntrantDataAsyncTaskWithInfo extends AsyncTask<Void, Void, LiveData<List<AllDataClass>>> {

        private AccessDao mAsyncTaskDao;

        getAllEntrantDataAsyncTaskWithInfo(AccessDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<AllDataClass>> doInBackground(Void... keys) {

            return mAsyncTaskDao.getAllDataWithApplicantInfo();
        }
    }



}
