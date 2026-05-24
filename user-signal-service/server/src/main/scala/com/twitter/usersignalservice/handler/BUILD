scala_library(
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "src/scala/com/twitter/twistly/common",
        "src/scala/com/twitter/twistly/store",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
        "strato/src/main/scala/com/twitter/strato/fed",
        "strato/src/main/scala/com/twitter/strato/fed/server",
        "user-signal-service/server/src/main/scala/com/twitter/usersignalservice/config",
        "user-signal-service/server/src/main/scala/com/twitter/usersignalservice/module",
        "user-signal-service/thrift/src/main/thrift:thrift-scala",
    ],
)
