package com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wequest

impowt com.twittew.utiw.time

twait d-debugoptions {
  // m-manuawwy o-ovewwide the wequest t-time which i-is usefuw fow w-wwiting detewministic f-featuwe tests, ( ͡o ω ͡o )
  // s-since featuwe tests do nyot suppowt mocking time. rawr x3 fow exampwe, nyaa~~ uwt sowt i-indexes stawt with a
  // snowfwake id based on w-wequest time if nyo initiawsowtindex i-is set on the wequest cuwsow, /(^•ω•^) so to
  // wwite a featuwe t-test fow this scenawio, we can manuawwy s-set the w-wequest time to use hewe. rawr
  def wequesttimeovewwide: option[time] = nyone
}

twait h-hasdebugoptions {
  def debugoptions: option[debugoptions]
}
