package com.tw ter.product_m xer.core.module.str ngcenter

 mport com.google. nject.Prov des
 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.jackson.ScalaObjectMapper
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product
 mport com.tw ter.str ngcenter.cl ent.ExternalStr ngReg stry
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.str ngcenter.cl ent.Str ngCenterCl entConf g
 mport com.tw ter.str ngcenter.cl ent.s ces.Refresh ngStr ngS ce
 mport com.tw ter.str ngcenter.cl ent.s ces.Refresh ngStr ngS ceConf g
 mport com.tw ter.str ngcenter.cl ent.s ces.Str ngS ce
 mport com.tw ter.translat on.Languages
 mport javax. nject.S ngleton
 mport scala.collect on.concurrent

/*
 * Fun tr v a - t  has to be a Class not an Object, ot rw se w n   ./bazel test blah/...
 * and glob mult ple feature tests toget r  'll reuse t  concurrentMaps below across
 * execut ons / d fferent server objects.
 */
class ProductScopeStr ngCenterModule extends Tw terModule {

  pr vate val loadNoth ng =
    flag[Boolean](na  = "str ngcenter.dontload", default = false,  lp = "Avo d load ng any f les")

  flag[Boolean](
    na  = "str ngcenter.handle.language.fallback",
    default = true,
     lp = "Handle language fallback for serv ces that don't already handle  ")

  flag[Str ng](
    na  = "str ngcenter.default_bundle_path",
    default = "str ngcenter",
     lp = "T  path on d sk to t  default bundle ava lable at startup t  ")

  pr vate val refresh ng nterval = flag[ nt](
    na  = "str ngcenter.refresh_ nterval_m nutes",
    default = 3,
     lp = "How often to poll t  refresh ng bundle path to c ck for new bundles")

  /* T  Gu ce  njector  s s ngle threaded, but out of a preponderance of caut on   use a concurrent Map.
   *
   *   need to ensure that   only bu ld one Str ngS ce, Str ngCenter cl ent, and External Str ng
   * Reg stry for each Str ng Center Project. @ProductScoped doesn't ensure t  on  's own as
   * two products can have t  sa  Str ng Center Project set.
   */
  val str ngS ces: concurrent.Map[Str ng, Str ngS ce] = concurrent.Tr eMap.empty
  val str ngCenterCl ents: concurrent.Map[Str ng, Str ngCenter] = concurrent.Tr eMap.empty
  val externalStr ngReg str es: concurrent.Map[Str ng, ExternalStr ngReg stry] =
    concurrent.Tr eMap.empty

  @ProductScoped
  @Prov des
  def prov desStr ngCenterCl ents(
    abDec der: Logg ngABDec der,
    str ngS ce: Str ngS ce,
    languages: Languages,
    statsRece ver: StatsRece ver,
    cl entConf g: Str ngCenterCl entConf g,
    product: Product
  ): Str ngCenter = {
    str ngCenterCl ents.getOrElseUpdate(
      str ngCenterForProduct(product), {
        new Str ngCenter(
          abDec der,
          str ngS ce,
          languages,
          statsRece ver,
          cl entConf g
        )
      })
  }

  @ProductScoped
  @Prov des
  def prov desExternalStr ngReg str es(
    product: Product
  ): ExternalStr ngReg stry = {
    externalStr ngReg str es.getOrElseUpdate(
      str ngCenterForProduct(product), {
        new ExternalStr ngReg stry()
      })
  }

  @ProductScoped
  @Prov des
  def prov desStr ngCenterS ces(
    mapper: ScalaObjectMapper,
    statsRece ver: StatsRece ver,
    product: Product,
    @Flag("str ngcenter.default_bundle_path") defaultBundlePath: Str ng
  ): Str ngS ce = {
     f (loadNoth ng()) {
      Str ngS ce.Empty
    } else {
      val str ngCenterProduct = str ngCenterForProduct(product)

      str ngS ces.getOrElseUpdate(
        str ngCenterProduct, {
          val conf g = Refresh ngStr ngS ceConf g(
            str ngCenterProduct,
            defaultBundlePath,
            "str ngcenter/downloaded/current/str ngcenter",
            refresh ng nterval().m nutes
          )
          new Refresh ngStr ngS ce(
            conf g,
            mapper,
            statsRece ver
              .scope("Str ngCenter", "refresh ng", "project", str ngCenterProduct))
        }
      )
    }
  }

  pr vate def str ngCenterForProduct(product: Product): Str ng =
    product.str ngCenterProject.getOrElse {
      throw new UnsupportedOperat onExcept on(
        s"No Str ngCenter project def ned for Product ${product. dent f er}")
    }

  @S ngleton
  @Prov des
  def prov desStr ngCenterCl entConf g(
    @Flag("str ngcenter.handle.language.fallback") handleLanguageFallback: Boolean
  ): Str ngCenterCl entConf g = {
    Str ngCenterCl entConf g(handleLanguageFallback = handleLanguageFallback)
  }
}
