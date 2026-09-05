package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple4;
import org.lidiuma.math.ffm.tuples.Tuple4FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_BYTE;

public final class Tuple4I8Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec4 vec = new Vec4((byte) 23, (byte) 41, (byte) 76, (byte) 98);
    private final MemorySegment array = arena.allocate(Tuple4FFM.I8.byteSize() * 10);

    private record Vec4(Byte x, Byte y, Byte z, Byte w) implements UnaryTuple4<Byte> {}

    @Test
    public void intSize() {
        Assertions.assertEquals(4, Tuple4FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple4FFM.COMPONENT_COUNT * JAVA_BYTE.byteSize(), Tuple4FFM.I8.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple4FFM.I8.write(arena, vec);
        final Vec4 original = Tuple4FFM.I8.read(segment, Vec4::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec4 iVec = new Vec4((byte) -index, (byte) (-index * 3), (byte) (index * 2), (byte) index);
        Tuple4FFM.I8.write(array, index, iVec);
        final Vec4 original = Tuple4FFM.I8.read(array, index, Vec4::new);
        Assertions.assertEquals(iVec, original);
    }
}
