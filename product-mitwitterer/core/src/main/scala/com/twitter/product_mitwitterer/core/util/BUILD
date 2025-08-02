scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-core/src/main",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/pipeline_failure",
        "servo/util",
        "snowflake:id",
        "stitch/stitch-core",
        "util/util-core:util-core-util",
        "util/util-stats",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/pipeline_failure",
        "servo/util",
        "snowflake:id",
        "stitch/stitch-core",
        "util/util-core:util-core-util",
        "util/util-stats",
    ],
)
