package br.com.htech.mbroker;

import com.htech.mbroker.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ClientListFragment2 extends ListFragment{

	private String myandroidversions[];
	public ClientListFragment2() {
		myandroidversions= new String[] {
				"Jelly Bean",
				"Ice cream Sandwich",
				"GingerBread",
				"HoneyComb",
				"Friyo",
				
		};
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myandroidversions);
		setListAdapter(listAdapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_fragment, container, false);
	}
	
	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {
		
		Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
	}
}
