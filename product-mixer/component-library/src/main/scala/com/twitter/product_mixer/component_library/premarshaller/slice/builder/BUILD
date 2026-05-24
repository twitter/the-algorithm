scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/cursor",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/premarshaller/cursor",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common/presentation/slice",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/slice",
    ],
    exports = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/cursor",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/premarshaller/cursor",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common/presentation/slice",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/slice",
    ],
)
