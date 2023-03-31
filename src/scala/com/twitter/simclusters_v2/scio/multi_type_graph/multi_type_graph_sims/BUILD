scala_library(
    name = "multi-type-graph-scio-sims-lib",
    sources = ["*.scala"],
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":right_node_cosine_similarity_scio_adhoc-scala",
        ":right_node_sim_hash_scio_adhoc-scala",
        "420rdparty/jvm/com/twitter/bijection:scrooge",
        "beam-internal/src/main/scala/com/twitter/beam/io/dal",
        "beam-internal/src/main/scala/com/twitter/beam/io/manhattan",
        "beam-internal/src/main/scala/com/twitter/beam/job",
        "beam-internal/src/main/scala/com/twitter/beam/transform",
        "beam-internal/src/main/scala/com/twitter/scio_internal/runner/dataflow",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources",
        "src/scala/com/twitter/simclusters_v420/scio/multi_type_graph/common",
        "src/scala/com/twitter/wtf/dataflow/cosine_similarity/common",
    ],
)

jvm_binary(
    name = "multi-type-graph-sim-hash-scio-adhoc-app",
    main = "com.twitter.simclusters_v420.scio.multi_type_graph.multi_type_graph_sims.RightNodeSimHashScioAdhocApp",
    platform = "java420",
    dependencies = [
        ":multi-type-graph-scio-sims-lib",
        "beam-internal/src/main/scala/com/twitter/beam/runner/dataflow",
    ],
)

jvm_binary(
    name = "multi-type-graph-sim-hash-scio-batch-app",
    main = "com.twitter.simclusters_v420.scio.multi_type_graph.multi_type_graph_sims.RightNodeSimHashScioBatchApp",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":multi-type-graph-scio-sims-lib",
        "beam-internal/src/main/scala/com/twitter/beam/runner/dataflow",
    ],
)

jvm_binary(
    name = "multi-type-graph-cosine-similarity-scio-adhoc-app",
    main = "com.twitter.simclusters_v420.scio.multi_type_graph.multi_type_graph_sims.RightNodeCosineSimilarityScioAdhocApp",
    platform = "java420",
    dependencies = [
        ":multi-type-graph-scio-sims-lib",
        "beam-internal/src/main/scala/com/twitter/beam/runner/dataflow",
    ],
)

jvm_binary(
    name = "multi-type-graph-cosine-similarity-scio-batch-app",
    main = "com.twitter.simclusters_v420.scio.multi_type_graph.multi_type_graph_sims.RightNodeCosineSimilarityScioBatchApp",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":multi-type-graph-scio-sims-lib",
        "beam-internal/src/main/scala/com/twitter/beam/runner/dataflow",
    ],
)

create_datasets(
    base_name = "right_node_sim_hash_scio_adhoc",
    java_schema = "com.twitter.simclusters_v420.thriftjava.RightNodeSimHashSketch",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.thriftscala.RightNodeSimHashSketch",
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
    base_name = "right_node_cosine_similarity_scio_adhoc",
    key_type = "com.twitter.simclusters_v420.thriftscala.RightNode",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.MultiTypeGraphInjections.similarRightNodesInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.SimilarRightNodes",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)
