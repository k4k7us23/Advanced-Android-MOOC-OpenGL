package com.bennyplo.openglpipeline;

import android.opengl.GLES32;
import android.util.Pair;

import com.bennyplo.openglpipeline.utils.ShaderLoader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

public class Ellipse {

    private final FloatBuffer vertexBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mMVPMatrixHandle;
    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    private int vertexCount;// number of vertices
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private static final int POINTS_COUNT = 50;

    private ShaderLoader shaderLoader = new ShaderLoader(App.INSTANCE);

    public Ellipse() throws IOException {
        float[] vertexes = getSquareVertexes();

        ByteBuffer bb = ByteBuffer.allocateDirect(vertexes.length * 4);// (# of coordinate values * 4 bytes per float)
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertexes);
        vertexBuffer.position(0);
        vertexCount = vertexes.length / COORDS_PER_VERTEX;


        // prepare shaders and OpenGL program
        int vertexShader = MyRenderer.loadShader(GLES32.GL_VERTEX_SHADER, shaderLoader.loaderShader(R.raw.ellipse_vert));
        int fragmentShader = MyRenderer.loadShader(GLES32.GL_FRAGMENT_SHADER, shaderLoader.loaderShader(R.raw.ellipse_frag));
        mProgram = GLES32.glCreateProgram();             // create empty OpenGL Program
        GLES32.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES32.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES32.glLinkProgram(mProgram);                  // link the  OpenGL program to create an executable
        GLES32.glUseProgram(mProgram);// Add program to OpenGL environment
        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES32.glGetAttribLocation(mProgram, "aVertexPosition");
        // Enable a handle to the triangle vertices
        GLES32.glEnableVertexAttribArray(mPositionHandle);
        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES32.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyRenderer.checkGlError("glGetUniformLocation");
    }

    public void draw(float[] mvpMatrix) {
        // Apply the projection and view transformation
        GLES32.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyRenderer.checkGlError("glUniformMatrix4fv");
        //set the attribute of the vertex to point to the vertex buffer
        GLES32.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES32.GL_FLOAT, false, vertexStride, vertexBuffer);
        // Draw the triangle
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_FAN, 0, vertexCount);
    }

    private float[] getSquareVertexes() {
        EllipsePointsProvider provider = new EllipsePointsProvider(1f, 1.5f);
        List<Pair<Float, Float>> points = provider.getPoints(POINTS_COUNT);
        points.add(points.get(0));

        float[] result = new float[(points.size() + 1) * 3];
        result[0] = 0f;
        result[1] = 0f;
        result[2] = 1f;

        int index = 3;
        for (Pair<Float, Float> point : points) {
            result[index++] = point.first;
            result[index++] = point.second;
            result[index++] = 1f;
        }
        return result;
    }
}
