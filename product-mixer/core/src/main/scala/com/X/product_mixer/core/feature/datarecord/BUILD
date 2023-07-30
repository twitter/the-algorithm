scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/core/src/main/scala/com/X/product_mixer/core/feature",
        "src/java/com/X/ml/api:api-base",
        "src/scala/com/X/ml/api/util:datarecord",
        "src/thrift/com/X/dal/personal_data:personal_data-java",
        "src/thrift/com/X/ml/api:data-java",
        "src/thrift/com/X/ml/api:data-scala",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/X/product_mixer/core/feature",
        "src/thrift/com/X/dal/personal_data:personal_data-java",
        "src/thrift/com/X/ml/api:data-java",
        "src/thrift/com/X/ml/api:data-scala",
    ],
)
