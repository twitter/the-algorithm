packagelon com.twittelonr.follow_reloncommelonndations.products.common

import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation

trait ProductRelongistry {
  delonf products: Selonq[Product]
  delonf displayLocationProductMap: Map[DisplayLocation, Product]
  delonf gelontProductByDisplayLocation(displayLocation: DisplayLocation): Product
}
