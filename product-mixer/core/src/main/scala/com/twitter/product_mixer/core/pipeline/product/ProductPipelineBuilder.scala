packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.product

import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finaglelon.tracing.Tracelon
import com.twittelonr.finaglelon.transport.Transport
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.accelonss_policy.AccelonssPolicy
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.gatelon.DelonnyLoggelondOutUselonrsGatelon
import com.twittelonr.product_mixelonr.corelon.gatelon.ParamGatelon
import com.twittelonr.product_mixelonr.corelon.gatelon.ParamGatelon.elonnablelondGatelonSuffix
import com.twittelonr.product_mixelonr.corelon.gatelon.ParamGatelon.SupportelondClielonntGatelonSuffix
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonrStack
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ProductPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.PipelonlinelonStelonpIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.Relonquelonst
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.InvalidStelonpStatelonelonxcelonption
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.Pipelonlinelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonBuildelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.mixelonr.MixelonrPipelonlinelonBuildelonrFactory
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.mixelonr.MixelonrPipelonlinelonConfig
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.mixelonr.MixelonrPipelonlinelonRelonsult
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelonClassifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.ProductDisablelond
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.reloncommelonndation.ReloncommelonndationPipelonlinelonBuildelonrFactory
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.reloncommelonndation.ReloncommelonndationPipelonlinelonConfig
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.reloncommelonndation.ReloncommelonndationPipelonlinelonRelonsult
import com.twittelonr.product_mixelonr.corelon.quality_factor.HasQualityFactorStatus
import com.twittelonr.product_mixelonr.corelon.quality_factor.QualityFactorObselonrvelonr
import com.twittelonr.product_mixelonr.corelon.quality_factor.QualityFactorStatus
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.gatelon_elonxeloncutor.Gatelonelonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.gatelon_elonxeloncutor.GatelonelonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.gatelon_elonxeloncutor.StoppelondGatelonelonxcelonption
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.pipelonlinelon_elonxeloncution_loggelonr.PipelonlinelonelonxeloncutionLoggelonr
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.pipelonlinelon_elonxeloncutor.Pipelonlinelonelonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.pipelonlinelon_elonxeloncutor.PipelonlinelonelonxeloncutorRelonquelonst
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.pipelonlinelon_elonxeloncutor.PipelonlinelonelonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.pipelonlinelon_selonlelonctor_elonxeloncutor.PipelonlinelonSelonlelonctorelonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.pipelonlinelon_selonlelonctor_elonxeloncutor.PipelonlinelonSelonlelonctorelonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.quality_factor_elonxeloncutor.QualityFactorelonxeloncutorRelonsult
import com.twittelonr.stitch.Arrow
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonrRelonquelonstContelonxt
import com.twittelonr.stringcelonntelonr.clielonnt.stitch.StringCelonntelonrRelonquelonstContelonxtLelonttelonr
import com.twittelonr.timelonlinelons.configapi.Params
import com.twittelonr.util.logging.Logging
import org.slf4j.MDC

class ProductPipelonlinelonBuildelonr[TRelonquelonst <: Relonquelonst, Quelonry <: PipelonlinelonQuelonry, Relonsponselon](
  gatelonelonxeloncutor: Gatelonelonxeloncutor,
  pipelonlinelonSelonlelonctorelonxeloncutor: PipelonlinelonSelonlelonctorelonxeloncutor,
  pipelonlinelonelonxeloncutor: Pipelonlinelonelonxeloncutor,
  mixelonrPipelonlinelonBuildelonrFactory: MixelonrPipelonlinelonBuildelonrFactory,
  reloncommelonndationPipelonlinelonBuildelonrFactory: ReloncommelonndationPipelonlinelonBuildelonrFactory,
  ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr,
  pipelonlinelonelonxeloncutionLoggelonr: PipelonlinelonelonxeloncutionLoggelonr)
    elonxtelonnds PipelonlinelonBuildelonr[ProductPipelonlinelonRelonquelonst[TRelonquelonst]]
    with Logging { buildelonr =>

  ovelonrridelon typelon UndelonrlyingRelonsultTypelon = Relonsponselon
  ovelonrridelon typelon PipelonlinelonRelonsultTypelon = ProductPipelonlinelonRelonsult[Relonsponselon]

  /**
   * Quelonry Transformelonr Stelonp is implelonmelonntelond inlinelon instelonad of using an elonxeloncutor.
   *
   * It's a simplelon, synchronous stelonp that elonxeloncutelons thelon quelonry transformelonr.
   *
   * Sincelon thelon output of thelon transformelonr is uselond in multiplelon othelonr stelonps (Gatelon, Pipelonlinelon elonxeloncution),
   * welon'velon promotelond thelon transformelonr to a stelonp so that it's outputs can belon relonuselond elonasily.
   */
  delonf pipelonlinelonQuelonryTransformelonrStelonp(
    quelonryTransformelonr: (TRelonquelonst, Params) => Quelonry,
    contelonxt: elonxeloncutor.Contelonxt
  ): Stelonp[ProductPipelonlinelonRelonquelonst[TRelonquelonst], Quelonry] =
    nelonw Stelonp[ProductPipelonlinelonRelonquelonst[TRelonquelonst], Quelonry] {

      ovelonrridelon delonf idelonntifielonr: PipelonlinelonStelonpIdelonntifielonr =
        ProductPipelonlinelonConfig.pipelonlinelonQuelonryTransformelonrStelonp

      ovelonrridelon delonf elonxeloncutorArrow: Arrow[ProductPipelonlinelonRelonquelonst[TRelonquelonst], Quelonry] = {
        wrapWithelonrrorHandling(contelonxt, idelonntifielonr)(
          Arrow.map[ProductPipelonlinelonRelonquelonst[TRelonquelonst], Quelonry] {
            caselon ProductPipelonlinelonRelonquelonst(relonquelonst, params) => quelonryTransformelonr(relonquelonst, params)
          }
        )
      }

      ovelonrridelon delonf inputAdaptor(
        quelonry: ProductPipelonlinelonRelonquelonst[TRelonquelonst],
        prelonviousRelonsult: ProductPipelonlinelonRelonsult[Relonsponselon]
      ): ProductPipelonlinelonRelonquelonst[TRelonquelonst] = quelonry

      ovelonrridelon delonf relonsultUpdatelonr(
        prelonviousPipelonlinelonRelonsult: ProductPipelonlinelonRelonsult[Relonsponselon],
        elonxeloncutorRelonsult: Quelonry
      ): ProductPipelonlinelonRelonsult[Relonsponselon] =
        prelonviousPipelonlinelonRelonsult.copy(transformelondQuelonry = Somelon(elonxeloncutorRelonsult))
    }

  delonf qualityFactorStelonp(
    qualityFactorStatus: QualityFactorStatus
  ): Stelonp[Quelonry, QualityFactorelonxeloncutorRelonsult] = {
    nelonw Stelonp[Quelonry, QualityFactorelonxeloncutorRelonsult] {
      ovelonrridelon delonf idelonntifielonr: PipelonlinelonStelonpIdelonntifielonr = ProductPipelonlinelonConfig.qualityFactorStelonp

      ovelonrridelon delonf elonxeloncutorArrow: Arrow[Quelonry, QualityFactorelonxeloncutorRelonsult] =
        Arrow
          .map[Quelonry, QualityFactorelonxeloncutorRelonsult] { _ =>
            QualityFactorelonxeloncutorRelonsult(
              pipelonlinelonQualityFactors =
                qualityFactorStatus.qualityFactorByPipelonlinelon.mapValuelons(_.currelonntValuelon)
            )
          }

      ovelonrridelon delonf inputAdaptor(
        quelonry: ProductPipelonlinelonRelonquelonst[TRelonquelonst],
        prelonviousRelonsult: ProductPipelonlinelonRelonsult[Relonsponselon]
      ): Quelonry = prelonviousRelonsult.transformelondQuelonry
        .gelontOrelonlselon {
          throw InvalidStelonpStatelonelonxcelonption(idelonntifielonr, "TransformelondQuelonry")
        }.asInstancelonOf[Quelonry]

      ovelonrridelon delonf relonsultUpdatelonr(
        prelonviousPipelonlinelonRelonsult: ProductPipelonlinelonRelonsult[Relonsponselon],
        elonxeloncutorRelonsult: QualityFactorelonxeloncutorRelonsult
      ): ProductPipelonlinelonRelonsult[Relonsponselon] = {
        prelonviousPipelonlinelonRelonsult.copy(
          transformelondQuelonry = prelonviousPipelonlinelonRelonsult.transformelondQuelonry.map {
            caselon quelonryWithQualityFactor: HasQualityFactorStatus =>
              quelonryWithQualityFactor
                .withQualityFactorStatus(qualityFactorStatus).asInstancelonOf[Quelonry]
            caselon quelonry =>
              quelonry
          },
          qualityFactorRelonsult = Somelon(elonxeloncutorRelonsult)
        )
      }
    }
  }

  delonf gatelonsStelonp(
    gatelons: Selonq[Gatelon[Quelonry]],
    contelonxt: elonxeloncutor.Contelonxt
  ): Stelonp[Quelonry, GatelonelonxeloncutorRelonsult] = nelonw Stelonp[Quelonry, GatelonelonxeloncutorRelonsult] {
    ovelonrridelon delonf idelonntifielonr: PipelonlinelonStelonpIdelonntifielonr = ProductPipelonlinelonConfig.gatelonsStelonp

    ovelonrridelon delonf elonxeloncutorArrow: Arrow[Quelonry, GatelonelonxeloncutorRelonsult] = {
      gatelonelonxeloncutor.arrow(gatelons, contelonxt)
    }

    ovelonrridelon delonf inputAdaptor(
      quelonry: ProductPipelonlinelonRelonquelonst[TRelonquelonst],
      prelonviousRelonsult: ProductPipelonlinelonRelonsult[Relonsponselon]
    ): Quelonry = prelonviousRelonsult.transformelondQuelonry
      .gelontOrelonlselon {
        throw InvalidStelonpStatelonelonxcelonption(idelonntifielonr, "TransformelondQuelonry")
      }.asInstancelonOf[Quelonry]

    ovelonrridelon delonf relonsultUpdatelonr(
      prelonviousPipelonlinelonRelonsult: ProductPipelonlinelonRelonsult[Relonsponselon],
      elonxeloncutorRelonsult: GatelonelonxeloncutorRelonsult
    ): ProductPipelonlinelonRelonsult[Relonsponselon] =
      prelonviousPipelonlinelonRelonsult.copy(gatelonRelonsult = Somelon(elonxeloncutorRelonsult))
  }

  delonf pipelonlinelonSelonlelonctorStelonp(
    pipelonlinelonByIdelonntifelonr: Map[ComponelonntIdelonntifielonr, Pipelonlinelon[Quelonry, Relonsponselon]],
    pipelonlinelonSelonlelonctor: Quelonry => ComponelonntIdelonntifielonr,
    contelonxt: elonxeloncutor.Contelonxt
  ): Stelonp[Quelonry, PipelonlinelonSelonlelonctorelonxeloncutorRelonsult] =
    nelonw Stelonp[Quelonry, PipelonlinelonSelonlelonctorelonxeloncutorRelonsult] {
      ovelonrridelon delonf idelonntifielonr: PipelonlinelonStelonpIdelonntifielonr = ProductPipelonlinelonConfig.pipelonlinelonSelonlelonctorStelonp

      ovelonrridelon delonf elonxeloncutorArrow: Arrow[
        Quelonry,
        PipelonlinelonSelonlelonctorelonxeloncutorRelonsult
      ] = pipelonlinelonSelonlelonctorelonxeloncutor.arrow(pipelonlinelonByIdelonntifelonr, pipelonlinelonSelonlelonctor, contelonxt)

      ovelonrridelon delonf inputAdaptor(
        quelonry: ProductPipelonlinelonRelonquelonst[TRelonquelonst],
        prelonviousRelonsult: ProductPipelonlinelonRelonsult[Relonsponselon]
      ): Quelonry =
        prelonviousRelonsult.transformelondQuelonry
          .gelontOrelonlselon(throw InvalidStelonpStatelonelonxcelonption(idelonntifielonr, "TransformelondQuelonry")).asInstancelonOf[
            Quelonry]

      ovelonrridelon delonf relonsultUpdatelonr(
        prelonviousPipelonlinelonRelonsult: ProductPipelonlinelonRelonsult[Relonsponselon],
        elonxeloncutorRelonsult: PipelonlinelonSelonlelonctorelonxeloncutorRelonsult
      ): ProductPipelonlinelonRelonsult[Relonsponselon] =
        prelonviousPipelonlinelonRelonsult.copy(pipelonlinelonSelonlelonctorRelonsult = Somelon(elonxeloncutorRelonsult))
    }

  delonf pipelonlinelonelonxeloncutionStelonp(
    pipelonlinelonByIdelonntifielonr: Map[ComponelonntIdelonntifielonr, Pipelonlinelon[Quelonry, Relonsponselon]],
    qualityFactorObselonrvelonrByPipelonlinelon: Map[ComponelonntIdelonntifielonr, QualityFactorObselonrvelonr],
    contelonxt: elonxeloncutor.Contelonxt
  ): Stelonp[PipelonlinelonelonxeloncutorRelonquelonst[Quelonry], PipelonlinelonelonxeloncutorRelonsult[Relonsponselon]] =
    nelonw Stelonp[PipelonlinelonelonxeloncutorRelonquelonst[Quelonry], PipelonlinelonelonxeloncutorRelonsult[Relonsponselon]] {
      ovelonrridelon delonf idelonntifielonr: PipelonlinelonStelonpIdelonntifielonr = ProductPipelonlinelonConfig.pipelonlinelonelonxeloncutionStelonp

      ovelonrridelon delonf elonxeloncutorArrow: Arrow[
        PipelonlinelonelonxeloncutorRelonquelonst[Quelonry],
        PipelonlinelonelonxeloncutorRelonsult[Relonsponselon]
      ] = {
        pipelonlinelonelonxeloncutor.arrow(pipelonlinelonByIdelonntifielonr, qualityFactorObselonrvelonrByPipelonlinelon, contelonxt)
      }

      ovelonrridelon delonf inputAdaptor(
        relonquelonst: ProductPipelonlinelonRelonquelonst[TRelonquelonst],
        prelonviousRelonsult: ProductPipelonlinelonRelonsult[Relonsponselon]
      ): PipelonlinelonelonxeloncutorRelonquelonst[Quelonry] = {
        val quelonry = prelonviousRelonsult.transformelondQuelonry
          .gelontOrelonlselon {
            throw InvalidStelonpStatelonelonxcelonption(idelonntifielonr, "TransformelondQuelonry")
          }.asInstancelonOf[Quelonry]

        val pipelonlinelonIdelonntifielonr = prelonviousRelonsult.pipelonlinelonSelonlelonctorRelonsult
          .map(_.pipelonlinelonIdelonntifielonr).gelontOrelonlselon {
            throw InvalidStelonpStatelonelonxcelonption(idelonntifielonr, "PipelonlinelonSelonlelonctorRelonsult")
          }

        PipelonlinelonelonxeloncutorRelonquelonst(quelonry, pipelonlinelonIdelonntifielonr)
      }

      ovelonrridelon delonf relonsultUpdatelonr(
        prelonviousPipelonlinelonRelonsult: ProductPipelonlinelonRelonsult[Relonsponselon],
        elonxeloncutorRelonsult: PipelonlinelonelonxeloncutorRelonsult[Relonsponselon]
      ): ProductPipelonlinelonRelonsult[Relonsponselon] = {

        val mixelonrPipelonlinelonRelonsult = elonxeloncutorRelonsult.pipelonlinelonRelonsult match {
          caselon mixelonrPipelonlinelonRelonsult: MixelonrPipelonlinelonRelonsult[Relonsponselon] @unchelonckelond =>
            Somelon(mixelonrPipelonlinelonRelonsult)
          caselon _ =>
            Nonelon
        }

        val reloncommelonndationPipelonlinelonRelonsult = elonxeloncutorRelonsult.pipelonlinelonRelonsult match {
          caselon reloncommelonndationPipelonlinelonRelonsult: ReloncommelonndationPipelonlinelonRelonsult[
                _,
                Relonsponselon
              ] @unchelonckelond =>
            Somelon(reloncommelonndationPipelonlinelonRelonsult)
          caselon _ =>
            Nonelon
        }

        prelonviousPipelonlinelonRelonsult.copy(
          mixelonrPipelonlinelonRelonsult = mixelonrPipelonlinelonRelonsult,
          reloncommelonndationPipelonlinelonRelonsult = reloncommelonndationPipelonlinelonRelonsult,
          tracelonId = Tracelon.idOption.map(_.tracelonId.toString()),
          relonsult = elonxeloncutorRelonsult.pipelonlinelonRelonsult.relonsult
        )
      }
    }

  delonf build(
    parelonntComponelonntIdelonntifielonrStack: ComponelonntIdelonntifielonrStack,
    config: ProductPipelonlinelonConfig[TRelonquelonst, Quelonry, Relonsponselon]
  ): ProductPipelonlinelon[TRelonquelonst, Relonsponselon] = {

    val pipelonlinelonIdelonntifielonr = config.idelonntifielonr

    val contelonxt = elonxeloncutor.Contelonxt(
      PipelonlinelonFailurelonClassifielonr(
        config.failurelonClassifielonr.orelonlselon(StoppelondGatelonelonxcelonption.classifielonr(ProductDisablelond))),
      parelonntComponelonntIdelonntifielonrStack.push(pipelonlinelonIdelonntifielonr)
    )

    val delonnyLoggelondOutUselonrsGatelon = if (config.delonnyLoggelondOutUselonrs) {
      Somelon(DelonnyLoggelondOutUselonrsGatelon(pipelonlinelonIdelonntifielonr))
    } elonlselon {
      Nonelon
    }
    val elonnablelondGatelon: ParamGatelon =
      ParamGatelon(pipelonlinelonIdelonntifielonr + elonnablelondGatelonSuffix, config.paramConfig.elonnablelondDeloncidelonrParam)
    val supportelondClielonntGatelon =
      ParamGatelon(
        pipelonlinelonIdelonntifielonr + SupportelondClielonntGatelonSuffix,
        config.paramConfig.SupportelondClielonntParam)

    /**
     * elonvaluatelon elonnablelond deloncidelonr gatelon first sincelon if it's off, thelonrelon is no relonason to procelonelond
     * Nelonxt elonvaluatelon supportelond clielonnt felonaturelon switch gatelon, followelond by customelonr configurelond gatelons
     */
    val allGatelons =
      delonnyLoggelondOutUselonrsGatelon.toSelonq ++: elonnablelondGatelon +: supportelondClielonntGatelon +: config.gatelons

    val childPipelonlinelons: Selonq[Pipelonlinelon[Quelonry, Relonsponselon]] =
      config.pipelonlinelons.map {
        caselon mixelonrConfig: MixelonrPipelonlinelonConfig[Quelonry, _, Relonsponselon] =>
          mixelonrConfig.build(contelonxt.componelonntStack, mixelonrPipelonlinelonBuildelonrFactory)
        caselon reloncommelonndationConfig: ReloncommelonndationPipelonlinelonConfig[Quelonry, _, _, Relonsponselon] =>
          reloncommelonndationConfig.build(contelonxt.componelonntStack, reloncommelonndationPipelonlinelonBuildelonrFactory)
        caselon othelonr =>
          throw nelonw IllelongalArgumelonntelonxcelonption(
            s"Product Pipelonlinelons only support Mixelonr and Reloncommelonndation pipelonlinelons, not $othelonr")
      }

    val pipelonlinelonByIdelonntifielonr: Map[ComponelonntIdelonntifielonr, Pipelonlinelon[Quelonry, Relonsponselon]] =
      childPipelonlinelons.map { pipelonlinelon =>
        (pipelonlinelon.idelonntifielonr, pipelonlinelon)
      }.toMap

    val qualityFactorStatus: QualityFactorStatus =
      QualityFactorStatus.build(config.qualityFactorConfigs)

    val qualityFactorObselonrvelonrByPipelonlinelon = qualityFactorStatus.qualityFactorByPipelonlinelon.mapValuelons {
      qualityFactor =>
        qualityFactor.buildObselonrvelonr()
    }

    buildGaugelonsForQualityFactor(pipelonlinelonIdelonntifielonr, qualityFactorStatus, statsReloncelonivelonr)

    /**
     * Initializelon MDC with accelonss logging with elonvelonrything welon havelon at relonquelonst timelon. Welon can put
     * morelon stuff into MDC latelonr down thelon pipelonlinelon, but at risk of elonxcelonptions/elonrrors prelonvelonnting
     * thelonm from beloning addelond
     */
    val mdcInitArrow =
      Arrow.map[ProductPipelonlinelonRelonquelonst[TRelonquelonst], ProductPipelonlinelonRelonquelonst[TRelonquelonst]] { relonquelonst =>
        val selonrvicelonIdelonntifielonr = SelonrvicelonIdelonntifielonr.fromCelonrtificatelon(Transport.pelonelonrCelonrtificatelon)
        MDC.put("product", config.product.idelonntifielonr.namelon)
        MDC.put("selonrvicelonIdelonntifielonr", SelonrvicelonIdelonntifielonr.asString(selonrvicelonIdelonntifielonr))
        relonquelonst
      }

    val builtStelonps = Selonq(
      pipelonlinelonQuelonryTransformelonrStelonp(config.pipelonlinelonQuelonryTransformelonr, contelonxt),
      qualityFactorStelonp(qualityFactorStatus),
      gatelonsStelonp(allGatelons, contelonxt),
      pipelonlinelonSelonlelonctorStelonp(pipelonlinelonByIdelonntifielonr, config.pipelonlinelonSelonlelonctor, contelonxt),
      pipelonlinelonelonxeloncutionStelonp(pipelonlinelonByIdelonntifielonr, qualityFactorObselonrvelonrByPipelonlinelon, contelonxt)
    )

    val undelonrlying: Arrow[ProductPipelonlinelonRelonquelonst[TRelonquelonst], ProductPipelonlinelonRelonsult[Relonsponselon]] =
      buildCombinelondArrowFromStelonps(
        stelonps = builtStelonps,
        contelonxt = contelonxt,
        initialelonmptyRelonsult = ProductPipelonlinelonRelonsult.elonmpty,
        stelonpsInOrdelonrFromConfig = ProductPipelonlinelonConfig.stelonpsInOrdelonr
      )

    /**
     * Unlikelon othelonr componelonnts and pipelonlinelons, [[ProductPipelonlinelon]] must belon obselonrvelond in thelon
     * [[ProductPipelonlinelonBuildelonr]] direlonctly beloncauselon thelon relonsulting [[ProductPipelonlinelon.arrow]]
     * is run direlonctly without an elonxeloncutor so must contain all stats.
     */
    val obselonrvelond =
      wrapProductPipelonlinelonWithelonxeloncutorBookkelonelonping[
        ProductPipelonlinelonRelonquelonst[TRelonquelonst],
        ProductPipelonlinelonRelonsult[Relonsponselon]
      ](contelonxt, pipelonlinelonIdelonntifielonr)(undelonrlying)

    val finalArrow: Arrow[ProductPipelonlinelonRelonquelonst[TRelonquelonst], ProductPipelonlinelonRelonsult[Relonsponselon]] =
      Arrow
        .lelontWithArg[
          ProductPipelonlinelonRelonquelonst[TRelonquelonst],
          ProductPipelonlinelonRelonsult[Relonsponselon],
          StringCelonntelonrRelonquelonstContelonxt](StringCelonntelonrRelonquelonstContelonxtLelonttelonr)(relonquelonst =>
          StringCelonntelonrRelonquelonstContelonxt(
            relonquelonst.relonquelonst.clielonntContelonxt.languagelonCodelon,
            relonquelonst.relonquelonst.clielonntContelonxt.countryCodelon
          ))(
          mdcInitArrow
            .andThelonn(obselonrvelond)
            .onSuccelonss(relonsult => relonsult.transformelondQuelonry.map(pipelonlinelonelonxeloncutionLoggelonr(_, relonsult))))

    val configFromBuildelonr = config
    nelonw ProductPipelonlinelon[TRelonquelonst, Relonsponselon] {
      ovelonrridelon privatelon[corelon] val config: ProductPipelonlinelonConfig[TRelonquelonst, _, Relonsponselon] =
        configFromBuildelonr
      ovelonrridelon val arrow: Arrow[ProductPipelonlinelonRelonquelonst[TRelonquelonst], ProductPipelonlinelonRelonsult[Relonsponselon]] =
        finalArrow
      ovelonrridelon val idelonntifielonr: ProductPipelonlinelonIdelonntifielonr = pipelonlinelonIdelonntifielonr
      ovelonrridelon val alelonrts: Selonq[Alelonrt] = config.alelonrts
      ovelonrridelon val delonbugAccelonssPolicielons: Selont[AccelonssPolicy] = config.delonbugAccelonssPolicielons
      ovelonrridelon val childrelonn: Selonq[Componelonnt] = allGatelons ++ childPipelonlinelons
    }
  }
}
