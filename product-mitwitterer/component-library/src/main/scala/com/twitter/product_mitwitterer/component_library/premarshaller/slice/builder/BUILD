scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/cursor",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/premarshaller/cursor",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common/presentation/slice",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling/response/slice",
    ],
    etwitterports = [
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/cursor",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/premarshaller/cursor",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common/presentation/slice",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling/response/slice",
    ],
)
