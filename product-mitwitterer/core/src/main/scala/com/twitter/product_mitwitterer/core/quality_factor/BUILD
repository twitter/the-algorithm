scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "finagle/finagle-mutwitter/src/main/scala",
        "finatra/inject/inject-core/src/main/scala",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common/identifier",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline:query",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/pipeline_failure",
        "servo/util",
        "stitch/stitch-core",
        "util/util-core",
    ],
)
