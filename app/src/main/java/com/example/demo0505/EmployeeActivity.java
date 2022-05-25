package com.example.demo0505;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class EmployeeActivity extends AppCompatActivity {

    NavigationBarView navigationBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss);
        initView();
        E_HomeFragment homeFragment = new E_HomeFragment();
        openFragment(homeFragment);
        /*        ButtonNavigationBar       */
        navigationBarView.getMenu().setGroupCheckable(0, false, false);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                E_HomeFragment homeFragment = new E_HomeFragment();
                E_PunchFragment E_PunchFragment = new E_PunchFragment();
                E_BulletinFragment E_bulletinFragment = new E_BulletinFragment();
                E_SalaryFragment E_SalaryFragment = new E_SalaryFragment();
                switch (item.getItemId()) {
                    case R.id.home:
                        openFragment(homeFragment);
                        break;
                    case R.id.punch:
                        openFragment(E_PunchFragment);
                        break;
                    case R.id.salary:
                        openFragment(E_SalaryFragment);
                        break;
                    case R.id.Bulletin:
                        openFragment(E_bulletinFragment);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void initView(){
        navigationBarView = findViewById(R.id.navigation);
    }

    private void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_main, fragment);
        fragmentTransaction.commit();
    }
}