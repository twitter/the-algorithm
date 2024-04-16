package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.suggest on

/**
 * Represents t  d fferent types of Spell ng Suggest on  ems.
 *
 * URT AP  Reference: https://docb rd.tw ter.b z/un f ed_r ch_t  l nes_urt/gen/com/tw ter/t  l nes/render/thr ftscala/Spell ngAct onType.html
 */
sealed tra  Spell ngAct onType

/**
 * Used w n t  or g nal query  s replaced completed by anot r query  n t  backend.
 * Cl ents use t  text 'Search ng  nstead for …' to d splay t  suggest on.
 */
case object ReplaceSpell ngAct onType extends Spell ngAct onType

/**
 * Used w n t  or g nal query  s expanded by a suggest on w n perform ng t  search.
 * Cl ents use t  text ' nclud ng results for …' to d splay t  suggest on.
 */
case object ExpandSpell ngAct onType extends Spell ngAct onType

/**
 * Used w n t  search query  s not changed and a suggest on  s d splayed as an alternat ve query.
 * Cl ents use t  text 'D d    an … ?' to d splay t  suggest on.
 */
case object SuggestSpell ngAct onType extends Spell ngAct onType
