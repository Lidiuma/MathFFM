package org.lidiuma.math.ffm;

import rife.bld.Project;
import rife.bld.dependencies.Scope;
import rife.bld.publish.PublishInfo;
import java.util.List;
import static org.lidiuma.math.ffm.PublishUtil.*;
import static rife.bld.dependencies.Repository.*;
import static rife.bld.dependencies.Scope.test;

public final class MathFFMBuild extends Project {

    public MathFFMBuild() {
        pkg = "org.lidiuma.math.ffm";
        name = "MathFFM";
        module = "lidiuma.math.ffm";
        version = snapshot(0,1,0);
        javaRelease = 25;

        downloadSources = true;
        repositories = List.of(MAVEN_CENTRAL);

        scope(Scope.compile)
                .include(module("org.jspecify", "jspecify", version(1, 0, 0)))
                .include(module("org.lidiuma.math", "math-api", version(1, 0, 0, "rc3")));

        final var jUnitVersion = version(6,1,3);
        scope(test)
                .include(module("org.junit.jupiter", "junit-jupiter", jUnitVersion))
                .include(module("org.junit.platform", "junit-platform-console-standalone", jUnitVersion));

        publishing();
        // By keeping the parameters names in the compiled classes,
        // I make it easier by implementors and people reading the API to understand clearly what the variables are.
        compileOperation().compileOptions().parameters();
    }

    private void publishing() {
        // The credentials for publishing.
        publishOperation().repositories(CENTRAL_SNAPSHOTS.withCredentials(
                property("sonatype.username"),
                property("sonatype.password")
        )).info(publishInfo());
    }

    private PublishInfo publishInfo() {
        final var projectInfo = ProjectInfo.github("Lidiuma", name());
        return new PublishInfo()
                .groupId("org.lidiuma.math")
                // I prefer the prefix since the final jar will be math-ffm.jar instead of ffm.jar.
                .artifactId("math-ffm")
                .version(version())
                .name("Math FFM")
                .description("Math marshalling & unmarshalling library")
                .url(projectInfo.url())
                .developer(XASMEDY_DEV)
                .license(APACHE_V2_LICENSE)
                .scm(projectInfo.scm())
                .signKey(property("sign.key"))
                .signPassphrase(property("sign.passphrase"));
    }

    @Override
    public void publish() throws Exception {
        patchDependencies(this);
        super.publish();
    }

    static void main(String[] args) {
        new MathFFMBuild().start(args);
    }
}
