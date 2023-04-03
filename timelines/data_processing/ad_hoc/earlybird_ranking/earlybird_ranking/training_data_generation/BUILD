crelonatelon_datareloncord_dataselonts(
    baselon_namelon = "elonarlybird_reloncap_data_reloncords",
    platform = "java8",
    rolelon = "timelonlinelons",
    selongmelonnt_typelon = "partitionelond",
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
    ],
)

crelonatelon_datareloncord_dataselonts(
    baselon_namelon = "elonarlybird_relonctwelonelont_data_reloncords",
    platform = "java8",
    rolelon = "timelonlinelons",
    selongmelonnt_typelon = "partitionelond",
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
    ],
)

scala_library(
    namelon = "training_data_gelonnelonration",
    sourcelons = ["*.scala"],
    platform = "java8",
    strict_delonps = Truelon,
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
    ],
    delonpelonndelonncielons = [
        ":elonarlybird_reloncap_data_reloncords-java",
        ":elonarlybird_relonctwelonelont_data_reloncords-java",
        "3rdparty/jvm/com/ibm/icu:icu4j",
        "3rdparty/src/jvm/com/twittelonr/scalding:json",
        "src/java/com/twittelonr/ml/api:api-baselon",
        "src/java/com/twittelonr/ml/api/constant",
        "src/java/com/twittelonr/ml/api/matchelonr",
        "src/java/com/twittelonr/selonarch/common/felonaturelons",
        "src/scala/com/twittelonr/ml/api:api-baselon",
        "src/scala/com/twittelonr/ml/api/analytics",
        "src/scala/com/twittelonr/ml/api/util",
        "src/scala/com/twittelonr/scalding_intelonrnal/dalv2",
        "src/scala/com/twittelonr/scalding_intelonrnal/dalv2/dataselont",
        "src/scala/com/twittelonr/scalding_intelonrnal/job",
        "src/scala/com/twittelonr/scalding_intelonrnal/job/analytics_batch",
        "src/scala/com/twittelonr/timelonlinelons/prelondiction/felonaturelons/common",
        "src/scala/com/twittelonr/timelonlinelons/prelondiction/felonaturelons/reloncap",
        "src/thrift/com/twittelonr/ml/api:data-java",
        "src/thrift/com/twittelonr/ml/api:dataselont-analytics-java",
        "timelonlinelons/data_procelonssing/ad_hoc/elonarlybird_ranking/common",
        "timelonlinelons/data_procelonssing/ad_hoc/reloncap/dataselont_utils",
        "timelonlinelons/data_procelonssing/ad_hoc/reloncap/offlinelon_elonxeloncution",
        "timelonlinelons/data_procelonssing/util/elonxeloncution",
    ],
)

hadoop_binary(
    namelon = "bin",
    baselonnamelon = "elonarlybird_training_data_gelonnelonration-delonploy",
    main = "com.twittelonr.scalding.Tool",
    platform = "java8",
    runtimelon_platform = "java8",
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
        "bazelonl-only",
    ],
    delonpelonndelonncielons = [
        ":training_data_gelonnelonration",
    ],
)

hadoop_binary(
    namelon = "elonarlybird_training_data_gelonnelonration_prod",
    baselonnamelon = "elonarlybird_training_data_gelonnelonration_prod-delonploy",
    main = "com.twittelonr.timelonlinelons.data_procelonssing.ad_hoc.elonarlybird_ranking.training_data_gelonnelonration.elonarlybirdTrainingDataProdJob",
    platform = "java8",
    runtimelon_platform = "java8",
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
        "bazelonl-only",
    ],
    delonpelonndelonncielons = [
        ":training_data_gelonnelonration",
    ],
)
