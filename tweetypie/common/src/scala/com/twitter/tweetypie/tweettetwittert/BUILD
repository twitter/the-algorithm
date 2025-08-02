scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    provides = scala_artifact(
        org = "com.twitter",
        name = "tweetypie-tweettetwittert",
        repo = artifactory,
    ),
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/ibm/icu:icu4j",
        "twitter-tetwittert/lib/java/src/main/java/com/twitter/twittertetwittert",
    ],
)
