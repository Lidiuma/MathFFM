package org.lidiuma.math.ffm.matrix;

import org.lidiuma.math.api.matrix.Matrix3;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import static java.lang.foreign.ValueLayout.JAVA_DOUBLE;
import static java.lang.foreign.ValueLayout.JAVA_FLOAT;

/// [Row/Column-major order](https://en.wikipedia.org/wiki/Row-_and_column-major_order)
public interface Matrix3FFM<N> {

    int COMPONENT_COUNT = 9;
    F32 F32 = new F32();
    F64 F64 = new F64();

    /// Returns the total number of bytes to represent one [Matrix3].
    /// @return the amount of bytes.
    long byteSize();

    /// Writes the provided [Matrix3] into a newly allocated [MemorySegment] from the provided [Arena] in column-major order.
    ///
    /// The [MemorySegment] will have the exact size to fit one [Matrix3] element, and an alignment equal to the alignment of the underlying numerical type.
    /// @param arena the allocator used to create the [MemorySegment].
    /// @param matrix the matrix to write (using a row-major view) into the [MemorySegment].
    /// @return the [MemorySegment] containing the [Matrix3] in column-major order.
    default MemorySegment writeColumn(Arena arena, Matrix3<N> matrix) {
        final long size = byteSize();
        final var segment = arena.allocate(size, size / COMPONENT_COUNT);
        writeColumn(segment, 0, matrix);
        return segment;
    }

    /// Writes the provided [Matrix3] into a [MemorySegment] in column-major order at the provided logical index.
    /// @param destination the [MemorySegment] to write into.
    /// @param index the logical index (e.g., `0`=first matrix, `1`=second matrix).
    /// @param matrix the matrix to write (using a row-major view) into the [MemorySegment].
    void writeColumn(MemorySegment destination, long index, Matrix3<N> matrix);

    /// Writes the provided [Matrix3] into a newly allocated [MemorySegment] from the provided [Arena] in row-major order.
    ///
    /// The [MemorySegment] will have the exact size to fit one [Matrix3] element, and an alignment equal to the alignment of the underlying numerical type.
    /// @param arena the allocator used to create the [MemorySegment].
    /// @param matrix the matrix to write (using a row-major view) into the [MemorySegment].
    /// @return the [MemorySegment] containing the [Matrix3] in row-major order.
    default MemorySegment writeRow(Arena arena, Matrix3<N> matrix) {
        final long size = byteSize();
        final var segment = arena.allocate(size, size / COMPONENT_COUNT);
        writeRow(segment, 0, matrix);
        return segment;
    }

    /// Writes the provided [Matrix3] into a [MemorySegment] in row-major order at the provided logical index.
    /// @param destination the [MemorySegment] to write into.
    /// @param index the logical index (e.g., `0`=first matrix, `1`=second matrix).
    /// @param matrix the matrix to write (using a row-major view) into the [MemorySegment].
    void writeRow(MemorySegment destination, long index, Matrix3<N> matrix);

    /// Reads a [Matrix3] from the start of the [MemorySegment] in column-major order.
    /// @param source the [MemorySegment] to read from.
    /// @param factory the factory providing an instance of a [Matrix3] using a row-major view.
    /// @param <M> the matrix type to return.
    /// @return the [Matrix3] instance representing the value read from the [MemorySegment].
    default <M extends Matrix3<N>> M readColumn(MemorySegment source, Factory<M, N> factory) {
        return readColumn(source, 0, factory);
    }

    /// Reads a [Matrix3] from the [MemorySegment] in column-major order at the provided logical index.
    /// @param source the [MemorySegment] to read from.
    /// @param index the logical index (e.g., `0`=first matrix, `1`=second matrix).
    /// @param factory the factory providing an instance of a [Matrix3] using a row-major view.
    /// @param <M> the matrix type to return.
    /// @return the [Matrix3] instance representing the value read from the [MemorySegment].
    <M extends Matrix3<N>> M readColumn(MemorySegment source, long index, Factory<M, N> factory);

    /// Reads a [Matrix3] from the start of the [MemorySegment] in row-major order.
    /// @param source the [MemorySegment] to read from.
    /// @param factory the factory providing an instance of a [Matrix3] using a row-major view.
    /// @param <M> the matrix type to return.
    /// @return the [Matrix3] instance representing the value read from the [MemorySegment].
    default <M extends Matrix3<N>> M readRow(MemorySegment source, Factory<M, N> factory) {
        return readRow(source, 0, factory);
    }

    /// Reads a [Matrix3] from the [MemorySegment] in row-major order at the provided logical index.
    /// @param source the [MemorySegment] to read from.
    /// @param index the logical index (e.g., `0`=first matrix, `1`=second matrix).
    /// @param factory the factory providing an instance of a [Matrix3] using a row-major view.
    /// @param <M> the matrix type to return.
    /// @return the [Matrix3] instance representing the value read from the [MemorySegment].
    <M extends Matrix3<N>> M readRow(MemorySegment source, long index, Factory<M, N> factory);

    @FunctionalInterface
    interface Factory<T extends Matrix3<N>, N> {

        /// Returns an instance of [Matrix3] using a row-major view.
        /// @return the instance of the matrix.
        T create(
                N m00, N m01, N m02,
                N m10, N m11, N m12,
                N m20, N m21, N m22
        );
    }

    final class F32 implements Matrix3FFM<Float> {

        private F32() {}

        private static ValueLayout.OfFloat layout() {
            return JAVA_FLOAT;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public void writeColumn(MemorySegment destination, long index, Matrix3<Float> matrix) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            // Column 0
            destination.setAtIndex(layout, at    , matrix.m00());
            destination.setAtIndex(layout, at + 1, matrix.m10());
            destination.setAtIndex(layout, at + 2, matrix.m20());
            // Column 1
            destination.setAtIndex(layout, at + 3, matrix.m01());
            destination.setAtIndex(layout, at + 4, matrix.m11());
            destination.setAtIndex(layout, at + 5, matrix.m21());
            // Column 2
            destination.setAtIndex(layout, at + 6, matrix.m02());
            destination.setAtIndex(layout, at + 7, matrix.m12());
            destination.setAtIndex(layout, at + 8, matrix.m22());
        }

        @Override
        public void writeRow(MemorySegment destination, long index, Matrix3<Float> matrix) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            // Row 0
            destination.setAtIndex(layout, at    , matrix.m00());
            destination.setAtIndex(layout, at + 1, matrix.m01());
            destination.setAtIndex(layout, at + 2, matrix.m02());
            // Row 1
            destination.setAtIndex(layout, at + 3, matrix.m10());
            destination.setAtIndex(layout, at + 4, matrix.m11());
            destination.setAtIndex(layout, at + 5, matrix.m12());
            // Row 2
            destination.setAtIndex(layout, at + 6, matrix.m20());
            destination.setAtIndex(layout, at + 7, matrix.m21());
            destination.setAtIndex(layout, at + 8, matrix.m22());
        }

        @Override
        public <M extends Matrix3<Float>> M readColumn(MemorySegment source, long index, Factory<M, Float> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(
                    // Row 0
                    source.getAtIndex(layout, at    ), // m00
                    source.getAtIndex(layout, at + 3), // m01
                    source.getAtIndex(layout, at + 6), // m02
                    // Row 1
                    source.getAtIndex(layout, at + 1), // m10
                    source.getAtIndex(layout, at + 4), // m11
                    source.getAtIndex(layout, at + 7), // m12
                    // Row 2
                    source.getAtIndex(layout, at + 2), // m20
                    source.getAtIndex(layout, at + 5), // m21
                    source.getAtIndex(layout, at + 8)  // m22
            );
        }

        @Override
        public <M extends Matrix3<Float>> M readRow(MemorySegment source, long index, Factory<M, Float> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(
                    // Row 0
                    source.getAtIndex(layout, at    ), // m00
                    source.getAtIndex(layout, at + 1), // m01
                    source.getAtIndex(layout, at + 2), // m02
                    // Row 1
                    source.getAtIndex(layout, at + 3), // m10
                    source.getAtIndex(layout, at + 4), // m11
                    source.getAtIndex(layout, at + 5), // m12
                    // Row 2
                    source.getAtIndex(layout, at + 6), // m20
                    source.getAtIndex(layout, at + 7), // m21
                    source.getAtIndex(layout, at + 8)  // m22
            );
        }
    }

    final class F64 implements Matrix3FFM<Double> {

        private F64() {}

        private static ValueLayout.OfDouble layout() {
            return JAVA_DOUBLE;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public void writeColumn(MemorySegment destination, long index, Matrix3<Double> matrix) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            // Column 0
            destination.setAtIndex(layout, at    , matrix.m00());
            destination.setAtIndex(layout, at + 1, matrix.m10());
            destination.setAtIndex(layout, at + 2, matrix.m20());
            // Column 1
            destination.setAtIndex(layout, at + 3, matrix.m01());
            destination.setAtIndex(layout, at + 4, matrix.m11());
            destination.setAtIndex(layout, at + 5, matrix.m21());
            // Column 2
            destination.setAtIndex(layout, at + 6, matrix.m02());
            destination.setAtIndex(layout, at + 7, matrix.m12());
            destination.setAtIndex(layout, at + 8, matrix.m22());
        }

        @Override
        public void writeRow(MemorySegment destination, long index, Matrix3<Double> matrix) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            // Row 0
            destination.setAtIndex(layout, at    , matrix.m00());
            destination.setAtIndex(layout, at + 1, matrix.m01());
            destination.setAtIndex(layout, at + 2, matrix.m02());
            // Row 1
            destination.setAtIndex(layout, at + 3, matrix.m10());
            destination.setAtIndex(layout, at + 4, matrix.m11());
            destination.setAtIndex(layout, at + 5, matrix.m12());
            // Row 2
            destination.setAtIndex(layout, at + 6, matrix.m20());
            destination.setAtIndex(layout, at + 7, matrix.m21());
            destination.setAtIndex(layout, at + 8, matrix.m22());
        }

        @Override
        public <M extends Matrix3<Double>> M readColumn(MemorySegment source, long index, Factory<M, Double> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(
                    // Row 0
                    source.getAtIndex(layout, at    ), // m00
                    source.getAtIndex(layout, at + 3), // m01
                    source.getAtIndex(layout, at + 6), // m02
                    // Row 1
                    source.getAtIndex(layout, at + 1), // m10
                    source.getAtIndex(layout, at + 4), // m11
                    source.getAtIndex(layout, at + 7), // m12
                    // Row 2
                    source.getAtIndex(layout, at + 2), // m20
                    source.getAtIndex(layout, at + 5), // m21
                    source.getAtIndex(layout, at + 8)  // m22
            );
        }

        @Override
        public <M extends Matrix3<Double>> M readRow(MemorySegment source, long index, Factory<M, Double> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(
                    // Row 0
                    source.getAtIndex(layout, at    ), // m00
                    source.getAtIndex(layout, at + 1), // m01
                    source.getAtIndex(layout, at + 2), // m02
                    // Row 1
                    source.getAtIndex(layout, at + 3), // m10
                    source.getAtIndex(layout, at + 4), // m11
                    source.getAtIndex(layout, at + 5), // m12
                    // Row 2
                    source.getAtIndex(layout, at + 6), // m20
                    source.getAtIndex(layout, at + 7), // m21
                    source.getAtIndex(layout, at + 8)  // m22
            );
        }
    }
}
