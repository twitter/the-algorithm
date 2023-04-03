scala_library(
    namelon = "labelonls",
    sourcelons = ["*.scala"],
    compilelonr_option_selonts = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":intelonraction_graph_labelonls_daily-scala",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/dal",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/job",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/runnelonr/dataflow",
        "consumelonr-data-tools/src/main/scala/com/twittelonr/cdelon/scio/dal_relonad",
        "socialgraph/hadoop/src/main/scala/com/twittelonr/socialgraph/hadoop:socialgraph-follow-elonvelonnts-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_clielonnt_elonvelonnt_logs:intelonraction_graph_agg_clielonnt_elonvelonnt_logs_elondgelon_daily-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_direlonct_intelonractions:intelonraction_graph_agg_direlonct_intelonractions_elondgelon_daily-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_elonmail:intelonraction_graph_elonxtelonndelond_elonmail_elondgelon_daily-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_notifications:intelonraction_graph_agg_notifications_elondgelon_daily-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_relontwelonelonts:intelonraction_graph_elonxtelonndelond_relontwelonelont_elondgelon_daily-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_sharelons:intelonraction_graph_elonxtelonndelond_sharelon_elondgelon_daily-scala",
        "tcdc/bq_blastelonr/src/main/scala/com/twittelonr/tcdc/bqblastelonr/belonam",
    ],
)

jvm_binary(
    namelon = "intelonraction_graph_labelonls",
    main = "com.twittelonr.intelonraction_graph.scio.ml.labelonls.IntelonractionGraphLabelonlsJob",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":labelonls",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_labelonls_daily",
    delonscription = "Daily labelonls",
    java_schelonma = "com.twittelonr.intelonraction_graph.thriftjava.elondgelonLabelonl",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.intelonraction_graph.thriftscala.elondgelonLabelonl",
    selongmelonnt_typelon = "partitionelond",
    tags = ["bazelonl-compatiblelon"],
    java_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-java",
    ],
    scala_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-scala",
    ],
)
