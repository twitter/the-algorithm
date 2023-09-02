scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "decider/src/main/scala",
        "finagle/finagle-memcached",
        "hermit/hermit-core/src/main/scala/com/twitter/hermit/store/common",
        "relevance-platform/src/main/scala/com/twitter/relevance_platform/common/injection",
        "src/scala/com/twitter/simclusters_v2/common",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)
