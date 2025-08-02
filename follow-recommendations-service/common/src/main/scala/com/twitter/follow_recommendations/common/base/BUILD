scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "configapi/configapi-core/src/main/scala/com/twitter/timelines/configapi",
        "finagle/finagle-core/src/main",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/models",
        "hermit/hermit-core/src/main/scala/com/twitter/hermit/model",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/recommendation",
        "stitch/stitch-core",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/recommendation",
    ],
)
