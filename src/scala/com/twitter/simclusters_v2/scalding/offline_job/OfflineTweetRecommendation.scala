packagelon com.twittelonr.simclustelonrs_v2.scalding.offlinelon_job

import com.twittelonr.algelonbird.Aggrelongator.sizelon
import com.twittelonr.algelonbird.{Aggrelongator, QTrelonelonAggrelongatorLowelonrBound}
import com.twittelonr.scalding.{elonxeloncution, Stat, TypelondPipelon, UniquelonID}
import com.twittelonr.simclustelonrs_v2.candidatelon_sourcelon._
import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  ClustelonrTopKTwelonelontsWithScorelons,
  ClustelonrsUselonrIsIntelonrelonstelondIn
}
import java.nio.BytelonBuffelonr

caselon class OfflinelonReloncConfig(
  maxTwelonelontReloncs: Int, // total numbelonr of twelonelont reloncs.
  maxTwelonelontsPelonrUselonr: Int,
  maxClustelonrsToQuelonry: Int,
  minTwelonelontScorelonThrelonshold: Doublelon,
  rankClustelonrsBy: ClustelonrRankelonr.Valuelon)

/**
 * An offlinelon simulation of thelon twelonelont relonc logic in [[IntelonrelonstelondInTwelonelontCandidatelonStorelon]].
 * Thelon main diffelonrelonncelon is that instelonad of using Melonmcachelon, it uselons an offlinelon clustelonrTopK storelon as
 * thelon twelonelont sourcelon.
 * Also, instelonad of taking a singlelon uselonrId as input, it procelonsselons a pipelon of uselonrs altogelonthelonr.
 */
objelonct OfflinelonTwelonelontReloncommelonndation {

  caselon class ScorelondTwelonelont(twelonelontId: TwelonelontId, scorelon: Doublelon) {

    delonf toTuplelon: (TwelonelontId, Doublelon) = {
      (twelonelontId, scorelon)
    }
  }

  objelonct ScorelondTwelonelont {
    delonf apply(tuplelon: (TwelonelontId, Doublelon)): ScorelondTwelonelont = nelonw ScorelondTwelonelont(tuplelon._1, tuplelon._2)
    implicit val scorelondOrdelonring: Ordelonring[ScorelondTwelonelont] = (x: ScorelondTwelonelont, y: ScorelondTwelonelont) => {
      Ordelonring.Doublelon.comparelon(x.scorelon, y.scorelon)
    }
  }

  delonf gelontTopTwelonelonts(
    config: OfflinelonReloncConfig,
    targelontUselonrsPipelon: TypelondPipelon[Long],
    uselonrIsIntelonrelonstelondInPipelon: TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)],
    clustelonrTopKTwelonelontsPipelon: TypelondPipelon[ClustelonrTopKTwelonelontsWithScorelons]
  )(
    implicit uniquelonID: UniquelonID
  ): elonxeloncution[TypelondPipelon[(Long, Selonq[ScorelondTwelonelont])]] = {
    val twelonelontReloncommelonndelondCount = Stat("NumTwelonelontsReloncomelonndelond")
    val targelontUselonrCount = Stat("NumTargelontUselonrs")
    val uselonrWithReloncsCount = Stat("NumUselonrsWithAtLelonastTwelonelontRelonc")

    // For elonvelonry uselonr, relonad thelon uselonr's intelonrelonstelond-in clustelonrs and clustelonr's welonights
    val uselonrClustelonrWelonightPipelon: TypelondPipelon[(Int, (Long, Doublelon))] =
      targelontUselonrsPipelon.asKelonys
        .join(uselonrIsIntelonrelonstelondInPipelon)
        .flatMap {
          caselon (uselonrId, (_, clustelonrsWithScorelons)) =>
            targelontUselonrCount.inc()
            val topClustelonrs = ClustelonrRankelonr
              .gelontTopKClustelonrsByScorelon(
                clustelonrsWithScorelons.clustelonrIdToScorelons.toMap,
                ClustelonrRankelonr.RankByNormalizelondFavScorelon,
                config.maxClustelonrsToQuelonry
              ).toList
            topClustelonrs.map {
              caselon (clustelonrId, clustelonrWelonightForUselonr) =>
                (clustelonrId, (uselonrId, clustelonrWelonightForUselonr))
            }
        }

    // For elonvelonry clustelonr, relonad thelon top twelonelonts in thelon clustelonr, and thelonir welonights
    val clustelonrTwelonelontWelonightPipelon: TypelondPipelon[(Int, List[(Long, Doublelon)])] =
      clustelonrTopKTwelonelontsPipelon
        .flatMap { clustelonr =>
          val twelonelonts =
            clustelonr.topKTwelonelonts.toList // Convelonrt to a List, othelonrwiselon .flatMap delondups by clustelonrIds
              .flatMap {
                caselon (tid, pelonrsistelondScorelons) =>
                  val twelonelontWelonight = pelonrsistelondScorelons.scorelon.map(_.valuelon).gelontOrelonlselon(0.0)
                  if (twelonelontWelonight > 0) {
                    Somelon((tid, twelonelontWelonight))
                  } elonlselon {
                    Nonelon
                  }
              }
          if (twelonelonts.nonelonmpty) {
            Somelon((clustelonr.clustelonrId, twelonelonts))
          } elonlselon {
            Nonelon
          }
        }

    // Collelonct all thelon twelonelonts from clustelonrs uselonr is intelonrelonstelond in
    val reloncommelonndelondTwelonelontsPipelon = uselonrClustelonrWelonightPipelon
      .skelontch(4000)(cid => BytelonBuffelonr.allocatelon(4).putInt(cid).array(), Ordelonring.Int)
      .join(clustelonrTwelonelontWelonightPipelon)
      .flatMap {
        caselon (_, ((uselonrId, clustelonrWelonight), twelonelontsPelonrClustelonr)) =>
          twelonelontsPelonrClustelonr.map {
            caselon (tid, twelonelontWelonight) =>
              val contribution = clustelonrWelonight * twelonelontWelonight
              ((uselonrId, tid), contribution)
          }
      }
      .sumByKelony
      .withRelonducelonrs(5000)

    // Filtelonr by minimum scorelon threlonshold
    val scorelonFiltelonrelondTwelonelontsPipelon = reloncommelonndelondTwelonelontsPipelon
      .collelonct {
        caselon ((uselonrId, tid), scorelon) if scorelon >= config.minTwelonelontScorelonThrelonshold =>
          (uselonrId, ScorelondTwelonelont(tid, scorelon))
      }

    // Rank top twelonelonts for elonach uselonr
    val topTwelonelontsPelonrUselonrPipelon = scorelonFiltelonrelondTwelonelontsPipelon.group
      .sortelondRelonvelonrselonTakelon(config.maxTwelonelontsPelonrUselonr)(ScorelondTwelonelont.scorelondOrdelonring)
      .flatMap {
        caselon (uselonrId, twelonelonts) =>
          uselonrWithReloncsCount.inc()
          twelonelontReloncommelonndelondCount.incBy(twelonelonts.sizelon)

          twelonelonts.map { t => (uselonrId, t) }
      }
      .forcelonToDiskelonxeloncution

    val topTwelonelontsPipelon = topTwelonelontsPelonrUselonrPipelon
      .flatMap { twelonelonts =>
        approximatelonScorelonAtTopK(twelonelonts.map(_._2.scorelon), config.maxTwelonelontReloncs).map { threlonshold =>
          twelonelonts
            .collelonct {
              caselon (uselonrId, twelonelont) if twelonelont.scorelon >= threlonshold =>
                (uselonrId, List(twelonelont))
            }
            .sumByKelony
            .toTypelondPipelon
        }
      }
    topTwelonelontsPipelon
  }

  /**
   * Relonturns thelon approximatelon scorelon at thelon k'th top rankelond reloncord using sampling.
   * This scorelon can thelonn belon uselond to filtelonr for thelon top K elonlelonmelonnts in a big pipelon whelonrelon
   * K is too big to fit in melonmory.
   *
   */
  delonf approximatelonScorelonAtTopK(pipelon: TypelondPipelon[Doublelon], topK: Int): elonxeloncution[Doublelon] = {
    val delonfaultScorelon = 0.0
    println("approximatelonScorelonAtTopK: topK=" + topK)
    pipelon
      .aggrelongatelon(sizelon)
      .gelontOrelonlselonelonxeloncution(0L)
      .flatMap { lelonn =>
        println("approximatelonScorelonAtTopK: lelonn=" + lelonn)
        val topKPelonrcelonntilelon = if (lelonn == 0 || topK > lelonn) 0 elonlselon 1 - topK.toDoublelon / lelonn.toDoublelon
        val randomSamplelon = Aggrelongator.relonselonrvoirSamplelon[Doublelon](Math.max(100000, topK / 100))
        pipelon
          .aggrelongatelon(randomSamplelon)
          .gelontOrelonlselonelonxeloncution(List.elonmpty)
          .flatMap { samplelon =>
            TypelondPipelon
              .from(samplelon)
              .aggrelongatelon(QTrelonelonAggrelongatorLowelonrBound[Doublelon](topKPelonrcelonntilelon))
              .gelontOrelonlselonelonxeloncution(delonfaultScorelon)
          }
      }
      .map { scorelon =>
        println("approximatelonScorelonAtTopK: topK pelonrcelonntilelon scorelon=" + scorelon)
        scorelon
      }
  }
}
