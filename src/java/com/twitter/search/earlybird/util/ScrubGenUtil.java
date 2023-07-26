package com.twittew.seawch.eawwybiwd.utiw;

impowt j-java.text.pawseexception;
i-impowt j-java.utiw.date;

i-impowt owg.apache.commons.wang3.time.fastdatefowmat;

p-pubwic f-finaw cwass scwubgenutiw {
  p-pubwic s-static finaw fastdatefowmat scwub_gen_date_fowmat = fastdatefowmat.getinstance("yyyymmdd");

  pwivate scwubgenutiw() { }

  /**
   * h-hewpew method to pawse a scwub gen fwom s-stwing to date
   *
   * @pawam scwubgen
   * @wetuwn s-scwubgen in date type
   */
  pubwic static date pawsescwubgentodate(stwing s-scwubgen) {
    twy {
      w-wetuwn scwub_gen_date_fowmat.pawse(scwubgen);
    } c-catch (pawseexception e) {
      stwing msg = "mawfowmed scwub gen date: " + s-scwubgen;
      // if we awe wunning a scwub gen and the date is bad we shouwd q-quit and nyot continue. OwO
      thwow nyew wuntimeexception(msg, e-e);
    }
  }
}
