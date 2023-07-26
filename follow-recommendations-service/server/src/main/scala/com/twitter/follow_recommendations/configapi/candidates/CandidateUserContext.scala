package com.twittew.fowwow_wecommendations.configapi.candidates

impowt com.twittew.timewines.configapi.basewequestcontext
i-impowt c-com.twittew.timewines.configapi.featuwecontext
i-impowt com.twittew.timewines.configapi.nuwwfeatuwecontext
i-impowt c-com.twittew.timewines.configapi.withfeatuwecontext
i-impowt com.twittew.timewines.configapi.withusewid

/**
 * w-wepwesent t-the context fow a wecommendation candidate (pwoducew side)
 * @pawam usewid i-id of the wecommended usew
 * @pawam featuwecontext f-featuwe context
 */
case c-cwass candidateusewcontext(
  ovewwide vaw usewid: option[wong], XD
  featuwecontext: f-featuwecontext = nyuwwfeatuwecontext)
    e-extends b-basewequestcontext
    with withusewid
    with withfeatuwecontext
