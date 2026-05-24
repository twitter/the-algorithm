scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature",
        "src/java/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/ml/api/util:datarecord",
        "src/thrift/com/twitter/dal/personal_data:personal_data-java",
        "src/thrift/com/twitter/ml/api:data-java",
        "src/thrift/com/twitter/ml/api:data-scala",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature",
        "src/thrift/com/twitter/dal/personal_data:personal_data-java",
        "src/thrift/com/twitter/ml/api:data-java",
        "src/thrift/com/twitter/ml/api:data-scala",
    ],
)
