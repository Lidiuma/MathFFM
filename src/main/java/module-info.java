import org.jspecify.annotations.NullMarked;

@NullMarked
module lidiuma.math.ffm {
    requires lidiuma.math.api;
    requires org.jspecify;
    exports org.lidiuma.math.ffm.tuples;
    exports org.lidiuma.math.ffm.matrix;
}