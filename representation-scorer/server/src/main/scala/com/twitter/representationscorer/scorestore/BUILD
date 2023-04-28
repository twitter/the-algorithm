scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "frigate/frigate-common/src/main/scala/com/twitter/frigate/common/util",
        "hermit/hermit-core/src/main/scala/com/twitter/hermit/store/common",
        "relevance-platform/src/main/scala/com/twitter/relevance_platform/common/injection",
        "representation-manager/client/src/main/scala/com/twitter/representation_manager",
        "representation-manager/client/src/main/scala/com/twitter/representation_manager/config",
        "representation-scorer/server/src/main/scala/com/twitter/representationscorer/common",
        "src/scala/com/twitter/simclusters_v2/score",
        "src/scala/com/twitter/topic_recos/common",
        "src/scala/com/twitter/topic_recos/stores",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
        "src/thrift/com/twitter/topic_recos:topic_recos-thrift-scala",
        "stitch/stitch-storehaus",
    ],
)
