scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finatra/inject/inject-thrift-client",
        "frigate/frigate-common/src/main/scala/com/twitter/frigate/common/store/strato",
        "hermit/hermit-core/src/main/scala/com/twitter/hermit/store/common",
        "relevance-platform/src/main/scala/com/twitter/relevance_platform/common/readablestore",
        "representation-manager/client/src/main/scala/com/twitter/representation_manager/config",
        "representation-manager/server/src/main/thrift:thrift-scala",
        "src/scala/com/twitter/simclusters_v2/common",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
        "stitch/stitch-storehaus",
        "strato/src/main/scala/com/twitter/strato/client",
    ],
)
