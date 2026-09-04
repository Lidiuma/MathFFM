package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple3;
import org.lidiuma.math.ffm.tuples.Tuple3FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_FLOAT;

public final class Tuple3F32Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec3 vec = new Vec3(23f, 41f, 76f);
    private final MemorySegment array = arena.allocate(Tuple3FFM.F32.byteSize() * 10);

    private record Vec3(Float x, Float y, Float z) implements UnaryTuple3<Float> {}

    @Test
    public void intSize() {
        Assertions.assertEquals(3, Tuple3FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple3FFM.COMPONENT_COUNT * JAVA_FLOAT.byteSize(), Tuple3FFM.F32.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple3FFM.F32.write(arena, vec);
        final Vec3 original = Tuple3FFM.F32.read(segment, Vec3::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec3 iVec = new Vec3((float) -index, -index * 3f, index * 2f);
        Tuple3FFM.F32.write(array, index, iVec);
        final Vec3 original = Tuple3FFM.F32.read(array, index, Vec3::new);
        Assertions.assertEquals(iVec, original);
    }
}
