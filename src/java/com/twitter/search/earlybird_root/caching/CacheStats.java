package com.tw ter.search.earlyb rd_root.cach ng;

 mport com.tw ter.search.common. tr cs.SearchRateCounter;

publ c f nal class Cac Stats {
  publ c stat c f nal SearchRateCounter REQUEST_FA LED_COUNTER =
      SearchRateCounter.export(" mcac _request_fa led");
  publ c stat c f nal SearchRateCounter REQUEST_T MEOUT_COUNTER =
      SearchRateCounter.export(" mcac _request_t  out");

  pr vate Cac Stats() {
  }
}
