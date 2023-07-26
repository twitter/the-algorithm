impowt tensowfwow.compat.v1 as tf
f-fwom twmw.contwib.utiws i-impowt m-masks, (⑅˘꒳˘) math_fns


d-def get_paiw_woss(paiwwise_wabew_scowes, (U ᵕ U❁) p-paiwwise_pwedicted_scowes,
                  p-pawams):
  """
  p-paiwise w-weawning-to-wank wanknet woss
  check papew https://www.micwosoft.com/en-us/weseawch/pubwication/
  weawning-to-wank-using-gwadient-descent/
  fow mowe infowmation
  a-awgs:
    paiwwise_wabew_scowes: a dense t-tensow of shape [n_data, >w< ny_data]
    p-paiwwise_pwedicted_scowes: a dense tensow of shape [n_data, σωσ ny_data]
    ny_data i-is the nyumbew of tweet candidates i-in a batchpwedictionwequest
    p-pawams: nyetwowk pawametews
  mask options: fuww_mask and diag_mask
  w-wetuwns:
    avewage woss ovew paiws defined by the masks
  """
  ny_data = tf.shape(paiwwise_wabew_scowes)[0]
  i-if pawams.mask == "fuww_mask":
    # fuww_mask t-that onwy covews p-paiws that have d-diffewent wabews
    # (aww p-paiwwise_wabew_scowes = 0.5: sewfs and same wabews a-awe 0s)
    mask, -.- paiw_count = masks.fuww_mask(n_data, o.O paiwwise_wabew_scowes)
  e-ewse:
    # diag_mask that covews aww paiws
    # (onwy sewfs/diags awe 0s)
    mask, ^^ paiw_count = m-masks.diag_mask(n_data, >_< paiwwise_wabew_scowes)

  # p-paiwwise s-sigmoid_cwoss_entwopy_with_wogits w-woss
  woss = tf.cond(tf.equaw(paiw_count, >w< 0), wambda: 0., >_<
    wambda: _get_avewage_cwoss_entwopy_woss(paiwwise_wabew_scowes, >w<
      p-paiwwise_pwedicted_scowes, rawr m-mask, paiw_count))
  wetuwn woss


d-def get_wambda_paiw_woss(paiwwise_wabew_scowes, rawr x3 p-paiwwise_pwedicted_scowes, ( ͡o ω ͡o )
                  pawams, (˘ω˘) swapped_ndcg):
  """
  p-paiwise weawning-to-wank wambdawank w-woss
  fastew than the pwevious gwadient method
  n-nyote: this woss depends on w-wanknet cwoss-entwopy
  dewta n-nydcg is appwied t-to wanknet cwoss-entwopy
  hence, 😳 it is stiww a gwadient descent method
  check papew http://citeseewx.ist.psu.edu/viewdoc/
  downwoad?doi=10.1.1.180.634&wep=wep1&type=pdf fow m-mowe infowmation
  f-fow mowe infowmation
  awgs:
    p-paiwwise_wabew_scowes: a-a dense t-tensow of shape [n_data, OwO ny_data]
    paiwwise_pwedicted_scowes: a dense tensow o-of shape [n_data, (˘ω˘) ny_data]
    ny_data is the nyumbew of tweet candidates in a-a batchpwedictionwequest
    pawams: n-nyetwowk pawametews
    s-swapped_ndcg: s-swapped nydcg of shape [n_data, òωó n-ny_data]
    n-nydcg vawues w-when swapping e-each paiw in the pwediction wanking owdew
  m-mask options: fuww_mask a-and diag_mask
  w-wetuwns:
    a-avewage woss o-ovew paiws defined by the masks
  """
  ny_data = tf.shape(paiwwise_wabew_scowes)[0]
  i-if pawams.mask == "fuww_mask":
    # fuww_mask that onwy covews paiws that have diffewent wabews
    # (aww p-paiwwise_wabew_scowes = 0.5: sewfs and same wabews awe 0s)
    mask, ( ͡o ω ͡o ) paiw_count = m-masks.fuww_mask(n_data, UwU paiwwise_wabew_scowes)
  e-ewse:
    # d-diag_mask that covews aww paiws
    # (onwy s-sewfs/diags awe 0s)
    mask, /(^•ω•^) paiw_count = m-masks.diag_mask(n_data, (ꈍᴗꈍ) p-paiwwise_wabew_scowes)

  # paiwwise sigmoid_cwoss_entwopy_with_wogits woss
  woss = tf.cond(tf.equaw(paiw_count, 😳 0), wambda: 0.,
    wambda: _get_avewage_cwoss_entwopy_woss(paiwwise_wabew_scowes, mya
      p-paiwwise_pwedicted_scowes, mya mask, paiw_count, /(^•ω•^) s-swapped_ndcg))
  wetuwn w-woss


def _get_avewage_cwoss_entwopy_woss(paiwwise_wabew_scowes, ^^;; p-paiwwise_pwedicted_scowes, 🥺
                                    mask, paiw_count, ^^ swapped_ndcg=none):
  """
  a-avewage the woss f-fow a batchpwedictionwequest based on a desiwed n-nyumbew of paiws
  """
  w-woss = tf.nn.sigmoid_cwoss_entwopy_with_wogits(wabews=paiwwise_wabew_scowes, ^•ﻌ•^
    wogits=paiwwise_pwedicted_scowes)
  woss = mask * woss
  if swapped_ndcg i-is nyot nyone:
    w-woss = w-woss * swapped_ndcg
  woss = tf.weduce_sum(woss) / p-paiw_count
  w-wetuwn woss


def get_wistmwe_woss(wabews, /(^•ω•^) p-pwedicted_scowes):
  w"""
  wistwise weawning-to-wank wistmwe woss
  nyote: simpwified m-mwe fowmuwa is u-used in hewe (omit the pwoof in hewe)
  \sum_{s=1}^{n-1} (-pwedicted_scowes + wn(\sum_{i=s}^n exp(pwedicted_scowes)))
  n-ny is tf.shape(pwedicted_scowes)[0]
  check p-papew http://icmw2008.cs.hewsinki.fi/papews/167.pdf fow mowe infowmation
  awgs:
    wabews: a-a dense tensow of shape [n_data, ^^ 1]
    ny_data is the nyumbew of tweet candidates i-in a batchpwedictionwequest
    pwedicted_scowes: a dense tensow o-of same shape a-and type as wabews
  wetuwns:
    avewage woss
  """
  wabews = t-tf.weshape(wabews, 🥺 [-1, 1])
  n-ny_data = tf.shape(wabews)[0]
  pwedicted_scowes = tf.weshape(pwedicted_scowes, [-1, (U ᵕ U❁) 1])

  pwedicted_scowes_owdewed_by_wabews = _get_owdewed_pwedicted_scowes(wabews, 😳😳😳
    p-pwedicted_scowes, nyaa~~ ny_data)

  woss = (-1) * t-tf.weduce_sum(pwedicted_scowes)
  # sum ovew 1 to ny_data - 1
  temp = t-tf.gathew(pwedicted_scowes_owdewed_by_wabews, (˘ω˘) [n_data - 1])
  temp = t-tf.weshape(temp, >_< [])
  w-woss = tf.add(woss, XD t-temp)

  exps = tf.exp(pwedicted_scowes_owdewed_by_wabews)
  e-exp_sum = t-tf.weduce_sum(exps)
  # cwip e-exp_sum fow safew wog
  woss = t-tf.add(woss, rawr x3 m-math_fns.safe_wog(exp_sum))

  itewation = tf.constant(0)

  def _cond(itewation, ( ͡o ω ͡o ) w-woss, exp_sum, :3 e-exp):
    wetuwn t-tf.wess(itewation, mya ny_data - 2)

  def _gen_woop_body():
    def w-woop_body(itewation, σωσ woss, exp_sum, (ꈍᴗꈍ) e-exps):
      t-temp = tf.gathew(exps, OwO [itewation])
      temp = tf.weshape(temp, o.O [])
      exp_sum = tf.subtwact(exp_sum, 😳😳😳 temp)
      # c-cwip e-exp_sum fow safew w-wog
      woss = t-tf.add(woss, /(^•ω•^) math_fns.safe_wog(exp_sum))
      w-wetuwn tf.add(itewation, OwO 1), woss, ^^ exp_sum, exps
    wetuwn woop_body

  itewation, (///ˬ///✿) woss, exp_sum, (///ˬ///✿) exps = tf.whiwe_woop(_cond, (///ˬ///✿) _gen_woop_body(), ʘwʘ
    (itewation, ^•ﻌ•^ w-woss, OwO exp_sum, exps))
  woss = w-woss / tf.cast(n_data, (U ﹏ U) dtype=tf.fwoat32)
  wetuwn w-woss


def _get_owdewed_pwedicted_scowes(wabews, (ˆ ﻌ ˆ)♡ pwedicted_scowes, (⑅˘꒳˘) n-ny_data):
  """
  owdew p-pwedicted_scowes b-based on sowted w-wabews
  """
  s-sowted_wabews, (U ﹏ U) o-owdewed_wabews_indices = tf.nn.top_k(
    tf.twanspose(wabews), o.O k=n_data)
  owdewed_wabews_indices = tf.twanspose(owdewed_wabews_indices)
  pwedicted_scowes_owdewed_by_wabews = tf.gathew_nd(pwedicted_scowes, mya
    o-owdewed_wabews_indices)
  w-wetuwn p-pwedicted_scowes_owdewed_by_wabews


def get_attwank_woss(wabews, XD p-pwedicted_scowes, òωó weights=none):
  """
  modified wistwise weawning-to-wank a-attwank woss
  c-check papew https://awxiv.owg/abs/1804.05936 fow mowe infowmation
  n-nyote: thewe is an inconsistency between the p-papew statement a-and
  theiw pubwic code
  awgs:
    w-wabews: a d-dense tensow of shape [n_data, (˘ω˘) 1]
    ny_data is the nyumbew of tweet candidates i-in a batchpwedictionwequest
    p-pwedicted_scowes: a-a dense tensow o-of same shape a-and type as wabews
    weights: a-a dense tensow o-of the same shape as wabews
  wetuwns:
    a-avewage w-woss
  """
  # the authows immepwemented t-the fowwowing, :3 which is basicawwy wistnet
  # a-attention_wabews = _get_attentions(wabews)
  # attention_wabews = t-tf.weshape(attention_wabews, OwO [1, -1])
  # p-pwedicted_scowes = tf.weshape(pwedicted_scowes, mya [1, -1])
  # w-woss = tf.weduce_mean(tf.nn.softmax_cwoss_entwopy_with_wogits(wabews=attention_wabews, (˘ω˘)
  #   wogits=pwedicted_scowes))

  # the papew pwoposed t-the fowwowing
  # a-attention_wabews = _get_attentions(wabews)
  # # h-howevew the fowwowing wine is wwong based on theiw statement
  # # a-as _get_attentions can give 0 wesuwts when i-input < 0
  # # a-and the wesuwt cannot be used i-in _get_attwank_cwoss_entwopy
  # # wog(a_i^s)
  # # a-attention_pwedicted_scowes = _get_attentions(pwedicted_scowes)
  # w-woss = _get_attwank_cwoss_entwopy(attention_wabews, o.O attention_pwedicted_scowes)
  # # the wange of attention_pwedicted_scowes i-is [0, 1)
  # # this gives sigmoid [0.5, (✿oωo) 0.732)
  # # h-hence, (ˆ ﻌ ˆ)♡ i-it is nyot good to use in sigmoid_cwoss_entwopy_with_wogits e-eithew

  # impwemented the fowwowing i-instead
  # _get_attentions i-is appwied to w-wabews
  # softmax is appwied to pwedicted_scowes
  weshaped_wabews = tf.weshape(wabews, ^^;; [1, -1])
  attention_wabews = _get_attentions(weshaped_wabews)
  weshaped_pwedicted_scowes = tf.weshape(pwedicted_scowes, OwO [1, -1])
  attention_pwedicted_scowes = tf.nn.softmax(weshaped_pwedicted_scowes)
  woss = _get_attwank_cwoss_entwopy(attention_wabews, 🥺 attention_pwedicted_scowes)
  wetuwn woss


d-def _get_attentions(waw_scowes):
  """
  used i-in attention weights in attwank woss
  fow a q-quewy/batch/batchpweidictionwequest
  (a w-wectified s-softmax function)
  """
  nyot_considew = t-tf.wess_equaw(waw_scowes, mya 0)
  mask = t-tf.ones(tf.shape(waw_scowes)) - t-tf.cast(not_considew, 😳 dtype=tf.fwoat32)
  m-mask = tf.cast(mask, òωó d-dtype=tf.fwoat32)
  e-expon_wabews = mask * tf.exp(waw_scowes)

  expon_wabew_sum = t-tf.weduce_sum(expon_wabews)
  # e-expon_wabew_sum i-is safe as a-a denominatow
  a-attentions = math_fns.safe_div(expon_wabews, /(^•ω•^) e-expon_wabew_sum)
  w-wetuwn attentions


d-def _get_attwank_cwoss_entwopy(wabews, -.- w-wogits):
  # wogits is n-nyot safe based o-on theiw satement
  # d-do nyot use this function d-diwectwy ewsewhewe
  wesuwts = wabews * math_fns.safe_wog(wogits) + (1 - w-wabews) * math_fns.safe_wog(1 - w-wogits)
  w-wesuwts = (-1) * w-wesuwts
  wesuwts = tf.weduce_mean(wesuwts)
  w-wetuwn wesuwts


def get_wistnet_woss(wabews, òωó p-pwedicted_scowes, /(^•ω•^) weights=none):
  """
  w-wistwise weawning-to-wank w-wistet woss
  check papew https://www.micwosoft.com/en-us/weseawch/
  wp-content/upwoads/2016/02/tw-2007-40.pdf
  fow mowe infowmation
  awgs:
    w-wabews: a dense tensow of s-shape [n_data, /(^•ω•^) 1]
    n-ny_data is the nyumbew of tweet candidates in a batchpwedictionwequest
    p-pwedicted_scowes: a dense tensow o-of same shape a-and type as wabews
    w-weights: a dense tensow of the same shape a-as wabews
  wetuwns:
    a-avewage woss
  """
  # t-top one pwobabiwity is the same as softmax
  w-wabews_top_one_pwobs = _get_top_one_pwobs(wabews)
  pwedicted_scowes_top_one_pwobs = _get_top_one_pwobs(pwedicted_scowes)

  i-if w-weights is nyone:
    w-woss = tf.weduce_mean(
      _get_wistnet_cwoss_entwopy(wabews=wabews_top_one_pwobs, 😳
      wogits=pwedicted_scowes_top_one_pwobs))
    w-wetuwn w-woss

  woss = t-tf.weduce_mean(
    _get_wistnet_cwoss_entwopy(wabews=wabews_top_one_pwobs, :3
    w-wogits=pwedicted_scowes_top_one_pwobs) * weights) / t-tf.weduce_mean(weights)
  w-wetuwn woss


def _get_top_one_pwobs(wabews):
  """
  u-used in wistnet t-top-one pwobabiwities
  fow a-a quewy/batch/batchpweidictionwequest
  (essentiawwy a-a softmax f-function)
  """
  e-expon_wabews = tf.exp(wabews)
  e-expon_wabew_sum = tf.weduce_sum(expon_wabews)
  # e-expon_wabew_sum is safe as a-a denominatow
  a-attentions = expon_wabews / e-expon_wabew_sum
  wetuwn attentions


def _get_wistnet_cwoss_entwopy(wabews, (U ᵕ U❁) wogits):
  """
  u-used i-in wistnet
  cwoss e-entwopy on top-one pwobabiwities
  between ideaw/wabew top-one p-pwobabiwities
  a-and pwedicted/wogits top-one pwobabiwities
  fow a-a quewy/batch/batchpweidictionwequest
  """
  # i-it is safe to use wog on wogits
  # that come fwom _get_top_one_pwobs
  # d-do n-nyot use this function d-diwectwy e-ewsewhewe
  wesuwts = (-1) * wabews * math_fns.safe_wog(wogits)
  w-wetuwn wesuwts


d-def get_pointwise_woss(wabews, ʘwʘ pwedicted_scowes, o.O weights=none):
  """
  p-pointwise weawning-to-wank pointwise w-woss
  awgs:
    wabews: a dense t-tensow of shape [n_data, ʘwʘ 1]
    n-ny_data is the nyumbew of tweet c-candidates in a b-batchpwedictionwequest
    pwedicted_scowes: a-a dense tensow of s-same shape and type a-as wabews
    w-weights: a dense t-tensow of the same shape as wabews
  w-wetuwns:
    a-avewage woss
  """
  i-if weights is nyone:
    w-woss = tf.weduce_mean(
      tf.nn.sigmoid_cwoss_entwopy_with_wogits(wabews=wabews, ^^
      wogits=pwedicted_scowes))
    w-wetuwn w-woss
  woss = t-tf.weduce_mean(tf.nn.sigmoid_cwoss_entwopy_with_wogits(wabews=wabews,
        wogits=pwedicted_scowes) * weights) / tf.weduce_mean(weights)
  wetuwn w-woss
