scala_l brary(
    na  = "bq_generat on",
    s ces = [
        "**/*.scala",
    ],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "beam- nternal/src/ma n/scala/com/tw ter/beam/ o/dal",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/job",
        "beam- nternal/src/ma n/scala/com/tw ter/sc o_ nternal/runner/dataflow",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces:offl ne_t et_recom ndat ons_from_ nterested_ n_20M_145K_2020-scala",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces:offl ne_t et_recom ndat ons_from_ nterested_ n_20M_145K_2020_HL_0_EL_15-scala",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces:offl ne_t et_recom ndat ons_from_ nterested_ n_20M_145K_2020_HL_2_EL_15-scala",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces:offl ne_t et_recom ndat ons_from_ nterested_ n_20M_145K_2020_HL_2_EL_50-scala",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces:offl ne_t et_recom ndat ons_from_ nterested_ n_20M_145K_2020_HL_8_EL_50-scala",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces:offl ne_t et_recom ndat ons_from_mts_consu r_embedd ngs-scala",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/common",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql",
        "src/scala/com/tw ter/wtf/beam/bq_embedd ng_export:bq_embedd ng_export_l b",
        "tcdc/bq_blaster/src/ma n/scala/com/tw ter/tcdc/bqblaster/beam",
    ],
)

jvm_b nary(
    na  = "  kf-t ets-ann-adhoc-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.t ets_ann.  KF2020T etsANNBQAdhocJob",
    platform = "java8",
    dependenc es = [
        ":bq_generat on",
    ],
)

jvm_b nary(
    na  = "  kf-hl-8-el-50-t ets-ann-adhoc-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.t ets_ann.  KF2020Hl8El50T etsANNBQAdhocJob",
    platform = "java8",
    dependenc es = [
        ":bq_generat on",
    ],
)

jvm_b nary(
    na  = "  kf-t ets-ann-batch-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.t ets_ann.  KF2020T etsANNBQBatchJob",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":bq_generat on",
    ],
)

jvm_b nary(
    na  = "  kf-hl-0-el-15-t ets-ann-batch-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.t ets_ann.  KF2020Hl0El15T etsANNBQBatchJob",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":bq_generat on",
    ],
)

jvm_b nary(
    na  = "  kf-hl-2-el-15-t ets-ann-batch-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.t ets_ann.  KF2020Hl2El15T etsANNBQBatchJob",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":bq_generat on",
    ],
)

jvm_b nary(
    na  = "  kf-hl-2-el-50-t ets-ann-batch-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.t ets_ann.  KF2020Hl2El50T etsANNBQBatchJob",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":bq_generat on",
    ],
)

jvm_b nary(
    na  = "  kf-hl-8-el-50-t ets-ann-batch-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.t ets_ann.  KF2020Hl8El50T etsANNBQBatchJob",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":bq_generat on",
    ],
)

jvm_b nary(
    na  = "mts-consu r-embedd ngs-t ets-ann-adhoc-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.t ets_ann.MTSConsu rEmbedd ngsT etsANNBQAdhocJob",
    platform = "java8",
    dependenc es = [
        ":bq_generat on",
    ],
)

jvm_b nary(
    na  = "mts-consu r-embedd ngs-t ets-ann-batch-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.t ets_ann.MTSConsu rEmbedd ngsT etsANNBQBatchJob",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":bq_generat on",
    ],
)
