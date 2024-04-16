package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle

sealed tra  Art cleSeedType

/**
 * Seed UTEG w h a user's follow ng l st (1st degree network)
 */
case object Follow ngL stSeed extends Art cleSeedType

/**
 * Seed UTEG w h a user's fr ends of fr ends (follow graph + 1) l st
 */
case object Fr endsOfFr endsSeed extends Art cleSeedType

/**
 * Seed UTEG w h a g ven l sts'  mbers
 */
case object L st dSeed extends Art cleSeedType
