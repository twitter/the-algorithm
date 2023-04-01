scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/inject:guice",
        "3rdparty/jvm/net/codingwell:scala-guice",
        "3rdparty/jvm/org/slf4j:slf4j-api",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/clients/cache",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/clients/strato",
        "follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations/configapi/deciders",
        "stitch/stitch-core",
        "strato/src/main/scala/com/twitter/strato/client",
        "user-signal-service/thrift/src/main/thrift:thrift-scala",
    ],
)
