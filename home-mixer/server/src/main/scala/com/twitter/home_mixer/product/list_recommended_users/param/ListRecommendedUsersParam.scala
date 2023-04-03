packagelon com.twittelonr.homelon_mixelonr.product.list_reloncommelonndelond_uselonrs.param

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam

objelonct ListReloncommelonndelondUselonrsParam {
  val SupportelondClielonntFSNamelon = "list_reloncommelonndelond_uselonrs_supportelond_clielonnt"

  objelonct SelonrvelonrMaxRelonsultsParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "list_reloncommelonndelond_uselonrs_selonrvelonr_max_relonsults",
        delonfault = 10,
        min = 1,
        max = 500
      )

  objelonct elonxcludelondIdsMaxLelonngthParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "list_reloncommelonndelond_uselonrs_elonxcludelond_ids_max_lelonngth",
        delonfault = 2000,
        min = 0,
        max = 5000
      )
}
