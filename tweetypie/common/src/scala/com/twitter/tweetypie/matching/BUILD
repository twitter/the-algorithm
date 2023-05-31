scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/twitter/common/text/language:language-identifier",
        "src/java/com/twitter/common/text/language:locale-util",
        "src/java/com/twitter/common/text/pipeline",
        "src/java/com/twitter/common/text/token",
        "src/java/com/twitter/common_internal/text",
        "src/java/com/twitter/common_internal/text/version",
        "tweetypie/src/resources/com/twitter/tweetypie/matching",
        "util/util-core/src/main/scala/com/twitter/concurrent",
        "util/util-core/src/main/scala/com/twitter/io",
    ],
)
