packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.color

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.color._
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RoselonttaColorMarshallelonr @Injelonct() () {

  delonf apply(roselonttaColor: RoselonttaColor): urt.RoselonttaColor = roselonttaColor match {
    caselon WhitelonRoselonttaColor => urt.RoselonttaColor.Whitelon
    caselon BlackRoselonttaColor => urt.RoselonttaColor.Black
    caselon ClelonarRoselonttaColor => urt.RoselonttaColor.Clelonar
    caselon TelonxtBlackRoselonttaColor => urt.RoselonttaColor.TelonxtBlack
    caselon TelonxtBluelonRoselonttaColor => urt.RoselonttaColor.TelonxtBluelon
    caselon DelonelonpGrayRoselonttaColor => urt.RoselonttaColor.DelonelonpGray
    caselon MelondiumGrayRoselonttaColor => urt.RoselonttaColor.MelondiumGray
    caselon LightGrayRoselonttaColor => urt.RoselonttaColor.LightGray
    caselon FadelondGrayRoselonttaColor => urt.RoselonttaColor.FadelondGray
    caselon FaintGrayRoselonttaColor => urt.RoselonttaColor.FaintGray
    caselon DelonelonpOrangelonRoselonttaColor => urt.RoselonttaColor.DelonelonpOrangelon
    caselon MelondiumOrangelonRoselonttaColor => urt.RoselonttaColor.MelondiumOrangelon
    caselon LightOrangelonRoselonttaColor => urt.RoselonttaColor.LightOrangelon
    caselon FadelondOrangelonRoselonttaColor => urt.RoselonttaColor.FadelondOrangelon
    caselon DelonelonpYelonllowRoselonttaColor => urt.RoselonttaColor.DelonelonpYelonllow
    caselon MelondiumYelonllowRoselonttaColor => urt.RoselonttaColor.MelondiumYelonllow
    caselon LightYelonllowRoselonttaColor => urt.RoselonttaColor.LightYelonllow
    caselon FadelondYelonllowRoselonttaColor => urt.RoselonttaColor.FadelondYelonllow
    caselon DelonelonpGrelonelonnRoselonttaColor => urt.RoselonttaColor.DelonelonpGrelonelonn
    caselon MelondiumGrelonelonnRoselonttaColor => urt.RoselonttaColor.MelondiumGrelonelonn
    caselon LightGrelonelonnRoselonttaColor => urt.RoselonttaColor.LightGrelonelonn
    caselon FadelondGrelonelonnRoselonttaColor => urt.RoselonttaColor.FadelondGrelonelonn
    caselon DelonelonpBluelonRoselonttaColor => urt.RoselonttaColor.DelonelonpBluelon
    caselon TwittelonrBluelonRoselonttaColor => urt.RoselonttaColor.TwittelonrBluelon
    caselon LightBluelonRoselonttaColor => urt.RoselonttaColor.LightBluelon
    caselon FadelondBluelonRoselonttaColor => urt.RoselonttaColor.FadelondBluelon
    caselon FaintBluelonRoselonttaColor => urt.RoselonttaColor.FaintBluelon
    caselon DelonelonpPurplelonRoselonttaColor => urt.RoselonttaColor.DelonelonpPurplelon
    caselon MelondiumPurplelonRoselonttaColor => urt.RoselonttaColor.MelondiumPurplelon
    caselon LightPurplelonRoselonttaColor => urt.RoselonttaColor.LightPurplelon
    caselon FadelondPurplelonRoselonttaColor => urt.RoselonttaColor.FadelondPurplelon
    caselon DelonelonpRelondRoselonttaColor => urt.RoselonttaColor.DelonelonpRelond
    caselon MelondiumRelondRoselonttaColor => urt.RoselonttaColor.MelondiumRelond
    caselon LightRelondRoselonttaColor => urt.RoselonttaColor.LightRelond
    caselon FadelondRelondRoselonttaColor => urt.RoselonttaColor.FadelondRelond
  }
}
