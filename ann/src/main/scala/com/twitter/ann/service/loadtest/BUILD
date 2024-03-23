scala_library(
    name = "loadtest",
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "ann/src/main/resources",
        "ann/src/main/scala/com/ExTwitter/ann/annoy",
        "ann/src/main/scala/com/ExTwitter/ann/common",
        "ann/src/main/scala/com/ExTwitter/ann/faiss",
        "ann/src/main/scala/com/ExTwitter/ann/hnsw",
        "ann/src/main/scala/com/ExTwitter/ann/util",
        "ann/src/main/thrift/com/ExTwitter/ann/common:ann-common-scala",
        "finatra/inject/inject-server/src/main/scala",
        "src/scala/com/ExTwitter/cortex/ml/embeddings/common:Helpers",
        "ExTwitter-server-internal/src/main/scala",
        "util/util-logging/src/main/scala",
    ],
)

jvm_binary(
    name = "bin",
    basename = "ann-loadtest",
    main = "com.ExTwitter.ann.service.loadtest.AnnLoadTestMain",
    runtime_platform = "java11",
    dependencies = [
        ":loadtest",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
        "ExTwitter-server/slf4j-jdk14/src/main/scala",
    ],
)
