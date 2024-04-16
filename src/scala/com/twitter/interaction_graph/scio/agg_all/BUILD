scala_l brary(
    na  = "agg_all",
    s ces = ["*.scala"],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ": nteract on_graph_ tory_aggregated_raw_edge_da ly-scala",
        ": nteract on_graph_ tory_aggregated_vertex_da ly-scala",
        ": nteract on_graph_aggregated_edge_da ly-scala",
        ": nteract on_graph_aggregated_vertex_da ly-scala",
        ": nteract on_graph_ tory_aggregated_edge_snapshot-scala",
        ": nteract on_graph_ tory_aggregated_vertex_snapshot-scala",
        ":real_graph_features-scala",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/dal",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/job",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/runner/dataflow",
        "consu r-data-tools/src/ma n/scala/com/tw ter/cde/sc o/dal_read",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_address_book: nteract on_graph_agg_address_book_edge_snapshot-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_address_book: nteract on_graph_agg_address_book_vertex_snapshot-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_cl ent_event_logs: nteract on_graph_agg_cl ent_event_logs_edge_da ly-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_cl ent_event_logs: nteract on_graph_agg_cl ent_event_logs_vertex_da ly-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_d rect_ nteract ons: nteract on_graph_agg_d rect_ nteract ons_edge_da ly-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_d rect_ nteract ons: nteract on_graph_agg_d rect_ nteract ons_vertex_da ly-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_flock: nteract on_graph_agg_flock_edge_snapshot-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_flock: nteract on_graph_agg_flock_vertex_snapshot-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/common",
        "src/scala/com/tw ter/ nteract on_graph/sc o/ml/scores:real_graph_ n_scores-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/ml/scores:real_graph_oon_scores-scala",
        "src/scala/com/tw ter/wtf/dataflow/user_events:val d_user_follows-scala",
        "src/thr ft/com/tw ter/wtf/cand date:wtf-cand date-scala",
        "tcdc/bq_blaster/src/ma n/scala/com/tw ter/tcdc/bqblaster/beam",
        "users ce/snapshot/src/ma n/scala/com/tw ter/users ce/snapshot/flat:users ce_flat-scala",
    ],
)

create_datasets(
    base_na  = " nteract on_graph_ tory_aggregated_raw_edge_da ly",
    descr pt on = "User-user d rected edges w h all features",
    java_sc ma = "com.tw ter. nteract on_graph.thr ftjava.Edge",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter. nteract on_graph.thr ftscala.Edge",
    seg nt_type = "part  oned",
    tags = ["bazel-compat ble"],
    java_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-java",
    ],
    scala_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-scala",
    ],
)

create_datasets(
    base_na  = " nteract on_graph_ tory_aggregated_vertex_da ly",
    descr pt on = "User vertex w h all features",
    java_sc ma = "com.tw ter. nteract on_graph.thr ftjava.Vertex",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter. nteract on_graph.thr ftscala.Vertex",
    seg nt_type = "part  oned",
    tags = ["bazel-compat ble"],
    java_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-java",
    ],
    scala_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-scala",
    ],
)

jvm_b nary(
    na  = " nteract on_graph_aggregat on_job_sc o",
    ma n = "com.tw ter. nteract on_graph.sc o.agg_all. nteract onGraphAggregat onJob",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":agg_all",
    ],
)

create_datasets(
    base_na  = " nteract on_graph_ tory_aggregated_edge_snapshot",
    descr pt on = "User-user d rected edges w h all features",
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
    base_na  = " nteract on_graph_ tory_aggregated_vertex_snapshot",
    descr pt on = "User vertex w h all features",
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

create_datasets(
    base_na  = " nteract on_graph_aggregated_edge_da ly",
    descr pt on = "User-user d rected edges w h all features",
    java_sc ma = "com.tw ter. nteract on_graph.thr ftjava.Edge",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter. nteract on_graph.thr ftscala.Edge",
    seg nt_type = "part  oned",
    tags = ["bazel-compat ble"],
    java_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-java",
    ],
    scala_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-scala",
    ],
)

create_datasets(
    base_na  = " nteract on_graph_aggregated_vertex_da ly",
    descr pt on = "User vertex w h all features",
    java_sc ma = "com.tw ter. nteract on_graph.thr ftjava.Vertex",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter. nteract on_graph.thr ftscala.Vertex",
    seg nt_type = "part  oned",
    tags = ["bazel-compat ble"],
    java_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-java",
    ],
    scala_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-scala",
    ],
)

create_datasets(
    base_na  = "real_graph_features",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter. nteract on_graph. nject on.UserSess on nject on. nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.user_sess on_store.thr ftscala.UserSess on",
    scala_dependenc es = [
        "src/scala/com/tw ter/ nteract on_graph/ nject on:user_sess on_ nj",
    ],
)

create_datasets(
    base_na  = "ho _l ght_ranker_top_k_real_graph_features",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter. nteract on_graph. nject on.EdgeL st nject on. nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter. nteract on_graph.thr ftscala.EdgeL st",
    scala_dependenc es = [
        "src/scala/com/tw ter/ nteract on_graph/ nject on:edge_l st_ nject on",
    ],
)
