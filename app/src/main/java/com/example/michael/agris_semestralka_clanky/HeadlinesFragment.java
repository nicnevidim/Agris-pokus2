package com.example.michael.agris_semestralka_clanky;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class HeadlinesFragment extends ListFragment {
    private ArticlesList articlesList;
    private static final String PARCELABLE_KEY="parkey";
    private static final String DEBUG_TAG = "DEBUG_TAG";
    private static final String TAG_CLANKY = "clanky";
    private static final String TAG_ID = "id_text";
    private static final String TAG_HEADLINE = "nazev";
    private static final String TAG_PEREX = "perex";

    private OnFragmentInteractionListener mListener;

    private ProgressDialog pDialog;
    private ArrayAdapter mAdapter;
    private SimpleAdapter sAdapter;


    public static HeadlinesFragment newInstance(ArticlesList articlesList) {
        HeadlinesFragment fragment = new HeadlinesFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARCELABLE_KEY);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HeadlinesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        triggerDownload("http://develop.agris.cz/dalsi-novinky?vratmi=json");



    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
            mListener.onFragmentInteraction(ArticlesList.ITEMS.get(position).id);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    // When user clicks button, calls AsyncTask.
    // Before attempting to fetch the URL, makes sure that there is a network connection.
    public void triggerDownload(String stringUrl) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadHeadlines().execute(stringUrl);
        } else {
            //showToast("No connection to internet available");
        }
    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class DownloadHeadlines extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Načítám data...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... urls) {

            String jsonStr = null;
            // params comes from the execute() call: params[0] is the url.
            try {
                jsonStr = downloadUrl(urls[0]);
            } catch (IOException e) {
                Log.e("ServiceHandler", "problem");
                return null;
            }

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray articles = null;
                    // Getting JSON Array node
                    articles = jsonObj.getJSONArray(TAG_CLANKY);

                    // looping through All Contacts
                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject c = articles.getJSONObject(i);

                        String id = c.getString(TAG_ID);
                        String headline = c.getString(TAG_HEADLINE);
                        String perex = c.getString(TAG_PEREX);

                        // tmp hashmap for single contact
                        ArticlesList.ArticleItem articleItem=new ArticlesList.ArticleItem(id,headline,perex);
                        articlesList.addArticleItem(articleItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            mAdapter = new ArrayAdapter<ArticlesList.ArticleItem>(getActivity(),
                    android.R.layout.two_line_list_item, android.R.id.text1, articlesList.ITEMS);
            setListAdapter(mAdapter);




        }
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
           String contentAsString = convertStreamToString(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}
