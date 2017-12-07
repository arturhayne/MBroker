package br.com.htech.mbroker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import com.htech.mbroker.R;

public class AddClient extends Activity {
	
	SqlOpenHelper banco;
	Boolean fromMain = true; 
	long id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_client);
		// Show the Up button in the action bar.
		setupActionBar();
		
		EditText inputField = (EditText) findViewById(R.id.edtCfone);
		inputField.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		
		
		if (this.getIntent().hasExtra("ID")) {
			id = this.getIntent().getExtras().getLong("ID");
			this.getClient(id);
			fromMain = false;
		}
		

		
	}
	
	private void getClient(long clientId){
		
		banco = SqlOpenHelper.getInstance();		
		String [] cliente = banco.getClient(clientId);
		
		EditText nome = (EditText)findViewById(R.id.edtCNome);
		EditText fone = (EditText)findViewById(R.id.edtCfone);
		EditText email = (EditText)findViewById(R.id.edtCEmail);
		EditText solicit = (EditText)findViewById(R.id.edtCsolicit);
		CheckBox comprar = (CheckBox)findViewById(R.id.cbxCComprar);
		CheckBox alugar = (CheckBox)findViewById(R.id.cbxCAlugar);
		
		nome.setText(cliente[5]);
		fone.setText(cliente[6]);
		email.setText(cliente[4]);
		solicit.setText(cliente[7]);
		if (cliente[2].compareTo("1")==0) {
			comprar.setChecked(true);
		}else {
			comprar.setChecked(false);
		}
		if (cliente[3].compareTo("1")==0) {
			alugar.setChecked(true);
		}else{
			alugar.setChecked(false);
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
		getMenuInflater().inflate(R.menu.add_client, menu);

		
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
				voltar();
				return true;				
			}

			case R.id.save:	{
				salvarCliente();
				voltar();
			}		
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private void voltar() {
		// TODO Auto-generated method stub
		if (fromMain) {
			Intent intent = new Intent(AddClient.this, MainActivity.class);
			startActivity(intent);
			finish();			
		}else{		
			Intent intent = new Intent(AddClient.this, ClientActivity.class);
			intent.putExtra("ID",id);
			startActivity(intent);
			finish();
		}
		
		
		
	}

	private void salvarCliente() {
		// TODO Auto-generated method stub
		SqlOpenHelper banco = SqlOpenHelper.getInstance();
		int comprar = 0;
		int alugar = 0;
		int eAccomplished = 0;
		
		EditText eName = (EditText)findViewById(R.id.edtCNome);
		EditText ePhone = (EditText)findViewById(R.id.edtCfone);
		EditText eEmail = (EditText)findViewById(R.id.edtCEmail);		
		EditText eSolicit = (EditText)findViewById(R.id.edtCsolicit);
		CheckBox cbxComprar = (CheckBox)findViewById(R.id.cbxCComprar);
		if (cbxComprar.isChecked()) {
			comprar = 1;
		}
		CheckBox cbxAlugar = (CheckBox)findViewById(R.id.cbxCAlugar);
		if (cbxAlugar.isChecked()) {
			alugar = 1;
		}
		
		if (fromMain) {
			banco.insertClient(eName.getText().toString(), 
					ePhone.getText().toString(), 
					eEmail.getText().toString(), 
					comprar,
					alugar,
					eSolicit.getText().toString(), 
					eAccomplished);
		}else {
			banco.updateClient(id,eName.getText().toString(), 
					ePhone.getText().toString(), 
					eEmail.getText().toString(), 
					comprar,
					alugar,
					eSolicit.getText().toString(), 
					eAccomplished);
		}
		

		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {        	
			Intent intent = new Intent(AddClient.this, MainActivity.class);
			startActivity(intent);
			finish();
			return true;

		}else{
			return super.onKeyDown(keyCode, event);
		}
	}

}
