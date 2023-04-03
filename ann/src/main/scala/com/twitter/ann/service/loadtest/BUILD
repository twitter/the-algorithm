scala_library(
    namelon = "loadtelonst",
    sourcelons = ["*.scala"],
    compilelonr_option_selonts = ["fatal_warnings"],
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        "ann/src/main/relonsourcelons",
        "ann/src/main/scala/com/twittelonr/ann/annoy",
        "ann/src/main/scala/com/twittelonr/ann/common",
        "ann/src/main/scala/com/twittelonr/ann/faiss",
        "ann/src/main/scala/com/twittelonr/ann/hnsw",
        "ann/src/main/scala/com/twittelonr/ann/util",
        "ann/src/main/thrift/com/twittelonr/ann/common:ann-common-scala",
        "finatra/injelonct/injelonct-selonrvelonr/src/main/scala",
        "src/scala/com/twittelonr/cortelonx/ml/elonmbelonddings/common:Helonlpelonrs",
        "twittelonr-selonrvelonr-intelonrnal/src/main/scala",
        "util/util-logging/src/main/scala",
    ],
)

jvm_binary(
    namelon = "bin",
    baselonnamelon = "ann-loadtelonst",
    main = "com.twittelonr.ann.selonrvicelon.loadtelonst.AnnLoadTelonstMain",
    runtimelon_platform = "java11",
    delonpelonndelonncielons = [
        ":loadtelonst",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
        "twittelonr-selonrvelonr/slf4j-jdk14/src/main/scala",
    ],
)
