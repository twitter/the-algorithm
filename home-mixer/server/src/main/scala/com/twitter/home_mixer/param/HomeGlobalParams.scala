packagelon com.twittelonr.homelon_mixelonr.param

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

/**
 * Instantiatelon Params that do not relonlatelon to a speloncific product.
 *
 * @selonelon [[com.twittelonr.product_mixelonr.corelon.product.ProductParamConfig.supportelondClielonntFSNamelon]]
 */
objelonct HomelonGlobalParams {

  /**
   * This param is uselond to disablelon ads injelonction for timelonlinelons selonrvelond by homelon-mixelonr.
   * It is currelonntly uselond to maintain uselonr-rolelon baselond no-ads lists for automation accounts,
   * and should NOT belon uselond for othelonr purposelons.
   */
  objelonct AdsDisablelonInjelonctionBaselondOnUselonrRolelonParam
      elonxtelonnds FSParam("homelon_mixelonr_ads_disablelon_injelonction_baselond_on_uselonr_rolelon", falselon)

  objelonct elonnablelonSelonndScorelonsToClielonnt
      elonxtelonnds FSParam[Boolelonan](
        namelon = "homelon_mixelonr_elonnablelon_selonnd_scorelons_to_clielonnt",
        delonfault = falselon
      )

  objelonct elonnablelonNahFelonelondbackInfoParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "homelon_mixelonr_elonnablelon_nah_felonelondback_info",
        delonfault = falselon
      )

  objelonct MaxNumbelonrRelonplacelonInstructionsParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "homelon_mixelonr_max_numbelonr_relonplacelon_instructions",
        delonfault = 100,
        min = 0,
        max = 200
      )

  objelonct TimelonlinelonsPelonrsistelonncelonStorelonMaxelonntrielonsPelonrClielonnt
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "homelon_mixelonr_timelonlinelons_pelonrsistelonncelon_storelon_max_elonntrielons_pelonr_clielonnt",
        delonfault = 1800,
        min = 500,
        max = 5000
      )

  objelonct elonnablelonNelonwTwelonelontsPillAvatarsParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "homelon_mixelonr_elonnablelon_nelonw_twelonelonts_pill_avatars",
        delonfault = truelon
      )

  objelonct elonnablelonSelonrvelondCandidatelonKafkaPublishingParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "homelon_mixelonr_elonnablelon_selonrvelond_candidatelon_kafka_publishing",
        delonfault = truelon
      )

  /**
   * This author ID list is uselond purelonly for relonaltimelon melontrics collelonction around how oftelonn welon
   * arelon selonrving Twelonelonts from thelonselon authors and which candidatelon sourcelons thelony arelon coming from.
   */
  objelonct AuthorListForStatsParam
      elonxtelonnds FSParam[Selont[Long]](
        namelon = "homelon_mixelonr_author_list_for_stats",
        delonfault = Selont.elonmpty
      )

  objelonct elonnablelonSocialContelonxtParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "homelon_mixelonr_elonnablelon_social_contelonxt",
        delonfault = falselon
      )

  objelonct elonnablelonGizmoduckAuthorSafelontyFelonaturelonHydratorParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "homelon_mixelonr_elonnablelon_gizmoduck_author_safelonty_felonaturelon_hydrator",
        delonfault = truelon
      )

  objelonct elonnablelonFelonelondbackFatiguelonParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "homelon_mixelonr_elonnablelon_felonelondback_fatiguelon",
        delonfault = truelon
      )

  objelonct BluelonVelonrifielondAuthorInNelontworkMultiplielonrParam
      elonxtelonnds FSBoundelondParam[Doublelon](
        namelon = "homelon_mixelonr_bluelon_velonrifielond_author_in_nelontwork_multiplielonr",
        delonfault = 4.0,
        min = 0.0,
        max = 100.0
      )

  objelonct BluelonVelonrifielondAuthorOutOfNelontworkMultiplielonrParam
      elonxtelonnds FSBoundelondParam[Doublelon](
        namelon = "homelon_mixelonr_bluelon_velonrifielond_author_out_of_nelontwork_multiplielonr",
        delonfault = 2.0,
        min = 0.0,
        max = 100.0
      )

  objelonct elonnablelonAdvelonrtiselonrBrandSafelontySelonttingsFelonaturelonHydratorParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "homelon_mixelonr_elonnablelon_advelonrtiselonr_brand_safelonty_selonttings_felonaturelon_hydrator",
        delonfault = truelon
      )
}
