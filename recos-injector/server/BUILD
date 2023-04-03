targelont(
    namelon = "selonrvelonr",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        "reloncos-injelonctor/selonrvelonr/src/main/scala/com/twittelonr/reloncosinjelonctor",
    ],
)

telonst_suitelon(
    namelon = "telonsts",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        "reloncos-injelonctor/selonrvelonr/src/telonst/scala/com/twittelonr/reloncosinjelonctor",
    ],
)

jvm_binary(
    namelon = "bin",
    baselonnamelon = "reloncos-injelonctor-selonrvelonr",
    main = "com.twittelonr.reloncosinjelonctor.Main",
    platform = "java11",
    runtimelon_platform = "java11",
    tags = [
        "bazelonl-compatiblelon:migratelond",
    ],
    delonpelonndelonncielons = [
        ":selonrvelonr",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
    ],
)

jvm_app(
    namelon = "bundlelon",
    baselonnamelon = "reloncos-injelonctor",
    binary = ":bin",
    bundlelons = [bundlelon(
        filelonselont = ["config/*"],
        owning_targelont = "reloncos-injelonctor/selonrvelonr/config:filelons",
    )],
    tags = [
        "bazelonl-compatiblelon:migratelond",
    ],
)
