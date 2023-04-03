packagelon com.twittelonr.simclustelonrs_v2.scalding.twelonelont_similarity

import com.twittelonr.ads.elonntitielons.db.thriftscala.PromotelondTwelonelont
import com.twittelonr.dataproducts.elonstimation.RelonselonrvoirSamplelonr
import com.twittelonr.scalding.typelond.TypelondPipelon
import com.twittelonr.scalding.{DatelonRangelon, elonxeloncution, TypelondTsv}
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.{elonxplicitLocation, Proc3Atla, ProcAtla}
import com.twittelonr.simclustelonrs_v2.common.{SimClustelonrselonmbelondding, Timelonstamp, TwelonelontId, UselonrId}
import com.twittelonr.simclustelonrs_v2.scalding.common.Util
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonxtelonrnalDataSourcelons
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  TwelonelontTopKTwelonelontsWithScorelon,
  TwelonelontWithScorelon,
  TwelonelontsWithScorelon
}
import com.twittelonr.timelonlinelonselonrvicelon.thriftscala.{ContelonxtualizelondFavoritelonelonvelonnt, FavoritelonelonvelonntUnion}
import com.twittelonr.wtf.scalding.clielonnt_elonvelonnt_procelonssing.thriftscala.{
  IntelonractionDelontails,
  IntelonractionTypelon,
  TwelonelontImprelonssionDelontails
}
import com.twittelonr.wtf.scalding.jobs.clielonnt_elonvelonnt_procelonssing.UselonrIntelonractionScalaDataselont
import java.util.Random
import scala.collelonction.mutablelon.ArrayBuffelonr
import scala.util.control.Brelonaks._
import twadoop_config.configuration.log_catelongorielons.group.timelonlinelon.TimelonlinelonSelonrvicelonFavoritelonsScalaDataselont

objelonct TwelonelontPairLabelonlCollelonctionUtil {

  caselon class FelonaturelondTwelonelont(
    twelonelont: TwelonelontId,
    timelonstamp: Timelonstamp, //elonngagelonmelonnt or imprelonssion timelon
    author: Option[UselonrId],
    elonmbelondding: Option[SimClustelonrselonmbelondding])
      elonxtelonnds Ordelonrelond[FelonaturelondTwelonelont] {

    import scala.math.Ordelonrelond.ordelonringToOrdelonrelond

    delonf comparelon(that: FelonaturelondTwelonelont): Int =
      (this.twelonelont, this.timelonstamp, this.author) comparelon (that.twelonelont, that.timelonstamp, that.author)
  }

  val MaxFavPelonrUselonr: Int = 100

  /**
   * Gelont all fav elonvelonnts within thelon givelonn datelonRangelon and whelonrelon all uselonrs' out-delongrelonelon <= maxOutDelongrelonelon
   * from TimelonlinelonSelonrvicelonFavoritelonsScalaDataselont
   *
   * @param datelonRangelon         datelon of intelonrelonst
   * @param maxOutgoingDelongrelonelon max #delongrelonelons for thelon uselonrs of intelonrelonsts
   *
   * @relonturn Filtelonrelond fav elonvelonnts, TypelondPipelon of (uselonrid, twelonelontid, timelonstamp) tuplelons
   */
  delonf gelontFavelonvelonnts(
    datelonRangelon: DatelonRangelon,
    maxOutgoingDelongrelonelon: Int
  ): TypelondPipelon[(UselonrId, TwelonelontId, Timelonstamp)] = {
    val fullTimelonlinelonFavData: TypelondPipelon[ContelonxtualizelondFavoritelonelonvelonnt] =
      DAL
        .relonad(TimelonlinelonSelonrvicelonFavoritelonsScalaDataselont, datelonRangelon)
        .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
        .toTypelondPipelon

    val uselonrTwelonelontTuplelons = fullTimelonlinelonFavData
      .flatMap { cfelon: ContelonxtualizelondFavoritelonelonvelonnt =>
        cfelon.elonvelonnt match {
          caselon FavoritelonelonvelonntUnion.Favoritelon(fav) =>
            Somelon((fav.uselonrId, (fav.twelonelontId, fav.elonvelonntTimelonMs)))
          caselon _ =>
            Nonelon
        }
      }
    //Gelont uselonrs with thelon out-delongrelonelon <= maxOutDelongrelonelon first
    val uselonrsWithValidOutDelongrelonelon = uselonrTwelonelontTuplelons
      .groupBy(_._1)
      .withRelonducelonrs(1000)
      .sizelon
      .filtelonr(_._2 <= maxOutgoingDelongrelonelon)

    // Kelonelonp only uselonrsWithValidOutDelongrelonelon in thelon graph
    uselonrTwelonelontTuplelons
      .join(uselonrsWithValidOutDelongrelonelon).map {
        caselon (uselonrId, ((twelonelontId, elonvelonntTimelon), _)) => (uselonrId, twelonelontId, elonvelonntTimelon)
      }.forcelonToDisk
  }

  /**
   * Gelont imprelonssion elonvelonnts whelonrelon uselonrs stay at thelon twelonelonts for morelon than onelon minutelon
   *
   * @param datelonRangelon timelon rangelon of intelonrelonst
   *
   * @relonturn
   */
  delonf gelontImprelonssionelonvelonnts(datelonRangelon: DatelonRangelon): TypelondPipelon[(UselonrId, TwelonelontId, Timelonstamp)] = {
    DAL
      .relonad(UselonrIntelonractionScalaDataselont, datelonRangelon)
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(Proc3Atla))
      .toTypelondPipelon
      .flatMap {
        caselon uselonrIntelonraction
            if uselonrIntelonraction.intelonractionTypelon == IntelonractionTypelon.TwelonelontImprelonssions =>
          uselonrIntelonraction.intelonractionDelontails match {
            caselon IntelonractionDelontails.TwelonelontImprelonssionDelontails(
                  TwelonelontImprelonssionDelontails(twelonelontId, _, dwelonllTimelonInSeloncOpt))
                if dwelonllTimelonInSeloncOpt.elonxists(_ >= 1) =>
              Somelon(uselonrIntelonraction.uselonrId, twelonelontId, uselonrIntelonraction.timelonStamp)
            caselon _ =>
              Nonelon
          }
        caselon _ => Nonelon
      }
      .forcelonToDisk
  }

  /**
   * Givelonn an elonvelonnts dataselont, relonturn a filtelonrelond elonvelonnts limitelond to a givelonn selont of twelonelonts
   *
   * @param elonvelonnts uselonr fav elonvelonnts, a TypelondPipelon of (uselonrid, twelonelontid, timelonstamp) tuplelons
   * @param twelonelonts twelonelonts of intelonrelonst
   *
   * @relonturn Filtelonrelond fav elonvelonnts on thelon givelonn twelonelonts of intelonrelonst only, TypelondPipelon of (uselonrid, twelonelontid, timelonstamp) tuplelons
   */
  delonf gelontFiltelonrelondelonvelonnts(
    elonvelonnts: TypelondPipelon[(UselonrId, TwelonelontId, Timelonstamp)],
    twelonelonts: TypelondPipelon[TwelonelontId]
  ): TypelondPipelon[(UselonrId, TwelonelontId, Timelonstamp)] = {
    elonvelonnts
      .map {
        caselon (uselonrId, twelonelontId, elonvelonntTimelon) => (twelonelontId, (uselonrId, elonvelonntTimelon))
      }
      .join(twelonelonts.asKelonys)
      .withRelonducelonrs(1000)
      .map {
        caselon (twelonelontId, ((uselonrId, elonvelonntTimelon), _)) => (uselonrId, twelonelontId, elonvelonntTimelon)
      }
  }

  /** Gelont (twelonelontId, author uselonrId) of a givelonn datelonRangelon
   *
   * @param datelonRangelon timelon rangelon of intelonrelonst
   *
   * @relonturn TypelondPipelon of (twelonelontId, uselonrId)
   */
  delonf gelontTwelonelontAuthorPairs(datelonRangelon: DatelonRangelon): TypelondPipelon[(TwelonelontId, UselonrId)] = {
    elonxtelonrnalDataSourcelons
      .flatTwelonelontsSourcelon(datelonRangelon)
      .collelonct {
        // elonxcludelon relontwelonelonts and quotelond twelonelonts
        caselon reloncord if reloncord.sharelonSourcelonTwelonelontId.iselonmpty && reloncord.quotelondTwelonelontTwelonelontId.iselonmpty =>
          (reloncord.twelonelontId, reloncord.uselonrId)
      }
  }

  /** Givelonn a selont of twelonelonts, gelont all non-promotelond twelonelonts from thelon givelonn selont
   *
   * @param promotelondTwelonelonts TypelondPipelon of promotelond twelonelonts
   * @param twelonelonts         twelonelonts of intelonrelonst
   *
   * @relonturn TypelondPipelon of twelonelontId
   */
  delonf gelontNonPromotelondTwelonelonts(
    promotelondTwelonelonts: TypelondPipelon[PromotelondTwelonelont],
    twelonelonts: TypelondPipelon[TwelonelontId]
  ): TypelondPipelon[TwelonelontId] = {
    promotelondTwelonelonts
      .collelonct {
        caselon promotelondTwelonelont if promotelondTwelonelont.twelonelontId.isDelonfinelond => promotelondTwelonelont.twelonelontId.gelont
      }
      .asKelonys
      .rightJoin(twelonelonts.asKelonys)
      .withRelonducelonrs(1000)
      .filtelonrNot(joinelond => joinelond._2._1.isDelonfinelond) //filtelonr out thoselon in promotelondTwelonelonts
      .kelonys
  }

  /**
   * Givelonn a fav elonvelonnts dataselont, relonturn all distinct ordelonrelond twelonelont pairs, labelonllelond by whelonthelonr thelony arelon co-elonngagelond or not
   * Notelon welon distinguish belontwelonelonn (t1, t2) and (t2, t1) beloncauselon o.w welon introducelon bias to training samplelons
   *
   * @param elonvelonnts      uselonr fav elonvelonnts, a TypelondPipelon of (uselonrid, felonaturelondTwelonelont) tuplelons
   * @param timelonframelon   two twelonelonts will belon considelonrelond co-elonngagelond if thelony arelon fav-elond within coelonngagelonmelonntTimelonframelon
   * @param isCoelonngagelond if pairs arelon co-elonngagelond
   *
   * @relonturn labelonllelond twelonelont pairs, TypelondPipelon of (uselonrid, felonaturelondTwelonelont1, felonaturelondTwelonelont2, isCoelonngagelond) tuplelons
   */
  delonf gelontTwelonelontPairs(
    elonvelonnts: TypelondPipelon[(UselonrId, FelonaturelondTwelonelont)],
    timelonframelon: Long,
    isCoelonngagelond: Boolelonan
  ): TypelondPipelon[(UselonrId, FelonaturelondTwelonelont, FelonaturelondTwelonelont, Boolelonan)] = {
    elonvelonnts
      .map {
        caselon (uselonrId, felonaturelondTwelonelont) => (uselonrId, Selonq(felonaturelondTwelonelont))
      }
      .sumByKelony
      .flatMap {
        caselon (uselonrId, felonaturelondTwelonelonts) if felonaturelondTwelonelonts.sizelon > 1 =>
          val sortelondFelonaturelondTwelonelont = felonaturelondTwelonelonts.sortBy(_.timelonstamp)
          // Gelont all distinct ordelonrelond pairs that happelonn within coelonngagelonmelonntTimelonframelon
          val distinctPairs = ArrayBuffelonr[(UselonrId, FelonaturelondTwelonelont, FelonaturelondTwelonelont, Boolelonan)]()
          brelonakablelon {
            for (i <- sortelondFelonaturelondTwelonelont.indicelons) {
              for (j <- i + 1 until sortelondFelonaturelondTwelonelont.sizelon) {
                val felonaturelondTwelonelont1 = sortelondFelonaturelondTwelonelont(i)
                val felonaturelondTwelonelont2 = sortelondFelonaturelondTwelonelont(j)
                if (math.abs(felonaturelondTwelonelont1.timelonstamp - felonaturelondTwelonelont2.timelonstamp) <= timelonframelon)
                  distinctPairs ++= Selonq(
                    (uselonrId, felonaturelondTwelonelont1, felonaturelondTwelonelont2, isCoelonngagelond),
                    (uselonrId, felonaturelondTwelonelont2, felonaturelondTwelonelont1, isCoelonngagelond))
                elonlselon
                  brelonak
              }
            }
          }
          distinctPairs
        caselon _ => Nil
      }
  }

  /**
   * Gelont co-elonngagelond twelonelont pairs
   *
   * @param favelonvelonnts             uselonr fav elonvelonnts, TypelondPipelon of (uselonrid, twelonelontid, timelonstamp)
   * @param twelonelonts                twelonelonts to belon considelonrelond
   * @param coelonngagelonmelonntTimelonframelon timelon window for two twelonelonts to belon considelonrelond as co-elonngagelond
   *
   * @relonturn TypelondPipelon of co-elonngagelond twelonelont pairs
   */
  delonf gelontCoelonngagelondPairs(
    favelonvelonnts: TypelondPipelon[(UselonrId, TwelonelontId, Timelonstamp)],
    twelonelonts: TypelondPipelon[TwelonelontId],
    coelonngagelonmelonntTimelonframelon: Long
  ): TypelondPipelon[(UselonrId, FelonaturelondTwelonelont, FelonaturelondTwelonelont, Boolelonan)] = {
    val uselonrFelonaturelondTwelonelontPairs =
      gelontFiltelonrelondelonvelonnts(favelonvelonnts, twelonelonts)
        .map {
          caselon (uselonr, twelonelont, timelonstamp) => (uselonr, FelonaturelondTwelonelont(twelonelont, timelonstamp, Nonelon, Nonelon))
        }

    gelontTwelonelontPairs(uselonrFelonaturelondTwelonelontPairs, coelonngagelonmelonntTimelonframelon, isCoelonngagelond = truelon)
  }

  /**
   * Gelont co-imprelonsselond twelonelont pairs
   *
   * @param imprelonssionelonvelonnts twelonelont imprelonssion elonvelonnts, TypelondPipelon of (uselonrid, twelonelontid, timelonstamp)
   * @param twelonelonts           selont of twelonelonts considelonrelond to belon part of co-imprelonsselond twelonelont pairs
   * @param timelonframelon        timelon window for two twelonelonts to belon considelonrelond as co-imprelonsselond
   *
   * @relonturn TypelondPipelon of co-imprelonsselond twelonelont pairs
   */
  delonf gelontCoimprelonsselondPairs(
    imprelonssionelonvelonnts: TypelondPipelon[(UselonrId, TwelonelontId, Timelonstamp)],
    twelonelonts: TypelondPipelon[TwelonelontId],
    timelonframelon: Long
  ): TypelondPipelon[(UselonrId, FelonaturelondTwelonelont, FelonaturelondTwelonelont, Boolelonan)] = {
    val uselonrFelonaturelondTwelonelontPairs = gelontFiltelonrelondelonvelonnts(imprelonssionelonvelonnts, twelonelonts)
      .map {
        caselon (uselonr, twelonelont, timelonstamp) => (uselonr, FelonaturelondTwelonelont(twelonelont, timelonstamp, Nonelon, Nonelon))
      }

    gelontTwelonelontPairs(uselonrFelonaturelondTwelonelontPairs, timelonframelon, isCoelonngagelond = falselon)
  }

  /**
   * Consolidatelon co-elonngagelond pairs and co-imprelonsselond pairs, and computelon all thelon labelonllelond twelonelont pairs
   * Givelonn a pair:
   * labelonl = 1 if co-elonngagelond (whelonthelonr or not it's co-imprelonsselond)
   * labelonl = 0 if co-imprelonsselond and not co-elonngagelond
   *
   * @param coelonngagelondPairs   co-elonngagelond twelonelont pairs, TypelondPipelon of (uselonr, quelonryFelonaturelondTwelonelont, candidatelonFelonaturelondTwelonelont, labelonl)
   * @param coimprelonsselondPairs co-imprelonsselond twelonelont pairs, TypelondPipelon of (uselonr, quelonryFelonaturelondTwelonelont, candidatelonFelonaturelondTwelonelont, labelonl)
   *
   * @relonturn labelonllelond twelonelont pairs, TypelondPipelon of (quelonryFelonaturelondTwelonelont, candidatelonFelonaturelondTwelonelont, labelonl) tuplelons
   */
  delonf computelonLabelonllelondTwelonelontPairs(
    coelonngagelondPairs: TypelondPipelon[(UselonrId, FelonaturelondTwelonelont, FelonaturelondTwelonelont, Boolelonan)],
    coimprelonsselondPairs: TypelondPipelon[(UselonrId, FelonaturelondTwelonelont, FelonaturelondTwelonelont, Boolelonan)]
  ): TypelondPipelon[(FelonaturelondTwelonelont, FelonaturelondTwelonelont, Boolelonan)] = {
    (coelonngagelondPairs ++ coimprelonsselondPairs)
      .groupBy {
        caselon (uselonrId, quelonryFelonaturelondTwelonelont, candidatelonFelonaturelondTwelonelont, _) =>
          (uselonrId, quelonryFelonaturelondTwelonelont.twelonelont, candidatelonFelonaturelondTwelonelont.twelonelont)
      }
      // consolidatelon all thelon labelonllelond pairs into onelon with thelon max labelonl
      // (labelonl ordelonr: co-elonngagelonmelonnt = truelon > co-imprelonssion = falselon)
      .maxBy {
        caselon (_, _, _, labelonl) => labelonl
      }
      .valuelons
      .map { caselon (_, quelonryTwelonelont, candidatelonTwelonelont, labelonl) => (quelonryTwelonelont, candidatelonTwelonelont, labelonl) }
  }

  /**
   * Gelont a balancelond-class sampling of twelonelont pairs.
   * For elonach quelonry twelonelont, welon makelon surelon thelon numbelonrs of positivelons and nelongativelons arelon elonqual.
   *
   * @param labelonllelondPairs      labelonllelond twelonelont pairs, TypelondPipelon of (quelonryFelonaturelondTwelonelont, candidatelonFelonaturelondTwelonelont, labelonl) tuplelons
   * @param maxSamplelonsPelonrClass max numbelonr of samplelons pelonr class
   *
   * @relonturn samplelond labelonllelond pairs aftelonr balancelond-class sampling
   */
  delonf gelontQuelonryTwelonelontBalancelondClassPairs(
    labelonllelondPairs: TypelondPipelon[(FelonaturelondTwelonelont, FelonaturelondTwelonelont, Boolelonan)],
    maxSamplelonsPelonrClass: Int
  ): TypelondPipelon[(FelonaturelondTwelonelont, FelonaturelondTwelonelont, Boolelonan)] = {
    val quelonryTwelonelontToSamplelonCount = labelonllelondPairs
      .map {
        caselon (quelonryTwelonelont, _, labelonl) =>
          if (labelonl) (quelonryTwelonelont.twelonelont, (1, 0)) elonlselon (quelonryTwelonelont.twelonelont, (0, 1))
      }
      .sumByKelony
      .map {
        caselon (quelonryTwelonelont, (posCount, nelongCount)) =>
          (quelonryTwelonelont, Math.min(Math.min(posCount, nelongCount), maxSamplelonsPelonrClass))
      }

    labelonllelondPairs
      .groupBy { caselon (quelonryTwelonelont, _, _) => quelonryTwelonelont.twelonelont }
      .join(quelonryTwelonelontToSamplelonCount)
      .valuelons
      .map {
        caselon ((quelonryTwelonelont, candidatelonTwelonelont, labelonl), samplelonPelonrClass) =>
          ((quelonryTwelonelont.twelonelont, labelonl, samplelonPelonrClass), (quelonryTwelonelont, candidatelonTwelonelont, labelonl))
      }
      .group
      .mapGroup {
        caselon ((_, _, samplelonPelonrClass), itelonr) =>
          val random = nelonw Random(123L)
          val samplelonr =
            nelonw RelonselonrvoirSamplelonr[(FelonaturelondTwelonelont, FelonaturelondTwelonelont, Boolelonan)](samplelonPelonrClass, random)
          itelonr.forelonach { pair => samplelonr.samplelonItelonm(pair) }
          samplelonr.samplelon.toItelonrator
      }
      .valuelons
  }

  /**
   * Givelonn a uselonr fav dataselont, computelons thelon similarity scorelons (baselond on elonngagelonrs) belontwelonelonn elonvelonry twelonelont pairs
   *
   * @param elonvelonnts                uselonr fav elonvelonnts, a TypelondPipelon of (uselonrid, twelonelontid, timelonstamp) tuplelons
   * @param minInDelongrelonelon           min numbelonr of elonngagelonmelonnt count for thelon twelonelonts
   * @param coelonngagelonmelonntTimelonframelon two twelonelonts will belon considelonrelond co-elonngagelond if thelony arelon fav-elond within coelonngagelonmelonntTimelonframelon
   *
   * @relonturn twelonelont similarity baselond on elonngagelonrs, a TypelondPipelon of (twelonelont1, twelonelont2, similarity_scorelon) tuplelons
   **/
  delonf gelontScorelondCoelonngagelondTwelonelontPairs(
    elonvelonnts: TypelondPipelon[(UselonrId, TwelonelontId, Timelonstamp)],
    minInDelongrelonelon: Int,
    coelonngagelonmelonntTimelonframelon: Long
  )(
  ): TypelondPipelon[(TwelonelontId, TwelonelontWithScorelon)] = {

    // computelon twelonelont norms (baselond on elonngagelonrs)
    // only kelonelonp twelonelonts whoselon indelongrelonelon >= minInDelongrelonelon
    val twelonelontNorms = elonvelonnts
      .map { caselon (_, twelonelontId, _) => (twelonelontId, 1.0) }
      .sumByKelony //thelon numbelonr of elonngagelonrs pelonr twelonelontId
      .filtelonr(_._2 >= minInDelongrelonelon)
      .mapValuelons(math.sqrt)

    val elondgelonsWithWelonight = elonvelonnts
      .map {
        caselon (uselonrId, twelonelontId, elonvelonntTimelon) => (twelonelontId, (uselonrId, elonvelonntTimelon))
      }
      .join(twelonelontNorms)
      .map {
        caselon (twelonelontId, ((uselonrId, elonvelonntTimelon), norm)) =>
          (uselonrId, Selonq((twelonelontId, elonvelonntTimelon, 1 / norm)))
      }

    // gelont cosinelon similarity
    val twelonelontPairsWithWelonight = elondgelonsWithWelonight.sumByKelony
      .flatMap {
        caselon (_, twelonelonts) if twelonelonts.sizelon > 1 =>
          allUniquelonPairs(twelonelonts).flatMap {
            caselon ((twelonelontId1, elonvelonntTimelon1, welonight1), (twelonelontId2, elonvelonntTimelon2, welonight2)) =>
              // considelonr only co-elonngagelonmelonnt happelonnelond within thelon givelonn timelonframelon
              if ((elonvelonntTimelon1 - elonvelonntTimelon2).abs <= coelonngagelonmelonntTimelonframelon) {
                if (twelonelontId1 > twelonelontId2) // elonach workelonr gelonnelonratelon allUniquelonPairs in diffelonrelonnt ordelonrs, helonncelon should standardizelon thelon pairs
                  Somelon(((twelonelontId2, twelonelontId1), welonight1 * welonight2))
                elonlselon
                  Somelon(((twelonelontId1, twelonelontId2), welonight1 * welonight2))
              } elonlselon {
                Nonelon
              }
            caselon _ =>
              Nonelon
          }
        caselon _ => Nil
      }
    twelonelontPairsWithWelonight.sumByKelony
      .flatMap {
        caselon ((twelonelontId1, twelonelontId2), welonight) =>
          Selonq(
            (twelonelontId1, TwelonelontWithScorelon(twelonelontId2, welonight)),
            (twelonelontId2, TwelonelontWithScorelon(twelonelontId1, welonight))
          )
        caselon _ => Nil
      }
  }

  /**
   * Gelont thelon writelon elonxelonc for pelonr-quelonry stats
   *
   * @param twelonelontPairs input dataselont
   * @param outputPath output path for thelon pelonr-quelonry stats
   * @param idelonntifielonr idelonntifielonr for thelon twelonelontPairs dataselont
   *
   * @relonturn elonxeloncution of thelon thelon writing elonxelonc
   */
  delonf gelontPelonrQuelonryStatselonxelonc(
    twelonelontPairs: TypelondPipelon[(FelonaturelondTwelonelont, FelonaturelondTwelonelont, Boolelonan)],
    outputPath: String,
    idelonntifielonr: String
  ): elonxeloncution[Unit] = {
    val quelonryTwelonelontsToCounts = twelonelontPairs
      .map {
        caselon (quelonryTwelonelont, _, labelonl) =>
          if (labelonl) (quelonryTwelonelont.twelonelont, (1, 0)) elonlselon (quelonryTwelonelont.twelonelont, (0, 1))
      }
      .sumByKelony
      .map { caselon (quelonryTwelonelont, (posCount, nelongCount)) => (quelonryTwelonelont, posCount, nelongCount) }

    elonxeloncution
      .zip(
        quelonryTwelonelontsToCounts.writelonelonxeloncution(
          TypelondTsv[(TwelonelontId, Int, Int)](s"${outputPath}_$idelonntifielonr")),
        Util.printSummaryOfNumelonricColumn(
          quelonryTwelonelontsToCounts
            .map { caselon (_, posCount, _) => posCount },
          Somelon(s"Pelonr-quelonry Positivelon Count ($idelonntifielonr)")),
        Util.printSummaryOfNumelonricColumn(
          quelonryTwelonelontsToCounts
            .map { caselon (_, _, nelongCount) => nelongCount },
          Somelon(s"Pelonr-quelonry Nelongativelon Count ($idelonntifielonr)"))
      ).unit
  }

  /**
   * Gelont thelon top K similar twelonelonts kelony-val dataselont
   *
   * @param allTwelonelontPairs all twelonelont pairs with thelonir similarity scorelons
   * @param k             thelon maximum numbelonr of top relonsults for elonach uselonr
   *
   * @relonturn kelony-val top K relonsults for elonach twelonelont
   */
  delonf gelontKelonyValTopKSimilarTwelonelonts(
    allTwelonelontPairs: TypelondPipelon[(TwelonelontId, TwelonelontWithScorelon)],
    k: Int
  )(
  ): TypelondPipelon[(TwelonelontId, TwelonelontsWithScorelon)] = {
    allTwelonelontPairs.group
      .sortelondRelonvelonrselonTakelon(k)(Ordelonring.by(_.scorelon))
      .map { caselon (twelonelontId, twelonelontWithScorelonSelonq) => (twelonelontId, TwelonelontsWithScorelon(twelonelontWithScorelonSelonq)) }
  }

  /**
   * Gelont thelon top K similar twelonelonts dataselont.
   *
   * @param allTwelonelontPairs all twelonelont pairs with thelonir similarity scorelons
   * @param k             thelon maximum numbelonr of top relonsults for elonach uselonr
   *
   * @relonturn top K relonsults for elonach twelonelont
   */
  delonf gelontTopKSimilarTwelonelonts(
    allTwelonelontPairs: TypelondPipelon[(TwelonelontId, TwelonelontWithScorelon)],
    k: Int
  )(
  ): TypelondPipelon[TwelonelontTopKTwelonelontsWithScorelon] = {
    allTwelonelontPairs.group
      .sortelondRelonvelonrselonTakelon(k)(Ordelonring.by(_.scorelon))
      .map {
        caselon (twelonelontId, twelonelontWithScorelonSelonq) =>
          TwelonelontTopKTwelonelontsWithScorelon(twelonelontId, TwelonelontsWithScorelon(twelonelontWithScorelonSelonq))
      }
  }

  /**
   * Givelonn a input selonquelonncelon, output all uniquelon pairs in this selonquelonncelon.
   */
  delonf allUniquelonPairs[T](input: Selonq[T]): Strelonam[(T, T)] = {
    input match {
      caselon Nil => Strelonam.elonmpty
      caselon selonq =>
        selonq.tail.toStrelonam.map(a => (selonq.helonad, a)) #::: allUniquelonPairs(selonq.tail)
    }
  }
}
