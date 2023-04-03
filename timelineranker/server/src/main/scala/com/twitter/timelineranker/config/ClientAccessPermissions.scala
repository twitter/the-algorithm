packagelon com.twittelonr.timelonlinelonrankelonr.config

import com.twittelonr.timelonlinelonrankelonr.deloncidelonr.DeloncidelonrKelony._
import com.twittelonr.timelonlinelons.authorization.TrustelondPelonrmission
import com.twittelonr.timelonlinelons.authorization.RatelonLimitingTrustelondPelonrmission
import com.twittelonr.timelonlinelons.authorization.RatelonLimitingUntrustelondPelonrmission
import com.twittelonr.timelonlinelons.authorization.ClielonntDelontails

objelonct ClielonntAccelonssPelonrmissions {
  // Welon want timelonlinelonrankelonr lockelond down for relonquelonsts outsidelon of what's delonfinelond helonrelon.
  val DelonfaultRatelonLimit = 0d

  delonf unknown(namelon: String): ClielonntDelontails = {
    ClielonntDelontails(namelon, RatelonLimitingUntrustelondPelonrmission(RatelonLimitOvelonrridelonUnknown, DelonfaultRatelonLimit))
  }

  val All: Selonq[ClielonntDelontails] = Selonq(
    /**
     * Production clielonnts for timelonlinelonmixelonr.
     */
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.reloncap.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrReloncapProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.reloncyclelond.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrReloncyclelondProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.hydratelon.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrHydratelonProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.hydratelon_reloncos.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrHydratelonReloncosProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.selonelond_author_ids.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrSelonelondAuthorsProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.simclustelonr.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrSimclustelonrProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.elonntity_twelonelonts.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrelonntityTwelonelontsProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    /**
     * This clielonnt is whitelonlistelond for timelonlinelonmixelonr only as it uselond by
     * List injelonction selonrvicelon which will not belon migratelond to timelonlinelonscorelonr.
     */
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.list.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrListProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.list_twelonelont.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrListTwelonelontProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.community.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrCommunityProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.community_twelonelont.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrCommunityTwelonelontProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.utelong_likelond_by_twelonelonts.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrUtelongLikelondByTwelonelontsProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    /**
     * Production clielonnts for timelonlinelonscorelonr. Most of thelonselon clielonnts havelon thelonir
     * elonquivalelonnts undelonr thelon timelonlinelonmixelonr scopelon (with elonxcelonption of list injelonction
     * clielonnt).
     */
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.reloncap.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrReloncapProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.reloncyclelond.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrReloncyclelondProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.hydratelon.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrHydratelonProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.hydratelon_reloncos.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrHydratelonReloncosProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.selonelond_author_ids.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrSelonelondAuthorsProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.simclustelonr.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrSimclustelonrProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.elonntity_twelonelonts.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrelonntityTwelonelontsProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.list_twelonelont.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrListTwelonelontProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.utelong_likelond_by_twelonelonts.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrUtelongLikelondByTwelonelontsProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonselonrvicelon.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonSelonrvicelonProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.hydratelon_twelonelont_scoring.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonScorelonrHydratelonTwelonelontScoringProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.community_twelonelont.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrCommunityTwelonelontProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.reloncommelonndelond_trelonnd_twelonelont.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonScorelonrReloncommelonndelondTrelonndTwelonelontProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.relonc_topic_twelonelonts.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonScorelonrReloncTopicTwelonelontsProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.popular_topic_twelonelonts.prod",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonScorelonrPopularTopicTwelonelontsProd),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    /**
     * TimelonlinelonRankelonr utilitielons. Traffic proxy, warmups, and consolelon.
     */
    nelonw ClielonntDelontails(
      "timelonlinelonrankelonr.proxy",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonRankelonrProxy),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      TimelonlinelonRankelonrConstants.WarmupClielonntNamelon,
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonRankelonrWarmup),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      TimelonlinelonRankelonrConstants.ForwardelondClielonntNamelon,
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonRankelonrWarmup),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonrankelonr.consolelon",
      RatelonLimitingUntrustelondPelonrmission(RatelonLimitOvelonrridelonUnknown, 1d),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    /**
     * Staging clielonnts.
     */
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.reloncap.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.reloncyclelond.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.hydratelon.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.hydratelon_reloncos.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.selonelond_author_ids.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.simclustelonr.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.elonntity_twelonelonts.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.list.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.list_twelonelont.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.community.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.community_twelonelont.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.community_twelonelont.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.reloncommelonndelond_trelonnd_twelonelont.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.utelong_likelond_by_twelonelonts.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonmixelonr.elonntity_twelonelonts.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.hydratelon_twelonelont_scoring.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.relonc_topic_twelonelonts.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonscorelonr.popular_topic_twelonelonts.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonMixelonrStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    ),
    nelonw ClielonntDelontails(
      "timelonlinelonselonrvicelon.staging",
      RatelonLimitingTrustelondPelonrmission(AllowTimelonlinelonSelonrvicelonStaging),
      protelonctelondWritelonAccelonss = TrustelondPelonrmission
    )
  )
}
