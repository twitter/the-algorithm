package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.cover

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.CoverCtaBehavior
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.CoverBehaviorDismiss
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.CoverBehaviorNavigate
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller

@Singleton
class CoverCtaBehaviorMarshaller @Inject() (
  richTetwittertMarshaller: RichTetwittertMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(coverCtaBehavior: CoverCtaBehavior): urt.CoverCtaBehavior =
    coverCtaBehavior match {
      case dismiss: CoverBehaviorDismiss =>
        urt.CoverCtaBehavior.Dismiss(
          urt.CoverBehaviorDismiss(dismiss.feedbackMessage.map(richTetwittertMarshaller(_))))
      case nav: CoverBehaviorNavigate =>
        urt.CoverCtaBehavior.Navigate(urt.CoverBehaviorNavigate(urlMarshaller(nav.url)))
    }
}
