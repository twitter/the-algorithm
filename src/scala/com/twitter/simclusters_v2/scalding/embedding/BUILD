scala_l brary(
    s ces = [
        "*.scala",
        "common/*.scala",
    ],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "esc rb rd/src/scala/com/tw ter/esc rb rd/scald ng/s ce",
        "flockdb-tools/datasets/flock:flock-blocks-edges-scala",
        "flockdb-tools/datasets/flock:flock-follows-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-abuse-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-spam-edges-scala",
        " es ce/process ng/events/src/ma n/scala/com/tw ter/ es ce/process ng/events/batch:server_engage nts-scala",
        " nterests-ds/src/ma n/scala/com/tw ter/ nterests_ds/jobs/ nterests_serv ce",
        " nterests-ds/src/ma n/scala/com/tw ter/ nterests_ds/jobs/ nterests_serv ce:user_top c_relat on_snapshot-scala",
        "src/java/com/tw ter/common/text/language:locale-ut l",
        "src/scala/com/tw ter/fr gate/data_p pel ne/scald ng/mag crecs/mag crecs_not f cat on_l e:mag crecs_not f cat on_l e_1day_lag-scala",
        "src/scala/com/tw ter/onboard ng/relevance/s ce:utt_account_recom ndat ons-scala",
        "src/scala/com/tw ter/pengu n/scald ng/datasets:pengu n_user_languages-scala",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces:s mclusters_v2_embedd ngs_l e-scala",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/presto_hdfs_s ces",
        "src/scala/com/tw ter/s mclusters_v2/scald ng/common",
        "src/scala/com/tw ter/s mclusters_v2/scald ng/common/matr x",
        "src/scala/com/tw ter/wtf/ent y_real_graph/common",
        "src/scala/com/tw ter/wtf/ent y_real_graph/scald ng/common",
        "src/scala/com/tw ter/wtf/scald ng/jobs/common:execut on_app",
        "src/scala/com/tw ter/wtf/scald ng/jobs/common:s ces",
        "src/scala/com/tw ter/wtf/scald ng/jobs/common:stats_ut l",
        "src/thr ft/com/tw ter/ rm /cand date: rm -cand date-scala",
        "src/thr ft/com/tw ter/onboard ng/relevance/cand dates:cand dates-scala",
        "src/thr ft/com/tw ter/recos/ent  es:ent  es-thr ft-scala",
        "src/thr ft/com/tw ter/search/adapt ve/scr b ng:adapt ve-scr b ng-scala",
        "src/thr ft/com/tw ter/wtf/ent y_real_graph:ent y_real_graph-thr ft-scala",
        "twadoop_conf g/conf gurat on/log_categor es/group/search:adapt ve_search-scala",
        "users ce/snapshot/src/ma n/scala/com/tw ter/users ce/snapshot/flat:users ce_flat-scala",
        "users ce/snapshot/src/ma n/thr ft/com/tw ter/users ce/snapshot/flat:flat-scala",
    ],
)

hadoop_b nary(
    na  = "ent y_embedd ngs_job-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.Ent yToS mClustersEmbedd ngAdhocApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "ent y_per_language_embedd ngs_job-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.LocaleEnt yS mClustersEmbedd ngAdhocApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "semant c_core_ent y_embedd ngs_dec11_model_job",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.Semant cCoreEnt yEmbedd ngsDec11ModelApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "semant c_core_ent y_embedd ngs_2020_job",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.Semant cCoreEnt yEmbedd ngs2020App",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "semant c_core_ent y_embedd ngs_per_language_job",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.LocaleEnt yS mClustersEmbedd ngSc duledApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "locale_ent y_s mclusters_embedd ng_v2",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.LocaleEnt yS mClustersEmbedd ngV2Sc duledApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "locale_ent y_s mclusters_embedd ng_v2-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.LocaleEnt yS mClustersEmbedd ngV2AdhocApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "producer_embedd ngs_from_ nterested_ n-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.ProducerEmbedd ngsFrom nterested nAdhocApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "producer_embedd ngs_from_ nterested_ n_by_fav_score",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.ProducerEmbedd ngsFrom nterested nByFavScoreBatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "producer_embedd ngs_from_ nterested_ n_by_fav_score_2020",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.ProducerEmbedd ngsFrom nterested nByFavScore2020BatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "producer_embedd ngs_from_ nterested_ n_by_fav_score_dec11",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.ProducerEmbedd ngsFrom nterested nByFavScoreDec11BatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "producer_embedd ngs_from_ nterested_ n_by_follow_score",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.ProducerEmbedd ngsFrom nterested nByFollowScoreBatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "producer_embedd ngs_from_ nterested_ n_by_follow_score_2020",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.ProducerEmbedd ngsFrom nterested nByFollowScore2020BatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "producer_embedd ngs_from_ nterested_ n_by_follow_score_dec11",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.ProducerEmbedd ngsFrom nterested nByFollowScoreDec11BatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "s m lar_users_by_s mclusters_embedd ngs-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.S m larUsersByS mClustersEmbedd ngAdhocApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "s m lar_users_by_s mclusters_embedd ngs",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.S m larUsersByS mClustersEmbedd ngBatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "ent y_embedd ng_from_producer_embedd ng-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.Ent yEmbedd ngFromProducerEmbedd ngAdhocJob",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "ent y_embedd ng_from_producer_embedd ng_job",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.Ent yEmbedd ngFromProducerEmbedd ngSc duledJob",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

# Generated w h `capesospy-v2 create_target s m lar_users_by_s mclusters_embedd ngs_job src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml`, conf g hash b8cf4d.
scald ng_job(
    na  = "s m lar_users_by_s mclusters_embedd ngs_job",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.S m l arUsersByS mClustersEmbedd ngBatchApp",
    conf g = [
        ("hadoop.comb ne- nput", "true"),
        ("hadoop.map.jvm.total- mory", "3072m"),
        ("hadoop.queue", "cassowary.default"),
        ("hadoop.reduce.jvm.total- mory", "3072m"),
        ("hadoop.subm ter.jvm.total- mory", "5120m"),
        ("subm ter.t er", "preempt ble"),
    ],
    cron = "15 * * * *",
    hadoop_cluster = "atla-proc3",
    platform = "java8",
    role = "cassowary",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)

hadoop_b nary(
    na  = "global_s mclusters_language_embedd ng_job",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.GlobalS mClustersLanguageEmbedd ngBatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":embedd ng"],
)
