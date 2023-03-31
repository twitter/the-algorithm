scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    scalac_plugins = ["no-roomba"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/candidate_source/ads",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/decorator/urt",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate/ads",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/query/ads",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/candidate",
    ],
    exports = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/candidate_source/ads",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate/ads",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/query/ads",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/candidate",
    ],
)
