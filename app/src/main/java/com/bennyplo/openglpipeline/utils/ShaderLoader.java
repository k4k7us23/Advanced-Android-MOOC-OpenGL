package com.bennyplo.openglpipeline.utils;

import android.content.Context;
import android.support.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShaderLoader {

    private Context context;

    public ShaderLoader(Context context) {
        this.context = context;
    }

    public String loaderShader(@RawRes int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = br.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        return result.toString();
    }
}
