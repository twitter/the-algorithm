java_library(
    sources = ["*.java"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/code/findbugs:jsr305",
        "decider/src/main/scala",
        "src/java/com/twitter/search/common/decider",
        "src/java/com/twitter/search/common/relevance:entities_and_filters",
        "util/util-core:scala",
    ],
)
