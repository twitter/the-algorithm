scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/decorator",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common/presentation",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/slice",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/decorator",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common/presentation",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/slice",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline",
    ],
)
