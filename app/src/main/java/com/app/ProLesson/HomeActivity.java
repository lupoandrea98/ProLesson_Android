package com.app.ProLesson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.app.ProLesson.Controller.SessionManager;
import com.app.ProLesson.Controller.serverQry;
import com.app.ProLesson.dataType.LessonModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //private LessonModel lessonObj;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TabLayout selectableDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        toolbar = findViewById(R.id.homeToolBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        selectableDays = findViewById(R.id.tabs);
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.setDrawerListener(toggle);


        selectableDays.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabID = tab.getId();
                Toast.makeText(HomeActivity.this, tabID, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, new LessonsFragment());
        fragmentTransaction.commit();

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Uso uno switch per richiamare tutto quello da fare alla pressione dei tasti presenti nel menu

        switch (item.getItemId()) {
            case R.id.active_pren:
                drawerLayout.closeDrawers();
                break;
            case R.id.del_pren:
                test();
                break;
            case R.id.all_pren:
                break;
            case R.id.logout:
                logout();
                break;

        }

        return true;
    }

    //************************************************************
    public void test() {
        SessionManager sessionManager = new SessionManager(HomeActivity.this);
        System.out.println("Sessione utente presa dalla homeActivity " + sessionManager.getSession());
    }
    //************************************************************

    public void logout() {
        //this method will remove session and open login screen
        SessionManager sessionManager = new SessionManager(HomeActivity.this);
        sessionManager.removeSession();
        //TODO: magari qui chiamo una funzione per effettuare il logout anche nel backend? (forse obsoleto, tanto devo rifare il login)
        serverQry.logout(sessionManager.getSession());
        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}