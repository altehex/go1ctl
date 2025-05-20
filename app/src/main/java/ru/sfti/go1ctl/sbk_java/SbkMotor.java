package ru.sfti.go1ctl.sbk_java;

public class SbkMotor {
    public static final int SEND_SIZE      = 27;
    public static final int RECV_HIGH_SIZE = 38;
    public static final int RECV_LOW_SIZE  = 32;

    private byte _mode;
    private float _q;
    private float _dq;
    private float _ddq;
    private float _tau;
    private float _kp;
    private float _kd;
}
