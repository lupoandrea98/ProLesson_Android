package com.app.ProLesson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.app.ProLesson.Controller.SessionManager;
import com.app.ProLesson.Controller.serverQry;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private SessionManager sessionManager;
    private TextView navUser;
    private String day;
    private TabLayout selectableDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        sessionManager = new SessionManager(HomeActivity.this);
        toolbar = findViewById(R.id.homeToolBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        selectableDays = findViewById(R.id.tabs);
        navUser = navigationView.getHeaderView(0).findViewById(R.id.navUser);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        day = "Lun";
        serverQry.setContext(HomeActivity.this);
        setSupportActionBar(toolbar);
        drawerLayout.setDrawerListener(toggle);
        navUser.setText(sessionManager.getUsername());

        LessonsFragment lessonsFragment = new LessonsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("giorno", day);
        bundle.putString("sessionid", sessionManager.getSession());
        lessonsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, lessonsFragment).commit();

        selectableDays.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //richiamo la query per quel determinato giorno
                LessonsFragment lessonsFragment;
                switch (tab.getPosition()){
                    case 0:             //Lun
                        day = "Lun";
                        lessonsFragment = new LessonsFragment();
                        bundle.putString("giorno", day);
                        lessonsFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().add(R.id.container, lessonsFragment).commit();
                        break;
                    case 1:             //Mar
                        day = "Mar";
                        lessonsFragment = new LessonsFragment();
                        bundle.putString("giorno", day);
                        lessonsFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().add(R.id.container, lessonsFragment).commit();
                        break;
                    case 2:             //Mer
                        day = "Mer";
                        lessonsFragment = new LessonsFragment();
                        bundle.putString("giorno", day);
                        lessonsFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().add(R.id.container, lessonsFragment).commit();
                        break;
                    case 3:             //Gio
                        day = "Gio";
                        lessonsFragment = new LessonsFragment();
                        bundle.putString("giorno", day);
                        lessonsFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().add(R.id.container, lessonsFragment).commit();
                        break;
                    case 4:             //Ven
                        day = "Ven";
                        lessonsFragment = new LessonsFragment();
                        bundle.putString("giorno", day);
                        lessonsFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().add(R.id.container, lessonsFragment).commit();
                        break;
                }
            }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            }
        );

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Uso uno switch per richiamare tutto quello da fare alla pressione dei tasti presenti nel menu
                final int active_pren = R.id.active_pren;
                final int del_pren = R.id.del_pren;
                final int all_pren = R.id.all_pren;
                final int logout = R.id.logout;
                switch (item.getItemId()) {
                    case active_pren:
                        System.out.println("puppa");
                        drawerLayout.closeDrawers();
                        break;
                    case del_pren:
                        drawerLayout.closeDrawers();
                        break;
                    case all_pren:
                        drawerLayout.closeDrawers();
                        break;
                    case logout:
                        logout();
                        drawerLayout.closeDrawers();
                        break;
                }

                return false;
            }
        });

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toggle.syncState();
    }


    private void logout() {
        //this method will remove session and open login screen
        SessionManager sessionManager = new SessionManager(HomeActivity.this);
        serverQry.logout(sessionManager.getSession());
        sessionManager.removeSession();
        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}