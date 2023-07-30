# This package/target is separate from other simclusters common packages because the ml/api dep is
# large (350MB+). Having it as a separate target means that we can avoid bundling it with targets
# that do not need it.
scala_library(
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/X/ml/api:api-base",
        "src/scala/com/X/ml/api/util",
        "src/scala/com/X/simclusters_v2/common",
    ],
)
