scala_l brary(
    na  = "agg_address_book",
    s ces = ["*.scala"],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ": nteract on_graph_agg_address_book_edge_snapshot-scala",
        ": nteract on_graph_agg_address_book_vertex_snapshot-scala",
        "3rdparty/jvm/com/tw ter/storehaus:algebra",
        "addressbook/jobs/src/ma n/scala/com/tw ter/addressbook/jobs/s mplematc s:s mple_user_matc s-scala",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/dal",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/job",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/runner/dataflow",
        "consu r-data-tools/src/ma n/scala/com/tw ter/cde/sc o/dal_read",
        "src/scala/com/tw ter/ nteract on_graph/sc o/common",
    ],
)

jvm_b nary(
    na  = " nteract on_graph_address_book_sc o",
    ma n = "com.tw ter. nteract on_graph.sc o.agg_address_book. nteract onGraphAddressBookJob",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":agg_address_book",
    ],
)

create_datasets(
    base_na  = " nteract on_graph_agg_address_book_edge_snapshot",
    descr pt on = "User-user d rected edges w h addressbook features",
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
    base_na  = " nteract on_graph_agg_address_book_vertex_snapshot",
    descr pt on = "User vertex w h addressbook features",
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
