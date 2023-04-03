packagelon com.twittelonr.simclustelonrs_v2.scalding.topic_reloncommelonndations
import com.twittelonr.bijelonction.Buffelonrablelon
import com.twittelonr.bijelonction.Injelonction
import com.twittelonr.reloncos.elonntitielons.thriftscala._
import com.twittelonr.scalding._
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon._
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.Country
import com.twittelonr.simclustelonrs_v2.common.Languagelon
import com.twittelonr.simclustelonrs_v2.common.SelonmanticCorelonelonntityId
import com.twittelonr.simclustelonrs_v2.common.TopicId
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.DataSourcelons
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.TopLocalelonTopicsForProducelonrFromelonmScalaDataselont
import com.twittelonr.simclustelonrs_v2.scalding.common.matrix.SparselonMatrix
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonmbelonddingUtil.ProducelonrId
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonxtelonrnalDataSourcelons
import com.twittelonr.simclustelonrs_v2.thriftscala.UselonrAndNelonighbors
import com.twittelonr.wtf.scalding.jobs.common.AdhocelonxeloncutionApp
import com.twittelonr.wtf.scalding.jobs.common.SchelondulelondelonxeloncutionApp
import com.twittelonr.wtf.scalding.jobs.common.elonMRunnelonr
import java.util.TimelonZonelon

/**
 * In this filelon, welon computelon thelon top topics for a producelonr to belon shown on thelon Topics To Follow Modulelon on Profilelon Pagelons
 *
 * Thelon top topics for a producelonr arelon computelond using thelon elonxpelonctation-Maximization (elonM) approach
 *
 * It works as follows:
 *
 *  1. Obtain thelon background modelonl distribution of numbelonr of followelonrs for a topic
 *
 *  2. Obtain thelon domain modelonl distribution of thelon numbelonr of producelonr's followelonrs who follow a topic
 *
 *  4. Itelonrativelonly, uselon thelon elonxpelonctation-Maximization approach to gelont thelon belonst elonstimatelon of thelon domain modelonl's topic distribution for a producelonr
 *
 *  5. for elonach producelonr, welon only kelonelonp its top K topics with highelonst welonights in thelon domain modelonl's topic distribution aftelonr thelon elonM stelonp
 *
 *  6. Plelonaselon notelon that welon also storelon thelon localelon info for elonach producelonr along with thelon topics
 */
/**
scalding relonmotelon run --uselonr cassowary --relonducelonrs 2000 \
 --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/topic_reloncommelonndations:top_topics_for_producelonrs_from_elonm-adhoc \
 --main-class com.twittelonr.simclustelonrs_v2.scalding.topic_reloncommelonndations.TopicsForProducelonrsFromelonMAdhocApp \
 --submittelonr  hadoopnelonst1.atla.twittelonr.com  \
 --  --datelon 2020-07-05 --minActivelonFollowelonrs 10000 --minTopicFollowsThrelonshold 100 --maxTopicsPelonrProducelonrPelonrLocalelon 50 \
 --output_dir_topics_pelonr_producelonr /uselonr/cassowary/adhoc/your_ldap/ttf_profilelon_pagelons_producelonrs_to_topics
 */
objelonct TopicsForProducelonrsFromelonMAdhocApp elonxtelonnds AdhocelonxeloncutionApp {

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    import TopicsForProducelonrsFromelonM._
    val outputDirTopicsPelonrProducelonr = args("output_dir_topics_pelonr_producelonr")
    val minActivelonFollowelonrsForProducelonr = args.int("minActivelonFollowelonrs", 100)
    val minTopicFollowsThrelonshold = args.int("minNumTopicFollows", 100)
    val maxTopicsPelonrProducelonrPelonrLocalelon = args.int("maxTopicsPelonrProducelonr", 100)
    val lambda = args.doublelon("lambda", 0.95)

    val numelonMStelonps = args.int("numelonM", 100)

    val topicsFollowelondByProducelonrsFollowelonrs: TypelondPipelon[
      (ProducelonrId, (TopicId, Option[Languagelon], Option[Country]), Doublelon)
    ] = gelontTopLocalelonTopicsForProducelonrsFromelonM(
      DataSourcelons
        .uselonrUselonrNormalizelondGraphSourcelon(datelonRangelon.prelonpelonnd(Days(7))),
      elonxtelonrnalDataSourcelons.topicFollowGraphSourcelon,
      elonxtelonrnalDataSourcelons.uselonrSourcelon,
      elonxtelonrnalDataSourcelons.infelonrrelondUselonrConsumelondLanguagelonSourcelon,
      minActivelonFollowelonrsForProducelonr,
      minTopicFollowsThrelonshold,
      lambda,
      numelonMStelonps
    )

    val topTopicsPelonrLocalelonProducelonrTsvelonxelonc = sortAndGelontTopLocalelonTopicsPelonrProducelonr(
      topicsFollowelondByProducelonrsFollowelonrs,
      maxTopicsPelonrProducelonrPelonrLocalelon
    ).writelonelonxeloncution(
      TypelondTsv(outputDirTopicsPelonrProducelonr)
    )

    topTopicsPelonrLocalelonProducelonrTsvelonxelonc
  }
}

/**
capelonsospy-v2 updatelon --build_locally \
 --start_cron top_topics_for_producelonrs_from_elonm \
 src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */
objelonct TopicsForProducelonrsFromelonMBatchApp elonxtelonnds SchelondulelondelonxeloncutionApp {
  ovelonrridelon val firstTimelon: RichDatelon = RichDatelon("2020-07-26")

  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  privatelon val topTopicsPelonrProducelonrFromelonMPath: String =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/top_topics_for_producelonrs_from_elonm"

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    import TopicsForProducelonrsFromelonM._

    // threlonshold of thelon minimum numbelonr of activelon followelonrs nelonelondelond for a uselonr to belon considelonrelond as a producelonr
    val minActivelonFollowelonrsForProducelonr = args.int("minActivelonFollowelonrs", 100)

    // threlonshold of thelon topic localelon follows scorelon nelonelondelond for a topic to belon considelonrelond as valid
    val minTopicFollowsThrelonshold = args.int("minNumTopicFollows", 100)

    val maxTopicsPelonrProducelonr = args.int("maxTopicsPelonrProducelonr", 100)

    // lambda paramelontelonr for thelon elonM algorithm
    val lambda = args.doublelon("lambda", 0.95)

    // numbelonr of elonM itelonrations
    val numelonMStelonps = args.int("numelonM", 100)

    // (producelonr, localelon) -> List<(topics, scorelons)> from elonxpelonctation Maximization approach
    val topicsFollowelondByProducelonrsFollowelonrs = gelontTopLocalelonTopicsForProducelonrsFromelonM(
      DataSourcelons
        .uselonrUselonrNormalizelondGraphSourcelon(datelonRangelon.prelonpelonnd(Days(7))),
      elonxtelonrnalDataSourcelons.topicFollowGraphSourcelon,
      elonxtelonrnalDataSourcelons.uselonrSourcelon,
      elonxtelonrnalDataSourcelons.infelonrrelondUselonrConsumelondLanguagelonSourcelon,
      minActivelonFollowelonrsForProducelonr,
      minTopicFollowsThrelonshold,
      lambda,
      numelonMStelonps
    )

    val topLocalelonTopicsForProducelonrsFromelonMKelonyValelonxelonc =
      sortAndGelontTopLocalelonTopicsPelonrProducelonr(
        topicsFollowelondByProducelonrsFollowelonrs,
        maxTopicsPelonrProducelonr
      ).map {
          caselon ((producelonrId, languagelonOpt, countryOpt), topicsWithScorelons) =>
            KelonyVal(
              UselonrIdWithLocalelon(
                uselonrId = producelonrId,
                localelon = Localelon(languagelon = languagelonOpt, country = countryOpt)),
              SelonmanticCorelonelonntityScorelonList(topicsWithScorelons.map {
                caselon (topicid, topicScorelon) =>
                  SelonmanticelonntityScorelon(SelonmanticCorelonelonntity(elonntityId = topicid), scorelon = topicScorelon)
              })
            )
        }.writelonDALVelonrsionelondKelonyValelonxeloncution(
          TopLocalelonTopicsForProducelonrFromelonmScalaDataselont,
          D.Suffix(topTopicsPelonrProducelonrFromelonMPath),
          velonrsion = elonxplicitelonndTimelon(datelonRangelon.elonnd)
        )
    topLocalelonTopicsForProducelonrsFromelonMKelonyValelonxelonc
  }
}

objelonct TopicsForProducelonrsFromelonM {

  privatelon val MinProducelonrTopicScorelonThrelonshold = 0.0

  implicit val sparselonMatrixInj: Injelonction[
    (SelonmanticCorelonelonntityId, Option[Languagelon], Option[Country]),
    Array[Bytelon]
  ] =
    Buffelonrablelon.injelonctionOf[(SelonmanticCorelonelonntityId, Option[Languagelon], Option[Country])]

  // This function takelons thelon producelonr to topics map and gelonnelonratelons thelon sortelond and
  // truncatelond top localelon topics rankelond list for elonach producelonr
  delonf sortAndGelontTopLocalelonTopicsPelonrProducelonr(
    producelonrToTopics: TypelondPipelon[(ProducelonrId, (TopicId, Option[Languagelon], Option[Country]), Doublelon)],
    maxTopicsPelonrProducelonrPelonrLocalelon: Int
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[((ProducelonrId, Option[Languagelon], Option[Country]), List[(TopicId, Doublelon)])] = {
    val numProducelonrsWithLocalelons = Stat("num_producelonrs_with_localelons")
    producelonrToTopics
      .map {
        caselon (producelonrId, (topicId, languagelonOpt, countryOpt), scorelon) =>
          ((producelonrId, languagelonOpt, countryOpt), Selonq((topicId, scorelon)))
      }.sumByKelony.mapValuelons { topicsList: Selonq[(TopicId, Doublelon)] =>
        numProducelonrsWithLocalelons.inc()
        topicsList
          .filtelonr(_._2 >= MinProducelonrTopicScorelonThrelonshold).sortBy(-_._2).takelon(
            maxTopicsPelonrProducelonrPelonrLocalelon).toList
      }.toTypelondPipelon
  }

  delonf gelontTopLocalelonTopicsForProducelonrsFromelonM(
    uselonrUselonrGraph: TypelondPipelon[UselonrAndNelonighbors],
    followelondTopicsToUselonrs: TypelondPipelon[(TopicId, UselonrId)],
    uselonrSourcelon: TypelondPipelon[(UselonrId, (Country, Languagelon))],
    uselonrLanguagelons: TypelondPipelon[(UselonrId, Selonq[(Languagelon, Doublelon)])],
    minActivelonFollowelonrsForProducelonr: Int,
    minTopicFollowsThrelonshold: Int,
    lambda: Doublelon,
    numelonMStelonps: Int
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): TypelondPipelon[(ProducelonrId, (TopicId, Option[Languagelon], Option[Country]), Doublelon)] = {

    // Obtain Producelonr To Uselonrs Matrix
    val producelonrsToUselonrsMatrix: SparselonMatrix[ProducelonrId, UselonrId, Doublelon] =
      TopicsForProducelonrsUtils.gelontProducelonrsToFollowelondByUselonrsSparselonMatrix(
        uselonrUselonrGraph,
        minActivelonFollowelonrsForProducelonr)

    // Obtain Uselonrs to TopicsWithLocalelons Matrix
    val topicToUselonrsMatrix: SparselonMatrix[
      (TopicId, Option[Languagelon], Option[Country]),
      UselonrId,
      Doublelon
    ] = TopicsForProducelonrsUtils.gelontFollowelondTopicsToUselonrSparselonMatrix(
      followelondTopicsToUselonrs,
      uselonrSourcelon,
      uselonrLanguagelons,
      minTopicFollowsThrelonshold)

    // Domain input probability distribution is thelon Map(topics->followelonrs) pelonr producelonr localelon
    val domainInputModelonl = producelonrsToUselonrsMatrix
      .multiplySparselonMatrix(topicToUselonrsMatrix.transposelon).toTypelondPipelon.map {
        caselon (producelonrId, (topicId, languagelonOpt, countryOpt), dotProduct) =>
          ((producelonrId, languagelonOpt, countryOpt), Map(topicId -> dotProduct))
      }.sumByKelony.toTypelondPipelon.map {
        caselon ((producelonrId, languagelonOpt, countryOpt), topicsDomainInputMap) =>
          ((languagelonOpt, countryOpt), (producelonrId, topicsDomainInputMap))
      }

    // BackgroundModelonl is thelon Map(topics -> elonxpelonctelond valuelon of thelon numbelonr of uselonrs who follow thelon topic)
    val backgroundModelonl = topicToUselonrsMatrix.rowL1Norms.map {
      caselon ((topicId, languagelonOpt, countryOpt), numFollowelonrsOfTopic) =>
        ((languagelonOpt, countryOpt), Map(topicId -> numFollowelonrsOfTopic))
    }.sumByKelony

    val relonsultsFromelonMForelonachLocalelon = domainInputModelonl.hashJoin(backgroundModelonl).flatMap {
      caselon (
            (languagelonOpt, countryOpt),
            ((producelonrId, domainInputTopicFollowelonrsMap), backgroundModelonlTopicFollowelonrsMap)) =>
        val elonmScorelondTopicsForelonachProducelonrPelonrLocalelon = elonMRunnelonr.elonstimatelonDomainModelonl(
          domainInputTopicFollowelonrsMap,
          backgroundModelonlTopicFollowelonrsMap,
          lambda,
          numelonMStelonps)

        elonmScorelondTopicsForelonachProducelonrPelonrLocalelon.map {
          caselon (topicId, topicScorelon) =>
            (producelonrId, (topicId, languagelonOpt, countryOpt), topicScorelon)
        }
    }
    relonsultsFromelonMForelonachLocalelon
  }
}
