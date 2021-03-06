package sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.scheduleprogram.util.Util;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_PROGRAM;

/**
 * Created by thushara on 9/26/2017.
 */

public class ModifyScheduleDelegate  extends AsyncTask<ProgramSlot, Void, Boolean> {
    // Tag for logging
    private static final String TAG = ModifyScheduleDelegate.class.getName();

    private final ScheduleController scheduleController;
    public ModifyScheduleDelegate(ScheduleController scheduleController) {

       this.scheduleController=scheduleController;
    }


    @Override
    protected Boolean doInBackground(ProgramSlot... params) {
        Uri builtUri = Uri.parse(PRMS_BASE_URL_SCHEDULE_PROGRAM).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"update").buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        JSONObject json = new JSONObject();
        try {
            JSONObject radioProgramObject = new JSONObject();
            radioProgramObject.put("name",params[0].getRadioProgram().getRadioProgramName());
            json.put("radioProgram", radioProgramObject);
            json.put("dateOfProgram", Util.convertProgramDateToJSONString(params[0].getScheduleDate()));
            json.put("duration", params[0].getScheduleDuration());
            json.put("startTime", Util.convertProgramTimeToJSONString(params[0].getScheduleStartTime()));
            json.put("presenter", params[0].getPresenter());
            json.put("producer", params[0].getProducer());
            json.put("id", params[0].getId());
            Log.v("Update json", json.toString());
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }

        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dos = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setDoOutput(true);
            dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeUTF(json.toString());
            dos.write(512);
            Log.v(TAG, "Http POST response " + httpURLConnection.getResponseCode());
            if(httpURLConnection.getResponseCode()==201)
            success = true;
        } catch (IOException exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
            if (dos != null) {
                try {
                    dos.flush();
                    dos.close();
                } catch (IOException exception) {
                    Log.v(TAG, exception.getMessage());
                }
            }
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return new Boolean(success);
    }
    @Override
    protected void onPostExecute(Boolean result) {
        scheduleController.scheduleUpdated(result.booleanValue());
    }
}
