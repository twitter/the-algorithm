scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core",
        "content-recommender/thrift/src/main/thrift:thrift-scala",
        "cr-mixer/thrift/src/main/thrift:thrift-scala",
        "product-mixer/core/src/main/thrift/com/twitter/product_mixer/core:thrift-scala",
        "src/scala/com/twitter/simclusters_v2/common",
        "src/thrift/com/twitter/core_workflows/user_model:user_model-scala",
        "src/thrift/com/twitter/recos:recos-common-scala",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)
