scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    scalac_plugins = ["no-roomba"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "ads-injection/lib/src/main/scala/com/twitter/goldfinch/model",
        "ads-injection/lib/src/main/scala/com/twitter/goldfinch/model/promoted",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common",
        "src/thrift/com/twitter/ads/adserver:adserver_common-scala",
    ],
    exports = [
        "ads-injection/lib/src/main/scala/com/twitter/goldfinch/model",
        "ads-injection/lib/src/main/scala/com/twitter/goldfinch/model/promoted",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common",
        "src/thrift/com/twitter/ads/adserver:adserver_common-scala",
    ],
)
