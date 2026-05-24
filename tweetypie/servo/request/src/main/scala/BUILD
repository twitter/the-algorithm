scala_library(
    sources = ["**/*.scala"],
    platform = "java8",
    provides = scala_artifact(
        org = "com.twitter",
        name = "servo-request",
        repo = artifactory,
    ),
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-core/src/main",
        "tweetypie/servo/util",
        "twitter-config/yaml",
        "util/util-stats/src/main/scala",
    ],
    exports = [
        "tweetypie/servo/util",
    ],
)
