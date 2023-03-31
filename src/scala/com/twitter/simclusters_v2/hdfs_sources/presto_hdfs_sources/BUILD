scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":data_sources",
        "3rdparty/src/jvm/com/twitter/scalding:core",
        "src/scala/com/twitter/scalding_internal/dalv2",
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/scalding_internal/source/lzo_scrooge",
        "src/scala/com/twitter/simclusters_v2/common",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
        "src/thrift/com/twitter/wtf/entity_real_graph:entity_real_graph-thrift-scala",
    ],
)

scala_library(
    name = "data_sources",
    sources = [],
    description = "DAL datasets we wish to expose externally",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":reverse_index_semantic_core_per_language_simclusters_embeddings_presto-scala",
        ":semantic_core_per_language_simclusters_embeddings_presto-scala",
        "src/scala/com/twitter/simclusters_v2/common",
    ],
)

create_datasets(
    base_name = "reverse_index_semantic_core_per_language_simclusters_embeddings_presto",
    java_schema = "com.twitter.simclusters_v2.thriftjava.InternalIdEmbeddingWithId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.InternalIdEmbeddingWithId",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "semantic_core_per_language_simclusters_embeddings_presto",
    java_schema = "com.twitter.simclusters_v2.thriftjava.SimClustersEmbeddingWithId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingWithId",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)
