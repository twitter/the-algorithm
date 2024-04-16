scala_l brary(
    na  = "labels",
    s ces = ["*.scala"],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ": nteract on_graph_labels_da ly-scala",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/dal",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/job",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/runner/dataflow",
        "consu r-data-tools/src/ma n/scala/com/tw ter/cde/sc o/dal_read",
        "soc algraph/hadoop/src/ma n/scala/com/tw ter/soc algraph/hadoop:soc algraph-follow-events-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_cl ent_event_logs: nteract on_graph_agg_cl ent_event_logs_edge_da ly-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_d rect_ nteract ons: nteract on_graph_agg_d rect_ nteract ons_edge_da ly-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_ema l: nteract on_graph_extended_ema l_edge_da ly-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_not f cat ons: nteract on_graph_agg_not f cat ons_edge_da ly-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_ret ets: nteract on_graph_extended_ret et_edge_da ly-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/agg_shares: nteract on_graph_extended_share_edge_da ly-scala",
        "tcdc/bq_blaster/src/ma n/scala/com/tw ter/tcdc/bqblaster/beam",
    ],
)

jvm_b nary(
    na  = " nteract on_graph_labels",
    ma n = "com.tw ter. nteract on_graph.sc o.ml.labels. nteract onGraphLabelsJob",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":labels",
    ],
)

create_datasets(
    base_na  = " nteract on_graph_labels_da ly",
    descr pt on = "Da ly labels",
    java_sc ma = "com.tw ter. nteract on_graph.thr ftjava.EdgeLabel",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter. nteract on_graph.thr ftscala.EdgeLabel",
    seg nt_type = "part  oned",
    tags = ["bazel-compat ble"],
    java_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-java",
    ],
    scala_dependenc es = [
        "src/thr ft/com/tw ter/ nteract on_graph: nteract on_graph-scala",
    ],
)
