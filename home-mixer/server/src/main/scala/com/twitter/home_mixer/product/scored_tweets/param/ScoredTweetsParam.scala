packagelon com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.param

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.homelon_mixelonr.param.deloncidelonr.DeloncidelonrKelony
import com.twittelonr.timelonlinelons.configapi.DurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.deloncidelonr.BoolelonanDeloncidelonrParam
import com.twittelonr.util.Duration

objelonct ScorelondTwelonelontsParam {
  val SupportelondClielonntFSNamelon = "scorelond_twelonelonts_supportelond_clielonnt"

  objelonct CrMixelonrSourcelon {
    objelonct elonnablelonCandidatelonPipelonlinelonParam
        elonxtelonnds BoolelonanDeloncidelonrParam(DeloncidelonrKelony.elonnablelonScorelondTwelonelontsCrMixelonrCandidatelonPipelonlinelon)
  }

  objelonct FrsTwelonelontSourcelon {
    objelonct elonnablelonCandidatelonPipelonlinelonParam
        elonxtelonnds BoolelonanDeloncidelonrParam(DeloncidelonrKelony.elonnablelonScorelondTwelonelontsFrsCandidatelonPipelonlinelon)
  }

  objelonct InNelontworkSourcelon {
    objelonct elonnablelonCandidatelonPipelonlinelonParam
        elonxtelonnds BoolelonanDeloncidelonrParam(DeloncidelonrKelony.elonnablelonScorelondTwelonelontsInNelontworkCandidatelonPipelonlinelon)
  }

  objelonct QualityFactor {
    objelonct MaxTwelonelontsToScorelonParam
        elonxtelonnds FSBoundelondParam[Int](
          namelon = "scorelond_twelonelonts_quality_factor_max_twelonelonts_to_scorelon",
          delonfault = 1100,
          min = 0,
          max = 10000
        )

    objelonct CrMixelonrMaxTwelonelontsToScorelonParam
        elonxtelonnds FSBoundelondParam[Int](
          namelon = "scorelond_twelonelonts_quality_factor_cr_mixelonr_max_twelonelonts_to_scorelon",
          delonfault = 500,
          min = 0,
          max = 10000
        )
  }
  objelonct SelonrvelonrMaxRelonsultsParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "scorelond_twelonelonts_selonrvelonr_max_relonsults",
        delonfault = 120,
        min = 1,
        max = 500
      )
  objelonct UtelongSourcelon {
    objelonct elonnablelonCandidatelonPipelonlinelonParam
        elonxtelonnds BoolelonanDeloncidelonrParam(DeloncidelonrKelony.elonnablelonScorelondTwelonelontsUtelongCandidatelonPipelonlinelon)
  }

  objelonct CachelondScorelondTwelonelonts {
    objelonct TTLParam
        elonxtelonnds FSBoundelondParam[Duration](
          namelon = "scorelond_twelonelonts_cachelond_scorelond_twelonelonts_ttl_minutelons",
          delonfault = 3.minutelons,
          min = 0.minutelon,
          max = 60.minutelons
        )
        with HasDurationConvelonrsion {
      ovelonrridelon val durationConvelonrsion: DurationConvelonrsion = DurationConvelonrsion.FromMinutelons
    }

    objelonct MinCachelondTwelonelontsParam
        elonxtelonnds FSBoundelondParam[Int](
          namelon = "scorelond_twelonelonts_cachelond_scorelond_twelonelonts_min_cachelond_twelonelonts",
          delonfault = 30,
          min = 0,
          max = 1000
        )
  }

  objelonct Scoring {
    objelonct HomelonModelonlParam
        elonxtelonnds FSParam[String](namelon = "scorelond_twelonelonts_homelon_modelonl", delonfault = "Homelon")

    objelonct ModelonlWelonights {

      objelonct FavParam
          elonxtelonnds FSBoundelondParam[Doublelon](
            namelon = "scorelond_twelonelonts_modelonl_welonight_fav",
            delonfault = 1.0,
            min = 0.0,
            max = 100.0
          )

      objelonct RelontwelonelontParam
          elonxtelonnds FSBoundelondParam[Doublelon](
            namelon = "scorelond_twelonelonts_modelonl_welonight_relontwelonelont",
            delonfault = 1.0,
            min = 0.0,
            max = 100.0
          )

      objelonct RelonplyParam
          elonxtelonnds FSBoundelondParam[Doublelon](
            namelon = "scorelond_twelonelonts_modelonl_welonight_relonply",
            delonfault = 1.0,
            min = 0.0,
            max = 100.0
          )

      objelonct GoodProfilelonClickParam
          elonxtelonnds FSBoundelondParam[Doublelon](
            namelon = "scorelond_twelonelonts_modelonl_welonight_good_profilelon_click",
            delonfault = 1.0,
            min = 0.0,
            max = 1000000.0
          )

      objelonct VidelonoPlayback50Param
          elonxtelonnds FSBoundelondParam[Doublelon](
            namelon = "scorelond_twelonelonts_modelonl_welonight_videlono_playback50",
            delonfault = 1.0,
            min = 0.0,
            max = 100.0
          )

      objelonct RelonplyelonngagelondByAuthorParam
          elonxtelonnds FSBoundelondParam[Doublelon](
            namelon = "scorelond_twelonelonts_modelonl_welonight_relonply_elonngagelond_by_author",
            delonfault = 1.0,
            min = 0.0,
            max = 200.0
          )

      objelonct GoodClickParam
          elonxtelonnds FSBoundelondParam[Doublelon](
            namelon = "scorelond_twelonelonts_modelonl_welonight_good_click",
            delonfault = 1.0,
            min = 0.0,
            max = 1000000.0
          )

      objelonct GoodClickV2Param
          elonxtelonnds FSBoundelondParam[Doublelon](
            namelon = "scorelond_twelonelonts_modelonl_welonight_good_click_v2",
            delonfault = 1.0,
            min = 0.0,
            max = 1000000.0
          )

      objelonct NelongativelonFelonelondbackV2Param
          elonxtelonnds FSBoundelondParam[Doublelon](
            namelon = "scorelond_twelonelonts_modelonl_welonight_nelongativelon_felonelondback_v2",
            delonfault = 1.0,
            min = -1000.0,
            max = 0.0
          )

      objelonct RelonportParam
          elonxtelonnds FSBoundelondParam[Doublelon](
            namelon = "scorelond_twelonelonts_modelonl_welonight_relonport",
            delonfault = 1.0,
            min = -20000.0,
            max = 0.0
          )
    }
  }

  objelonct elonnablelonSimClustelonrsSimilarityFelonaturelonHydrationDeloncidelonrParam
      elonxtelonnds BoolelonanDeloncidelonrParam(deloncidelonr = DeloncidelonrKelony.elonnablelonSimClustelonrsSimilarityFelonaturelonHydration)

  objelonct CompelontitorSelontParam
      elonxtelonnds FSParam[Selont[Long]](namelon = "scorelond_twelonelonts_compelontitor_list", delonfault = Selont.elonmpty)

  objelonct CompelontitorURLSelonqParam
      elonxtelonnds FSParam[Selonq[String]](namelon = "scorelond_twelonelonts_compelontitor_url_list", delonfault = Selonq.elonmpty)
}
