scala_l brary(
    na  = "agg_flock",
    s ces = ["*.scala"],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ": nteract on_graph_agg_flock_edge_snapshot-scala",
        ": nteract on_graph_agg_flock_vertex_snapshot-scala",
        "3rdparty/jvm/com/tw ter/storehaus:algebra",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/dal",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/job",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/runner/dataflow",
        "consu r-data-tools/src/ma n/scala/com/tw ter/cde/sc o/dal_read",
        "flockdb-tools/datasets/flock:flock-blocks-edges-scala",
        "flockdb-tools/datasets/flock:flock-mutes-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-abuse-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-spam-edges-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/common",
        "src/scala/com/tw ter/wtf/dataflow/user_events:val d_user_follows-scala",
        "src/thr ft/com/tw ter/core_workflows/user_model:user_model-scala",
        "src/thr ft/com/tw ter/twadoop/user/gen:gen-java",
        "src/thr ft/com/tw ter/twadoop/user/gen:gen-scala",
        "users ce/snapshot/src/ma n/scala/com/tw ter/users ce/snapshot/comb ned:users ce-scala",
    ],
)

jvm_b nary(
    na  = " nteract on_graph_agg_flock_sc o",
    ma n = "com.tw ter. nteract on_graph.sc o.agg_flock. nteract onGraphAggFlockJob",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":agg_flock",
    ],
)

create_datasets(
    base_na  = " nteract on_graph_agg_flock_edge_snapshot",
    descr pt on = "User-user d rected edges w h flock features",
    java_sc ma = "com.tw ter. nteract on_graph.thr ftjava.Edge",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter. nteract on_graph.thr ftscala.Edge",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    java_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-java",
    ],
    scala_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-scala",
    ],
)

create_datasets(
    base_na  = " nteract on_graph_agg_flock_vertex_snapshot",
    descr pt on = "User vertex w h flock features",
    java_sc ma = "com.tw ter. nteract on_graph.thr ftjava.Vertex",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter. nteract on_graph.thr ftscala.Vertex",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    java_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-java",
    ],
    scala_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-scala",
    ],
)
