precision mediump float;
varying vec3 vVertexPosition;

void main() {
    float x = vVertexPosition.x;
    float y = vVertexPosition.y;
    float part1 = sqrt(x * x + (y - 1.118) * (y - 1.118));
    float part2 = sqrt(x * x + (y + 1.118) * (y + 1.118));
    float distance = part1 + part2;

    if (distance < 2.98) {
        gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
    } else if (distance < 2.99) {
        gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
    } else {
        gl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);
    }
}