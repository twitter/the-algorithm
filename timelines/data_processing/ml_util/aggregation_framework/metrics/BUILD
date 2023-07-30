scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/X/algebird:core",
        "src/java/com/X/ml/api:api-base",
        "src/java/com/X/ml/api/constant",
        "src/scala/com/X/ml/api/util:datarecord",
        "src/thrift/com/X/dal/personal_data:personal_data-java",
        "src/thrift/com/X/ml/api:data-java",
        "src/thrift/com/X/ml/api:interpretable-model-java",
        "util/util-core:scala",
    ],
)
