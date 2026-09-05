package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple4;
import org.lidiuma.math.ffm.tuples.Tuple4FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_INT;

public final class Tuple4I32Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec4 vec = new Vec4(23, 41, 76, 98);
    private final MemorySegment array = arena.allocate(Tuple4FFM.I32.byteSize() * 10);

    private record Vec4(Integer x, Integer y, Integer z, Integer w) implements UnaryTuple4<Integer> {}

    @Test
    public void intSize() {
        Assertions.assertEquals(4, Tuple4FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple4FFM.COMPONENT_COUNT * JAVA_INT.byteSize(), Tuple4FFM.I32.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple4FFM.I32.write(arena, vec);
        final Vec4 original = Tuple4FFM.I32.read(segment, Vec4::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec4 iVec = new Vec4(-index, -index * 3, index * 2, index);
        Tuple4FFM.I32.write(array, index, iVec);
        final Vec4 original = Tuple4FFM.I32.read(array, index, Vec4::new);
        Assertions.assertEquals(iVec, original);
    }
}
