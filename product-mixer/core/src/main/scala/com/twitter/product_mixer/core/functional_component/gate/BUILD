scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common/presentation",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/pipeline_failure",
        "stitch/stitch-core",
    ],
    exports = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common/presentation",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/pipeline_failure",
        "stitch/stitch-core",
    ],
)
