package ru.solandme.remindmeabout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.solandme.remindmeabout.Holiday;
import ru.solandme.remindmeabout.R;
import ru.solandme.remindmeabout.adapters.HolidaysAdapter;
import ru.solandme.remindmeabout.database.HolidayDBHelper;

public class BirthdayFragment extends Fragment {
    private static final int LAYOUT = R.layout.fragment_birthday;
    protected View view;
    private HolidaysAdapter holidaysAdapter;
    private RecyclerView recyclerView;

    private HolidayDBHelper holidayDbHelper;

    public BirthdayFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvBirthday);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        holidayDbHelper = new HolidayDBHelper(getContext());
        holidaysAdapter = new HolidaysAdapter(holidayDbHelper.getHolidaysByCategory(Holiday.CATEGORY_BIRTHDAY));
        recyclerView.setAdapter(holidaysAdapter);
        holidayDbHelper.close();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        holidaysAdapter = new HolidaysAdapter(holidayDbHelper.getHolidaysByCategory(Holiday.CATEGORY_BIRTHDAY));
        recyclerView.setAdapter(holidaysAdapter);
        holidayDbHelper.close();
    }
}

