scala_library(
    name = "socialproof_features",
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/ibm/icu:icu4j",
        "src/java/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/ml/api/util",
        "src/scala/com/twitter/timelines/util",
        "src/thrift/com/twitter/dal/personal_data:personal_data-java",
        "src/thrift/com/twitter/ml/api:data-java",
        "src/thrift/com/twitter/timelines/socialproof:socialproof-scala",
    ],
)
