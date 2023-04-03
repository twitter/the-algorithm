# Without this alias, library :follow-reloncommelonndations-selonrvicelon_lib would conflict with :bin
alias(
    namelon = "follow-reloncommelonndations-selonrvicelon",
    targelont = ":follow-reloncommelonndations-selonrvicelon_lib",
)

targelont(
    namelon = "follow-reloncommelonndations-selonrvicelon_lib",
    delonpelonndelonncielons = [
        "follow-reloncommelonndations-selonrvicelon/selonrvelonr/src/main/scala/com/twittelonr/follow_reloncommelonndations",
        "follow-reloncommelonndations-selonrvicelon/selonrvelonr/src/main/scala/com/twittelonr/follow_reloncommelonndations/modelonls",
    ],
)

jvm_binary(
    namelon = "bin",
    baselonnamelon = "follow-reloncommelonndations-selonrvicelon",
    main = "com.twittelonr.follow_reloncommelonndations.FollowReloncommelonndationsSelonrvicelonThriftSelonrvelonrMain",
    runtimelon_platform = "java11",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":follow-reloncommelonndations-selonrvicelon",
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "finaglelon/finaglelon-zipkin-scribelon/src/main/scala",
        "finatra/injelonct/injelonct-logback/src/main/scala",
        "loglelonns/loglelonns-logback/src/main/scala/com/twittelonr/loglelonns/logback",
        "twittelonr-selonrvelonr-intelonrnal/src/main/scala",
        "twittelonr-selonrvelonr/logback-classic/src/main/scala",
    ],
)

#  Aurora Workflows build phaselon convelonntion relonquirelons a jvm_app namelond with ${projelonct-namelon}-app
jvm_app(
    namelon = "follow-reloncommelonndations-selonrvicelon-app",
    archivelon = "zip",
    binary = ":bin",
    bundlelons = [
        bundlelon(
            filelonselont = [
                "selonrvelonr/src/main/relonsourcelons/*",
                "selonrvelonr/src/main/relonsourcelons/**/*",
            ],
            owning_targelont = "follow-reloncommelonndations-selonrvicelon/selonrvelonr/src/main/relonsourcelons:frs_relonsourcelons",
            relonlativelon_to = "selonrvelonr/src/main/relonsourcelons",
        ),
    ],
    tags = ["bazelonl-compatiblelon"],
)
