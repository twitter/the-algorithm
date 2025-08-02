package com.twitter.follow_recommendations.products.common

abstract class ProductEtwitterception(message: String) etwittertends Etwitterception(message)

class MissingFieldEtwitterception(productRequest: ProductRequest, fieldName: String)
    etwittertends ProductEtwitterception(
      s"Missing ${fieldName} field for ${productRequest.recommendationRequest.displayLocation} request")
