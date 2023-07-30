scala_library(
    name = "interests_service",
    sources = ["InterestServiceClient.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "frigate/frigate-common/src/main/scala/com/X/frigate/common/store/interests",
        "interests-service/thrift/src/main/thrift:thrift-scala",
        "strato/src/main/scala/com/X/strato/catalog",
        "strato/src/main/scala/com/X/strato/client",
        "strato/src/main/scala/com/X/strato/data",
        "strato/src/main/scala/com/X/strato/thrift",
    ],
)
