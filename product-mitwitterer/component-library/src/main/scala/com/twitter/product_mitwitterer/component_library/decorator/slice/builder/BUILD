scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/candidate",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/decorator/slice/builder",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline",
    ],
    etwitterports = [
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/candidate",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/decorator/slice/builder",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline",
    ],
)
