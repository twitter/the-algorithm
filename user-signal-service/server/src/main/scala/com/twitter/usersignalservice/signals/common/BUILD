scala_library(
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "hermit/hermit-core/src/main/scala/com/twitter/hermit/predicate/socialgraph",
        "src/scala/com/twitter/simclusters_v2/common",
        "src/scala/com/twitter/twistly/common",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
        "src/thrift/com/twitter/socialgraph:thrift-scala",
        "user-signal-service/thrift/src/main/thrift:thrift-scala",
        "util/util-core:util-core-util",
        "util/util-core/src/main/java/com/twitter/util",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)
