scala_library(
    sourcelons = ["*.scala"],
    compilelonr_option_selonts = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":relonal_graph_in_scorelons-scala",
        ":relonal_graph_oon_scorelons-scala",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/dal",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/job",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/runnelonr/dataflow",
        "tcdc/bq_blastelonr/src/main/scala/com/twittelonr/tcdc/bqblastelonr/belonam",
    ],
)

jvm_binary(
    namelon = "intelonraction_graph_scorelons_scio",
    main = "com.twittelonr.intelonraction_graph.scio.ml.scorelons.IntelonractionGraphScorelonelonxportJob",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":scorelons",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "relonal_graph_in_scorelons",
    delonscription = "Relonal Graph in nelontwork scorelons",
    kelony_typelon = "Long",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.wtf.scalding.jobs.injelonction.CandidatelonSelonqInjelonction.injelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.wtf.candidatelon.thriftscala.CandidatelonSelonq",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/wtf/scalding/jobs/injelonction",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "relonal_graph_oon_scorelons",
    delonscription = "Relonal Graph OON Scorelons",
    kelony_typelon = "Long",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.wtf.scalding.jobs.injelonction.CandidatelonSelonqInjelonction.injelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.wtf.candidatelon.thriftscala.CandidatelonSelonq",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/wtf/scalding/jobs/injelonction",
    ],
)
