package com.ore.vicse.labretrofit.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ore.vicse.labretrofit.R;
import com.ore.vicse.labretrofit.models.Solicitud;
import com.ore.vicse.labretrofit.services.ApiService;
import com.ore.vicse.labretrofit.services.ApiServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudesAdapter extends RecyclerView.Adapter<SolicitudesAdapter.ViewHolder> {

    private static final String TAG = SolicitudesAdapter.class.getSimpleName();
    private List<Solicitud> solicitudes;

    public SolicitudesAdapter() {
        this.solicitudes = new ArrayList<>();
    }

    public void setSolicitudes(List<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView fotoImage;
        public TextView correoText;
        public TextView tipoText;
        public TextView motivoText;
        public ImageButton menuButton;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImage = itemView.findViewById(R.id.foto_image);
            correoText = itemView.findViewById(R.id.correo_text);
            tipoText = itemView.findViewById(R.id.tipo_text);
            motivoText = itemView.findViewById(R.id.motivo_text);
            menuButton = (ImageButton) itemView.findViewById(R.id.menu_button);
        }
    }

    @Override
    public SolicitudesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud, parent, false);
        return new ViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(SolicitudesAdapter.ViewHolder viewHolder, final int position) {

        final Solicitud solicitud = this.solicitudes.get(position);

        viewHolder.correoText.setText(solicitud.getCorreo());
        viewHolder.tipoText.setText(solicitud.getTipo());
        viewHolder.motivoText.setText(solicitud.getMotivo());

        String url = solicitud.getVoucher();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);

        viewHolder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.remove_button:

                                ApiService service = ApiServiceGenerator.createService(ApiService.class);

                                Call<Solicitud> call = service.destroySolicitud(solicitud.getId());

                                call.enqueue(new Callback<Solicitud>() {
                                    @Override
                                    public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                                        try {

                                            int statusCode = response.code();
                                            Log.d(TAG, "HTTP status code: " + statusCode);

                                            if (response.isSuccessful()) {

                                                Solicitud solicitud = response.body();
                                                Log.d(TAG, "solicitud: " + solicitud);

                                                // Eliminar item del recyclerView y notificar cambios
                                                solicitudes.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeChanged(position, solicitudes.size());

                                                Toast.makeText(v.getContext(), "Eliminación satisfactoria", Toast.LENGTH_LONG).show();

                                            } else {
                                                Log.e(TAG, "onError: " + response.errorBody().string());
                                                throw new Exception("Error en el servicio");
                                            }

                                        } catch (Throwable t) {
                                            try {
                                                Log.e(TAG, "onThrowable: " + t.toString(), t);
                                                Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                            }catch (Throwable x){}
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Solicitud> call, Throwable t) {
                                        Log.e(TAG, "onFailure: " + t.toString());
                                        Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                });

                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.solicitudes.size();
    }


}
