scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature",
        "src/scala/com/twitter/ml/api/util:datarecord",
        "src/scala/com/twitter/ml/featurestore/lib/data",
        "src/scala/com/twitter/ml/featurestore/lib/dynamic",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature",
        "src/scala/com/twitter/ml/api/util:datarecord",
        "src/scala/com/twitter/ml/featurestore/lib/data",
        "src/scala/com/twitter/ml/featurestore/lib/dynamic",
    ],
)
