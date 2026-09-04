package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple1;
import org.lidiuma.math.ffm.tuples.Tuple1FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_FLOAT;

public final class Tuple1F32Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec1 vec = new Vec1(23f);
    private final MemorySegment array = arena.allocate(Tuple1FFM.F32.byteSize() * 10);

    private record Vec1(Float x) implements UnaryTuple1<Float> {}

    @Test
    public void byteSize() {
        Assertions.assertEquals(1, Tuple1FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple1FFM.COMPONENT_COUNT * JAVA_FLOAT.byteSize(), Tuple1FFM.F32.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple1FFM.F32.write(arena, vec);
        final Vec1 original = Tuple1FFM.F32.read(segment, Vec1::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec1 iVec = new Vec1((float) -index);
        Tuple1FFM.F32.write(array, index, iVec);
        final Vec1 original = Tuple1FFM.F32.read(array, index, Vec1::new);
        Assertions.assertEquals(iVec, original);
    }
}
