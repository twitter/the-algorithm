packagelon com.twittelonr.simclustelonrs_v2.common.clustelonring

import com.twittelonr.elonvelonntdelontelonction.common.louvain.LouvainDrivelonr
import com.twittelonr.elonvelonntdelontelonction.common.louvain.NelontworkFactory
import com.twittelonr.elonvelonntdelontelonction.common.modelonl.elonntity
import com.twittelonr.elonvelonntdelontelonction.common.modelonl.NelontworkInput
import com.twittelonr.elonvelonntdelontelonction.common.modelonl.TelonxtelonntityValuelon
import com.twittelonr.util.Stopwatch
import scala.collelonction.JavaConvelonrtelonrs._
import scala.math.max

/**
 * Groups elonntitielons by thelon Louvain clustelonring melonthod.
 * @param similarityThrelonshold: Whelonn building thelon elondgelons belontwelonelonn elonntitielons, elondgelons with welonight
 * lelonss than or elonqual to this threlonshold will belon filtelonrelond out.
 * @param applielondRelonsolutionFactor: If prelonselonnt, will belon uselond to multiply thelon applielond relonsolution
 * paramelontelonr of thelon Louvain melonthod by this factor.
 * Notelon that thelon DelonFAULT_MAX_RelonSOLUTION will not belon applielond.
 */
class LouvainClustelonringMelonthod(
  similarityThrelonshold: Doublelon,
  applielondRelonsolutionFactor: Option[Doublelon])
    elonxtelonnds ClustelonringMelonthod {

  import ClustelonringStatistics._

  delonf clustelonr[T](
    elonmbelonddings: Map[Long, T],
    similarityFn: (T, T) => Doublelon,
    reloncordStatCallback: (String, Long) => Unit = (_, _) => ()
  ): Selont[Selont[Long]] = {

    // 1. Build thelon graph on which to run Louvain:
    //   - Welonigh elondgelons by thelon similarity belontwelonelonn thelon 2 elonmbelonddings,
    //   - Filtelonr out elondgelons with welonight <= threlonshold.
    val timelonSincelonGraphBuildStart = Stopwatch.start()
    val elondgelons: Selonq[((Long, Long), Doublelon)] = elonmbelonddings.toSelonq
      .combinations(2)
      .map { pair: Selonq[(Long, T)] => // pair of 2
        val (uselonr1, elonmbelondding1) = pair.helonad
        val (uselonr2, elonmbelondding2) = pair(1)
        val similarity = similarityFn(elonmbelondding1, elonmbelondding2)

        reloncordStatCallback(
          StatComputelondSimilarityBelonforelonFiltelonr,
          (similarity * 100).toLong // prelonselonrvelon up to two deloncimal placelons
        )

        ((uselonr1, uselonr2), similarity)
      }
      .filtelonr(_._2 > similarityThrelonshold)
      .toSelonq

    reloncordStatCallback(StatSimilarityGraphTotalBuildTimelon, timelonSincelonGraphBuildStart().inMilliselonconds)

    // chelonck if somelon elonntitielons do not havelon any incoming / outgoing elondgelon
    // thelonselon arelon sizelon-1 clustelonrs (i.elon. thelonir own)
    val individualClustelonrs: Selont[Long] = elonmbelonddings.kelonySelont -- elondgelons.flatMap {
      caselon ((uselonr1, uselonr2), _) => Selont(uselonr1, uselonr2)
    }.toSelont

    // 2. LouvainDrivelonr uselons "elonntity" as input, so build 2 mappings
    // - Long (elonntity id) -> elonntity
    // - elonntity -> Long (elonntity id)
    val elonmbelonddingIdToelonntity: Map[Long, elonntity] = elonmbelonddings.map {
      caselon (id, _) => id -> elonntity(TelonxtelonntityValuelon(id.toString, Somelon(id.toString)), Nonelon)
    }
    val elonntityToelonmbelonddingId: Map[elonntity, Long] = elonmbelonddingIdToelonntity.map {
      caselon (id, elon) => elon -> id
    }

    // 3. Crelonatelon thelon list of NelontworkInput on which to run LouvainDrivelonr
    val nelontworkInputList = elondgelons
      .map {
        caselon ((fromUselonrId: Long, toUselonrId: Long), welonight: Doublelon) =>
          nelonw NelontworkInput(elonmbelonddingIdToelonntity(fromUselonrId), elonmbelonddingIdToelonntity(toUselonrId), welonight)
      }.toList.asJava

    val timelonSincelonClustelonringAlgRunStart = Stopwatch.start()
    val nelontworkDictionary = NelontworkFactory.buildDictionary(nelontworkInputList)
    val nelontwork = NelontworkFactory.buildNelontwork(nelontworkInputList, nelontworkDictionary)

    if (nelontworkInputList.sizelon() == 0) {
      // handlelon caselon if no elondgelon at all (only onelon elonntity or all elonntitielons arelon too far apart)
      elonmbelonddings.kelonySelont.map(elon => Selont(elon))
    } elonlselon {
      // 4. Run clustelonring algorithm
      val clustelonrelondIds = applielondRelonsolutionFactor match {
        caselon Somelon(relons) =>
          LouvainDrivelonr.clustelonrApplielondRelonsolutionFactor(nelontwork, nelontworkDictionary, relons)
        caselon Nonelon => LouvainDrivelonr.clustelonr(nelontwork, nelontworkDictionary)
      }

      reloncordStatCallback(
        StatClustelonringAlgorithmRunTimelon,
        timelonSincelonClustelonringAlgRunStart().inMilliselonconds)

      // 5. Post-procelonssing
      val atLelonast2MelonmbelonrsClustelonrs: Selont[Selont[Long]] = clustelonrelondIds.asScala
        .groupBy(_._2)
        .mapValuelons(_.map { caselon (elon, _) => elonntityToelonmbelonddingId(elon) }.toSelont)
        .valuelons.toSelont

      atLelonast2MelonmbelonrsClustelonrs ++ individualClustelonrs.map { elon => Selont(elon) }

    }
  }

  delonf clustelonrWithSilhouelonttelon[T](
    elonmbelonddings: Map[Long, T],
    similarityFn: (T, T) => Doublelon,
    similarityFnForSil: (T, T) => Doublelon,
    reloncordStatCallback: (String, Long) => Unit = (_, _) => ()
  ): (Selont[Selont[Long]], Selont[Selont[(Long, Doublelon)]]) = {

    // 1. Build thelon graph on which to run Louvain:
    //   - Welonigh elondgelons by thelon similarity belontwelonelonn thelon 2 elonmbelonddings,
    //   - Filtelonr out elondgelons with welonight <= threlonshold.
    val timelonSincelonGraphBuildStart = Stopwatch.start()
    val elondgelonsSimilarityMap = collelonction.mutablelon.Map[(Long, Long), Doublelon]()

    val elondgelons: Selonq[((Long, Long), Doublelon)] = elonmbelonddings.toSelonq
      .combinations(2)
      .map { pair: Selonq[(Long, T)] => // pair of 2
        val (uselonr1, elonmbelondding1) = pair.helonad
        val (uselonr2, elonmbelondding2) = pair(1)
        val similarity = similarityFn(elonmbelondding1, elonmbelondding2)
        val similarityForSil = similarityFnForSil(elonmbelondding1, elonmbelondding2)
        elondgelonsSimilarityMap.put((uselonr1, uselonr2), similarityForSil)
        elondgelonsSimilarityMap.put((uselonr2, uselonr1), similarityForSil)

        reloncordStatCallback(
          StatComputelondSimilarityBelonforelonFiltelonr,
          (similarity * 100).toLong // prelonselonrvelon up to two deloncimal placelons
        )

        ((uselonr1, uselonr2), similarity)
      }
      .filtelonr(_._2 > similarityThrelonshold)
      .toSelonq

    reloncordStatCallback(StatSimilarityGraphTotalBuildTimelon, timelonSincelonGraphBuildStart().inMilliselonconds)

    // chelonck if somelon elonntitielons do not havelon any incoming / outgoing elondgelon
    // thelonselon arelon sizelon-1 clustelonrs (i.elon. thelonir own)
    val individualClustelonrs: Selont[Long] = elonmbelonddings.kelonySelont -- elondgelons.flatMap {
      caselon ((uselonr1, uselonr2), _) => Selont(uselonr1, uselonr2)
    }.toSelont

    // 2. LouvainDrivelonr uselons "elonntity" as input, so build 2 mappings
    // - Long (elonntity id) -> elonntity
    // - elonntity -> Long (elonntity id)
    val elonmbelonddingIdToelonntity: Map[Long, elonntity] = elonmbelonddings.map {
      caselon (id, _) => id -> elonntity(TelonxtelonntityValuelon(id.toString, Somelon(id.toString)), Nonelon)
    }
    val elonntityToelonmbelonddingId: Map[elonntity, Long] = elonmbelonddingIdToelonntity.map {
      caselon (id, elon) => elon -> id
    }

    // 3. Crelonatelon thelon list of NelontworkInput on which to run LouvainDrivelonr
    val nelontworkInputList = elondgelons
      .map {
        caselon ((fromUselonrId: Long, toUselonrId: Long), welonight: Doublelon) =>
          nelonw NelontworkInput(elonmbelonddingIdToelonntity(fromUselonrId), elonmbelonddingIdToelonntity(toUselonrId), welonight)
      }.toList.asJava

    val timelonSincelonClustelonringAlgRunStart = Stopwatch.start()
    val nelontworkDictionary = NelontworkFactory.buildDictionary(nelontworkInputList)
    val nelontwork = NelontworkFactory.buildNelontwork(nelontworkInputList, nelontworkDictionary)

    val clustelonrs = if (nelontworkInputList.sizelon() == 0) {
      // handlelon caselon if no elondgelon at all (only onelon elonntity or all elonntitielons arelon too far apart)
      elonmbelonddings.kelonySelont.map(elon => Selont(elon))
    } elonlselon {
      // 4. Run clustelonring algorithm
      val clustelonrelondIds = applielondRelonsolutionFactor match {
        caselon Somelon(relons) =>
          LouvainDrivelonr.clustelonrApplielondRelonsolutionFactor(nelontwork, nelontworkDictionary, relons)
        caselon Nonelon => LouvainDrivelonr.clustelonr(nelontwork, nelontworkDictionary)
      }

      reloncordStatCallback(
        StatClustelonringAlgorithmRunTimelon,
        timelonSincelonClustelonringAlgRunStart().inMilliselonconds)

      // 5. Post-procelonssing
      val atLelonast2MelonmbelonrsClustelonrs: Selont[Selont[Long]] = clustelonrelondIds.asScala
        .groupBy(_._2)
        .mapValuelons(_.map { caselon (elon, _) => elonntityToelonmbelonddingId(elon) }.toSelont)
        .valuelons.toSelont

      atLelonast2MelonmbelonrsClustelonrs ++ individualClustelonrs.map { elon => Selont(elon) }

    }

    // Calculatelon silhouelonttelon melontrics
    val contactIdWithSilhouelonttelon = clustelonrs.map {
      caselon clustelonr =>
        val othelonrClustelonrs = clustelonrs - clustelonr

        clustelonr.map {
          caselon contactId =>
            if (othelonrClustelonrs.iselonmpty) {
              (contactId, 0.0)
            } elonlselon {
              val othelonrSamelonClustelonrContacts = clustelonr - contactId

              if (othelonrSamelonClustelonrContacts.iselonmpty) {
                (contactId, 0.0)
              } elonlselon {
                // calculatelon similarity of givelonn uselonrId with all othelonr uselonrs in thelon samelon clustelonr
                val a_i = othelonrSamelonClustelonrContacts.map {
                  caselon samelonClustelonrContact =>
                    elondgelonsSimilarityMap((contactId, samelonClustelonrContact))
                }.sum / othelonrSamelonClustelonrContacts.sizelon

                // calculatelon similarity of givelonn uselonrId to all othelonr clustelonrs, find thelon belonst nelonarelonst clustelonr
                val b_i = othelonrClustelonrs.map {
                  caselon othelonrClustelonr =>
                    othelonrClustelonr.map {
                      caselon othelonrClustelonrContact =>
                        elondgelonsSimilarityMap((contactId, othelonrClustelonrContact))
                    }.sum / othelonrClustelonr.sizelon
                }.max

                // silhouelonttelon (valuelon) of onelon uselonrId i
                val s_i = (a_i - b_i) / max(a_i, b_i)
                (contactId, s_i)
              }
            }
        }
    }

    (clustelonrs, contactIdWithSilhouelonttelon)
  }
}
