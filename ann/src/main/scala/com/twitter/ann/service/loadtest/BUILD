scala_library(
    name = "loadtest",
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "ann/src/main/resources",
        "ann/src/main/scala/com/twitter/ann/annoy",
        "ann/src/main/scala/com/twitter/ann/common",
        "ann/src/main/scala/com/twitter/ann/faiss",
        "ann/src/main/scala/com/twitter/ann/hnsw",
        "ann/src/main/scala/com/twitter/ann/util",
        "ann/src/main/thrift/com/twitter/ann/common:ann-common-scala",
        "finatra/inject/inject-server/src/main/scala",
        "src/scala/com/twitter/cortex/ml/embeddings/common:Helpers",
        "twitter-server-internal/src/main/scala",
        "util/util-logging/src/main/scala",
    ],
)

jvm_binary(
    name = "bin",
    basename = "ann-loadtest",
    main = "com.twitter.ann.service.loadtest.AnnLoadTestMain",
    runtime_platform = "java11",
    dependencies = [
        ":loadtest",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
        "twitter-server/slf4j-jdk14/src/main/scala",
    ],
)
