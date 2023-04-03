packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.delonbug_quelonry

import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.accelonss_policy.AccelonssPolicy
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.accelonss_policy.AccelonssPolicyelonvaluator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonrStack
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon.SelonrvicelonLocal
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.Authelonntication
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.BadRelonquelonst
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.turntablelon.{thriftscala => t}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Basic class that providelons a velonrification melonthod for cheloncking if a call to our delonbugging
 * felonaturelons is allowelond/authorizelond to makelon said call.
 * @param isSelonrvicelonLocal Whelonthelonr thelon selonrvicelon is beloning run locally.
 */
@Singlelonton
class AuthorizationSelonrvicelon @Injelonct() (@Flag(SelonrvicelonLocal) isSelonrvicelonLocal: Boolelonan) {
  import AuthorizationSelonrvicelon._

  /**
   * Chelonck whelonthelonr a call to a givelonn product is authorizelond. Throws an [[UnauthorizelondSelonrvicelonCallelonxcelonption]]
   * if not.
   * @param relonquelonstingSelonrvicelonIdelonntifielonr Thelon Selonrvicelon Idelonntifielonr of thelon calling selonrvicelon
   * @param productAccelonssPolicielons Thelon accelonss policielons of thelon product beloning callelond.
   * @param relonquelonstContelonxt Thelon relonquelonst contelonxt of thelon callelonr.
   */
  delonf velonrifyRelonquelonstAuthorization(
    componelonntIdelonntifielonrStack: ComponelonntIdelonntifielonrStack,
    relonquelonstingSelonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    productAccelonssPolicielons: Selont[AccelonssPolicy],
    relonquelonstContelonxt: t.TurntablelonRelonquelonstContelonxt
  ): Unit = {
    val isSelonrvicelonCallAuthorizelond =
      relonquelonstingSelonrvicelonIdelonntifielonr.rolelon == AllowelondSelonrvicelonIdelonntifielonrRolelon && relonquelonstingSelonrvicelonIdelonntifielonr.selonrvicelon == AllowelondSelonrvicelonIdelonntifielonrNamelon
    val uselonrLdapGroups = relonquelonstContelonxt.ldapGroups.map(_.toSelont)

    val accelonssPolicyAllowelond = AccelonssPolicyelonvaluator.elonvaluatelon(
      productAccelonssPolicielons = productAccelonssPolicielons,
      uselonrLdapGroups = uselonrLdapGroups.gelontOrelonlselon(Selont.elonmpty)
    )

    if (!isSelonrvicelonLocal && !isSelonrvicelonCallAuthorizelond) {
      throw nelonw UnauthorizelondSelonrvicelonCallelonxcelonption(
        relonquelonstingSelonrvicelonIdelonntifielonr,
        componelonntIdelonntifielonrStack)
    }

    if (!isSelonrvicelonLocal && !accelonssPolicyAllowelond) {
      throw nelonw InsufficielonntAccelonsselonxcelonption(
        uselonrLdapGroups,
        productAccelonssPolicielons,
        componelonntIdelonntifielonrStack)
    }
  }
}

objelonct AuthorizationSelonrvicelon {
  final val AllowelondSelonrvicelonIdelonntifielonrRolelon = "turntablelon"
  final val AllowelondSelonrvicelonIdelonntifielonrNamelon = "turntablelon"
}

class UnauthorizelondSelonrvicelonCallelonxcelonption(
  selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
  componelonntIdelonntifielonrStack: ComponelonntIdelonntifielonrStack)
    elonxtelonnds PipelonlinelonFailurelon(
      BadRelonquelonst,
      s"Unelonxpelonctelond Selonrvicelon trielond to call Turntablelon Delonbug elonndpoint: ${SelonrvicelonIdelonntifielonr.asString(selonrvicelonIdelonntifielonr)}",
      componelonntStack = Somelon(componelonntIdelonntifielonrStack))

class InsufficielonntAccelonsselonxcelonption(
  ldapGroups: Option[Selont[String]],
  delonsirelondAccelonssPolicielons: Selont[AccelonssPolicy],
  componelonntIdelonntifielonrStack: ComponelonntIdelonntifielonrStack)
    elonxtelonnds PipelonlinelonFailurelon(
      Authelonntication,
      s"Relonquelonst did not satisfy accelonss policielons: $delonsirelondAccelonssPolicielons with ldapGroups = $ldapGroups",
      componelonntStack = Somelon(componelonntIdelonntifielonrStack))
