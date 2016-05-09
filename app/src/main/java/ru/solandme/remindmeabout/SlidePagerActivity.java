package ru.solandme.remindmeabout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import ru.solandme.remindmeabout.fragments.SlidePageFragment;
import ru.solandme.remindmeabout.trasformers.ZoomOutPageTransformer;

public class SlidePagerActivity extends AppCompatActivity {
    Toolbar toolbar;
    RadioGroup rg_sex;
    RadioButton rb_for_her;
    RadioButton rb_for_him;
    SwitchCompat switch_sms;
    PagerAdapter pagerAdapter;
    ViewPager slidePager;
    String filter = "0"; // forHim - 1, forHer - 2, forAll - 0
    public static final String OFF = "0";
    public static final String ON = "1";
    String sms = OFF; // on - 1, off - 0


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        initToolBar();
        initView();
    }

    private void setMyAdapter(ViewPager slidePager) {
        pagerAdapter = new SlidePageAdapter(getSupportFragmentManager(), getTextCongratulate());
        if (slidePager != null) {
            //Можно выбрать другую анимацию, заменив PageTransformer на
            //slidePager.setPageTransformer(true, new DepthPageTransformer());
            slidePager.setPageTransformer(true, new ZoomOutPageTransformer());
            slidePager.setAdapter(pagerAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        if (slidePager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            slidePager.setCurrentItem(0);
        }
    }

    private void initView() {
        slidePager = (ViewPager) findViewById(R.id.slidePager);
        setMyAdapter(slidePager);

        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        switch_sms = (SwitchCompat) findViewById(R.id.switch_sms);
        rb_for_her = (RadioButton) findViewById(R.id.rb_for_her);
        rb_for_him = (RadioButton) findViewById(R.id.rb_for_him);

        if (getIntent().getStringExtra("code").equals(Holiday.CODE_WOMANSDAY) ||
                getIntent().getStringExtra("code").equals(Holiday.CODE_MANSDAY)) {
            rb_for_her.setEnabled(false);
            rb_for_him.setEnabled(false);
        }

        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_for_all:
                        filter = "0";
                        setMyAdapter(slidePager);
                        break;
                    case R.id.rb_for_him:
                        filter = "1";
                        setMyAdapter(slidePager);
                        break;
                    case R.id.rb_for_her:
                        filter = "2";
                        setMyAdapter(slidePager);
                        break;
                    default:
                        filter = "0";
                        setMyAdapter(slidePager);
                }
            }
        });
        switch_sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sms = ON;
                    setMyAdapter(slidePager);
                } else {
                    sms = OFF;
                    setMyAdapter(slidePager);
                }
            }
        });


    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBarSlideActivity);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra("holidayName"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    private List<String> getTextCongratulate() {
        List<String> textCongratulate;
        CongratulateDBHelper helper = new CongratulateDBHelper(getApplicationContext());
        textCongratulate = helper.getCongratulationsByCode(getIntent().getStringExtra("code"), sms, filter);
        if (textCongratulate.size() == 0) {
            textCongratulate.add(getString(R.string.empty));
        }
        helper.close();
        return textCongratulate;
    }

    private class SlidePageAdapter extends FragmentStatePagerAdapter {
        List<String> textData;

        public SlidePageAdapter(FragmentManager fm, List<String> textData) {
            super(fm);
            this.textData = textData;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = new SlidePageFragment();
            Bundle args = new Bundle();
            args.putString(SlidePageFragment.ARG_TEXT, textData.get(position));
            args.putInt(SlidePageFragment.ARG_POSITION, position + 1);
            args.putInt(SlidePageFragment.ARG_COUNT, getCount());
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getCount() {
            return textData.size();
        }
    }


    // Работаем с меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_slide_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                onBackPressed();
                break;
            case R.id.about_app_menu_item:
                Toast.makeText(getApplicationContext(), item.getTitle().toString(), Toast.LENGTH_LONG).show();
                break;
            case R.id.add_new_congratulate:
                Toast.makeText(getApplicationContext(), item.getTitle().toString(), Toast.LENGTH_LONG).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


}
