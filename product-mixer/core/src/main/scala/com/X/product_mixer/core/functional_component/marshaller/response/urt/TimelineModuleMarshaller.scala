package com.X.product_mixer.core.functional_component.marshaller.response.urt

import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.ClientEventInfoMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.FeedbackInfoMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.timeline_module.ModuleDisplayTypeMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.timeline_module.ModuleFooterMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.timeline_module.ModuleHeaderMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.timeline_module.ModuleMetadataMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.timeline_module.ModuleShowMoreBehaviorMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineModule
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelineModuleMarshaller @Inject() (
  moduleItemMarshaller: ModuleItemMarshaller,
  moduleDisplayTypeMarshaller: ModuleDisplayTypeMarshaller,
  moduleHeaderMarshaller: ModuleHeaderMarshaller,
  moduleFooterMarshaller: ModuleFooterMarshaller,
  clientEventInfoMarshaller: ClientEventInfoMarshaller,
  feedbackInfoMarshaller: FeedbackInfoMarshaller,
  moduleMetadataMarshaller: ModuleMetadataMarshaller,
  moduleShowMoreBehaviorMarshaller: ModuleShowMoreBehaviorMarshaller) {

  def apply(timelineModule: TimelineModule): urt.TimelineModule = urt.TimelineModule(
    items = timelineModule.items.map(moduleItemMarshaller(_, timelineModule.entryIdentifier)),
    displayType = moduleDisplayTypeMarshaller(timelineModule.displayType),
    header = timelineModule.header.map(moduleHeaderMarshaller(_)),
    footer = timelineModule.footer.map(moduleFooterMarshaller(_)),
    clientEventInfo = timelineModule.clientEventInfo.map(clientEventInfoMarshaller(_)),
    feedbackInfo = timelineModule.feedbackActionInfo.map(feedbackInfoMarshaller(_)),
    metadata = timelineModule.metadata.map(moduleMetadataMarshaller(_)),
    showMoreBehavior = timelineModule.showMoreBehavior.map(moduleShowMoreBehaviorMarshaller(_))
  )
}
