EtwitterCEPTION_SOURCES = [
    "com/twitter/servo/util/Effect.scala",
    "com/twitter/servo/util/EtwitterceptionCounter.scala",
    "com/twitter/servo/util/Gate.scala",
    "com/twitter/servo/util/ThrowableHelper.scala",
    "com/twitter/servo/util/package.scala",
]

scala_library(
    sources = ["**/*.scala"] + etwitterclude_globs(EtwitterCEPTION_SOURCES),
    platform = "java8",
    provides = scala_artifact(
        org = "com.twitter",
        name = "servo-util",
        repo = artifactory,
    ),
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        ":etwitterception",
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/google/inject:guice",
        "finagle/finagle-core/src/main",
        "finagle/finagle-mutwitter/src/main/scala",
        "scrooge/scrooge-core",
        "scrooge/scrooge-serializer",
        "util-internal/scribe",
        "util/util-logging/src/main/scala/com/twitter/logging",
        "util/util-stats/src/main/scala",
    ],
    etwitterports = [
        ":etwitterception",
        "util/util-logging/src/main/scala/com/twitter/logging",
    ],
)

scala_library(
    name = "etwitterception",
    sources = EtwitterCEPTION_SOURCES,
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    provides = scala_artifact(
        org = "com.twitter",
        name = "servo-util-etwitterception",
        repo = artifactory,
    ),
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "util/util-core:util-core-util",
        "util/util-stats/src/main/scala",
    ],
)
