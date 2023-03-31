package com.twitter.product_mixer.core.model.marshalling.request

import com.twitter.product_mixer.core.model.common.Component
import com.twitter.product_mixer.core.model.common.identifier.ProductIdentifier

trait Product extends Component {

  /**
   * Identifier names on products can be used to create Feature Switch rules by product,
   * which useful if bucketing occurs in a component shared by multiple products.
   *
   * @see [[com.twitter.product_mixer.core.product.ProductParamConfig.supportedClientFSName]]
   */
  override val identifier: ProductIdentifier

  /**
   * To support StringCenter, override this val to `Some("name-of-string-center-project")` and
   * include the `ProductScopeStringCenterModule` in the server's modules list
   */
  val stringCenterProject: Option[String] = None
}

trait HasProduct {
  def product: Product
}
