scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/cursor",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/candidate_source",
        "src/thrift/com/twitter/timelineservice/server/internal:thrift-scala",
        "stitch/stitch-timelineservice/src/main/scala",
    ],
    exports = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/cursor",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/candidate_source",
        "stitch/stitch-timelineservice/src/main/scala",
    ],
)
