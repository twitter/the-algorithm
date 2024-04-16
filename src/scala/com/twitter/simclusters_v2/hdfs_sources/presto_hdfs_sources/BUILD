scala_l brary(
    s ces = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":data_s ces",
        "3rdparty/src/jvm/com/tw ter/scald ng:core",
        "src/scala/com/tw ter/scald ng_ nternal/dalv2",
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
        "src/scala/com/tw ter/scald ng_ nternal/s ce/lzo_scrooge",
        "src/scala/com/tw ter/s mclusters_v2/common",
        "src/thr ft/com/tw ter/s mclusters_v2:s mclusters_v2-thr ft-scala",
        "src/thr ft/com/tw ter/wtf/ent y_real_graph:ent y_real_graph-thr ft-scala",
    ],
)

scala_l brary(
    na  = "data_s ces",
    s ces = [],
    descr pt on = "DAL datasets   w sh to expose externally",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":reverse_ ndex_semant c_core_per_language_s mclusters_embedd ngs_presto-scala",
        ":semant c_core_per_language_s mclusters_embedd ngs_presto-scala",
        "src/scala/com/tw ter/s mclusters_v2/common",
    ],
)

create_datasets(
    base_na  = "reverse_ ndex_semant c_core_per_language_s mclusters_embedd ngs_presto",
    java_sc ma = "com.tw ter.s mclusters_v2.thr ftjava. nternal dEmbedd ngW h d",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.thr ftscala. nternal dEmbedd ngW h d",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    java_dependenc es = [
        "src/thr ft/com/tw ter/s mclusters_v2:s mclusters_v2-thr ft-java",
    ],
    scala_dependenc es = [
        "src/thr ft/com/tw ter/s mclusters_v2:s mclusters_v2-thr ft-scala",
    ],
)

create_datasets(
    base_na  = "semant c_core_per_language_s mclusters_embedd ngs_presto",
    java_sc ma = "com.tw ter.s mclusters_v2.thr ftjava.S mClustersEmbedd ngW h d",
    platform = "java8",
    role = "cassowary",
    scala_sc ma = "com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ngW h d",
    seg nt_type = "snapshot",
    tags = ["bazel-compat ble"],
    java_dependenc es = [
        "src/thr ft/com/tw ter/s mclusters_v2:s mclusters_v2-thr ft-java",
    ],
    scala_dependenc es = [
        "src/thr ft/com/tw ter/s mclusters_v2:s mclusters_v2-thr ft-scala",
    ],
)
