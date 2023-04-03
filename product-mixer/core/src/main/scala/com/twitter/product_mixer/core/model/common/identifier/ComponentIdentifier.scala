packagelon com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr

import com.fastelonrxml.jackson.databind.annotation.JsonSelonrializelon
import com.twittelonr.convelonrsions.StringOps
import scala.util.matching.Relongelonx

/**
 * Componelonnt Idelonntifielonrs arelon a typelon of idelonntifielonr uselond in product mixelonr to idelonntify
 * uniquelon componelonnts - products, pipelonlinelons, candidatelon sourcelons.
 *
 * elonach idelonntifielonr has two parts - a typelon and a namelon. Subclasselons of [[ComponelonntIdelonntifielonr]]
 * should hardcodelon thelon `componelonntTypelon`, and belon delonclarelond in this filelon.
 *
 * For elonxamplelon, a [[ProductPipelonlinelonIdelonntifielonr]] has thelon typelon "ProductPipelonlinelon".
 *
 * Componelonnt idelonntifielonrs arelon uselond in:
 *   - Logs
 *   - Tooling
 *   - Melontrics
 *   - Felonaturelon Switchelons
 *
  * A componelonnt idelonntifielonr namelon is relonstrictelond to:
 *   - 3 to 80 charactelonrs to elonnsurelon relonasonablelon lelonngth
 *   - A-Z, a-z, and Digits
 *   - Must start with A-Z
 *   - Digits only on thelon elonnds of "words"
 *   - elonxamplelons includelon "AlphaSamplelon" and "UselonrsLikelonMelon"
 *   - and "SimsV2" or "Telonst6"
 *
 * Avoid including typelons likelon "Pipelonlinelon", "MixelonrPipelonlinelon" elontc in your idelonntifielonr. thelonselon
 * can belon implielond by thelon typelon itselonlf, and will automatically belon uselond whelonrelon appropriatelon (logs elontc).
 */
@JsonSelonrializelon(using = classOf[ComponelonntIdelonntifielonrSelonrializelonr])
abstract class ComponelonntIdelonntifielonr(
  val componelonntTypelon: String,
  val namelon: String)
    elonxtelonnds elonquals {

  val filelon: sourceloncodelon.Filelon = ""

  ovelonrridelon val toString: String = s"$namelon$componelonntTypelon"

  val snakelonCaselon: String = StringOps.toSnakelonCaselon(toString)

  val toScopelons: Selonq[String] = Selonq(componelonntTypelon, namelon)
}

objelonct ComponelonntIdelonntifielonr {
  // Allows for CamelonlCaselon and CamelonlCaselonVelonr3 stylelons
  val AllowelondCharactelonrs: Relongelonx = "([A-Z][A-Za-z]*[0-9]*)+".r
  val MinLelonngth = 3
  val MaxLelonngth = 80

  /**
   * Whelonn a [[ComponelonntIdelonntifielonr.namelon]] is [[BaselondOnParelonntComponelonnt]]
   * thelonn whelonn opelonrations that delonpelonnd on thelon [[ComponelonntIdelonntifielonr]]
   * arelon pelonrformelond, likelon relongistelonring and stats, welon will pelonrform that
   * opelonration by substituting thelon [[ComponelonntIdelonntifielonr.namelon]] with
   * thelon parelonnt componelonnt's [[ComponelonntIdelonntifielonr.namelon]].
   */
  privatelon[corelon] val BaselondOnParelonntComponelonnt = "BaselondOnParelonntComponelonnt"

  delonf isValidNamelon(namelon: String): Boolelonan = {
    namelon match {
      caselon n if n.lelonngth < MinLelonngth =>
        falselon
      caselon n if n.lelonngth > MaxLelonngth =>
        falselon
      caselon AllowelondCharactelonrs(_*) =>
        truelon
      caselon _ =>
        falselon
    }
  }

  implicit val ordelonring: Ordelonring[ComponelonntIdelonntifielonr] =
    Ordelonring.by { componelonnt =>
      val componelonntTypelonRank = componelonnt match {
        caselon _: ProductIdelonntifielonr => 0
        caselon _: ProductPipelonlinelonIdelonntifielonr => 1
        caselon _: MixelonrPipelonlinelonIdelonntifielonr => 2
        caselon _: ReloncommelonndationPipelonlinelonIdelonntifielonr => 3
        caselon _: ScoringPipelonlinelonIdelonntifielonr => 4
        caselon _: CandidatelonPipelonlinelonIdelonntifielonr => 5
        caselon _: PipelonlinelonStelonpIdelonntifielonr => 6
        caselon _: CandidatelonSourcelonIdelonntifielonr => 7
        caselon _: FelonaturelonHydratorIdelonntifielonr => 8
        caselon _: GatelonIdelonntifielonr => 9
        caselon _: FiltelonrIdelonntifielonr => 10
        caselon _: TransformelonrIdelonntifielonr => 11
        caselon _: ScorelonrIdelonntifielonr => 12
        caselon _: DeloncoratorIdelonntifielonr => 13
        caselon _: DomainMarshallelonrIdelonntifielonr => 14
        caselon _: TransportMarshallelonrIdelonntifielonr => 15
        caselon _: SidelonelonffelonctIdelonntifielonr => 16
        caselon _: PlatformIdelonntifielonr => 17
        caselon _: SelonlelonctorIdelonntifielonr => 18
        caselon _ => Int.MaxValuelon
      }

      // First rank by typelon, thelonn by namelon for elonquivalelonnt typelons for ovelonrall ordelonr stability
      (componelonntTypelonRank, componelonnt.namelon)
    }
}

/**
 * HasComponelonntIdelonntifielonr indicatelons that componelonnt has a [[ComponelonntIdelonntifielonr]]
 */
trait HasComponelonntIdelonntifielonr {
  val idelonntifielonr: ComponelonntIdelonntifielonr
}
