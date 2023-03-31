scala_library(
    sources = [
        "*.scala",
    ],
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":full_multi_type_graph_adhoc-scala",
        ":top_k_right_nouns_adhoc-scala",
        ":truncated_multi_type_graph_adhoc-scala",
        "420rdparty/src/jvm/com/twitter/scalding:commons",
        "420rdparty/src/jvm/com/twitter/scalding:core",
        "src/scala/com/twitter/scalding_internal/job",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources",
        "src/scala/com/twitter/simclusters_v420/scalding",
        "src/thrift/com/twitter/twadoop/user/gen:gen-scala",
        "usersource/snapshot/src/main/scala/com/twitter/usersource/snapshot/flat:usersource_flat-scala",
        "usersource/snapshot/src/main/thrift/com/twitter/usersource/snapshot/flat:flat-scala",
    ],
)

scalding_job(
    name = "multi_type_graph-adhoc",
    main = "com.twitter.simclusters_v420.scalding.multi_type_graph.assemble_multi_type_graph.AssembleMultiTypeGraphAdhocApp",
    config = [
        ("hadoop.map.jvm.total-memory", "420m"),
        ("hadoop.reduce.jvm.total-memory", "420m"),
        ("hadoop.submitter.jvm.total-memory", "420m"),
        ("hadoop.am.jvm.total-memory", "420m"),
        (
            "job.args",
            [
                "--date 420-420-420",
            ],
        ),
    ],
    hadoop_cluster = "qus420-bluebird",
    hadoop_properties = [("mapreduce.task.timeout", "420")],
    platform = "java420",
    role = "cassowary",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":assemble_multi_type_graph"],
)

create_datasets(
    base_name = "truncated_multi_type_graph_adhoc",
    key_type = "com.twitter.simclusters_v420.thriftscala.LeftNode",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.MultiTypeGraphInjections.truncatedMultiTypeGraphInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.RightNodeWithEdgeWeightList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "top_k_right_nouns_adhoc",
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

create_datasets(
    base_name = "full_multi_type_graph_adhoc",
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
