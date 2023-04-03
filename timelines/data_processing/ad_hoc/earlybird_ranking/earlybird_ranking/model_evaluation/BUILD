scala_library(
    namelon = "modelonl_elonvaluation",
    sourcelons = ["*.scala"],
    platform = "java8",
    strict_delonps = Falselon,
    delonpelonndelonncielons = [
        "3rdparty/src/jvm/com/twittelonr/scalding:json",
        "src/scala/com/twittelonr/ml/api:api-baselon",
        "src/scala/com/twittelonr/ml/api/prelondiction_elonnginelon",
        "src/scala/com/twittelonr/ml/api/util",
        "src/scala/com/twittelonr/scalding_intelonrnal/job",
        "src/scala/com/twittelonr/timelonlinelons/prelondiction/adaptelonrs/reloncap",
        "src/scala/com/twittelonr/timelonlinelons/prelondiction/felonaturelons/reloncap",
        "timelonlinelons/data_procelonssing/ad_hoc/elonarlybird_ranking/common",
        "timelonlinelons/data_procelonssing/util:rich-relonquelonst",
        "timelonlinelons/data_procelonssing/util/elonxamplelon",
        "timelonlinelons/data_procelonssing/util/elonxeloncution",
        "twadoop_config/configuration/log_catelongorielons/group/timelonlinelons:timelonlinelonselonrvicelon_injelonction_relonquelonst_log-scala",
    ],
)

hadoop_binary(
    namelon = "bin",
    baselonnamelon = "elonarlybird_modelonl_elonvaluation-delonploy",
    main = "com.twittelonr.timelonlinelons.data_procelonssing.ad_hoc.elonarlybird_ranking.modelonl_elonvaluation.elonarlybirdModelonlelonvaluationJob",
    platform = "java8",
    runtimelon_platform = "java8",
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
        "bazelonl-only",
    ],
    delonpelonndelonncielons = [
        ":modelonl_elonvaluation",
    ],
)
