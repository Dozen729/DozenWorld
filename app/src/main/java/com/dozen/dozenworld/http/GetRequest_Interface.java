package com.dozen.dozenworld.http;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Hugo on 19-10-22.
 * Describe:
 */
public interface GetRequest_Interface {

    /**
     * method：网络请求的方法（区分大小写）
     * path：网络请求地址路径
     * hasBody：是否有请求体
     */
//    @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
//    Call<ResponseBody> getCall(@Path("id") int id);
    // {id} 表示是一个变量
    // method 的值 retrofit 不会做处理，所以要自行保证准确

    @GET("api/data/福利/{data}")
    Call<ResponseBody> getGankFuLi(@Path("data")String data);

    //http://gank.io/api/data/福利/10/1
    //分类数据: http://gank.io/api/data/数据类型/请求个数/第几页

//    @GET("web/test/{url}")
//    Call<ResponseBody> test(@Path("url")String url, @QueryMap Map<String,String> options);

}
