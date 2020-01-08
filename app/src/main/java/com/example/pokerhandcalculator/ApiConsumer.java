package com.example.pokerhandcalculator;

import android.content.Context;
import android.util.Log;
import android.widget.Adapter;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ApiConsumer {

    public void callApi(final RankingActivity context){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String URL = "https://poker-hand-calculator.herokuapp.com/solveround";
            final RoundSerializer roundFormatter = new RoundSerializer();
            final String requestBody = roundFormatter.getJSONOf(Round.getInstance()).toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
                    context.setRanksAdapter();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
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
