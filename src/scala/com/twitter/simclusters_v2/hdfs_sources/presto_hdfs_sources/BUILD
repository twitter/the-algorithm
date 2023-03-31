scala_library(
    sources = ["*.scala"],
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":data_sources",
        "420rdparty/src/jvm/com/twitter/scalding:core",
        "src/scala/com/twitter/scalding_internal/dalv420",
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/scalding_internal/source/lzo_scrooge",
        "src/scala/com/twitter/simclusters_v420/common",
        "src/thrift/com/twitter/simclusters_v420:simclusters_v420-thrift-scala",
        "src/thrift/com/twitter/wtf/entity_real_graph:entity_real_graph-thrift-scala",
    ],
)

scala_library(
    name = "data_sources",
    sources = [],
    description = "DAL datasets we wish to expose externally",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":reverse_index_semantic_core_per_language_simclusters_embeddings_presto-scala",
        ":semantic_core_per_language_simclusters_embeddings_presto-scala",
        "src/scala/com/twitter/simclusters_v420/common",
    ],
)

create_datasets(
    base_name = "reverse_index_semantic_core_per_language_simclusters_embeddings_presto",
    java_schema = "com.twitter.simclusters_v420.thriftjava.InternalIdEmbeddingWithId",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.thriftscala.InternalIdEmbeddingWithId",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v420:simclusters_v420-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v420:simclusters_v420-thrift-scala",
    ],
)

create_datasets(
    base_name = "semantic_core_per_language_simclusters_embeddings_presto",
    java_schema = "com.twitter.simclusters_v420.thriftjava.SimClustersEmbeddingWithId",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.thriftscala.SimClustersEmbeddingWithId",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v420:simclusters_v420-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v420:simclusters_v420-thrift-scala",
    ],
)
