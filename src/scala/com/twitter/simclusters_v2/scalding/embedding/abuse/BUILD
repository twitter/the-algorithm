scala_l brary(
    s ces = [
        "*.scala",
    ],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "3rdparty/jvm/com/tw ter/algeb rd:core",
        "graphstore/common:flock_blocks-java",
        "src/java/com/tw ter/search/common/features",
        "src/scala/com/tw ter/ml/ap :ap -base",
        "src/scala/com/tw ter/scald ng_ nternal/dalv2",
        "src/scala/com/tw ter/scald ng_ nternal/dalv2/dataset",
        "src/scala/com/tw ter/scald ng_ nternal/s ce/lzo_scrooge",
        "src/scala/com/tw ter/search/t et_rank ng/scald ng/datasets:t et_engage nt_raw_tra n ng_data_da ly-java",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces:adhoc_abuse_s mcluster_features-scala",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces:adhoc_cross_s mcluster_block_ nteract on_features-scala",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces:adhoc_cross_s mcluster_fav_ nteract on_features-scala",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces:search_abuse_s mcluster_features_manhattan-scala",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces:user_user_fav_graph-scala",
        "src/scala/com/tw ter/s mclusters_v2/scald ng/common",
        "src/scala/com/tw ter/s mclusters_v2/scald ng/common/matr x",
        "src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng",
        "src/scala/com/tw ter/wtf/ent y_real_graph/common",
        "src/scala/com/tw ter/wtf/ent y_real_graph/scald ng/common",
        "src/scala/com/tw ter/wtf/scald ng/jobs/common:cassowary_job",
        "src/scala/com/tw ter/wtf/scald ng/jobs/common:execut on_app",
        "src/scala/com/tw ter/wtf/scald ng/jobs/common:s ces",
        "src/scala/com/tw ter/wtf/scald ng/jobs/common:stats_ut l",
    ],
)

hadoop_b nary(
    na  = "abuse-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse.AdhocAbuseScald ngJob",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":abuse"],
)

hadoop_b nary(
    na  = "abuse-prod",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse.SearchAbuseS mclusterFeaturesScald ngJob",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [":abuse"],
)

hadoop_b nary(
    na  = "cross_s mcluster-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse.CrossS mClusterFeaturesScald ngJob",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":abuse",
    ],
)
