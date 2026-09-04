package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.matrix.Matrix4;
import org.lidiuma.math.ffm.matrix.Matrix4FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_FLOAT;

public final class Mat4F32Test {

    private final Arena arena = Arena.ofAuto();
    private final Matrix4<Float> matrix = new Mat4(
             0f,  1f,  2f,  3f,
             4f,  5f,  6f,  7f,
             8f,  9f, 10f, 11f,
            12f, 13f, 14f, 15f
    );
    private final MemorySegment array = arena.allocate(Matrix4FFM.F32.byteSize() * 10);

    private record Mat4(
            Float m00, Float m01, Float m02, Float m03,
            Float m10, Float m11, Float m12, Float m13,
            Float m20, Float m21, Float m22, Float m23,
            Float m30, Float m31, Float m32, Float m33
    ) implements Matrix4<Float> {}

    @Test
    public void byteSize() {
        Assertions.assertEquals(16, Matrix4FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Matrix4FFM.COMPONENT_COUNT * JAVA_FLOAT.byteSize(), Matrix4FFM.F32.byteSize());
    }

    @Test
    public void column() {
        final var   segment = Matrix4FFM.F32.writeColumn(arena, matrix);
        final Mat4 original = Matrix4FFM.F32.readColumn(segment, Mat4::new);
        Assertions.assertEquals(matrix, original);
    }

    @Test
    public void row() {
        final var   segment = Matrix4FFM.F32.writeRow(arena, matrix);
        final Mat4 original = Matrix4FFM.F32.readRow(segment, Mat4::new);
        Assertions.assertEquals(matrix, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void columnIndexed(int index) {
        Matrix4FFM.F32.writeColumn(array, index, matrix);
        final Mat4 original = Matrix4FFM.F32.readColumn(array, index, Mat4::new);
        Assertions.assertEquals(matrix, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void rowIndexed(int index) {
        Matrix4FFM.F32.writeRow(array, index, matrix);
        final Mat4 original = Matrix4FFM.F32.readRow(array, index, Mat4::new);
        Assertions.assertEquals(matrix, original);
    }

    @Test
    public void columnRowMix() {
        final var   segment = Matrix4FFM.F32.writeColumn(arena, matrix);
        final Mat4 original = Matrix4FFM.F32.readRow(segment, Mat4::new);
        Assertions.assertNotEquals(matrix, original);
    }

    @Test
    public void rowColumnMix() {
        final var   segment = Matrix4FFM.F32.writeRow(arena, matrix);
        final Mat4 original = Matrix4FFM.F32.readColumn(segment, Mat4::new);
        Assertions.assertNotEquals(matrix, original);
    }
}
