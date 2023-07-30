scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = [
        "bazel-compatible",
        "logging_impl_check_whitelisted_target",
    ],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "3rdparty/jvm/com/google/guava",
        "finagle/finagle-memcached/src/main/scala",
        "src/thrift/com/X/servo:servo-exception-java",
        "tweetypie/server/src/main/scala/com/X/tweetypie/serverutil",
        "util/util-stats/src/main/scala/com/X/finagle/stats",
    ],
)
