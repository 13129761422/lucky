package com.example.lrzhao.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lrzhao.retrofit.tEST.PostRequest_Interface;
import com.example.lrzhao.retrofit.tEST.Traslation;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
String TAG="jjj";
EditText et;
String at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=findViewById(R.id.editText);
        //步骤4：创建Retrofit对象

        Log.v("sdsddddddd",at+"dd");
//postqu();dd
    }
    public void requesrt(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤5：创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<Translation> observable = request.getCall();

        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.io())               // 在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 回到主线程 处理请求结果
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Translation result) {
                        // 步骤8：对返回的数据进行处理
                        result.show() ;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "请求成功");
                    }
                });
    }
    public void postqu(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostRequest_Interface request_interface=retrofit.create(PostRequest_Interface.class);
        Call<Traslation> call=request_interface.getCall(at);
        call.enqueue(new Callback<Traslation>() {
            @Override
            public void onResponse(Call<Traslation> call, Response<Traslation> response) {
                  setTitle(response.body().getTranslateResult().get(0).get(0).getTgt());
                  Log.v("sadddd",response.body().getTranslateResult().get(0).get(0).getTgt());
                //Toast.makeText(getApplicationContext(),response.body().getTranslateResult().get(0).get(0).getTgt(),Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Call<Traslation> call, Throwable t) {
                  Toast.makeText(getApplicationContext(),"qingqiushibai",Toast.LENGTH_LONG);
            }
        });
    }
    public void click(View view){
        at=et.getText().toString();
      //  Toast.makeText(this,"qingqiushibai",Toast.LENGTH_SHORT);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostRequest_Interface request_interface=retrofit.create(PostRequest_Interface.class);
        Call<Traslation> call=request_interface.getCall(at);
        call.enqueue(new Callback<Traslation>() {
            @Override
            public void onResponse(Call<Traslation> call, Response<Traslation> response) {
                setTitle(response.body().getTranslateResult().get(0).get(0).getTgt());
                Log.v("sadddd",response.body().getTranslateResult().get(0).get(0).getTgt());
                //Toast.makeText(getApplicationContext(),response.body().getTranslateResult().get(0).get(0).getTgt(),Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Call<Traslation> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"qingqiushibai",Toast.LENGTH_LONG);
            }
        });

    }
}
