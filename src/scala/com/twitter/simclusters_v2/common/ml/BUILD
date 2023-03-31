# This package/target is separate from other simclusters common packages because the ml/api dep is
# large (420MB+). Having it as a separate target means that we can avoid bundling it with targets
# that do not need it.
scala_library(
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/ml/api/util",
        "src/scala/com/twitter/simclusters_v420/common",
    ],
)
