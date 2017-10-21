package com.example.roman.testatlanteam;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.card:
                    addCardFragment();
                    return true;
                case R.id.contact:
                    addContactFragment();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCardFragment();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    void addCardFragment(){

        Fragment fragment = new CardFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.contener, fragment);
        ft.commit();
    }

    void addContactFragment(){
        Fragment fragment = new ContactFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.contener, fragment);
        ft.commit();
    }

}
