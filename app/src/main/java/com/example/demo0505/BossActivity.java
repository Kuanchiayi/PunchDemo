package com.example.demo0505;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class BossActivity extends AppCompatActivity {

    NavigationBarView navigationBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss);

        initView();

        /*      ButtonNavigationBar     */
        navigationBarView.getMenu().setGroupCheckable(0, false, false);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                HomeFragment homefragment = new HomeFragment();
                PunchFragment punchFragment = new PunchFragment();
                BulletinFragment bulletinFragment = new BulletinFragment();
                SalaryFragment salaryFragment = new SalaryFragment();
                switch (item.getItemId()) {
                    case R.id.home:
                        setTitle("首頁");
                        openFragment(homefragment);
                        break;
                    case R.id.punch:
                        setTitle("打卡");
                        openFragment(punchFragment);
                        break;
                    case R.id.Bulletin:
                        setTitle("公告");
                        openFragment(bulletinFragment);
                        break;
                    case R.id.salary:
                        setTitle("薪水計算");
                        openFragment(salaryFragment);
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