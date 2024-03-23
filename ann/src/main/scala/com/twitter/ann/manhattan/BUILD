scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/ExTwitter/bijection:core",
        "3rdparty/jvm/com/ExTwitter/bijection:scrooge",
        "ann/src/main/scala/com/ExTwitter/ann/common",
        "src/scala/com/ExTwitter/ml/api/embedding",
        "storage/clients/manhattan",
    ],
)
