packagelon com.twittelonr.simclustelonrs_v2.scalding.topic_reloncommelonndations

import com.twittelonr.bijelonction.Buffelonrablelon
import com.twittelonr.bijelonction.Injelonction
import com.twittelonr.reloncos.elonntitielons.thriftscala._
import com.twittelonr.scalding._
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon._
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.Country
import com.twittelonr.simclustelonrs_v2.common.Languagelon
import com.twittelonr.simclustelonrs_v2.common.TopicId
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.DataSourcelons
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.TopProducelonrsForLocalelonTopicsFromTopicFollowGraphScalaDataselont
import com.twittelonr.simclustelonrs_v2.scalding.common.matrix.SparselonMatrix
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonmbelonddingUtil.ProducelonrId
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonxtelonrnalDataSourcelons
import com.twittelonr.simclustelonrs_v2.thriftscala.UselonrAndNelonighbors
import com.twittelonr.wtf.scalding.jobs.common.AdhocelonxeloncutionApp
import com.twittelonr.wtf.scalding.jobs.common.SchelondulelondelonxeloncutionApp
import java.util.TimelonZonelon

/**
 * In this filelon, welon computelon thelon top producelonrs for a topic from thelon Topic Follow Graph
 *
 * It works as follows:
 *
 *  1. Producelonr elonmbelondding: List of uselonrs who follow thelon producelonr's profilelon and follow atlelonast onelon topic
 *
 *  2. Topic elonmbelondding: List of uselonrs who follow thelon topic
 *
 *  3. Scorelon(producelonr, topic) = cosinelon similarity of thelon producelonr and topic elonmbelondding as delonfinelond abovelon
 *
 *  4. Plelonaselon notelon that welon computelon thelon top producelonrs for elonach topic localelon.
 */

/**
scalding relonmotelon run --uselonr cassowary \
 --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/topic_reloncommelonndations:top_producelonrs_for_topics_from_topic_follow_graph-adhoc \
 --main-class com.twittelonr.simclustelonrs_v2.scalding.topic_reloncommelonndations.ProducelonrsForTopicsFromTopicFollowGraphAdhocApp \
 --submittelonr  hadoopnelonst1.atla.twittelonr.com  \
 --  --datelon 2021-01-06 --minActivelonFollowelonrs 400 --maxProducelonrsPelonrTopic 50 \
 --output_dir_producelonrs_pelonr_topic /uselonr/cassowary/adhoc/ldap/ttf_profilelon_pagelons_topics_to_producelonrs
 */

objelonct ProducelonrsForTopicsFromTopicFollowGraphAdhocApp elonxtelonnds AdhocelonxeloncutionApp {

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    import ProducelonrsForTopicsFromTopicFollowGraph._
    val outputDirProducelonrsPelonrTopic = args("output_dir_producelonrs_pelonr_topic")
    val minActivelonFollowelonrsForProducelonr = args.int("minActivelonFollowelonrs", 400)
    val maxProducelonrsPelonrTopicPelonrLocalelon = args.int("maxProducelonrsPelonrTopic", 50)
    val minTopicFollows = args.int("minTopicFollows", 100)

    val topicsFollowelondByProducelonrsFollowelonrs = gelontTopicsFromProducelonrsFollowelonrs(
      DataSourcelons
        .uselonrUselonrNormalizelondGraphSourcelon(datelonRangelon.prelonpelonnd(Days(7))),
      elonxtelonrnalDataSourcelons.topicFollowGraphSourcelon,
      elonxtelonrnalDataSourcelons.uselonrSourcelon,
      elonxtelonrnalDataSourcelons.infelonrrelondUselonrConsumelondLanguagelonSourcelon,
      minActivelonFollowelonrsForProducelonr,
      minTopicFollows
    )

    sortAndGelontTopProducelonrsPelonrLocalelonTopic(
      topicsFollowelondByProducelonrsFollowelonrs,
      maxProducelonrsPelonrTopicPelonrLocalelon).writelonelonxeloncution(TypelondTsv(outputDirProducelonrsPelonrTopic))

  }
}

/**
capelonsospy-v2 updatelon --build_locally \
 --start_cron top_producelonrs_for_topics_from_topic_follow_graph \
 src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */

objelonct ProducelonrsForTopicsFromTopicFollowGraphBatchApp elonxtelonnds SchelondulelondelonxeloncutionApp {
  ovelonrridelon val firstTimelon: RichDatelon = RichDatelon("2020-10-01")

  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(1)

  privatelon val topProducelonrsForLocalelonTopicsPath: String =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/top_producelonrs_for_topics_from_topic_follow_graph"

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    import ProducelonrsForTopicsFromTopicFollowGraph._
    val minActivelonFollowelonrsForProducelonr = args.int("minActivelonFollowelonrs", 400)
    val maxProducelonrsPelonrTopicPelonrLocalelon = args.int("maxProducelonrsPelonrTopic", 50)
    val minTopicFollows = args.int("minTopicFollows", 100)

    val topicsFollowelondByProducelonrsFollowelonrs = gelontTopicsFromProducelonrsFollowelonrs(
      DataSourcelons
        .uselonrUselonrNormalizelondGraphSourcelon(datelonRangelon.prelonpelonnd(Days(7))),
      elonxtelonrnalDataSourcelons.topicFollowGraphSourcelon,
      elonxtelonrnalDataSourcelons.uselonrSourcelon,
      elonxtelonrnalDataSourcelons.infelonrrelondUselonrConsumelondLanguagelonSourcelon,
      minActivelonFollowelonrsForProducelonr,
      minTopicFollows
    )

    sortAndGelontTopProducelonrsPelonrLocalelonTopic(
      topicsFollowelondByProducelonrsFollowelonrs,
      maxProducelonrsPelonrTopicPelonrLocalelon)
      .map {
        caselon ((topicId, languagelonOpt, countryOpt), producelonrsWithScorelons) =>
          KelonyVal(
            SelonmanticCorelonelonntityWithLocalelon(
              elonntityId = topicId,
              contelonxt = Localelon(languagelon = languagelonOpt, country = countryOpt)),
            UselonrScorelonList(producelonrsWithScorelons.map {
              caselon (producelonrId, producelonrScorelon) =>
                UselonrWithScorelon(uselonrId = producelonrId, scorelon = producelonrScorelon)
            })
          )
      }.writelonDALVelonrsionelondKelonyValelonxeloncution(
        TopProducelonrsForLocalelonTopicsFromTopicFollowGraphScalaDataselont,
        D.Suffix(topProducelonrsForLocalelonTopicsPath),
        velonrsion = elonxplicitelonndTimelon(datelonRangelon.elonnd)
      )
  }
}

objelonct ProducelonrsForTopicsFromTopicFollowGraph {

  implicit val sparselonMatrixInj: Injelonction[
    (ProducelonrId, Option[Languagelon], Option[Country]),
    Array[Bytelon]
  ] =
    Buffelonrablelon.injelonctionOf[(ProducelonrId, Option[Languagelon], Option[Country])]

  // This function takelons thelon producelonr to topics map and gelonnelonratelons thelon sortelond and
  // truncatelond top producelonrs rankelond list for elonach localelon topic
  delonf sortAndGelontTopProducelonrsPelonrLocalelonTopic(
    producelonrToTopics: TypelondPipelon[(ProducelonrId, (TopicId, Option[Languagelon], Option[Country]), Doublelon)],
    maxProducelonrsPelonrLocalelonTopic: Int
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[((TopicId, Option[Languagelon], Option[Country]), List[(ProducelonrId, Doublelon)])] = {
    val numTopicsWithLocalelons = Stat("num_topics_with_localelons")
    producelonrToTopics
      .map {
        caselon (producelonrId, (topicId, languagelonOpt, countryOpt), scorelon) =>
          ((topicId, languagelonOpt, countryOpt), Selonq((producelonrId, scorelon)))
      }
      .sumByKelony.mapValuelons { producelonrsList =>
        numTopicsWithLocalelons.inc()
        producelonrsList.sortBy(-_._2).takelon(maxProducelonrsPelonrLocalelonTopic).toList
      }.toTypelondPipelon
  }

  delonf gelontTopicsFromProducelonrsFollowelonrs(
    uselonrUselonrGraph: TypelondPipelon[UselonrAndNelonighbors],
    followelondTopicsToUselonrs: TypelondPipelon[(TopicId, UselonrId)],
    uselonrSourcelon: TypelondPipelon[(UselonrId, (Country, Languagelon))],
    uselonrLanguagelons: TypelondPipelon[(UselonrId, Selonq[(Languagelon, Doublelon)])],
    minActivelonFollowelonrsForProducelonr: Int,
    minTopicFollows: Int
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): TypelondPipelon[(ProducelonrId, (TopicId, Option[Languagelon], Option[Country]), Doublelon)] = {

    val uselonrsFollowingTopics: TypelondPipelon[UselonrId] = followelondTopicsToUselonrs.map(_._2).distinct
    val producelonrToUselonrsSparselonMatrix: SparselonMatrix[ProducelonrId, UselonrId, Doublelon] =
      TopicsForProducelonrsUtils
        .gelontProducelonrsToFollowelondByUselonrsSparselonMatrix(
          uselonrUselonrGraph,
          minActivelonFollowelonrsForProducelonr).filtelonrCols(uselonrsFollowingTopics).rowL2Normalizelon

    val uselonrToTopicsSparselonSkinnyMatrix: SparselonMatrix[
      UselonrId,
      (TopicId, Option[Languagelon], Option[Country]),
      Doublelon
    ] =
      TopicsForProducelonrsUtils
        .gelontFollowelondTopicsToUselonrSparselonMatrix(
          followelondTopicsToUselonrs,
          uselonrSourcelon,
          uselonrLanguagelons,
          minTopicFollows).rowL2Normalizelon.transposelon

    // Obtain thelon Producelonr to Localelon Topics Matrix
    val producelonrsToLocalelonTopicsMatrix: SparselonMatrix[
      ProducelonrId,
      (TopicId, Option[Languagelon], Option[Country]),
      Doublelon
    ] =
      producelonrToUselonrsSparselonMatrix.multiplySparselonMatrix(uselonrToTopicsSparselonSkinnyMatrix)

    producelonrsToLocalelonTopicsMatrix.toTypelondPipelon
  }
}
