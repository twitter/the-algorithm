scala_l brary(
    s ces = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "esc rb rd/src/scala/com/tw ter/esc rb rd/scald ng/jobs/exportent  es:ent  es-scala",
        "esc rb rd/src/scala/com/tw ter/esc rb rd/scald ng/s ce/utt:utt_s ce-scala",
        " nterests-ds/src/ma n/scala/com/tw ter/ nterests_ds/jobs/ nterests_serv ce",
        " nterests-ds/src/ma n/scala/com/tw ter/ nterests_ds/jobs/ nterests_serv ce:user_top c_relat on_snapshot-scala",
        "src/scala/com/tw ter/scald ng_ nternal/dalv2",
        "src/scala/com/tw ter/scald ng_ nternal/job",
        "src/scala/com/tw ter/scald ng_ nternal/job/analyt cs_batch",
        "src/scala/com/tw ter/scald ng_ nternal/s ce",
        "src/scala/com/tw ter/scald ng_ nternal/s ce/lzo_scrooge",
        "src/scala/com/tw ter/s mclusters_v2/cand date_s ce",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces",
        "src/scala/com/tw ter/s mclusters_v2/scald ng",
        "src/scala/com/tw ter/wtf/scald ng/jobs/common:em_ut l",
        "t  l nes/data_process ng/jobs/ tr cs/per_top c_ tr cs:per_top c_aggregate_engage nt-scala",
    ],
)

hadoop_b nary(
    na  = "geopopular_top_t ets_ mpressed_top cs",
    ma n = "com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.GeoPopularTop csBatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":top c_recom ndat ons",
    ],
)

hadoop_b nary(
    na  = "geopopular_top_t ets_ mpressed_top cs_adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.GeoPopularTop csAdhocApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":top c_recom ndat ons",
    ],
)

hadoop_b nary(
    na  = "s m lar_top cs_from_top c_follow_graph",
    ma n = "com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.S m larTop csFromTop cFollowGraphSc duledApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":top c_recom ndat ons",
    ],
)

hadoop_b nary(
    na  = "s m lar_top cs_from_top c_follow_graph-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.S m larTop csFromTop cFollowGraphAdhocApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":top c_recom ndat ons",
    ],
)

hadoop_b nary(
    na  = "top_top cs_for_producers_from_em",
    ma n = "com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.Top csForProducersFromEMBatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":top c_recom ndat ons",
    ],
)

hadoop_b nary(
    na  = "top_top cs_for_producers_from_em-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.Top csForProducersFromEMAdhocApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":top c_recom ndat ons",
    ],
)

hadoop_b nary(
    na  = "top_producers_for_top cs_from_top c_follow_graph",
    ma n = "com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.ProducersForTop csFromTop cFollowGraphBatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":top c_recom ndat ons",
    ],
)

hadoop_b nary(
    na  = "top_producers_for_top cs_from_top c_follow_graph-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.ProducersForTop csFromTop cFollowGraphAdhocApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":top c_recom ndat ons",
    ],
)

# Generated w h `capesospy-v2 create_target popular_top cs_per_country src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml`, conf g hash beffad.
scald ng_job(
    na  = "popular_top cs_per_country",
    ma n = "com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.GeoPopularTop csBatchApp",
    args = ["--maxTop cs 100"],
    conf g = [
        ("hadoop.comb ne- nput", "true"),
        ("hadoop.map.jvm.total- mory", "3072m"),
        ("hadoop.queue", "cassowary.default"),
        ("hadoop.reduce.jvm.total- mory", "3072m"),
        ("hadoop.subm ter.jvm.total- mory", "5120m"),
        ("subm ter.t er", "preempt ble"),
    ],
    cron = "16 * * * *",
    hadoop_cluster = "atla-proc3",
    platform = "java8",
    role = "cassowary",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":top c_recom ndat ons",
    ],
)
