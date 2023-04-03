packagelon com.twittelonr.product_mixelonr.corelon.product.guicelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.Product
import com.googlelon.injelonct.Kelony

/**
 * A speloncialization of SimplelonScopelon - a simplelon Guicelon Scopelon that takelons an initial Product Mixelonr Product as a kelony
 */
class ProductScopelon elonxtelonnds SimplelonScopelon {
  delonf lelont[T](product: Product)(f: => T): T = supelonr.lelont(Map(Kelony.gelont(classOf[Product]) -> product))(f)
}
