package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple4;
import org.lidiuma.math.ffm.tuples.Tuple4FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_FLOAT;

public final class Tuple4F32Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec4 vec = new Vec4(23f, 41f, 76f, 98f);
    private final MemorySegment array = arena.allocate(Tuple4FFM.F32.byteSize() * 10);

    private record Vec4(Float x, Float y, Float z, Float w) implements UnaryTuple4<Float> {}

    @Test
    public void intSize() {
        Assertions.assertEquals(4, Tuple4FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple4FFM.COMPONENT_COUNT * JAVA_FLOAT.byteSize(), Tuple4FFM.F32.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple4FFM.F32.write(arena, vec);
        final Vec4 original = Tuple4FFM.F32.read(segment, Vec4::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec4 iVec = new Vec4((float) -index, -index * 3f, index * 2f, (float) index);
        Tuple4FFM.F32.write(array, index, iVec);
        final Vec4 original = Tuple4FFM.F32.read(array, index, Vec4::new);
        Assertions.assertEquals(iVec, original);
    }
}
