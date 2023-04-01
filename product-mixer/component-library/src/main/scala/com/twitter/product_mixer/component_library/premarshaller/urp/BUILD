scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/premarshaller/urp/builder",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/premarshaller/urt/builder",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/premarshaller",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/selector",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/urp",
    ],
    exports = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/premarshaller/urp/builder",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/premarshaller",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/selector",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/urp",
    ],
)
