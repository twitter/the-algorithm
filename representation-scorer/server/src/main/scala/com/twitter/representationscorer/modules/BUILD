scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle-internal/mtls/src/main/scala/com/twitter/finagle/mtls/authentication",
        "finagle/finagle-stats",
        "finatra/inject/inject-core/src/main/scala",
        "representation-manager/client/src/main/scala/com/twitter/representation_manager",
        "representation-manager/client/src/main/scala/com/twitter/representation_manager/config",
        "representation-manager/server/src/main/scala/com/twitter/representation_manager/migration",
        "representation-scorer/server/src/main/scala/com/twitter/representationscorer/common",
        "servo/util",
        "src/scala/com/twitter/simclusters_v2/stores",
        "src/scala/com/twitter/storehaus_internal/memcache",
        "src/scala/com/twitter/storehaus_internal/util",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)
