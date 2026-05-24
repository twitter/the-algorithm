scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/fasterxml/jackson:jackson-module-scala",
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-annotations",
        "configapi/configapi-core/src/main/scala/com/twitter/timelines/configapi",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common/identifier",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/pipeline_failure",
        "util/util-core:util-core-util",
    ],
    exports = [
        "3rdparty/jvm/com/fasterxml/jackson:jackson-module-scala",
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-annotations",
        "configapi/configapi-core/src/main/scala/com/twitter/timelines/configapi",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling",
        "util/util-core:util-core-util",
    ],
)
