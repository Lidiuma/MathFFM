package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.matrix.Matrix4;
import org.lidiuma.math.ffm.matrix.Matrix4FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_DOUBLE;

public final class Mat4F64Test {

    private final Arena arena = Arena.ofAuto();
    private final Matrix4<Double> matrix = new Mat4(
             0d,  1d,  2d,  3d,
             4d,  5d,  6d,  7d,
             8d,  9d, 10d, 11d,
            12d, 13d, 14d, 15d
    );
    private final MemorySegment array = arena.allocate(Matrix4FFM.F64.byteSize() * 10);

    private record Mat4(
            Double m00, Double m01, Double m02, Double m03,
            Double m10, Double m11, Double m12, Double m13,
            Double m20, Double m21, Double m22, Double m23,
            Double m30, Double m31, Double m32, Double m33
    ) implements Matrix4<Double> {}

    @Test
    public void byteSize() {
        Assertions.assertEquals(16, Matrix4FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Matrix4FFM.COMPONENT_COUNT * JAVA_DOUBLE.byteSize(), Matrix4FFM.F64.byteSize());
    }

    @Test
    public void column() {
        final var   segment = Matrix4FFM.F64.writeColumn(arena, matrix);
        final Mat4 original = Matrix4FFM.F64.readColumn(segment, Mat4::new);
        Assertions.assertEquals(matrix, original);
    }

    @Test
    public void row() {
        final var   segment = Matrix4FFM.F64.writeRow(arena, matrix);
        final Mat4 original = Matrix4FFM.F64.readRow(segment, Mat4::new);
        Assertions.assertEquals(matrix, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void columnIndexed(int index) {
        Matrix4FFM.F64.writeColumn(array, index, matrix);
        final Mat4 original = Matrix4FFM.F64.readColumn(array, index, Mat4::new);
        Assertions.assertEquals(matrix, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void rowIndexed(int index) {
        Matrix4FFM.F64.writeRow(array, index, matrix);
        final Mat4 original = Matrix4FFM.F64.readRow(array, index, Mat4::new);
        Assertions.assertEquals(matrix, original);
    }

    @Test
    public void columnRowMix() {
        final var   segment = Matrix4FFM.F64.writeColumn(arena, matrix);
        final Mat4 original = Matrix4FFM.F64.readRow(segment, Mat4::new);
        Assertions.assertNotEquals(matrix, original);
    }

    @Test
    public void rowColumnMix() {
        final var   segment = Matrix4FFM.F64.writeRow(arena, matrix);
        final Mat4 original = Matrix4FFM.F64.readColumn(segment, Mat4::new);
        Assertions.assertNotEquals(matrix, original);
    }
}
