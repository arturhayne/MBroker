package br.com.htech.mbroker;

/**
 * This file is part of AdvancedListViewDemo.
 * You should have downloaded this file from www.intransitione.com, if not, 
 * please inform me by writing an e-mail at the address below:
 *
 * Copyright [2011] [Marco Dinacci <marco.dinacci@gmail.com>]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.

 * The license text is available online and in the LICENSE file accompanying the distribution
 * of this program.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.htech.mbroker.R;

public class HomeAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	
	private String[] mId;
	private String[] mStrings;
	//private TypedArray mIcons;
	private String[] mIcons;
	private String[] mVender;
	private String[] mAlugar;
	String vend;
	String alug;
	
	public ImageLoader imageLoader;
	
	private int mViewResourceId;
	
	public HomeAdapter(Context ctx, int viewResourceId,
			String ids[], String[] strings, String[] icons, String[] vender, String[] alugar) {
		super(ctx, viewResourceId, strings);
		
		mInflater = (LayoutInflater)ctx.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		mStrings = strings;
		mIcons = icons;
		mId = ids;
		mVender = vender;
		mAlugar = alugar;
		
		imageLoader=new ImageLoader(ctx);
		
		mViewResourceId = viewResourceId;
		
		vend = ctx.getString(R.string.sale);
		alug = ctx.getString(R.string.rent);
	}

	@Override
	public int getCount() {
		return mStrings.length;
	}

	@Override
	public String getItem(int position) {
		return mStrings[position];
	}

	@Override
	public long getItemId(int position) {
		return Long.parseLong(mId[position]);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(mViewResourceId, null);
		
		ImageView iv = (ImageView)convertView.findViewById(R.id.option_icon);
		//iv.setImageDrawable(mIcons.getDrawable(position));
		
		TextView tv = (TextView)convertView.findViewById(R.id.homeId);
		tv.setText(mId[position]);

		TextView tv1 = (TextView)convertView.findViewById(R.id.HomeProp);
		tv1.setText(mStrings[position]);
		
		TextView tv3 = (TextView)convertView.findViewById(R.id.homeVender);
		
		if (mVender[position].compareTo("1")==0) {
			tv3.setText(vend);
			
		}
		
		TextView tv4 = (TextView)convertView.findViewById(R.id.homeAlugar);
		if (mAlugar[position].compareTo("1")==0) {
			tv4.setText(alug);
		}
		
		imageLoader.DisplayImage(mIcons[position], iv);
		
		return convertView;
	}
}
