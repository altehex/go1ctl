package ru.sfti.go1ctl.util;

import android.view.View;

import androidx.navigation.NavController;

public class OnClickRouteListener implements View.OnClickListener {
    private final NavController _navCtrl;
    private int _route;

    public OnClickRouteListener(NavController navCtrl, int route) {
        this._navCtrl = navCtrl;
        this._route = route;
    }

    @Override
    public void onClick(View v) {
        this._navCtrl.navigate(_route);
    }
}
