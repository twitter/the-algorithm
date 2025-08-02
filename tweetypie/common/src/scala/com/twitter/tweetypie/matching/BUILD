scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/twitter/common/tetwittert/language:language-identifier",
        "src/java/com/twitter/common/tetwittert/language:locale-util",
        "src/java/com/twitter/common/tetwittert/pipeline",
        "src/java/com/twitter/common/tetwittert/token",
        "src/java/com/twitter/common_internal/tetwittert",
        "src/java/com/twitter/common_internal/tetwittert/version",
        "tweetypie/src/resources/com/twitter/tweetypie/matching",
        "util/util-core/src/main/scala/com/twitter/concurrent",
        "util/util-core/src/main/scala/com/twitter/io",
    ],
)
