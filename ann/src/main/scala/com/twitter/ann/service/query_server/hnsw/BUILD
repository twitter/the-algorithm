scala_l brary(
    na  = "server",
    s ces = ["*.scala"],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    tags = ["bazel-compat ble"],
    dependenc es = [
        "ann/src/ma n/scala/com/tw ter/ann/common",
        "ann/src/ma n/scala/com/tw ter/ann/hnsw",
        "ann/src/ma n/scala/com/tw ter/ann/serv ce/query_server/common",
        "ann/src/ma n/scala/com/tw ter/ann/serv ce/query_server/common/warmup",
        "src/java/com/tw ter/search/common/f le",
    ],
)

jvm_b nary(
    na  = "hnsw-query-server",
    ma n = "com.tw ter.ann.serv ce.query_server.hnsw.HnswQuery ndexServer",
    comp ler_opt on_sets = ["fatal_warn ngs"],
    runt  _platform = "java11",
    tags = [
        "bazel-compat ble",
    ],
    dependenc es = [
        ":server",
        "3rdparty/jvm/ch/qos/logback:logback-class c",
        "3rdparty/jvm/org/slf4j:jcl-over-slf4j",
        "3rdparty/jvm/org/slf4j:jul-to-slf4j",
        "3rdparty/jvm/org/slf4j:log4j-over-slf4j",
        "ann/src/ma n/res ces",
        "loglens/loglens-logback/src/ma n/scala/com/tw ter/loglens/logback",
    ],
)
