package org.lidiuma.math.ffm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lidiuma.math.api.tuple.UnaryTuple2;
import org.lidiuma.math.ffm.tuples.Tuple2FFM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import static java.lang.foreign.ValueLayout.JAVA_DOUBLE;

public final class Tuple2F64Test {

    private final Arena arena = Arena.ofAuto();
    private final Vec2 vec = new Vec2(23d, 41d);
    private final MemorySegment array = arena.allocate(Tuple2FFM.F64.byteSize() * 10);

    private record Vec2(Double x, Double y) implements UnaryTuple2<Double> {}

    @Test
    public void byteSize() {
        Assertions.assertEquals(2, Tuple2FFM.COMPONENT_COUNT);
        Assertions.assertEquals(Tuple2FFM.COMPONENT_COUNT * JAVA_DOUBLE.byteSize(), Tuple2FFM.F64.byteSize());
    }

    @Test
    public void readWrite() {
        final var   segment = Tuple2FFM.F64.write(arena, vec);
        final Vec2 original = Tuple2FFM.F64.read(segment, Vec2::new);
        Assertions.assertEquals(vec, original);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void readWriteIndexed(int index) {
        final Vec2 iVec = new Vec2((double) -index, -index * 3d);
        Tuple2FFM.F64.write(array, index, iVec);
        final Vec2 original = Tuple2FFM.F64.read(array, index, Vec2::new);
        Assertions.assertEquals(iVec, original);
    }
}
