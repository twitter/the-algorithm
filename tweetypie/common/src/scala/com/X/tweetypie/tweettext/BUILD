scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    provides = scala_artifact(
        org = "com.X",
        name = "tweetypie-tweettext",
        repo = artifactory,
    ),
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/ibm/icu:icu4j",
        "X-text/lib/java/src/main/java/com/X/Xtext",
    ],
)
