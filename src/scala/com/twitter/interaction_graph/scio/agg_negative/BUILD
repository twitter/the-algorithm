scala_l brary(
    na  = "agg_negat ve",
    s ces = ["*.scala"],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":real_graph_negat ve_features-scala",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/dal",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/job",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/runner/dataflow",
        "flockdb-tools/datasets/flock:flock-blocks-edges-scala",
        "flockdb-tools/datasets/flock:flock-mutes-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-abuse-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-spam-edges-scala",
        "soc algraph/hadoop/src/ma n/scala/com/tw ter/soc algraph/hadoop:soc algraph-unfollows-scala",
        "src/scala/com/tw ter/ nteract on_graph/sc o/common",
        "tcdc/bq_blaster/src/ma n/scala/com/tw ter/tcdc/bqblaster/beam",
    ],
)

jvm_b nary(
    na  = " nteract on_graph_negat ve_sc o",
    ma n = "com.tw ter. nteract on_graph.sc o.agg_negat ve. nteract onGraphNegat veJob",
    platform = "java8",
    dependenc es = [
        ":agg_negat ve",
    ],
)

create_datasets(
    base_na  = "real_graph_negat ve_features",
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
