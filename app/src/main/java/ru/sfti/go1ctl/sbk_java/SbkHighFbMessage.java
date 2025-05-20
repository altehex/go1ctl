package ru.sfti.go1ctl.sbk_java;


public class SbkHighFbMessage
        extends SbkFbMessage
{
    public static final int SIZE = 1087;

    protected static final int
        _IMU_OFF            = 22,
        _JOINT_OFF          = 75,
        _BMS_OFF            = 835,
        _FOOT_FORCE_OFF     = 869,
        _MODE_OFF           = 885,
        _GAIT_OFF           = 890,
        _FOOT_HEIGHT_OFF    = 891,
        _POS_OFF            = 895,
        _BODY_HEIGHT_OFF    = 907,
        _VELOCITY_OFF       = 911,
        _YAW_SPEED_OFF      = 923,
        _RANGE_OBSTACLE_OFF = 927;

    public SbkImu imu;
    public SbkMotorFb[] joints;
    public SbkBms bms;

    private SbkModeEnum _mode;
    private SbkGaitEnum _gait;

    private short[] _footForce;
    private float _footHeight;
    private float[] _pos;
    private float _bodyHeight;
    private float[] _velocity;
    private float _yawSpeed;
    private float[] _rangeObstacle;

    public SbkHighFbMessage()
    {
        super(SIZE);
        this._init();
    }

    public SbkHighFbMessage(byte[] packet)
    {
        super(SIZE);
        this._init();

        if (packet.length != SIZE) return;

        this._packetBuffer = packet;
        this._deserialize();
    }

    private void
    _init()
    {
        this.joints = new SbkMotorFb[SbkJointEnum.JOINT_COUNT.ordinal()];
        this._footForce = new short[4];
        this._pos = new float[3];
        this._velocity = new float[3];
        this._rangeObstacle = new float[4];
    }


    @Override
    protected void _deserialize() {
        this._mode = SbkModeEnum.values()[this._packetBuffer[_MODE_OFF]];
        this._gait = SbkGaitEnum.values()[this._packetBuffer[_GAIT_OFF]];

        for (int i = 0; i < 3; ++i) {
            this._velocity[i] = this._toFloat(_VELOCITY_OFF + 4 * i);
            this._pos[i]      = this._toFloat(_POS_OFF + 4 * i);
        }

        for (int i = 0; i < 4; ++i) {
            this._footForce[i]     = this._toShort(_FOOT_FORCE_OFF + 2 * i);
            this._rangeObstacle[i] = this._toFloat(_RANGE_OBSTACLE_OFF + 4 * i);
        }

        this._footHeight = this._toFloat(_FOOT_HEIGHT_OFF);
        this._bodyHeight = this._toFloat(_BODY_HEIGHT_OFF);
        this._yawSpeed   = this._toFloat(_YAW_SPEED_OFF);

        for (int i = 0; i < SbkJointEnum.JOINT_COUNT.ordinal(); ++i)
            this._deserializeMotor(i);
    }

    protected void
    _deserializeMotor(int i) {
        int offset = _JOINT_OFF + SbkMotor.RECV_HIGH_SIZE*i;

    }


    public byte
    getMode()
    {
        if (this._mode != null)
            return (byte) this._mode.ordinal();

        return (byte) SbkModeEnum.IDLE.ordinal();
    }


    public byte
    getGait()
    {
        if (this._gait != null)
            return (byte) this._gait.ordinal();

        return (byte) SbkGaitEnum.IDLE.ordinal();
    }


    public float[]
    getVelocity() {
        return this._velocity;
    }
}
