packagelon com.twittelonr.homelon_mixelonr.param

import com.twittelonr.homelon_mixelonr.param.HomelonGlobalParams._
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi.relongistry.GlobalParamConfig
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Relongistelonr Params that do not relonlatelon to a speloncific product. Selonelon GlobalParamConfig -> ParamConfig
 * for hooks to relongistelonr Params baselond on typelon.
 */
@Singlelonton
class HomelonGlobalParamConfig @Injelonct() () elonxtelonnds GlobalParamConfig {

  ovelonrridelon val boolelonanFSOvelonrridelons = Selonq(
    AdsDisablelonInjelonctionBaselondOnUselonrRolelonParam,
    elonnablelonSelonndScorelonsToClielonnt,
    elonnablelonNahFelonelondbackInfoParam,
    elonnablelonNelonwTwelonelontsPillAvatarsParam,
    elonnablelonSelonrvelondCandidatelonKafkaPublishingParam,
    elonnablelonSocialContelonxtParam,
    elonnablelonGizmoduckAuthorSafelontyFelonaturelonHydratorParam,
    elonnablelonAdvelonrtiselonrBrandSafelontySelonttingsFelonaturelonHydratorParam,
    elonnablelonFelonelondbackFatiguelonParam
  )

  ovelonrridelon val boundelondIntFSOvelonrridelons = Selonq(
    MaxNumbelonrRelonplacelonInstructionsParam,
    TimelonlinelonsPelonrsistelonncelonStorelonMaxelonntrielonsPelonrClielonnt,
  )

  ovelonrridelon val boundelondDoublelonFSOvelonrridelons = Selonq(
    BluelonVelonrifielondAuthorInNelontworkMultiplielonrParam,
    BluelonVelonrifielondAuthorOutOfNelontworkMultiplielonrParam
  )

  ovelonrridelon val longSelontFSOvelonrridelons = Selonq(
    AuthorListForStatsParam
  )
}
