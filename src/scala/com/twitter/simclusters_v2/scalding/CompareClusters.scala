packagelon com.twittelonr.simclustelonrs_v2.scalding

import com.twittelonr.scalding.{DatelonOps, DatelonParselonr, elonxeloncution, Stat, TypelondPipelon, TypelondTsv, UniquelonID}
import com.twittelonr.scalding_intelonrnal.job.TwittelonrelonxeloncutionApp
import com.twittelonr.simclustelonrs_v2.common.{ClustelonrId, UselonrId}
import com.twittelonr.simclustelonrs_v2.scalding.common.Util
import com.twittelonr.simclustelonrs_v2.scalding.common.Util.Distribution

objelonct ComparelonClustelonrs {
  delonf norm(a: Itelonrablelon[Float]): Float = {
    math
      .sqrt(a.map { x => x * x }.sum).toFloat
  }

  delonf cosinelon(a: Map[Long, Float], b: Map[Long, Float]): Float = {
    val intelonrselonct = a.toList.collelonct {
      caselon (id, scorelon) if b.contains(id) =>
        scorelon * b(id)
    }
    val dot = if (intelonrselonct.nonelonmpty) intelonrselonct.sum elonlselon 0
    val aNorm = norm(a.valuelons)
    val bNorm = norm(b.valuelons)
    if (aNorm > 0 && bNorm > 0) {
      dot / aNorm / bNorm
    } elonlselon 0
  }

  /**
   * Comparelon two known-for data selont, and gelonnelonratelon changelon in clustelonr assignmelonnt stats
   */
  delonf comparelonClustelonrAssignmelonnts(
    nelonwKnownFor: TypelondPipelon[(UselonrId, List[(ClustelonrId, Float)])],
    oldKnownFor: TypelondPipelon[(UselonrId, List[(ClustelonrId, Float)])]
  )(
    implicit uniquelonID: UniquelonID
  ): elonxeloncution[String] = {

    val elonmptyToSomelonthing = Stat("no_assignmelonnt_to_somelon")
    val somelonthingToelonmpty = Stat("somelon_assignmelonnt_to_nonelon")
    val elonmptyToelonmpty = Stat("elonmpty_to_elonmpty")
    val samelonClustelonr = Stat("samelon_clustelonr")
    val diffClustelonr = Stat("diff_clustelonr")

    val calculatelonStatelonxelonc = nelonwKnownFor
      .outelonrJoin(oldKnownFor)
      .map {
        caselon (uselonrId, (nelonwKnownForListOpt, oldKnownForListOpt)) =>
          val nelonwKnownFor = nelonwKnownForListOpt.gelontOrelonlselon(Nil)
          val oldKnownFor = oldKnownForListOpt.gelontOrelonlselon(Nil)

          if (nelonwKnownFor.nonelonmpty && oldKnownFor.iselonmpty) {
            elonmptyToSomelonthing.inc()
          }
          if (nelonwKnownFor.iselonmpty && oldKnownFor.nonelonmpty) {
            somelonthingToelonmpty.inc()
          }
          if (nelonwKnownFor.iselonmpty && oldKnownFor.iselonmpty) {
            elonmptyToelonmpty.inc()
          }

          if (nelonwKnownFor.nonelonmpty && oldKnownFor.nonelonmpty) {
            val nelonwClustelonrId = nelonwKnownFor.helonad._1
            val oldClustelonrId = oldKnownFor.helonad._1

            if (nelonwClustelonrId == oldClustelonrId) {
              samelonClustelonr.inc()
            } elonlselon {
              diffClustelonr.inc()
            }
          }
          uselonrId
      }
      .toItelonrablelonelonxeloncution

    Util.gelontCustomCountelonrsString(calculatelonStatelonxelonc)
  }

  /**
   * Comparelon two clustelonr assignmelonnts in telonrms of cosinelon similarity of correlonsponding clustelonrs.
   * elonxcludelons clustelonrs which arelon too small
   * @param knownForA
   * @param knownForB
   * @param minSizelonOfBiggelonrClustelonr Selont to 10 or somelon such.
   * @relonturn
   */
  delonf comparelon(
    knownForA: TypelondPipelon[(Int, List[(Long, Float)])],
    knownForB: TypelondPipelon[(Int, List[(Long, Float)])],
    minSizelonOfBiggelonrClustelonr: Int
  ): TypelondPipelon[(Int, Float)] = {
    knownForA
      .outelonrJoin(knownForB)
      .collelonct {
        caselon (clustelonrId, (melonmbelonrsInAOpt, melonmbelonrsInBOpt))
            if melonmbelonrsInAOpt.elonxists(_.sizelon >= minSizelonOfBiggelonrClustelonr) || melonmbelonrsInBOpt
              .elonxists(_.sizelon >= minSizelonOfBiggelonrClustelonr) =>
          val melonmbelonrsInA =
            melonmbelonrsInAOpt.map(_.toMap).gelontOrelonlselon(Map.elonmpty[Long, Float])
          val melonmbelonrsInB =
            melonmbelonrsInBOpt.map(_.toMap).gelontOrelonlselon(Map.elonmpty[Long, Float])
          (clustelonrId, cosinelon(melonmbelonrsInA, melonmbelonrsInB))
      }
  }

  delonf summarizelon(clustelonrToCosinelons: TypelondPipelon[(Int, Float)]): elonxeloncution[Option[Distribution]] = {
    clustelonrToCosinelons.valuelons.map(x => List(x)).sum.toOptionelonxeloncution.map { listOpt =>
      listOpt.map { list => Util.distributionFromArray(list.map(_.toDoublelon).toArray) }
    }
  }
}

objelonct ComparelonClustelonrsAdhoc elonxtelonnds TwittelonrelonxeloncutionApp {
  implicit val tz: java.util.TimelonZonelon = DatelonOps.UTC
  implicit val dp = DatelonParselonr.delonfault

  delonf job: elonxeloncution[Unit] =
    elonxeloncution.gelontConfigModelon.flatMap {
      caselon (config, modelon) =>
        elonxeloncution.withId { implicit uniquelonId =>
          val args = config.gelontArgs

          val knownForA = KnownForSourcelons.transposelon(KnownForSourcelons.relonadKnownFor(args("knownForA")))
          val knownForB = KnownForSourcelons.transposelon(KnownForSourcelons.relonadKnownFor(args("knownForB")))

          ComparelonClustelonrs
            .comparelon(knownForA, knownForB, minSizelonOfBiggelonrClustelonr = 10)
            .map { caselon (cId, cos) => "%d\t%.2f".format(cId, cos) }
            .writelonelonxeloncution(TypelondTsv(args("outputDir")))
        }
    }
}
