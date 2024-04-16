scala_l brary(
    s ces = ["*.scala"],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":real_graph_ n_scores-scala",
        ":real_graph_oon_scores-scala",
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/dal",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/job",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/runner/dataflow",
        "tcdc/bq_blaster/src/ma n/scala/com/tw ter/tcdc/bqblaster/beam",
    ],
)

jvm_b nary(
    na  = " nteract on_graph_scores_sc o",
    ma n = "com.tw ter. nteract on_graph.sc o.ml.scores. nteract onGraphScoreExportJob",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":scores",
    ],
)

create_datasets(
    base_na  = "real_graph_ n_scores",
    descr pt on = "Real Graph  n network scores",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.wtf.scald ng.jobs. nject on.Cand dateSeq nject on. nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.wtf.cand date.thr ftscala.Cand dateSeq",
    scala_dependenc es = [
        "src/scala/com/tw ter/wtf/scald ng/jobs/ nject on",
    ],
)

create_datasets(
    base_na  = "real_graph_oon_scores",
    descr pt on = "Real Graph OON Scores",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.wtf.scald ng.jobs. nject on.Cand dateSeq nject on. nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.wtf.cand date.thr ftscala.Cand dateSeq",
    scala_dependenc es = [
        "src/scala/com/tw ter/wtf/scald ng/jobs/ nject on",
    ],
)
