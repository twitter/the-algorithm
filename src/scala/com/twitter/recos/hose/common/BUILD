scala_library(
    sources = ["*.scala"],
    strict_deps = False,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/twitter/graphjet",
        "3rdparty/jvm/org/apache/kafka:rosette-kafka",
        "finagle/finagle-stats/src/main/scala",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "kafka/libs/src/main/scala/com/twitter/kafka/client/processor",
        "servo/repo/src/main/scala",
        "src/scala/com/twitter/recos/util:recos-util",
        "src/thrift/com/twitter/recos:recos-internal-scala",
    ],
)
