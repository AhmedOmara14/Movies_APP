package com.task.common;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.task.R;
import com.github.ybq.android.spinkit.style.DoubleBounce;

public class LoadingDialog extends DialogFragment {
    protected View view;
    protected ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view= inflater.inflate(R.layout.loading_dialog, container, false);
        progressBar =view.findViewById(R.id.progress_bar);
        DoubleBounce doubleBounce = new DoubleBounce();
        doubleBounce.setBounds(0, 0, 100, 100);
        doubleBounce.setColor(ContextCompat.getColor(requireContext(), R.color.purple_500));
        progressBar.setIndeterminateDrawable(doubleBounce);
        return view;
    }
}