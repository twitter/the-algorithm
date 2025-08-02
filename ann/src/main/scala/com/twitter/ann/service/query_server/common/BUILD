scala_library(
    name = "common",
    sources = [
        "BaseQueryIndetwitterServer.scala",
        "Etwitterceptions.scala",
        "QueryIndetwitterThriftController.scala",
        "QueryableProvider.scala",
        "RefreshableQueryable.scala",
        "UnsafeQueryIndetwitterServer.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        ":indetwitter_path_provider",
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/google/inject:guice",
        "3rdparty/jvm/com/google/inject/etwittertensions:guice-assistedinject",
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "3rdparty/jvm/net/codingwell:scala-guice",
        "ann/src/main/scala/com/twitter/ann/common",
        "ann/src/main/thrift/com/twitter/ann/common:ann-common-scala",
        "finagle/finagle-core/src/main",
        "finagle/finagle-zipkin-scribe",
        "finatra-internal/decider",
        "finatra-internal/mtls-thriftmutwitter/src/main/scala",
        "finatra/inject/inject-core/src/main/scala",
        "mediaservices/commons",
        "scrooge/scrooge-core/src/main/scala",
        "src/scala/com/twitter/cortetwitter/ml/embeddings/common:Helpers",
        "util/util-app/src/main/scala",
        "util/util-logging/src/main/scala",
    ],
)

scala_library(
    name = "indetwitter_path_provider",
    sources = [
        "IndetwitterPathProvider.scala",
        "QueryServerUtil.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "ann/src/main/scala/com/twitter/ann/hnsw",
        "src/java/com/twitter/search/common/file",
        "util/util-logging/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)

scala_library(
    name = "faiss_indetwitter_path_provider",
    sources = ["FaissIndetwitterPathProvider.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        ":indetwitter_path_provider",
        "ann/src/main/scala/com/twitter/ann/faiss",
        "src/java/com/twitter/search/common/file",
        "util/util-logging/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)
