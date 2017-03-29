package my.framework.look.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import my.framework.look.R;
import my.framework.look.adapter.ViewPagerAdapter;
import my.framework.look.ui.fragment.MeiZiFragment;
import my.framework.look.ui.fragment.WangYiFragment;
import my.framework.look.ui.fragment.ZhiHuFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    long exitTime = 0;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();

    }

    /**
     * 初始化fragment
     */
    public void initFragment() {

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        ZhiHuFragment zhiHuFragment = new ZhiHuFragment();
        WangYiFragment wangYiFragment = new WangYiFragment();
        MeiZiFragment meiZiFragment = new MeiZiFragment();

        viewPagerAdapter.addFragment(zhiHuFragment, "知乎日报");
        viewPagerAdapter.addFragment(wangYiFragment, "网易头条");
        viewPagerAdapter.addFragment(meiZiFragment, "每日看看");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void initView() {

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //去掉默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //创建返回键，并实现打开关/闭监听
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再点一次，退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            goAboutActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        this.startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.zhi_hu) {
            tabLayout.getTabAt(0).select();
        } else if (id == R.id.top_news) {
            tabLayout.getTabAt(1).select();
        } else if (id == R.id.meizi) {
            tabLayout.getTabAt(2).select();
        } else if (id == R.id.nav_theme) {

        } else if (id == R.id.nav_set) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
