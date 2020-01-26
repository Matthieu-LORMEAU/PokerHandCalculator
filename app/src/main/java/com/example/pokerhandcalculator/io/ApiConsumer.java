package com.example.pokerhandcalculator.io;

import android.app.ProgressDialog;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokerhandcalculator.activities.GameActivity;
import com.example.pokerhandcalculator.business.NullCardException;
import com.example.pokerhandcalculator.business.Round;
import com.example.pokerhandcalculator.activities.RankingActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ApiConsumer {

    // notre API est sur Heroku et "s'endort" au bout de 30 min d'inactivité,
    // ce qui fait que la premiere requete met du temps a aboutir, donc on envoie
    // une requete au lancement e lappli pour reveiller le serveur
    public void wakeUpServer(final GameActivity context) {
        Volley.newRequestQueue(context)
                .add(
                        new StringRequest(
                                Request.Method.GET,
                                "https://poker-hand-calculator.herokuapp.com/solveround",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }
                        ));
    }

    public void callApi(final RankingActivity context){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String URL = "https://poker-hand-calculator.herokuapp.com/solveround";
            final RoundSerializer roundFormatter = new RoundSerializer();
            final String requestBody = roundFormatter.getJSONOf(Round.getInstance()).toString();

            final ProgressDialog progress = new ProgressDialog(context);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
// To dismiss the dialog


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    try {
                        JSONObject result = new JSONObject(response);
                        JSONRankingParser parser = new JSONRankingParser();
                        parser.parse(result);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    VolleyLog.v(response);
                    Round.getInstance().sortPlayersByRanking();
                    progress.dismiss();
                    context.setRanksAdapter();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    progress.dismiss();
                    Toast errorToast = Toast.makeText(context, "Could not connect to the server", Toast.LENGTH_LONG);
                    errorToast.show();
                    context.finish();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return super.parseNetworkResponse(response);// Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException | NullCardException e) {
            e.printStackTrace();
        }
    }
}
