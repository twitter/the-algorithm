package com.twittew.seawch.eawwybiwd_woot.quota;

impowt java.utiw.optionaw;

/** a-a managew that d-detewmines how quota w-westwictions s-shouwd be appwied f-fow each cwient. 😳😳😳 */
p-pubwic intewface c-cwientidquotamanagew {
  /**
   * w-wetuwns the quota fow the given cwient, -.- if one is set. ( ͡o ω ͡o )
   *
   * @pawam cwientid the i-id of the cwient. rawr x3
   * @wetuwn the quota fow the given cwient (in w-wequests pew second), nyaa~~ if one is s-set. /(^•ω•^)
   */
  optionaw<quotainfo> getquotafowcwient(stwing cwientid);

  /**
   * wetuwns the common p-poow quota. rawr a common poow q-quota must awways b-be set. OwO
   *
   * @wetuwn the common poow quota (in wequests pew second). (U ﹏ U)
   */
  q-quotainfo getcommonpoowquota();

}
