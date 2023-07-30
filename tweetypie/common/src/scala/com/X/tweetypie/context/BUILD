scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    provides = scala_artifact(
        org = "com.X.tweetypie",
        name = "context",
        repo = artifactory,
    ),
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-core/src/main",
        "graphql/common/src/main/scala/com/X/graphql/common/core",
        "src/thrift/com/X/context:X-context-scala",
        "X-context/src/main/scala",
        "util/util-core:scala",
    ],
)
