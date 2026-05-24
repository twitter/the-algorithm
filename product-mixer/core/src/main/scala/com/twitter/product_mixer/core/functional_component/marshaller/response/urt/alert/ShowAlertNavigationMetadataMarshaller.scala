package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert

import com.twitter.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertNavigationMetadata
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowAlertNavigationMetadataMarshaller @Inject() () {

  def apply(alertNavigationMetadata: ShowAlertNavigationMetadata): urt.ShowAlertNavigationMetadata =
    urt.ShowAlertNavigationMetadata(navigateToEntryId =
      Some(alertNavigationMetadata.navigateToEntryId))
}
