scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "content-recommender/thrift/src/main/thrift:thrift-scala",
        "finatra/inject/inject-core/src/main/scala",
        "representation-scorer/server/src/main/scala/com/twitter/representationscorer/common",
        "representation-scorer/server/src/main/scala/com/twitter/representationscorer/modules",
        "representation-scorer/server/src/main/scala/com/twitter/representationscorer/scorestore",
        "representation-scorer/server/src/main/scala/com/twitter/representationscorer/twistlyfeatures",
        "representation-scorer/server/src/main/thrift:thrift-scala",
        "strato/src/main/scala/com/twitter/strato/fed",
        "strato/src/main/scala/com/twitter/strato/fed/server",
    ],
)
