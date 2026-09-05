package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple4;
import org.lidiuma.math.ffm.tuples.Tuple4FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

public final class Tuple4I64Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec4 vec = new Vec4(23L, 41L, 76L, 98L);
    private final MemorySegment array = arena.allocate(Tuple4FFM.I64.byteSize() * 10);

    private record Vec4(Long x, Long y, Long z, Long w) implements UnaryTuple4<Long> {}

    @Test
    public void intSize() {
        Assertions.assertEquals(4, Tuple4FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple4FFM.COMPONENT_COUNT * JAVA_LONG.byteSize(), Tuple4FFM.I64.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple4FFM.I64.write(arena, vec);
        final Vec4 original = Tuple4FFM.I64.read(segment, Vec4::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec4 iVec = new Vec4((long) -index, -index * 3L, index * 2L, (long) index);
        Tuple4FFM.I64.write(array, index, iVec);
        final Vec4 original = Tuple4FFM.I64.read(array, index, Vec4::new);
        Assertions.assertEquals(iVec, original);
    }
}
