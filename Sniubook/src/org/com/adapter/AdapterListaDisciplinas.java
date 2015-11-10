package org.com.adapter;

import java.util.ArrayList;

import org.com.main.R;
import org.com.main.TelaAvaliarDisciplina;
import org.com.main.TelaDisciplinasCurso;
import org.com.model.Disciplina;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class AdapterListaDisciplinas extends BaseAdapter {

	private Context context;
	private ArrayList<Disciplina> disciplinas;
	
	public AdapterListaDisciplinas(Context context, ArrayList<Disciplina> disciplinas) {
		super();
		this.context = context;
		this.disciplinas = disciplinas;
	}

	@Override
	public int getCount() {
		return disciplinas.size();
	}

	@Override
	public Object getItem(int position) {
		return disciplinas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.list_disciplinas, null);
		
		if (view != null) {
			TextView tvNomeDisc = (TextView) view.findViewById(R.id.tvNomeDisc);
			RatingBar ratingDiscGer = (RatingBar) view.findViewById(R.id.ratingDiscGer);
		
			ratingDiscGer.setEnabled(false);
			
			Disciplina disciplina = this.disciplinas.get(position);
			tvNomeDisc.setText(disciplina.getNome());
			ratingDiscGer.setRating(disciplina.getRate());
		}
		return view;
	}

}
