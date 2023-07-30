scala_library(
    name = "multi-type-graph-scio-sims-lib",
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":right_node_cosine_similarity_scio_adhoc-scala",
        ":right_node_sim_hash_scio_adhoc-scala",
        "3rdparty/jvm/com/X/bijection:scrooge",
        "beam-internal/src/main/scala/com/X/beam/io/dal",
        "beam-internal/src/main/scala/com/X/beam/io/manhattan",
        "beam-internal/src/main/scala/com/X/beam/job",
        "beam-internal/src/main/scala/com/X/beam/transform",
        "beam-internal/src/main/scala/com/X/scio_internal/runner/dataflow",
        "src/scala/com/X/simclusters_v2/hdfs_sources",
        "src/scala/com/X/simclusters_v2/scio/multi_type_graph/common",
        "src/scala/com/X/wtf/dataflow/cosine_similarity/common",
    ],
)

jvm_binary(
    name = "multi-type-graph-sim-hash-scio-adhoc-app",
    main = "com.X.simclusters_v2.scio.multi_type_graph.multi_type_graph_sims.RightNodeSimHashScioAdhocApp",
    platform = "java8",
    dependencies = [
        ":multi-type-graph-scio-sims-lib",
        "beam-internal/src/main/scala/com/X/beam/runner/dataflow",
    ],
)

jvm_binary(
    name = "multi-type-graph-sim-hash-scio-batch-app",
    main = "com.X.simclusters_v2.scio.multi_type_graph.multi_type_graph_sims.RightNodeSimHashScioBatchApp",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":multi-type-graph-scio-sims-lib",
        "beam-internal/src/main/scala/com/X/beam/runner/dataflow",
    ],
)

jvm_binary(
    name = "multi-type-graph-cosine-similarity-scio-adhoc-app",
    main = "com.X.simclusters_v2.scio.multi_type_graph.multi_type_graph_sims.RightNodeCosineSimilarityScioAdhocApp",
    platform = "java8",
    dependencies = [
        ":multi-type-graph-scio-sims-lib",
        "beam-internal/src/main/scala/com/X/beam/runner/dataflow",
    ],
)

jvm_binary(
    name = "multi-type-graph-cosine-similarity-scio-batch-app",
    main = "com.X.simclusters_v2.scio.multi_type_graph.multi_type_graph_sims.RightNodeCosineSimilarityScioBatchApp",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":multi-type-graph-scio-sims-lib",
        "beam-internal/src/main/scala/com/X/beam/runner/dataflow",
    ],
)

create_datasets(
    base_name = "right_node_sim_hash_scio_adhoc",
    java_schema = "com.X.simclusters_v2.thriftjava.RightNodeSimHashSketch",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.X.simclusters_v2.thriftscala.RightNodeSimHashSketch",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/X/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/X/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "right_node_cosine_similarity_scio_adhoc",
    key_type = "com.X.simclusters_v2.thriftscala.RightNode",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.X.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.similarRightNodesInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.X.simclusters_v2.thriftscala.SimilarRightNodes",
    scala_dependencies = [
        "src/scala/com/X/scalding_internal/multiformat/format",
        "src/scala/com/X/simclusters_v2/hdfs_sources/injections",
    ],
)
