package com.lucas.study.organizeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lucas.study.organizeme.Activity.About;
import com.lucas.study.organizeme.Activity.AddTask;
import com.lucas.study.organizeme.Activity.AddToDo;
import com.lucas.study.organizeme.Activity.FirstUse;
import com.lucas.study.organizeme.Activity.HistoryTasks;
import com.lucas.study.organizeme.Model.AppConfig;
import com.lucas.study.organizeme.Page.Stats;
import com.lucas.study.organizeme.Page.Tasks;
import com.lucas.study.organizeme.Page.ToDos;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public boolean creatConfig;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public boolean doNotCreate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!doNotCreate){
            AppConfig aux = new AppConfig(true);
            aux.save();
        }

        List<AppConfig> appConfig = AppConfig.creatListConfig();

        if(appConfig.get(0).isFirstUse()){

            doNotCreate = true;
            Intent intentFirstUse = new Intent(this, FirstUse.class);
            startActivity(intentFirstUse);
        }else{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarApp);
            toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(toolbar);


            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

            tabLayout.getTabAt(0).setIcon(R.drawable.ic_round_alarm);
            //tabLayout.getTabAt(0).setText(R.string.tab_text_1);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_round_stats);
            //tabLayout.getTabAt(1).setText(R.string.tab_text_2);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_round_done_outline);
            //tabLayout.getTabAt(2).setText(R.string.tab_text_3);

        }

    }


    public void openAddTaskActivity(View view) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }

    public void openAddToDoActivity(View view) {
        Intent intent = new Intent(this, AddToDo.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history_tasks) {
            Intent intent = new Intent(MainActivity.this, HistoryTasks.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0 :
                    Tasks tab1 = new Tasks();
                    return tab1;

                case 1 :
                    Stats tab2 = new Stats();
                    return tab2;


                case 2 :
                    ToDos tab3 = new ToDos();
                    return tab3;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() { return 3; }
    }



}
