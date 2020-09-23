package com.cerveceria.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cerveceria.POJOs.ProductoPOJO;
import com.cerveceria.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.MiniActivity> {
    private Context context;
    private ArrayList<ProductoPOJO> productos;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public ProductoAdapter(Context context, ArrayList<ProductoPOJO> productos){
        this.context = context;
        this.productos = productos;
    }

    @Override
    public MiniActivity onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.cardview_producto, viewGroup, false);
        return new MiniActivity(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniActivity miniActivity, int i) {
        ProductoPOJO productoitem = productos.get(i);

        String simagen = productoitem.getImagen();
        String smarca = productoitem.getMarca();
        String sconcepto = productoitem.getConcepto();
        int ipreciolista = productoitem.getPreciolista();
        int iml = productoitem.getMl();

        miniActivity.marca.setText(""+smarca);
        miniActivity.preciolista.setText("$"+ipreciolista+".00");
        miniActivity.ml.setText(""+iml+" ml");
        miniActivity.concepto.setText(sconcepto);
        Picasso.get().load(simagen).into(miniActivity.xd);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class MiniActivity extends RecyclerView.ViewHolder{
        public ImageView xd;
        public TextView concepto, preciolista, marca, ml;

        public MiniActivity(View itemView) {
            super(itemView);

            concepto = itemView.findViewById(R.id.conceptotv);
            preciolista = itemView.findViewById(R.id.preciolistatv);
            marca = itemView.findViewById(R.id.marcatv);
            ml = itemView.findViewById(R.id.mltv);
            xd = itemView.findViewById(R.id.productoiv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int i = getAdapterPosition();
                        if (i != RecyclerView.NO_POSITION){
                            mListener.onItemClick(i);
                        }
                    }
                }
            });
        }
    }
}
