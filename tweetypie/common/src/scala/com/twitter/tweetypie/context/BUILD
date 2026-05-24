scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    provides = scala_artifact(
        org = "com.twitter.tweetypie",
        name = "context",
        repo = artifactory,
    ),
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-core/src/main",
        "graphql/common/src/main/scala/com/twitter/graphql/common/core",
        "src/thrift/com/twitter/context:twitter-context-scala",
        "twitter-context/src/main/scala",
        "util/util-core:scala",
    ],
)
