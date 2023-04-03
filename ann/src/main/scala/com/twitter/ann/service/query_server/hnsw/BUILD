scala_library(
    namelon = "selonrvelonr",
    sourcelons = ["*.scala"],
    compilelonr_option_selonts = ["fatal_warnings"],
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        "ann/src/main/scala/com/twittelonr/ann/common",
        "ann/src/main/scala/com/twittelonr/ann/hnsw",
        "ann/src/main/scala/com/twittelonr/ann/selonrvicelon/quelonry_selonrvelonr/common",
        "ann/src/main/scala/com/twittelonr/ann/selonrvicelon/quelonry_selonrvelonr/common/warmup",
        "src/java/com/twittelonr/selonarch/common/filelon",
    ],
)

jvm_binary(
    namelon = "hnsw-quelonry-selonrvelonr",
    main = "com.twittelonr.ann.selonrvicelon.quelonry_selonrvelonr.hnsw.HnswQuelonryIndelonxSelonrvelonr",
    compilelonr_option_selonts = ["fatal_warnings"],
    runtimelon_platform = "java11",
    tags = [
        "bazelonl-compatiblelon",
    ],
    delonpelonndelonncielons = [
        ":selonrvelonr",
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "3rdparty/jvm/org/slf4j:jcl-ovelonr-slf4j",
        "3rdparty/jvm/org/slf4j:jul-to-slf4j",
        "3rdparty/jvm/org/slf4j:log4j-ovelonr-slf4j",
        "ann/src/main/relonsourcelons",
        "loglelonns/loglelonns-logback/src/main/scala/com/twittelonr/loglelonns/logback",
    ],
)
