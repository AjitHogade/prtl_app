package com.portal.college.myapplication;




import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class NavigationDrawerFragment extends Fragment {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
private VivzAdapter adapter;
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer,container,false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new VivzAdapter(getActivity(),getData());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return layout;
    }
    public static List<Information> getData(){
        List<Information> data = new ArrayList<Information>();
        int icons[] = {
                R.drawable.ic_menu_camera,
                R.drawable.ic_menu_gallery,
                R.drawable.ic_menu_manage,
                R.drawable.ic_menu_send};
        String[] title = {"Camera","Gallery","Settings","Send"};
        for(int i=0;i<title.length && i<icons.length;i++){
            Information current = new Information();
            current.iconId = icons[i];
            current.title = title[i];
            data.add(current);
        }
        return data;

    }


    public void setUp(DrawerLayout drawerLayout,Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);

                }
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);

            }

        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {

            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
      /*  containerView = getActivity().findViewById(fragment_id);
        mDrawerLayout=drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open_drawer,R.string.close_drawer){
            @SuppressLint("NewApi")
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
				 if(!mUserLearnedDrawer){
					 mUserLearnedDrawer = false;
					 saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer+"");
				 }
				 getActivity().invalidateOptionsMenu();
				 */
            }

}
