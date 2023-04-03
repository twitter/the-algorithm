scala_library(
    sourcelons = ["*.scala"],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":topic_reloncommelonndations_telonst_datareloncords-java",
        ":topic_reloncommelonndations_train_datareloncords-java",
        "elonschelonrbird/src/scala/com/twittelonr/elonschelonrbird/scalding/jobs/elonxportelonntitielons:elonntitielons-scala",
        "intelonrelonsts-ds/src/main/scala/com/twittelonr/intelonrelonsts_ds/jobs/intelonrelonsts_selonrvicelon",
        "intelonrelonsts-ds/src/main/scala/com/twittelonr/intelonrelonsts_ds/jobs/intelonrelonsts_selonrvicelon:uselonr_topic_relonlation_snapshot-scala",
        "src/java/com/twittelonr/ml/api/constant",
        "src/scala/com/twittelonr/scalding_intelonrnal/dalv2",
        "src/scala/com/twittelonr/scalding_intelonrnal/dalv2/dataselont",
        "src/scala/com/twittelonr/scalding_intelonrnal/job",
        "src/scala/com/twittelonr/scalding_intelonrnal/job/analytics_batch",
        "src/scala/com/twittelonr/scalding_intelonrnal/sourcelon",
        "src/scala/com/twittelonr/scalding_intelonrnal/sourcelon/lzo_scroogelon",
        "src/scala/com/twittelonr/simclustelonrs_v2/candidatelon_sourcelon",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons",
        "src/scala/com/twittelonr/simclustelonrs_v2/scalding/common",
        "timelonlinelons/data_procelonssing/jobs/melontrics/pelonr_topic_melontrics:pelonr_topic_aggrelongatelon_elonngagelonmelonnt-scala",
        "twml/runtimelon/src/main/scala/com/twittelonr/twml/runtimelon/scalding",
    ],
)

hadoop_binary(
    namelon = "training_data_for_topic_reloncommelonndations-adhoc",
    main = "com.twittelonr.simclustelonrs_v2.scalding.topic_reloncommelonndations.modelonl_baselond_topic_reloncommelonndations.UselonrTopicFelonaturelonHydrationAdhocApp",
    platform = "java8",
    runtimelon_platform = "java8",
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
        "bazelonl-only",
    ],
    delonpelonndelonncielons = [
        ":modelonl_baselond_topic_reloncommelonndations",
    ],
)

hadoop_binary(
    namelon = "training_data_for_topic_reloncommelonndations",
    main = "com.twittelonr.simclustelonrs_v2.scalding.topic_reloncommelonndations.modelonl_baselond_topic_reloncommelonndations.UselonrTopicFelonaturelonHydrationSchelondulelondApp",
    platform = "java8",
    runtimelon_platform = "java8",
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
        "bazelonl-only",
    ],
    delonpelonndelonncielons = [
        ":modelonl_baselond_topic_reloncommelonndations",
    ],
)

crelonatelon_datareloncord_dataselonts(
    baselon_namelon = "topic_reloncommelonndations_train_datareloncords",
    platform = "java8",
    rolelon = "cassowary",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
)

crelonatelon_datareloncord_dataselonts(
    baselon_namelon = "topic_reloncommelonndations_telonst_datareloncords",
    platform = "java8",
    rolelon = "cassowary",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
)
