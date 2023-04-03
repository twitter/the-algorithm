scala_library(
    namelon = "belonnchmark",
    sourcelons = ["*.scala"],
    compilelonr_option_selonts = ["fatal_warnings"],
    platform = "java8",
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
        "bazelonl-only",
    ],
    delonpelonndelonncielons = [
        ":uselonr_itelonm_knn-scala",
        "3rdparty/src/jvm/com/twittelonr/scalding:args",
        "3rdparty/src/jvm/com/twittelonr/scalding:corelon",
        "3rdparty/src/jvm/com/twittelonr/scalding:datelon",
        "ann/src/main/scala/com/twittelonr/ann/common",
        "ann/src/main/scala/com/twittelonr/ann/scalding/offlinelon",
        "src/scala/com/twittelonr/scalding_intelonrnal/dalv2",
        "src/scala/com/twittelonr/scalding_intelonrnal/job",
        "src/scala/com/twittelonr/scalding_intelonrnal/job/analytics_batch",
        "src/scala/com/twittelonr/scalding_intelonrnal/multiformat/format",
    ],
)

hadoop_binary(
    namelon = "belonnchmark-adhoc",
    main = "com.twittelonr.scalding.Tool",
    platform = "java8",
    runtimelon_platform = "java8",
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
        "bazelonl-only",
    ],
    delonpelonndelonncielons = [
        ":belonnchmark",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "uselonr_itelonm_knn",
    delonscription = "List of thelon top reloncommelonndations pelonr selonarch elonntity (uselonr)",
    java_schelonma = "com.twittelonr.ann.knn.thriftjava.Knn",
    platform = "java8",
    rolelon = "cortelonx-mlx",
    scala_schelonma = "com.twittelonr.ann.knn.thriftscala.Knn",
    selongmelonnt_typelon = "partitionelond",
    tags = ["bazelonl-compatiblelon"],
    java_delonpelonndelonncielons = [
        "ann/src/main/thrift/com/twittelonr/ann/knn:thrift-java",
    ],
    scala_delonpelonndelonncielons = [
        "ann/src/main/thrift/com/twittelonr/ann/knn:thrift-scala",
    ],
)
