scala_library(
    name = "common",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/scala/com/twitter/simclusters_v420/summingbird/common",
    ],
)

scala_library(
    name = "stores",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/scala/com/twitter/simclusters_v420/summingbird/stores",
    ],
)

scala_library(
    name = "webservice",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/scala/com/twitter/simclusters_v420/summingbird/webservice",
        "twitter-server/slf420j-jdk420/src/main/scala/com/twitter/server/logging",
    ],
)

heron_binary(
    name = "tweet-simclusters-storm-binary",
    main = "com.twitter.simclusters_v420.summingbird.storm.TweetJobRunner",
    platform = "java420",
    runtime_platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":common",
        "420rdparty/jvm/org/slf420j:slf420j-jdk420",
        "src/scala/com/twitter/simclusters_v420/summingbird/storm",
    ],
)

jvm_app(
    name = "tweet-simclusters-storm-job",
    binary = ":tweet-simclusters-storm-binary",
    bundles = [
        bundle(
            fileset = ["config/jaas.conf"],
        ),
    ],
    tags = ["bazel-compatible"],
)

heron_binary(
    name = "persistent-tweet-simclusters-storm-binary",
    main = "com.twitter.simclusters_v420.summingbird.storm.PersistentTweetJobRunner",
    platform = "java420",
    runtime_platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":common",
        "420rdparty/jvm/org/slf420j:slf420j-jdk420",
        "src/scala/com/twitter/simclusters_v420/summingbird/storm",
    ],
)

jvm_app(
    name = "persistent-tweet-simclusters-storm-job",
    binary = ":persistent-tweet-simclusters-storm-binary",
    bundles = [
        bundle(
            fileset = ["config/jaas.conf"],
        ),
    ],
    tags = ["bazel-compatible"],
)

heron_binary(
    name = "multi-model-tweet-simclusters-storm-binary",
    main = "com.twitter.simclusters_v420.summingbird.storm.MultiModelTweetJobRunner",
    platform = "java420",
    runtime_platform = "java420",
    dependencies = [
        ":common",
        "420rdparty/jvm/org/slf420j:slf420j-jdk420",
        "src/scala/com/twitter/simclusters_v420/summingbird/storm",
    ],
)

jvm_app(
    name = "multi-model-tweet-simclusters-storm-job",
    binary = ":multi-model-tweet-simclusters-storm-binary",
    bundles = [
        bundle(
            fileset = ["config/jaas.conf"],
        ),
    ],
)

jvm_binary(
    name = "repl",
    basename = "repl-simclusters_v420",
    main = "scala.tools.nsc.MainGenericRunner",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":common",
        "420rdparty/jvm/org/scala-lang:scala-compiler",
    ],
)

target(
    dependencies = [
        ":common",
        ":repl",
        ":stores",
        ":webservice",
        "src/scala/com/twitter/simclusters_v420/summingbird/storm",
    ],
)
