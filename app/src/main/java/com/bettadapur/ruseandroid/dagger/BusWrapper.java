package com.bettadapur.ruseandroid.dagger;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Alex on 8/9/2015.
 */
public class BusWrapper
{
    private static Bus bus;

    public static Bus GetInstance()
    {
        if(bus==null)
        {
            bus = new Bus(ThreadEnforcer.ANY);
        }
        return bus;
    }
}
