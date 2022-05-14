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

        /*        ButtonNavigationBar       */
        navigationBarView.getMenu().setGroupCheckable(0, false, false);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                B_HomeFragment homefragment = new B_HomeFragment();
                B_PunchFragment BPunchFragment = new B_PunchFragment();
                B_BulletinFragment bulletinFragment = new B_BulletinFragment();
                B_SalaryFragment BSalaryFragment = new B_SalaryFragment();
                switch (item.getItemId()) {
                    case R.id.home:
                        setTitle("首頁");
                        openFragment(homefragment);
                        break;
                    case R.id.punch:
                        setTitle("打卡");
                        openFragment(BPunchFragment);
                        break;
                    case R.id.Bulletin:
                        setTitle("公告");
                        openFragment(bulletinFragment);
                        break;
                    case R.id.salary:
                        setTitle("薪水計算");
                        openFragment(BSalaryFragment);
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