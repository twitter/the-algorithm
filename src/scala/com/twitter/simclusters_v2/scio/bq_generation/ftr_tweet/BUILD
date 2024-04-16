scala_l brary(
    na  = "ftr_bq_generat on",
    s ces = [
        "**/*.scala",
    ],
    tags = ["bazel-compat ble"],
    dependenc es = [
        "src/scala/com/tw ter/s mclusters_v2/common",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/common",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:offl ne_t et_recom ndat ons_decayed_sum-scala",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:offl ne_t et_recom ndat ons_ftr_adhoc-scala",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:offl ne_t et_recom ndat ons_ftrat5_pop_b ased_1000-scala",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:offl ne_t et_recom ndat ons_ftrat5_pop_b ased_10000-scala",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:s mclusters_decayed_sum_cluster_to_t et_ ndex-scala",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:s mclusters_ftr_adhoc_cluster_to_t et_ ndex-scala",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:s mclusters_ftr_pop10000_rnkdecay11_cluster_to_t et_ ndex-scala",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:s mclusters_ftr_pop1000_rnkdecay11_cluster_to_t et_ ndex-scala",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:s mclusters_oon_ftr_adhoc_cluster_to_t et_ ndex-scala",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:s mclusters_oon_ftr_pop1000_rnkdecay_cluster_to_t et_ ndex-scala",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/sql",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql",
        "src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/t ets_ann:bq_generat on",
    ],
)

jvm_b nary(
    na  = "ftr-t et-adhoc-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.ftr_t et.FTRAdhocJob",
    dependenc es = [
        ":ftr_bq_generat on",
    ],
)

jvm_b nary(
    na  = "  kf2020-decayed-sum-batch-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.ftr_t et.  KF2020DecayedSumBatchJobProd",
    dependenc es = [
        ":ftr_bq_generat on",
    ],
)

jvm_b nary(
    na  = "  kf2020-ftrat5-pop1000-batch-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.ftr_t et.  KF2020FTRAt5Pop1000batchJobProd",
    dependenc es = [
        ":ftr_bq_generat on",
    ],
)

jvm_b nary(
    na  = "  kf2020-ftrat5-pop10000-batch-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.ftr_t et.  KF2020FTRAt5Pop10000batchJobProd",
    dependenc es = [
        ":ftr_bq_generat on",
    ],
)

create_datasets(
    base_na  = "offl ne_t et_recom ndat ons_ftr_adhoc",
    key_type = "Long",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.Mult TypeGraph nject ons.t etRecom ndat ons nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.Cand dateT etsL st",
    scala_dependenc es = [
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)

create_datasets(
    base_na  = "offl ne_t et_recom ndat ons_decayed_sum",
    key_type = "Long",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.Mult TypeGraph nject ons.t etRecom ndat ons nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.Cand dateT etsL st",
    scala_dependenc es = [
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)

create_datasets(
    base_na  = "offl ne_t et_recom ndat ons_ftrat5_pop_b ased_1000",
    key_type = "Long",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.Mult TypeGraph nject ons.t etRecom ndat ons nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.Cand dateT etsL st",
    scala_dependenc es = [
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)

create_datasets(
    base_na  = "offl ne_t et_recom ndat ons_ftrat5_pop_b ased_10000",
    key_type = "Long",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.Mult TypeGraph nject ons.t etRecom ndat ons nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.Cand dateT etsL st",
    scala_dependenc es = [
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)

jvm_b nary(
    na  = "ftr-t et- ndex-generat on-adhoc-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.ftr_t et.FTRClusterToT et ndexGenerat onAdhoc",
    dependenc es = [
        ":ftr_bq_generat on",
    ],
)

jvm_b nary(
    na  = "oon-ftr-t et- ndex-generat on-adhoc-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.ftr_t et.OONFTRClusterToT et ndexGenerat onAdhoc",
    dependenc es = [
        ":ftr_bq_generat on",
    ],
)

jvm_b nary(
    na  = "ftr-t et- ndex-generat on-pop1000-rnkdecay11-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.ftr_t et.FTRPop1000RankDecay11ClusterToT et ndexGenerat onBatch",
    dependenc es = [
        ":ftr_bq_generat on",
    ],
)

jvm_b nary(
    na  = "ftr-t et- ndex-generat on-pop10000-rnkdecay11-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.ftr_t et.FTRPop10000RankDecay11ClusterToT et ndexGenerat onBatch",
    dependenc es = [
        ":ftr_bq_generat on",
    ],
)

jvm_b nary(
    na  = "oon-ftr-t et- ndex-generat on-pop1000-rnkdecay-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.ftr_t et.OONFTRPop1000RankDecayClusterToT et ndexGenerat onBatch",
    dependenc es = [
        ":ftr_bq_generat on",
    ],
)

jvm_b nary(
    na  = "ftr-t et- ndex-generat on-decayed-sum-job",
    ma n = "com.tw ter.s mclusters_v2.sc o.bq_generat on.ftr_t et.DecayedSumClusterToT et ndexGenerat onBatch",
    dependenc es = [
        ":ftr_bq_generat on",
    ],
)

create_datasets(
    base_na  = "s mclusters_ftr_adhoc_cluster_to_t et_ ndex",
    key_type = "com.tw ter.s mclusters_v2.thr ftscala.FullCluster d",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.ClusterTopT ets nject on.cluster dToTopKT ets nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.TopKT etsW hScores",
    scala_dependenc es = [
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)

create_datasets(
    base_na  = "s mclusters_oon_ftr_adhoc_cluster_to_t et_ ndex",
    key_type = "com.tw ter.s mclusters_v2.thr ftscala.FullCluster d",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.ClusterTopT ets nject on.cluster dToTopKT ets nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.TopKT etsW hScores",
    scala_dependenc es = [
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)

create_datasets(
    base_na  = "s mclusters_ftr_pop1000_rnkdecay11_cluster_to_t et_ ndex",
    key_type = "com.tw ter.s mclusters_v2.thr ftscala.FullCluster d",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.ClusterTopT ets nject on.cluster dToTopKT ets nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.TopKT etsW hScores",
    scala_dependenc es = [
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)

create_datasets(
    base_na  = "s mclusters_ftr_pop10000_rnkdecay11_cluster_to_t et_ ndex",
    key_type = "com.tw ter.s mclusters_v2.thr ftscala.FullCluster d",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.ClusterTopT ets nject on.cluster dToTopKT ets nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.TopKT etsW hScores",
    scala_dependenc es = [
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)

create_datasets(
    base_na  = "s mclusters_oon_ftr_pop1000_rnkdecay_cluster_to_t et_ ndex",
    key_type = "com.tw ter.s mclusters_v2.thr ftscala.FullCluster d",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.ClusterTopT ets nject on.cluster dToTopKT ets nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.TopKT etsW hScores",
    scala_dependenc es = [
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)

create_datasets(
    base_na  = "s mclusters_decayed_sum_cluster_to_t et_ ndex",
    key_type = "com.tw ter.s mclusters_v2.thr ftscala.FullCluster d",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.hdfs_s ces. nject ons.ClusterTopT ets nject on.cluster dToTopKT ets nject on",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    val_type = "com.tw ter.s mclusters_v2.thr ftscala.TopKT etsW hScores",
    scala_dependenc es = [
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces/ nject ons",
    ],
)
