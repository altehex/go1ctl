package ru.sfti.go1ctl.high_level;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;

import ru.sfti.go1ctl.R;


public class JoystickView
        extends View
        implements View.OnTouchListener
{
    private static final String _TAG = "JoystickView";

    private float _OX, _OY;
    private float _R, _r;
    private float _HX, _HY;

    private Paint _baseColor;
    private Paint _stickColor;

    public JoystickView(Context context) {
        super(context);
        this._init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void _init()
    {
        super.setWillNotDraw(false);

        this._baseColor = new Paint();
        this._baseColor.setARGB(255, 50, 50, 50);

        this._stickColor = new Paint();
        this._stickColor.setARGB(255, 150, 150, 150);

        this.setWillNotDraw(false);

        this.setOnTouchListener(this);
    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._init();
    }

    public JoystickView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this._init();
    }


    @Override
    protected  void
    onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        this._OX = w/2f; this._OY = h/2f;

        this._HX = this._OX; this._HY = this._OY;

        this._R = Math.min(w, h) / 2f;
        this._r = Math.min(w, h) / 4f;
    }


    private void
    _updateStickPos(float touchX, float touchY)
    {
        float d = (float) Math.sqrt(
                  Math.pow(touchX-this._OX, 2)
                + Math.pow(touchY-this._OY, 2)
        );

        if (d < this._R-this._r) {
            this._HX = touchX; this._HY = touchY;
        } else {
            float r = (this._R-this._r)/d;
            float x = this._OX + (touchX-this._OX)*r;
            float y = this._OY + (touchY-this._OY)*r;
            this._HX = x; this._HY = y;
        }
    }


    @Override
    public void
    onDraw(@NonNull Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawCircle(this._OX, this._OY, this._R, this._baseColor);
        canvas.drawCircle(this._HX, this._HY, this._r, this._stickColor);
    }


    @Override
    public boolean
    onTouch(View v, MotionEvent event)
    {
        if ( ! v.equals(this)) return true;

        if (event.getAction() == MotionEvent.ACTION_MOVE)
            this._updateStickPos(event.getX(), event.getY());
        else {
            this._HX = this._OX; this._HY = this._OY;
        }

        this.invalidate();
        return true;
    }
}
