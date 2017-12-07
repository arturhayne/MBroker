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

 * The license text is available online and in the LICENSE file accompanying 
 * the distribution of this program.
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.htech.mbroker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeListFragment extends ListFragment {
	
	SqlOpenHelper banco;
	List<String[]> homes; 
	String[] id;
	String[] prop;
	String[] sale;
	String[] rent;
	String[] image;
	
	public HomeListFragment(){
		
		banco = SqlOpenHelper.getInstance();
		homes = new ArrayList<String[]>();
		homes = banco.getAllHomes();
		String addHome = "";
		
		if (Locale.getDefault().getLanguage().contains("pt")) {
			addHome = "Adicione um imovel";
		}else {
			addHome = "Add a Home";
		}

		
		if (homes.isEmpty()) {
			prop = new String[] {
					addHome
			};
			
			id = new String[] {"0"};
			sale = new String[] {""};
			rent = new String[] {""};
			image  = new String[] {""};
			
		}else{
			
			id = new String[homes.size()];
			prop = new String[homes.size()];	
			sale = new String[homes.size()];
			rent = new String[homes.size()];	
			image =  new String[homes.size()];	
			
			for (int i = 0; i < homes.size(); i++) {				

				id[i] = homes.get(i)[0];
				image[i] =  homes.get(i)[1];
				prop[i] = homes.get(i)[2];
				rent[i] = homes.get(i)[3];
				sale[i] = homes.get(i)[4];
				

			}
		}
		
	}
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        Context ctx = this.getActivity().getApplicationContext();
		Resources res = ctx.getResources();

		//String[] options = res.getStringArray(R.array.country_names);
		//String[] options2 = res.getStringArray(R.array.second_line);
		//TypedArray icons = res.obtainTypedArray(R.array.country_icons);
		//String[] icons 
		
		setListAdapter(new HomeAdapter(ctx, R.layout.main_list_item,
				id, prop, image ,sale, rent));
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main, container, false);
	}
	
	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {
	
		if (homes.isEmpty()) {
			Intent intent = new Intent(getActivity(), AddHome.class);
	    	startActivity(intent);	
	    	getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	    	getActivity().finish();
			
		}else{		
			Intent intent = new Intent(getActivity(), HomeActivity.class);
			intent.putExtra("ID",id);
			startActivity(intent);
			getActivity().finish();		
			getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);		
		}
		
	}
	
	
  /*  private String[] icons={
    		Environment.getExternalStorageDirectory()+"/DCIM/Camera/mbroker2.png", 
    		Environment.getExternalStorageDirectory()+"/DCIM/Camera/bhutan.png",
    		Environment.getExternalStorageDirectory()+"/DCIM/Camera/20131213_173426.jpg",
            "http://a3.twimg.com/profile_images/121630227/Droid_normal.jpg",
            "http://a1.twimg.com/profile_images/957149154/twitterhalf_normal.jpg",
            "http://a1.twimg.com/profile_images/97470808/icon_normal.png",
            "http://a3.twimg.com/profile_images/511790713/AG.png",
            "http://a3.twimg.com/profile_images/956404323/androinica-avatar_normal.png"
    };*/
}