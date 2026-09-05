package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple4;
import org.lidiuma.math.ffm.tuples.Tuple4FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_DOUBLE;

public final class Tuple4F64Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec4 vec = new Vec4(23d, 41d, 76d, 98d);
    private final MemorySegment array = arena.allocate(Tuple4FFM.F64.byteSize() * 10);

    private record Vec4(Double x, Double y, Double z, Double w) implements UnaryTuple4<Double> {}

    @Test
    public void intSize() {
        Assertions.assertEquals(4, Tuple4FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple4FFM.COMPONENT_COUNT * JAVA_DOUBLE.byteSize(), Tuple4FFM.F64.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple4FFM.F64.write(arena, vec);
        final Vec4 original = Tuple4FFM.F64.read(segment, Vec4::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec4 iVec = new Vec4((double) -index, -index * 3d, index * 2d, (double) index);
        Tuple4FFM.F64.write(array, index, iVec);
        final Vec4 original = Tuple4FFM.F64.read(array, index, Vec4::new);
        Assertions.assertEquals(iVec, original);
    }
}
