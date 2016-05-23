package ru.solandme.remindmeabout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import ru.solandme.remindmeabout.R;
import ru.solandme.remindmeabout.SlidePagerActivity;
import ru.solandme.remindmeabout.database.CongratulateDBHelper;

public class SlidePageFragment extends Fragment {

    public static final String ARG_POSITION = "item_position";
    public static final String ARG_COUNT = "item_count";
    public static final String ARG_TEXT = "item_text";
    public static final String ARG_VERSE = "item_verse";
    public static final String ARG_ID = "item_id";
    public static final String ARG_FAVORITE = "item_favorite";
    public static final String ARG_SEX = "item_sex";

    //    View fragment;
    TextView textContainer;
    TextView textCounter;
    CheckBox checkBoxAddFavorite;
    View colorTeg;

//    View fragment_filter;

    CongratulateDBHelper helper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_slide_page, container, false);
        final Bundle args = getArguments();

//        fragment_filter = getActivity().findViewById(R.id.fragment_filter);

//        fragment = rootView.findViewById(R.id.fragment);
        textCounter = (TextView) rootView.findViewById(R.id.text_counter);
        textContainer = (TextView) rootView.findViewById(R.id.text_container);
        colorTeg = rootView.findViewById(R.id.colorTeg);

        checkBoxAddFavorite = (CheckBox) rootView.findViewById(R.id.chb_add_to_fw);

        textCounter.setText(args.getInt(ARG_POSITION) + " / " + args.getInt(ARG_COUNT));
        textContainer.setText(args.getString(ARG_TEXT));


        if (args.getString(ARG_FAVORITE).equals(SlidePagerActivity.OFF)) {
            checkBoxAddFavorite.setChecked(false);
        } else checkBoxAddFavorite.setChecked(true);


        if (args.getString(ARG_VERSE).equals(SlidePagerActivity.OFF)) {
            textContainer.setGravity(Gravity.START);
        }

        if (args.getString(ARG_SEX).equals(SlidePagerActivity.FORHER)) {
//            fragment.setBackgroundColor(getResources().getColor(R.color.colorForHer));
            colorTeg.setBackgroundColor(getResources().getColor(R.color.colorForHer));

        } else if (args.getString(ARG_SEX).equals(SlidePagerActivity.FORHIM)) {
//            fragment.setBackgroundColor(getResources().getColor(R.color.colorForHim));
            colorTeg.setBackgroundColor(getResources().getColor(R.color.colorForHim));
        } else {
//            fragment.setBackgroundColor(getResources().getColor(R.color.colorUniversal));
            colorTeg.setBackgroundColor(getResources().getColor(R.color.colorWhiteDark));
//
        }

        checkBoxAddFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                helper = new CongratulateDBHelper(getContext());
                if (isChecked) {
                    helper.setFavorite(getArguments().getString(ARG_ID));
                } else {
                    helper.clearFavorite(getArguments().getString(ARG_ID));
                }
            }
        });
        return rootView;
    }

}
