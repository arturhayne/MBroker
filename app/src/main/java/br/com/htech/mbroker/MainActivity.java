package br.com.htech.mbroker;


import com.htech.mbroker.R;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new SqlOpenHelper(this);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// For each of the sections in the app, add a tab to the action bar.
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section1).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section2).setTabListener(this));
		
		
		
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
	}


	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

		/**
		 * On first tab we will show our list
		 */
		if (tab.getPosition() == 0) {
			ClientListFragment simpleListFragment = new ClientListFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.container, simpleListFragment).commit();
			
		} 
		else if (tab.getPosition() == 1) {
			HomeListFragment simpleListFragment = new HomeListFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.container, simpleListFragment).commit();
			
		}

	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply displays dummy text.
	 */
	/* public static class DummySectionFragment extends Fragment {
        public DummySectionFragment() {
        }

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            Bundle args = getArguments();
            textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER)));
            return textView;
        }
    }*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.addClient:
			
    	    intent = new Intent(MainActivity.this, AddClient.class);
	    	startActivity(intent);	
	    	finish();
			
			break;
		case R.id.addHome:
  			        			
    	    intent = new Intent(MainActivity.this, AddHome.class);
	    	startActivity(intent);	
	    	finish();
			break;
			/*case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				break;*/

		default:
			break;
		}
		//finish();

		return true;

	}
	
	@Override
	public void onBackPressed() 
	{

		finish();
	}
	
	
}
