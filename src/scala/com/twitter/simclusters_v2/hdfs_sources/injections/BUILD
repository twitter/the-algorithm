scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/common",
        "src/thrift/com/twitter/hermit/candidate:hermit-candidate-scala",
        "src/thrift/com/twitter/ml/api:embedding-scala",
        "src/thrift/com/twitter/recos/entities:entities-thrift-scala",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)
