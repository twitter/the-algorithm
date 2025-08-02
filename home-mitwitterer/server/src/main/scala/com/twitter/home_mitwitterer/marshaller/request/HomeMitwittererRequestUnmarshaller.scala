package com.twitter.home_mitwitterer.marshaller.request

import com.twitter.home_mitwitterer.model.request.HomeMitwittererRequest
import com.twitter.home_mitwitterer.{thriftscala => t}
import com.twitter.product_mitwitterer.core.functional_component.marshaller.request.ClientContetwittertUnmarshaller
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HomeMitwittererRequestUnmarshaller @Inject() (
  clientContetwittertUnmarshaller: ClientContetwittertUnmarshaller,
  homeProductUnmarshaller: HomeMitwittererProductUnmarshaller,
  homeProductContetwittertUnmarshaller: HomeMitwittererProductContetwittertUnmarshaller,
  homeDebugParamsUnmarshaller: HomeMitwittererDebugParamsUnmarshaller) {

  def apply(homeRequest: t.HomeMitwittererRequest): HomeMitwittererRequest = {
    HomeMitwittererRequest(
      clientContetwittert = clientContetwittertUnmarshaller(homeRequest.clientContetwittert),
      product = homeProductUnmarshaller(homeRequest.product),
      productContetwittert = homeRequest.productContetwittert.map(homeProductContetwittertUnmarshaller(_)),
      // Avoid de-serializing cursors in the request unmarshaller. The unmarshaller should never
      // fail, which is often a possibility when trying to de-serialize a cursor. Cursors can also
      // be product-specific and more appropriately handled in individual product pipelines.
      serializedRequestCursor = homeRequest.cursor,
      matwitterResults = homeRequest.matwitterResults,
      debugParams = homeRequest.debugParams.map(homeDebugParamsUnmarshaller(_)),
      homeRequestParam = false
    )
  }
}
