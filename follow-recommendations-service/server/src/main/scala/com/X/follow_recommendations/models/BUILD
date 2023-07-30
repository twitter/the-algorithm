scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core/src/main/scala/com/X/timelines/configapi",
        "follow-recommendations-service/common/src/main/scala/com/X/follow_recommendations/common/feature_hydration/common",
        "follow-recommendations-service/common/src/main/scala/com/X/follow_recommendations/common/models",
        "follow-recommendations-service/server/src/main/scala/com/X/follow_recommendations/assembler/models",
        "follow-recommendations-service/thrift/src/main/thrift:thrift-scala",
        "product-mixer/core/src/main/scala/com/X/product_mixer/core/model/marshalling/request",
    ],
)
