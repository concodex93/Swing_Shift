package com.example.conor.SwingShift.MenuButton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conor.SwingShift.R;

/**
 * Created by conor on 02/06/2016.
 */
public class PageIntoOne extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.intro_one, container, false);

    }
}
