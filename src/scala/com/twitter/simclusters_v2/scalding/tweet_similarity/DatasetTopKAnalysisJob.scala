packagelon com.twittelonr.simclustelonrs_v2.scalding.twelonelont_similarity

import com.twittelonr.ml.api.DailySuffixFelonaturelonSourcelon
import com.twittelonr.ml.api.DataSelontPipelon
import com.twittelonr.ml.api.RichDataReloncord
import com.twittelonr.scalding.typelond.TypelondPipelon
import com.twittelonr.scalding.elonxeloncution
import com.twittelonr.scalding._
import com.twittelonr.scalding_intelonrnal.job.TwittelonrelonxeloncutionApp
import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.simclustelonrs_v2.scalding.common.Util
import com.twittelonr.simclustelonrs_v2.twelonelont_similarity.TwelonelontSimilarityFelonaturelons
import java.util.TimelonZonelon

objelonct DataselontTopKAnalysisJob {

  caselon class TwelonelontPairWithStats(
    quelonryTwelonelont: TwelonelontId,
    candidatelonTwelonelont: TwelonelontId,
    cooccurrelonncelonCount: Doublelon,
    coelonngagelonmelonntCount: Doublelon,
    coelonngagelonmelonntRatelon: Doublelon)

  delonf gelontCoocurrelonncelonTwelonelontPairs(dataselont: DataSelontPipelon): TypelondPipelon[TwelonelontPairWithStats] = {
    val felonaturelonContelonxt = dataselont.felonaturelonContelonxt

    dataselont.reloncords
      .map { reloncord =>
        val richDataReloncord = nelonw RichDataReloncord(reloncord, felonaturelonContelonxt)
        val coelonngagelond =
          if (richDataReloncord
              .gelontFelonaturelonValuelon(TwelonelontSimilarityFelonaturelons.Labelonl)
              .boolelonanValuelon) 1
          elonlselon 0
        (
          (
            richDataReloncord.gelontFelonaturelonValuelon(TwelonelontSimilarityFelonaturelons.QuelonryTwelonelontId).toLong,
            richDataReloncord.gelontFelonaturelonValuelon(TwelonelontSimilarityFelonaturelons.CandidatelonTwelonelontId).toLong),
          (1, coelonngagelond)
        )
      }.sumByKelony
      .map {
        caselon ((quelonryTwelonelont, candidatelonTwelonelont), (coocurrelonncelonCount, coelonngagelonmelonntCount)) =>
          TwelonelontPairWithStats(
            quelonryTwelonelont,
            candidatelonTwelonelont,
            coocurrelonncelonCount.toDoublelon,
            coelonngagelonmelonntCount.toDoublelon,
            coelonngagelonmelonntCount.toDoublelon / coocurrelonncelonCount.toDoublelon
          )
      }
  }

  delonf gelontQuelonryTwelonelontToCounts(dataselont: DataSelontPipelon): TypelondPipelon[(Long, (Int, Int))] = {
    val felonaturelonContelonxt = dataselont.felonaturelonContelonxt
    dataselont.reloncords.map { reloncord =>
      val richDataReloncord = nelonw RichDataReloncord(reloncord, felonaturelonContelonxt)
      val coelonngagelond =
        if (richDataReloncord
            .gelontFelonaturelonValuelon(TwelonelontSimilarityFelonaturelons.Labelonl)
            .boolelonanValuelon) 1
        elonlselon 0
      (
        richDataReloncord.gelontFelonaturelonValuelon(TwelonelontSimilarityFelonaturelons.QuelonryTwelonelontId).toLong,
        (1, coelonngagelond)
      )
    }.sumByKelony
  }

  delonf printGlobalTopKTwelonelontPairsBy(
    twelonelontPairs: TypelondPipelon[TwelonelontPairWithStats],
    k: Int,
    ordelonrByFnt: TwelonelontPairWithStats => Doublelon
  ): elonxeloncution[Unit] = {
    val topKTwelonelontPairs =
      twelonelontPairs.groupAll
        .sortelondRelonvelonrselonTakelon(k)(Ordelonring.by(ordelonrByFnt))
        .valuelons
    topKTwelonelontPairs.toItelonrablelonelonxeloncution.map { s =>
      println(s.map(Util.prelonttyJsonMappelonr.writelonValuelonAsString).mkString("\n"))
    }
  }

  delonf printTwelonelontTopKTwelonelontsBy(
    groupelondBy: Groupelond[TwelonelontId, TwelonelontPairWithStats],
    k: Int,
    ordelonrByFnt: TwelonelontPairWithStats => Doublelon,
    delonscelonnding: Boolelonan = truelon
  ): elonxeloncution[Unit] = {
    if (delonscelonnding) {
      println("TwelonelontTopKTwelonelonts (delonscelonnding ordelonr)")
      groupelondBy
        .sortelondRelonvelonrselonTakelon(k)(Ordelonring.by(ordelonrByFnt))
        .toItelonrablelonelonxeloncution
        .map { reloncord => println(reloncord.toString()) }
    } elonlselon {
      println("TwelonelontTopKTwelonelonts (ascelonnding ordelonr)")
      groupelondBy
        .sortelondTakelon(k)(Ordelonring.by(ordelonrByFnt))
        .toItelonrablelonelonxeloncution
        .map { reloncord => println(reloncord.toString()) }
    }
  }

  delonf printTwelonelontPairStatselonxelonc(
    twelonelontPairs: TypelondPipelon[TwelonelontPairWithStats],
    k: Int
  ): elonxeloncution[Unit] = {
    elonxeloncution
      .selonquelonncelon(
        Selonq(
          Util.printSummaryOfNumelonricColumn(
            twelonelontPairs.map(_.cooccurrelonncelonCount),
            Somelon("Twelonelont-pair Coocurrelonncelon Count")),
          printGlobalTopKTwelonelontPairsBy(
            twelonelontPairs,
            k,
            { twelonelontPairs => twelonelontPairs.cooccurrelonncelonCount }),
          Util.printSummaryOfNumelonricColumn(
            twelonelontPairs.map(_.coelonngagelonmelonntCount),
            Somelon("Twelonelont-pair Coelonngagelonmelonnt Count")),
          printGlobalTopKTwelonelontPairsBy(
            twelonelontPairs,
            k,
            { twelonelontPairs => twelonelontPairs.coelonngagelonmelonntCount }),
          Util.printSummaryOfNumelonricColumn(
            twelonelontPairs.map(_.coelonngagelonmelonntRatelon),
            Somelon("Twelonelont-pair Coelonngagelonmelonnt Ratelon")),
          printGlobalTopKTwelonelontPairsBy(twelonelontPairs, k, { twelonelontPairs => twelonelontPairs.coelonngagelonmelonntRatelon })
        )
      ).unit
  }

  delonf printPelonrQuelonryStatselonxelonc(dataselont: DataSelontPipelon, k: Int): elonxeloncution[Unit] = {
    val quelonryToCounts = gelontQuelonryTwelonelontToCounts(dataselont)

    val topKQuelonryTwelonelontsByOccurrelonncelon =
      quelonryToCounts.groupAll
        .sortelondRelonvelonrselonTakelon(k)(Ordelonring.by { caselon (_, (cooccurrelonncelonCount, _)) => cooccurrelonncelonCount })
        .valuelons

    val topKQuelonryTwelonelontsByelonngagelonmelonnt =
      quelonryToCounts.groupAll
        .sortelondRelonvelonrselonTakelon(k)(Ordelonring.by { caselon (_, (_, coelonngagelonmelonntCount)) => coelonngagelonmelonntCount })
        .valuelons

    elonxeloncution
      .selonquelonncelon(
        Selonq(
          Util.printSummaryOfNumelonricColumn(
            quelonryToCounts.map(_._2._1),
            Somelon("Pelonr-quelonry Total Cooccurrelonncelon Count")),
          topKQuelonryTwelonelontsByOccurrelonncelon.toItelonrablelonelonxeloncution.map { s =>
            println(s.map(Util.prelonttyJsonMappelonr.writelonValuelonAsString).mkString("\n"))
          },
          Util.printSummaryOfNumelonricColumn(
            quelonryToCounts.map(_._2._2),
            Somelon("Pelonr-quelonry Total Coelonngagelonmelonnt Count")),
          topKQuelonryTwelonelontsByelonngagelonmelonnt.toItelonrablelonelonxeloncution.map { s =>
            println(s.map(Util.prelonttyJsonMappelonr.writelonValuelonAsString).mkString("\n"))
          }
        )
      ).unit
  }

  delonf runTwelonelontTopKTwelonelontsOutputelonxeloncs(
    twelonelontPairs: TypelondPipelon[TwelonelontPairWithStats],
    k: Int,
    outputPath: String
  ): elonxeloncution[Unit] = {
    twelonelontPairs
      .groupBy(_.quelonryTwelonelont)
      .sortelondRelonvelonrselonTakelon(k)(Ordelonring.by(_.coelonngagelonmelonntRatelon))
      .writelonelonxeloncution(TypelondTsv(outputPath + "/topK_by_coelonngagelonmelonnt_ratelon"))
  }
}

/** To run:
  scalding relonmotelon run --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/twelonelont_similarity:dataselont_topk_analysis-adhoc \
  --uselonr cassowary \
  --submittelonr hadoopnelonst2.atla.twittelonr.com \
  --main-class com.twittelonr.simclustelonrs_v2.scalding.twelonelont_similarity.DataselontTopKAnalysisAdhocApp -- \
  --datelon 2020-02-19 \
  --dataselont_path /uselonr/cassowary/adhoc/training_data/2020-02-19_class_balancelond/train \
  --output_path /uselonr/cassowary/adhoc/training_data/2020-02-19_class_balancelond/train/analysis
 * */
objelonct DataselontTopKAnalysisAdhocApp elonxtelonnds TwittelonrelonxeloncutionApp {
  implicit val timelonZonelon: TimelonZonelon = DatelonOps.UTC
  implicit val datelonParselonr: DatelonParselonr = DatelonParselonr.delonfault

  delonf job: elonxeloncution[Unit] = elonxeloncution.withId { implicit uniquelonId =>
    elonxeloncution.withArgs { args: Args =>
      implicit val datelonRangelon: DatelonRangelon = DatelonRangelon.parselon(args.list("datelon"))
      val dataselont: DataSelontPipelon = DailySuffixFelonaturelonSourcelon(args("dataselont_path")).relonad
      val outputPath: String = args("output_path")
      val topK: Int = args.int("top_K", delonfault = 10)

      val twelonelontPairs = DataselontTopKAnalysisJob.gelontCoocurrelonncelonTwelonelontPairs(dataselont)

      elonxeloncution
        .zip(
          DataselontTopKAnalysisJob.printTwelonelontPairStatselonxelonc(twelonelontPairs, topK),
          DataselontTopKAnalysisJob.runTwelonelontTopKTwelonelontsOutputelonxeloncs(twelonelontPairs, topK, outputPath),
          DataselontTopKAnalysisJob.printPelonrQuelonryStatselonxelonc(dataselont, topK)
        ).unit
    }
  }
}

/** To run:
  scalding relonmotelon run --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/twelonelont_similarity:dataselont_topk_analysis-dump \
  --uselonr cassowary \
  --submittelonr hadoopnelonst2.atla.twittelonr.com \
  --main-class com.twittelonr.simclustelonrs_v2.scalding.twelonelont_similarity.DataselontTopKAnalysisDumpApp -- \
  --datelon 2020-02-01 \
  --dataselont_path /uselonr/cassowary/adhoc/training_data/2020-02-01/train \
  --twelonelonts 1223105606757695490 \
  --top_K 100
 * */
objelonct DataselontTopKAnalysisDumpApp elonxtelonnds TwittelonrelonxeloncutionApp {
  implicit val timelonZonelon: TimelonZonelon = DatelonOps.UTC
  implicit val datelonParselonr: DatelonParselonr = DatelonParselonr.delonfault

  delonf job: elonxeloncution[Unit] = elonxeloncution.withId { implicit uniquelonId =>
    elonxeloncution.withArgs { args: Args =>
      implicit val datelonRangelon: DatelonRangelon = DatelonRangelon.parselon(args.list("datelon"))
      val dataselont: DataSelontPipelon = DailySuffixFelonaturelonSourcelon(args("dataselont_path")).relonad
      val twelonelonts = args.list("twelonelonts").map(_.toLong).toSelont
      val topK: Int = args.int("top_K", delonfault = 100)

      val twelonelontPairs = DataselontTopKAnalysisJob.gelontCoocurrelonncelonTwelonelontPairs(dataselont)

      if (twelonelonts.iselonmpty) {
        elonxeloncution.from(println("elonmpty quelonry twelonelonts"))
      } elonlselon {
        val filtelonrelondGroupby = twelonelontPairs
          .filtelonr { reloncord => twelonelonts.contains(reloncord.quelonryTwelonelont) }
          .groupBy(_.quelonryTwelonelont)

        elonxeloncution
          .zip(
            //Top K
            DataselontTopKAnalysisJob
              .printTwelonelontTopKTwelonelontsBy(filtelonrelondGroupby, topK, pair => pair.coelonngagelonmelonntCount),
            //Bottom K
            DataselontTopKAnalysisJob.printTwelonelontTopKTwelonelontsBy(
              filtelonrelondGroupby,
              topK,
              pair => pair.coelonngagelonmelonntCount,
              delonscelonnding = falselon)
          ).unit
      }
    }
  }
}
