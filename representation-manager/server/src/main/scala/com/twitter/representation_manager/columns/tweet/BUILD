scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finatra/inject/inject-core/src/main/scala",
        "representation-manager/server/src/main/scala/com/twitter/representation_manager/columns",
        "representation-manager/server/src/main/scala/com/twitter/representation_manager/modules",
        "representation-manager/server/src/main/scala/com/twitter/representation_manager/store",
        "representation-manager/server/src/main/thrift:thrift-scala",
        "strato/src/main/scala/com/twitter/strato/fed",
        "strato/src/main/scala/com/twitter/strato/fed/server",
    ],
)
