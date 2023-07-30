package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.generic_summary_item

import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.ClientEventInfoMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.item.generic_summary.GenericSummaryAction
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenericSummaryActionMarshaller @Inject() (
  urlMarshaller: UrlMarshaller,
  clientEventInfoMarshaller: ClientEventInfoMarshaller) {

  def apply(genericSummaryItemAction: GenericSummaryAction): urt.GenericSummaryAction =
    urt.GenericSummaryAction(
      url = urlMarshaller(genericSummaryItemAction.url),
      clientEventInfo = genericSummaryItemAction.clientEventInfo.map(clientEventInfoMarshaller(_))
    )
}
