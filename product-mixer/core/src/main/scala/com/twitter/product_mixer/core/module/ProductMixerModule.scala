packagelon com.twittelonr.product_mixelonr.corelon.modulelon

import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon
import com.twittelonr.finatra.deloncidelonr.modulelons.DeloncidelonrModulelon
import com.twittelonr.finatra.intelonrnational.modulelons.LanguagelonsModulelon
import com.twittelonr.product_mixelonr.corelon.product.guicelon.ProductScopelonModulelon
import com.twittelonr.finatra.jackson.modulelons.ScalaObjelonctMappelonrModulelon
import com.twittelonr.injelonct.thrift.modulelons.ThriftClielonntIdModulelon

/**
 * ProductMixelonrModulelon providelons modulelons relonquirelond by all Product Mixelonr selonrvicelons.
 *
 * @notelon if your selonrvicelon calls Strato you will nelonelond to add thelon [[StratoClielonntModulelon]] yourselonlf.
 */
objelonct ProductMixelonrModulelon elonxtelonnds TwittelonrModulelon {

  ovelonrridelon val modulelons = Selonq(
    ABDeloncidelonrModulelon,
    ConfigApiModulelon,
    DeloncidelonrModulelon,
    FelonaturelonSwitchelonsModulelon,
    LanguagelonsModulelon,
    PipelonlinelonelonxeloncutionLoggelonrModulelon,
    ProductMixelonrFlagModulelon,
    nelonw ProductScopelonModulelon(),
    ScalaObjelonctMappelonrModulelon,
    ThriftClielonntIdModulelon,
  )
}
