package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple2;
import org.lidiuma.math.ffm.tuples.Tuple2FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_INT;

public final class Tuple2I32Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec2 vec = new Vec2(23, 41);
    private final MemorySegment array = arena.allocate(Tuple2FFM.I32.byteSize() * 10);

    private record Vec2(Integer x, Integer y) implements UnaryTuple2<Integer> {}

    @Test
    public void intSize() {
        Assertions.assertEquals(2, Tuple2FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple2FFM.COMPONENT_COUNT * JAVA_INT.byteSize(), Tuple2FFM.I32.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple2FFM.I32.write(arena, vec);
        final Vec2 original = Tuple2FFM.I32.read(segment, Vec2::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec2 iVec = new Vec2(-index, -index * 3);
        Tuple2FFM.I32.write(array, index, iVec);
        final Vec2 original = Tuple2FFM.I32.read(array, index, Vec2::new);
        Assertions.assertEquals(iVec, original);
    }
}
