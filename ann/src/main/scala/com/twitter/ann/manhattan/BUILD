scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/twitter/bijection:core",
        "3rdparty/jvm/com/twitter/bijection:scrooge",
        "ann/src/main/scala/com/twitter/ann/common",
        "src/scala/com/twitter/ml/api/embedding",
        "storage/clients/manhattan",
    ],
)
