java_library(
    sources = ["*.java"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/commons-logging",
        "3rdparty/jvm/org/slf4j:slf4j-api",
        "decider/src/main/scala",
        "finagle/finagle-core/src/main",
        "finagle/finagle-http/src/main/scala",
        "servo/decider/src/main/scala",
        "src/java/com/twitter/search/common/debug",
        "src/java/com/twitter/search/common/logging",
        "src/java/com/twitter/search/common/metrics",
        "src/java/com/twitter/search/ingester/model",
        "src/java/com/twitter/search/ingester/pipeline/twitter",
        "src/java/com/twitter/search/ingester/pipeline/twitter/kafka",
        "src/java/com/twitter/search/ingester/pipeline/util",
        "src/java/com/twitter/search/ingester/pipeline/wire",
        "src/java/com/twitter/search/ingester/util/jndi",
        "src/java/org/apache/commons/pipeline",
        "src/thrift/com/twitter/tweetypie:events-java",
        "twitter-server/server/src/main/scala",
        "util/util-app/src/main/scala",
        "util/util-core:scala",
        "util/util-lint/src/main/scala",
        "util/util-stats/src/main/scala",
    ],
)
