package com.nikhil.androidwebview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;

/**
 * Created by Nikhil on 26-01-2016.
 */
public class JavaScriptInterface {
    private Activity activity;

    public JavaScriptInterface(Activity activiy) {
        this.activity = activiy;
    }

    @JavascriptInterface
    public String getString() {
        return "Hello buddy ";
    }
}
