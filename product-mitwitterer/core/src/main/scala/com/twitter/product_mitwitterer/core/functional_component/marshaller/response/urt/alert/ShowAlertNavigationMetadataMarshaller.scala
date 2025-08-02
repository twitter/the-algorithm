package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.alert

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertNavigationMetadata
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ShowAlertNavigationMetadataMarshaller @Inject() () {

  def apply(alertNavigationMetadata: ShowAlertNavigationMetadata): urt.ShowAlertNavigationMetadata =
    urt.ShowAlertNavigationMetadata(navigateToEntryId =
      Some(alertNavigationMetadata.navigateToEntryId))
}
