package org.lidiuma.math.ffm.tuples;

import org.lidiuma.math.api.tuple.UnaryTuple1;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import static java.lang.foreign.ValueLayout.*;

public interface Tuple1FFM<N> {

    long COMPONENT_COUNT = 1;
    I8  I8  = new I8();
    I16 I16 = new I16();
    I32 I32 = new I32();
    I64 I64 = new I64();
    F32 F32 = new F32();
    F64 F64 = new F64();

    long byteSize();

    MemorySegment write(Arena arena, UnaryTuple1<N> tuple);

    void write(MemorySegment destination, long index, UnaryTuple1<N> tuple);

    default void write(MemorySegment destination, UnaryTuple1<N> tuple) {
        write(destination, 0, tuple);
    }

    <T extends UnaryTuple1<N>> T read(MemorySegment source, long index, Factory<T, N> factory);

    default <T extends UnaryTuple1<N>> T read(MemorySegment source, Factory<T, N> factory) {
        return read(source, 0, factory);
    }

    @FunctionalInterface
    interface Factory<T extends UnaryTuple1<N>, N> {
        T create(N x);
    }

    final class I8 implements Tuple1FFM<Byte> {

        private I8() {}

        private static ValueLayout.OfByte layout() {
            return JAVA_BYTE;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public MemorySegment write(Arena arena, UnaryTuple1<Byte> tuple) {
            final var segment = arena.allocate(byteSize(), layout().byteAlignment());
            write(segment, 0, tuple);
            return segment;
        }

        @Override
        public void write(MemorySegment destination, long index, UnaryTuple1<Byte> tuple) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            destination.setAtIndex(layout, at, tuple.x());
        }

        @Override
        public <T extends UnaryTuple1<Byte>> T read(MemorySegment source, long index, Factory<T, Byte> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(source.getAtIndex(layout, at));
        }
    }

    final class I16 implements Tuple1FFM<Short> {

        private I16() {}

        private static ValueLayout.OfShort layout() {
            return JAVA_SHORT;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public MemorySegment write(Arena arena, UnaryTuple1<Short> tuple) {
            final var segment = arena.allocate(byteSize(), layout().byteAlignment());
            write(segment, 0, tuple);
            return segment;
        }

        @Override
        public void write(MemorySegment destination, long index, UnaryTuple1<Short> tuple) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            destination.setAtIndex(layout, at, tuple.x());
        }

        @Override
        public <T extends UnaryTuple1<Short>> T read(MemorySegment source, long index, Factory<T, Short> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(source.getAtIndex(layout, at));
        }
    }

    final class I32 implements Tuple1FFM<Integer> {

        private I32() {}

        private static ValueLayout.OfInt layout() {
            return JAVA_INT;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public MemorySegment write(Arena arena, UnaryTuple1<Integer> tuple) {
            final var segment = arena.allocate(byteSize(), layout().byteAlignment());
            write(segment, 0, tuple);
            return segment;
        }

        @Override
        public void write(MemorySegment destination, long index, UnaryTuple1<Integer> tuple) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            destination.setAtIndex(layout, at    , tuple.x());
        }

        @Override
        public <T extends UnaryTuple1<Integer>> T read(MemorySegment source, long index, Factory<T, Integer> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(source.getAtIndex(layout, at));
        }
    }

    final class I64 implements Tuple1FFM<Long> {

        private I64() {}

        private static ValueLayout.OfLong layout() {
            return JAVA_LONG;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public MemorySegment write(Arena arena, UnaryTuple1<Long> tuple) {
            final var segment = arena.allocate(byteSize(), layout().byteAlignment());
            write(segment, 0, tuple);
            return segment;
        }

        @Override
        public void write(MemorySegment destination, long index, UnaryTuple1<Long> tuple) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            destination.setAtIndex(layout, at, tuple.x());
        }

        @Override
        public <T extends UnaryTuple1<Long>> T read(MemorySegment source, long index, Factory<T, Long> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(source.getAtIndex(layout, at));
        }
    }

    final class F32 implements Tuple1FFM<Float> {

        private F32() {}

        private static ValueLayout.OfFloat layout() {
            return JAVA_FLOAT;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public MemorySegment write(Arena arena, UnaryTuple1<Float> tuple) {
            final var segment = arena.allocate(byteSize(), layout().byteAlignment());
            write(segment, 0, tuple);
            return segment;
        }

        @Override
        public void write(MemorySegment destination, long index, UnaryTuple1<Float> tuple) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            destination.setAtIndex(layout, at, tuple.x());
        }

        @Override
        public <T extends UnaryTuple1<Float>> T read(MemorySegment source, long index, Factory<T, Float> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(source.getAtIndex(layout, at));
        }
    }

    final class F64 implements Tuple1FFM<Double> {

        private F64() {}

        private static ValueLayout.OfDouble layout() {
            return JAVA_DOUBLE;
        }

        @Override
        public long byteSize() {
            return COMPONENT_COUNT * layout().byteSize();
        }

        @Override
        public MemorySegment write(Arena arena, UnaryTuple1<Double> tuple) {
            final var segment = arena.allocate(byteSize(), layout().byteAlignment());
            write(segment, 0, tuple);
            return segment;
        }

        @Override
        public void write(MemorySegment destination, long index, UnaryTuple1<Double> tuple) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            destination.setAtIndex(layout, at, tuple.x());
        }

        @Override
        public <T extends UnaryTuple1<Double>> T read(MemorySegment source, long index, Factory<T, Double> factory) {
            final var layout = layout();
            final long at = index * COMPONENT_COUNT;
            return factory.create(source.getAtIndex(layout, at));
        }
    }
}
