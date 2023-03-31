scala_library(
    name = "interests_service",
    sources = ["InterestServiceClient.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "frigate/frigate-common/src/main/scala/com/twitter/frigate/common/store/interests",
        "interests-service/thrift/src/main/thrift:thrift-scala",
        "strato/src/main/scala/com/twitter/strato/catalog",
        "strato/src/main/scala/com/twitter/strato/client",
        "strato/src/main/scala/com/twitter/strato/data",
        "strato/src/main/scala/com/twitter/strato/thrift",
    ],
)
