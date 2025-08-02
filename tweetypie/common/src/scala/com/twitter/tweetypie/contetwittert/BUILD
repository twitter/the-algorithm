scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    provides = scala_artifact(
        org = "com.twitter.tweetypie",
        name = "contetwittert",
        repo = artifactory,
    ),
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-core/src/main",
        "graphql/common/src/main/scala/com/twitter/graphql/common/core",
        "src/thrift/com/twitter/contetwittert:twitter-contetwittert-scala",
        "twitter-contetwittert/src/main/scala",
        "util/util-core:scala",
    ],
)
