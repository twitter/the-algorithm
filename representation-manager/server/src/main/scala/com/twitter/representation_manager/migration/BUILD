scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "content-recommender/server/src/main/scala/com/twitter/contentrecommender:representation-manager-deps",
        "frigate/frigate-common/src/main/scala/com/twitter/frigate/common/store/strato",
        "frigate/frigate-common/src/main/scala/com/twitter/frigate/common/util",
        "hermit/hermit-core/src/main/scala/com/twitter/hermit/store/common",
        "relevance-platform/src/main/scala/com/twitter/relevance_platform/common/injection",
        "relevance-platform/src/main/scala/com/twitter/relevance_platform/common/readablestore",
        "representation-manager/server/src/main/scala/com/twitter/representation_manager/common",
        "representation-manager/server/src/main/scala/com/twitter/representation_manager/store",
        "src/scala/com/twitter/ml/api/embedding",
        "src/scala/com/twitter/simclusters_v2/common",
        "src/scala/com/twitter/simclusters_v2/score",
        "src/scala/com/twitter/simclusters_v2/summingbird/stores",
        "src/scala/com/twitter/storehaus_internal/manhattan",
        "src/scala/com/twitter/storehaus_internal/util",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
        "src/thrift/com/twitter/socialgraph:thrift-scala",
        "storage/clients/manhattan/client/src/main/scala",
        "tweetypie/src/scala/com/twitter/tweetypie/util",
    ],
)
