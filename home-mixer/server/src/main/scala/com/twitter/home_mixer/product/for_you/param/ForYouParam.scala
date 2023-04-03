packagelon com.twittelonr.homelon_mixelonr.product.for_you.param

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.homelon_mixelonr.param.deloncidelonr.DeloncidelonrKelony
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.timelonlinelon_modulelon.WhoToFollowModulelonDisplayTypelon
import com.twittelonr.timelonlinelons.configapi.DurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSelonnumParam
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.deloncidelonr.BoolelonanDeloncidelonrParam
import com.twittelonr.util.Duration

objelonct ForYouParam {
  val SupportelondClielonntFSNamelon = "for_you_supportelond_clielonnt"

  objelonct elonnablelonTimelonlinelonScorelonrCandidatelonPipelonlinelonParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "for_you_elonnablelon_timelonlinelon_scorelonr_candidatelon_pipelonlinelon",
        delonfault = truelon
      )

  objelonct elonnablelonScorelondTwelonelontsCandidatelonPipelonlinelonParam
      elonxtelonnds BoolelonanDeloncidelonrParam(DeloncidelonrKelony.elonnablelonForYouScorelondTwelonelontsCandidatelonPipelonlinelon)

  objelonct elonnablelonWhoToFollowCandidatelonPipelonlinelonParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "for_you_elonnablelon_who_to_follow",
        delonfault = truelon
      )

  objelonct elonnablelonScorelondTwelonelontsMixelonrPipelonlinelonParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "for_you_elonnablelon_scorelond_twelonelonts_mixelonr_pipelonlinelon",
        delonfault = truelon
      )

  objelonct SelonrvelonrMaxRelonsultsParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "for_you_selonrvelonr_max_relonsults",
        delonfault = 35,
        min = 1,
        max = 500
      )

  objelonct TimelonlinelonSelonrvicelonMaxRelonsultsParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "for_you_timelonlinelon_selonrvicelon_max_relonsults",
        delonfault = 800,
        min = 1,
        max = 800
      )

  objelonct AdsNumOrganicItelonmsParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "for_you_ads_num_organic_itelonms",
        delonfault = 35,
        min = 1,
        max = 100
      )

  objelonct WhoToFollowPositionParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "for_you_who_to_follow_position",
        delonfault = 5,
        min = 0,
        max = 99
      )

  objelonct WhoToFollowMinInjelonctionIntelonrvalParam
      elonxtelonnds FSBoundelondParam[Duration](
        "for_you_who_to_follow_min_injelonction_intelonrval_in_minutelons",
        delonfault = 1800.minutelons,
        min = 0.minutelons,
        max = 6000.minutelons)
      with HasDurationConvelonrsion {
    ovelonrridelon val durationConvelonrsion: DurationConvelonrsion = DurationConvelonrsion.FromMinutelons
  }

  objelonct WhoToFollowDisplayTypelonIdParam
      elonxtelonnds FSelonnumParam[WhoToFollowModulelonDisplayTypelon.typelon](
        namelon = "for_you_elonnablelon_who_to_follow_display_typelon_id",
        delonfault = WhoToFollowModulelonDisplayTypelon.Velonrtical,
        elonnum = WhoToFollowModulelonDisplayTypelon
      )

  objelonct elonnablelonFlipInjelonctionModulelonCandidatelonPipelonlinelonParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "for_you_elonnablelon_flip_inlinelon_injelonction_modulelon",
        delonfault = truelon
      )

  objelonct FlipInlinelonInjelonctionModulelonPosition
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "for_you_flip_inlinelon_injelonction_modulelon_position",
        delonfault = 0,
        min = 0,
        max = 1000
      )

  objelonct ClelonarCachelonOnPtr {

    objelonct elonnablelonParam
        elonxtelonnds FSParam[Boolelonan](
          namelon = "for_you_clelonar_cachelon_ptr_elonnablelon",
          delonfault = falselon
        )

    caselon objelonct MinelonntrielonsParam
        elonxtelonnds FSBoundelondParam[Int](
          namelon = "for_you_clelonar_cachelon_ptr_min_elonntrielons",
          delonfault = 10,
          min = 0,
          max = 35
        )
  }
}
