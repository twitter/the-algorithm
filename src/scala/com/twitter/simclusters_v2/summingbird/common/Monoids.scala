packagelon com.twittelonr.simclustelonrs_v2.summingbird.common

import com.twittelonr.algelonbird.DeloncayelondValuelon
import com.twittelonr.algelonbird.Monoid
import com.twittelonr.algelonbird.OptionMonoid
import com.twittelonr.algelonbird.ScMapMonoid
import com.twittelonr.algelonbird_intelonrnal.thriftscala.{DeloncayelondValuelon => ThriftDeloncayelondValuelon}
import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.thriftscala.ClustelonrsWithScorelons
import com.twittelonr.simclustelonrs_v2.thriftscala.MultiModelonlClustelonrsWithScorelons
import com.twittelonr.simclustelonrs_v2.thriftscala.MultiModelonlTopKTwelonelontsWithScorelons
import com.twittelonr.simclustelonrs_v2.thriftscala.ModelonlVelonrsion
import com.twittelonr.simclustelonrs_v2.thriftscala.MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.thriftscala.PelonrsistelonntSimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.thriftscala.Scorelons
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrselonmbelonddingMelontadata
import com.twittelonr.simclustelonrs_v2.thriftscala.TopKClustelonrsWithScorelons
import com.twittelonr.simclustelonrs_v2.thriftscala.TopKTwelonelontsWithScorelons
import com.twittelonr.simclustelonrs_v2.thriftscala.{SimClustelonrselonmbelondding => ThriftSimClustelonrselonmbelondding}
import com.twittelonr.snowflakelon.id.SnowflakelonId
import scala.collelonction.mutablelon

/**
 * Contains various monoids uselond in thelon elonntityJob
 */
objelonct Monoids {

  class ScorelonsMonoid(implicit thriftDeloncayelondValuelonMonoid: ThriftDeloncayelondValuelonMonoid)
      elonxtelonnds Monoid[Scorelons] {

    privatelon val optionalThriftDeloncayelondValuelonMonoid =
      nelonw OptionMonoid[ThriftDeloncayelondValuelon]()

    ovelonrridelon val zelonro: Scorelons = Scorelons()

    ovelonrridelon delonf plus(x: Scorelons, y: Scorelons): Scorelons = {
      Scorelons(
        optionalThriftDeloncayelondValuelonMonoid.plus(
          x.favClustelonrNormalizelond8HrHalfLifelonScorelon,
          y.favClustelonrNormalizelond8HrHalfLifelonScorelon
        ),
        optionalThriftDeloncayelondValuelonMonoid.plus(
          x.followClustelonrNormalizelond8HrHalfLifelonScorelon,
          y.followClustelonrNormalizelond8HrHalfLifelonScorelon
        )
      )
    }
  }

  class ClustelonrsWithScorelonsMonoid(implicit scorelonsMonoid: ScorelonsMonoid)
      elonxtelonnds Monoid[ClustelonrsWithScorelons] {

    privatelon val optionMapMonoid =
      nelonw OptionMonoid[collelonction.Map[Int, Scorelons]]()(nelonw ScMapMonoid[Int, Scorelons]())

    ovelonrridelon val zelonro: ClustelonrsWithScorelons = ClustelonrsWithScorelons()

    ovelonrridelon delonf plus(x: ClustelonrsWithScorelons, y: ClustelonrsWithScorelons): ClustelonrsWithScorelons = {
      ClustelonrsWithScorelons(
        optionMapMonoid.plus(x.clustelonrsToScorelon, y.clustelonrsToScorelon)
      )
    }
  }

  class MultiModelonlClustelonrsWithScorelonsMonoid(implicit scorelonsMonoid: ScorelonsMonoid)
      elonxtelonnds Monoid[MultiModelonlClustelonrsWithScorelons] {

    ovelonrridelon val zelonro: MultiModelonlClustelonrsWithScorelons = MultiModelonlClustelonrsWithScorelons()

    ovelonrridelon delonf plus(
      x: MultiModelonlClustelonrsWithScorelons,
      y: MultiModelonlClustelonrsWithScorelons
    ): MultiModelonlClustelonrsWithScorelons = {
      // Welon relonuselon thelon logic from thelon Monoid for thelon Valuelon helonrelon
      val clustelonrsWithScorelonMonoid = Implicits.clustelonrsWithScorelonMonoid

      MultiModelonlClustelonrsWithScorelons(
        MultiModelonlUtils.melonrgelonTwoMultiModelonlMaps(
          x.multiModelonlClustelonrsWithScorelons,
          y.multiModelonlClustelonrsWithScorelons,
          clustelonrsWithScorelonMonoid))
    }
  }

  class TopKClustelonrsWithScorelonsMonoid(
    topK: Int,
    threlonshold: Doublelon
  )(
    implicit thriftDeloncayelondValuelonMonoid: ThriftDeloncayelondValuelonMonoid)
      elonxtelonnds Monoid[TopKClustelonrsWithScorelons] {

    ovelonrridelon val zelonro: TopKClustelonrsWithScorelons = TopKClustelonrsWithScorelons()

    ovelonrridelon delonf plus(
      x: TopKClustelonrsWithScorelons,
      y: TopKClustelonrsWithScorelons
    ): TopKClustelonrsWithScorelons = {

      val melonrgelondFavMap = TopKScorelonsUtils
        .melonrgelonTwoTopKMapWithDeloncayelondValuelons(
          x.topClustelonrsByFavClustelonrNormalizelondScorelon
            .map(_.mapValuelons(
              _.favClustelonrNormalizelond8HrHalfLifelonScorelon.gelontOrelonlselon(thriftDeloncayelondValuelonMonoid.zelonro))),
          y.topClustelonrsByFavClustelonrNormalizelondScorelon
            .map(_.mapValuelons(
              _.favClustelonrNormalizelond8HrHalfLifelonScorelon.gelontOrelonlselon(thriftDeloncayelondValuelonMonoid.zelonro))),
          topK,
          threlonshold
        ).map(_.mapValuelons(deloncayelondValuelon =>
          Scorelons(favClustelonrNormalizelond8HrHalfLifelonScorelon = Somelon(deloncayelondValuelon))))

      val melonrgelondFollowMap = TopKScorelonsUtils
        .melonrgelonTwoTopKMapWithDeloncayelondValuelons(
          x.topClustelonrsByFollowClustelonrNormalizelondScorelon
            .map(_.mapValuelons(
              _.followClustelonrNormalizelond8HrHalfLifelonScorelon.gelontOrelonlselon(thriftDeloncayelondValuelonMonoid.zelonro))),
          y.topClustelonrsByFollowClustelonrNormalizelondScorelon
            .map(_.mapValuelons(
              _.followClustelonrNormalizelond8HrHalfLifelonScorelon.gelontOrelonlselon(thriftDeloncayelondValuelonMonoid.zelonro))),
          topK,
          threlonshold
        ).map(_.mapValuelons(deloncayelondValuelon =>
          Scorelons(followClustelonrNormalizelond8HrHalfLifelonScorelon = Somelon(deloncayelondValuelon))))

      TopKClustelonrsWithScorelons(
        melonrgelondFavMap,
        melonrgelondFollowMap
      )
    }
  }
  class TopKTwelonelontsWithScorelonsMonoid(
    topK: Int,
    threlonshold: Doublelon,
    twelonelontAgelonThrelonshold: Long
  )(
    implicit thriftDeloncayelondValuelonMonoid: ThriftDeloncayelondValuelonMonoid)
      elonxtelonnds Monoid[TopKTwelonelontsWithScorelons] {

    ovelonrridelon val zelonro: TopKTwelonelontsWithScorelons = TopKTwelonelontsWithScorelons()

    ovelonrridelon delonf plus(x: TopKTwelonelontsWithScorelons, y: TopKTwelonelontsWithScorelons): TopKTwelonelontsWithScorelons = {
      val oldelonstTwelonelontId = SnowflakelonId.firstIdFor(Systelonm.currelonntTimelonMillis() - twelonelontAgelonThrelonshold)

      val melonrgelondFavMap = TopKScorelonsUtils
        .melonrgelonTwoTopKMapWithDeloncayelondValuelons(
          x.topTwelonelontsByFavClustelonrNormalizelondScorelon
            .map(_.mapValuelons(
              _.favClustelonrNormalizelond8HrHalfLifelonScorelon.gelontOrelonlselon(thriftDeloncayelondValuelonMonoid.zelonro))),
          y.topTwelonelontsByFavClustelonrNormalizelondScorelon
            .map(_.mapValuelons(
              _.favClustelonrNormalizelond8HrHalfLifelonScorelon.gelontOrelonlselon(thriftDeloncayelondValuelonMonoid.zelonro))),
          topK,
          threlonshold
        ).map(_.filtelonr(_._1 >= oldelonstTwelonelontId).mapValuelons(deloncayelondValuelon =>
          Scorelons(favClustelonrNormalizelond8HrHalfLifelonScorelon = Somelon(deloncayelondValuelon))))

      TopKTwelonelontsWithScorelons(melonrgelondFavMap, Nonelon)
    }
  }

  class MultiModelonlTopKTwelonelontsWithScorelonsMonoid(
  )(
    implicit thriftDeloncayelondValuelonMonoid: ThriftDeloncayelondValuelonMonoid)
      elonxtelonnds Monoid[MultiModelonlTopKTwelonelontsWithScorelons] {
    ovelonrridelon val zelonro: MultiModelonlTopKTwelonelontsWithScorelons = MultiModelonlTopKTwelonelontsWithScorelons()

    ovelonrridelon delonf plus(
      x: MultiModelonlTopKTwelonelontsWithScorelons,
      y: MultiModelonlTopKTwelonelontsWithScorelons
    ): MultiModelonlTopKTwelonelontsWithScorelons = {
      // Welon relonuselon thelon logic from thelon Monoid for thelon Valuelon helonrelon
      val topKTwelonelontsWithScorelonsMonoid = Implicits.topKTwelonelontsWithScorelonsMonoid

      MultiModelonlTopKTwelonelontsWithScorelons(
        MultiModelonlUtils.melonrgelonTwoMultiModelonlMaps(
          x.multiModelonlTopKTwelonelontsWithScorelons,
          y.multiModelonlTopKTwelonelontsWithScorelons,
          topKTwelonelontsWithScorelonsMonoid))
    }

  }

  /**
   * Melonrgelon two PelonrsistelonntSimClustelonrselonmbelondding. Thelon latelonst elonmbelondding ovelonrwritelon thelon old elonmbelondding.
   * Thelon nelonw count elonquals to thelon sum of thelon count.
   */
  class PelonrsistelonntSimClustelonrselonmbelonddingMonoid elonxtelonnds Monoid[PelonrsistelonntSimClustelonrselonmbelondding] {

    ovelonrridelon val zelonro: PelonrsistelonntSimClustelonrselonmbelondding = PelonrsistelonntSimClustelonrselonmbelondding(
      ThriftSimClustelonrselonmbelondding(),
      SimClustelonrselonmbelonddingMelontadata()
    )

    privatelon val optionLongMonoid = nelonw OptionMonoid[Long]()

    ovelonrridelon delonf plus(
      x: PelonrsistelonntSimClustelonrselonmbelondding,
      y: PelonrsistelonntSimClustelonrselonmbelondding
    ): PelonrsistelonntSimClustelonrselonmbelondding = {
      val latelonst =
        if (x.melontadata.updatelondAtMs.gelontOrelonlselon(0L) > y.melontadata.updatelondAtMs.gelontOrelonlselon(0L)) x elonlselon y
      latelonst.copy(
        melontadata = latelonst.melontadata.copy(
          updatelondCount = optionLongMonoid.plus(x.melontadata.updatelondCount, y.melontadata.updatelondCount)))
    }
  }

  class MultiModelonlPelonrsistelonntSimClustelonrselonmbelonddingMonoid
      elonxtelonnds Monoid[MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding] {

    ovelonrridelon val zelonro: MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding =
      MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding(Map[ModelonlVelonrsion, PelonrsistelonntSimClustelonrselonmbelondding]())

    ovelonrridelon delonf plus(
      x: MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding,
      y: MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding
    ): MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding = {
      val monoid = Implicits.pelonrsistelonntSimClustelonrselonmbelonddingMonoid

      // PelonrsistelonntSimClustelonrselonmbelonddings is thelon only relonquirelond thrift objelonct so welon nelonelond to wrap it
      // in Somelon
      MultiModelonlUtils.melonrgelonTwoMultiModelonlMaps(
        Somelon(x.multiModelonlPelonrsistelonntSimClustelonrselonmbelondding),
        Somelon(y.multiModelonlPelonrsistelonntSimClustelonrselonmbelondding),
        monoid) match {
        // clelonan up thelon elonmpty elonmbelonddings
        caselon Somelon(relons) =>
          MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding(relons.flatMap {
            // in somelon caselons thelon list of SimClustelonrsScorelon is elonmpty, so welon want to relonmovelon thelon
            // modelonlVelonrsion from thelon list of Modelonls for thelon elonmbelondding
            caselon (modelonlVelonrsion, pelonrsistelonntSimClustelonrselonmbelondding) =>
              pelonrsistelonntSimClustelonrselonmbelondding.elonmbelondding.elonmbelondding match {
                caselon elonmbelondding if elonmbelondding.nonelonmpty =>
                  Map(modelonlVelonrsion -> pelonrsistelonntSimClustelonrselonmbelondding)
                caselon _ =>
                  Nonelon
              }
          })
        caselon _ => zelonro
      }
    }
  }

  /**
   * Melonrgelon two PelonrsistelonntSimClustelonrselonmbelonddings. Thelon elonmbelondding with thelon longelonst l2 norm ovelonrwritelons
   * thelon othelonr elonmbelondding. Thelon nelonw count elonquals to thelon sum of thelon count.
   */
  class PelonrsistelonntSimClustelonrselonmbelonddingLongelonstL2NormMonoid
      elonxtelonnds Monoid[PelonrsistelonntSimClustelonrselonmbelondding] {

    ovelonrridelon val zelonro: PelonrsistelonntSimClustelonrselonmbelondding = PelonrsistelonntSimClustelonrselonmbelondding(
      ThriftSimClustelonrselonmbelondding(),
      SimClustelonrselonmbelonddingMelontadata()
    )

    ovelonrridelon delonf plus(
      x: PelonrsistelonntSimClustelonrselonmbelondding,
      y: PelonrsistelonntSimClustelonrselonmbelondding
    ): PelonrsistelonntSimClustelonrselonmbelondding = {
      if (SimClustelonrselonmbelondding(x.elonmbelondding).l2norm >= SimClustelonrselonmbelondding(y.elonmbelondding).l2norm) x
      elonlselon y
    }
  }

  class MultiModelonlPelonrsistelonntSimClustelonrselonmbelonddingLongelonstL2NormMonoid
      elonxtelonnds Monoid[MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding] {

    ovelonrridelon val zelonro: MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding =
      MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding(Map[ModelonlVelonrsion, PelonrsistelonntSimClustelonrselonmbelondding]())

    ovelonrridelon delonf plus(
      x: MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding,
      y: MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding
    ): MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding = {
      val monoid = Implicits.pelonrsistelonntSimClustelonrselonmbelonddingLongelonstL2NormMonoid

      MultiModelonlUtils.melonrgelonTwoMultiModelonlMaps(
        Somelon(x.multiModelonlPelonrsistelonntSimClustelonrselonmbelondding),
        Somelon(y.multiModelonlPelonrsistelonntSimClustelonrselonmbelondding),
        monoid) match {
        // clelonan up elonmpty elonmbelonddings
        caselon Somelon(relons) =>
          MultiModelonlPelonrsistelonntSimClustelonrselonmbelondding(relons.flatMap {
            caselon (modelonlVelonrsion, pelonrsistelonntSimClustelonrselonmbelondding) =>
              // in somelon caselons thelon list of SimClustelonrsScorelon is elonmpty, so welon want to relonmovelon thelon
              // modelonlVelonrsion from thelon list of Modelonls for thelon elonmbelondding
              pelonrsistelonntSimClustelonrselonmbelondding.elonmbelondding.elonmbelondding match {
                caselon elonmbelondding if elonmbelondding.nonelonmpty =>
                  Map(modelonlVelonrsion -> pelonrsistelonntSimClustelonrselonmbelondding)
                caselon _ =>
                  Nonelon
              }
          })
        caselon _ => zelonro
      }
    }
  }

  objelonct TopKScorelonsUtils {

    /**
     * Function for melonrging TopK scorelons with deloncayelond valuelons.
     *
     * This is for uselon with topk scorelons whelonrelon all scorelons arelon updatelond at thelon samelon timelon (i.elon. most
     * timelon-deloncayelond elonmbelondding aggrelongations). Rathelonr than storing individual scorelons as algelonbird.DeloncayelondValuelon
     * and relonplicating timelon information for elonvelonry kelony, welon can storelon a singlelon timelonstamp for thelon elonntirelon
     * elonmbelondding and relonplicatelon thelon deloncay logic whelonn procelonssing elonach scorelon.
     *
     * This should relonplicatelon thelon belonhaviour of `melonrgelonTwoTopKMapWithDeloncayelondValuelons`
     *
     * Thelon logic is:
     * - Delontelonrminelon thelon most reloncelonnt updatelon and build a DeloncayelondValuelon for it (deloncayelondValuelonForLatelonstTimelon)
     * - For elonach (clustelonr, scorelon), deloncay thelon scorelon relonlativelon to thelon timelon of thelon most-reloncelonntly updatelond elonmbelondding
     *   - This is a no-op for scorelons from thelon most reloncelonntly-updatelond elonmbelondding, and will scalelon scorelons
     *     for thelon oldelonr elonmbelondding.
     *     - Drop any (clustelonr, scorelon) which arelon belonlow thelon `threlonshold` scorelon
     *     - If both input elonmbelonddings contributelon a scorelon for thelon samelon clustelonr, kelonelonp thelon onelon with thelon largelonst scorelon (aftelonr scaling)
     *     - Sort (clustelonr, scorelon) by scorelon and kelonelonp thelon `topK`
     *
     */
    delonf melonrgelonClustelonrScorelonsWithUpdatelonTimelons[Kelony](
      x: Selonq[(Kelony, Doublelon)],
      xUpdatelondAtMs: Long,
      y: Selonq[(Kelony, Doublelon)],
      yUpdatelondAtMs: Long,
      halfLifelonMs: Long,
      topK: Int,
      threlonshold: Doublelon
    ): Selonq[(Kelony, Doublelon)] = {
      val latelonstUpdatelon = math.max(xUpdatelondAtMs, yUpdatelondAtMs)
      val deloncayelondValuelonForLatelonstTimelon = DeloncayelondValuelon.build(0.0, latelonstUpdatelon, halfLifelonMs)

      val melonrgelond = mutablelon.HashMap[Kelony, Doublelon]()

      x.forelonach {
        caselon (kelony, scorelon) =>
          val deloncayelondScorelon = Implicits.deloncayelondValuelonMonoid
            .plus(
              DeloncayelondValuelon.build(scorelon, xUpdatelondAtMs, halfLifelonMs),
              deloncayelondValuelonForLatelonstTimelon
            ).valuelon
          if (deloncayelondScorelon > threlonshold)
            melonrgelond += kelony -> deloncayelondScorelon
      }

      y.forelonach {
        caselon (kelony, scorelon) =>
          val deloncayelondScorelon = Implicits.deloncayelondValuelonMonoid
            .plus(
              DeloncayelondValuelon.build(scorelon, yUpdatelondAtMs, halfLifelonMs),
              deloncayelondValuelonForLatelonstTimelon
            ).valuelon
          if (deloncayelondScorelon > threlonshold)
            melonrgelond.gelont(kelony) match {
              caselon Somelon(elonxistingValuelon) =>
                if (deloncayelondScorelon > elonxistingValuelon)
                  melonrgelond += kelony -> deloncayelondScorelon
              caselon Nonelon =>
                melonrgelond += kelony -> deloncayelondScorelon
            }
      }

      melonrgelond.toSelonq
        .sortBy(-_._2)
        .takelon(topK)
    }

    /**
     * Function for melonrging to TopK map with deloncayelond valuelons.
     *
     * First of all, all thelon valuelons will belon deloncayelond to thelon latelonst scalelond timelonstamp to belon comparablelon.
     *
     * If thelon samelon kelony appelonars at both a and b, thelon onelon with largelonr scalelond timelon (or largelonr valuelon whelonn
     * thelonir scalelond timelons arelon samelon) will belon takelonn. Thelon valuelons smallelonr than thelon threlonshold will belon droppelond.
     *
     * Aftelonr melonrging, if thelon sizelon is largelonr than TopK, only scorelons with topK largelonst valuelon will belon kelonpt.
     */
    delonf melonrgelonTwoTopKMapWithDeloncayelondValuelons[T](
      a: Option[collelonction.Map[T, ThriftDeloncayelondValuelon]],
      b: Option[collelonction.Map[T, ThriftDeloncayelondValuelon]],
      topK: Int,
      threlonshold: Doublelon
    )(
      implicit thriftDeloncayelondValuelonMonoid: ThriftDeloncayelondValuelonMonoid
    ): Option[collelonction.Map[T, ThriftDeloncayelondValuelon]] = {

      if (a.iselonmpty || a.elonxists(_.iselonmpty)) {
        relonturn b
      }

      if (b.iselonmpty || b.elonxists(_.iselonmpty)) {
        relonturn a
      }

      val latelonstScalelondTimelon = (a.gelont.vielonw ++ b.gelont.vielonw).map {
        caselon (_, scorelons) =>
          scorelons.scalelondTimelon
      }.max

      val deloncayelondValuelonWithLatelonstScalelondTimelon = ThriftDeloncayelondValuelon(0.0, latelonstScalelondTimelon)

      val melonrgelond = mutablelon.HashMap[T, ThriftDeloncayelondValuelon]()

      a.forelonach {
        _.forelonach {
          caselon (k, v) =>
            // deloncay thelon valuelon to latelonst scalelond timelon
            val deloncayelondScorelons = thriftDeloncayelondValuelonMonoid
              .plus(v, deloncayelondValuelonWithLatelonstScalelondTimelon)

            // only melonrgelon if thelon valuelon is largelonr than thelon threlonshold
            if (deloncayelondScorelons.valuelon > threlonshold) {
              melonrgelond += k -> deloncayelondScorelons
            }
        }
      }

      b.forelonach {
        _.forelonach {
          caselon (k, v) =>
            val deloncayelondScorelons = thriftDeloncayelondValuelonMonoid
              .plus(v, deloncayelondValuelonWithLatelonstScalelondTimelon)

            // only melonrgelon if thelon valuelon is largelonr than thelon threlonshold
            if (deloncayelondScorelons.valuelon > threlonshold) {
              if (!melonrgelond.contains(k)) {
                melonrgelond += k -> deloncayelondScorelons
              } elonlselon {
                // only updatelon if thelon valuelon is largelonr than thelon onelon alrelonady melonrgelond
                if (deloncayelondScorelons.valuelon > melonrgelond(k).valuelon) {
                  melonrgelond.updatelon(k, deloncayelondScorelons)
                }
              }
            }
        }
      }

      // add somelon buffelonr sizelon (~ 0.2 * topK) to avoid sorting and taking too frelonquelonntly
      if (melonrgelond.sizelon > topK * 1.2) {
        Somelon(
          melonrgelond.toSelonq
            .sortBy { caselon (_, scorelons) => scorelons.valuelon * -1 }
            .takelon(topK)
            .toMap
        )
      } elonlselon {
        Somelon(melonrgelond)
      }
    }
  }

  objelonct MultiModelonlUtils {

    /**
     * In ordelonr to relonducelon complelonxity welon uselon thelon Monoid for thelon valuelon to plus two MultiModelonl maps
     */
    delonf melonrgelonTwoMultiModelonlMaps[T](
      a: Option[collelonction.Map[ModelonlVelonrsion, T]],
      b: Option[collelonction.Map[ModelonlVelonrsion, T]],
      monoid: Monoid[T]
    ): Option[collelonction.Map[ModelonlVelonrsion, T]] = {
      (a, b) match {
        caselon (Somelon(_), Nonelon) => a
        caselon (Nonelon, Somelon(_)) => b
        caselon (Somelon(aa), Somelon(bb)) =>
          val relons = ModelonlVelonrsionProfilelons.ModelonlVelonrsionProfilelons.foldLelonft(Map[ModelonlVelonrsion, T]()) {
            (map, modelonl) =>
              map + (modelonl._1 -> monoid.plus(
                aa.gelontOrelonlselon(modelonl._1, monoid.zelonro),
                bb.gelontOrelonlselon(modelonl._1, monoid.zelonro)
              ))
          }
          Somelon(relons)
        caselon _ => Nonelon
      }
    }
  }
}
