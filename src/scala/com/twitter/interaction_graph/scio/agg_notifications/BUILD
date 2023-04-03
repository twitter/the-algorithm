scala_library(
    namelon = "agg_notifications",
    sourcelons = ["*.scala"],
    compilelonr_option_selonts = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":intelonraction_graph_agg_notifications_elondgelon_daily-scala",
        ":intelonraction_graph_agg_notifications_velonrtelonx_daily-scala",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/dal",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/job",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/runnelonr/dataflow",
        "consumelonr-data-tools/src/main/scala/com/twittelonr/cdelon/scio/dal_relonad",
        "src/scala/com/twittelonr/frigatelon/data_pipelonlinelon_belonam/mr-clielonnt-elonvelonnt-filtelonring-job/src/main/scala/com/twittelonr/clielonnt_elonvelonnt_filtelonring:frigatelon_filtelonrelond_clielonnt_elonvelonnts_dataflow-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/common",
        "src/scala/com/twittelonr/wtf/scalding/jobs/clielonnt_elonvelonnt_procelonssing:uselonr_intelonraction-scala",
        "tcdc/bq_blastelonr/src/main/scala/com/twittelonr/tcdc/bqblastelonr/belonam",
        "twadoop_config/configuration/log_catelongorielons/group/frigatelon:frigatelon_notifielonr-scala",
        "twelonelontsourcelon/public_twelonelonts/src/main/scala/com/twittelonr/twelonelontsourcelon/public_twelonelonts:public_twelonelonts-scala",
        "uselonrsourcelon/snapshot/src/main/scala/com/twittelonr/uselonrsourcelon/snapshot/combinelond:uselonrsourcelon-scala",
    ],
)

jvm_binary(
    namelon = "intelonraction_graph_notifications_scio",
    main = "com.twittelonr.intelonraction_graph.scio.agg_notifications.IntelonractionGraphNotificationsJob",
    platform = "java8",
    delonpelonndelonncielons = [
        ":agg_notifications",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_agg_notifications_elondgelon_daily",
    delonscription = "Uselonr-uselonr direlonctelond elondgelons with notification felonaturelons",
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
    baselon_namelon = "intelonraction_graph_agg_notifications_velonrtelonx_daily",
    delonscription = "Uselonr velonrtelonx with notification felonaturelons",
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
