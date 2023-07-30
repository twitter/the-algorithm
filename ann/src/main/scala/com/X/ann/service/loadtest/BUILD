scala_library(
    name = "loadtest",
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "ann/src/main/resources",
        "ann/src/main/scala/com/X/ann/annoy",
        "ann/src/main/scala/com/X/ann/common",
        "ann/src/main/scala/com/X/ann/faiss",
        "ann/src/main/scala/com/X/ann/hnsw",
        "ann/src/main/scala/com/X/ann/util",
        "ann/src/main/thrift/com/X/ann/common:ann-common-scala",
        "finatra/inject/inject-server/src/main/scala",
        "src/scala/com/X/cortex/ml/embeddings/common:Helpers",
        "X-server-internal/src/main/scala",
        "util/util-logging/src/main/scala",
    ],
)

jvm_binary(
    name = "bin",
    basename = "ann-loadtest",
    main = "com.X.ann.service.loadtest.AnnLoadTestMain",
    runtime_platform = "java11",
    dependencies = [
        ":loadtest",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
        "X-server/slf4j-jdk14/src/main/scala",
    ],
)
