package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple1;
import org.lidiuma.math.ffm.tuples.Tuple1FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

public final class Tuple1I64Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec1 vec = new Vec1(23L);
    private final MemorySegment array = arena.allocate(Tuple1FFM.I64.byteSize() * 10);

    private record Vec1(Long x) implements UnaryTuple1<Long> {}

    @Test
    public void byteSize() {
        Assertions.assertEquals(1, Tuple1FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple1FFM.COMPONENT_COUNT * JAVA_LONG.byteSize(), Tuple1FFM.I64.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple1FFM.I64.write(arena, vec);
        final Vec1 original = Tuple1FFM.I64.read(segment, Vec1::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec1 iVec = new Vec1((long) -index);
        Tuple1FFM.I64.write(array, index, iVec);
        final Vec1 original = Tuple1FFM.I64.read(array, index, Vec1::new);
        Assertions.assertEquals(iVec, original);
    }
}
