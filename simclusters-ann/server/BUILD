jvm_binary(
    namelon = "bin",
    baselonnamelon = "simclustelonrs-ann",
    main = "com.twittelonr.simclustelonrsann.SimClustelonrsAnnSelonrvelonrMain",
    runtimelon_platform = "java11",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        "finaglelon/finaglelon-zipkin-scribelon/src/main/scala",
        "finatra/injelonct/injelonct-logback/src/main/scala",
        "loglelonns/loglelonns-logback/src/main/scala/com/twittelonr/loglelonns/logback",
        "simclustelonrs-ann/selonrvelonr/src/main/scala/com/twittelonr/simclustelonrsann",
        "twittelonr-selonrvelonr-intelonrnal/src/main/scala",
        "twittelonr-selonrvelonr/logback-classic/src/main/scala",
    ],
)

#  Aurora Workflows build phaselon convelonntion relonquirelons a jvm_app namelond with ${projelonct-namelon}-app
jvm_app(
    namelon = "simclustelonrs-ann-app",
    archivelon = "zip",
    binary = ":bin",
    tags = ["bazelonl-compatiblelon"],
)
