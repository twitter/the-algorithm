scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/X/common/text/language:language-identifier",
        "src/java/com/X/common/text/language:locale-util",
        "src/java/com/X/common/text/pipeline",
        "src/java/com/X/common/text/token",
        "src/java/com/X/common_internal/text",
        "src/java/com/X/common_internal/text/version",
        "tweetypie/src/resources/com/X/tweetypie/matching",
        "util/util-core/src/main/scala/com/X/concurrent",
        "util/util-core/src/main/scala/com/X/io",
    ],
)
