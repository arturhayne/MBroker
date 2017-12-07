package br.com.htech.mbroker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.htech.mbroker.R;

public class ClientListFragment extends ListFragment{

//private String myandroidphone[];

	SqlOpenHelper banco;
	List<String[]> clientes;
	String[] id;
	String[] Nome;
	String[] buy;
	String[] rent;

	
	
	public ClientListFragment() {
		
		banco = SqlOpenHelper.getInstance();
		clientes = new ArrayList<String[]>();
		clientes = banco.getAllClients();
		String addClient = "";
		
		if (Locale.getDefault().getLanguage().contains("pt")) {
			addClient = "Adicione um cliente";
		}else {
			addClient = "Add a Client";
		}
		
		
		
		if (clientes.isEmpty()) {
			Nome = new String[] {
					addClient
			};
			
			id = new String[] {"0"};
			buy = new String[] {""};
			rent = new String[] {""};
			
		}else{
			
			id = new String[clientes.size()];
			Nome = new String[clientes.size()];	
			buy = new String[clientes.size()];
			rent = new String[clientes.size()];	

			
			for (int i = 0; i < clientes.size(); i++) {				

				id[i] = clientes.get(i)[0];
				Nome[i] = clientes.get(i)[1];
				buy[i] = clientes.get(i)[2];
				rent[i] = clientes.get(i)[3];

			}
		}
		
		

		
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        Context ctx = this.getActivity().getApplicationContext();
		
		setListAdapter(new ClientAdapter(ctx, R.layout.client_list_item,
				id, Nome,buy ,rent));

	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_fragment, container, false);
	}
	
	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {
		if (!clientes.isEmpty()) {			
			//Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();		
			Intent intent = new Intent(getActivity(), ClientActivity.class);
			intent.putExtra("ID",id);
			startActivity(intent);
			getActivity().finish();
			
			//getLayoutInflater(null).inflate(R.layout.activity_client, null);
			getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			
			//HomeListFragment simpleListFragment = new HomeListFragment();
			//getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, simpleListFragment).commit();
			//getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			//getActivity().ge
		}else {
			Intent intent = new Intent(getActivity(), AddClient.class);
	    	startActivity(intent);	
	    	getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	    	getActivity().finish();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	

    

}
