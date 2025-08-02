scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling/response/urt/reaction",
        "src/thrift/com/twitter/timelines/render:thrift-scala",
    ],
    etwitterports = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling/response/urt/reaction",
        "src/thrift/com/twitter/timelines/render:thrift-scala",
    ],
)
