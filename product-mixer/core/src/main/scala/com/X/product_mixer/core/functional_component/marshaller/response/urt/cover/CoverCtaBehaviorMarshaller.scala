package com.X.product_mixer.core.functional_component.marshaller.response.urt.cover

import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.cover.CoverCtaBehavior
import com.X.product_mixer.core.model.marshalling.response.urt.cover.CoverBehaviorDismiss
import com.X.product_mixer.core.model.marshalling.response.urt.cover.CoverBehaviorNavigate
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton
import com.X.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller

@Singleton
class CoverCtaBehaviorMarshaller @Inject() (
  richTextMarshaller: RichTextMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(coverCtaBehavior: CoverCtaBehavior): urt.CoverCtaBehavior =
    coverCtaBehavior match {
      case dismiss: CoverBehaviorDismiss =>
        urt.CoverCtaBehavior.Dismiss(
          urt.CoverBehaviorDismiss(dismiss.feedbackMessage.map(richTextMarshaller(_))))
      case nav: CoverBehaviorNavigate =>
        urt.CoverCtaBehavior.Navigate(urt.CoverBehaviorNavigate(urlMarshaller(nav.url)))
    }
}
