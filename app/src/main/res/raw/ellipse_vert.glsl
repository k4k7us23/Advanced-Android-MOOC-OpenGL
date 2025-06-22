attribute vec3 aVertexPosition;
varying vec3 vVertexPosition;
uniform mat4 uMVPMatrix;

void main() {
    vVertexPosition = aVertexPosition;
    gl_Position = uMVPMatrix *vec4(aVertexPosition, 1.0);
}