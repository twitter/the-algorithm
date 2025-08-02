scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/lihaoyi:pprint",
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "configapi/configapi-core",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/marshaller/response/urp",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling/response/urp",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/product",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/product",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/product/registry",
        "stitch/stitch-core",
        "util/util-core:scala",
    ],
)
