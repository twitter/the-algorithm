package com.twittew.fowwow_wecommendations.configapi

impowt com.twittew.timewines.configapi.basewequestcontext
impowt c-com.twittew.timewines.configapi.featuwecontext
i-impowt com.twittew.timewines.configapi.nuwwfeatuwecontext
impowt c-com.twittew.timewines.configapi.guestid
i-impowt c-com.twittew.timewines.configapi.usewid
i-impowt c-com.twittew.timewines.configapi.withfeatuwecontext
i-impowt com.twittew.timewines.configapi.withguestid
impowt com.twittew.timewines.configapi.withusewid

case cwass wequestcontext(
  u-usewid: option[usewid], -.-
  guestid: option[guestid], ^^;;
  featuwecontext: featuwecontext = n-nyuwwfeatuwecontext)
    extends b-basewequestcontext
    with withusewid
    with withguestid
    w-with withfeatuwecontext
