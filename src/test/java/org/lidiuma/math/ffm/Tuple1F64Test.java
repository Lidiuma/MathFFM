package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple1;
import org.lidiuma.math.ffm.tuples.Tuple1FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_DOUBLE;

public final class Tuple1F64Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec1 vec = new Vec1(23d);
    private final MemorySegment array = arena.allocate(Tuple1FFM.F64.byteSize() * 10);

    private record Vec1(Double x) implements UnaryTuple1<Double> {}

    @Test
    public void byteSize() {
        Assertions.assertEquals(1, Tuple1FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple1FFM.COMPONENT_COUNT * JAVA_DOUBLE.byteSize(), Tuple1FFM.F64.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple1FFM.F64.write(arena, vec);
        final Vec1 original = Tuple1FFM.F64.read(segment, Vec1::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec1 iVec = new Vec1((double) -index);
        Tuple1FFM.F64.write(array, index, iVec);
        final Vec1 original = Tuple1FFM.F64.read(array, index, Vec1::new);
        Assertions.assertEquals(iVec, original);
    }
}
