package org.lidiuma.math.ffm;

import rife.bld.BaseProject;
import rife.bld.dependencies.Scope;

import java.util.List;

import static rife.bld.dependencies.Repository.MAVEN_CENTRAL;

public class MathFFMBuild extends BaseProject {
    public MathFFMBuild() {
        pkg = "org.lidiuma.math.ffm";
        name = "MathFFM";
        mainClass = "org.lidiuma.math.ffm.MathFFM";
        version = version(0,1,0);
        javaRelease = 25;

        downloadSources = true;
        repositories = List.of(MAVEN_CENTRAL);

        testOperation().mainClass("org.lidiuma.math.ffm.MathFFMTest");

        scope(Scope.compile).include(module("org.lidiuma.math", "math-api", version(1, 0, 0, "rc3")));
    }

    static void main(String[] args) {
        new MathFFMBuild().start(args);
    }
}
