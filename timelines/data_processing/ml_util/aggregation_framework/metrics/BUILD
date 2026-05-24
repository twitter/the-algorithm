scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/twitter/algebird:core",
        "src/java/com/twitter/ml/api:api-base",
        "src/java/com/twitter/ml/api/constant",
        "src/scala/com/twitter/ml/api/util:datarecord",
        "src/thrift/com/twitter/dal/personal_data:personal_data-java",
        "src/thrift/com/twitter/ml/api:data-java",
        "src/thrift/com/twitter/ml/api:interpretable-model-java",
        "util/util-core:scala",
    ],
)
