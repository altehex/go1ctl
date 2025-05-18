package ru.sfti.go1ctl.util;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

public class OnClickListenerNavCtrl implements View.OnClickListener {
    protected final NavController _navCtrl;
    protected final Fragment _parent;

    public OnClickListenerNavCtrl(NavController navCtrl)
    {
        this._navCtrl = navCtrl;
        this._parent = null;
    }

    public OnClickListenerNavCtrl(NavController navCtrl, Fragment parent)
    {
        this._navCtrl = navCtrl;
        this._parent = parent;
    }

    @Override
    public void onClick(View v) {
        this._navCtrl.popBackStack();
    }
}