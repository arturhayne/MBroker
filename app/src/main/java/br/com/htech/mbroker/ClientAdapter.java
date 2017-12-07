package br.com.htech.mbroker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.htech.mbroker.R;

public class ClientAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	
	private String[] mId;
	private String[] mNames;
	private String[] mBuy;
	private String[] mRent;
	String comprar;
	String alugar;


	
	private int mViewResourceId;
	
	public ClientAdapter(Context ctx, int viewResourceId,
			String[] ids, String[] names, String[] buy, String[] rent) {
		super(ctx, viewResourceId, names);
		
		mInflater = (LayoutInflater)ctx.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		mNames = names;
		mId = ids;
		mBuy = buy;
		mRent = rent;
		
		mViewResourceId = viewResourceId;
		comprar = ctx.getString(R.string.buy);
		alugar = ctx.getString(R.string.rent);
	}

	@Override
	public int getCount() {
		return mNames.length;
	}

	@Override
	public String getItem(int position) {
		return mNames[position];
	}

	@Override
	public long getItemId(int position) {
		return Long.parseLong(mId[position]);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(mViewResourceId, null);

		TextView tv = (TextView)convertView.findViewById(R.id.clientName);
		tv.setText(mNames[position]);
		
		TextView tv2 = (TextView)convertView.findViewById(R.id.clientId);
		tv2.setText(mId[position]);
		
		TextView tv3 = (TextView)convertView.findViewById(R.id.stEmailVi);
		
		if (mBuy[position].compareTo("1")==0) {
			tv3.setText(comprar);
			
		}
		
		TextView tv4 = (TextView)convertView.findViewById(R.id.clientAlugar);
		if (mRent[position].compareTo("1")==0) {
			tv4.setText(alugar);
		}
		
		

		

		
		return convertView;
	}
}
