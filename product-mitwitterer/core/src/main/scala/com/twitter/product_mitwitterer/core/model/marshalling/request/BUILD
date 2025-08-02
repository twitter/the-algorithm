scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/fastertwitterml/jackson:jackson-module-scala",
        "3rdparty/jvm/com/fastertwitterml/jackson/core:jackson-annotations",
        "configapi/configapi-core/src/main/scala/com/twitter/timelines/configapi",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common/identifier",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/pipeline_failure",
        "util/util-core:util-core-util",
    ],
    etwitterports = [
        "3rdparty/jvm/com/fastertwitterml/jackson:jackson-module-scala",
        "3rdparty/jvm/com/fastertwitterml/jackson/core:jackson-annotations",
        "configapi/configapi-core/src/main/scala/com/twitter/timelines/configapi",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling",
        "util/util-core:util-core-util",
    ],
)
