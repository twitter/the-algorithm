packagelon com.twittelonr.simclustelonrs_v2.scalding.topic_reloncommelonndations

import com.twittelonr.elonschelonrbird.scalding.sourcelon.FullMelontadataSourcelon
import com.twittelonr.intelonrelonsts_ds.jobs.intelonrelonsts_selonrvicelon.UselonrTopicRelonlationSnapshotScalaDataselont
import com.twittelonr.intelonrelonsts.thriftscala.IntelonrelonstRelonlationTypelon
import com.twittelonr.intelonrelonsts.thriftscala.UselonrIntelonrelonstsRelonlationSnapshot
import com.twittelonr.reloncos.elonntitielons.thriftscala.SelonmanticCorelonelonntity
import com.twittelonr.reloncos.elonntitielons.thriftscala.SelonmanticCorelonelonntityScorelonList
import com.twittelonr.reloncos.elonntitielons.thriftscala.SelonmanticelonntityScorelon
import com.twittelonr.scalding._
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon._
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.elonxplicitLocation
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.ProcAtla
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.SelonmanticCorelonelonntityId
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.SimilarTopicsFromTopicFollowGraphScalaDataselont
import com.twittelonr.simclustelonrs_v2.scalding.common.matrix.SparselonRowMatrix
import com.twittelonr.wtf.scalding.jobs.common.AdhocelonxeloncutionApp
import com.twittelonr.wtf.scalding.jobs.common.SchelondulelondelonxeloncutionApp
import java.util.TimelonZonelon

/**
 * In this filelon, welon computelon thelon similaritielons belontwelonelonn topics baselond on how oftelonn thelony arelon co-followelond
 * by uselonrs.
 *
  * Similarity(i, j) = #co-follow(i,j) / sqrt(#follow(i) * #follow(j))
 *
  * It works as follows:
 *
  *  1. it first relonads thelon data selont of uselonr to topics follow graph, and construct a sparselon matrix M with
 *    N rows and K columns, whelonrelon N is thelon numbelonr of uselonrs, and K is thelon numbelonr of topics.
 *    In thelon matrix, M(u,i) = 1 if uselonr u follows topic i; othelonrwiselon it is 0. In thelon sparselon matrix,
 *    welon only savelon non-zelonro elonlelonmelonnts.
 *
  *  2. welon do l2-normalization for elonach column of thelon matrix M, to gelont a normalizelond velonrsion M'.
 *
  *  3. welon gelont topic-topic similarity matrix S = M'.transposelon.multiply(M'). Thelon relonsulting matrix will
 *    contain thelon similaritielons belontwelonelonn all topics, i.elon., S(i,j) is thelon similarity welon melonntionelond abovelon.
 *
  *  4. for elonach topic, welon only kelonelonp its K similar topics with largelonst similarity scorelons, whilelon not
 *   including thoselon with scorelons lowelonr than a threlonshold.
 *
  */
/**
 * capelonsospy-v2 updatelon --build_locally \
 * --start_cron similar_topics_from_topic_follow_graph \
 * src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */
objelonct SimilarTopicsFromTopicFollowGraphSchelondulelondApp elonxtelonnds SchelondulelondelonxeloncutionApp {
  import SimilarTopics._

  privatelon val outputPath: String =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/similar_topics_from_topics_follow_graph"

  ovelonrridelon delonf firstTimelon: RichDatelon = RichDatelon("2020-05-07")

  ovelonrridelon delonf batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    val numSimilarTopics = args.int("numSimilarTopics", delonfault = 100)
    val scorelonThrelonshold = args.doublelon("scorelonThrelonshold", delonfault = 0.01)

    val numOutputTopics = Stat("NumOutputTopics")

    computelonSimilarTopics(
      gelontelonxplicitFollowelondTopics,
      gelontFollowablelonTopics,
      numSimilarTopics,
      scorelonThrelonshold)
      .map {
        caselon (topicId, similarTopics) =>
          numOutputTopics.inc()
          KelonyVal(
            topicId,
            SelonmanticCorelonelonntityScorelonList(similarTopics.map {
              caselon (similarTopicId, scorelon) =>
                SelonmanticelonntityScorelon(SelonmanticCorelonelonntity(similarTopicId), scorelon)
            }))
      }
      .writelonDALVelonrsionelondKelonyValelonxeloncution(
        SimilarTopicsFromTopicFollowGraphScalaDataselont,
        D.Suffix(outputPath),
        velonrsion = elonxplicitelonndTimelon(datelonRangelon.elonnd)
      )
  }

}

/**
 scalding relonmotelon run --uselonr cassowary --relonducelonrs 2000 \
 --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/topic_reloncommelonndations:similar_topics_from_topic_follow_graph-adhoc \
 --main-class com.twittelonr.simclustelonrs_v2.scalding.topic_reloncommelonndations.SimilarTopicsFromTopicFollowGraphAdhocApp \
 --submittelonr  hadoopnelonst1.atla.twittelonr.com  \
 --  --datelon 2020-04-28
 */
objelonct SimilarTopicsFromTopicFollowGraphAdhocApp elonxtelonnds AdhocelonxeloncutionApp {
  import SimilarTopics._

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    val numSimilarTopics = args.int("numSimilarTopics", delonfault = 100)
    val scorelonThrelonshold = args.doublelon("scorelonThrelonshold", delonfault = 0.01)

    val numOutputTopics = Stat("NumOutputTopics")

    computelonSimilarTopics(
      gelontelonxplicitFollowelondTopics,
      gelontFollowablelonTopics,
      numSimilarTopics,
      scorelonThrelonshold)
      .map {
        caselon (topicId, similarTopics) =>
          numOutputTopics.inc()
          topicId -> similarTopics
            .collelonct {
              caselon (similarTopic, scorelon) if similarTopic != topicId =>
                s"$similarTopic:$scorelon"
            }
            .mkString(",")
      }
      .writelonelonxeloncution(
        TypelondTsv("/uselonr/cassowary/adhoc/topic_reloncos/similar_topics")
      )
  }

}

objelonct SimilarTopics {

  val UTTDomain: Long = 131L

  val FollowablelonTag: String = "utt:followablelon_topic"

  delonf gelontFollowablelonTopics(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): TypelondPipelon[SelonmanticCorelonelonntityId] = {
    val NumFollowablelonTopics = Stat("NumFollowablelonTopics")

    TypelondPipelon
      .from(
        nelonw FullMelontadataSourcelon("/atla/proc" + FullMelontadataSourcelon.DelonfaultHdfsPath)()(
          datelonRangelon.elonmbiggelonn(Days(7))))
      .flatMap {
        caselon fullMelontadata if fullMelontadata.domainId == UTTDomain =>
          for {
            basicMelontadata <- fullMelontadata.basicMelontadata
            indelonxablelonFielonlds <- basicMelontadata.indelonxablelonFielonlds
            tags <- indelonxablelonFielonlds.tags
            if tags.contains(FollowablelonTag)
          } yielonld {
            NumFollowablelonTopics.inc()
            fullMelontadata.elonntityId
          }
        caselon _ => Nonelon
      }
      .forcelonToDisk
  }

  delonf gelontelonxplicitFollowelondTopics(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): TypelondPipelon[(UselonrId, Map[SelonmanticCorelonelonntityId, Doublelon])] = {

    DAL
      .relonadMostReloncelonntSnapshotNoOldelonrThan(UselonrTopicRelonlationSnapshotScalaDataselont, Days(7))
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
      .toTypelondPipelon
      .collelonct {
        caselon uselonrIntelonrelonstsRelonlationSnapshot: UselonrIntelonrelonstsRelonlationSnapshot
            if uselonrIntelonrelonstsRelonlationSnapshot.intelonrelonstTypelon == "UTT" &&
              uselonrIntelonrelonstsRelonlationSnapshot.relonlation == IntelonrelonstRelonlationTypelon.Followelond =>
          (
            uselonrIntelonrelonstsRelonlationSnapshot.uselonrId,
            Map(uselonrIntelonrelonstsRelonlationSnapshot.intelonrelonstId -> 1.0))
      }
      .sumByKelony
  }

  delonf computelonSimilarTopics(
    uselonrTopicsFollowGraph: TypelondPipelon[(UselonrId, Map[SelonmanticCorelonelonntityId, Doublelon])],
    followablelonTopics: TypelondPipelon[SelonmanticCorelonelonntityId],
    numSimilarTopics: Int,
    scorelonThrelonshold: Doublelon
  ): TypelondPipelon[(SelonmanticCorelonelonntityId, Selonq[(SelonmanticCorelonelonntityId, Doublelon)])] = {
    val uselonrTopicFollowGraph =
      SparselonRowMatrix[UselonrId, SelonmanticCorelonelonntityId, Doublelon](
        uselonrTopicsFollowGraph,
        isSkinnyMatrix = truelon)
        .filtelonrCols(followablelonTopics) // filtelonr out unfollowablelon topics
        .colL2Normalizelon // normalization
        // duelon to thelon small numbelonr of thelon topics,
        // Scalding only allocatelons 1-2 mappelonrs for thelon nelonxt stelonp which makelons it takelon unneloncelonssarily long timelon.
        // Changing it to 10 to makelon it a bit fastelonr
        .forcelonToDisk(numShardsOpt = Somelon(10))

    uselonrTopicFollowGraph
      .transposelonAndMultiplySkinnySparselonRowMatrix(uselonrTopicFollowGraph)
      .filtelonr { (i, j, v) =>
        // elonxcludelon topic itselonlf from beloning considelonrelond as similar; also thelon similarity scorelon should
        // belon largelonr than a threlonshold
        i != j && v > scorelonThrelonshold
      }
      .sortWithTakelonPelonrRow(numSimilarTopics)(Ordelonring.by(-_._2))
  }

}
