package ru.sfti.go1ctl;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

import ru.sfti.go1ctl.databinding.ActivityMainBinding;
import ru.sfti.go1ctl.sbk_java.SbkUdpSocket;
import ru.sfti.go1ctl.util.SbkUdpSocketViewModel;

public class MainActivity
    extends AppCompatActivity
{

    protected void
    onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }
}
