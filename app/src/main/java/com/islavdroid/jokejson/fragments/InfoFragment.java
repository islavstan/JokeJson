package com.islavdroid.jokejson.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.islavdroid.jokejson.R;

public class InfoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info,
                container, false);
        TextView textView =(TextView)view.findViewById(R.id.link);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://www.rzhunemogu.ru/'> rzhunemogu.ru </a>";
        textView.setText(Html.fromHtml("Весь материал взят с сайта "+text));
       // textView.setTextColor(getResources().getColor(R.color.gray));
        return view;
}}
