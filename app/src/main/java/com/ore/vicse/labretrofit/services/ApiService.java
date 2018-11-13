package com.ore.vicse.labretrofit.services;

import com.ore.vicse.labretrofit.models.Solicitud;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    String API_BASE_URL = "https://labcalificadoandroid-vicse.c9users.io";

    @GET("/api/solicitudes/")
    Call<List<Solicitud>> getSolicitudes();

    @FormUrlEncoded
    @POST("/api/solicitudes/")
    Call< Solicitud> createSolicitud(@Field("correo") String correo,
                                     @Field("motivo") String motivo,
                                     @Field("tipo") String tipo);

    @Multipart
    @POST("/api/solicitudes/")
    Call< Solicitud> createSolicitudWithImage(
            @Part("correo") RequestBody correo,
            @Part("motivo") RequestBody motivo,
            @Part("tipo") RequestBody tipo,
            @Part MultipartBody.Part imagen
    );

    @DELETE("/api/solicitudes/{id}/")
    Call<Solicitud> destroySolicitud(@Path("id") Integer id);

}
