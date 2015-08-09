package com.bettadapur.ruseandroid.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Alex on 8/7/2015.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment
{
}
