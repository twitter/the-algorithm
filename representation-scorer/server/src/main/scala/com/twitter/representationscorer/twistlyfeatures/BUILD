scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/github/ben-manes/caffeine",
        "finatra/inject/inject-core/src/main/scala",
        "representation-scorer/server/src/main/scala/com/twitter/representationscorer/common",
        "representation-scorer/server/src/main/scala/com/twitter/representationscorer/scorestore",
        "representation-scorer/server/src/main/thrift:thrift-scala",
        "src/thrift/com/twitter/twistly:twistly-scala",
        "stitch/stitch-core",
        "stitch/stitch-core:cache",
        "strato/config/columns/recommendations/twistly:twistly-strato-client",
        "strato/config/columns/recommendations/user-signal-service:user-signal-service-strato-client",
        "strato/src/main/scala/com/twitter/strato/client",
        "user-signal-service/thrift/src/main/thrift:thrift-scala",
        "util/util-core",
    ],
)
