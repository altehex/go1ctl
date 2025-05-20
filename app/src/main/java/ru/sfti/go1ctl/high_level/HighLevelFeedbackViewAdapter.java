package ru.sfti.go1ctl.high_level;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.sfti.go1ctl.sbk_java.SbkHighFbMessage;
import ru.sfti.go1ctl.util.FeedbackViewAdapter;

public class HighLevelFeedbackViewAdapter
    extends FeedbackViewAdapter
{
    private static final String[] _fields = {
            "mode: ",
            "gait: ",
            "velocity: "
    };

    private final String[] _values;


    public HighLevelFeedbackViewAdapter() {
        super(_fields);

        this._values = new String[_fields.length];
    }


    public void
    setValues(SbkHighFbMessage fbMsg)
    {
        float[] velocity;

        this._values[FieldIndex.MODE.ordinal()] = String.valueOf(fbMsg.getMode());
        this._values[FieldIndex.GAIT.ordinal()] = String.valueOf(fbMsg.getGait());

        velocity = fbMsg.getVelocity();
        this._values[FieldIndex.VELOCITY.ordinal()] =
                  "x: " + velocity[0]
                + "y: " + velocity[1]
                + "z: " + velocity[2];

        super.setValues(this._values);
    }


    private enum
    FieldIndex
    {
        MODE, GAIT, VELOCITY
    }
}
