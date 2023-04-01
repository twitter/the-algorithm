scala_library(
    name = "common",
    sources = [
        "BaseQueryIndexServer.scala",
        "Exceptions.scala",
        "QueryIndexThriftController.scala",
        "QueryableProvider.scala",
        "RefreshableQueryable.scala",
        "UnsafeQueryIndexServer.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        ":index_path_provider",
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/google/inject:guice",
        "3rdparty/jvm/com/google/inject/extensions:guice-assistedinject",
        "3rdparty/jvm/javax/inject:javax.inject",
        "3rdparty/jvm/net/codingwell:scala-guice",
        "ann/src/main/scala/com/twitter/ann/common",
        "ann/src/main/thrift/com/twitter/ann/common:ann-common-scala",
        "finagle/finagle-core/src/main",
        "finagle/finagle-zipkin-scribe",
        "finatra-internal/decider",
        "finatra-internal/mtls-thriftmux/src/main/scala",
        "finatra/inject/inject-core/src/main/scala",
        "mediaservices/commons",
        "scrooge/scrooge-core/src/main/scala",
        "src/scala/com/twitter/cortex/ml/embeddings/common:Helpers",
        "util/util-app/src/main/scala",
        "util/util-logging/src/main/scala",
    ],
)

scala_library(
    name = "index_path_provider",
    sources = [
        "IndexPathProvider.scala",
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
    name = "faiss_index_path_provider",
    sources = ["FaissIndexPathProvider.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        ":index_path_provider",
        "ann/src/main/scala/com/twitter/ann/faiss",
        "src/java/com/twitter/search/common/file",
        "util/util-logging/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)
