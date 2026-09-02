package org.lidiuma.math.ffm.tuples;

import org.lidiuma.math.api.tuple.UnaryTuple2;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import static java.lang.foreign.ValueLayout.*;

public interface Tuple2FFM<N> {

    long COMPONENT_COUNT = 2;
    I8  I8  = new I8();
    I16 I16 = new I16();
    I32 I32 = new I32();
    I64 I64 = new I64();
    F32 F32 = new F32();
    F64 F64 = new F64();

    long byteSize();

    MemorySegment write(Arena arena, UnaryTuple2<N> tuple);

    void write(MemorySegment destination, long index, UnaryTuple2<N> tuple);

    default void write(MemorySegment destination, UnaryTuple2<N> tuple) {
        write(destination, 0, tuple);
    }

    <T extends UnaryTuple2<N>> T read(MemorySegment source, long index, Builder<T, N> builder);

    default <T extends UnaryTuple2<N>> T read(MemorySegment source, Builder<T, N> builder) {
        return read(source, 0, builder);
    }

    @FunctionalInterface
    interface Builder<T extends UnaryTuple2<N>, N> {
        T build(N x, N y);
    }

    final class I8 implements Tuple2FFM<Byte> {

        private static ValueLayout.OfByte layout() {
            return JAVA_BYTE;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public MemorySegment write(Arena arena, UnaryTuple2<Byte> tuple) {
            final var segment = arena.allocate(byteSize(), layout().byteAlignment());
            write(segment, 0, tuple);
            return segment;
        }

        @Override
        public void write(MemorySegment destination, long index, UnaryTuple2<Byte> tuple) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            destination.setAtIndex(layout, at    , tuple.x());
            destination.setAtIndex(layout, at + 1, tuple.y());
        }

        @Override
        public <T extends UnaryTuple2<Byte>> T read(MemorySegment source, long index, Builder<T, Byte> builder) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return builder.build(
                    source.getAtIndex(layout, at    ),
                    source.getAtIndex(layout, at + 1)
            );
        }
    }

    final class I16 implements Tuple2FFM<Short> {

        private static ValueLayout.OfShort layout() {
            return JAVA_SHORT;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public MemorySegment write(Arena arena, UnaryTuple2<Short> tuple) {
            final var segment = arena.allocate(byteSize(), layout().byteAlignment());
            write(segment, 0, tuple);
            return segment;
        }

        @Override
        public void write(MemorySegment destination, long index, UnaryTuple2<Short> tuple) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            destination.setAtIndex(layout, at    , tuple.x());
            destination.setAtIndex(layout, at + 1, tuple.y());
        }

        @Override
        public <T extends UnaryTuple2<Short>> T read(MemorySegment source, long index, Builder<T, Short> builder) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return builder.build(
                    source.getAtIndex(layout, at    ),
                    source.getAtIndex(layout, at + 1)
            );
        }
    }

    final class I32 implements Tuple2FFM<Integer> {

        private static ValueLayout.OfInt layout() {
            return JAVA_INT;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public MemorySegment write(Arena arena, UnaryTuple2<Integer> tuple) {
            final var segment = arena.allocate(byteSize(), layout().byteAlignment());
            write(segment, 0, tuple);
            return segment;
        }

        @Override
        public void write(MemorySegment destination, long index, UnaryTuple2<Integer> tuple) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            destination.setAtIndex(layout, at    , tuple.x());
            destination.setAtIndex(layout, at + 1, tuple.y());
        }

        @Override
        public <T extends UnaryTuple2<Integer>> T read(MemorySegment source, long index, Builder<T, Integer> builder) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return builder.build(
                    source.getAtIndex(layout, at    ),
                    source.getAtIndex(layout, at + 1)
            );
        }
    }

    final class I64 implements Tuple2FFM<Long> {

        private static ValueLayout.OfLong layout() {
            return JAVA_LONG;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public MemorySegment write(Arena arena, UnaryTuple2<Long> tuple) {
            final var segment = arena.allocate(byteSize(), layout().byteAlignment());
            write(segment, 0, tuple);
            return segment;
        }

        @Override
        public void write(MemorySegment destination, long index, UnaryTuple2<Long> tuple) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            destination.setAtIndex(layout, at    , tuple.x());
            destination.setAtIndex(layout, at + 1, tuple.y());
        }

        @Override
        public <T extends UnaryTuple2<Long>> T read(MemorySegment source, long index, Builder<T, Long> builder) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return builder.build(
                    source.getAtIndex(layout, at    ),
                    source.getAtIndex(layout, at + 1)
            );
        }
    }

    final class F32 implements Tuple2FFM<Float> {

        private static ValueLayout.OfFloat layout() {
            return JAVA_FLOAT;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public MemorySegment write(Arena arena, UnaryTuple2<Float> tuple) {
            final var segment = arena.allocate(byteSize(), layout().byteAlignment());
            write(segment, 0, tuple);
            return segment;
        }

        @Override
        public void write(MemorySegment destination, long index, UnaryTuple2<Float> tuple) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            destination.setAtIndex(layout, at    , tuple.x());
            destination.setAtIndex(layout, at + 1, tuple.y());
        }

        @Override
        public <T extends UnaryTuple2<Float>> T read(MemorySegment source, long index, Builder<T, Float> builder) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return builder.build(
                    source.getAtIndex(layout, at    ),
                    source.getAtIndex(layout, at + 1)
            );
        }
    }

    final class F64 implements Tuple2FFM<Double> {

        private static ValueLayout.OfDouble layout() {
            return JAVA_DOUBLE;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public MemorySegment write(Arena arena, UnaryTuple2<Double> tuple) {
            final var segment = arena.allocate(byteSize(), layout().byteAlignment());
            write(segment, 0, tuple);
            return segment;
        }

        @Override
        public void write(MemorySegment destination, long index, UnaryTuple2<Double> tuple) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            destination.setAtIndex(layout, at    , tuple.x());
            destination.setAtIndex(layout, at + 1, tuple.y());
        }

        @Override
        public <T extends UnaryTuple2<Double>> T read(MemorySegment source, long index, Builder<T, Double> builder) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return builder.build(
                    source.getAtIndex(layout, at    ),
                    source.getAtIndex(layout, at + 1)
            );
        }
    }
}
