package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple3;
import org.lidiuma.math.ffm.tuples.Tuple3FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_BYTE;

public final class Tuple3I8Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec3 vec = new Vec3((byte) 23, (byte) 41, (byte) 76);
    private final MemorySegment array = arena.allocate(Tuple3FFM.I8.byteSize() * 10);

    private record Vec3(Byte x, Byte y, Byte z) implements UnaryTuple3<Byte> {}

    @Test
    public void intSize() {
        Assertions.assertEquals(3, Tuple3FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple3FFM.COMPONENT_COUNT * JAVA_BYTE.byteSize(), Tuple3FFM.I8.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple3FFM.I8.write(arena, vec);
        final Vec3 original = Tuple3FFM.I8.read(segment, Vec3::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec3 iVec = new Vec3((byte) -index, (byte) (-index * 3), (byte) (index * 2));
        Tuple3FFM.I8.write(array, index, iVec);
        final Vec3 original = Tuple3FFM.I8.read(array, index, Vec3::new);
        Assertions.assertEquals(iVec, original);
    }
}
