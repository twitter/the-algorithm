scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature",
        "src/scala/com/twitter/ml/api/util:datarecord",
        "src/scala/com/twitter/ml/featurestore/lib/data",
        "src/scala/com/twitter/ml/featurestore/lib/dynamic",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature",
        "src/scala/com/twitter/ml/api/util:datarecord",
        "src/scala/com/twitter/ml/featurestore/lib/data",
        "src/scala/com/twitter/ml/featurestore/lib/dynamic",
    ],
)
