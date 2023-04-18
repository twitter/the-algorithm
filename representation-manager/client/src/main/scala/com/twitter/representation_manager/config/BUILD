scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finatra/inject/inject-thrift-client",
        "representation-manager/server/src/main/scala/com/twitter/representation_manager/common",
        "representation-manager/server/src/main/thrift:thrift-scala",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
        "strato/src/main/scala/com/twitter/strato/client",
    ],
)
