scala_library(
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/twitter/storehaus:core",
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate/ads",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/query/ads",
        "src/thrift/com/twitter/ads/adserver:ads_shared_types-scala",
    ],
    exports = [
        "3rdparty/jvm/com/twitter/storehaus:core",
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate/ads",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/query/ads",
        "src/thrift/com/twitter/ads/adserver:ads_shared_types-scala",
    ],
)
