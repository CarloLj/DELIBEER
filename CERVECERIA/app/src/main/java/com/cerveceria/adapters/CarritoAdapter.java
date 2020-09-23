package com.cerveceria.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cerveceria.POJOs.CarritoPOJO;
import com.cerveceria.POJOs.ProductoPOJO;
import com.cerveceria.R;
import com.cerveceria.globals.Global;
import com.squareup.picasso.Picasso;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.MiniActivity> {
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onibmas(int i);
        void onibmenos(int i);
        void ondelete(int i);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CarritoAdapter(Context context){
        this.context = context;
    }

    @Override
    public MiniActivity onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_carrito, viewGroup, false);
        return new MiniActivity(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniActivity miniActivity, int i) {
        CarritoPOJO carritoitem = Global.carrito.get(i);

        String simagen = carritoitem.getImagen();
        String sconcepto = carritoitem.getConcepto();
        int iml = carritoitem.getMl();
        int cantidad = carritoitem.getCantidad();
        int ipreciolista = carritoitem.getPreciolista();
        int ipreciototal = carritoitem.getSubtotal();

        miniActivity.ml.setText("" + iml + " ml");
        miniActivity.concepto.setText(sconcepto);
        miniActivity.preciolista.setText("$"+ipreciolista+".00");
        miniActivity.preciototal.setText("$"+ipreciototal+".00");
        miniActivity.cant.setText(""+cantidad);
        Picasso.get().load(simagen).into(miniActivity.producto);
    }

    @Override
    public int getItemCount() {
        return Global.carrito.size();
    }

    public class MiniActivity extends RecyclerView.ViewHolder {
        public ImageView producto;
        public TextView concepto, ml, preciolista, preciototal, cantidad, cant;
        public ImageButton ibmas, ibmenos, ibborrar;

        public MiniActivity(View itemView) {
            super(itemView);

            producto = itemView.findViewById(R.id.prodred);
            concepto = itemView.findViewById(R.id.conceptored);
            ml = itemView.findViewById(R.id.mlred);
            preciolista = itemView.findViewById(R.id.preciolistared);
            preciototal = itemView.findViewById(R.id.preciototalred);
            cantidad = itemView.findViewById(R.id.cantidadred);
            cant = itemView.findViewById(R.id.cantred);

            ibmas = itemView.findViewById(R.id.masred);
            ibmenos = itemView.findViewById(R.id.menosred);
            ibborrar = itemView.findViewById(R.id.borrarred);

            ibmas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int i = getAdapterPosition();
                        if (i != RecyclerView.NO_POSITION){
                            mListener.onibmas(i);
                        }
                    }
                }
            });
            ibmenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int i = getAdapterPosition();
                        if (i != RecyclerView.NO_POSITION){
                            mListener.onibmenos(i);
                        }
                    }
                }
            });
            ibborrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int i = getAdapterPosition();
                        if (i != RecyclerView.NO_POSITION){
                            mListener.ondelete(i);
                        }
                    }
                }
            });
        }
    }
}