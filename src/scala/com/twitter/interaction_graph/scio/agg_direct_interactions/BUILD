scala_l brary(
    na  = "agg_d rect_ nteract ons",
    s ces = ["*.scala"],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ": nteract on_graph_agg_d rect_ nteract ons_edge_da ly-scala",
        ": nteract on_graph_agg_d rect_ nteract ons_vertex_da ly-scala",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/dal",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/job",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/runner/dataflow",
        "consu r-data-tools/src/ma n/scala/com/tw ter/cde/sc o/dal_read",
        "src/scala/com/tw ter/ nteract on_graph/sc o/common",
        "src/thr ft/com/tw ter/t  l neserv ce/server/ nternal:thr ft-scala",
        "twadoop_conf g/conf gurat on/log_categor es/group/t  l ne:t  l ne_serv ce_favor es-scala",
        "twadoop_conf g/conf gurat on/log_categor es/group/t etyp e:t etyp e_ d a_tag_events-scala",
        "t ets ce/common:unhydrated_flat-scala",
        "users ce/snapshot/src/ma n/scala/com/tw ter/users ce/snapshot/comb ned:users ce-scala",
    ],
)

jvm_b nary(
    na  = " nteract on_graph_agg_d rect_ nteract ons_sc o",
    ma n = "com.tw ter. nteract on_graph.sc o.agg_d rect_ nteract ons. nteract onGraphAggD rect nteract onsJob",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":agg_d rect_ nteract ons",
    ],
)

create_datasets(
    base_na  = " nteract on_graph_agg_d rect_ nteract ons_edge_da ly",
    descr pt on = "User-user d rected edges w h d rect  nteract ons features",
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
    base_na  = " nteract on_graph_agg_d rect_ nteract ons_vertex_da ly",
    descr pt on = "User vertex w h d rect  nteract ons features",
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
