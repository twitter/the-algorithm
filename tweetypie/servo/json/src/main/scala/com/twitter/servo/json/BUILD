scala_library(
    sources = ["*.scala"],
    platform = "java8",
    provides = scala_artifact(
        org = "com.twitter",
        name = "servo-json",
        repo = artifactory,
    ),
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "//:scala-reflect",
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-core",
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-databind",
        "3rdparty/jvm/com/googlecode/java-diff-utils:diffutils",
        "3rdparty/jvm/org/apache/thrift:libthrift",
        "scrooge/scrooge-core",
        "scrooge/scrooge-serializer",
        "util/util-codec/src/main/scala",
    ],
)
