package ru.sfti.go1ctl.high_level;

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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import ru.sfti.go1ctl.R;

/**
 * TODO: document your custom view class.
 */
public class JoystickView
        extends View
{
    private float _OX, _OY;
    private float _R, _r;
    private float _HX, _HY;

    private Paint _baseColor;
    private Paint _stickColor;

    public JoystickView(Context context) {
        super(context);
        this._init();
    }

    private void _init()
    {
        super.setWillNotDraw(false);

        float w = getWidth(), h = getHeight();

        this._OX = w/2;
        this._OY = h/2;

        this._R = Math.min(w, h)/3;
        this._r = Math.min(w, h)/5;

        this._HX = this._OX;
        this._HY = this._OY;

        this._baseColor = new Paint();
        this._baseColor.setARGB(255, 50, 50, 50);

        this._stickColor = new Paint();
        this._stickColor.setARGB(255, 150, 150, 150);

        this.setWillNotDraw(false);
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
    public void onDraw(@NonNull Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawCircle(this._OX, this._OY, this._R, this._baseColor);
        canvas.drawCircle(this._HY, this._HY, this._r, this._stickColor);
    }
}
