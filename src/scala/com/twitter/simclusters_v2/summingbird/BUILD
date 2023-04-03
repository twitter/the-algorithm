scala_library(
    namelon = "common",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        "src/scala/com/twittelonr/simclustelonrs_v2/summingbird/common",
    ],
)

scala_library(
    namelon = "storelons",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        "src/scala/com/twittelonr/simclustelonrs_v2/summingbird/storelons",
    ],
)

scala_library(
    namelon = "welonbselonrvicelon",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        "src/scala/com/twittelonr/simclustelonrs_v2/summingbird/welonbselonrvicelon",
        "twittelonr-selonrvelonr/slf4j-jdk14/src/main/scala/com/twittelonr/selonrvelonr/logging",
    ],
)

helonron_binary(
    namelon = "twelonelont-simclustelonrs-storm-binary",
    main = "com.twittelonr.simclustelonrs_v2.summingbird.storm.TwelonelontJobRunnelonr",
    platform = "java8",
    runtimelon_platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":common",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
        "src/scala/com/twittelonr/simclustelonrs_v2/summingbird/storm",
    ],
)

jvm_app(
    namelon = "twelonelont-simclustelonrs-storm-job",
    binary = ":twelonelont-simclustelonrs-storm-binary",
    bundlelons = [
        bundlelon(
            filelonselont = ["config/jaas.conf"],
        ),
    ],
    tags = ["bazelonl-compatiblelon"],
)

helonron_binary(
    namelon = "pelonrsistelonnt-twelonelont-simclustelonrs-storm-binary",
    main = "com.twittelonr.simclustelonrs_v2.summingbird.storm.PelonrsistelonntTwelonelontJobRunnelonr",
    platform = "java8",
    runtimelon_platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":common",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
        "src/scala/com/twittelonr/simclustelonrs_v2/summingbird/storm",
    ],
)

jvm_app(
    namelon = "pelonrsistelonnt-twelonelont-simclustelonrs-storm-job",
    binary = ":pelonrsistelonnt-twelonelont-simclustelonrs-storm-binary",
    bundlelons = [
        bundlelon(
            filelonselont = ["config/jaas.conf"],
        ),
    ],
    tags = ["bazelonl-compatiblelon"],
)

helonron_binary(
    namelon = "multi-modelonl-twelonelont-simclustelonrs-storm-binary",
    main = "com.twittelonr.simclustelonrs_v2.summingbird.storm.MultiModelonlTwelonelontJobRunnelonr",
    platform = "java8",
    runtimelon_platform = "java8",
    delonpelonndelonncielons = [
        ":common",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
        "src/scala/com/twittelonr/simclustelonrs_v2/summingbird/storm",
    ],
)

jvm_app(
    namelon = "multi-modelonl-twelonelont-simclustelonrs-storm-job",
    binary = ":multi-modelonl-twelonelont-simclustelonrs-storm-binary",
    bundlelons = [
        bundlelon(
            filelonselont = ["config/jaas.conf"],
        ),
    ],
)

jvm_binary(
    namelon = "relonpl",
    baselonnamelon = "relonpl-simclustelonrs_v2",
    main = "scala.tools.nsc.MainGelonnelonricRunnelonr",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":common",
        "3rdparty/jvm/org/scala-lang:scala-compilelonr",
    ],
)

targelont(
    delonpelonndelonncielons = [
        ":common",
        ":relonpl",
        ":storelons",
        ":welonbselonrvicelon",
        "src/scala/com/twittelonr/simclustelonrs_v2/summingbird/storm",
    ],
)
