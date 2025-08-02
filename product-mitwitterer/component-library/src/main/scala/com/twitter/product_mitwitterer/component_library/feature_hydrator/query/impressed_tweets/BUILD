scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "3rdparty/src/jvm/com/twitter/storehaus:core",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/feature_hydrator",
        "src/thrift/com/twitter/timelines/impression_store:thrift-scala",
        "stitch/stitch-core",
    ],
    etwitterports = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "3rdparty/src/jvm/com/twitter/storehaus:core",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/feature_hydrator",
        "src/thrift/com/twitter/timelines/impression_store:thrift-scala",
        "stitch/stitch-core",
    ],
)
