packagelon com.twittelonr.homelon_mixelonr.product.following.param

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.timelonlinelon_modulelon.WhoToFollowModulelonDisplayTypelon
import com.twittelonr.timelonlinelons.configapi.DurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSelonnumParam
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.util.Duration

objelonct FollowingParam {
  val SupportelondClielonntFSNamelon = "following_supportelond_clielonnt"

  objelonct SelonrvelonrMaxRelonsultsParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "following_selonrvelonr_max_relonsults",
        delonfault = 100,
        min = 1,
        max = 500
      )

  objelonct elonnablelonWhoToFollowCandidatelonPipelonlinelonParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "following_elonnablelon_who_to_follow",
        delonfault = truelon
      )

  objelonct elonnablelonAdsCandidatelonPipelonlinelonParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "following_elonnablelon_ads",
        delonfault = truelon
      )

  objelonct elonnablelonFlipInjelonctionModulelonCandidatelonPipelonlinelonParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "following_elonnablelon_flip_inlinelon_injelonction_modulelon",
        delonfault = truelon
      )

  objelonct FlipInlinelonInjelonctionModulelonPosition
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "following_flip_inlinelon_injelonction_modulelon_position",
        delonfault = 0,
        min = 0,
        max = 1000
      )

  objelonct WhoToFollowPositionParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "following_who_to_follow_position",
        delonfault = 5,
        min = 0,
        max = 99
      )

  objelonct WhoToFollowMinInjelonctionIntelonrvalParam
      elonxtelonnds FSBoundelondParam[Duration](
        "following_who_to_follow_min_injelonction_intelonrval_in_minutelons",
        delonfault = 1800.minutelons,
        min = 0.minutelons,
        max = 6000.minutelons)
      with HasDurationConvelonrsion {
    ovelonrridelon val durationConvelonrsion: DurationConvelonrsion = DurationConvelonrsion.FromMinutelons
  }

  objelonct WhoToFollowDisplayTypelonIdParam
      elonxtelonnds FSelonnumParam[WhoToFollowModulelonDisplayTypelon.typelon](
        namelon = "following_elonnablelon_who_to_follow_display_typelon_id",
        delonfault = WhoToFollowModulelonDisplayTypelon.Velonrtical,
        elonnum = WhoToFollowModulelonDisplayTypelon
      )

  objelonct WhoToFollowDisplayLocationParam
      elonxtelonnds FSParam[String](
        namelon = "following_who_to_follow_display_location",
        delonfault = "timelonlinelon_relonvelonrselon_chron"
      )

  objelonct elonnablelonFastAds
      elonxtelonnds FSParam[Boolelonan](
        namelon = "following_elonnablelon_fast_ads",
        delonfault = truelon
      )
}
