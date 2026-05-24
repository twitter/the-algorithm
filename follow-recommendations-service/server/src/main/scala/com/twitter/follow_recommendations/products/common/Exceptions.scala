package com.twitter.follow_recommendations.products.common

abstract class ProductException(message: String) extends Exception(message)

class MissingFieldException(productRequest: ProductRequest, fieldName: String)
    extends ProductException(
      s"Missing ${fieldName} field for ${productRequest.recommendationRequest.displayLocation} request")
