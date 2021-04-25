package com.example.movieappmvvmdaggerhilt.di

import android.os.Build
import androidx.paging.PagedList
import com.example.movieappmvvmdaggerhilt.commons.Const
import com.example.movieappmvvmdaggerhilt.commons.Const.Companion.API_KEY
import com.example.movieappmvvmdaggerhilt.commons.Const.Companion.POST_PER_PAGE
import com.example.movieappmvvmdaggerhilt.data.remote.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton
import kotlin.jvm.Throws


@Module
@InstallIn(SingletonComponent::class )
object RetrofitModule {

    @Singleton //Para generar solo una instancia del objecto
    @Provides //Para poder proveer este objecto en cualquier clase
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    @Singleton //Para generar solo una instancia del objecto
    @Provides //Para poder proveer este objecto en cualquier clase
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    @Singleton //Para generar solo una instancia del objecto
    @Provides //Para poder proveer este objecto en cualquier clase
    fun clientInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
  //                  .addEncodedQueryParameter("apiKey", Const.API_KEY)
                val url =
                    request.url.newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build()
                request = request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
//                    .addHeader("Authorization", BEARER_TOKEN)
                    .addHeader("x-device-type", Build.DEVICE)
                    .url(url)
                    .build()
                return chain.proceed(request)
            }
        }
    }

    @Singleton //Para generar solo una instancia del objecto
    @Provides //Para poder proveer este objecto en cualquier clase
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        clientInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addNetworkInterceptor(clientInterceptor)
            .addInterceptor(httpLoggingInterceptor).build()
    }

//    @Singleton
//    @Provides
//    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory? {
//        return RxJava2CallAdapterFactory.create()
//    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: GsonConverterFactory,
        http: OkHttpClient
        //  provideRxJava2CallAdapterFactory: RxJava2CallAdapterFactory?
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.API_URL) //Configurar el base URL
            //      .addCallAdapterFactory(provideRxJava2CallAdapterFactory)
            .addConverterFactory(gson) //Se agrega el GSON que se va a convertir uno generico
            .client(http) //Se agrega el cliente para que se pruebe el interceptor
            .build()
    }


    @Provides
    @Singleton
    fun provideApiCompanies(retrofit : Retrofit) : MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
    //
//
    @Provides
    @Singleton
    fun pagingConfig(): PagedList.Config{
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()
    }


}