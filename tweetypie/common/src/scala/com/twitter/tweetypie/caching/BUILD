scala_library(
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-memcached/src/main/scala",
        "scrooge/scrooge-serializer",
        "stitch/stitch-core",
        "util/util-core",
        "util/util-logging",
        # CachedValue struct
        "tweetypie/servo/repo/src/main/thrift:thrift-scala",
        "util/util-slf4j-api/src/main/scala/com/twitter/util/logging",
    ],
)
