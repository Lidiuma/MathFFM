package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple4;
import org.lidiuma.math.ffm.tuples.Tuple4FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_SHORT;

public final class Tuple4I16Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec4 vec = new Vec4((short) 23, (short) 41, (short) 76, (short) 98);
    private final MemorySegment array = arena.allocate(Tuple4FFM.I16.byteSize() * 10);

    private record Vec4(Short x, Short y, Short z, Short w) implements UnaryTuple4<Short> {}

    @Test
    public void intSize() {
        Assertions.assertEquals(4, Tuple4FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple4FFM.COMPONENT_COUNT * JAVA_SHORT.byteSize(), Tuple4FFM.I16.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple4FFM.I16.write(arena, vec);
        final Vec4 original = Tuple4FFM.I16.read(segment, Vec4::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec4 iVec = new Vec4((short) -index, (short) (-index * 3), (short) (index * 2), (short) index);
        Tuple4FFM.I16.write(array, index, iVec);
        final Vec4 original = Tuple4FFM.I16.read(array, index, Vec4::new);
        Assertions.assertEquals(iVec, original);
    }
}
