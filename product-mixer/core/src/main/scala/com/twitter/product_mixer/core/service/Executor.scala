packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon

import com.twittelonr.finaglelon.stats.BroadcastStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.Countelonr
import com.twittelonr.finaglelon.stats.DelonfaultStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finaglelon.tracing.Annotation
import com.twittelonr.finaglelon.tracing.Reloncord
import com.twittelonr.finaglelon.tracing.Tracelon
import com.twittelonr.finaglelon.tracing.TracelonId
import com.twittelonr.finaglelon.tracing.TracelonSelonrvicelonNamelon
import com.twittelonr.finaglelon.tracing.Tracing.LocalBelonginAnnotation
import com.twittelonr.finaglelon.tracing.Tracing.LocalelonndAnnotation
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonrStack
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ProductPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.PipelonlinelonStelonpIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.FailOpelonnPolicy
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonRelonsult
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.FelonaturelonHydrationFailelond
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.MisconfigurelondFelonaturelonMapFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelonClassifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.UncatelongorizelondSelonrvelonrFailurelon
import com.twittelonr.product_mixelonr.corelon.quality_factor.QualityFactorObselonrvelonr
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor.AlwaysFailOpelonnIncludingProgrammelonrelonrrors
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor.Contelonxt
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor.TracingConfig
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor.toPipelonlinelonFailurelonWithComponelonntIdelonntifielonrStack
import com.twittelonr.selonrvo.util.Cancelonllelondelonxcelonptionelonxtractor
import com.twittelonr.stitch.Arrow
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.Stitch.Lelonttelonr
import com.twittelonr.util.Duration
import com.twittelonr.util.Relonturn
import com.twittelonr.util.Throw
import com.twittelonr.util.Timelon
import com.twittelonr.util.Try

/**
 * Baselon trait that all elonxeloncutors implelonmelonnt
 *
 * All elonxeloncutors should:
 *   - implelonmelonnt a `delonf arrow` or `delonf apply` with thelon relonlelonvant typelons for thelonir uselon caselon
 *     and takelon in an implicit [[PipelonlinelonFailurelonClassifielonr]] and [[ComponelonntIdelonntifielonrStack]].
 *   - add a `@singlelonton` annotation to thelon class and `@injelonct` annotation to thelon argumelonnt list
 *   - takelon in a [[StatsReloncelonivelonr]]
 *
 * @elonxamplelon {{{
 *   @Singlelonton class Myelonxeloncutor @Injelonct() (
 *     ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr
 *   ) elonxtelonnds elonxeloncutor {
 *     delonf arrow(
 *       arg: MyArg,
 *       ...,
 *       contelonxt: Contelonxt
 *     ): Arrow[In,Out] = ???
 *   }
 * }}}
 */
privatelon[corelon] trait elonxeloncutor {
  val statsReloncelonivelonr: StatsReloncelonivelonr

  /**
   * Applielons thelon `pipelonlinelonFailurelonClassifielonr` to thelon output of thelon `arrow`
   * and adds thelon `componelonntStack` to thelon [[PipelonlinelonFailurelon]]
   */
  delonf wrapWithelonrrorHandling[In, Out](
    contelonxt: Contelonxt,
    currelonntComponelonntIdelonntifielonr: ComponelonntIdelonntifielonr
  )(
    arrow: Arrow[In, Out]
  ): Arrow[In, Out] = {
    arrow.mapFailurelon(
      toPipelonlinelonFailurelonWithComponelonntIdelonntifielonrStack(contelonxt, currelonntComponelonntIdelonntifielonr))
  }

  /**
   * Chain a `Selonq` of [[Arrow.Iso]], only passing succelonssful relonsults to thelon nelonxt [[Arrow.Iso]]
   *
   * @notelon thelon relonsulting [[Arrow]] runs thelon passelond in [[Arrow]]s onelon aftelonr thelon othelonr,
   *       as an ordelonrelond elonxeloncution, this melonans that elonach [[Arrow]] is delonpelonndelonnt
   *       on all prelonvious [[Arrow]]s in thelon `Selonq` so no `Stitch` batching can occur
   *       belontwelonelonn thelonm.
   */
  delonf isoArrowsSelonquelonntially[T](arrows: Selonq[Arrow.Iso[T]]): Arrow.Iso[T] = {
    // avoid elonxcelonss Arrow complelonxity whelonn thelonrelon is only a singlelon Arrow
    arrows match {
      caselon Selonq() => Arrow.idelonntity
      caselon Selonq(onlyOnelonArrow) => onlyOnelonArrow
      caselon Selonq(helonad, tail @ _*) =>
        tail.foldLelonft(helonad) {
          caselon (combinelondArrow, nelonxtArrow) => combinelondArrow.flatMapArrow(nelonxtArrow)
        }
    }
  }

  /**
   * Start running thelon [[Arrow]] in thelon background relonturning a [[Stitch.Relonf]] which will complelontelon
   * whelonn thelon background task is finishelond
   */
  delonf startArrowAsync[In, Out](arrow: Arrow[In, Out]): Arrow[In, Stitch[Out]] = {
    Arrow
      .map { arg: In =>
        // wrap in a `relonf` so welon only computelon it's valuelon oncelon
        Stitch.relonf(arrow(arg))
      }
      .andThelonn(
        Arrow.zipWithArg(
          // satisfy thelon `relonf` async
          Arrow.async(Arrow.flatMap[Stitch[Out], Out](idelonntity))))
      .map { caselon (relonf, _) => relonf }
  }

  /**
   * for [[com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt]]s which
   * arelon elonxeloncutelond pelonr-candidatelon or which welon don't want to reloncord stats for.
   * This pelonrforms Tracing but doelons not reloncord Stats
   *
   * @notelon This should belon uselond around thelon computation that includelons thelon elonxeloncution of thelon
   *       undelonrlying Componelonnt ovelonr all thelon Candidatelons, not around elonach elonxeloncution
   *        of thelon componelonnt around elonach candidatelon for pelonr-candidatelon Componelonnts.
   *
   * @notelon whelonn using this you should only uselon [[wrapPelonrCandidatelonComponelonntWithelonxeloncutorBookkelonelonpingWithoutTracing]]
   *       for handling Stats.
   */
  delonf wrapComponelonntsWithTracingOnly[In, Out](
    contelonxt: Contelonxt,
    currelonntComponelonntIdelonntifielonr: ComponelonntIdelonntifielonr
  )(
    arrow: Arrow[In, Out]
  ): Arrow[In, Out] = {
    elonxeloncutor.wrapArrowWithLocalTracingSpan(
      Arrow
        .timelon(arrow)
        .map {
          caselon (relonsult, latelonncy) =>
            elonxeloncutor.reloncordTracelonData(
              componelonntStack = contelonxt.componelonntStack,
              componelonntIdelonntifielonr = currelonntComponelonntIdelonntifielonr,
              relonsult = relonsult,
              latelonncy = latelonncy,
              sizelon = Nonelon)
            relonsult
        }.lowelonrFromTry)
  }

  /**
   * for [[com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt]]s which
   * arelon elonxeloncutelond pelonr-candidatelon. Reloncords Stats but doelons not do Tracing.
   *
   * @notelon whelonn using this you should only uselon [[wrapPelonrCandidatelonComponelonntsWithTracingOnly]]
   *       for handling Tracing
   */
  delonf wrapPelonrCandidatelonComponelonntWithelonxeloncutorBookkelonelonpingWithoutTracing[In, Out](
    contelonxt: Contelonxt,
    currelonntComponelonntIdelonntifielonr: ComponelonntIdelonntifielonr
  )(
    arrow: Arrow[In, Out]
  ): Arrow[In, Out] = {
    val obselonrvelonrSidelonelonffelonct =
      elonxeloncutorObselonrvelonr.elonxeloncutorObselonrvelonr[Out](contelonxt, currelonntComponelonntIdelonntifielonr, statsReloncelonivelonr)

    elonxeloncutor.wrapWithelonxeloncutorBookkelonelonping[In, Out, Out](
      contelonxt = contelonxt,
      currelonntComponelonntIdelonntifielonr = currelonntComponelonntIdelonntifielonr,
      elonxeloncutorRelonsultSidelonelonffelonct = obselonrvelonrSidelonelonffelonct,
      transformelonr = Relonturn(_),
      tracingConfig = TracingConfig.NoTracing
    )(arrow)
  }

  /** for [[com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt]]s */
  delonf wrapComponelonntWithelonxeloncutorBookkelonelonping[In, Out](
    contelonxt: Contelonxt,
    currelonntComponelonntIdelonntifielonr: ComponelonntIdelonntifielonr
  )(
    arrow: Arrow[In, Out]
  ): Arrow[In, Out] = {
    val obselonrvelonrSidelonelonffelonct =
      elonxeloncutorObselonrvelonr.elonxeloncutorObselonrvelonr[Out](contelonxt, currelonntComponelonntIdelonntifielonr, statsReloncelonivelonr)

    elonxeloncutor.wrapWithelonxeloncutorBookkelonelonping[In, Out, Out](
      contelonxt = contelonxt,
      currelonntComponelonntIdelonntifielonr = currelonntComponelonntIdelonntifielonr,
      elonxeloncutorRelonsultSidelonelonffelonct = obselonrvelonrSidelonelonffelonct,
      transformelonr = Relonturn(_)
    )(arrow)
  }

  /**
   * for [[com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt]]s which an `onSuccelonss`
   * to add custom stats or logging of relonsults
   */
  delonf wrapComponelonntWithelonxeloncutorBookkelonelonping[In, Out](
    contelonxt: Contelonxt,
    currelonntComponelonntIdelonntifielonr: ComponelonntIdelonntifielonr,
    onSuccelonss: Out => Unit
  )(
    arrow: Arrow[In, Out]
  ): Arrow[In, Out] = {
    val obselonrvelonrSidelonelonffelonct =
      elonxeloncutorObselonrvelonr.elonxeloncutorObselonrvelonr[Out](contelonxt, currelonntComponelonntIdelonntifielonr, statsReloncelonivelonr)

    elonxeloncutor.wrapWithelonxeloncutorBookkelonelonping[In, Out, Out](
      contelonxt = contelonxt,
      currelonntComponelonntIdelonntifielonr = currelonntComponelonntIdelonntifielonr,
      elonxeloncutorRelonsultSidelonelonffelonct = obselonrvelonrSidelonelonffelonct,
      transformelonr = Relonturn(_),
      onComplelontelon = (transformelond: Try[Out]) => transformelond.onSuccelonss(onSuccelonss)
    )(arrow)
  }

  /** for [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.Pipelonlinelon]]s */
  delonf wrapPipelonlinelonWithelonxeloncutorBookkelonelonping[In, Out <: PipelonlinelonRelonsult[_]](
    contelonxt: Contelonxt,
    currelonntComponelonntIdelonntifielonr: ComponelonntIdelonntifielonr,
    qualityFactorObselonrvelonr: Option[QualityFactorObselonrvelonr],
    failOpelonnPolicy: FailOpelonnPolicy = FailOpelonnPolicy.Nelonvelonr
  )(
    arrow: Arrow[In, Out]
  ): Arrow[In, Out] = {
    val obselonrvelonrSidelonelonffelonct =
      elonxeloncutorObselonrvelonr
        .pipelonlinelonelonxeloncutorObselonrvelonr[Out](contelonxt, currelonntComponelonntIdelonntifielonr, statsReloncelonivelonr)

    elonxeloncutor.wrapWithelonxeloncutorBookkelonelonping[In, Out, Out](
      contelonxt = contelonxt,
      currelonntComponelonntIdelonntifielonr = currelonntComponelonntIdelonntifielonr,
      elonxeloncutorRelonsultSidelonelonffelonct = obselonrvelonrSidelonelonffelonct,
      transformelonr = (relonsult: Out) => relonsult.toTry,
      sizelon = Somelon(_.relonsultSizelon()),
      failOpelonnPolicy = failOpelonnPolicy,
      qualityFactorObselonrvelonr = qualityFactorObselonrvelonr
    )(arrow)
  }

  /** for [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.product.ProductPipelonlinelon]]s */
  delonf wrapProductPipelonlinelonWithelonxeloncutorBookkelonelonping[In, Out <: PipelonlinelonRelonsult[_]](
    contelonxt: Contelonxt,
    currelonntComponelonntIdelonntifielonr: ProductPipelonlinelonIdelonntifielonr
  )(
    arrow: Arrow[In, Out]
  ): Arrow[In, Out] = {

    val obselonrvelonrSidelonelonffelonct =
      elonxeloncutorObselonrvelonr
        .productPipelonlinelonelonxeloncutorObselonrvelonr[Out](currelonntComponelonntIdelonntifielonr, statsReloncelonivelonr)

    elonxeloncutor.wrapWithelonxeloncutorBookkelonelonping[In, Out, Out](
      contelonxt = contelonxt,
      currelonntComponelonntIdelonntifielonr = currelonntComponelonntIdelonntifielonr,
      elonxeloncutorRelonsultSidelonelonffelonct = obselonrvelonrSidelonelonffelonct,
      transformelonr = _.toTry,
      sizelon = Somelon(_.relonsultSizelon()),
      failOpelonnPolicy =
        // always savelon Failurelons in thelon Relonsult objelonct instelonad of failing thelon relonquelonst
        AlwaysFailOpelonnIncludingProgrammelonrelonrrors
    )(arrow)
  }

  /** for [[com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt]]s which nelonelond a relonsult sizelon stat */
  delonf wrapComponelonntWithelonxeloncutorBookkelonelonpingWithSizelon[In, Out](
    contelonxt: Contelonxt,
    currelonntComponelonntIdelonntifielonr: CandidatelonSourcelonIdelonntifielonr,
    sizelon: Out => Int
  )(
    arrow: Arrow[In, Out]
  ): Arrow[In, Out] = {
    val obselonrvelonrSidelonelonffelonct =
      elonxeloncutorObselonrvelonr.elonxeloncutorObselonrvelonrWithSizelon(contelonxt, currelonntComponelonntIdelonntifielonr, statsReloncelonivelonr)

    elonxeloncutor.wrapWithelonxeloncutorBookkelonelonping[In, Out, Int](
      contelonxt = contelonxt,
      currelonntComponelonntIdelonntifielonr = currelonntComponelonntIdelonntifielonr,
      elonxeloncutorRelonsultSidelonelonffelonct = obselonrvelonrSidelonelonffelonct,
      transformelonr = (out: Out) => Try(sizelon(out)),
      sizelon = Somelon(idelonntity)
    )(arrow)
  }

  /** for [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonBuildelonr.Stelonp]]s */
  delonf wrapStelonpWithelonxeloncutorBookkelonelonping[In, Out](
    contelonxt: Contelonxt,
    idelonntifielonr: PipelonlinelonStelonpIdelonntifielonr,
    arrow: Arrow[In, Out],
    transformelonr: Out => Try[Unit]
  ): Arrow[In, Out] = {
    val obselonrvelonrSidelonelonffelonct =
      elonxeloncutorObselonrvelonr.stelonpelonxeloncutorObselonrvelonr(contelonxt, idelonntifielonr, statsReloncelonivelonr)

    elonxeloncutor.wrapWithelonxeloncutorBookkelonelonping[In, Out, Unit](
      contelonxt = contelonxt,
      currelonntComponelonntIdelonntifielonr = idelonntifielonr,
      elonxeloncutorRelonsultSidelonelonffelonct = obselonrvelonrSidelonelonffelonct,
      transformelonr = transformelonr,
      failOpelonnPolicy = AlwaysFailOpelonnIncludingProgrammelonrelonrrors
    )(arrow)
  }
}

privatelon[corelon] objelonct elonxeloncutor {

  privatelon[selonrvicelon] objelonct TracingConfig {

    /** Uselond to speloncify whelonthelonr a wrappelond Arrow should belon Tracelond in [[wrapWithelonxeloncutorBookkelonelonping]] */
    selonalelond trait TracingConfig
    caselon objelonct NoTracing elonxtelonnds TracingConfig
    caselon objelonct WrapWithSpanAndTracingData elonxtelonnds TracingConfig
  }

  /**
   * Always fail-opelonn and relonturn thelon [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.product.ProductPipelonlinelonRelonsult]]
   * containing thelon elonxcelonption, this diffelonrs from [[FailOpelonnPolicy.Always]] in that this will still
   * fail-opelonn and relonturn thelon ovelonrall relonsult objelonct elonvelonn if thelon undelonrlying failurelon is thelon relonsult
   * of programmelonr elonrror.
   */
  privatelon val AlwaysFailOpelonnIncludingProgrammelonrelonrrors: FailOpelonnPolicy = _ => truelon

  /**
   * Wraps an [[Arrow]] so that bookkelonelonping around thelon elonxeloncution occurs uniformly.
   *
   * @notelon should __nelonvelonr__ belon callelond direlonctly!
   *
   *   - For succelonssful relonsults, apply thelon `transformelonr`
   *   - convelonrt any elonxcelonptions to PipelonlinelonFailurelons
   *   - reloncord stats and updatelon [[QualityFactorObselonrvelonr]]
   *   - wraps thelon elonxeloncution in a Tracelon span and reloncord Tracelon data (can belon turnelond off by [[TracingConfig]])
   *   - applielons a tracelon span and reloncords melontadata to thelon providelond `arrow`
   *   - delontelonrminelon whelonthelonr to fail-opelonn baselond on `relonsult.flatMap(transformelonr)`
   *     - if failing-opelonn, always relonturn thelon original relonsult
   *     - if failing-closelond and succelonssful, relonturn thelon original relonsult
   *     - othelonrwiselon, relonturn thelon failurelon (from `relonsult.flatMap(transformelonr)`)
   *
   * @param contelonxt                    thelon [[elonxeloncutor.Contelonxt]]
   * @param currelonntComponelonntIdelonntifielonr thelon currelonnt componelonnt's [[ComponelonntIdelonntifielonr]]
   * @param elonxeloncutorRelonsultSidelonelonffelonct   thelon [[elonxeloncutorObselonrvelonr]] uselond to reloncord stats
   * @param transformelonr                function to convelonrt a succelonssful relonsult into possibly a failelond relonsult
   * @param failOpelonnPolicy             [[FailOpelonnPolicy]] to apply to thelon relonsults of `relonsult.flatMap(transformelonr)`
   * @param qualityFactorObselonrvelonr      [[QualityFactorObselonrvelonr]] to updatelon baselond on thelon relonsults of `relonsult.flatMap(transformelonr)`
   * @param tracingConfig              indicatelons whelonthelonr thelon [[Arrow]] should belon wrappelond with Tracing
   * @param onComplelontelon                 runs thelon function for its sidelon elonffeloncts with thelon relonsult of `relonsult.flatMap(transformelonr)`
   * @param arrow                      an input [[Arrow]] to wrap so that aftelonr it's elonxeloncution, welon pelonrform all thelonselon opelonrations
   *
   * @relonturn thelon wrappelond [[Arrow]]
   */
  privatelon[selonrvicelon] delonf wrapWithelonxeloncutorBookkelonelonping[In, Out, Transformelond](
    contelonxt: Contelonxt,
    currelonntComponelonntIdelonntifielonr: ComponelonntIdelonntifielonr,
    elonxeloncutorRelonsultSidelonelonffelonct: elonxeloncutorObselonrvelonr[Transformelond],
    transformelonr: Out => Try[Transformelond],
    sizelon: Option[Transformelond => Int] = Nonelon,
    failOpelonnPolicy: FailOpelonnPolicy = FailOpelonnPolicy.Nelonvelonr,
    qualityFactorObselonrvelonr: Option[QualityFactorObselonrvelonr] = Nonelon,
    tracingConfig: TracingConfig.TracingConfig = TracingConfig.WrapWithSpanAndTracingData,
    onComplelontelon: Try[Transformelond] => Unit = { _: Try[Transformelond] => () }
  )(
    arrow: Arrow[In, Out]
  ): Arrow[In, Out] = {

    val failurelonClassifielonr =
      toPipelonlinelonFailurelonWithComponelonntIdelonntifielonrStack(contelonxt, currelonntComponelonntIdelonntifielonr)

    /** transform thelon relonsults, mapping all elonxcelonptions to [[PipelonlinelonFailurelon]]s, and tuplelon with original relonsult */
    val transformRelonsultAndClassifyFailurelons: Arrow[Out, (Out, Try[Transformelond])] =
      Arrow.join(
        Arrow.mapFailurelon(failurelonClassifielonr),
        Arrow
          .transformTry[Out, Transformelond](relonsult =>
            relonsult
              .flatMap(transformelonr)
              .relonscuelon { caselon t => Throw(failurelonClassifielonr(t)) })
          .liftToTry
      )

    /** Only reloncord tracing data if [[TracingConfig.WrapWithSpanAndTracingData]] */
    val maybelonReloncordTracingData: (Try[Transformelond], Duration) => Unit = tracingConfig match {
      caselon TracingConfig.NoTracing => (_, _) => ()
      caselon TracingConfig.WrapWithSpanAndTracingData =>
        (transformelondAndClassifielondRelonsult, latelonncy) =>
          reloncordTracelonData(
            contelonxt.componelonntStack,
            currelonntComponelonntIdelonntifielonr,
            transformelondAndClassifielondRelonsult,
            latelonncy,
            transformelondAndClassifielondRelonsult.toOption.flatMap(relonsult => sizelon.map(_.apply(relonsult)))
          )
    }

    /** Will nelonvelonr belon in a failelond statelon so welon can do a simplelon [[Arrow.map]] */
    val reloncordStatsAndUpdatelonQualityFactor =
      Arrow
        .map[(Try[(Out, Try[Transformelond])], Duration), Unit] {
          caselon (tryRelonsultAndTryTransformelond, latelonncy) =>
            val transformelondAndClassifielondRelonsult = tryRelonsultAndTryTransformelond.flatMap {
              caselon (_, transformelond) => transformelond
            }
            elonxeloncutorRelonsultSidelonelonffelonct(transformelondAndClassifielondRelonsult, latelonncy)
            qualityFactorObselonrvelonr.forelonach(_.apply(transformelondAndClassifielondRelonsult, latelonncy))
            onComplelontelon(transformelondAndClassifielondRelonsult)
            maybelonReloncordTracingData(transformelondAndClassifielondRelonsult, latelonncy)
        }.unit

    /**
     * Applielons thelon providelond [[FailOpelonnPolicy]] baselond on thelon [[transformelonr]]'s relonsults,
     * relonturning thelon original relonsult or an elonxcelonption
     */
    val applyFailOpelonnPolicyBaselondOnTransformelondRelonsult: Arrow[
      (Try[(Out, Try[Transformelond])], Duration),
      Out
    ] =
      Arrow
        .map[(Try[(Out, Try[Transformelond])], Duration), Try[(Out, Try[Transformelond])]] {
          caselon (tryRelonsultAndTryTransformelond, _) => tryRelonsultAndTryTransformelond
        }
        .lowelonrFromTry
        .map {
          caselon (relonsult, Throw(pipelonlinelonFailurelon: PipelonlinelonFailurelon))
              if failOpelonnPolicy(pipelonlinelonFailurelon.catelongory) =>
            Relonturn(relonsult)
          caselon (_, t: Throw[_]) => t.asInstancelonOf[Throw[Out]]
          caselon (relonsult, _) => Relonturn(relonsult)
        }.lowelonrFromTry

    /** Thelon complelontelon Arrow minus a Local span wrapping */
    val arrowWithTimingelonxeloncutorSidelonelonffeloncts = Arrow
      .timelon(arrow.andThelonn(transformRelonsultAndClassifyFailurelons))
      .applyelonffelonct(reloncordStatsAndUpdatelonQualityFactor)
      .andThelonn(applyFailOpelonnPolicyBaselondOnTransformelondRelonsult)

    /** Dont wrap with a span if welon arelon not tracing */
    tracingConfig match {
      caselon TracingConfig.WrapWithSpanAndTracingData =>
        wrapArrowWithLocalTracingSpan(arrowWithTimingelonxeloncutorSidelonelonffeloncts)
      caselon TracingConfig.NoTracing =>
        arrowWithTimingelonxeloncutorSidelonelonffeloncts
    }
  }

  /** Lelont-scopelons a [[TracelonId]] around a computation */
  privatelon[this] objelonct TracingLelonttelonr elonxtelonnds Lelonttelonr[TracelonId] {
    ovelonrridelon delonf lelont[S](tracelonId: TracelonId)(s: => S): S = Tracelon.lelontId(tracelonId)(s)
  }

  /**
   * Wraps thelon Arrow's elonxeloncution in a nelonw tracelon span as a child of thelon currelonnt parelonnt span
   *
   * @notelon Should __nelonvelonr__ belon callelond direlonctly!
   *
   * It's elonxpelonctelond that thelon containelond `arrow` will invokelon [[reloncordTracelonData]] elonxactly ONCelon
   * during it's elonxeloncution.
   *
   * @notelon this doelons not reloncord any data about thelon tracelon, it only selonts thelon [[Tracelon]] Span
   *       for thelon elonxeloncution of `arrow`
   */
  privatelon[selonrvicelon] delonf wrapArrowWithLocalTracingSpan[In, Out](
    arrow: Arrow[In, Out]
  ): Arrow[In, Out] =
    Arrow.ifelonlselon(
      _ => Tracelon.isActivelonlyTracing,
      Arrow.lelont(TracingLelonttelonr)(Tracelon.nelonxtId)(arrow),
      arrow
    )

  privatelon[this] objelonct Tracing {

    /**
     * Duplicatelon of [[com.twittelonr.finaglelon.tracing.Tracing]]'s `localSpans` which
     * uselons an un-scopelond [[StatsReloncelonivelonr]]
     *
     * Sincelon welon nelonelondelond to roll-our-own latelonncy melonasurelonmelonnt welon arelon unablelon to increlonmelonnt thelon
     * `local_spans` melontric automatically, this is important in thelon elonvelonnt a selonrvicelon is
     * unelonxpelonctelondly not reloncording spans or unelonxpelonctelondly reloncording too many, so welon manually
     * increlonmelonnt it
     */
    val localSpans: Countelonr = DelonfaultStatsReloncelonivelonr.countelonr("tracing", "local_spans")

    /** Local Componelonnt fielonld of a span in thelon UI */
    val localComponelonntTag = "lc"
    val sizelonTag = "product_mixelonr.relonsult.sizelon"
    val succelonssTag = "product_mixelonr.relonsult.succelonss"
    val succelonssValuelon = "succelonss"
    val cancelonllelondTag = "product_mixelonr.relonsult.cancelonllelond"
    val failurelonTag = "product_mixelonr.relonsult.failurelon"
  }

  /**
   * Reloncords melontadata onto thelon currelonnt [[Tracelon]] Span
   *
   * @notelon Should __nelonvelonr__ belon callelond direlonctly!
   *
   * This should belon callelond elonxactly ONCelon in thelon Arrow passelond into [[wrapArrowWithLocalTracingSpan]]
   * to reloncord data for thelon Span.
   */
  privatelon[selonrvicelon] delonf reloncordTracelonData[T](
    componelonntStack: ComponelonntIdelonntifielonrStack,
    componelonntIdelonntifielonr: ComponelonntIdelonntifielonr,
    relonsult: Try[T],
    latelonncy: Duration,
    sizelon: Option[Int] = Nonelon
  ): Unit = {
    if (Tracelon.isActivelonlyTracing) {
      Tracing.localSpans.incr()
      val tracelonId = Tracelon.id
      val elonndTimelon = Timelon.nowNanoPreloncision

      // Thelonselon annotations arelon nelonelondelond for thelon Zipkin UI to display thelon span propelonrly
      TracelonSelonrvicelonNamelon().forelonach(Tracelon.reloncordSelonrvicelonNamelon)
      Tracelon.reloncordRpc(componelonntIdelonntifielonr.snakelonCaselon) // namelon of thelon span in thelon UI
      Tracelon.reloncordBinary(
        Tracing.localComponelonntTag,
        componelonntStack.pelonelonk.toString + "/" + componelonntIdelonntifielonr.toString)
      Tracelon.reloncord(Reloncord(tracelonId, elonndTimelon - latelonncy, Annotation.Melonssagelon(LocalBelonginAnnotation)))
      Tracelon.reloncord(Reloncord(tracelonId, elonndTimelon, Annotation.Melonssagelon(LocalelonndAnnotation)))

      // product mixelonr speloncific zipkin data
      sizelon.forelonach(sizelon => Tracelon.reloncordBinary(Tracing.sizelonTag, sizelon))
      relonsult match {
        caselon Relonturn(_) =>
          Tracelon.reloncordBinary(Tracing.succelonssTag, Tracing.succelonssValuelon)
        caselon Throw(Cancelonllelondelonxcelonptionelonxtractor(elon)) =>
          Tracelon.reloncordBinary(Tracing.cancelonllelondTag, elon)
        caselon Throw(elon) =>
          Tracelon.reloncordBinary(Tracing.failurelonTag, elon)
      }
    }
  }

  /**
   * Relonturns a tuplelon of thelon stats scopelons for thelon currelonnt componelonnt and thelon relonlativelon scopelon for
   * thelon parelonnt componelonnt and thelon currelonnt componelonnt togelonthelonr
   *
   * This is uselonful whelonn reloncording stats for a componelonnt by itselonlf as welonll as stats for calls to that componelonnt from it's parelonnt.
   *
   * @elonxamplelon if thelon currelonnt componelonnt has a scopelon of "currelonntComponelonnt" and thelon parelonnt componelonnt has a scopelon of "parelonntComponelonnt"
   *          thelonn this will relonturn `(Selonq("currelonntComponelonnt"), Selonq("parelonntComponelonnt", "currelonntComponelonnt"))`
   */
  delonf buildScopelons(
    contelonxt: Contelonxt,
    currelonntComponelonntIdelonntifielonr: ComponelonntIdelonntifielonr
  ): elonxeloncutor.Scopelons = {
    val parelonntScopelons = contelonxt.componelonntStack.pelonelonk.toScopelons
    val componelonntScopelons = currelonntComponelonntIdelonntifielonr.toScopelons
    val relonlativelonScopelons = parelonntScopelons ++ componelonntScopelons
    elonxeloncutor.Scopelons(componelonntScopelons, relonlativelonScopelons)
  }

  /**
   * Makelons a [[BroadcastStatsReloncelonivelonr]] that will broadcast stats to thelon correlonct
   * currelonnt componelonnt's scopelon and to thelon scopelon relonlativelon to thelon parelonnt.
   */
  delonf broadcastStatsReloncelonivelonr(
    contelonxt: Contelonxt,
    currelonntComponelonntIdelonntifielonr: ComponelonntIdelonntifielonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): StatsReloncelonivelonr = {
    val elonxeloncutor.Scopelons(componelonntScopelons, relonlativelonScopelons) =
      elonxeloncutor.buildScopelons(contelonxt, currelonntComponelonntIdelonntifielonr)

    BroadcastStatsReloncelonivelonr(
      Selonq(statsReloncelonivelonr.scopelon(relonlativelonScopelons: _*), statsReloncelonivelonr.scopelon(componelonntScopelons: _*)))
  }

  /**
   * Relonturns a felonaturelon map containing all thelon [[com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon]]s
   * storelond as failurelons using thelon elonxcelonption providelond with as thelon relonason wrappelond in a PipelonlinelonFailurelon.
   * elon.g, for felonaturelons A & B that threlonw an elonxamplelonelonxcelonption b, this will relonturn:
   * { A -> Throw(PipelonlinelonFailurelon(...)), B -> Throw(PipelonlinelonFailurelon(...)) }
   */
  delonf felonaturelonMapWithFailurelonsForFelonaturelons(
    felonaturelons: Selont[Felonaturelon[_, _]],
    elonrror: Throwablelon,
    contelonxt: elonxeloncutor.Contelonxt
  ): FelonaturelonMap = {
    val buildelonr = FelonaturelonMapBuildelonr()
    felonaturelons.forelonach { felonaturelon =>
      val pipelonlinelonFailurelon = PipelonlinelonFailurelon(
        FelonaturelonHydrationFailelond,
        s"Felonaturelon hydration failelond for ${felonaturelon.toString}",
        Somelon(elonrror),
        Somelon(contelonxt.componelonntStack))
      buildelonr.addFailurelon(felonaturelon, pipelonlinelonFailurelon)
    }
    buildelonr.build()
  }

  /**
   * Validatelons and relonturns back thelon passelond felonaturelon map if it passelons validation. A felonaturelon map
   * is considelonrelond valid if it contains only thelon passelond `relongistelonrelondFelonaturelons` felonaturelons in it,
   * nothing elonlselon and nothing missing.
   */
  @throws(classOf[PipelonlinelonFailurelon])
  delonf validatelonFelonaturelonMap(
    relongistelonrelondFelonaturelons: Selont[Felonaturelon[_, _]],
    felonaturelonMap: FelonaturelonMap,
    contelonxt: elonxeloncutor.Contelonxt
  ): FelonaturelonMap = {
    val hydratelondFelonaturelons = felonaturelonMap.gelontFelonaturelons
    if (hydratelondFelonaturelons == relongistelonrelondFelonaturelons) {
      felonaturelonMap
    } elonlselon {
      val missingFelonaturelons = relongistelonrelondFelonaturelons -- hydratelondFelonaturelons
      val unrelongistelonrelondFelonaturelons = hydratelondFelonaturelons -- relongistelonrelondFelonaturelons
      throw PipelonlinelonFailurelon(
        MisconfigurelondFelonaturelonMapFailurelon,
        s"Unrelongistelonrelond felonaturelons $unrelongistelonrelondFelonaturelons and missing felonaturelons $missingFelonaturelons",
        Nonelon,
        Somelon(contelonxt.componelonntStack)
      )
    }
  }

  objelonct NotAMisconfigurelondFelonaturelonMapFailurelon {

    /**
     * Will relonturn any elonxcelonption that isn't a [[MisconfigurelondFelonaturelonMapFailurelon]] [[PipelonlinelonFailurelon]]
     * Allows for elonasy [[Arrow.handlelon]]ing all elonxcelonptions that arelonn't [[MisconfigurelondFelonaturelonMapFailurelon]]s
     */
    delonf unapply(elon: Throwablelon): Option[Throwablelon] = elon match {
      caselon pipelonlinelonFailurelon: PipelonlinelonFailurelon
          if pipelonlinelonFailurelon.catelongory == MisconfigurelondFelonaturelonMapFailurelon =>
        Nonelon
      caselon elon => Somelon(elon)
    }
  }

  /**
   * contains thelon scopelons for reloncording melontrics for thelon componelonnt by itselonlf and
   * thelon relonlativelon scopelon of that componelonnt within it's parelonnt componelonnt scopelon
   *
   * @selonelon [[elonxeloncutor.buildScopelons]]
   */
  caselon class Scopelons(componelonntScopelons: Selonq[String], relonlativelonScopelon: Selonq[String])

  /**
   * Wrap thelon [[Throwablelon]] in a [[UncatelongorizelondSelonrvelonrFailurelon]] [[PipelonlinelonFailurelon]] with thelon original
   * [[Throwablelon]] as thelon causelon, elonvelonn if it's alrelonady a [[PipelonlinelonFailurelon]].
   *
   * This elonnsurelons that any accelonss to thelon storelond felonaturelon will relonsult in a melonaningful [[UncatelongorizelondSelonrvelonrFailurelon]]
   * [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelonCatelongory]] in stats which is morelon uselonful
   * for customelonrs componelonnts which accelonss a failelond [[Felonaturelon]] than thelon original [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelonCatelongory]].
   */
  delonf uncatelongorizelondSelonrvelonrFailurelon(
    componelonntStack: ComponelonntIdelonntifielonrStack,
    throwablelon: Throwablelon
  ): PipelonlinelonFailurelon = {
    PipelonlinelonFailurelon(
      UncatelongorizelondSelonrvelonrFailurelon,
      relonason = "Unclassifielond Failurelon in Pipelonlinelon",
      Somelon(throwablelon),
      Somelon(componelonntStack)
    )
  }

  /**
   * [[PartialFunction]] that convelonrts any [[Throwablelon]] into a
   * [[PipelonlinelonFailurelon]] baselond on thelon providelond `failurelonClassifielonr`
   */
  delonf toPipelonlinelonFailurelonWithComponelonntIdelonntifielonrStack(
    contelonxt: Contelonxt,
    currelonntComponelonntIdelonntifielonr: ComponelonntIdelonntifielonr
  ): PipelonlinelonFailurelonClassifielonr = {
    // if givelonn a `currelonntComponelonntIdelonntifielonr` thelonn elonnsurelon welon correlonctly handlelon `BaselondOnParelonntComponelonnt` idelonntifielonr typelons
    val contelonxtWithCurrelonntComponelonntIdelonntifielonr =
      contelonxt.pushToComponelonntStack(currelonntComponelonntIdelonntifielonr)
    PipelonlinelonFailurelonClassifielonr(
      contelonxtWithCurrelonntComponelonntIdelonntifielonr.pipelonlinelonFailurelonClassifielonr
        .orelonlselon[Throwablelon, PipelonlinelonFailurelon] {
          caselon Cancelonllelondelonxcelonptionelonxtractor(throwablelon) => throw throwablelon
          caselon pipelonlinelonFailurelon: PipelonlinelonFailurelon => pipelonlinelonFailurelon
          caselon throwablelon =>
            uncatelongorizelondSelonrvelonrFailurelon(
              contelonxtWithCurrelonntComponelonntIdelonntifielonr.componelonntStack,
              throwablelon)
        }.andThelonn { pipelonlinelonFailurelon =>
          pipelonlinelonFailurelon.componelonntStack match {
            caselon _: Somelon[_] => pipelonlinelonFailurelon
            caselon Nonelon =>
              pipelonlinelonFailurelon.copy(componelonntStack =
                Somelon(contelonxtWithCurrelonntComponelonntIdelonntifielonr.componelonntStack))
          }
        }
    )
  }

  /**
   * information uselond by an [[elonxeloncutor]] that providelons contelonxt around elonxeloncution
   */
  caselon class Contelonxt(
    pipelonlinelonFailurelonClassifielonr: PipelonlinelonFailurelonClassifielonr,
    componelonntStack: ComponelonntIdelonntifielonrStack) {

    delonf pushToComponelonntStack(nelonwComponelonntIdelonntifielonr: ComponelonntIdelonntifielonr): Contelonxt =
      copy(componelonntStack = componelonntStack.push(nelonwComponelonntIdelonntifielonr))
  }
}
