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
    private boolean les_pren;   //true = lesson, false = prenotation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        System.out.println("\"" + "true" + "\"");
        sessionManager = new SessionManager(HomeActivity.this);
        toolbar = findViewById(R.id.homeToolBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        selectableDays = findViewById(R.id.tabs);
        navUser = navigationView.getHeaderView(0).findViewById(R.id.navUser);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        day = "Lun";
        les_pren = true;
        serverQry.setContext(HomeActivity.this);
        setSupportActionBar(toolbar);
        drawerLayout.setDrawerListener(toggle);
        navUser.setText(sessionManager.getUsername());

        launchNewLessonsFragment();

        selectableDays.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //richiamo la query per quel determinato giorno

                switch (tab.getPosition()){
                    case 0:             //Lun
                        day = "Lun";
                        break;
                    case 1:             //Mar
                        day = "Mar";
                        break;
                    case 2:             //Mer
                        day = "Mer";
                        break;
                    case 3:             //Gio
                        day = "Gio";
                        break;
                    case 4:             //Ven
                        day = "Ven";
                        break;
                }
                //Se sto navigando tra le lezioni verranno lanciate le lezioni
                //Se sto navigando tra le prenotazioni verranno lanciate le prenotazioni
                if(les_pren)
                    launchNewLessonsFragment();
                else
                    launchNewPrenotationFragment();
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
                final int return_home = R.id.return_home;
                final int active_pren = R.id.active_pren;
                final int del_pren = R.id.del_pren;
                final int all_pren = R.id.all_pren;
                final int logout = R.id.logout;
                //se lancio return home reimposto les_pren a true in modo tale da visualizzare le lezioni
                //se invece richiamo una qualsiasi delle prenotazioni, imposto les_pren a false
                switch (item.getItemId()) {
                    case return_home:
                        les_pren = true;
                        launchNewLessonsFragment();
                        drawerLayout.closeDrawers();
                        break;
                    case active_pren:
                        les_pren = false;
                        System.out.println("puppa");
                        drawerLayout.closeDrawers();
                        break;
                    case del_pren:
                        les_pren = false;
                        drawerLayout.closeDrawers();
                        break;
                    case all_pren:
                        les_pren = false;
                        day = "Lun";
                        launchNewPrenotationFragment();
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

    private void launchNewLessonsFragment() {
        LessonsFragment lessonsFragment = new LessonsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("giorno", day);
        bundle.putString("sessionid", sessionManager.getSession());
        lessonsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, lessonsFragment).commit();
    }

    private void launchNewPrenotationFragment() {
        PrenotationFragment prenFrag = new PrenotationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("giorno", day);
        bundle.putString("sessionid", sessionManager.getSession());
        prenFrag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, prenFrag).commit();
    }
}