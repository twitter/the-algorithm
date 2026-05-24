scala_library(
    sources = [
        "*.scala",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":full_multi_type_graph_adhoc-scala",
        ":top_k_right_nouns_adhoc-scala",
        ":truncated_multi_type_graph_adhoc-scala",
        "3rdparty/src/jvm/com/twitter/scalding:commons",
        "3rdparty/src/jvm/com/twitter/scalding:core",
        "src/scala/com/twitter/scalding_internal/job",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources",
        "src/scala/com/twitter/simclusters_v2/scalding",
        "src/thrift/com/twitter/twadoop/user/gen:gen-scala",
        "usersource/snapshot/src/main/scala/com/twitter/usersource/snapshot/flat:usersource_flat-scala",
        "usersource/snapshot/src/main/thrift/com/twitter/usersource/snapshot/flat:flat-scala",
    ],
)

scalding_job(
    name = "multi_type_graph-adhoc",
    main = "com.twitter.simclusters_v2.scalding.multi_type_graph.assemble_multi_type_graph.AssembleMultiTypeGraphAdhocApp",
    config = [
        ("hadoop.map.jvm.total-memory", "8192m"),
        ("hadoop.reduce.jvm.total-memory", "8192m"),
        ("hadoop.submitter.jvm.total-memory", "8192m"),
        ("hadoop.am.jvm.total-memory", "8192m"),
        (
            "job.args",
            [
                "--date 2021-07-14",
            ],
        ),
    ],
    hadoop_cluster = "qus1-bluebird",
    hadoop_properties = [("mapreduce.task.timeout", "3600000")],
    platform = "java8",
    role = "cassowary",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":assemble_multi_type_graph"],
)

create_datasets(
    base_name = "truncated_multi_type_graph_adhoc",
    key_type = "com.twitter.simclusters_v2.thriftscala.LeftNode",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.truncatedMultiTypeGraphInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.RightNodeWithEdgeWeightList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "top_k_right_nouns_adhoc",
    key_type = "com.twitter.simclusters_v2.thriftscala.RightNodeTypeStruct",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.topKRightNounListInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.NounWithFrequencyList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "full_multi_type_graph_adhoc",
    java_schema = "com.twitter.simclusters_v2.thriftjava.MultiTypeGraphEdge",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.MultiTypeGraphEdge",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)
