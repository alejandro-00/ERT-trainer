package com.alex00.erttrainer;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Preguntas> preguntas;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void  setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public Adapter (Context context, ArrayList<Preguntas> preg){
        mContext=context;
        preguntas=preg;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.card_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Preguntas current=preguntas.get(i);

        String pregunta = current.getP();
        String op=current.getO1();
        String c=current.getC();
        boolean b=current.getBool();
        int no=current.getNo();

        if (b==true){
            viewHolder.card.setBackgroundColor(Color.rgb(148,184,144));
        }else viewHolder.card.setBackgroundColor(Color.rgb(255,98,98));

        viewHolder.Pregunta.setText(no+"\n"+pregunta);
        viewHolder.Respuesta.setText("Answer: "+op);
        viewHolder.Correcta.setText("Correct answer: "+c);
    }

    @Override
    public int getItemCount() {
        return preguntas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView Pregunta,Respuesta,Correcta;

        public LinearLayout card;

        public  ViewHolder(View itemView){
            super(itemView);
            card=itemView.findViewById(R.id.card);
            Pregunta=itemView.findViewById(R.id.CardQuestion);
            Respuesta=itemView.findViewById(R.id.CardRes);
            Correcta=itemView.findViewById(R.id.CardCorr);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }

    }
}
