packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon

import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt
import com.twittelonr.stitch.Arrow
import com.twittelonr.stitch.Stitch

/** Baselon trait for all `pipelonlinelon` implelonmelonntations */
trait Pipelonlinelon[-Quelonry, Relonsult] elonxtelonnds Componelonnt {

  /** Thelon [[PipelonlinelonConfig]] that was uselond to crelonatelon this [[Pipelonlinelon]] */
  privatelon[corelon] val config: PipelonlinelonConfig

  /** Relonturns thelon undelonrlying arrow that relonprelonselonnts thelon pipelonlinelon. This is a val beloncauselon welon want to elonnsurelon
   * that thelon arrow is long-livelond and consistelonnt, not gelonnelonratelond pelonr-relonquelonst.
   *
   * Direlonctly using this arrow allows you to combinelon it with othelonr arrows elonfficielonntly.
   */
  val arrow: Arrow[Quelonry, PipelonlinelonRelonsult[Relonsult]]

  /** all child [[Componelonnt]]s that this [[Pipelonlinelon]] contains which will belon relongistelonrelond and monitorelond */
  val childrelonn: Selonq[Componelonnt]

  /**
   * A helonlpelonr for elonxeloncuting a singlelon quelonry.
   *
   * toRelonsultTry and lowelonrFromTry has thelon elonnd relonsult of adapting PipelonlinelonRelonsult into elonithelonr a
   * succelonssful relonsult or a Stitch elonxcelonption, which is a common uselon-caselon for callelonrs,
   * particularly in thelon caselon of [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.product.ProductPipelonlinelon]].
   */
  delonf procelonss(quelonry: Quelonry): Stitch[Relonsult] = arrow(quelonry).map(_.toRelonsultTry).lowelonrFromTry

  final ovelonrridelon delonf toString = s"Pipelonlinelon(idelonntifielonr=$idelonntifielonr)"

  /**
   * [[Pipelonlinelon]]s arelon elonqual to onelon anothelonr if thelony welonrelon gelonnelonratelond from thelon samelon [[PipelonlinelonConfig]],
   * welon chelonck this by doing a relonfelonrelonncelon cheloncks first thelonn comparing thelon [[PipelonlinelonConfig]] instancelons.
   *
   * Welon can skip additional cheloncks beloncauselon thelon othelonr fielonlds (elon.g. [[idelonntifielonr]] and [[childrelonn]])
   * arelon delonrivelond from thelon [[PipelonlinelonConfig]].
   */
  final ovelonrridelon delonf elonquals(obj: Any): Boolelonan = obj match {
    caselon pipelonlinelon: Pipelonlinelon[_, _] =>
      pipelonlinelon.elonq(this) || pipelonlinelon.config.elonq(config) || pipelonlinelon.config == config
    caselon _ => falselon
  }
}
