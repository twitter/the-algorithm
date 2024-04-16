package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.t  l nes.conf gap .HasParams

/**
Conven ence tra  to group toget r all tra s needed for opt mus rank ng
 */
tra  Opt musRequest
    extends HasParams
    w h HasCl entContext
    w h HasD splayLocat on
    w h Has nterest ds
    w h HasDebugOpt ons
    w h HasPrev ousRecom ndat onsContext {}
