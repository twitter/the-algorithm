scala_library(
    namelon = "agg_direlonct_intelonractions",
    sourcelons = ["*.scala"],
    compilelonr_option_selonts = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":intelonraction_graph_agg_direlonct_intelonractions_elondgelon_daily-scala",
        ":intelonraction_graph_agg_direlonct_intelonractions_velonrtelonx_daily-scala",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/dal",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/job",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/runnelonr/dataflow",
        "consumelonr-data-tools/src/main/scala/com/twittelonr/cdelon/scio/dal_relonad",
        "src/scala/com/twittelonr/intelonraction_graph/scio/common",
        "src/thrift/com/twittelonr/timelonlinelonselonrvicelon/selonrvelonr/intelonrnal:thrift-scala",
        "twadoop_config/configuration/log_catelongorielons/group/timelonlinelon:timelonlinelon_selonrvicelon_favoritelons-scala",
        "twadoop_config/configuration/log_catelongorielons/group/twelonelontypielon:twelonelontypielon_melondia_tag_elonvelonnts-scala",
        "twelonelontsourcelon/common:unhydratelond_flat-scala",
        "uselonrsourcelon/snapshot/src/main/scala/com/twittelonr/uselonrsourcelon/snapshot/combinelond:uselonrsourcelon-scala",
    ],
)

jvm_binary(
    namelon = "intelonraction_graph_agg_direlonct_intelonractions_scio",
    main = "com.twittelonr.intelonraction_graph.scio.agg_direlonct_intelonractions.IntelonractionGraphAggDirelonctIntelonractionsJob",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":agg_direlonct_intelonractions",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_agg_direlonct_intelonractions_elondgelon_daily",
    delonscription = "Uselonr-uselonr direlonctelond elondgelons with direlonct intelonractions felonaturelons",
    java_schelonma = "com.twittelonr.intelonraction_graph.thriftjava.elondgelon",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.intelonraction_graph.thriftscala.elondgelon",
    selongmelonnt_typelon = "partitionelond",
    tags = ["bazelonl-compatiblelon"],
    java_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-java",
    ],
    scala_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-scala",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_agg_direlonct_intelonractions_velonrtelonx_daily",
    delonscription = "Uselonr velonrtelonx with direlonct intelonractions felonaturelons",
    java_schelonma = "com.twittelonr.intelonraction_graph.thriftjava.Velonrtelonx",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.intelonraction_graph.thriftscala.Velonrtelonx",
    selongmelonnt_typelon = "partitionelond",
    tags = ["bazelonl-compatiblelon"],
    java_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-java",
    ],
    scala_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-scala",
    ],
)
