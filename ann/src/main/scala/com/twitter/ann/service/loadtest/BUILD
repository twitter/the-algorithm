scala_l brary(
    na  = "loadtest",
    s ces = ["*.scala"],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    tags = ["bazel-compat ble"],
    dependenc es = [
        "ann/src/ma n/res ces",
        "ann/src/ma n/scala/com/tw ter/ann/annoy",
        "ann/src/ma n/scala/com/tw ter/ann/common",
        "ann/src/ma n/scala/com/tw ter/ann/fa ss",
        "ann/src/ma n/scala/com/tw ter/ann/hnsw",
        "ann/src/ma n/scala/com/tw ter/ann/ut l",
        "ann/src/ma n/thr ft/com/tw ter/ann/common:ann-common-scala",
        "f natra/ nject/ nject-server/src/ma n/scala",
        "src/scala/com/tw ter/cortex/ml/embedd ngs/common: lpers",
        "tw ter-server- nternal/src/ma n/scala",
        "ut l/ut l-logg ng/src/ma n/scala",
    ],
)

jvm_b nary(
    na  = "b n",
    basena  = "ann-loadtest",
    ma n = "com.tw ter.ann.serv ce.loadtest.AnnLoadTestMa n",
    runt  _platform = "java11",
    dependenc es = [
        ":loadtest",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
        "tw ter-server/slf4j-jdk14/src/ma n/scala",
    ],
)
