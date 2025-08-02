scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    scalac_plugins = ["no-roomba"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/candidate_source/ads",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/decorator/urt",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/candidate/ads",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/query/ads",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/candidate",
    ],
    etwitterports = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/candidate_source/ads",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/candidate/ads",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/query/ads",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/candidate",
    ],
)
