scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "3rdparty/src/jvm/com/twitter/storehaus:core",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/feature_hydrator",
        "src/thrift/com/twitter/timelines/impression_store:thrift-scala",
        "stitch/stitch-core",
    ],
    exports = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "3rdparty/src/jvm/com/twitter/storehaus:core",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/feature_hydrator",
        "src/thrift/com/twitter/timelines/impression_store:thrift-scala",
        "stitch/stitch-core",
    ],
)
