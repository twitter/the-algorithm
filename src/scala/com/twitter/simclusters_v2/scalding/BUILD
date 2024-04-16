scala_l brary(
    s ces = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "3rdparty/jvm/com/fasterxml/jackson:jackson-module-scala",
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-core",
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-datab nd",
        "3rdparty/jvm/com/fasterxml/jackson/module:jackson-module-scala",
        "3rdparty/jvm/com/googlecode/matr x-toolk s-java",
        "3rdparty/jvm/com/tw ter/storehaus:algebra",
        "3rdparty/jvm/com/tw ter/storehaus:core",
        "esc rb rd/src/scala/com/tw ter/esc rb rd/scald ng/s ce",
        "flockdb-tools/datasets/flock:flock-follows-edges-scala",
        "src/java/com/tw ter/ml/ap /constant",
        "src/java/com/tw ter/sbf/core",
        "src/java/com/tw ter/sbf/graph",
        "src/scala/com/tw ter/fr gate/user_sampler/common",
        "src/scala/com/tw ter/ml/ap :ap -base",
        "src/scala/com/tw ter/ml/ap /bq",
        "src/scala/com/tw ter/pluck/s ce/cassowary:s ms",
        "src/scala/com/tw ter/pluck/s ce/core_workflows/user_model:condensed_user_state-scala",
        "src/scala/com/tw ter/scald ng_ nternal/dalv2",
        "src/scala/com/tw ter/scald ng_ nternal/job",
        "src/scala/com/tw ter/scald ng_ nternal/job/analyt cs_batch",
        "src/scala/com/tw ter/scald ng_ nternal/s ce",
        "src/scala/com/tw ter/scald ng_ nternal/s ce/lzo_scrooge",
        "src/scala/com/tw ter/s mclusters_v2/cand date_s ce",
        "src/scala/com/tw ter/s mclusters_v2/hdfs_s ces",
        "src/scala/com/tw ter/s mclusters_v2/scald ng/common",
        "src/scala/com/tw ter/s mclusters_v2/summ ngb rd/common",
        "src/scala/com/tw ter/t  l nes/pred ct on/features/common",
        "src/scala/com/tw ter/t  l nes/pred ct on/features/ l",
        "src/scala/com/tw ter/t  l nes/pred ct on/features/recap",
        "src/scala/com/tw ter/wtf/ent y_real_graph/scald ng/common",
        "src/thr ft/com/tw ter/ rm /cand date: rm -cand date-scala",
        "src/thr ft/com/tw ter/wtf/scald ng/s ms:s ms-thr ft-scala",
        "twadoop_conf g/conf gurat on/log_categor es/group/recos-platform:content_recom nder_get_content_recom ndat ons-scala",
        "twadoop_conf g/conf gurat on/log_categor es/group/recos-platform:content_recom nder_get_top c_t ets_recom ndat ons-scala",
        "twadoop_conf g/conf gurat on/log_categor es/group/t  l ne:t  l ne_serv ce_favor es-scala",
        "users ce/snapshot/src/ma n/scala/com/tw ter/users ce/snapshot/flat:users ce_flat-scala",
        "users ce/snapshot/src/ma n/thr ft/com/tw ter/users ce/snapshot/flat:flat-scala",
        "ut l/ut l-core:ut l-core-ut l",
    ],
)

hadoop_b nary(
    na  = "evd_cluster_s m lar y",
    ma n = "com.tw ter.s mclusters_v2.scald ng.E genVectorsForClusterS m lar yAdhoc",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "cluster_evaluat on",
    ma n = "com.tw ter.s mclusters_v2.scald ng.ClusterEvaluat onAdhoc",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "cluster_evaluat on_20m_145k",
    ma n = "com.tw ter.s mclusters_v2.scald ng.ClusterEvaluat onFor20M145K",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "cluster_evaluat on_20m_145k_2020",
    ma n = "com.tw ter.s mclusters_v2.scald ng.ClusterEvaluat onFor20M145K2020",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "bp_cluster_evaluat on",
    ma n = "com.tw ter.s mclusters_v2.scald ng.B part eClusterEvaluat on",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "update_knownfor",
    ma n = "com.tw ter.s mclusters_v2.scald ng.UpdateKnownForAdhoc",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "update_knownfor_prod",
    ma n = "com.tw ter.s mclusters_v2.scald ng.UpdateKnownFor20M145K",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "cluster_deta ls",
    ma n = "com.tw ter.s mclusters_v2.scald ng.ClusterDeta lsBatch",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "cluster_deta ls_20m_145k_updated",
    ma n = "com.tw ter.s mclusters_v2.scald ng.ClusterDeta ls20M145KUpdated",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "cluster_deta ls_20m_145k_2020",
    ma n = "com.tw ter.s mclusters_v2.scald ng.ClusterDeta ls20M145K2020",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "cluster_deta ls-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.ClusterDeta lsAdhoc",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "cluster_deta ls-dump",
    ma n = "com.tw ter.s mclusters_v2.scald ng.DumpClusterDeta lsAdhoc",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = " nterested_ n",
    ma n = "com.tw ter.s mclusters_v2.scald ng. nterested nFromKnownForBatch",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = " nterested_ n_from_producer_embedd ngs",
    ma n = "com.tw ter.s mclusters_v2.scald ng. nterested nFromProducerEmbedd ngsBatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "employee_graph_from_user_user",
    ma n = "com.tw ter.s mclusters_v2.scald ng.EmployeeGraphFromUserUser",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = " nterested_ n_20m_145k_updated",
    ma n = "com.tw ter.s mclusters_v2.scald ng. nterested nFromKnownFor20M145KUpdated",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = " nterested_ n_20m_145k_2020",
    ma n = "com.tw ter.s mclusters_v2.scald ng. nterested nFromKnownFor20M145K2020",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = " nterested_ n_l e_20m_145k_2020",
    ma n = "com.tw ter.s mclusters_v2.scald ng. nterested nFromKnownForL e20M145K2020",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = " nterested_ n_l e_20m_145k_2020-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng. nterested nFromKnownForL e20M145K2020Adhoc",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = " nterested_ n_from_ape_2020-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng. nterested nFromAPE2020AdhocApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = " nterested_ n_from_ape_2020",
    ma n = "com.tw ter.s mclusters_v2.scald ng. nterested nFromAPE2020BatchApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "known_for_to_mh",
    ma n = "com.tw ter.s mclusters_v2.scald ng.KnownForToMHBatch",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "user_user_normal zed_graph",
    ma n = "com.tw ter.s mclusters_v2.scald ng.UserUserNormal zedGraphBatch",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "user_user_graph",
    ma n = "com.tw ter.s mclusters_v2.scald ng.UserUserGraphBatch",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "user_user_graph-adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.UserUserGraphAdhoc",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "producer_norms_and_counts",
    ma n = "com.tw ter.s mclusters_v2.scald ng.ProducerNormsAndCountsBatch",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "fav_graph",
    ma n = "com.tw ter.s mclusters_v2.scald ng.UserUserFavGraphBatch",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "top_users_s m lar y_graph",
    ma n = "com.tw ter.s mclusters_v2.scald ng.TopUsersS m lar yGraphApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "top_users_only",
    ma n = "com.tw ter.s mclusters_v2.scald ng.TopUsersOnlyApp",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

hadoop_b nary(
    na  = "dump_fav_graph_adhoc",
    ma n = "com.tw ter.s mclusters_v2.scald ng.DumpFavGraphAdhoc",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)

# Generated w h `capesospy-v2 create_target  nterested_ n_for_20M_145k_2020 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml`, conf g hash 8f19bf.
scald ng_job(
    na  = " nterested_ n_for_20M_145k_2020",
    ma n = "com.tw ter.s mclusters_v2.scald ng. nterested nFromKnownFor20M145K2020",
    args = ["--soc alProofThreshold 2 --maxClustersPerUser 50"],
    conf g = [
        ("hadoop.comb ne- nput", "true"),
        ("hadoop.map.jvm.total- mory", "3072m"),
        ("hadoop.reduce.jvm.total- mory", "3072m"),
        ("hadoop.subm ter.jvm.total- mory", "5120m"),
        ("subm ter.t er", "preempt ble"),
    ],
    cron = "14 * * * *",
    hadoop_cluster = "atla-proc",
    platform = "java8",
    role = "cassowary",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":scald ng",
    ],
)
