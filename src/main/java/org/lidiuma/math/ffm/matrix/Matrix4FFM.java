package org.lidiuma.math.ffm.matrix;

import org.lidiuma.math.api.matrix.Matrix4;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import static java.lang.foreign.ValueLayout.JAVA_DOUBLE;
import static java.lang.foreign.ValueLayout.JAVA_FLOAT;

/// [Row/Column-major order](https://en.wikipedia.org/wiki/Row-_and_column-major_order)
public interface Matrix4FFM<N> {

    int COMPONENT_COUNT = 16;
    F32 F32 = new F32();
    F64 F64 = new F64();

    /// Returns the total number of bytes to represent one [Matrix4].
    /// @return the amount of bytes.
    long byteSize();

    /// Writes the provided [Matrix4] into a newly allocated [MemorySegment] from the provided [Arena] in column-major order.
    ///
    /// The [MemorySegment] will have the exact size to fit one [Matrix4] element, and an alignment equal to the size in bytes of one column ([#byteSize()]` / 4`).
    /// @param arena the allocator used to create the [MemorySegment].
    /// @param matrix the matrix to write (using a row-major view) into the [MemorySegment].
    /// @return the [MemorySegment] containing the [Matrix4] in column-major order.
    default MemorySegment writeColumn(Arena arena, Matrix4<N> matrix) {
        final long size = byteSize();
        final var segment = arena.allocate(size, size >>> 2); // division by 4.
        writeColumn(segment, 0, matrix);
        return segment;
    }

    /// Writes the provided [Matrix4] into a [MemorySegment] in column-major order at the provided logical index.
    /// @param destination the [MemorySegment] to write into.
    /// @param index the logical index (e.g., `0`=first matrix, `1`=second matrix).
    /// @param matrix the matrix to write (using a row-major view) into the [MemorySegment].
    void writeColumn(MemorySegment destination, long index, Matrix4<N> matrix);

    /// Writes the provided [Matrix4] into a newly allocated [MemorySegment] from the provided [Arena] in row-major order.
    ///
    /// The [MemorySegment] will have the exact size to fit one [Matrix4] element, and an alignment equal to the size in bytes of one column ([#byteSize()]` / 4`).
    /// @param arena the allocator used to create the [MemorySegment].
    /// @param matrix the matrix to write (using a row-major view) into the [MemorySegment].
    /// @return the [MemorySegment] containing the [Matrix4] in row-major order.
    default MemorySegment writeRow(Arena arena, Matrix4<N> matrix) {
        final long size = byteSize();
        final var segment = arena.allocate(size, size >>> 2); // division by 4.
        writeRow(segment, 0, matrix);
        return segment;
    }

    /// Writes the provided [Matrix4] into a [MemorySegment] in row-major order at the provided logical index.
    /// @param destination the [MemorySegment] to write into.
    /// @param index the logical index (e.g., `0`=first matrix, `1`=second matrix).
    /// @param matrix the matrix to write (using a row-major view) into the [MemorySegment].
    void writeRow(MemorySegment destination, long index, Matrix4<N> matrix);

    /// Reads a [Matrix4] from the start of the [MemorySegment] in column-major order.
    /// @param source the [MemorySegment] to read from.
    /// @param factory the factory providing an instance of a [Matrix4] using a row-major view.
    /// @param <M> the matrix type to return.
    /// @return the [Matrix4] instance representing the value read from the [MemorySegment].
    default <M extends Matrix4<N>> M readColumn(MemorySegment source, Factory<M, N> factory) {
        return readColumn(source, 0, factory);
    }

    /// Reads a [Matrix4] from the [MemorySegment] in column-major order at the provided logical index.
    /// @param source the [MemorySegment] to read from.
    /// @param index the logical index (e.g., `0`=first matrix, `1`=second matrix).
    /// @param factory the factory providing an instance of a [Matrix4] using a row-major view.
    /// @param <M> the matrix type to return.
    /// @return the [Matrix4] instance representing the value read from the [MemorySegment].
    <M extends Matrix4<N>> M readColumn(MemorySegment source, long index, Factory<M, N> factory);

    /// Reads a [Matrix4] from the start of the [MemorySegment] in row-major order.
    /// @param source the [MemorySegment] to read from.
    /// @param factory the factory providing an instance of a [Matrix4] using a row-major view.
    /// @param <M> the matrix type to return.
    /// @return the [Matrix4] instance representing the value read from the [MemorySegment].
    default <M extends Matrix4<N>> M readRow(MemorySegment source, Factory<M, N> factory) {
        return readRow(source, 0, factory);
    }

    /// Reads a [Matrix4] from the [MemorySegment] in row-major order at the provided logical index.
    /// @param source the [MemorySegment] to read from.
    /// @param index the logical index (e.g., `0`=first matrix, `1`=second matrix).
    /// @param factory the factory providing an instance of a [Matrix4] using a row-major view.
    /// @param <M> the matrix type to return.
    /// @return the [Matrix4] instance representing the value read from the [MemorySegment].
    <M extends Matrix4<N>> M readRow(MemorySegment source, long index, Factory<M, N> factory);

    @FunctionalInterface
    interface Factory<T extends Matrix4<N>, N> {

        /// Returns an instance of [Matrix4] using a row-major view.
        /// @return the instance of the matrix.
        T create(
                N m00, N m01, N m02, N m03,
                N m10, N m11, N m12, N m13,
                N m20, N m21, N m22, N m23,
                N m30, N m31, N m32, N m33
        );
    }

    final class F32 implements Matrix4FFM<Float> {

        private F32() {}

        private static ValueLayout.OfFloat layout() {
            return JAVA_FLOAT;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public void writeColumn(MemorySegment destination, long index, Matrix4<Float> matrix) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            // Column 1
            destination.setAtIndex(layout, at    ,  matrix.m00());
            destination.setAtIndex(layout, at + 1,  matrix.m10());
            destination.setAtIndex(layout, at + 2,  matrix.m20());
            destination.setAtIndex(layout, at + 3,  matrix.m30());
            // Column 2
            destination.setAtIndex(layout, at + 4,  matrix.m01());
            destination.setAtIndex(layout, at + 5,  matrix.m11());
            destination.setAtIndex(layout, at + 6,  matrix.m21());
            destination.setAtIndex(layout, at + 7,  matrix.m31());
            // Column 3
            destination.setAtIndex(layout, at + 8,  matrix.m02());
            destination.setAtIndex(layout, at + 9,  matrix.m12());
            destination.setAtIndex(layout, at + 10, matrix.m22());
            destination.setAtIndex(layout, at + 11, matrix.m32());
            // Column 4
            destination.setAtIndex(layout, at + 12, matrix.m03());
            destination.setAtIndex(layout, at + 13, matrix.m13());
            destination.setAtIndex(layout, at + 14, matrix.m23());
            destination.setAtIndex(layout, at + 15, matrix.m33());
        }

        @Override
        public void writeRow(MemorySegment destination, long index, Matrix4<Float> matrix) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            // Row 0
            destination.setAtIndex(layout, at    , matrix.m00());
            destination.setAtIndex(layout, at + 1, matrix.m01());
            destination.setAtIndex(layout, at + 2, matrix.m02());
            destination.setAtIndex(layout, at + 3, matrix.m03());
            // Row 1
            destination.setAtIndex(layout, at + 4, matrix.m10());
            destination.setAtIndex(layout, at + 5, matrix.m11());
            destination.setAtIndex(layout, at + 6, matrix.m12());
            destination.setAtIndex(layout, at + 7, matrix.m13());
            // Row 2
            destination.setAtIndex(layout, at + 8, matrix.m20());
            destination.setAtIndex(layout, at + 9, matrix.m21());
            destination.setAtIndex(layout, at + 10, matrix.m22());
            destination.setAtIndex(layout, at + 11, matrix.m23());
            // Row 3
            destination.setAtIndex(layout, at + 12, matrix.m30());
            destination.setAtIndex(layout, at + 13, matrix.m31());
            destination.setAtIndex(layout, at + 14, matrix.m32());
            destination.setAtIndex(layout, at + 15, matrix.m33());
        }

        @Override
        public <M extends Matrix4<Float>> M readColumn(MemorySegment source, long index, Factory<M, Float> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(
                    source.getAtIndex(layout, at    ),  // m00
                    source.getAtIndex(layout, at + 4),  // m01
                    source.getAtIndex(layout, at + 8),  // m02
                    source.getAtIndex(layout, at + 12), // m03

                    source.getAtIndex(layout, at + 1),  // m10
                    source.getAtIndex(layout, at + 5),  // m11
                    source.getAtIndex(layout, at + 9),  // m12
                    source.getAtIndex(layout, at + 13), // m13

                    source.getAtIndex(layout, at + 2),  // m20
                    source.getAtIndex(layout, at + 6),  // m21
                    source.getAtIndex(layout, at + 10), // m22
                    source.getAtIndex(layout, at + 14), // m23

                    source.getAtIndex(layout, at + 3),  // m30
                    source.getAtIndex(layout, at + 7),  // m31
                    source.getAtIndex(layout, at + 11), // m32
                    source.getAtIndex(layout, at + 15)  // m33
            );
        }

        @Override
        public <M extends Matrix4<Float>> M readRow(MemorySegment source, long index, Factory<M, Float> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(
                    // Row 0
                    source.getAtIndex(layout, at    ),
                    source.getAtIndex(layout, at + 1),
                    source.getAtIndex(layout, at + 2),
                    source.getAtIndex(layout, at + 3),
                    // Row 1
                    source.getAtIndex(layout, at + 4),
                    source.getAtIndex(layout, at + 5),
                    source.getAtIndex(layout, at + 6),
                    source.getAtIndex(layout, at + 7),
                    // Row 2
                    source.getAtIndex(layout, at + 8),
                    source.getAtIndex(layout, at + 9),
                    source.getAtIndex(layout, at + 10),
                    source.getAtIndex(layout, at + 11),
                    // Row 3
                    source.getAtIndex(layout, at + 12),
                    source.getAtIndex(layout, at + 13),
                    source.getAtIndex(layout, at + 14),
                    source.getAtIndex(layout, at + 15)
            );
        }
    }

    final class F64 implements Matrix4FFM<Double> {

        private F64() {}

        private static ValueLayout.OfDouble layout() {
            return JAVA_DOUBLE;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public void writeColumn(MemorySegment destination, long index, Matrix4<Double> matrix) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            // Column 1
            destination.setAtIndex(layout, at    ,  matrix.m00());
            destination.setAtIndex(layout, at + 1,  matrix.m10());
            destination.setAtIndex(layout, at + 2,  matrix.m20());
            destination.setAtIndex(layout, at + 3,  matrix.m30());
            // Column 2
            destination.setAtIndex(layout, at + 4,  matrix.m01());
            destination.setAtIndex(layout, at + 5,  matrix.m11());
            destination.setAtIndex(layout, at + 6,  matrix.m21());
            destination.setAtIndex(layout, at + 7,  matrix.m31());
            // Column 3
            destination.setAtIndex(layout, at + 8,  matrix.m02());
            destination.setAtIndex(layout, at + 9,  matrix.m12());
            destination.setAtIndex(layout, at + 10, matrix.m22());
            destination.setAtIndex(layout, at + 11, matrix.m32());
            // Column 4
            destination.setAtIndex(layout, at + 12, matrix.m03());
            destination.setAtIndex(layout, at + 13, matrix.m13());
            destination.setAtIndex(layout, at + 14, matrix.m23());
            destination.setAtIndex(layout, at + 15, matrix.m33());
        }

        @Override
        public void writeRow(MemorySegment destination, long index, Matrix4<Double> matrix) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            // Row 0
            destination.setAtIndex(layout, at    , matrix.m00());
            destination.setAtIndex(layout, at + 1, matrix.m01());
            destination.setAtIndex(layout, at + 2, matrix.m02());
            destination.setAtIndex(layout, at + 3, matrix.m03());
            // Row 1
            destination.setAtIndex(layout, at + 4, matrix.m10());
            destination.setAtIndex(layout, at + 5, matrix.m11());
            destination.setAtIndex(layout, at + 6, matrix.m12());
            destination.setAtIndex(layout, at + 7, matrix.m13());
            // Row 2
            destination.setAtIndex(layout, at + 8, matrix.m20());
            destination.setAtIndex(layout, at + 9, matrix.m21());
            destination.setAtIndex(layout, at + 10, matrix.m22());
            destination.setAtIndex(layout, at + 11, matrix.m23());
            // Row 3
            destination.setAtIndex(layout, at + 12, matrix.m30());
            destination.setAtIndex(layout, at + 13, matrix.m31());
            destination.setAtIndex(layout, at + 14, matrix.m32());
            destination.setAtIndex(layout, at + 15, matrix.m33());
        }

        @Override
        public <M extends Matrix4<Double>> M readColumn(MemorySegment source, long index, Factory<M, Double> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(
                    source.getAtIndex(layout, at    ),  // m00
                    source.getAtIndex(layout, at + 4),  // m01
                    source.getAtIndex(layout, at + 8),  // m02
                    source.getAtIndex(layout, at + 12), // m03

                    source.getAtIndex(layout, at + 1),  // m10
                    source.getAtIndex(layout, at + 5),  // m11
                    source.getAtIndex(layout, at + 9),  // m12
                    source.getAtIndex(layout, at + 13), // m13

                    source.getAtIndex(layout, at + 2),  // m20
                    source.getAtIndex(layout, at + 6),  // m21
                    source.getAtIndex(layout, at + 10), // m22
                    source.getAtIndex(layout, at + 14), // m23

                    source.getAtIndex(layout, at + 3),  // m30
                    source.getAtIndex(layout, at + 7),  // m31
                    source.getAtIndex(layout, at + 11), // m32
                    source.getAtIndex(layout, at + 15)  // m33
            );
        }

        @Override
        public <M extends Matrix4<Double>> M readRow(MemorySegment source, long index, Factory<M, Double> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(
                    // Row 0
                    source.getAtIndex(layout, at    ),  // m00
                    source.getAtIndex(layout, at + 1),  // m01
                    source.getAtIndex(layout, at + 2),  // m02
                    source.getAtIndex(layout, at + 3),  // m03
                    // Row 1
                    source.getAtIndex(layout, at + 4),  // m10
                    source.getAtIndex(layout, at + 5),  // m11
                    source.getAtIndex(layout, at + 6),  // m12
                    source.getAtIndex(layout, at + 7),  // m13
                    // Row 2
                    source.getAtIndex(layout, at + 8),  // m20
                    source.getAtIndex(layout, at + 9),  // m21
                    source.getAtIndex(layout, at + 10), // m22
                    source.getAtIndex(layout, at + 11), // m23
                    // Row 3
                    source.getAtIndex(layout, at + 12), // m30
                    source.getAtIndex(layout, at + 13), // m31
                    source.getAtIndex(layout, at + 14), // m32
                    source.getAtIndex(layout, at + 15)  // m33
            );
        }
    }
}
