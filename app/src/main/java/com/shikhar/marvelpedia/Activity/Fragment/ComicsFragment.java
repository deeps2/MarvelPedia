package com.shikhar.marvelpedia.Activity.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shikhar.marvelpedia.Activity.Adapter.ComicsAdapter;
import com.shikhar.marvelpedia.Activity.Interface.MarvelInterface;
import com.shikhar.marvelpedia.Activity.ModelComics.ComicsResponse;

import com.shikhar.marvelpedia.Activity.ModelComics.Result;
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

    private static String PRIVATE_API_KEY = BuildConfig.PRIVATE_API_KEY;
    private static String PUBLIC_API_KEY = BuildConfig.PUBLIC_API_KEY;
    String url = "https://gateway.marvel.com:443/v1/public/";

    List<Result> listOfComics = new ArrayList<>();
    ComicsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new ComicsAdapter(listOfComics);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        getAllComics();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
            }
        });

        return view;
    }

    void getAllComics() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        MarvelInterface serviceRequest = retrofit.create(MarvelInterface.class);

        long timeStamp = System.currentTimeMillis();
        String timeStampString = Long.toString(timeStamp);
        String md5ApiKey = MD5(timeStamp + PRIVATE_API_KEY + PUBLIC_API_KEY);

        Call<ComicsResponse> call = serviceRequest.getAllComics(timeStampString, PUBLIC_API_KEY, md5ApiKey);

        call.enqueue(new Callback<ComicsResponse>() {
            @Override
            public void onResponse(Call<ComicsResponse> call, Response<ComicsResponse> response) {

                int statusCode = response.code();

                if (statusCode != 200) {
                    //TODO emptyView.setVisibility(View.INVISIBLE);
                    //TODO progressBar.setVisibility(View.INVISIBLE);
                    //TODO noData.setVisibility(View.VISIBLE);
                    //TODO noNet.setVisibility(View.INVISIBLE);
                    return;
                }

                List<Result> listOfComics = response.body().getData().getResults();
                //TODO check if list is 0 then show no data

                adapter.setDataAdapter(listOfComics);
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
                // TODO emptyView.setVisibility(View.INVISIBLE);
                // TODO noData.setVisibility(View.INVISIBLE);
                // TODO noNet.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ComicsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

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
        }
        return null;
    }

    //shows a Dailog with Search option
    void showSearchDialog() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("SEARCH COMICS");
        alertDialog.setMessage("Enter Starting letters of comics(Ex. Av for Avengers)");
        alertDialog.setIcon(R.mipmap.ic_launcher);

        final EditText input = new EditText(getActivity());
        input.setHint("Enter text here then click SEARCH");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("SEARCH",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String search = input.getText().toString().trim();
                        if (search.length() <= 0)
                            dialog.cancel();
                        else {
                           //TODO search algo
                        }
                    }
                });

        //alertDialog.show();

        AlertDialog dialog = alertDialog.create();

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        wmlp.y = 200;   //y position

        dialog.show();
    }
}
