package com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.product_p pel ne

 mport com.google. nject.Prov der
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .ParamsBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neRequest
 mport com.tw ter.product_m xer.core.product.reg stry.ProductP pel neReg stry
 mport com.tw ter.st ch.St ch
 mport scala.reflect.runt  .un verse._

/**
 * A [[Cand dateS ce]] for gett ng cand dates from a d fferent
 * [[com.tw ter.product_m xer.core.model.marshall ng.request.Product]] w h n t  sa  Product
 * M xer-based serv ce. T   s useful w n call ng a Recom ndat onP pel ne-based Product from a
 * M xerP pel ne-based Product.  n t  scenar o, t  two Products can rema n
 *  ndependent and encapsulated w h n t  Product M xer serv ce, wh ch prov des future opt onal y
 * for m grat ng one of t  two products  nto a new Product M xer-based serv ce based on t 
 * scal ng needs.
 *
 * @tparam Query [[P pel neQuery]] from t  or g nat ng Product
 * @tparam M xerRequest t  [[Request]] doma n model for t  Product M xer serv ce. Adds a Context
 *                      bound (syntact c sugar) to add TypeTag to  mpl c  scope for
 *                      [[ProductP pel neReg stry.getProductP pel ne()]]. Note that `tra ` does not
 *                      support context bounds, so t  abstract on  s expressed as an
 *                      `abstract class`.
 * @tparam ProductP pel neResult t  return type of t  cand date s ce Product. Adds a Context
 *                               bound (syntact c sugar) to add TypeTag to  mpl c  scope for
 *                               [[ProductP pel neReg stry.getProductP pel ne()]]
 * @tparam Cand date t  type of cand date returned by t  cand date s ce, wh ch  s typ cally
 *                   extracted from w h n t  ProductP pel neResult type
 */
abstract class ProductP pel neCand dateS ce[
  -Query <: P pel neQuery,
  M xerRequest <: Request: TypeTag,
  ProductP pel neResult: TypeTag,
  +Cand date]
    extends Cand dateS ce[Query, Cand date] {

  /**
   * @note Def ne as a Gu ce [[Prov der]]  n order to break t  c rcular  nject on dependency
   */
  val productP pel neReg stry: Prov der[ProductP pel neReg stry]

  /**
   * @note Def ne as a Gu ce [[Prov der]]  n order to break t  c rcular  nject on dependency
   */
  val paramsBu lder: Prov der[ParamsBu lder]

  def p pel neRequestTransfor r(currentP pel neQuery: Query): M xerRequest

  def productP pel neResultTransfor r(productP pel neResult: ProductP pel neResult): Seq[Cand date]

  overr de def apply(query: Query): St ch[Seq[Cand date]] = {
    val request = p pel neRequestTransfor r(query)

    val params = paramsBu lder
      .get().bu ld(
        cl entContext = request.cl entContext,
        product = request.product,
        featureOverr des = request.debugParams.flatMap(_.featureOverr des).getOrElse(Map.empty)
      )

    productP pel neReg stry
      .get()
      .getProductP pel ne[M xerRequest, ProductP pel neResult](request.product)
      .process(ProductP pel neRequest(request, params))
      .map(productP pel neResultTransfor r)
  }
}
