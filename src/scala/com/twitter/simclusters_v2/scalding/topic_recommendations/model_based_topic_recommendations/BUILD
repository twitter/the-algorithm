scala_l brary(
    s ces = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":top c_recom ndat ons_test_datarecords-java",
        ":top c_recom ndat ons_tra n_datarecords-java",
        "esc rb rd/src/scala/com/tw ter/esc rb rd/scald ng/jobs/exportent  es:ent  es-scala",
        " nterests-ds/src/ma n/scala/com/tw ter/ nterests_ds/jobs/ nterests_serv ce",
        " nterests-ds/src/ma n/scala/com/tw ter/ nterests_ds/jobs/ nterests_serv ce:user_top c_relat on_snapshot-scala",
        "src/java/com/tw ter/ml/ap /constant",
        "src/scala/com/tw ter/scald ng_ nternal/dalv2",
        "src/scala/com/tw ter/scald ng_ nternal/dalv2/dataset",
        "src/scala/com/tw ter/scald ng_ nternal/job",
        "src/scala/com/tw ter/scald ng_ nternal/job/analyt cs_batch",
        "src/scala/com/tw ter/scald ng_ nternal/s ce",
        "src/scala/com/tw ter/scald ng_ nternal/s ce/lzo_scrooge",
        "src/scala/com/tw ter/s mclusters_v2/cand date_s ce",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces",
        "src/scala/com/tw ter/s mclusters_v2/scald ng/common",
        "t  l nes/data_process ng/jobs/ tr cs/per_top c_ tr cs:per_top c_aggregate_engage nt-scala",
        "twml/runt  /src/ma n/scala/com/tw ter/twml/runt  /scald ng",
    ],
)

hadoop_b nary(
    na  = "tra n ng_data_for_top c_recom ndat ons-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.model_based_top c_recom ndat ons.UserTop cFeatureHydrat onAdhocApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":model_based_top c_recom ndat ons",
    ],
)

hadoop_b nary(
    na  = "tra n ng_data_for_top c_recom ndat ons",
    ma n = "com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.model_based_top c_recom ndat ons.UserTop cFeatureHydrat onSc duledApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":model_based_top c_recom ndat ons",
    ],
)

create_datarecord_datasets(
    base_na  = "top c_recom ndat ons_tra n_datarecords",
    platform = "java8",
    role = "cassowary",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
)

create_datarecord_datasets(
    base_na  = "top c_recom ndat ons_test_datarecords",
    platform = "java8",
    role = "cassowary",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
)
