scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/side_effect",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common/identifier",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/pipeline_failure",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/service",
        "stitch/stitch-core",
    ],
    etwitterports = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/side_effect",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common/identifier",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/pipeline_failure",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/service",
        "stitch/stitch-core",
    ],
)
