package com.bennyplo.openglpipeline;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class EllipsePointsProvider {

    private final float a;
    private final float b;

    public EllipsePointsProvider(float a, float b) {
        this.a = a;
        this.b = b;
    }

    public List<Pair<Float, Float>> getPoints(int pointsCount) {
        if (pointsCount < 2) {
            throw new IllegalArgumentException("pointsCount must be at least 2");
        }

        List<Pair<Float, Float>> result = new ArrayList<>();
        float tAddition = (float) (2f * Math.PI / (float) pointsCount);
        float t = 0;
        for (int i = 0; i < pointsCount; i++) {
            float x = (float) (a * Math.cos(t));
            float y = (float) (b * Math.sin(t));
            t += tAddition;
            result.add(new Pair<Float, Float>(x, y));
        }
        return result;
    }
}
