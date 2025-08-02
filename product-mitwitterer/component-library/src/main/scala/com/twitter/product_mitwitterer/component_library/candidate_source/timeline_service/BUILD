scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/cursor",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source",
        "src/thrift/com/twitter/timelineservice/server/internal:thrift-scala",
        "stitch/stitch-timelineservice/src/main/scala",
    ],
    etwitterports = [
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/cursor",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source",
        "stitch/stitch-timelineservice/src/main/scala",
    ],
)
