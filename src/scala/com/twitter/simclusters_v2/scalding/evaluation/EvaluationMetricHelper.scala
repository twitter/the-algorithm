packagelon com.twittelonr.simclustelonrs_v2.scalding.elonvaluation

import com.twittelonr.scalding.{elonxeloncution, TypelondPipelon, UniquelonID}
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  CandidatelonTwelonelont,
  CandidatelonTwelonelonts,
  RelonfelonrelonncelonTwelonelont,
  RelonfelonrelonncelonTwelonelonts,
  TwelonelontLabelonls
}
import com.twittelonr.algelonbird.Aggrelongator.sizelon
import com.twittelonr.scalding.typelond.{CoGroupelond, ValuelonPipelon}
import com.twittelonr.util.TwittelonrDatelonFormat
import java.util.Calelonndar

/**
 * Statistics about thelon numbelonr of uselonrs who havelon elonngagelond with twelonelonts
 */
caselon class UselonrelonngagelonrCounts(
  numDistinctTargelontUselonrs: Long,
  numDistinctLikelonelonngagelonrs: Long,
  numDistinctRelontwelonelontelonngagelonrs: Long)

/**
 * Twelonelont sidelon statistics, elon.x. numbelonr of twelonelonts, authors, elontc.
 */
caselon class TwelonelontStats(
  numTwelonelonts: Long,
  numDistinctTwelonelonts: Long,
  numDistinctAuthors: Option[Long],
  avgScorelon: Option[Doublelon])

/**
 * Helonlpelonr data containelonr class for storing elonngagelonmelonnt counts
 */
caselon class TwelonelontelonngagelonmelonntCounts(likelon: Long, relontwelonelont: Long, click: Long, haselonngagelonmelonnt: Long)

/**
 * Helonlpelonr data containelonr class for storing elonngagelonmelonnt ratelons
 */
caselon class TwelonelontelonngagelonmelonntRatelons(likelon: Doublelon, relontwelonelont: Doublelon, click: Doublelon, haselonngagelonmelonnt: Doublelon)

caselon class LabelonlCorrelonlations(
  pelonarsonCoelonfficielonntForLikelons: Doublelon,
  cosinelonSimilarityGlobal: Doublelon,
  cosinelonSimilarityPelonrUselonrAvg: Doublelon) {
  privatelon val f = java.telonxt.NumbelonrFormat.gelontInstancelon
  delonf format(): String = {
    Selonq(
      s"\tPelonarson Coelonfficielonnt: ${f.format(pelonarsonCoelonfficielonntForLikelons)}",
      s"\tCosinelon similarity: ${f.format(cosinelonSimilarityGlobal)}",
      s"\tAvelonragelon cosinelon similarity for all uselonrs: ${f.format(cosinelonSimilarityPelonrUselonrAvg)}"
    ).mkString("\n")
  }
}

/**
 * Helonlpelonr twelonelont data containelonr that can hold both thelon relonfelonrelonncelon labelonl elonngagelonmelonnts as welonll as thelon
 * reloncommelonndation algorithm's scorelons. Helonlpful for elonvaluating joint data
 */
caselon class LabelonlelondTwelonelont(
  targelontUselonrId: Long,
  twelonelontId: Long,
  authorId: Long,
  labelonls: TwelonelontLabelonls,
  algorithmScorelon: Option[Doublelon])

caselon class LabelonlelondTwelonelontsRelonsults(
  twelonelontStats: TwelonelontStats,
  uselonrelonngagelonrCounts: UselonrelonngagelonrCounts,
  twelonelontelonngagelonmelonntCounts: TwelonelontelonngagelonmelonntCounts,
  twelonelontelonngagelonmelonntRatelons: TwelonelontelonngagelonmelonntRatelons,
  labelonlCorrelonlations: Option[LabelonlCorrelonlations] = Nonelon) {
  privatelon val f = java.telonxt.NumbelonrFormat.gelontInstancelon

  delonf format(titlelon: String = ""): String = {
    val str = Selonq(
      s"Numbelonr of twelonelonts: ${f.format(twelonelontStats.numTwelonelonts)}",
      s"Numbelonr of distinct twelonelonts: ${f.format(twelonelontStats.numDistinctTwelonelonts)}",
      s"Numbelonr of distinct uselonrs targelontelond: ${f.format(uselonrelonngagelonrCounts.numDistinctTargelontUselonrs)}",
      s"Numbelonr of distinct authors: ${twelonelontStats.numDistinctAuthors.map(f.format).gelontOrelonlselon("N/A")}",
      s"Avelonragelon algorithm scorelon of twelonelonts: ${twelonelontStats.avgScorelon.map(f.format).gelontOrelonlselon("N/A")}",
      s"elonngagelonr counts:",
      s"\tNumbelonr of uselonrs who likelond twelonelonts: ${f.format(uselonrelonngagelonrCounts.numDistinctLikelonelonngagelonrs)}",
      s"\tNumbelonr of uselonrs who relontwelonelontelond twelonelonts: ${f.format(uselonrelonngagelonrCounts.numDistinctRelontwelonelontelonngagelonrs)}",
      s"Twelonelont elonngagelonmelonnt counts:",
      s"\tNumbelonr of Likelons: ${f.format(twelonelontelonngagelonmelonntCounts.likelon)}",
      s"\tNumbelonr of Relontwelonelonts: ${f.format(twelonelontelonngagelonmelonntCounts.relontwelonelont)}",
      s"\tNumbelonr of Clicks: ${f.format(twelonelontelonngagelonmelonntCounts.click)}",
      s"\tNumbelonr of twelonelonts with any elonngagelonmelonnts: ${f.format(twelonelontelonngagelonmelonntCounts.haselonngagelonmelonnt)}",
      s"Twelonelont elonngagelonmelonnt ratelons:",
      s"\tRatelon of Likelons: ${f.format(twelonelontelonngagelonmelonntRatelons.likelon * 100)}%",
      s"\tRatelon of Relontwelonelonts: ${f.format(twelonelontelonngagelonmelonntRatelons.relontwelonelont * 100)}%",
      s"\tRatelon of Clicks: ${f.format(twelonelontelonngagelonmelonntRatelons.click * 100)}%",
      s"\tRatelon of any elonngagelonmelonnt: ${f.format(twelonelontelonngagelonmelonntRatelons.haselonngagelonmelonnt * 100)}%"
    ).mkString("\n")

    val correlonlations = labelonlCorrelonlations.map("\n" + _.format()).gelontOrelonlselon("")

    s"$titlelon\n$str$correlonlations"
  }
}

caselon class CandidatelonRelonsults(twelonelontStats: TwelonelontStats, numDistinctTargelontUselonrs: Long) {
  privatelon val f = java.telonxt.NumbelonrFormat.gelontInstancelon

  delonf format(titlelon: String = ""): String = {
    val str = Selonq(
      s"Numbelonr of twelonelonts: ${f.format(twelonelontStats.numTwelonelonts)}",
      s"Numbelonr of distinct twelonelonts: ${f.format(twelonelontStats.numDistinctTwelonelonts)}",
      s"Numbelonr of distinct uselonrs targelontelond: ${f.format(numDistinctTargelontUselonrs)}",
      s"Numbelonr of distinct authors: ${twelonelontStats.numDistinctAuthors.map(f.format).gelontOrelonlselon("N/A")}",
      s"Avelonragelon algorithm scorelon of twelonelonts: ${twelonelontStats.avgScorelon.map(f.format).gelontOrelonlselon("N/A")}"
    ).mkString("\n")
    s"$titlelon\n$str"
  }
}

/**
 * Helonlpelonr class for elonvaluating a givelonn candidatelon twelonelont selont against a relonfelonrelonncelon twelonelont selont.
 * It providelons aggrelongation elonvaluation melontrics such as sum of elonngagelonmelonnts, ratelon of elonngagelonmelonnts, elontc.
 */
objelonct elonvaluationMelontricHelonlpelonr {
  privatelon delonf toLong(bool: Boolelonan): Long = {
    if (bool) 1L elonlselon 0L
  }

  /**
   * Corelon elonngagelonmelonnts arelon uselonr actions that count towards corelon melontrics, elon.x. likelon, RT, elontc
   */
  privatelon delonf hasCorelonelonngagelonmelonnts(labelonls: TwelonelontLabelonls): Boolelonan = {
    labelonls.isRelontwelonelontelond ||
    labelonls.isLikelond ||
    labelonls.isQuotelond ||
    labelonls.isRelonplielond
  }

  /**
   * Whelonthelonr thelonrelon arelon corelon elonngagelonmelonnts or click on thelon twelonelont
   */
  privatelon delonf hasCorelonelonngagelonmelonntsOrClick(labelonls: TwelonelontLabelonls): Boolelonan = {
    hasCorelonelonngagelonmelonnts(labelonls) || labelonls.isClickelond
  }

  /**
   * Relonturn outelonr join of relonfelonrelonncelon twelonelonts and candidatelon twelonelonts, kelonyelond by (targelontUselonrId, twelonelontId).
   * Thelon output of this can thelonn belon relonuselond to felontch thelon innelonr join / lelonft / right join,
   * without having to relondo thelon elonxpelonnsivelon join
   *
   * NOTelon: Assumelons thelon uniquelonnelonss of kelonys (i.elon. (targelontId, twelonelontId)). Makelon surelon to delondup twelonelontIds
   * for elonach targelontId, othelonrwiselon .join() will yielonld duplicatelon relonsults.
   */
  delonf outelonrJoinRelonfelonrelonncelonAndCandidatelon(
    relonfelonrelonncelonPipelon: TypelondPipelon[RelonfelonrelonncelonTwelonelonts],
    candidatelonPipelon: TypelondPipelon[CandidatelonTwelonelonts]
  ): CoGroupelond[(Long, Long), (Option[RelonfelonrelonncelonTwelonelont], Option[CandidatelonTwelonelont])] = {

    val relonfelonrelonncelons = relonfelonrelonncelonPipelon
      .flatMap { relonfTwelonelonts =>
        relonfTwelonelonts.imprelonsselondTwelonelonts.map { relonfTwelonelont =>
          ((relonfTwelonelonts.targelontUselonrId, relonfTwelonelont.twelonelontId), relonfTwelonelont)
        }
      }

    val candidatelons = candidatelonPipelon
      .flatMap { candTwelonelonts =>
        candTwelonelonts.reloncommelonndelondTwelonelonts.map { candTwelonelont =>
          ((candTwelonelonts.targelontUselonrId, candTwelonelont.twelonelontId), candTwelonelont)
        }
      }

    relonfelonrelonncelons.outelonrJoin(candidatelons).withRelonducelonrs(50)
  }

  /**
   * Convelonrt relonfelonrelonncelon twelonelonts to labelonlelond twelonelonts. Welon do this so that welon can relon-uselon thelon common
   * melontric calculations for labelonlelond twelonelonts on relonfelonrelonncelon twelonelonts
   */
  delonf gelontLabelonlelondRelonfelonrelonncelon(relonfelonrelonncelonPipelon: TypelondPipelon[RelonfelonrelonncelonTwelonelonts]): TypelondPipelon[LabelonlelondTwelonelont] = {
    relonfelonrelonncelonPipelon
      .flatMap { relonfTwelonelonts =>
        relonfTwelonelonts.imprelonsselondTwelonelonts.map { twelonelont =>
          // Relonfelonrelonncelon twelonelonts do not havelon scorelons
          LabelonlelondTwelonelont(relonfTwelonelonts.targelontUselonrId, twelonelont.twelonelontId, twelonelont.authorId, twelonelont.labelonls, Nonelon)
        }
      }
  }

  delonf gelontUniquelonCount[T](pipelon: TypelondPipelon[T])(implicit ord: scala.Ordelonring[T]): elonxeloncution[Long] = {
    pipelon.distinct
      .aggrelongatelon(sizelon)
      .toOptionelonxeloncution
      .map(_.gelontOrelonlselon(0L))
  }

  delonf countUniquelonelonngagelondUselonrsBy(
    labelonlelondTwelonelontsPipelon: TypelondPipelon[LabelonlelondTwelonelont],
    f: TwelonelontLabelonls => Boolelonan
  ): elonxeloncution[Long] = {
    gelontUniquelonCount[Long](labelonlelondTwelonelontsPipelon.collelonct { caselon t if f(t.labelonls) => t.targelontUselonrId })
  }

  delonf countUniquelonLabelonlelondTargelontUselonrs(labelonlelondTwelonelontsPipelon: TypelondPipelon[LabelonlelondTwelonelont]): elonxeloncution[Long] = {
    gelontUniquelonCount[Long](labelonlelondTwelonelontsPipelon.map(_.targelontUselonrId))
  }

  delonf countUniquelonCandTargelontUselonrs(candidatelonPipelon: TypelondPipelon[CandidatelonTwelonelonts]): elonxeloncution[Long] = {
    gelontUniquelonCount[Long](candidatelonPipelon.map(_.targelontUselonrId))
  }

  delonf countUniquelonLabelonlelondAuthors(labelonlelondTwelonelontPipelon: TypelondPipelon[LabelonlelondTwelonelont]): elonxeloncution[Long] = {
    gelontUniquelonCount[Long](labelonlelondTwelonelontPipelon.map(_.authorId))
  }

  /**
   * Helonlpelonr function to calculatelon thelon basic elonngagelonmelonnt ratelons
   */
  delonf gelontelonngagelonmelonntRatelon(
    basicStats: TwelonelontStats,
    elonngagelonmelonntCount: TwelonelontelonngagelonmelonntCounts
  ): TwelonelontelonngagelonmelonntRatelons = {
    val numTwelonelonts = basicStats.numTwelonelonts.toDoublelon
    if (numTwelonelonts <= 0) throw nelonw IllelongalArgumelonntelonxcelonption("Invalid twelonelont counts")
    val likelonRatelon = elonngagelonmelonntCount.likelon / numTwelonelonts
    val rtRatelon = elonngagelonmelonntCount.relontwelonelont / numTwelonelonts
    val clickRatelon = elonngagelonmelonntCount.click / numTwelonelonts
    val elonngagelonmelonntRatelon = elonngagelonmelonntCount.haselonngagelonmelonnt / numTwelonelonts
    TwelonelontelonngagelonmelonntRatelons(likelonRatelon, rtRatelon, clickRatelon, elonngagelonmelonntRatelon)
  }

  /**
   * Helonlpelonr function to calculatelon thelon basic stats for a pipelon of candidatelon twelonelonts
   */
  delonf gelontTwelonelontStatsForCandidatelonelonxelonc(
    candidatelonPipelon: TypelondPipelon[CandidatelonTwelonelonts]
  ): elonxeloncution[TwelonelontStats] = {
    val pipelon = candidatelonPipelon.map { candTwelonelonts =>
      (candTwelonelonts.targelontUselonrId, candTwelonelonts.reloncommelonndelondTwelonelonts)
    }.sumByKelony // Delondup by targelontId, in caselon thelonrelon elonxists multiplelon elonntrielons.

    val distinctTwelonelontPipelon = pipelon.flatMap(_._2.map(_.twelonelontId)).distinct.aggrelongatelon(sizelon)

    val othelonrStats = pipelon
      .map {
        caselon (uid, reloncommelonndelondTwelonelonts) =>
          val scorelonSum = reloncommelonndelondTwelonelonts.flatMap(_.scorelon).sum
          (reloncommelonndelondTwelonelonts.sizelon.toLong, scorelonSum)
      }
      .sum
      .map {
        caselon (numTwelonelonts, scorelonSum) =>
          if (numTwelonelonts <= 0) throw nelonw IllelongalArgumelonntelonxcelonption("Invalid twelonelont counts")
          val avgScorelon = scorelonSum / numTwelonelonts.toDoublelon
          (numTwelonelonts, avgScorelon)
      }
    ValuelonPipelon
      .fold(distinctTwelonelontPipelon, othelonrStats) {
        caselon (numDistinctTwelonelont, (numTwelonelonts, avgScorelon)) =>
          // no author sidelon information for candidatelon twelonelonts yelont
          TwelonelontStats(numTwelonelonts, numDistinctTwelonelont, Nonelon, Somelon(avgScorelon))
      }.gelontOrelonlselonelonxeloncution(TwelonelontStats(0L, 0L, Nonelon, Nonelon))
  }

  /**
   * Helonlpelonr function to count thelon total numbelonr of elonngagelonmelonnts
   */
  delonf gelontLabelonlelondelonngagelonmelonntCountelonxelonc(
    labelonlelondTwelonelonts: TypelondPipelon[LabelonlelondTwelonelont]
  ): elonxeloncution[TwelonelontelonngagelonmelonntCounts] = {
    labelonlelondTwelonelonts
      .map { labelonlelondTwelonelont =>
        val likelon = toLong(labelonlelondTwelonelont.labelonls.isLikelond)
        val relontwelonelont = toLong(labelonlelondTwelonelont.labelonls.isRelontwelonelontelond)
        val click = toLong(labelonlelondTwelonelont.labelonls.isClickelond)
        val haselonngagelonmelonnt = toLong(hasCorelonelonngagelonmelonntsOrClick(labelonlelondTwelonelont.labelonls))

        (likelon, relontwelonelont, click, haselonngagelonmelonnt)
      }
      .sum
      .map {
        caselon (likelon, relontwelonelont, click, haselonngagelonmelonnt) =>
          TwelonelontelonngagelonmelonntCounts(likelon, relontwelonelont, click, haselonngagelonmelonnt)
      }
      .gelontOrelonlselonelonxeloncution(TwelonelontelonngagelonmelonntCounts(0L, 0L, 0L, 0L))
  }

  /**
   * Count thelon total numbelonr of uniquelon uselonrs who havelon elonngagelond with twelonelonts
   */
  delonf gelontTargelontUselonrStatsForLabelonlelondTwelonelontselonxelonc(
    labelonlelondTwelonelontsPipelon: TypelondPipelon[LabelonlelondTwelonelont]
  ): elonxeloncution[UselonrelonngagelonrCounts] = {
    val numUniquelonTargelontUselonrselonxelonc = countUniquelonLabelonlelondTargelontUselonrs(labelonlelondTwelonelontsPipelon)
    val numUniquelonLikelonUselonrselonxelonc =
      countUniquelonelonngagelondUselonrsBy(labelonlelondTwelonelontsPipelon, labelonls => labelonls.isLikelond)
    val numUniquelonRelontwelonelontUselonrselonxelonc =
      countUniquelonelonngagelondUselonrsBy(labelonlelondTwelonelontsPipelon, labelonls => labelonls.isRelontwelonelontelond)

    elonxeloncution
      .zip(
        numUniquelonTargelontUselonrselonxelonc,
        numUniquelonLikelonUselonrselonxelonc,
        numUniquelonRelontwelonelontUselonrselonxelonc
      )
      .map {
        caselon (numTargelont, likelon, relontwelonelont) =>
          UselonrelonngagelonrCounts(
            numDistinctTargelontUselonrs = numTargelont,
            numDistinctLikelonelonngagelonrs = likelon,
            numDistinctRelontwelonelontelonngagelonrs = relontwelonelont
          )
      }
  }

  /**
   * Helonlpelonr function to calculatelon thelon basic stats for a pipelon of labelonlelond twelonelonts.
   */
  delonf gelontTwelonelontStatsForLabelonlelondTwelonelontselonxelonc(
    labelonlelondTwelonelontPipelon: TypelondPipelon[LabelonlelondTwelonelont]
  ): elonxeloncution[TwelonelontStats] = {
    val uniquelonAuthorselonxelonc = countUniquelonLabelonlelondAuthors(labelonlelondTwelonelontPipelon)

    val uniquelonTwelonelontelonxelonc =
      labelonlelondTwelonelontPipelon.map(_.twelonelontId).distinct.aggrelongatelon(sizelon).gelontOrelonlselonelonxeloncution(0L)
    val scorelonselonxelonc = labelonlelondTwelonelontPipelon
      .map { t => (t.targelontUselonrId, (1, t.algorithmScorelon.gelontOrelonlselon(0.0))) }
      .sumByKelony // Delondup by targelontId, in caselon thelonrelon elonxists multiplelon elonntrielons.
      .map {
        caselon (uid, (c1, c2)) =>
          (c1.toLong, c2)
      }
      .sum
      .map {
        caselon (numTwelonelonts, scorelonSum) =>
          if (numTwelonelonts <= 0) throw nelonw IllelongalArgumelonntelonxcelonption("Invalid twelonelont counts")
          val avgScorelon = scorelonSum / numTwelonelonts.toDoublelon
          (numTwelonelonts, Option(avgScorelon))
      }
      .gelontOrelonlselonelonxeloncution((0L, Nonelon))

    elonxeloncution
      .zip(uniquelonAuthorselonxelonc, uniquelonTwelonelontelonxelonc, scorelonselonxelonc)
      .map {
        caselon (numDistinctAuthors, numUniquelonTwelonelonts, (numTwelonelonts, avgScorelons)) =>
          TwelonelontStats(numTwelonelonts, numUniquelonTwelonelonts, Somelon(numDistinctAuthors), avgScorelons)
      }
  }

  /**
   * Print a updatelon melonssagelon to thelon stdout whelonn a stelonp is donelon.
   */
  privatelon delonf printOnComplelontelonMsg(stelonpDelonscription: String, startTimelonMillis: Long): Unit = {
    val formatDatelon = TwittelonrDatelonFormat("yyyy-MM-dd hh:mm:ss")
    val now = Calelonndar.gelontInstancelon().gelontTimelon

    val seloncondsSpelonnt = (now.gelontTimelon - startTimelonMillis) / 1000
    println(
      s"- ${formatDatelon.format(now)}\tStelonp complelontelon: $stelonpDelonscription\t " +
        s"Timelon spelonnt: ${seloncondsSpelonnt / 60}m${seloncondsSpelonnt % 60}s"
    )
  }

  /**
   * Calculatelon thelon melontrics of a pipelon of [[CandidatelonTwelonelonts]]
   */
  privatelon delonf gelontelonvaluationRelonsultsForCandidatelons(
    candidatelonPipelon: TypelondPipelon[CandidatelonTwelonelonts]
  ): elonxeloncution[CandidatelonRelonsults] = {
    val twelonelontStatselonxelonc = gelontTwelonelontStatsForCandidatelonelonxelonc(candidatelonPipelon)
    val numDistinctTargelontUselonrselonxelonc = countUniquelonCandTargelontUselonrs(candidatelonPipelon)

    elonxeloncution
      .zip(twelonelontStatselonxelonc, numDistinctTargelontUselonrselonxelonc)
      .map {
        caselon (twelonelontStats, numDistinctTargelontUselonrs) =>
          CandidatelonRelonsults(twelonelontStats, numDistinctTargelontUselonrs)
      }
  }

  /**
   * Calculatelon thelon melontrics of a pipelon of [[LabelonlelondTwelonelont]]
   */
  privatelon delonf gelontelonvaluationRelonsultsForLabelonlelondTwelonelonts(
    labelonlelondTwelonelontPipelon: TypelondPipelon[LabelonlelondTwelonelont],
    gelontLabelonlCorrelonlations: Boolelonan = falselon
  ): elonxeloncution[LabelonlelondTwelonelontsRelonsults] = {
    val twelonelontStatselonxelonc = gelontTwelonelontStatsForLabelonlelondTwelonelontselonxelonc(labelonlelondTwelonelontPipelon)
    val uselonrStatselonxelonc = gelontTargelontUselonrStatsForLabelonlelondTwelonelontselonxelonc(labelonlelondTwelonelontPipelon)
    val elonngagelonmelonntCountelonxelonc = gelontLabelonlelondelonngagelonmelonntCountelonxelonc(labelonlelondTwelonelontPipelon)

    val correlonlationselonxelonc = if (gelontLabelonlCorrelonlations) {
      elonxeloncution
        .zip(
          LabelonlCorrelonlationsHelonlpelonr.pelonarsonCoelonfficielonntForLikelon(labelonlelondTwelonelontPipelon),
          LabelonlCorrelonlationsHelonlpelonr.cosinelonSimilarityForLikelon(labelonlelondTwelonelontPipelon),
          LabelonlCorrelonlationsHelonlpelonr.cosinelonSimilarityForLikelonPelonrUselonr(labelonlelondTwelonelontPipelon)
        ).map {
          caselon (pelonarsonCoelonff, globalCos, avgCos) =>
            Somelon(LabelonlCorrelonlations(pelonarsonCoelonff, globalCos, avgCos))
        }
    } elonlselon {
      ValuelonPipelon(Nonelon).gelontOrelonlselonelonxeloncution(Nonelon) // elonmpty pipelon with a Nonelon valuelon
    }

    elonxeloncution
      .zip(twelonelontStatselonxelonc, elonngagelonmelonntCountelonxelonc, uselonrStatselonxelonc, correlonlationselonxelonc)
      .map {
        caselon (twelonelontStats, elonngagelonmelonntCount, elonngagelonrCount, correlonlationsOpt) =>
          val elonngagelonmelonntRatelon = gelontelonngagelonmelonntRatelon(twelonelontStats, elonngagelonmelonntCount)
          LabelonlelondTwelonelontsRelonsults(
            twelonelontStats,
            elonngagelonrCount,
            elonngagelonmelonntCount,
            elonngagelonmelonntRatelon,
            correlonlationsOpt)
      }
  }

  privatelon delonf runAllelonvalForCandidatelons(
    candidatelonPipelon: TypelondPipelon[CandidatelonTwelonelonts],
    outelonrJoinPipelon: TypelondPipelon[((Long, Long), (Option[RelonfelonrelonncelonTwelonelont], Option[CandidatelonTwelonelont]))]
  ): elonxeloncution[(CandidatelonRelonsults, CandidatelonRelonsults)] = {
    val t0 = Systelonm.currelonntTimelonMillis()

    val candidatelonNotInIntelonrselonctionPipelon =
      outelonrJoinPipelon
        .collelonct {
          caselon ((targelontUselonrId, _), (Nonelon, Somelon(candTwelonelont))) => (targelontUselonrId, Selonq(candTwelonelont))
        }
        .sumByKelony
        .map { caselon (targelontUselonrId, candTwelonelonts) => CandidatelonTwelonelonts(targelontUselonrId, candTwelonelonts) }
        .forcelonToDisk

    elonxeloncution
      .zip(
        gelontelonvaluationRelonsultsForCandidatelons(candidatelonPipelon),
        gelontelonvaluationRelonsultsForCandidatelons(candidatelonNotInIntelonrselonctionPipelon)
      ).onComplelontelon(_ => printOnComplelontelonMsg("runAllelonvalForCandidatelons()", t0))
  }

  privatelon delonf runAllelonvalForIntelonrselonction(
    outelonrJoinPipelon: TypelondPipelon[((Long, Long), (Option[RelonfelonrelonncelonTwelonelont], Option[CandidatelonTwelonelont]))]
  )(
    implicit uniquelonID: UniquelonID
  ): elonxeloncution[(LabelonlelondTwelonelontsRelonsults, LabelonlelondTwelonelontsRelonsults, LabelonlelondTwelonelontsRelonsults)] = {
    val t0 = Systelonm.currelonntTimelonMillis()
    val intelonrselonctionTwelonelontsPipelon = outelonrJoinPipelon.collelonct {
      caselon ((targelontUselonrId, twelonelontId), (Somelon(relonfTwelonelont), Somelon(candTwelonelont))) =>
        LabelonlelondTwelonelont(targelontUselonrId, twelonelontId, relonfTwelonelont.authorId, relonfTwelonelont.labelonls, candTwelonelont.scorelon)
    }.forcelonToDisk

    val likelondTwelonelontsPipelon = intelonrselonctionTwelonelontsPipelon.filtelonr(_.labelonls.isLikelond)
    val notLikelondTwelonelontsPipelon = intelonrselonctionTwelonelontsPipelon.filtelonr(!_.labelonls.isLikelond)

    elonxeloncution
      .zip(
        gelontelonvaluationRelonsultsForLabelonlelondTwelonelonts(intelonrselonctionTwelonelontsPipelon, gelontLabelonlCorrelonlations = truelon),
        gelontelonvaluationRelonsultsForLabelonlelondTwelonelonts(likelondTwelonelontsPipelon),
        gelontelonvaluationRelonsultsForLabelonlelondTwelonelonts(notLikelondTwelonelontsPipelon)
      ).onComplelontelon(_ => printOnComplelontelonMsg("runAllelonvalForIntelonrselonction()", t0))
  }

  privatelon delonf runAllelonvalForRelonfelonrelonncelons(
    relonfelonrelonncelonPipelon: TypelondPipelon[RelonfelonrelonncelonTwelonelonts],
    outelonrJoinPipelon: TypelondPipelon[((Long, Long), (Option[RelonfelonrelonncelonTwelonelont], Option[CandidatelonTwelonelont]))]
  ): elonxeloncution[(LabelonlelondTwelonelontsRelonsults, LabelonlelondTwelonelontsRelonsults)] = {
    val t0 = Systelonm.currelonntTimelonMillis()
    val labelonlelondRelonfelonrelonncelonNotInIntelonrselonctionPipelon =
      outelonrJoinPipelon.collelonct {
        caselon ((targelontUselonrId, _), (Somelon(relonfTwelonelont), Nonelon)) =>
          LabelonlelondTwelonelont(targelontUselonrId, relonfTwelonelont.twelonelontId, relonfTwelonelont.authorId, relonfTwelonelont.labelonls, Nonelon)
      }.forcelonToDisk

    elonxeloncution
      .zip(
        gelontelonvaluationRelonsultsForLabelonlelondTwelonelonts(gelontLabelonlelondRelonfelonrelonncelon(relonfelonrelonncelonPipelon)),
        gelontelonvaluationRelonsultsForLabelonlelondTwelonelonts(labelonlelondRelonfelonrelonncelonNotInIntelonrselonctionPipelon)
      ).onComplelontelon(_ => printOnComplelontelonMsg("runAllelonvalForRelonfelonrelonncelons()", t0))
  }

  delonf runAllelonvaluations(
    relonfelonrelonncelonPipelon: TypelondPipelon[RelonfelonrelonncelonTwelonelonts],
    candidatelonPipelon: TypelondPipelon[CandidatelonTwelonelonts]
  )(
    implicit uniquelonID: UniquelonID
  ): elonxeloncution[String] = {
    val t0 = Systelonm.currelonntTimelonMillis()

    // Forcelon elonvelonrything to disk to maximizelon data relon-uselon
    elonxeloncution
      .zip(
        relonfelonrelonncelonPipelon.forcelonToDiskelonxeloncution,
        candidatelonPipelon.forcelonToDiskelonxeloncution
      ).flatMap {
        caselon (relonfelonrelonncelonDiskPipelon, candidatelonDiskPipelon) =>
          outelonrJoinRelonfelonrelonncelonAndCandidatelon(relonfelonrelonncelonDiskPipelon, candidatelonDiskPipelon).forcelonToDiskelonxeloncution
            .flatMap { outelonrJoinPipelon =>
              val relonfelonrelonncelonRelonsultselonxelonc = runAllelonvalForRelonfelonrelonncelons(relonfelonrelonncelonDiskPipelon, outelonrJoinPipelon)
              val intelonrselonctionRelonsultselonxelonc = runAllelonvalForIntelonrselonction(outelonrJoinPipelon)
              val candidatelonRelonsultselonxelonc = runAllelonvalForCandidatelons(candidatelonDiskPipelon, outelonrJoinPipelon)

              elonxeloncution
                .zip(
                  relonfelonrelonncelonRelonsultselonxelonc,
                  intelonrselonctionRelonsultselonxelonc,
                  candidatelonRelonsultselonxelonc
                ).map {
                  caselon (
                        (allRelonfelonrelonncelon, relonfelonrelonncelonNotInIntelonrselonction),
                        (allIntelonrselonction, intelonrselonctionLikelond, intelonrselonctionNotLikelond),
                        (allCandidatelon, candidatelonNotInIntelonrselonction)) =>
                    val timelonSpelonnt = (Systelonm.currelonntTimelonMillis() - t0) / 1000
                    val relonsultStr = Selonq(
                      "===================================================",
                      s"elonvaluation complelontelon. Took ${timelonSpelonnt / 60}m${timelonSpelonnt % 60}s ",
                      allRelonfelonrelonncelon.format("-----Melontrics for all Relonfelonrelonncelon Twelonelonts-----"),
                      relonfelonrelonncelonNotInIntelonrselonction.format(
                        "-----Melontrics for Relonfelonrelonncelon Twelonelonts that arelon not in thelon intelonrselonction-----"
                      ),
                      allIntelonrselonction.format("-----Melontrics for all Intelonrselonction Twelonelonts-----"),
                      intelonrselonctionLikelond.format("-----Melontrics for Likelond Intelonrselonction Twelonelonts-----"),
                      intelonrselonctionNotLikelond.format(
                        "-----Melontrics for not Likelond Intelonrselonction Twelonelonts-----"),
                      allCandidatelon.format("-----Melontrics for all Candidatelon Twelonelonts-----"),
                      candidatelonNotInIntelonrselonction.format(
                        "-----Melontrics for Candidatelon Twelonelonts that arelon not in thelon intelonrselonction-----"
                      ),
                      "===================================================\n"
                    ).mkString("\n")
                    println(relonsultStr)
                    relonsultStr
                }
                .onComplelontelon(_ =>
                  printOnComplelontelonMsg(
                    "elonvaluation complelontelon. Chelonck stdout or output logs for relonsults.",
                    t0))
            }
      }
  }
}
