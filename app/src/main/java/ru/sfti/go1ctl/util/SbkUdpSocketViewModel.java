package ru.sfti.go1ctl.util;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.sfti.go1ctl.sbk_java.SbkUdpSocket;

public class SbkUdpSocketViewModel
    extends ViewModel
{
    private final MutableLiveData<SbkUdpSocket> _socketData =
            new MutableLiveData<SbkUdpSocket>();

    public void
    set(SbkUdpSocket socket) {
        this._socketData.setValue(socket);
    }


    public SbkUdpSocket
    get() {
        return this._socketData.getValue();
    }
}
