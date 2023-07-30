scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "configapi/configapi-core/src/main/scala/com/X/timelines/configapi",
        "finagle/finagle-core/src/main",
        "follow-recommendations-service/common/src/main/scala/com/X/follow_recommendations/common/models",
        "hermit/hermit-core/src/main/scala/com/X/hermit/model",
        "product-mixer/core/src/main/scala/com/X/product_mixer/core/functional_component/candidate_source",
        "product-mixer/core/src/main/scala/com/X/product_mixer/core/pipeline/recommendation",
        "stitch/stitch-core",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/X/product_mixer/core/pipeline/recommendation",
    ],
)
