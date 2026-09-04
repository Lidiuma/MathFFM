package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple1;
import org.lidiuma.math.ffm.tuples.Tuple1FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_SHORT;

public final class Tuple1I16Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec1 vec = new Vec1((short) 23);
    private final MemorySegment array = arena.allocate(Tuple1FFM.I16.byteSize() * 10);

    private record Vec1(Short x) implements UnaryTuple1<Short> {}

    @Test
    public void byteSize() {
        Assertions.assertEquals(1, Tuple1FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple1FFM.COMPONENT_COUNT * JAVA_SHORT.byteSize(), Tuple1FFM.I16.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple1FFM.I16.write(arena, vec);
        final Vec1 original = Tuple1FFM.I16.read(segment, Vec1::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec1 iVec = new Vec1((short) -index);
        Tuple1FFM.I16.write(array, index, iVec);
        final Vec1 original = Tuple1FFM.I16.read(array, index, Vec1::new);
        Assertions.assertEquals(iVec, original);
    }
}
