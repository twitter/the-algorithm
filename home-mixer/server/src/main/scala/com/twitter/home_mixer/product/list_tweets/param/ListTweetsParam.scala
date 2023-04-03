packagelon com.twittelonr.homelon_mixelonr.product.list_twelonelonts.param

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct ListTwelonelontsParam {
  val SupportelondClielonntFSNamelon = "list_twelonelonts_supportelond_clielonnt"

  objelonct elonnablelonAdsCandidatelonPipelonlinelonParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "list_twelonelonts_elonnablelon_ads",
        delonfault = falselon
      )

  objelonct SelonrvelonrMaxRelonsultsParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "list_twelonelonts_selonrvelonr_max_relonsults",
        delonfault = 100,
        min = 1,
        max = 500
      )
}
