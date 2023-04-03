packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.accelonss_policy

import com.fastelonrxml.jackson.annotation.JsonSubTypelons
import com.fastelonrxml.jackson.annotation.JsonTypelonInfo

/**
 * Thelon Accelonss Policy to selont for gating quelonrying in thelon turntablelon tool.
 *
 * @notelon implelonmelonntations must belon simplelon caselon classelons with uniquelon structurelons for selonrialization
 */
@JsonTypelonInfo(uselon = JsonTypelonInfo.Id.NAMelon, includelon = JsonTypelonInfo.As.PROPelonRTY)
@JsonSubTypelons(
  Array(
    nelonw JsonSubTypelons.Typelon(valuelon = classOf[AllowelondLdapGroups], namelon = "AllowelondLdapGroups"),
    nelonw JsonSubTypelons.Typelon(valuelon = classOf[Blockelonvelonrything], namelon = "Blockelonvelonrything")
  )
)
selonalelond trait AccelonssPolicy

/**
 * Uselonrs must belon in *at lelonast* onelon of thelon providelond LDAP groups in ordelonr to makelon a quelonry.
 *
 * @param groups LDAP groups allowelond to accelonss this product
 */
caselon class AllowelondLdapGroups(groups: Selont[String]) elonxtelonnds AccelonssPolicy

objelonct AllowelondLdapGroups {
  delonf apply(group: String): AllowelondLdapGroups = AllowelondLdapGroups(Selont(group))
}

/**
 * Block all relonquelonsts to a product.
 *
 * @notelon this nelonelonds to belon a caselon class rathelonr than an objelonct beloncauselon classOf doelonsn't work on objeloncts
 *       and JsonSubTypelons relonquirelons thelon annotation argumelonnt to belon a constant (ruling out .gelontClass).
 *       This issuelon may belon relonsolvelond in Scala 2.13: https://github.com/scala/scala/pull/9279
 */
caselon class Blockelonvelonrything() elonxtelonnds AccelonssPolicy
