scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/decorator/slice/builder",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/presentation/slice",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/decorator",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/decorator/slice/builder",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline",
    ],
    exports = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/decorator/slice/builder",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/presentation/slice",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/decorator",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/decorator/slice/builder",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline",
    ],
)
