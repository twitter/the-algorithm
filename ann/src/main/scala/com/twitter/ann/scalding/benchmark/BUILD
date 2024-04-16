scala_l brary(
    na  = "benchmark",
    s ces = ["*.scala"],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":user_ em_knn-scala",
        "3rdparty/src/jvm/com/tw ter/scald ng:args",
        "3rdparty/src/jvm/com/tw ter/scald ng:core",
        "3rdparty/src/jvm/com/tw ter/scald ng:date",
        "ann/src/ma n/scala/com/tw ter/ann/common",
        "ann/src/ma n/scala/com/tw ter/ann/scald ng/offl ne",
        "src/scala/com/tw ter/scald ng_ nternal/dalv2",
        "src/scala/com/tw ter/scald ng_ nternal/job",
        "src/scala/com/tw ter/scald ng_ nternal/job/analyt cs_batch",
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format",
    ],
)

hadoop_b nary(
    na  = "benchmark-adhoc",
    ma n = "com.tw ter.scald ng.Tool",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":benchmark",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
    ],
)

create_datasets(
    base_na  = "user_ em_knn",
    descr pt on = "L st of t  top recom ndat ons per search ent y (user)",
    java_sc ma = "com.tw ter.ann.knn.thr ftjava.Knn",
    platform = "java8",
    role = "cortex-mlx",
    scala_sc ma = "com.tw ter.ann.knn.thr ftscala.Knn",
    seg nt_type = "part  oned",
    tags = ["bazel-compat ble"],
    java_dependenc es = [
        "ann/src/ma n/thr ft/com/tw ter/ann/knn:thr ft-java",
    ],
    scala_dependenc es = [
        "ann/src/ma n/thr ft/com/tw ter/ann/knn:thr ft-scala",
    ],
)
