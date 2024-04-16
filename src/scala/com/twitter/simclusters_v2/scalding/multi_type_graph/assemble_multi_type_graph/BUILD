scala_l brary(
    s ces = [
        "*.scala",
    ],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":full_mult _type_graph_adhoc-scala",
        ":top_k_r ght_nouns_adhoc-scala",
        ":truncated_mult _type_graph_adhoc-scala",
        "3rdparty/src/jvm/com/tw ter/scald ng:commons",
        "3rdparty/src/jvm/com/tw ter/scald ng:core",
        "src/scala/com/tw ter/scald ng_ nternal/job",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces",
        "src/scala/com/tw ter/s mclusters_v2/scald ng",
        "src/thr ft/com/tw ter/twadoop/user/gen:gen-scala",
        "users ce/snapshot/src/ma n/scala/com/tw ter/users ce/snapshot/flat:users ce_flat-scala",
        "users ce/snapshot/src/ma n/thr ft/com/tw ter/users ce/snapshot/flat:flat-scala",
    ],
)

scald ng_job(
    na  = "mult _type_graph-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.mult _type_graph.assemble_mult _type_graph.AssembleMult TypeGraphAdhocApp",
    conf g = [
        ("hadoop.map.jvm.total- mory", "8192m"),
        ("hadoop.reduce.jvm.total- mory", "8192m"),
        ("hadoop.subm ter.jvm.total- mory", "8192m"),
        ("hadoop.am.jvm.total- mory", "8192m"),
        (
            "job.args",
            [
                "--date 2021-07-14",
            ],
        ),
    ],
    hadoop_cluster = "qus1-blueb rd",
    hadoop_propert es = [("mapreduce.task.t  out", "3600000")],
    platform = "java8",
    role = "cassowary",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":assemble_mult _type_graph"],
)

create_datasets(
    base_na  = "truncated_mult _type_graph_adhoc",
    key_type = "com.tw ter.s mclusters_v2.thr ftscala.LeftNode",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.Mult TypeGraph nject ons.truncatedMult TypeGraph nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeW hEdge  ghtL st",
    scala_dependenc es = [
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)

create_datasets(
    base_na  = "top_k_r ght_nouns_adhoc",
    key_type = "com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeTypeStruct",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.Mult TypeGraph nject ons.topKR ghtNounL st nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.NounW hFrequencyL st",
    scala_dependenc es = [
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)

create_datasets(
    base_na  = "full_mult _type_graph_adhoc",
    java_sc ma = "com.tw ter.s mclusters_v2.thr ftjava.Mult TypeGraphEdge",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.thr ftscala.Mult TypeGraphEdge",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    java_dependenc es = [
        "src/thr ft/com/tw ter/s mclusters_v2:s mclusters_v2-thr ft-java",
    ],
    scala_dependenc es = [
        "src/thr ft/com/tw ter/s mclusters_v2:s mclusters_v2-thr ft-scala",
    ],
)
