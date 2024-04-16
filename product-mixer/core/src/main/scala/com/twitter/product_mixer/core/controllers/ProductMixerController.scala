package com.tw ter.product_m xer.core.controllers

 mport com.tw ter.f nagle.http.Request
 mport com.tw ter.f nagle.http.Response
 mport com.tw ter.f nagle.http.Status
 mport com.tw ter.f nagle.http.Route ndex
 mport com.tw ter.f natra.http.Controller
 mport com.tw ter.scrooge.Thr ft thod
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.annotat ons.Flags
 mport com.tw ter.product_m xer.core.model.common. dent f er.Product dent f er
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Serv ceLocal
 mport com.tw ter.product_m xer.core.serv ce.component_reg stry.ComponentReg stry
 mport com.tw ter.product_m xer.core.serv ce.component_reg stry.{
  Reg steredComponent => ComponentReg stryReg steredComponent
}
 mport com.tw ter.ut l.Future
 mport java.net.URLEncoder

/**
 * Reg ster endpo nts necessary for enabl ng Product M xer tool ng such as alerts, dashboard
 * generat on and Turntable.
 *
 * @param debugEndpo nt a debug endpo nt to run quer es aga nst. T  feature  s exper  ntal and  
 *                      do not recom nd that teams use   yet. Prov d ng [[None]] w ll d sable
 *                      debug quer es.
 * @tparam Serv ce face a thr ft serv ce conta n ng t  [[debugEndpo nt]]
 */
case class ProductM xerController[Serv ce face](
   njector:  njector,
  debugEndpo nt: Thr ft thod,
)(
   mpl c  val serv ce Face: Man fest[Serv ce face])
    extends Controller {

  val  sLocal: Boolean =  njector. nstance[Boolean](Flags.na d(Serv ceLocal))

   f (! sLocal) {
    pref x("/adm n/product-m xer") {
      val productNa sFut: Future[Seq[Str ng]] =
         njector. nstance[ComponentReg stry].get.map { componentReg stry =>
          componentReg stry.getAllReg steredComponents.collect {
            case ComponentReg stryReg steredComponent( dent f er: Product dent f er, _, _) =>
               dent f er.na 
          }
        }

      productNa sFut.map { productNa s =>
        productNa s.foreach { productNa  =>
          get(
            route = "/debug-query/" + productNa ,
            adm n = true,
             ndex = So (Route ndex(al as = "Query " + productNa , group = "Feeds/Debug Query"))
          ) { _: Request =>
            val auroraPath =
              URLEncoder.encode(System.getProperty("aurora. nstanceKey", ""), "UTF-8")

            // Extract serv ce na  from cl ent d s nce t re  sn't a spec f c flag for that
            val serv ceNa  =  njector
              . nstance[Str ng](Flags.na d("thr ft.cl ent d"))
              .spl ("\\.")(0)

            val red rectUrl =
              s"https://feeds.tw ter.b z/dtab/$serv ceNa /$productNa ?serv cePath=$auroraPath"

            val response = Response().status(Status.Found)
            response.locat on = red rectUrl
            response
          }
        }
      }
    }
  }

  pref x("/product-m xer") {
    get(route = "/component-reg stry")(GetComponentReg stryHandler( njector).apply)
    get(route = "/debug-conf gurat on")(GetDebugConf gurat onHandler(debugEndpo nt).apply)
  }
}
