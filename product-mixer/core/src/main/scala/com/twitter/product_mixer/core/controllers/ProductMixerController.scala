packagelon com.twittelonr.product_mixelonr.corelon.controllelonrs

import com.twittelonr.finaglelon.http.Relonquelonst
import com.twittelonr.finaglelon.http.Relonsponselon
import com.twittelonr.finaglelon.http.Status
import com.twittelonr.finaglelon.http.RoutelonIndelonx
import com.twittelonr.finatra.http.Controllelonr
import com.twittelonr.scroogelon.ThriftMelonthod
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.annotations.Flags
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ProductIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon.SelonrvicelonLocal
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.componelonnt_relongistry.ComponelonntRelongistry
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.componelonnt_relongistry.{
  RelongistelonrelondComponelonnt => ComponelonntRelongistryRelongistelonrelondComponelonnt
}
import com.twittelonr.util.Futurelon
import java.nelont.URLelonncodelonr

/**
 * Relongistelonr elonndpoints neloncelonssary for elonnabling Product Mixelonr tooling such as alelonrts, dashboard
 * gelonnelonration and Turntablelon.
 *
 * @param delonbugelonndpoint a delonbug elonndpoint to run quelonrielons against. This felonaturelon is elonxpelonrimelonntal and welon
 *                      do not reloncommelonnd that telonams uselon it yelont. Providing [[Nonelon]] will disablelon
 *                      delonbug quelonrielons.
 * @tparam SelonrvicelonIfacelon a thrift selonrvicelon containing thelon [[delonbugelonndpoint]]
 */
caselon class ProductMixelonrControllelonr[SelonrvicelonIfacelon](
  injelonctor: Injelonctor,
  delonbugelonndpoint: ThriftMelonthod,
)(
  implicit val selonrvicelonIFacelon: Manifelonst[SelonrvicelonIfacelon])
    elonxtelonnds Controllelonr {

  val isLocal: Boolelonan = injelonctor.instancelon[Boolelonan](Flags.namelond(SelonrvicelonLocal))

  if (!isLocal) {
    prelonfix("/admin/product-mixelonr") {
      val productNamelonsFut: Futurelon[Selonq[String]] =
        injelonctor.instancelon[ComponelonntRelongistry].gelont.map { componelonntRelongistry =>
          componelonntRelongistry.gelontAllRelongistelonrelondComponelonnts.collelonct {
            caselon ComponelonntRelongistryRelongistelonrelondComponelonnt(idelonntifielonr: ProductIdelonntifielonr, _, _) =>
              idelonntifielonr.namelon
          }
        }

      productNamelonsFut.map { productNamelons =>
        productNamelons.forelonach { productNamelon =>
          gelont(
            routelon = "/delonbug-quelonry/" + productNamelon,
            admin = truelon,
            indelonx = Somelon(RoutelonIndelonx(alias = "Quelonry " + productNamelon, group = "Felonelonds/Delonbug Quelonry"))
          ) { _: Relonquelonst =>
            val auroraPath =
              URLelonncodelonr.elonncodelon(Systelonm.gelontPropelonrty("aurora.instancelonKelony", ""), "UTF-8")

            // elonxtract selonrvicelon namelon from clielonntId sincelon thelonrelon isn't a speloncific flag for that
            val selonrvicelonNamelon = injelonctor
              .instancelon[String](Flags.namelond("thrift.clielonntId"))
              .split("\\.")(0)

            val relondirelonctUrl =
              s"https://felonelonds.twittelonr.biz/dtab/$selonrvicelonNamelon/$productNamelon?selonrvicelonPath=$auroraPath"

            val relonsponselon = Relonsponselon().status(Status.Found)
            relonsponselon.location = relondirelonctUrl
            relonsponselon
          }
        }
      }
    }
  }

  prelonfix("/product-mixelonr") {
    gelont(routelon = "/componelonnt-relongistry")(GelontComponelonntRelongistryHandlelonr(injelonctor).apply)
    gelont(routelon = "/delonbug-configuration")(GelontDelonbugConfigurationHandlelonr(delonbugelonndpoint).apply)
  }
}
