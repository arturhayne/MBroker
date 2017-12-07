package br.com.htech.mbroker;


import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.htech.mbroker.R;

public class HomeActivity extends Activity {
	
	SqlOpenHelper banco;
	long id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		// Show the Up button in the action bar.
		setupActionBar();
		id = this.getIntent().getExtras().getLong("ID");
		this.getHome(id);
	}

	private void getHome(long homeId) {
		ImageLoader imageLoader = new ImageLoader(this);
		// TODO Auto-generated method stub
		banco = SqlOpenHelper.getInstance();		
		String [] home = banco.getHome(homeId);
		List<String>  images = banco.getAllImages(homeId);
		
		TextView prop = (TextView)findViewById(R.id.txtHproprietario);
		TextView endereco = (TextView)findViewById(R.id.txtHEndereco);
		TextView email = (TextView)findViewById(R.id.txtHemail);
		TextView quartos = (TextView)findViewById(R.id.txtHquartos);
		TextView telefone = (TextView)findViewById(R.id.txtHtelefone);
		//TextView referenciaId = (TextView)findViewById(R.id.txHiId);
		ImageView mainImage = (ImageView)findViewById(R.id.mainImageHome);
		CheckBox cbVenda = (CheckBox)findViewById(R.id.cbxHvenda);
		CheckBox cbAluguel = (CheckBox)findViewById(R.id.cbxHaluguel);
		RadioGroup rg = (RadioGroup)findViewById(R.id.rgHTipo1);
		RadioButton rb = (RadioButton)findViewById(R.id.rCasa1);
		RadioButton rb2 = (RadioButton)findViewById(R.id.rApt1);
		TextView descricao = (TextView)findViewById(R.id.txtDescription);
		imageLoader.DisplayImage(home[1], mainImage);
		
		prop.setText(home[2]);
		endereco.setText(home[4]);
		email.setText(home[5]);
		telefone.setText(home[6]);
		descricao.setText(home[10]);
		quartos.setText(home[11]);
		if (home[7].compareTo("1")==0) {
			cbVenda.setChecked(true);
		}else {
			cbVenda.setChecked(false);
		}
		if (home[8].compareTo("1")==0) {
			cbAluguel.setChecked(true);
		}else {
			cbAluguel.setChecked(false);
		}
		if (rg.getCheckedRadioButtonId()==R.id.rCasa1) {			
			rb.setChecked(true);
			rb2.setChecked(false);
		}else {
			rb.setChecked(false);
			rb2.setChecked(true);
		}
		
		LinearLayout myGallery = (LinearLayout)findViewById(R.id.myStGallery);
		
		for (String image : images) {
			if ((new File(image)).exists()) {
				myGallery.addView(insertPhoto(image));
			}
			
		}
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:{
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				//NavUtils.navigateUpFromSameTask(this);
				voltarPrincipal();
				return true;
			}
			case R.id.editMenuHome:	{
				Intent intent = new Intent(HomeActivity.this, AddHome.class);
				intent.putExtra("ID",id);
				startActivity(intent);
				finish();
				//overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			}		
			break;
			case R.id.deleteMenuHome:{
				
				String mnsagemretorno = this.getString(R.string.deleteHomeMsg);
				
	    		new AlertDialog.Builder( this ) 
	    		.setTitle( this.getString(R.string.deleteRecord) ) 
	    		.setMessage(mnsagemretorno )
	    		.setNegativeButton(this.getString(R.string.cancel), new DialogInterface.OnClickListener()
	    	    {
					
					public void onClick(DialogInterface dialog, int which) {
						return; 					
					}
	    	    	
	    	    })
	    		.setPositiveButton( "OK", new DialogInterface.OnClickListener()
	    	    {	    	        
	    	        public void onClick(DialogInterface dialog, int which) {	    	            	        	
	    				banco.deleteHome(id);
	    				voltarPrincipal();
	    	        }

	    	    }).show();
				

			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void voltarPrincipal() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(HomeActivity.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}
	
	@Override
	public void onBackPressed() 
	{
		voltarPrincipal();
	}
	
	
	
	   View insertPhoto(final String path){
	    	
	    	Bitmap bm = decodeSampledBitmapFromUri(path, 120, 120);
	    	
	    	int rotation = getCameraPhotoOrientation(path);
	    	
			Matrix rotateRight = new Matrix();
			rotateRight.preRotate(rotation);
			Bitmap b = Bitmap.createBitmap(bm, 0, 0,
					bm.getWidth(), bm.getHeight(), rotateRight, true);


	    	final LinearLayout layout = new LinearLayout(getApplicationContext());
	    	layout.setLayoutParams(new LayoutParams(150, 150));
	    	layout.setGravity(Gravity.CENTER);
	    	
	    	final ImageView imageView = new ImageView(getApplicationContext());
	    	imageView.setLayoutParams(new LayoutParams(120, 120));
	    	imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	    	imageView.setImageBitmap(b);    	
	    	
	    	
	        layout.addView(imageView);
	    	
	    	return layout;
	    }
	    
	    public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
	    	Bitmap bm = null;
	    	
	    	// First decode with inJustDecodeBounds=true to check dimensions
	    	final BitmapFactory.Options options = new BitmapFactory.Options();
	    	options.inJustDecodeBounds = true;
	    	BitmapFactory.decodeFile(path, options);
	    	
	    	// Calculate inSampleSize
	    	options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	    	
	    	// Decode bitmap with inSampleSize set
	    	options.inJustDecodeBounds = false;
	    	bm = BitmapFactory.decodeFile(path, options); 
	    	
	    	return bm; 	
	    }
	    
	    public int calculateInSampleSize(
	    		
	    	BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    	// Raw height and width of image
	    	final int height = options.outHeight;
	    	final int width = options.outWidth;
	    	int inSampleSize = 1;
	        
	    	if (height > reqHeight || width > reqWidth) {
	    		if (width > height) {
	    			inSampleSize = Math.round((float)height / (float)reqHeight);  	
	    		} else {
	    			inSampleSize = Math.round((float)width / (float)reqWidth);  	
	    		}  	
	    	}
	    	
	    	return inSampleSize;  	
	    }
	    
	    

	    public int getCameraPhotoOrientation(String imagePath){//Context context,
	    int rotate = 0;
	    try {
	    	
	    	//Uri imageUri = Uri.parse(imagePath);
	        //context.getContentResolver().notifyChange(imageUri, null);
	        File imageFile = new File(imagePath);
	        ExifInterface exif = new ExifInterface(
	                imageFile.getAbsolutePath());
	        int orientation = exif.getAttributeInt(
	                ExifInterface.TAG_ORIENTATION,
	                ExifInterface.ORIENTATION_NORMAL);

	        switch (orientation) {
	        case ExifInterface.ORIENTATION_ROTATE_270:
	            rotate = 270;
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_180:
	            rotate = 180;
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_90:
	            rotate = 90;
	            break;
	        }

	        //Log.v(TAG, "Exif orientation: " + orientation);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	   return rotate;
	}
	    
    public void call(final View view){
    	TextView nTelefone = (TextView)findViewById(R.id.txtHtelefone);
    	
    	
    	
		final String mnsagemretorno = nTelefone.getText().toString();
		
		new AlertDialog.Builder( this ) 
		.setTitle( this.getString(R.string.call)) 
		.setMessage(mnsagemretorno )
		.setNegativeButton(this.getString(R.string.cancel), new DialogInterface.OnClickListener()
	    {
			
			public void onClick(DialogInterface dialog, int which) {
				return; 					
			}
	    	
	    })
		.setPositiveButton( "OK", new DialogInterface.OnClickListener()
	    {	    	        
	        public void onClick(DialogInterface dialog, int which) {
	        	
	        	try {
	        		
		        	Intent intent = new Intent(Intent.ACTION_CALL);
		        	intent.setData(Uri.parse("tel:" + mnsagemretorno));
		        	view.getContext().startActivity(intent);
					
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
				}

	        }

	    }).show();
    }
    
    public void sendEmail(final View view){
    	TextView email = (TextView)findViewById(R.id.txtHemail);
    	
    	
    	
		final String mnsagemretorno = email.getText().toString();
		
		new AlertDialog.Builder( this ) 
		.setTitle( this.getString(R.string.sendEmail)) 
		.setMessage(mnsagemretorno )
		.setNegativeButton(this.getString(R.string.cancel), new DialogInterface.OnClickListener()
	    {
			
			public void onClick(DialogInterface dialog, int which) {
				return; 					
			}
	    	
	    })
		.setPositiveButton( "OK", new DialogInterface.OnClickListener()
	    {	    	        
	        public void onClick(DialogInterface dialog, int which) {
	        	
	        	try {
	        		
	        		Intent intent = new Intent();
	        		intent.setType("message/rfc822");
	        		intent.setAction(Intent.ACTION_VIEW);
	        		Uri data = Uri.parse("mailto:"+mnsagemretorno);
	        		intent.setData(data);	        		
	        		view.getContext().startActivity(Intent.createChooser(intent, "Select Client"));
					
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_LONG).show();
				}

	        }

	    }).show();
        
    }

}
