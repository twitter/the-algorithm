scala_l brary(
    na  = "assemble-mult -type-graph-sc o-l b",
    s ces = [
        "*.scala",
    ],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":full_mult _type_graph_sc o-scala",
        ":top_k_r ght_nouns_mh_sc o-scala",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/dal",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/manhattan",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/job",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/transform",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/runner/dataflow",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces",
        "src/scala/com/tw ter/s mclusters_v2/scald ng/mult _type_graph/assemble_mult _type_graph",
        "src/scala/com/tw ter/s mclusters_v2/sc o/common",
        "src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/common",
    ],
)

jvm_b nary(
    na  = "assemble-mult -type-graph-sc o-adhoc-app",
    ma n = "com.tw ter.s mclusters_v2.sc o.mult _type_graph.assemble_mult _type_graph.AssembleMult TypeGraphSc oAdhocApp",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":assemble-mult -type-graph-sc o-l b",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/runner/dataflow",
    ],
)

jvm_b nary(
    na  = "assemble-mult -type-graph-sc o-batch-app",
    ma n = "com.tw ter.s mclusters_v2.sc o.mult _type_graph.assemble_mult _type_graph.AssembleMult TypeGraphSc oBatchApp",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":assemble-mult -type-graph-sc o-l b",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/runner/dataflow",
    ],
)

create_datasets(
    base_na  = "full_mult _type_graph_sc o",
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

create_datasets(
    base_na  = "top_k_r ght_nouns_mh_sc o",
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
