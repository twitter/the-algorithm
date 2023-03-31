scala_library(
    name = "assemble-multi-type-graph-scio-lib",
    sources = [
        "*.scala",
    ],
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":full_multi_type_graph_scio-scala",
        ":top_k_right_nouns_mh_scio-scala",
        "beam-internal/src/main/scala/com/twitter/beam/io/dal",
        "beam-internal/src/main/scala/com/twitter/beam/io/manhattan",
        "beam-internal/src/main/scala/com/twitter/beam/job",
        "beam-internal/src/main/scala/com/twitter/beam/transform",
        "beam-internal/src/main/scala/com/twitter/scio_internal/runner/dataflow",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources",
        "src/scala/com/twitter/simclusters_v420/scalding/multi_type_graph/assemble_multi_type_graph",
        "src/scala/com/twitter/simclusters_v420/scio/common",
        "src/scala/com/twitter/simclusters_v420/scio/multi_type_graph/common",
    ],
)

jvm_binary(
    name = "assemble-multi-type-graph-scio-adhoc-app",
    main = "com.twitter.simclusters_v420.scio.multi_type_graph.assemble_multi_type_graph.AssembleMultiTypeGraphScioAdhocApp",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":assemble-multi-type-graph-scio-lib",
        "beam-internal/src/main/scala/com/twitter/beam/runner/dataflow",
    ],
)

jvm_binary(
    name = "assemble-multi-type-graph-scio-batch-app",
    main = "com.twitter.simclusters_v420.scio.multi_type_graph.assemble_multi_type_graph.AssembleMultiTypeGraphScioBatchApp",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":assemble-multi-type-graph-scio-lib",
        "beam-internal/src/main/scala/com/twitter/beam/runner/dataflow",
    ],
)

create_datasets(
    base_name = "full_multi_type_graph_scio",
    java_schema = "com.twitter.simclusters_v420.thriftjava.MultiTypeGraphEdge",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.thriftscala.MultiTypeGraphEdge",
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
    base_name = "top_k_right_nouns_mh_scio",
    key_type = "com.twitter.simclusters_v420.thriftscala.RightNodeTypeStruct",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.MultiTypeGraphInjections.topKRightNounListInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.NounWithFrequencyList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)
