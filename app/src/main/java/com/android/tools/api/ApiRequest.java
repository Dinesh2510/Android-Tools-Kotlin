package com.android.tools.api;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.tools.utils.Tools;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiRequest {

    public static void callApi(final Context context, String url, JSONObject jsonObject,
                               final Callback callback){
        Log.d(Tools.tag,url);
        Log.d(Tools.tag,jsonObject.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(Tools.tag,response.toString());

                        if(callback!=null)
                            callback .response(response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Tools.tag,error.toString());

                if(callback!=null)
                    callback .response(error.toString());

            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                Log.d(Tools.tag,headers.toString());
                return headers;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(240000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjReq);
    }


    public static void callApiGetRequest(final Activity context, final String url,
                                         final Callback callback) {

        Log.d(Tools.tag,url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        final String[] urlsplit = url.split("/");
                        Log.d(Tools.tag + urlsplit[urlsplit.length - 1], response.toString());

                        Log.d(Tools.tag,response.toString());

                        if(callback!=null)
                            callback .response(response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                final String[] urlsplit = url.split("/");
                Log.d(Tools.tag + urlsplit[urlsplit.length - 1], error.toString());


                if (callback != null)
                    callback.response(error.toString());

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                Log.d(Tools.tag, headers.toString());
                return headers;
            }
        };

        try {
            if (context != null) {
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.getCache().clear();
                requestQueue.add(jsonObjReq);
            }
        } catch (Exception e) {
            Log.d(Tools.tag, e.toString());
        }
    }



}
