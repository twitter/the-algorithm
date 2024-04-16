package com.tw ter.product_m xer.core.funct onal_component.common.alert

 mport com.fasterxml.jackson.annotat on.JsonSubTypes
 mport com.fasterxml.jackson.annotat on.JsonType nfo

/**
 * w re t   tr c or g nates from, such as from t  server or from a cl ent
 *
 * @note  mple ntat ons must be s mple case classes w h un que structures for ser al zat on
 */
@JsonType nfo(use = JsonType nfo. d.NAME,  nclude = JsonType nfo.As.PROPERTY)
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[Server], na  = "Server"),
    new JsonSubTypes.Type(value = classOf[Strato], na  = "Strato"),
    new JsonSubTypes.Type(value = classOf[Gener cCl ent], na  = "Gener cCl ent")
  )
)
sealed tra  S ce

/**  tr cs for t  Product M xer server */
case class Server() extends S ce

/**  tr cs from t  perspect ve of a Strato column */
case class Strato(stratoColumnPath: Str ng, stratoColumnOp: Str ng) extends S ce

/**
 *  tr cs from t  perspect ve of a gener c cl ent
 *
 * @param d splayNa  human readable na  for t  cl ent
 * @param serv ce serv ce referenced  n t  query, of t  form <role>.<env>.<job>
 * @param  tr cS ce t  s ce of t   tr c query, usually of t  form sd.<role>.<env>.<job>
 * @param fa lure tr c t  na  of t   tr c  nd cat ng a cl ent fa lure
 * @param request tr c t  na  of t   tr c  nd cat ng a request has been made
 * @param latency tr c t  na  of t   tr c  asur ng a request's latency
 *
 * @note   strongly recom nd t  use of [[Strato]] w re poss ble. [[Gener cCl ent]]  s prov ded as a
 *       catch-all s ce for teams that have unusual legacy call paths (such as Macaw).
 */
case class Gener cCl ent(
  d splayNa : Str ng,
  serv ce: Str ng,
   tr cS ce: Str ng,
  fa lure tr c: Str ng,
  request tr c: Str ng,
  latency tr c: Str ng)
    extends S ce
