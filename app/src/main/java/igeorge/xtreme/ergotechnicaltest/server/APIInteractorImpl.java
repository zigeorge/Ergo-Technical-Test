package igeorge.xtreme.ergotechnicaltest.server;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import igeorge.xtreme.ergotechnicaltest.R;
import igeorge.xtreme.ergotechnicaltest.responses.ItemResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zahidul_Islam_George on 08-November-2016.
 */
public class APIInteractorImpl implements APIInteractor {
    private Retrofit retrofit;
    private APIServiceInterface apiServiceInterface;
    private Context context;


    public APIInteractorImpl(Context context, String baseUrl) {
        this.context = context;
//        userManager = new UserManager(context);
        initAPIService(baseUrl);
    }

    private void initAPIService(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiServiceInterface = retrofit.create(APIServiceInterface.class);
    }

    private boolean isResponseValid(Response<?> response) {
        if (response == null) {
            return false;
        } else {
            if (response.body() == null) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void getItemResponse(final OnItemLoadedListener onItemLoadedListener) {
        Call<ItemResponse> call = apiServiceInterface.getJsonData();
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if(isResponseValid(response) && response.body().getStatus().equalsIgnoreCase(context.getString(R.string.status))){
                    onItemLoadedListener.onItemLoaded(response.body().getArray());
                }else{
                    onItemLoadedListener.onFailed(context.getString(R.string.failed_message));
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                onItemLoadedListener.onFailed(context.getString(R.string.failed_message));
            }
        });
    }

    @Override
    public void loadUrl(final OnUrlLoadedListener onUrlLoadedListener, String origin, String destination, boolean alternatives) {
        Call<ResponseBody> call = apiServiceInterface.loadUrl(origin, destination, alternatives);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    onUrlLoadedListener.onUrlLoaded(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onUrlLoadedListener.onFailed(context.getString(R.string.failed_loading_distance));
            }
        });
    }
}
