scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/core/src/main/scala/com/X/product_mixer/core/feature",
        "src/scala/com/X/ml/api/util:datarecord",
        "src/scala/com/X/ml/featurestore/lib/data",
        "src/scala/com/X/ml/featurestore/lib/dynamic",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/X/product_mixer/core/feature",
        "src/scala/com/X/ml/api/util:datarecord",
        "src/scala/com/X/ml/featurestore/lib/data",
        "src/scala/com/X/ml/featurestore/lib/dynamic",
    ],
)
