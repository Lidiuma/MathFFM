package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple3;
import org.lidiuma.math.ffm.tuples.Tuple3FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

public final class Tuple3I64Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec3 vec = new Vec3(23L, 41L, 76L);
    private final MemorySegment array = arena.allocate(Tuple3FFM.I64.byteSize() * 10);

    private record Vec3(Long x, Long y, Long z) implements UnaryTuple3<Long> {}

    @Test
    public void intSize() {
        Assertions.assertEquals(3, Tuple3FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple3FFM.COMPONENT_COUNT * JAVA_LONG.byteSize(), Tuple3FFM.I64.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple3FFM.I64.write(arena, vec);
        final Vec3 original = Tuple3FFM.I64.read(segment, Vec3::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec3 iVec = new Vec3((long) -index, -index * 3L, index * 2L);
        Tuple3FFM.I64.write(array, index, iVec);
        final Vec3 original = Tuple3FFM.I64.read(array, index, Vec3::new);
        Assertions.assertEquals(iVec, original);
    }
}
