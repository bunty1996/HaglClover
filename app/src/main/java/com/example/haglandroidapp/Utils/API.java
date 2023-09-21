package com.example.haglandroidapp.Utils;

import com.example.haglandroidapp.Models.DeleteProduct.DeleteProductExample;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductExample;
import com.example.haglandroidapp.Models.GetProductListModel.GetProductListExample;
import com.example.haglandroidapp.Models.GetTotalInventeryItems.GetTotalInventeryItemsExample;
import com.example.haglandroidapp.Models.PostThresoldPriceModel.PostProductExample;
import com.example.haglandroidapp.Models.UpdateHagalProductModel.UpdateHagalProductExample;
import com.google.gson.JsonObject;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    //////users////////

    //    @FormUrlEncoded
//    @POST("users/login")
//    Call<LoginExample> loginAPI(@Field("email") String email, @Field("password") String password);

//    @POST("users/register")
//    Call<SignUpExample> registerAPI(@Body JsonObject jsonObject);

//    @FormUrlEncoded
    @GET("{mId}/items")
    Call<GetTotalInventeryItemsExample> getItemsApi(@Path("mId") String merchant_id, @Header("Authorization") String token);

//    String STRING = CSPreferences.readString(MyApplication.context(),"merchantID");
//    https://stage-api.hagglit.com/v1/clover/product/?merchant_id=41HJH17GJD4H1


    //    https://stage-api.hagglit.com/v1/clover/41HJH17GJD4H1
    @GET("{merchant_id}")
    Call<HagalProductExample> getHagalListApi(@Path("merchant_id") String merchant_id);

    @POST("product/")
//    @POST("product/?merchant_id=/{merchant_id}")
    Call<PostProductExample> postThresoldPriceApi(@Query("merchant_id") String merchantId, @Body JsonObject jsonObject);
//    https://stage-api.hagglit.com/v1/clover/product/?merchat_id=41HJH17GJD4H1


    //    URL: https://stage-api.hagglit.com/v1/clover/<product_id>/<merchant_id>
    @PUT("{product_id}/{merchant_id}")
    Call<UpdateHagalProductExample> updateHagalProductApi(@Path("product_id") String product_id, @Path("merchant_id") String merchant_id,
                                                          @Body JsonObject jsonObject);

    //    https://stage-api.hagglit.com/v1/clover/<product_id>/<merchant_id>
//    @DELETE("{merchant_id}")
//    Call<DeleteProductExample> deleteHagalProductApi(@Path("merchant_id") String merchant_id,@Body JsonObject jsonObject);

    @HTTP(method = "DELETE", path = "{merchant_id}", hasBody = true)
    Call<DeleteProductExample> deleteHagalProductApi(@Path("merchant_id") String merchant_id, @Body JsonObject jsonObject);

    //    https://stage-api.hagglit.com/v1/clover/order/41HJH17GJD4H1?random_number=023
    @GET("order/{merchant_id}")
    Call<GetProductListExample> getProductPurchageDiscount(@Path("merchant_id") String merchant_id, @Query("random_number") String random_number);


}