scala_library(
    name = "socialproof_features",
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/ibm/icu:icu4j",
        "src/java/com/X/ml/api:api-base",
        "src/scala/com/X/ml/api/util",
        "src/scala/com/X/timelines/util",
        "src/thrift/com/X/dal/personal_data:personal_data-java",
        "src/thrift/com/X/ml/api:data-java",
        "src/thrift/com/X/timelines/socialproof:socialproof-scala",
    ],
)
