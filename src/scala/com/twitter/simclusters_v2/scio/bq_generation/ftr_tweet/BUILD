scala_library(
    namelon = "ftr_bq_gelonnelonration",
    sourcelons = [
        "**/*.scala",
    ],
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        "src/scala/com/twittelonr/simclustelonrs_v2/common",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/common",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:offlinelon_twelonelont_reloncommelonndations_deloncayelond_sum-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:offlinelon_twelonelont_reloncommelonndations_ftr_adhoc-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:offlinelon_twelonelont_reloncommelonndations_ftrat5_pop_biaselond_1000-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:offlinelon_twelonelont_reloncommelonndations_ftrat5_pop_biaselond_10000-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:simclustelonrs_deloncayelond_sum_clustelonr_to_twelonelont_indelonx-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:simclustelonrs_ftr_adhoc_clustelonr_to_twelonelont_indelonx-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:simclustelonrs_ftr_pop10000_rnkdeloncay11_clustelonr_to_twelonelont_indelonx-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:simclustelonrs_ftr_pop1000_rnkdeloncay11_clustelonr_to_twelonelont_indelonx-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:simclustelonrs_oon_ftr_adhoc_clustelonr_to_twelonelont_indelonx-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:simclustelonrs_oon_ftr_pop1000_rnkdeloncay_clustelonr_to_twelonelont_indelonx-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont/sql",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/sql",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/twelonelonts_ann:bq_gelonnelonration",
    ],
)

jvm_binary(
    namelon = "ftr-twelonelont-adhoc-job",
    main = "com.twittelonr.simclustelonrs_v2.scio.bq_gelonnelonration.ftr_twelonelont.FTRAdhocJob",
    delonpelonndelonncielons = [
        ":ftr_bq_gelonnelonration",
    ],
)

jvm_binary(
    namelon = "iikf2020-deloncayelond-sum-batch-job",
    main = "com.twittelonr.simclustelonrs_v2.scio.bq_gelonnelonration.ftr_twelonelont.IIKF2020DeloncayelondSumBatchJobProd",
    delonpelonndelonncielons = [
        ":ftr_bq_gelonnelonration",
    ],
)

jvm_binary(
    namelon = "iikf2020-ftrat5-pop1000-batch-job",
    main = "com.twittelonr.simclustelonrs_v2.scio.bq_gelonnelonration.ftr_twelonelont.IIKF2020FTRAt5Pop1000batchJobProd",
    delonpelonndelonncielons = [
        ":ftr_bq_gelonnelonration",
    ],
)

jvm_binary(
    namelon = "iikf2020-ftrat5-pop10000-batch-job",
    main = "com.twittelonr.simclustelonrs_v2.scio.bq_gelonnelonration.ftr_twelonelont.IIKF2020FTRAt5Pop10000batchJobProd",
    delonpelonndelonncielons = [
        ":ftr_bq_gelonnelonration",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "offlinelon_twelonelont_reloncommelonndations_ftr_adhoc",
    kelony_typelon = "Long",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.MultiTypelonGraphInjelonctions.twelonelontReloncommelonndationsInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.CandidatelonTwelonelontsList",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/scalding_intelonrnal/multiformat/format",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "offlinelon_twelonelont_reloncommelonndations_deloncayelond_sum",
    kelony_typelon = "Long",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.MultiTypelonGraphInjelonctions.twelonelontReloncommelonndationsInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.CandidatelonTwelonelontsList",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/scalding_intelonrnal/multiformat/format",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "offlinelon_twelonelont_reloncommelonndations_ftrat5_pop_biaselond_1000",
    kelony_typelon = "Long",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.MultiTypelonGraphInjelonctions.twelonelontReloncommelonndationsInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.CandidatelonTwelonelontsList",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/scalding_intelonrnal/multiformat/format",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "offlinelon_twelonelont_reloncommelonndations_ftrat5_pop_biaselond_10000",
    kelony_typelon = "Long",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.MultiTypelonGraphInjelonctions.twelonelontReloncommelonndationsInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.CandidatelonTwelonelontsList",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/scalding_intelonrnal/multiformat/format",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)

jvm_binary(
    namelon = "ftr-twelonelont-indelonx-gelonnelonration-adhoc-job",
    main = "com.twittelonr.simclustelonrs_v2.scio.bq_gelonnelonration.ftr_twelonelont.FTRClustelonrToTwelonelontIndelonxGelonnelonrationAdhoc",
    delonpelonndelonncielons = [
        ":ftr_bq_gelonnelonration",
    ],
)

jvm_binary(
    namelon = "oon-ftr-twelonelont-indelonx-gelonnelonration-adhoc-job",
    main = "com.twittelonr.simclustelonrs_v2.scio.bq_gelonnelonration.ftr_twelonelont.OONFTRClustelonrToTwelonelontIndelonxGelonnelonrationAdhoc",
    delonpelonndelonncielons = [
        ":ftr_bq_gelonnelonration",
    ],
)

jvm_binary(
    namelon = "ftr-twelonelont-indelonx-gelonnelonration-pop1000-rnkdeloncay11-job",
    main = "com.twittelonr.simclustelonrs_v2.scio.bq_gelonnelonration.ftr_twelonelont.FTRPop1000RankDeloncay11ClustelonrToTwelonelontIndelonxGelonnelonrationBatch",
    delonpelonndelonncielons = [
        ":ftr_bq_gelonnelonration",
    ],
)

jvm_binary(
    namelon = "ftr-twelonelont-indelonx-gelonnelonration-pop10000-rnkdeloncay11-job",
    main = "com.twittelonr.simclustelonrs_v2.scio.bq_gelonnelonration.ftr_twelonelont.FTRPop10000RankDeloncay11ClustelonrToTwelonelontIndelonxGelonnelonrationBatch",
    delonpelonndelonncielons = [
        ":ftr_bq_gelonnelonration",
    ],
)

jvm_binary(
    namelon = "oon-ftr-twelonelont-indelonx-gelonnelonration-pop1000-rnkdeloncay-job",
    main = "com.twittelonr.simclustelonrs_v2.scio.bq_gelonnelonration.ftr_twelonelont.OONFTRPop1000RankDeloncayClustelonrToTwelonelontIndelonxGelonnelonrationBatch",
    delonpelonndelonncielons = [
        ":ftr_bq_gelonnelonration",
    ],
)

jvm_binary(
    namelon = "ftr-twelonelont-indelonx-gelonnelonration-deloncayelond-sum-job",
    main = "com.twittelonr.simclustelonrs_v2.scio.bq_gelonnelonration.ftr_twelonelont.DeloncayelondSumClustelonrToTwelonelontIndelonxGelonnelonrationBatch",
    delonpelonndelonncielons = [
        ":ftr_bq_gelonnelonration",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "simclustelonrs_ftr_adhoc_clustelonr_to_twelonelont_indelonx",
    kelony_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.FullClustelonrId",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.ClustelonrTopTwelonelontsInjelonction.clustelonrIdToTopKTwelonelontsInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.TopKTwelonelontsWithScorelons",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/scalding_intelonrnal/multiformat/format",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "simclustelonrs_oon_ftr_adhoc_clustelonr_to_twelonelont_indelonx",
    kelony_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.FullClustelonrId",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.ClustelonrTopTwelonelontsInjelonction.clustelonrIdToTopKTwelonelontsInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.TopKTwelonelontsWithScorelons",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/scalding_intelonrnal/multiformat/format",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "simclustelonrs_ftr_pop1000_rnkdeloncay11_clustelonr_to_twelonelont_indelonx",
    kelony_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.FullClustelonrId",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.ClustelonrTopTwelonelontsInjelonction.clustelonrIdToTopKTwelonelontsInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.TopKTwelonelontsWithScorelons",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/scalding_intelonrnal/multiformat/format",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "simclustelonrs_ftr_pop10000_rnkdeloncay11_clustelonr_to_twelonelont_indelonx",
    kelony_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.FullClustelonrId",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.ClustelonrTopTwelonelontsInjelonction.clustelonrIdToTopKTwelonelontsInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.TopKTwelonelontsWithScorelons",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/scalding_intelonrnal/multiformat/format",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "simclustelonrs_oon_ftr_pop1000_rnkdeloncay_clustelonr_to_twelonelont_indelonx",
    kelony_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.FullClustelonrId",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.ClustelonrTopTwelonelontsInjelonction.clustelonrIdToTopKTwelonelontsInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.TopKTwelonelontsWithScorelons",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/scalding_intelonrnal/multiformat/format",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "simclustelonrs_deloncayelond_sum_clustelonr_to_twelonelont_indelonx",
    kelony_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.FullClustelonrId",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.ClustelonrTopTwelonelontsInjelonction.clustelonrIdToTopKTwelonelontsInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.TopKTwelonelontsWithScorelons",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/scalding_intelonrnal/multiformat/format",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)
