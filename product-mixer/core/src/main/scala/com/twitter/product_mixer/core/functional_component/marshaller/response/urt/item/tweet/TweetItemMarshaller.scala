package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.t et

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.graphql.contextual_ref.ContextualT etRefMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.conversat on_annotat on.Conversat onAnnotat onMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.forward_p vot.ForwardP votMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.tombstone.Tombstone nfoMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Soc alContextMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted.Preroll tadataMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted.Promoted tadataMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.BadgeMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T et emMarshaller @ nject() (
  t etD splayTypeMarshaller: T etD splayTypeMarshaller,
  soc alContextMarshaller: Soc alContextMarshaller,
  t etH ghl ghtsMarshaller: T etH ghl ghtsMarshaller,
  tombstone nfoMarshaller: Tombstone nfoMarshaller,
  t  l nesScore nfoMarshaller: T  l nesScore nfoMarshaller,
  forwardP votMarshaller: ForwardP votMarshaller,
  promoted tadataMarshaller: Promoted tadataMarshaller,
  conversat onAnnotat onMarshaller: Conversat onAnnotat onMarshaller,
  contextualT etRefMarshaller: ContextualT etRefMarshaller,
  preroll tadataMarshaller: Preroll tadataMarshaller,
  badgeMarshaller: BadgeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(t et em: T et em): urt.T  l ne emContent.T et = urt.T  l ne emContent.T et(
    urt.T et(
       d = t et em. d,
      d splayType = t etD splayTypeMarshaller(t et em.d splayType),
      soc alContext = t et em.soc alContext.map(soc alContextMarshaller(_)),
      h ghl ghts = t et em.h ghl ghts.map(t etH ghl ghtsMarshaller(_)),
       nnerTombstone nfo = t et em. nnerTombstone nfo.map(tombstone nfoMarshaller(_)),
      t  l nesScore nfo = t et em.t  l nesScore nfo.map(t  l nesScore nfoMarshaller(_)),
      hasModeratedRepl es = t et em.hasModeratedRepl es,
      forwardP vot = t et em.forwardP vot.map(forwardP votMarshaller(_)),
       nnerForwardP vot = t et em. nnerForwardP vot.map(forwardP votMarshaller(_)),
      promoted tadata = t et em.promoted tadata.map(promoted tadataMarshaller(_)),
      conversat onAnnotat on =
        t et em.conversat onAnnotat on.map(conversat onAnnotat onMarshaller(_)),
      contextualT etRef = t et em.contextualT etRef.map(contextualT etRefMarshaller(_)),
      preroll tadata = t et em.preroll tadata.map(preroll tadataMarshaller(_)),
      replyBadge = t et em.replyBadge.map(badgeMarshaller(_)),
      dest nat on = t et em.dest nat on.map(urlMarshaller(_))
    )
  )
}
