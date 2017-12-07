package br.com.htech.mbroker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.htech.mbroker.R;



public class GalleryImageAdapter  extends BaseAdapter {
	
	private LayoutInflater mInflater;
	
	 String[] paths;
	 boolean[] thumbnailsselection;
	 
	 public ImageLoader imageLoader;
	 
	 private int mViewResourceId;
	 
	 Context mctx;

	public GalleryImageAdapter(Context ctx, int viewResourceId,
			String[] strings, boolean[] selections ) {
		
		mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		paths = strings;
		thumbnailsselection = selections;
		mViewResourceId = viewResourceId;
		mctx = ctx;
		
		imageLoader=new ImageLoader(ctx,50);		
		
	}

	public int getCount() {
		return paths.length;
	}

	public String getItem(int position) {
		return paths[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(mViewResourceId, null);
		
		ImageView iv = (ImageView)convertView.findViewById(R.id.thumbImage);
		CheckBox tv = (CheckBox)convertView.findViewById(R.id.itemCheckBox);
		
		iv.setId(position);
		tv.setId(position);
		
		iv.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int id = v.getId();
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse("file://" + paths[id]), "image/*");
				mctx.startActivity(intent);
			}
		});


		
		
		tv.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckBox cb = (CheckBox) v;
				int id = cb.getId();
				if (thumbnailsselection[id]){
					cb.setChecked(false);
					thumbnailsselection[id] = false;
				} else {
					cb.setChecked(true);
					thumbnailsselection[id] = true;
				}
			}
		});
		
		
		tv.setChecked(thumbnailsselection[position]);				
		imageLoader.DisplayImage(paths[position], iv);
		
		return convertView;
	}
}

