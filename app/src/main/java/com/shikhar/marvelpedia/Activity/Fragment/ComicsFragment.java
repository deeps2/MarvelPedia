package com.shikhar.marvelpedia.Activity.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shikhar.marvelpedia.Activity.Adapter.ComicsAdapter;
import com.shikhar.marvelpedia.Activity.Interface.MarvelInterface;
import com.shikhar.marvelpedia.Activity.ModelComics.ComicsResponse;
import com.shikhar.marvelpedia.Activity.ModelComics.Result;
import com.shikhar.marvelpedia.Activity.SearchActivity;
import com.shikhar.marvelpedia.BuildConfig;
import com.shikhar.marvelpedia.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComicsFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.empty_result)
    TextView emptyText;

    private static String PRIVATE_API_KEY = BuildConfig.PRIVATE_API_KEY;
    private static String PUBLIC_API_KEY = BuildConfig.PUBLIC_API_KEY;
    String url = "https://gateway.marvel.com:443/v1/public/";
    String search; //string which is to be searched. contains starting characters of any marvel comic's title

    List<Result> listOfComics = new ArrayList<>();
    ComicsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            //get the String which is to be searched and put that string as Title in AppBar
            search = bundle.getString("Search");
            getActivity().setTitle("Search->Comics->" + "\"" + search + "\"");
        }

        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new ComicsAdapter(listOfComics);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        //hide FAB if search string is null; else attach an OnClickListener to it
        if (search == null) {

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showSearchDialog();
                }
            });
        } else {
            fab.setVisibility(View.INVISIBLE);
        }

        getAllComics();

        return view;
    }

    //will return list of Comics after calling the API
    void getAllComics() {

        //create OkHttp Client and set timeout to 60 seconds
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        //create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        MarvelInterface serviceRequest = retrofit.create(MarvelInterface.class);

        //get MD5 hash. needed for calling the API. MD5(timestamp+PRIVATE_KEY+PUBLIC_KEY)
        //according to API doc, timeStamp should change with each request
        long timeStamp = System.currentTimeMillis();
        String timeStampString = Long.toString(timeStamp);
        String md5ApiKey = MD5(timeStamp + PRIVATE_API_KEY + PUBLIC_API_KEY);

        Call<ComicsResponse> call;

        if (search == null) //if search is null, fetch all Comics
            call = serviceRequest.getAllComics(timeStampString, PUBLIC_API_KEY, md5ApiKey);
        else //if search has a string fetch all Comics starting with that string
            call = serviceRequest.searchComics(search, timeStampString, PUBLIC_API_KEY, md5ApiKey);

        call.enqueue(new Callback<ComicsResponse>() {//will run on a background thread
            @Override
            public void onResponse(Call<ComicsResponse> call, Response<ComicsResponse> response) {

                int statusCode = response.code();
                if (statusCode != 200) {
                    Log.e("Status Code", statusCode + ""); //if status is not OK, then Log it and return
                    return;
                }

                //get the Results from the response body
                List<Result> listOfComics = response.body().getData().getResults();

                if (listOfComics.size() == 0)
                    emptyText.setVisibility(View.VISIBLE);

                //attach the result returned(listOfComics) to adapter and notify the adapter that Data Set has changed
                adapter.setDataAdapter(listOfComics);
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ComicsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //return MD5 String(required to make the call to API)
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    //shows a Dialog with Search option
    void showSearchDialog() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("SEARCH COMICS");
        alertDialog.setMessage("Enter  \"JUST few Starting\" letters of comics(Ex. Av for Avengers). DON'T enter full name to get best search results");
        alertDialog.setIcon(R.mipmap.ic_launcher);

        final EditText input = new EditText(getActivity());
        input.setHint("Enter text here then click SEARCH");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //when SEARCH is clicked, check the entered string and pass it to SearchActivity if its not empty
                String search = input.getText().toString().trim();
                if (search.length() <= 0)
                    dialog.cancel();
                else {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra("Search", search);
                    intent.putExtra("Fragment", "Comics");//Fragment will tell from which fragment search string has come to SearchActivity
                    startActivity(intent);
                }
            }
        });

        AlertDialog dialog = alertDialog.create();

        //set position of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        wmlp.y = 200;   //y position

        dialog.show();
    }
}
