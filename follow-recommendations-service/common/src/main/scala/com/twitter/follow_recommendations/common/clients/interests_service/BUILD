scala_library(
    name = "interests_service",
    sources = ["InterestServiceClient.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "frigate/frigate-common/src/main/scala/com/ExTwitter/frigate/common/store/interests",
        "interests-service/thrift/src/main/thrift:thrift-scala",
        "strato/src/main/scala/com/ExTwitter/strato/catalog",
        "strato/src/main/scala/com/ExTwitter/strato/client",
        "strato/src/main/scala/com/ExTwitter/strato/data",
        "strato/src/main/scala/com/ExTwitter/strato/thrift",
    ],
)
