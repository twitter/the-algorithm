scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/lihaoyi:pprint",
        "3rdparty/jvm/javax/inject:javax.inject",
        "configapi/configapi-core",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller/response/urp",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/urp",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/product",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/product",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/product/registry",
        "stitch/stitch-core",
        "util/util-core:scala",
    ],
)
