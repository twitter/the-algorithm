scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/X/bijection:core",
        "3rdparty/jvm/com/X/bijection:scrooge",
        "ann/src/main/scala/com/X/ann/common",
        "src/scala/com/X/ml/api/embedding",
        "storage/clients/manhattan",
    ],
)
