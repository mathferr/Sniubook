package org.com.adapter;

import java.util.ArrayList;

import org.com.main.R;
import org.com.model.Comentarios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class AdapterProfessor extends BaseAdapter {
	
	private Context context;
	private ArrayList<Comentarios> comentarios;
	
	public AdapterProfessor(Context context, ArrayList<Comentarios> comentarios) {
		this.context = context;
		this.comentarios = comentarios;
	}

	@Override
	public int getCount() {
		return comentarios.size();
	}

	@Override
	public Object getItem(int position) {
		return comentarios.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.list_comentarios, null);
		
		if (view != null) {
			TextView tvNomeComent = (TextView) view.findViewById(R.id.tvNomeComent);
			TextView tvComent = (TextView) view.findViewById(R.id.tvComent);
			RatingBar ratingAlunoList = (RatingBar) view.findViewById(R.id.ratingAlunoList);
		
			ratingAlunoList.setEnabled(false);
			
			Comentarios comentarios = this.comentarios.get(position);
			tvNomeComent.setText(comentarios.getNomeAluno());
			tvComent.setText(comentarios.getComentario());
			ratingAlunoList.setRating(comentarios.getRatingAluno());
		}
		
		return view;
	}

}
