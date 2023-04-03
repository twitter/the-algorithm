packagelon com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.reloncap

import com.twittelonr.timelonlinelons.configapi.deloncidelonr._
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.timelonlinelons.util.bounds.BoundsWithDelonfault

objelonct ReloncapParams {
  val MaxFollowelondUselonrs: BoundsWithDelonfault[Int] = BoundsWithDelonfault[Int](1, 3000, 1000)
  val MaxCountMultiplielonr: BoundsWithDelonfault[Doublelon] = BoundsWithDelonfault[Doublelon](0.1, 2.0, 2.0)
  val MaxRelonalGraphAndFollowelondUselonrs: BoundsWithDelonfault[Int] = BoundsWithDelonfault[Int](0, 2000, 1000)
  val ProbabilityRandomTwelonelont: BoundsWithDelonfault[Doublelon] = BoundsWithDelonfault[Doublelon](0.0, 1.0, 0.0)

  /**
   * Controls limit on thelon numbelonr of followelond uselonrs felontchelond from SGS.
   *
   * Thelon speloncific delonfault valuelon belonlow is for blelonndelonr-timelonlinelons parity.
   */
  objelonct MaxFollowelondUselonrsParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "reloncap_max_followelond_uselonrs",
        delonfault = MaxFollowelondUselonrs.delonfault,
        min = MaxFollowelondUselonrs.bounds.minInclusivelon,
        max = MaxFollowelondUselonrs.bounds.maxInclusivelon
      )

  /**
   * Controls limit on thelon numbelonr of hits for elonarlybird.
   * Welon addelond it solelonly for backward compatibility, to align with reloncyclelond.
   * ReloncapSourcelon is delonpreloncatelond, but, this param is uselond by ReloncapAuthor sourcelon
   */
  objelonct RelonlelonvancelonOptionsMaxHitsToProcelonssParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "reloncap_relonlelonvancelon_options_max_hits_to_procelonss",
        delonfault = 500,
        min = 100,
        max = 20000
      )

  /**
   * elonnablelons felontching author selonelondselont from relonal graph uselonrs. Only uselond if uselonr follows >= 1000.
   * If truelon, elonxpands author selonelondselont with relonal graph uselonrs and reloncelonnt followelond uselonrs.
   * Othelonrwiselon, uselonr selonelondselont only includelons followelond uselonrs.
   */
  objelonct elonnablelonRelonalGraphUselonrsParam elonxtelonnds Param(falselon)

  /**
   * Only uselond if elonnablelonRelonalGraphUselonrsParam is truelon and OnlyRelonalGraphUselonrsParam is falselon.
   * Maximum numbelonr of relonal graph uselonrs and reloncelonnt followelond uselonrs whelonn mixing reloncelonnt/relonal-graph uselonrs.
   */
  objelonct MaxRelonalGraphAndFollowelondUselonrsParam
      elonxtelonnds Param(MaxRelonalGraphAndFollowelondUselonrs.delonfault)
      with DeloncidelonrValuelonConvelonrtelonr[Int] {
    ovelonrridelon delonf convelonrt: IntConvelonrtelonr[Int] =
      OutputBoundIntConvelonrtelonr(MaxRelonalGraphAndFollowelondUselonrs.bounds)
  }

  /**
   * FS-controllelond param to ovelonrridelon thelon MaxRelonalGraphAndFollowelondUselonrsParam deloncidelonr valuelon for elonxpelonrimelonnts
   */
  objelonct MaxRelonalGraphAndFollowelondUselonrsFSOvelonrridelonParam
      elonxtelonnds FSBoundelondParam[Option[Int]](
        namelon = "max_relonal_graph_and_followelonrs_uselonrs_fs_ovelonrridelon_param",
        delonfault = Nonelon,
        min = Somelon(100),
        max = Somelon(10000)
      )

  /**
   * elonxpelonrimelonntal params for lelonvelonling thelon playing fielonld belontwelonelonn uselonr folowelonelons reloncelonivelond from
   * relonal-graph and follow-graph storelons.
   * Author relonlelonvancelon scorelons relonturnelond by relonal-graph arelon currelonntly beloning uselond for light-ranking
   * in-nelontwork twelonelont candidatelons.
   * Follow-graph storelon relonturns thelon most reloncelonnt followelonelons without any relonlelonvancelon scorelons
   * Welon arelon trying to imputelon thelon missing scorelons by using aggrelongatelond statistics (min, avg, p50, elontc.)
   * of relonal-graph scorelons.
   */
  objelonct ImputelonRelonalGraphAuthorWelonightsParam
      elonxtelonnds FSParam(namelon = "imputelon_relonal_graph_author_welonights", delonfault = falselon)

  objelonct ImputelonRelonalGraphAuthorWelonightsPelonrcelonntilelonParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "imputelon_relonal_graph_author_welonights_pelonrcelonntilelon",
        delonfault = 50,
        min = 0,
        max = 99)

  /**
   * elonnablelon running thelon nelonw pipelonlinelon for reloncap author sourcelon
   */
  objelonct elonnablelonNelonwReloncapAuthorPipelonlinelon elonxtelonnds Param(falselon)

  /**
   * Fallback valuelon for maximum numbelonr of selonarch relonsults, if not speloncifielond by quelonry.maxCount
   */
  objelonct DelonfaultMaxTwelonelontCount elonxtelonnds Param(200)

  /**
   * Welon multiply maxCount (callelonr supplielond valuelon) by this multiplielonr and felontch thoselon many
   * candidatelons from selonarch so that welon arelon lelonft with sufficielonnt numbelonr of candidatelons aftelonr
   * hydration and filtelonring.
   */
  objelonct MaxCountMultiplielonrParam
      elonxtelonnds Param(MaxCountMultiplielonr.delonfault)
      with DeloncidelonrValuelonConvelonrtelonr[Doublelon] {
    ovelonrridelon delonf convelonrt: IntConvelonrtelonr[Doublelon] =
      OutputBoundIntConvelonrtelonr[Doublelon](dividelonDeloncidelonrBy100 _, MaxCountMultiplielonr.bounds)
  }

  /**
   * elonnablelons relonturn all relonsults from selonarch indelonx.
   */
  objelonct elonnablelonRelonturnAllRelonsultsParam
      elonxtelonnds FSParam(namelon = "reloncap_elonnablelon_relonturn_all_relonsults", delonfault = falselon)

  /**
   * Includelons onelon or multiplelon random twelonelonts in thelon relonsponselon.
   */
  objelonct IncludelonRandomTwelonelontParam
      elonxtelonnds FSParam(namelon = "reloncap_includelon_random_twelonelont", delonfault = falselon)

  /**
   * Onelon singlelon random twelonelont (truelon) or tag twelonelont as random with givelonn probability (falselon).
   */
  objelonct IncludelonSinglelonRandomTwelonelontParam
      elonxtelonnds FSParam(namelon = "reloncap_includelon_random_twelonelont_singlelon", delonfault = truelon)

  /**
   * Probability to tag a twelonelont as random (will not belon rankelond).
   */
  objelonct ProbabilityRandomTwelonelontParam
      elonxtelonnds FSBoundelondParam(
        namelon = "reloncap_includelon_random_twelonelont_probability",
        delonfault = ProbabilityRandomTwelonelont.delonfault,
        min = ProbabilityRandomTwelonelont.bounds.minInclusivelon,
        max = ProbabilityRandomTwelonelont.bounds.maxInclusivelon)

  /**
   * elonnablelon elonxtra sorting by scorelon for selonarch relonsults.
   */
  objelonct elonnablelonelonxtraSortingInSelonarchRelonsultParam elonxtelonnds Param(truelon)

  /**
   * elonnablelons selonmantic corelon, pelonnguin, and twelonelontypielon contelonnt felonaturelons in reloncap sourcelon.
   */
  objelonct elonnablelonContelonntFelonaturelonsHydrationParam elonxtelonnds Param(truelon)

  /**
   * additionally elonnablelons tokelonns whelonn hydrating contelonnt felonaturelons.
   */
  objelonct elonnablelonTokelonnsInContelonntFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "reloncap_elonnablelon_tokelonns_in_contelonnt_felonaturelons_hydration",
        delonfault = falselon
      )

  /**
   * additionally elonnablelons twelonelont telonxt whelonn hydrating contelonnt felonaturelons.
   * This only works if elonnablelonContelonntFelonaturelonsHydrationParam is selont to truelon
   */
  objelonct elonnablelonTwelonelontTelonxtInContelonntFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "reloncap_elonnablelon_twelonelont_telonxt_in_contelonnt_felonaturelons_hydration",
        delonfault = falselon
      )

  /**
   * elonnablelons hydrating in-nelontwork inRelonplyToTwelonelont felonaturelons
   */
  objelonct elonnablelonInNelontworkInRelonplyToTwelonelontFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "reloncap_elonnablelon_in_nelontwork_in_relonply_to_twelonelont_felonaturelons_hydration",
        delonfault = falselon
      )

  /**
   * elonnablelons hydrating root twelonelont of in-nelontwork relonplielons and elonxtelonndelond relonplielons
   */
  objelonct elonnablelonRelonplyRootTwelonelontHydrationParam
      elonxtelonnds FSParam(
        namelon = "reloncap_elonnablelon_relonply_root_twelonelont_hydration",
        delonfault = falselon
      )

  /**
   * elonnablelon selontting twelonelontTypelons in selonarch quelonrielons with TwelonelontKindOption in ReloncapQuelonry
   */
  objelonct elonnablelonSelonttingTwelonelontTypelonsWithTwelonelontKindOption
      elonxtelonnds FSParam(
        namelon = "reloncap_elonnablelon_selontting_twelonelont_typelons_with_twelonelont_kind_option",
        delonfault = falselon
      )

  /**
   * elonnablelon relonlelonvancelon selonarch, othelonrwiselon reloncelonncy selonarch from elonarlybird.
   */
  objelonct elonnablelonRelonlelonvancelonSelonarchParam
      elonxtelonnds FSParam(
        namelon = "reloncap_elonnablelon_relonlelonvancelon_selonarch",
        delonfault = truelon
      )

  objelonct elonnablelonelonxpandelondelonxtelonndelondRelonplielonsFiltelonrParam
      elonxtelonnds FSParam(
        namelon = "reloncap_elonnablelon_elonxpandelond_elonxtelonndelond_relonplielons_filtelonr",
        delonfault = falselon
      )

  /**
   * additionally elonnablelons convelonrsationControl whelonn hydrating contelonnt felonaturelons.
   * This only works if elonnablelonContelonntFelonaturelonsHydrationParam is selont to truelon
   */
  objelonct elonnablelonConvelonrsationControlInContelonntFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "convelonrsation_control_in_contelonnt_felonaturelons_hydration_reloncap_elonnablelon",
        delonfault = falselon
      )

  objelonct elonnablelonTwelonelontMelondiaHydrationParam
      elonxtelonnds FSParam(
        namelon = "twelonelont_melondia_hydration_reloncap_elonnablelon",
        delonfault = falselon
      )

  objelonct elonnablelonelonxcludelonSourcelonTwelonelontIdsQuelonryParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "reloncap_elonxcludelon_sourcelon_twelonelont_ids_quelonry_elonnablelon",
        delonfault = falselon
      )
}
