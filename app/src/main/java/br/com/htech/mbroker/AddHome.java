package br.com.htech.mbroker;



import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.htech.mbroker.R;


public class AddHome extends Activity {
	
	public ImageLoader imageLoader;
	LinearLayout myGallery;
	Boolean isdown = false;
	ArrayList< LinearLayout > removeIdex;
	ArrayList< String > imagePath;
	String mainImagePath;
	SqlOpenHelper banco;
	Boolean fromMain = true;
	long id;
	
   // ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_home);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		imageLoader=new ImageLoader(this);
		
		EditText inputField = (EditText) findViewById(R.id.edtFone);
		inputField.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		// Show the Up button in the action bar.
		setupActionBar();
		removeIdex = new ArrayList<LinearLayout>();
		imagePath = new ArrayList<String>();
        myGallery = (LinearLayout)findViewById(R.id.mygallery);
        
		if (this.getIntent().hasExtra("ID")) {
			id = this.getIntent().getExtras().getLong("ID");
			this.getHome(id);
			fromMain = false;
		}

	}

	private void getHome(long homeId) {
		ImageLoader imageLoader = new ImageLoader(this);
		// TODO Auto-generated method stub
		banco = SqlOpenHelper.getInstance();		
		String [] home = banco.getHome(homeId);
		ArrayList<String>  images = banco.getAllImages(homeId);
		
		
		EditText prop = (EditText)findViewById(R.id.edtProprietario);
		EditText endereco = (EditText)findViewById(R.id.edtEndereco);
		EditText email = (EditText)findViewById(R.id.edtEmail);
		EditText quartos = (EditText)findViewById(R.id.edtQuartos);
		EditText telefone = (EditText)findViewById(R.id.edtFone);
		EditText descricao = (EditText)findViewById(R.id.edtDescription);
		//TextView referenciaId = (TextView)findViewById(R.id.txHiId);
		
		CheckBox cbVenda = (CheckBox)findViewById(R.id.cbxVenda);
		CheckBox cbAluguel = (CheckBox)findViewById(R.id.cbxAluguel);
		RadioGroup rg = (RadioGroup)findViewById(R.id.rgTipo);
		RadioButton rb = (RadioButton)findViewById(R.id.rCasa);
		RadioButton rb2 = (RadioButton)findViewById(R.id.rApt);
		if (home[1]!=null) {		
			if ((new File(home[1])).exists()) {
				ImageButton mainImage = (ImageButton)findViewById(R.id.mainHome);
				imageLoader.DisplayImage(home[1], mainImage);
				mainImagePath = home[1];
			}
		}
		
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
		if (rg.getCheckedRadioButtonId()==R.id.rCasa) {			
			rb.setChecked(true);
			rb2.setChecked(false);
		}else {
			rb.setChecked(false);
			rb2.setChecked(true);
		}
		
		imagePath = images;
		
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
		getMenuInflater().inflate(R.menu.add_home, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() 
	{
		voltar();
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
			// public void onClick(DialogInterface dialog, int which) {
			
			voltar(); 
			return true;
			//}
			}
		case R.id.save:	{
			salvarImovel();
			voltar(); 
			}		
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void salvarImovel() {
		// TODO Auto-generated method stub
		SqlOpenHelper banco = SqlOpenHelper.getInstance();
		//ImageButton mainIm = (ImageButton)findViewById(R.id.mainHome);
		EditText eAddress = (EditText)findViewById(R.id.edtEndereco); 
		EditText eOwner = (EditText)findViewById(R.id.edtProprietario); 
		String venda = "0";
		String aluguel = "0";
		String tipo = "casa";
		EditText eRooms = (EditText)findViewById(R.id.edtQuartos); 
		EditText eEmail = (EditText)findViewById(R.id.edtEmail); 
		EditText eFone = (EditText)findViewById(R.id.edtFone); 
		EditText eDescricao = (EditText)findViewById(R.id.edtDescription);
		
		String acc = "0"; 
		int rooms;
		
		CheckBox cbxComprar = (CheckBox)findViewById(R.id.cbxVenda);
		if (cbxComprar.isChecked()) {
			venda = "1";
		}
		CheckBox cbxAlugar = (CheckBox)findViewById(R.id.cbxAluguel);
		if (cbxAlugar.isChecked()) {
			aluguel = "1";
		}
		
		RadioGroup rTipo = (RadioGroup)findViewById(R.id.rgTipo);
		if (rTipo.getCheckedRadioButtonId() == R.id.rApt) {
			tipo = "apt";
		}
		if (eRooms.getText().toString().isEmpty()) {
			rooms = 0;
		}else {
			rooms = Integer.parseInt(eRooms.getText().toString());
		}
		
		

		
		if (fromMain) {
			banco.insertHome(mainImagePath, 
					eAddress.getText().toString(), 
					eOwner.getText().toString(), 
					venda, 
					aluguel,
					tipo, 
					rooms, 
					eEmail.getText().toString(), 
					eFone.getText().toString(),
					eDescricao.getText().toString(),
					acc);
			
			String maxId = banco.getMaxIdHome();
			
			for (int i = 0; i < imagePath.size(); i++) {
				banco.insertImage(imagePath.get(i), maxId);
			}
			
		}else {
			banco.updateHome 
			(mainImagePath, 
					eAddress.getText().toString(), 
					eOwner.getText().toString(), 
					venda, 
					aluguel,
					tipo, 
					rooms, 
					eEmail.getText().toString(), 
					eFone.getText().toString(),
					eDescricao.getText().toString(),
					acc,id);
			
			banco.deleteImages(id);
			
			for (int i = 0; i < imagePath.size(); i++) {
				banco.insertImage(imagePath.get(i), String.valueOf(id));
			}
		}
		
		
	}

	private void voltar() {
		// TODO Auto-generated method stub
		if (fromMain) {
			Intent intent = new Intent(AddHome.this, MainActivity.class);
			startActivity(intent);
			finish();			
		}else{		
			Intent intent = new Intent(AddHome.this, HomeActivity.class);
			intent.putExtra("ID",id);
			startActivity(intent);
			finish();
		}
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.add_home, menu);
	    MenuItem   item = menu.findItem(R.id.remove);
	    MenuItem   item2 = menu.findItem(R.id.save);
	    item.setVisible(isdown);
	    item2.setVisible(true);    	    
	    return true;
	}
		
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {        	
			Intent intent = new Intent(AddHome.this, MainActivity.class);
			startActivity(intent);
			finish();
			return true;

		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
	
	public void selectMainImage(View view){
		
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
		
		
	
	}
	
	public void selectImage(View view){

    	int requestCode = 2;
    	Intent intent = new Intent(AddHome.this, AndroidCustomGalleryActivity.class );
    	startActivityForResult(intent, requestCode);

	}
	
	protected void onActivityResult(int requestCode, int resultCode, 
		       Intent imageReturnedIntent) {
		    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

		    switch(requestCode) { 
		    case 1:
		        if(resultCode == RESULT_OK){  
		            Uri selectedImage = imageReturnedIntent.getData();
		            String[] filePathColumn = {MediaStore.Images.Media.DATA};

		            Cursor cursor = getContentResolver().query(
		                               selectedImage, filePathColumn, null, null, null);
		            cursor.moveToFirst();

		            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		            final String filePath = cursor.getString(columnIndex);
		            cursor.close();

		            final ImageButton img = (ImageButton)findViewById(R.id.mainHome);
		            
		            imageLoader.DisplayImage(filePath, img);
		            
		            mainImagePath = filePath;
		            
		        }
		        break;
		    case 2:
		        if(resultCode == RESULT_OK){  
		        	
		        	String selectedImages = imageReturnedIntent.getExtras().getString("trnxnID");
		        	
		        	String[] parts = selectedImages.split("\\|");
		        	for (int i = 0; i < parts.length; i++) {
		        		File file = new File(parts[i]);
	            
			            myGallery.addView(insertPhoto(file.getAbsolutePath()));
			            imagePath.add(file.getAbsolutePath());

					}
		        	
		        	final ScrollView sc = (ScrollView)findViewById(R.id.addHomeScroll); 
		        			        	
		        	sc.post(new Runnable() {

		                @Override
		                public void run() {
		                   sc.fullScroll(ScrollView.FOCUS_DOWN);
		                }
		            });
		        }
		        break;
		    }
		    
		}
		
	public void getAddress(View view){
		
		
		GPSTracker gps = new GPSTracker(view.getContext(), this, R.id.edtEndereco);
		
		if (!gps.canGetLocation()) {
			gps.showSettingsAlert();
		}else{
		
		}

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
    	
    	
    	
        imageView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {            	
            // TODO Auto-generated method stub
            	Button remover = (Button)findViewById(R.id.btRemoveImg);

                Toast.makeText(getApplicationContext(), "Image Selected", Toast.LENGTH_SHORT).show();
                if (imageView.getImageAlpha() == 80) {
                	imageView.setImageAlpha(255);
                	removeIdex.remove(layout);
                	if (removeIdex.isEmpty()) {
                        //isdown = false;
                        //invalidateOptionsMenu();                		
                		remover.setEnabled(false);
					}
                	imagePath.add(path);
                	
				}else{
                
					imageView.setImageAlpha(80);					
					removeIdex.add(layout);
					imagePath.remove(path);
	                //isdown = true;
	                //invalidateOptionsMenu();
					remover.setEnabled(true);
				}

                return true;
            }

        });
    	
    	
        layout.addView(imageView);
    	
    	return layout;
    }
    
    public void removeImage(View view){
		if (!removeIdex.isEmpty()) {
			for (int i = 0; i < removeIdex.size(); i++) {					
				myGallery.removeView(removeIdex.get(i));
			}		
		    Button remover = (Button)findViewById(R.id.btRemoveImg);
    		remover.setEnabled(false);

		}
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
    
    
    

    


}
