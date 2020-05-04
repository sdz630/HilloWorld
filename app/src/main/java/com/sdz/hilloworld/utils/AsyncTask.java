package com.sdz.hilloworld.utils;

public class AsyncTask<T,X,Y> {
    public AsyncTask(T t, X x,Y y) {

    }

    public void execute(){
        onPreExecute();
        onPostExecute(doInBackground());
    }

    protected void onPreExecute(){

    }

    protected Boolean doInBackground(Void... params){
        return false;
    }

    protected void onPostExecute(boolean isSuccess){

    }
}
