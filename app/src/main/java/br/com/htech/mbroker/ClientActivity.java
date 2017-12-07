package br.com.htech.mbroker;

import com.htech.mbroker.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ClientActivity extends Activity {
	
	SqlOpenHelper banco;
	long id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client);		
		id = this.getIntent().getExtras().getLong("ID");
		this.getClient(id);
		
	}
	
	private void getClient(long clientId){
		
		banco = SqlOpenHelper.getInstance();		
		String [] cliente = banco.getClient(clientId);
		String comprarAlugar = "";
		
		TextView nome = (TextView)findViewById(R.id.txtCNome);
		TextView fone = (TextView)findViewById(R.id.txtCFone);
		TextView email = (TextView)findViewById(R.id.txtCemail);
		TextView solicit = (TextView)findViewById(R.id.txtCSolicit);
		TextView cprAlug = (TextView)findViewById(R.id.txtComprarAlugar);
		
		nome.setText(cliente[5]);
		fone.setText(cliente[6]);
		email.setText(cliente[4]);
		solicit.setText(cliente[7]);
		if (cliente[2].compareTo("1")==0) {
			comprarAlugar = getString(R.string.buy);
		}
		if (cliente[3].compareTo("1")==0) {
			if (comprarAlugar.compareTo(getString(R.string.buy))==0) {
				comprarAlugar += "/"+getString(R.string.rent);
			}else{
			comprarAlugar += getString(R.string.rent) ;
			}
		}
		
		if (cliente[3].compareTo("1")==0 || cliente[2].compareTo("1")==0) {
			comprarAlugar = getString(R.string.to) + comprarAlugar;
		}
		
		cprAlug.setText(comprarAlugar);
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client, menu);
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
			case R.id.editMenuClient:	{
				Intent intent = new Intent(ClientActivity.this, AddClient.class);
				intent.putExtra("ID",id);
				startActivity(intent);
				finish();
				//overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			}
			break;
			case R.id.deleteMenuClient:{
				String mnsagemretorno = getString(R.string.deleteClientMsg);
				
	    		new AlertDialog.Builder( this ) 
	    		.setTitle( getString(R.string.deleteRecord) ) 
	    		.setMessage(mnsagemretorno )
	    		.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener()
	    	    {
					
					public void onClick(DialogInterface dialog, int which) {
						return; 					
					}
	    	    	
	    	    })
	    		.setPositiveButton( "OK", new DialogInterface.OnClickListener()
	    	    {	    	        
	    	        public void onClick(DialogInterface dialog, int which) {	    	            	        	
	    				banco.deleteClient(id);
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
		Intent intent = new Intent(ClientActivity.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}
	
	@Override
	public void onBackPressed() 
	{
		voltarPrincipal();
	}
	
	 public void callClient(final View view){
	    	TextView nTelefone = (TextView)findViewById(R.id.txtCFone);
	    	
	    	
	    	
			final String mnsagemretorno = nTelefone.getText().toString();
			
			new AlertDialog.Builder( this ) 
			.setTitle( getString(R.string.call) ) 
			.setMessage(mnsagemretorno )
			.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener()
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
	    
	    public void sendEmailClient(final View view){
	    	TextView email = (TextView)findViewById(R.id.txtCemail);
	    	
	    	
	    	
			final String mnsagemretorno = email.getText().toString();
			
			new AlertDialog.Builder( this ) 
			.setTitle( getString(R.string.sendEmail) ) 
			.setMessage(mnsagemretorno )
			.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener()
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
