scala_l brary(
    na  = "mult -type-graph-sc o-s ms-l b",
    s ces = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":r ght_node_cos ne_s m lar y_sc o_adhoc-scala",
        ":r ght_node_s m_hash_sc o_adhoc-scala",
        "3rdparty/jvm/com/tw ter/b ject on:scrooge",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/dal",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/manhattan",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/job",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/transform",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/runner/dataflow",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces",
        "src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/common",
        "src/scala/com/tw ter/wtf/dataflow/cos ne_s m lar y/common",
    ],
)

jvm_b nary(
    na  = "mult -type-graph-s m-hash-sc o-adhoc-app",
    ma n = "com.tw ter.s mclusters_v2.sc o.mult _type_graph.mult _type_graph_s ms.R ghtNodeS mHashSc oAdhocApp",
    platform = "java8",
    dependenc es = [
        ":mult -type-graph-sc o-s ms-l b",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/runner/dataflow",
    ],
)

jvm_b nary(
    na  = "mult -type-graph-s m-hash-sc o-batch-app",
    ma n = "com.tw ter.s mclusters_v2.sc o.mult _type_graph.mult _type_graph_s ms.R ghtNodeS mHashSc oBatchApp",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":mult -type-graph-sc o-s ms-l b",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/runner/dataflow",
    ],
)

jvm_b nary(
    na  = "mult -type-graph-cos ne-s m lar y-sc o-adhoc-app",
    ma n = "com.tw ter.s mclusters_v2.sc o.mult _type_graph.mult _type_graph_s ms.R ghtNodeCos neS m lar ySc oAdhocApp",
    platform = "java8",
    dependenc es = [
        ":mult -type-graph-sc o-s ms-l b",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/runner/dataflow",
    ],
)

jvm_b nary(
    na  = "mult -type-graph-cos ne-s m lar y-sc o-batch-app",
    ma n = "com.tw ter.s mclusters_v2.sc o.mult _type_graph.mult _type_graph_s ms.R ghtNodeCos neS m lar ySc oBatchApp",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":mult -type-graph-sc o-s ms-l b",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/runner/dataflow",
    ],
)

create_datasets(
    base_na  = "r ght_node_s m_hash_sc o_adhoc",
    java_sc ma = "com.tw ter.s mclusters_v2.thr ftjava.R ghtNodeS mHashSketch",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeS mHashSketch",
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
    base_na  = "r ght_node_cos ne_s m lar y_sc o_adhoc",
    key_type = "com.tw ter.s mclusters_v2.thr ftscala.R ghtNode",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.Mult TypeGraph nject ons.s m larR ghtNodes nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.S m larR ghtNodes",
    scala_dependenc es = [
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)
