scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/squareup/okhttp:okhttp3",
        "finagle/finagle-mux/src/main/scala",
        "src/thrift/com/twitter/gizmoduck:user-thrift-scala",
        "src/thrift/com/twitter/search/common:constants-scala",
        "src/thrift/com/twitter/tweetypie:tweet-scala",
        "stitch/stitch-core",
        "visibility/lib/src/main/scala/com/twitter/visibility/models",
        "visibility/lib/src/main/scala/com/twitter/visibility/util",
    ],
)
