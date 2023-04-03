packagelon com.twittelonr.follow_reloncommelonndations.products.common

import com.twittelonr.follow_reloncommelonndations.asselonmblelonr.modelonls.Layout
import com.twittelonr.follow_reloncommelonndations.common.baselon.BaselonReloncommelonndationFlow
import com.twittelonr.follow_reloncommelonndations.common.baselon.Transform
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Reloncommelonndation
import com.twittelonr.follow_reloncommelonndations.modelonls.ReloncommelonndationRelonquelonst
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.{Product => ProductMixelonrProduct}
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Params

trait Product {

  /** elonach product also relonquirelons a human-relonadablelon namelon.
   * You can changelon this at any timelon
   */
  delonf namelon: String

  /**
   * elonvelonry product nelonelonds a machinelon-frielonndly idelonntifielonr for intelonrnal uselon.
   * You should uselon thelon samelon namelon as thelon product packagelon namelon.
   * elonxcelonpt dashelons arelon belonttelonr than undelonrscorelon
   *
   * Avoid changing this oncelon it's in production.
   */
  delonf idelonntifielonr: String

  delonf displayLocation: DisplayLocation

  delonf selonlelonctWorkflows(
    relonquelonst: ProductRelonquelonst
  ): Stitch[Selonq[BaselonReloncommelonndationFlow[ProductRelonquelonst, _ <: Reloncommelonndation]]]

  /**
   * Blelonndelonr is relonsponsiblelon for blelonnding togelonthelonr thelon candidatelons gelonnelonratelond by diffelonrelonnt flows uselond
   * in a product. For elonxamplelon, if a product uselons two flows, it is blelonndelonr's relonsponsibility to
   * intelonrlelonavelon thelonir gelonnelonratelond candidatelons togelonthelonr and makelon a unifielond selonquelonncelon of candidatelons.
   */
  delonf blelonndelonr: Transform[ProductRelonquelonst, Reloncommelonndation]

  /**
   * It is relonsultsTransformelonr job to do any final transformations nelonelondelond on thelon final list of
   * candidatelons gelonnelonratelond by a product. For elonxamplelon, if a final quality chelonck on candidatelons nelonelondelond,
   * relonsultsTransformelonr will handlelon it.
   */
  delonf relonsultsTransformelonr(relonquelonst: ProductRelonquelonst): Stitch[Transform[ProductRelonquelonst, Reloncommelonndation]]

  delonf elonnablelond(relonquelonst: ProductRelonquelonst): Stitch[Boolelonan]

  delonf layout: Option[Layout] = Nonelon

  delonf productMixelonrProduct: Option[ProductMixelonrProduct] = Nonelon
}

caselon class ProductRelonquelonst(reloncommelonndationRelonquelonst: ReloncommelonndationRelonquelonst, params: Params)
