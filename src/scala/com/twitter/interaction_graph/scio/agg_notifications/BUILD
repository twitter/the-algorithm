scala_l brary(
    na  = "agg_not f cat ons",
    s ces = ["*.scala"],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ": nteract on_graph_agg_not f cat ons_edge_da ly-scala",
        ": nteract on_graph_agg_not f cat ons_vertex_da ly-scala",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/dal",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/job",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/runner/dataflow",
        "consu r-data-tools/src/ma n/scala/com/tw ter/cde/sc o/dal_read",
        "src/scala/com/tw ter/fr gate/data_p pel ne_beam/mr-cl ent-event-f lter ng-job/src/ma n/scala/com/tw ter/cl ent_event_f lter ng:fr gate_f ltered_cl ent_events_dataflow-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/common",
        "src/scala/com/tw ter/wtf/scald ng/jobs/cl ent_event_process ng:user_ nteract on-scala",
        "tcdc/bq_blaster/src/ma n/scala/com/tw ter/tcdc/bqblaster/beam",
        "twadoop_conf g/conf gurat on/log_categor es/group/fr gate:fr gate_not f er-scala",
        "t ets ce/publ c_t ets/src/ma n/scala/com/tw ter/t ets ce/publ c_t ets:publ c_t ets-scala",
        "users ce/snapshot/src/ma n/scala/com/tw ter/users ce/snapshot/comb ned:users ce-scala",
    ],
)

jvm_b nary(
    na  = " nteract on_graph_not f cat ons_sc o",
    ma n = "com.tw ter. nteract on_graph.sc o.agg_not f cat ons. nteract onGraphNot f cat onsJob",
    platform = "java8",
    dependenc es = [
        ":agg_not f cat ons",
    ],
)

create_datasets(
    base_na  = " nteract on_graph_agg_not f cat ons_edge_da ly",
    descr pt on = "User-user d rected edges w h not f cat on features",
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
    base_na  = " nteract on_graph_agg_not f cat ons_vertex_da ly",
    descr pt on = "User vertex w h not f cat on features",
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
