package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.matrix.Matrix3;
import org.lidiuma.math.ffm.matrix.Matrix3FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_DOUBLE;

public final class Mat3F64Test {

    private final Arena arena = Arena.ofAuto();
    private final Matrix3<Double> matrix = new Mat3(
            0d, 1d, 2d,
            3d, 4d, 5d,
            6d, 7d, 8d
    );
    private final MemorySegment array = arena.allocate(Matrix3FFM.F64.byteSize() * 10);

    private record Mat3(
            Double m00, Double m01, Double m02,
            Double m10, Double m11, Double m12,
            Double m20, Double m21, Double m22
    ) implements Matrix3<Double> {}

    @Test
    public void byteSize() {
        Assertions.assertEquals(9, Matrix3FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Matrix3FFM.COMPONENT_COUNT * JAVA_DOUBLE.byteSize(), Matrix3FFM.F64.byteSize());
    }

    @Test
    public void column() {
        final var   segment = Matrix3FFM.F64.writeColumn(arena, matrix);
        final Mat3 original = Matrix3FFM.F64.readColumn(segment, Mat3::new);
        Assertions.assertEquals(matrix, original);
    }

    @Test
    public void row() {
        final var   segment = Matrix3FFM.F64.writeRow(arena, matrix);
        final Mat3 original = Matrix3FFM.F64.readRow(segment, Mat3::new);
        Assertions.assertEquals(matrix, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void columnIndexed(int index) {
        Matrix3FFM.F64.writeColumn(array, index, matrix);
        final Mat3 original = Matrix3FFM.F64.readColumn(array, index, Mat3::new);
        Assertions.assertEquals(matrix, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void rowIndexed(int index) {
        Matrix3FFM.F64.writeRow(array, index, matrix);
        final Mat3 original = Matrix3FFM.F64.readRow(array, index, Mat3::new);
        Assertions.assertEquals(matrix, original);
    }

    @Test
    public void columnRowMix() {
        final var   segment = Matrix3FFM.F64.writeColumn(arena, matrix);
        final Mat3 original = Matrix3FFM.F64.readRow(segment, Mat3::new);
        Assertions.assertNotEquals(matrix, original);
    }

    @Test
    public void rowColumnMix() {
        final var   segment = Matrix3FFM.F64.writeRow(arena, matrix);
        final Mat3 original = Matrix3FFM.F64.readColumn(segment, Mat3::new);
        Assertions.assertNotEquals(matrix, original);
    }
}
