package com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.suggestion

/**
 * wepwesents t-the diffewent t-types of spewwing s-suggestion i-items. 🥺
 *
 * u-uwt api wefewence: h-https://docbiwd.twittew.biz/unified_wich_timewines_uwt/gen/com/twittew/timewines/wendew/thwiftscawa/spewwingactiontype.htmw
 */
s-seawed twait s-spewwingactiontype

/**
 * used when the owiginaw quewy is wepwaced compweted by a-anothew quewy in the backend. mya
 * cwients use the t-text 'seawching instead fow …' t-to dispway this suggestion. 🥺
 */
case object wepwacespewwingactiontype extends s-spewwingactiontype

/**
 * used w-when the owiginaw q-quewy is expanded by a suggestion when pewfowming the seawch. >_<
 * cwients use t-the text 'incwuding wesuwts fow …' to dispway this suggestion. >_<
 */
case object e-expandspewwingactiontype extends s-spewwingactiontype

/**
 * u-used w-when the seawch q-quewy is nyot changed and a suggestion is dispwayed a-as an awtewnative quewy. (⑅˘꒳˘)
 * cwients use the t-text 'did you mean … ?' to dispway the suggestion. /(^•ω•^)
 */
case object suggestspewwingactiontype extends spewwingactiontype
