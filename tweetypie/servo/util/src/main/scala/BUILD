EXCEPTION_SOURCES = [
    "com/twitter/servo/util/Effect.scala",
    "com/twitter/servo/util/ExceptionCounter.scala",
    "com/twitter/servo/util/Gate.scala",
    "com/twitter/servo/util/ThrowableHelper.scala",
    "com/twitter/servo/util/package.scala",
]

scala_library(
    sources = ["**/*.scala"] + exclude_globs(EXCEPTION_SOURCES),
    platform = "java8",
    provides = scala_artifact(
        org = "com.twitter",
        name = "servo-util",
        repo = artifactory,
    ),
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        ":exception",
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/google/inject:guice",
        "finagle/finagle-core/src/main",
        "finagle/finagle-mux/src/main/scala",
        "scrooge/scrooge-core",
        "scrooge/scrooge-serializer",
        "util-internal/scribe",
        "util/util-logging/src/main/scala/com/twitter/logging",
        "util/util-stats/src/main/scala",
    ],
    exports = [
        ":exception",
        "util/util-logging/src/main/scala/com/twitter/logging",
    ],
)

scala_library(
    name = "exception",
    sources = EXCEPTION_SOURCES,
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    provides = scala_artifact(
        org = "com.twitter",
        name = "servo-util-exception",
        repo = artifactory,
    ),
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "util/util-core:util-core-util",
        "util/util-stats/src/main/scala",
    ],
)
