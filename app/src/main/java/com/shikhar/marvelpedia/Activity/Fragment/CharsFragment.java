package com.shikhar.marvelpedia.Activity.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.shikhar.marvelpedia.Activity.Adapter.CharsAdapter;
import com.shikhar.marvelpedia.Activity.Interface.MarvelInterface;
import com.shikhar.marvelpedia.Activity.Model.CharsResponse;
import com.shikhar.marvelpedia.Activity.Model.Result;
import com.shikhar.marvelpedia.BuildConfig;
import com.shikhar.marvelpedia.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharsFragment extends Fragment {

    @BindView(R.id.recyclerView)RecyclerView recyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    CharsAdapter adapter;

    private static String PRIVATE_API_KEY = BuildConfig.PRIVATE_API_KEY;
    private static String PUBLIC_API_KEY = BuildConfig.PUBLIC_API_KEY;

    String url = "https://gateway.marvel.com:443/v1/public/";

    List<Result> listOfCharacters = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CharsAdapter(listOfCharacters);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        getAllCharacters();

        return view;
    }

    void getAllCharacters(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarvelInterface serviceRequest = retrofit.create(MarvelInterface.class);

        long timeStamp = System.currentTimeMillis();
        String timeStampString = Long.toString(timeStamp);
        String md5ApiKey = MD5(timeStamp+PRIVATE_API_KEY+PUBLIC_API_KEY);

        Call<CharsResponse> call = serviceRequest.getAllCharacters(timeStampString, PUBLIC_API_KEY, md5ApiKey);

        call.enqueue(new Callback<CharsResponse>() {
            @Override
            public void onResponse(Call<CharsResponse> call, Response<CharsResponse> response) {
                int x = 0;

                int statusCode = response.code();

                if(statusCode != 200) {
                   //TODO emptyView.setVisibility(View.INVISIBLE);
                   //TODO progressBar.setVisibility(View.INVISIBLE);
                    //TODO noData.setVisibility(View.VISIBLE);
                   //TODO noNet.setVisibility(View.INVISIBLE);
                    return;
                }

                List<Result> listOfChars = response.body().getData().getResults();
                adapter.setDataAdapter(listOfChars);
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
               // TODO emptyView.setVisibility(View.INVISIBLE);
               // TODO noData.setVisibility(View.INVISIBLE);
               // TODO noNet.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<CharsResponse> call, Throwable t) {
                 int  y = 0;
            }
        });

    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
