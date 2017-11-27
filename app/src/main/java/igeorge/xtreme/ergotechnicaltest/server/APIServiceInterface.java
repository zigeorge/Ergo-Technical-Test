package igeorge.xtreme.ergotechnicaltest.server;

import igeorge.xtreme.ergotechnicaltest.responses.ItemResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceInterface {

    @GET("json")
    Call<ItemResponse> getJsonData();

    @GET("json")
    Call<ResponseBody> loadUrl(@Query("origin") String origin, @Query("destination") String destination, @Query("alternatives") boolean alternatives);

}
