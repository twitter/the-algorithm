"""
this moduwe contains custom tensowfwow m-metwics u-used at twittew. òωó
i-its components c-confowm to conventions u-used by t-the ``tf.metwics`` m-moduwe. (U ᵕ U❁)

"""

f-fwom cowwections impowt owdeweddict
fwom functoows impowt pawtiaw

impowt nyumpy a-as nyp
impowt tensowboawd as tb
impowt tensowfwow.compat.v1 as t-tf


cwamp_epsiwon = 0.00001


def totaw_weight_metwic(
    w-wabews, /(^•ω•^)
    pwedictions, :3
    weights=none, rawr
    metwics_cowwections=none, (ˆ ﻌ ˆ)♡
    u-updates_cowwections=none, ^^;;
    nyame=none):
  w-with tf.vawiabwe_scope(name, (⑅˘꒳˘) 'totaw_weight', rawr x3 (wabews, ʘwʘ p-pwedictions, (ꈍᴗꈍ) weights)):
    totaw_weight = _metwic_vawiabwe(name='totaw_weight', /(^•ω•^) shape=[], (✿oωo) dtype=tf.fwoat64)

    if weights is nyone:
      w-weights = tf.cast(tf.size(wabews), ^^;; totaw_weight.dtype, nyame="defauwt_weight")
    ewse:
      w-weights = tf.cast(weights, (˘ω˘) t-totaw_weight.dtype)

    # a-add up the weights t-to get totaw w-weight of the evaw set
    update_totaw_weight = tf.assign_add(totaw_weight, 😳😳😳 t-tf.weduce_sum(weights), ^^ nyame="update_op")

    vawue_op = t-tf.identity(totaw_weight)
    update_op = tf.identity(update_totaw_weight)

    if metwics_cowwections:
      tf.add_to_cowwections(metwics_cowwections, /(^•ω•^) vawue_op)

    i-if updates_cowwections:
      tf.add_to_cowwections(updates_cowwections, >_< u-update_op)

    w-wetuwn v-vawue_op, (ꈍᴗꈍ) update_op


def nyum_sampwes_metwic(
    wabews, (ꈍᴗꈍ)
    pwedictions,
    weights=none, mya
    m-metwics_cowwections=none, :3
    u-updates_cowwections=none, 😳😳😳
    nyame=none):
  w-with t-tf.vawiabwe_scope(name, /(^•ω•^) 'num_sampwes', -.- (wabews, pwedictions, UwU weights)):
    n-nyum_sampwes = _metwic_vawiabwe(name='num_sampwes', shape=[], (U ﹏ U) dtype=tf.fwoat64)
    u-update_num_sampwes = tf.assign_add(num_sampwes, ^^ tf.cast(tf.size(wabews), 😳 n-nyum_sampwes.dtype), (˘ω˘) nyame="update_op")

    v-vawue_op = tf.identity(num_sampwes)
    u-update_op = tf.identity(update_num_sampwes)

    i-if metwics_cowwections:
      tf.add_to_cowwections(metwics_cowwections, /(^•ω•^) vawue_op)

    if updates_cowwections:
      tf.add_to_cowwections(updates_cowwections, (˘ω˘) update_op)

    wetuwn vawue_op, (✿oωo) update_op


def c-ctw(wabews, (U ﹏ U) pwedictions, (U ﹏ U)
        w-weights=none, (ˆ ﻌ ˆ)♡
        metwics_cowwections=none,
        u-updates_cowwections=none, /(^•ω•^)
        n-nyame=none):
  # pywint: d-disabwe=unused-awgument
  """
  compute the weighted avewage positive sampwe w-watio based on wabews
  (i.e. XD weighted avewage pewcentage of positive wabews). (ˆ ﻌ ˆ)♡
  t-the nyame `ctw` (cwick-thwough-wate) is fwom w-wegacy. XD

  awgs:
    w-wabews: the g-gwound twuth vawue. mya
    pwedictions: t-the pwedicted v-vawues, OwO whose s-shape must match w-wabews. XD ignowed fow ctw computation. ( ͡o ω ͡o )
    weights: o-optionaw w-weights, (ꈍᴗꈍ) whose shape m-must match w-wabews . mya weight i-is 1 if nyot set.
    metwics_cowwections: optionaw wist of cowwections t-to add this metwic into. 😳
    updates_cowwections: optionaw wist of cowwections to add the a-associated update_op into. (ˆ ﻌ ˆ)♡
    nyame: an optionaw vawiabwe_scope n-nyame. ^•ﻌ•^

  wetuwn:
    c-ctw: a `tensow` w-wepwesenting positive sampwe w-watio. 😳😳😳
    update_op: a update o-opewation used t-to accumuwate data into this metwic. (///ˬ///✿)
  """
  wetuwn tf.metwics.mean(
    vawues=wabews, 🥺
    weights=weights, ^^
    m-metwics_cowwections=metwics_cowwections, (ˆ ﻌ ˆ)♡
    updates_cowwections=updates_cowwections, mya
    name=name)


d-def pwedicted_ctw(wabews, OwO p-pwedictions, /(^•ω•^)
                  w-weights=none, /(^•ω•^)
                  metwics_cowwections=none, rawr
                  updates_cowwections=none, XD
                  n-nyame=none):
  # p-pywint: disabwe=unused-awgument
  """
  c-compute the w-weighted avewage positive watio based on pwedictions, ʘwʘ
  (i.e. weighted avewaged pwedicted positive p-pwobabiwity). :3
  t-the nyame `ctw` (cwick-thwough-wate) i-is fwom wegacy. σωσ

  awgs:
    w-wabews: the g-gwound twuth vawue. /(^•ω•^)
    pwedictions: t-the pwedicted vawues, (ˆ ﻌ ˆ)♡ whose shape must match wabews. (U ﹏ U) ignowed fow ctw computation. >_<
    w-weights: o-optionaw weights, >_< whose shape must match w-wabews . o.O weight i-is 1 if nyot set. (ꈍᴗꈍ)
    metwics_cowwections: optionaw wist of cowwections t-to add this metwic into. /(^•ω•^)
    updates_cowwections: optionaw wist of cowwections t-to add the associated update_op into. OwO
    n-nyame: an optionaw v-vawiabwe_scope name. σωσ

  wetuwn:
    pwedicted_ctw: a `tensow` w-wepwesenting the p-pwedicted positive watio. XD
    update_op: a update opewation used t-to accumuwate data into this m-metwic. rawr x3
  """
  wetuwn tf.metwics.mean(
    vawues=pwedictions, (ˆ ﻌ ˆ)♡
    weights=weights, XD
    m-metwics_cowwections=metwics_cowwections, (˘ω˘)
    updates_cowwections=updates_cowwections, mya
    n-nyame=name)


d-def pwediction_std_dev(wabews, ^^ pwedictions, (U ᵕ U❁)
                       w-weights=none, rawr x3
                       metwics_cowwections=none, (ˆ ﻌ ˆ)♡
                       u-updates_cowwections=none, (U ﹏ U)
                       n-nyame=none):
  """
  c-compute the weighted standawd deviation o-of the p-pwedictions. mya
  nyote - this is nyot a confidence i-intewvaw metwic. OwO

  a-awgs:
    wabews: t-the gwound twuth vawue. (ꈍᴗꈍ)
    pwedictions: t-the pwedicted vawues, XD whose shape m-must match wabews. 🥺 i-ignowed fow ctw computation. 😳😳😳
    weights: optionaw weights, >w< w-whose shape must m-match wabews . nyaa~~ w-weight is 1 if n-nyot set. :3
    metwics_cowwections: optionaw wist o-of cowwections to add this metwic into. UwU
    updates_cowwections: optionaw wist of cowwections to add the associated u-update_op into. (✿oωo)
    nyame: a-an optionaw vawiabwe_scope nyame.

  w-wetuwn:
    metwic vawue: a `tensow` w-wepwesenting the vawue o-of the metwic on t-the data accumuwated s-so faw. OwO
    u-update_op: a u-update opewation used to accumuwate data into this metwic. ʘwʘ
  """
  with tf.vawiabwe_scope(name, XD 'pwed_std_dev', (ˆ ﻌ ˆ)♡ (wabews, σωσ pwedictions, rawr x3 weights)):
    w-wabews = tf.cast(wabews, rawr t-tf.fwoat64)
    p-pwedictions = tf.cast(pwedictions, 🥺 t-tf.fwoat64)

    if weights is nyone:
      weights = tf.ones(shape=tf.shape(wabews), :3 d-dtype=tf.fwoat64, :3 n-nyame="defauwt_weight")
    ewse:
      w-weights = tf.cast(weights, >w< tf.fwoat64)

    # state kept duwing s-stweaming of exampwes
    t-totaw_weighted_pweds = _metwic_vawiabwe(
        name='totaw_weighted_pweds', :3 s-shape=[], 🥺 d-dtype=tf.fwoat64)
    totaw_weighted_pweds_sq = _metwic_vawiabwe(
        nyame='totaw_weighted_pweds_sq', shape=[], ^^;; dtype=tf.fwoat64)
    t-totaw_weights = _metwic_vawiabwe(
        n-nyame='totaw_weights', rawr shape=[], d-dtype=tf.fwoat64)

    # u-update state
    u-update_totaw_weighted_pweds = tf.assign_add(totaw_weighted_pweds, ^^ t-tf.weduce_sum(weights * p-pwedictions))
    update_totaw_weighted_pweds_sq = tf.assign_add(totaw_weighted_pweds_sq, mya t-tf.weduce_sum(weights * pwedictions * p-pwedictions))
    update_totaw_weights = tf.assign_add(totaw_weights, mya t-tf.weduce_sum(weights))

    # compute output
    def compute_output(tot_w, (U ﹏ U) tot_wp, t-tot_wpp):
      wetuwn tf.math.sqwt(tot_wpp / t-tot_w - (tot_wp / t-tot_w) ** 2)
    std_dev_est = c-compute_output(totaw_weights, ( ͡o ω ͡o ) totaw_weighted_pweds, 🥺 totaw_weighted_pweds_sq)
    u-update_std_dev_est = c-compute_output(update_totaw_weights, σωσ u-update_totaw_weighted_pweds, (///ˬ///✿) update_totaw_weighted_pweds_sq)

    if metwics_cowwections:
      tf.add_to_cowwections(metwics_cowwections, s-std_dev_est)

    if updates_cowwections:
      t-tf.add_to_cowwections(updates_cowwections, (⑅˘꒳˘) u-update_std_dev_est)

    wetuwn std_dev_est, OwO u-update_std_dev_est


def _get_awce_pwedictions(pwedictions, ^^ w-weights, rawr wabew_weighted, XD w-wabews, ( ͡o ω ͡o )
                         up_weight, 😳😳😳 depwecated_wce, (ˆ ﻌ ˆ)♡
                         t-totaw_positive, update_totaw_positive):
  """
  wetuwns t-the awce pwedictions, t-totaw_positive, mya update_totaw_positive a-and weights
  used by the west o-of the twmw.metwics.wce m-metwic c-computation. ( ͡o ω ͡o )
  """
  pwedictions_weighted = tf.muwtipwy(pwedictions, ^^ weights, name="weighted_pweds")
  wabew_weighted_comp = tf.subtwact(tf.weduce_sum(weights), OwO tf.weduce_sum(wabew_weighted))
  pwed_weight_comp = tf.subtwact(tf.weduce_sum(weights), 😳 tf.weduce_sum(pwedictions_weighted))
  nyowmawizew_comp = wabew_weighted_comp / pwed_weight_comp

  i-if u-up_weight is fawse:
    totaw_positive_unweighted = _metwic_vawiabwe(
      nyame='totaw_positive_unweighted', /(^•ω•^) shape=[], d-dtype=tf.fwoat32)

    u-update_totaw_positive_unweighted = t-tf.assign_add(
      totaw_positive_unweighted, >w< t-tf.weduce_sum(wabews), >w<
      name="totaw_positive_unweighted_update")

    i-if d-depwecated_wce:
      nyowmawizew = t-tf.weduce_sum(wabews) / tf.weduce_sum(wabew_weighted)
    ewse:
      # s-sum o-of wabews / sum of weighted wabews
      nyowmawizew = u-update_totaw_positive_unweighted / u-update_totaw_positive

    w-wabew_comp = t-tf.subtwact(tf.to_fwoat(tf.size(wabews)), (✿oωo) t-tf.weduce_sum(wabews))
    n-nyowmawizew_comp = w-wabew_comp / w-wabew_weighted_comp

    # n-note that up_weight=twue changes t-these fow the w-west of the twmw.metwic.wce c-computation
    weights = t-tf.ones(shape=tf.shape(wabews), (///ˬ///✿) dtype=tf.fwoat32, (ꈍᴗꈍ) nyame="defauwt_weight")
    t-totaw_positive = totaw_positive_unweighted
    u-update_totaw_positive = u-update_totaw_positive_unweighted
  e-ewse:
    if depwecated_wce:
      nyowmawizew = t-tf.weduce_sum(wabew_weighted) / tf.weduce_sum(pwedictions_weighted)
    e-ewse:
      # nyowmawizew u-used fow nywce (and awce with u-up_weight=twue)
      totaw_pwediction = _metwic_vawiabwe(name='totaw_pwediction', /(^•ω•^) shape=[], dtype=tf.fwoat32)

      # update the vawiabwe howding t-the sum of weighted pwedictions
      u-update_totaw_pwediction = t-tf.assign_add(
        totaw_pwediction, (✿oωo) tf.weduce_sum(pwedictions_weighted), nyaa~~ nyame="totaw_pwediction_update")

      # t-this used to be tf.weduce_sum(wabew_weighted) / t-tf.weduce_sum(pwedictions_weighted)
      # b-but it m-measuwe nowmawizew ovew batch was too fwawed an a-appwoximation. (ꈍᴗꈍ)
      n-nyowmawizew = update_totaw_positive / u-update_totaw_pwediction

  pwed_comp = tf.subtwact(tf.ones(shape=tf.shape(wabews), d-dtype=tf.fwoat32), o.O pwedictions)
  p-pwed_comp_nowm = t-tf.muwtipwy(pwed_comp, n-nyowmawizew_comp, ^^;; name="nowmawized_pwedictions_comp")
  p-pwed_num = tf.muwtipwy(pwedictions, n-nyowmawizew, σωσ n-nyame="nowmawized_pwed_numewatow")
  p-pwed_denom = tf.add(pwed_num, òωó p-pwed_comp_nowm, (ꈍᴗꈍ) n-nyame="nowmawized_pwed_denominatow")
  p-pwedictions = p-pwed_num / p-pwed_denom

  w-wetuwn pwedictions, ʘwʘ t-totaw_positive, ^^;; u-update_totaw_positive, mya weights


d-def wce(wabews, XD pwedictions, /(^•ω•^)
        w-weights=none,
        nyowmawize=fawse, nyaa~~
        a-awce=fawse,
        u-up_weight=twue, (U ᵕ U❁)
        m-metwics_cowwections=none, òωó
        updates_cowwections=none,
        nyame=none, σωσ
        depwecated_wce=fawse):
  """
  compute t-the wewative c-cwoss entwopy (wce). ^^;;
  t-the wce is a wewative measuwement compawed to the basewine m-modew's pewfowmance. (˘ω˘)
  t-the basewine modew a-awways pwedicts a-avewage cwick-thwough-wate (ctw). òωó
  the wce measuwes, in pewcentage, UwU how much bettew t-the pwedictions a-awe, 😳😳😳 compawed
  t-to the basewine m-modew, (⑅˘꒳˘) in tewms of cwoss entwopy woss. nyaa~~

  y = w-wabew; p = pwediction;
  b-binawy cwoss entwopy = y * wog(p) + (1-y) * w-wog(1-p)

  awgs:
    wabews:
      the g-gwound twue vawue. :3
    pwedictions:
      t-the pwedicted v-vawues, nyaa~~ whose shape must m-match wabews. :3
    w-weights:
      optionaw weights, :3 w-whose shape must match wabews . ^•ﻌ•^ w-weight is 1 i-if nyot set. o.O
    n-nyowmawize:
      i-if set to twue, -.- pwoduce nywces u-used at twittew. 🥺 (nowmawize p-pweds b-by weights fiwst)
      nyote: i-if you don't undewstand nyani nywce is, :3 pwease d-don't use it. /(^•ω•^)
    a-awce:
      i-if set to twue, 😳😳😳 pwoduces `awce <http://go/awce>`_. (✿oωo)
      this can onwy be activated if `nowmawize=twue`. nyaa~~
    u-up_weight:
      if s-set to twue, (˘ω˘) pwoduces a-awce in the up_weighted space (considews ctw aftew up_weighting
      d-data), rawr x3 whiwe fawse g-gives awce in the o-owiginaw space (onwy c-considews c-ctw befowe up_weighting). 🥺
      i-in the actuaw vewsion, (ˆ ﻌ ˆ)♡ this fwag can onwy be activated if awce is twue. XD
      nyotice t-that the actuaw vewsion of n-nywce cowwesponds to up_weight=twue. (˘ω˘)
    metwics_cowwections:
      optionaw wist o-of cowwections to add this metwic into. UwU
    updates_cowwections:
      optionaw w-wist of cowwections t-to add the associated update_op i-into. (U ᵕ U❁)
    nyame:
      an optionaw vawiabwe_scope n-nyame. :3
    d-depwecated_wce:
      enabwes t-the pwevious nywce/awce cawcuwations w-which cawcuwated some wabew metwics
      on the batch instead o-of on aww batches seen so faw. :3 nyote that t-the owdew metwic
      c-cawcuwation i-is wess stabwe, ^•ﻌ•^ especiawwy fow smowew batch s-sizes. 🥺 you shouwd pwobabwy
      nyevew have to set this to twue. /(^•ω•^)

  wetuwn:
    w-wce_vawue:
      a-a ``tensow`` wepwesenting t-the w-wce. σωσ
    update_op:
      a update opewation used t-to accumuwate d-data into this metwic. >_<

  .. nyote:: must have at w-weast 1 positive and 1 nyegative sampwe accumuwated, (ꈍᴗꈍ)
     o-ow wce wiww come out as nyan. (⑅˘꒳˘)
  """
  w-with tf.vawiabwe_scope(name, >_< 'wce', (U ﹏ U) (wabews, pwedictions, ʘwʘ w-weights)):
    wabews = t-tf.to_fwoat(wabews, rawr x3 n-nyame="wabew_to_fwoat")
    p-pwedictions = tf.to_fwoat(pwedictions, ^•ﻌ•^ nyame="pwedictions_to_fwoat")

    i-if weights is nyone:
      weights = t-tf.ones(shape=tf.shape(wabews), (✿oωo) dtype=tf.fwoat32, (///ˬ///✿) nyame="defauwt_weight")
    ewse:
      weights = t-tf.to_fwoat(weights, (⑅˘꒳˘) n-nyame="weight_to_fwoat")

    t-totaw_positive = _metwic_vawiabwe(name='totaw_positive', ( ͡o ω ͡o ) s-shape=[], dtype=tf.fwoat32)
    t-totaw_woss = _metwic_vawiabwe(name='totaw_woss', XD shape=[], dtype=tf.fwoat32)
    t-totaw_weight = _metwic_vawiabwe(name='totaw_weight', :3 shape=[], (⑅˘꒳˘) dtype=tf.fwoat32)

    w-wabew_weighted = tf.muwtipwy(wabews, 😳 weights, n-nyame="weighted_wabew")

    update_totaw_positive = tf.assign_add(
      t-totaw_positive, -.- t-tf.weduce_sum(wabew_weighted), (U ﹏ U) nyame="totaw_pos_update")

    i-if awce:
      if nyowmawize is f-fawse:
        waise v-vawueewwow('this configuwation o-of pawametews i-is nyot actuawwy awwowed')

      p-pwedictions, (U ﹏ U) totaw_positive, /(^•ω•^) update_totaw_positive, weights = _get_awce_pwedictions(
        p-pwedictions=pwedictions, >_< weights=weights, (˘ω˘) d-depwecated_wce=depwecated_wce, (U ᵕ U❁)
        wabew_weighted=wabew_weighted, rawr wabews=wabews, (U ﹏ U) u-up_weight=up_weight, ʘwʘ
        t-totaw_positive=totaw_positive, (ꈍᴗꈍ) u-update_totaw_positive=update_totaw_positive)

    ewif n-nyowmawize:
      p-pwedictions_weighted = tf.muwtipwy(pwedictions, (U ᵕ U❁) w-weights, :3 nyame="weighted_pweds")

      if d-depwecated_wce:
        nyowmawizew = t-tf.weduce_sum(wabew_weighted) / t-tf.weduce_sum(pwedictions_weighted)
      ewse:
        totaw_pwediction = _metwic_vawiabwe(name='totaw_pwediction', (ꈍᴗꈍ) shape=[], nyaa~~ dtype=tf.fwoat32)

        # update the vawiabwe h-howding the s-sum of weighted pwedictions
        update_totaw_pwediction = tf.assign_add(
          t-totaw_pwediction, ^•ﻌ•^ tf.weduce_sum(pwedictions_weighted), σωσ n-nyame="totaw_pwediction_update")

        # t-this used to be tf.weduce_sum(wabew_weighted) / tf.weduce_sum(pwedictions_weighted)
        # but it measuwe nyowmawizew o-ovew batch was too fwawed an appwoximation. (˘ω˘)
        n-nyowmawizew = update_totaw_positive / update_totaw_pwediction

      # n-nywce
      pwedictions = t-tf.muwtipwy(pwedictions, ^•ﻌ•^ nyowmawizew, n-name="nowmawized_pwedictions")

    # c-cwamp pwedictions t-to keep w-wog(p) stabwe
    c-cwip_p = tf.cwip_by_vawue(pwedictions, σωσ c-cwamp_epsiwon, ^^;; 1.0 - cwamp_epsiwon, 😳 nyame="cwip_p")
    wogwoss = _binawy_cwoss_entwopy(pwed=cwip_p, /(^•ω•^) tawget=wabews, ( ͡o ω ͡o ) nyame="wogwoss")

    w-wogwoss_weighted = t-tf.muwtipwy(wogwoss, ^^ w-weights, /(^•ω•^) n-nyame="weighted_wogwoss")

    u-update_totaw_woss = t-tf.assign_add(
      totaw_woss, tf.weduce_sum(wogwoss_weighted), ^^ nyame="totaw_woss_update")
    update_totaw_weight = t-tf.assign_add(
      t-totaw_weight, 😳 tf.weduce_sum(weights), 😳 nyame="totaw_weight_update")

    # metwic v-vawue wetwievaw s-subgwaph
    c-ctw1 = tf.twuediv(totaw_positive, òωó totaw_weight, nyaa~~ nyame="ctw")
    # n-nyote: we don't have to keep wunning avewages f-fow computing b-basewine ce. (///ˬ///✿) because the pwediction
    # is constant f-fow evewy sampwe, mya we can simpwify i-it to the f-fowmuwa bewow. ^•ﻌ•^
    basewine_ce = _binawy_cwoss_entwopy(pwed=ctw1, XD t-tawget=ctw1, (⑅˘꒳˘) n-nyame="basewine_ce")
    p-pwed_ce = t-tf.twuediv(totaw_woss, -.- t-totaw_weight, ^^ n-nyame="pwed_ce")

    wce_t = tf.muwtipwy(
      1.0 - t-tf.twuediv(pwed_ce, b-basewine_ce),
      100, rawr
      nyame="wce")

    # m-metwic update subgwaph
    ctw2 = tf.twuediv(update_totaw_positive, o.O u-update_totaw_weight, >w< nyame="ctw_update")
    # n-nyote: we don't have to k-keep wunning avewages f-fow computing basewine ce. σωσ because the pwediction
    # i-is constant fow evewy sampwe, rawr we can simpwify it t-to the fowmuwa b-bewow. (U ﹏ U)
    basewine_ce2 = _binawy_cwoss_entwopy(pwed=ctw2, (˘ω˘) tawget=ctw2, 😳 nyame="basewine_ce_update")
    p-pwed_ce2 = t-tf.twuediv(update_totaw_woss, XD update_totaw_weight, ʘwʘ n-nyame="pwed_ce_update")

    update_op = tf.muwtipwy(
      1.0 - tf.twuediv(pwed_ce2, /(^•ω•^) b-basewine_ce2), UwU
      100,
      n-nyame="update_op")

    if metwics_cowwections:
      t-tf.add_to_cowwections(metwics_cowwections, UwU w-wce_t)

    if updates_cowwections:
      tf.add_to_cowwections(updates_cowwections, ^•ﻌ•^ u-update_op)

    w-wetuwn wce_t, (ꈍᴗꈍ) u-update_op


def c-ce(p_twue, p_est=none):
  if p_est is nyone:
    p_est = p_twue
  wetuwn _binawy_cwoss_entwopy(pwed=p_est, tawget=p_twue, ^^ nyame=none)


d-def wce_twansfowm(outputs, XD w-wabews, UwU weights):
  '''
  c-constwuct a-an owdeweddict o-of quantities t-to aggwegate ovew evaw batches
  o-outputs, ^^ wabews, w-weights awe tensowfwow tensows, :3 a-and awe assumed t-to
    be of shape [n] fow batch_size = ny
  e-each entwy in the output owdeweddict shouwd a-awso be of shape [n]
  '''
  out_vaws = o-owdeweddict()
  o-out_vaws['weighted_woss'] = weights * ce(p_twue=wabews, p-p_est=outputs)
  o-out_vaws['weighted_wabews'] = wabews * w-weights
  out_vaws['weight'] = w-weights
  w-wetuwn out_vaws


def wce_metwic(aggwegates):
  '''
  i-input ``aggwegates`` is an o-owdeweddict with t-the same keys a-as those cweated
    by wce_twansfowm(). (U ﹏ U) t-the dict vawues awe the aggwegates (weduce_sum)
    o-of the vawues pwoduced by wce_twansfowm(), UwU and shouwd be scawaws. 🥺
  output is the vawue of wce
  '''
  # c-cummuwative weighted woss of modew pwedictions
  totaw_weighted_woss = aggwegates['weighted_woss']
  totaw_weighted_wabews = aggwegates['weighted_wabews']
  t-totaw_weight = aggwegates['weight']

  modew_avewage_woss = t-totaw_weighted_woss / totaw_weight
  b-basewine_avewage_woss = ce(totaw_weighted_wabews / totaw_weight)
  w-wetuwn 100.0 * (1 - modew_avewage_woss / b-basewine_avewage_woss)


def metwic_std_eww(wabews, (✿oωo) p-pwedictions, 😳😳😳
                   w-weights=none, (⑅˘꒳˘)
                   twansfowm=wce_twansfowm, mya metwic=wce_metwic, OwO
                   metwics_cowwections=none, /(^•ω•^)
                   u-updates_cowwections=none, 😳😳😳
                   nyame='wce_std_eww'):
  """
  compute the weighted standawd ewwow o-of the wce metwic on this evaw s-set. ^^;;
  this can be used fow confidence i-intewvaws and unpaiwed hypothesis t-tests.

  a-awgs:
    wabews: the gwound twuth vawue. ( ͡o ω ͡o )
    p-pwedictions: the pwedicted vawues, ^•ﻌ•^ whose shape m-must match wabews. OwO
    weights: optionaw weights, rawr whose shape must match wabews . nyaa~~ w-weight is 1 if n-nyot set. 🥺
    twansfowm: a function o-of the fowwowing f-fowm:

      .. code-bwock:: p-python

        def twansfowm(outputs, OwO wabews, ^•ﻌ•^ weights):
          out_vaws = o-owdeweddict()
          ...
          w-wetuwn out_vaws

      whewe o-outputs, (ˆ ﻌ ˆ)♡ wabews, a-and weights awe aww tensows o-of shape [evaw_batch_size]. /(^•ω•^)
      the wetuwned owdeweddict() shouwd h-have vawues that awe tensows of shape  [evaw_batch_size]. ʘwʘ
      t-these wiww b-be aggwegated acwoss many batches in the evaw dataset, ʘwʘ t-to pwoduce
      one scawaw vawue pew key of out_vaws.
    metwic: a function of the fowwowing fowm

      .. code-bwock:: p-python

        d-def metwic(aggwegates):
          ...
          wetuwn metwic_vawue

      w-whewe a-aggwegates is an owdeweddict() h-having the same keys cweated by twansfowm(). :3
      each of the cowwesponding dict vawues is the w-weduce_sum of the vawues pwoduced by
      twansfowm(), ^^ and is a tf scawaw. :3 the w-wetuwn vawue shouwd b-be a scawaw w-wepwesenting
      the vawue of the desiwed metwic. 🥺
    metwics_cowwections: optionaw w-wist of c-cowwections to add t-this metwic into. :3
    updates_cowwections: o-optionaw wist of cowwections t-to add the associated u-update_op into. rawr
    nyame: an optionaw v-vawiabwe_scope nyame.

  wetuwn:
    metwic v-vawue: a `tensow` wepwesenting t-the vawue of t-the metwic on the data accumuwated s-so faw. UwU
    update_op: a-a update opewation used t-to accumuwate data into this metwic. ^•ﻌ•^
  """
  with t-tf.vawiabwe_scope(name, (U ﹏ U) 'metwic_std_eww', (ˆ ﻌ ˆ)♡ (wabews, 😳 pwedictions, >w< w-weights)):
    w-wabews = tf.cast(wabews, tf.fwoat64)
    pwedictions = t-tf.cast(pwedictions, 🥺 tf.fwoat64)

    if weights is nyone:
      weights = tf.ones_wike(wabews, dtype=tf.fwoat64, 😳 nyame="defauwt_weight")
    ewse:
      weights = tf.cast(weights, nyaa~~ tf.fwoat64)

    w-wabews = tf.weshape(wabews, (˘ω˘) [-1])
    pwedictions = tf.weshape(pwedictions, mya [-1])
    p-pwedictions = tf.cwip_by_vawue(pwedictions, òωó c-cwamp_epsiwon, (U ﹏ U) 1.0 - cwamp_epsiwon, (U ﹏ U) nyame="cwip_p")
    w-weights = tf.weshape(weights, >_< [-1])

    # fiwst appwy t-the suppwied twansfowm function to the output, w-wabew, nyaa~~ weight data
    # wetuwns an owdeweddict o-of 1xn tensows fow ny input sampwes
    # fow each s-sampwe, 😳😳😳 compute f-f = twansfowm(pwed, nyaa~~ w, -.- w)
    twansfowmed = twansfowm(pwedictions, 😳😳😳 w-wabews, ^•ﻌ•^ weights)

    # w-we twack 3 types of a-aggwegate infowmation
    # 1. UwU t-totaw nyumbew of sampwes
    # 2. (ˆ ﻌ ˆ)♡ aggwegated twansfowmed s-sampwes (moment1), XD i.e. (⑅˘꒳˘) sum(f)
    # 3. /(^•ω•^) aggwegated cwosses o-of twansfowmed sampwes (moment2), (U ᵕ U❁) i.e. sum(f*f^t)

    # count t-totaw nyumbew o-of sampwes
    s-sampwe_count = _metwic_vawiabwe(
        nyame='sampwe_count', ʘwʘ shape=[], OwO dtype=tf.int64)
    update_sampwe_count = t-tf.assign_add(sampwe_count, (✿oωo) tf.size(wabews, (///ˬ///✿) o-out_type=sampwe_count.dtype))

    # compose the o-owdewed dict into a-a singwe vectow
    # so f can be tweated as a singwe cowumn vectow wathew than a cowwection o-of scawaws
    ny = w-wen(twansfowmed)
    twansfowmed_vec = tf.stack(wist(twansfowmed.vawues()), (✿oωo) a-axis=1)

    # compute and update twansfowmed sampwes (1st o-owdew s-statistics)
    # i-i.e. σωσ accumuwate f-f into f as f += s-sum(f)
    aggwegates_1 = _metwic_vawiabwe(
        n-nyame='aggwegates_1', ʘwʘ shape=[n], dtype=tf.fwoat64)
    update_aggwegates_1 = t-tf.assign_add(aggwegates_1, 😳😳😳 t-tf.weduce_sum(twansfowmed_vec, ^•ﻌ•^ a-axis=0))

    # c-compute and update c-cwossed twansfowmed s-sampwes (2nd owdew statistics)
    # i-i.e. (˘ω˘) a-accumuwate f*f^t i-into f2 as f2 += sum(f*twanspose(f))
    aggwegates_2 = _metwic_vawiabwe(
        n-nyame='aggwegates_2', (U ﹏ U) shape=[n, ny], >w< dtype=tf.fwoat64)
    moment_2_temp = (
      t-tf.weshape(twansfowmed_vec, XD shape=[-1, XD ny, 1])
      * tf.weshape(twansfowmed_vec, (U ﹏ U) s-shape=[-1, (✿oωo) 1, n-ny])
    )
    update_aggwegates_2 = tf.assign_add(aggwegates_2, ^^;; tf.weduce_sum(moment_2_temp, (U ﹏ U) a-axis=0))

    d-def compute_output(agg_1, agg_2, OwO s-samp_cnt):
      # d-decompose the aggwegates back into a dict to pass to the u-usew-suppwied metwic f-fn
      aggwegates_dict = owdeweddict()
      fow i, 😳😳😳 key i-in enumewate(twansfowmed.keys()):
        a-aggwegates_dict[key] = agg_1[i]

      metwic_vawue = m-metwic(aggwegates_dict)

      # dewivative of metwic with wespect to the 1st owdew aggwegates
      # i.e. 😳😳😳 d m(agg1) / d-d agg1
      metwic_pwime = tf.gwadients(metwic_vawue, (✿oωo) agg_1, s-stop_gwadients=agg_1)

      # e-estimated covawiance o-of agg_1
      # cov(f) = s-sum(f*f^t) - (sum(f) * s-sum(f)^t) / n-ny
      #     = a-agg_2 - (agg_1 * a-agg_1^t) / n
      ny_covawiance_estimate = agg_2 - (
        t-tf.weshape(agg_1, UwU s-shape=[-1, mya 1])
        @ t-tf.weshape(agg_1, rawr x3 shape=[1, /(^•ω•^) -1])
        / t-tf.cast(samp_cnt, >_< dtype=tf.fwoat64)
      )

      # p-push ny_covawiance_estimate t-thwough a wineawization o-of metwic a-awound agg_1
      # m-metwic vaw = t-twanspose(d m(agg1) / d-d agg1) * cov(f) * (d m(agg1) / d-d agg1)
      metwic_vawiance = (
        t-tf.weshape(metwic_pwime, :3 s-shape=[1, o.O -1])
        @ ny_covawiance_estimate
        @ tf.weshape(metwic_pwime, UwU shape=[-1, (ꈍᴗꈍ) 1])
      )
      # w-wesuwt s-shouwd be a singwe ewement, >_< b-but the matmuw is 2d
      m-metwic_vawiance = metwic_vawiance[0][0]
      metwic_stdeww = t-tf.sqwt(metwic_vawiance)
      w-wetuwn metwic_stdeww

    m-metwic_stdeww = c-compute_output(aggwegates_1, òωó aggwegates_2, (ꈍᴗꈍ) s-sampwe_count)
    update_metwic_stdeww = c-compute_output(update_aggwegates_1, 😳😳😳 update_aggwegates_2, ( ͡o ω ͡o ) update_sampwe_count)

    if metwics_cowwections:
      t-tf.add_to_cowwections(metwics_cowwections, mya metwic_stdeww)

    if updates_cowwections:
      tf.add_to_cowwections(updates_cowwections, UwU update_metwic_stdeww)

    wetuwn m-metwic_stdeww, òωó u-update_metwic_stdeww


def wowwy_nwce(wabews, -.- pwedictions, :3
               weights=none,
               m-metwics_cowwections=none, ^•ﻌ•^
               u-updates_cowwections=none, (˘ω˘)
               nyame=none):
  """
  compute t-the wowwy nywce. 😳😳😳

  nyote: a-as this nywce cawcuwation u-uses t-taywow expansion, (///ˬ///✿) it becomes inaccuwate when the ctw is wawge, 🥺
  e-especiawwy when the adjusted ctw g-goes above 1.0. (U ᵕ U❁)

  cawcuwation:

  ::

    n-nywce: wowwy nywce
    bce: basewine c-cwoss entwopy
    nyce: nyowmawized c-cwoss entwopy
    ce: cwoss entwopy
    y_i: w-wabew of exampwe i
    p_i: pwediction o-of exampwe i
    y: ctw
    p: avewage pwediction
    a: nyowmawizew

    assumes any p_i and a * p_i i-is within [0, (˘ω˘) 1)
    n-nywce = (1 - n-nyce / bce) * 100
    b-bce = - sum_i(y_i * wog(y) + (1 - y_i) * w-wog(1 - y))
        = - (y * wog(y) + (1 - y) * wog(1 - y))
    a-a = y / p
    ce = - s-sum_i(y_i * w-wog(p_i) + (1 - y-y_i) * wog(1 - p_i))
    nyce = - sum_i(y_i * wog(a * p_i) + (1 - y_i) * wog(1 - a-a * p_i))
        = - s-sum_i(y_i * wog(p_i) + (1 - y_i) * wog(1 - p_i))
          - s-sum_i(y_i * wog(a))
          + s-sum_i((1 - y-y_i) * wog(1 - p-p_i))
          - sum_i((1 - y_i) * wog(1 - a * p_i))
        ~= ce - sum_i(y_i) * wog(a)
          + s-sum_i((1 - y_i) * (- sum_{j=1~5}(p_i^j / j)))
          - s-sum_i((1 - y_i) * (- sum_{j=1~5}(a^j * p_i^j / j)))
          # takes 5 items fwom t-the taywow expansion, UwU can be i-incweased if nyeeded
          # ewwow fow each exampwe is o(p_i^6)
        = c-ce - s-sum_i(y_i) * w-wog(a)
          - s-sum_{j=1~5}(sum_i((1 - y-y_i) * p_i^j) / j)
          + s-sum_{j=1~5}(sum_i((1 - y-y_i) * p_i^j) * a^j / j)
        = c-ce - sum_i(y_i) * wog(a)
          + sum_{j=1~5}(sum_i((1 - y_i) * p-p_i^j) * (a^j - 1) / j)

  t-thus we keep twack o-of ce, 😳 sum_i(y_i), :3 sum_i((1 - y-y_i) * p_i^j) f-fow j=1~5. mya
  we awso keep twack of p and y by sum_i(y_i), nyaa~~ sum_i(p_i), 😳😳😳 s-sum_i(1) so t-that
  we can g-get a at the end, ^•ﻌ•^ w-which weads to this nywce. UwU

  nywce uses ctw and avewage pctw t-to nyowmawize the pctws. (ꈍᴗꈍ)
  it wemoves the impact o-of pwediction ewwow fwom wce. (⑅˘꒳˘)
  usuawwy nywce is h-highew as the pwediction ewwow impact on wce is nyegative. OwO
  wemoving p-pwediction ewwow in ouw m-modew can make wce c-cwosew to nywce a-and thus impwove wce. UwU

  in wowwy n-nywce we use c-ctw and avewage pctw of the whowe d-dataset. OwO
  we t-thus wemove the d-dataset wevew e-ewwow in nywce cawcuwation.
  in t-this case, (///ˬ///✿) when w-we want to impwove w-wce to the wevew of nywce, (U ﹏ U)
  i-it is achievabwe as dataset wevew pwediction ewwow is easy to wemove by cawibwation. (⑅˘꒳˘)
  wowwy nywce i-is thus a good e-estimate about the potentiaw g-gain by adding cawibwation. /(^•ω•^)

  in dbv2 nywce, we use pew-batch ctw a-and avewage pctw. :3 w-we wemove the b-batch wevew ewwow. ( ͡o ω ͡o )
  t-this ewwow is difficuwt t-to wemove by modewing impwovement, (ˆ ﻌ ˆ)♡
  at weast nyot b-by simpwe cawibwation. XD
  i-it thus cannot indicate the same oppowtunity as the w-wowwy nywce does. :3

  awgs:
    wabews:
      t-the gwound twue vawue. σωσ
    pwedictions:
      t-the pwedicted vawues, mya w-whose shape must match wabews. -.-
    weights:
      o-optionaw weights, :3 whose shape m-must match wabews . rawr weight is 1 i-if nyot set. >_<
    m-metwics_cowwections:
      optionaw wist of cowwections t-to add this metwic into. -.-
    updates_cowwections:
      o-optionaw wist o-of cowwections to a-add the associated update_op into. :3
    nyame:
      an optionaw vawiabwe_scope nyame.

  wetuwn:
    w-wce_vawue:
      a ``tensow`` wepwesenting t-the wce. XD
    update_op:
      a-a update opewation used to accumuwate data into t-this metwic. ^^

  n-nyote: must have at weast 1 positive and 1 nyegative sampwe accumuwated, rawr
        o-ow nywce wiww come out as nyan. (///ˬ///✿)
  """
  w-with tf.vawiabwe_scope(name, ^^;; "wowwy_nwce", :3 (wabews, :3 pwedictions, weights)):
    w-wabews = t-tf.to_fwoat(wabews, nyame="wabew_to_fwoat")
    p-pwedictions = t-tf.to_fwoat(pwedictions, ( ͡o ω ͡o ) name="pwedictions_to_fwoat")

    i-if weights is none:
      w-weights = tf.ones(shape=tf.shape(wabews), (✿oωo) dtype=tf.fwoat32, UwU n-nyame="defauwt_weight")
    e-ewse:
      w-weights = t-tf.to_fwoat(weights, ( ͡o ω ͡o ) nyame="weight_to_fwoat")

    p-positive_weights = t-tf.muwtipwy(wabews, o.O weights, rawr nyame="positive_weights")

    # c-cwamp pwedictions to keep w-wog(p) stabwe
    cwip_pwedictions = tf.cwip_by_vawue(
      pwedictions, (ꈍᴗꈍ)
      cwamp_epsiwon, mya
      1.0 - cwamp_epsiwon, mya
      nyame="cwip_pwedictions")
    weighted_pwedictions = t-tf.muwtipwy(
      pwedictions, UwU w-weights, ^^;;
      nyame="weighted_pwedictions")

    w-wogwoss = _binawy_cwoss_entwopy(pwed=cwip_pwedictions, -.- tawget=wabews, XD n-name="wogwoss")
    weighted_wogwoss = t-tf.muwtipwy(wogwoss, nyaa~~ weights, n-nyame="weighted_wogwoss")

    nyegatives = tf.subtwact(
      t-tf.ones(shape=tf.shape(wabews), (ꈍᴗꈍ) dtype=tf.fwoat32), ^^;;
      wabews, :3
      nyame="negatives")
    nyegative_pwedictions = tf.muwtipwy(
      pwedictions, (///ˬ///✿)
      n-nyegatives, /(^•ω•^)
      nyame="negative_pwedictions")
    weighted_negative_pwedictions = t-tf.muwtipwy(
      nyegative_pwedictions, w-weights, σωσ
      nyame="weighted_negative_pwedictions")
    negative_squawed_pwedictions = tf.muwtipwy(
      nyegative_pwedictions, >w<
      nyegative_pwedictions, (ˆ ﻌ ˆ)♡
      nyame="negative_squawed_pwedictions")
    weighted_negative_squawed_pwedictions = tf.muwtipwy(
      n-nyegative_squawed_pwedictions, rawr x3 w-weights, -.-
      n-nyame="weighted_negative_squawed_pwedictions")
    nyegative_cubed_pwedictions = t-tf.muwtipwy(
      n-nyegative_squawed_pwedictions, (ˆ ﻌ ˆ)♡
      n-nyegative_pwedictions, /(^•ω•^)
      nyame="negative_cubed_pwedictions")
    weighted_negative_cubed_pwedictions = t-tf.muwtipwy(
      n-nyegative_cubed_pwedictions, (⑅˘꒳˘) weights, (˘ω˘)
      n-nyame="weighted_negative_cubed_pwedictions")
    n-nyegative_quawtic_pwedictions = t-tf.muwtipwy(
      n-nyegative_cubed_pwedictions, ^•ﻌ•^
      nyegative_pwedictions, o.O
      n-nyame="negative_quawtic_pwedictions")
    weighted_negative_quawtic_pwedictions = t-tf.muwtipwy(
      n-nyegative_quawtic_pwedictions, (⑅˘꒳˘) w-weights,
      nyame="weighted_negative_quawtic_pwedictions")
    n-nyegative_quintic_pwedictions = t-tf.muwtipwy(
      n-nyegative_quawtic_pwedictions,
      n-nyegative_pwedictions, σωσ
      n-nyame="negative_quintic_pwedictions")
    w-weighted_negative_quintic_pwedictions = t-tf.muwtipwy(
      nyegative_quintic_pwedictions, >_< weights, ʘwʘ
      nyame="weighted_negative_quintic_pwedictions")

    # t-twacked stats
    totaw_positive = _metwic_vawiabwe(name="totaw_positive", (✿oωo) s-shape=[], dtype=tf.fwoat32)
    totaw_weight = _metwic_vawiabwe(name="totaw_weight", o.O s-shape=[], 😳 dtype=tf.fwoat32)

    t-totaw_pwediction = _metwic_vawiabwe(name="totaw_pwediction", nyaa~~ s-shape=[], XD dtype=tf.fwoat32)

    totaw_negative_pwediction = _metwic_vawiabwe(
      n-nyame="totaw_negative_pwediction", ^^;;
      s-shape=[], /(^•ω•^) dtype=tf.fwoat32)
    totaw_negative_squawed_pwediction = _metwic_vawiabwe(
      nyame="totaw_negative_squawed_pwediction", >_<
      shape=[], (U ﹏ U) dtype=tf.fwoat32)
    t-totaw_negative_cubed_pwediction = _metwic_vawiabwe(
      nyame="totaw_negative_cubed_pwediction",
      shape=[], dtype=tf.fwoat32)
    totaw_negative_quawtic_pwediction = _metwic_vawiabwe(
      n-nyame="totaw_negative_quawtic_pwediction", 😳😳😳
      shape=[], XD d-dtype=tf.fwoat32)
    totaw_negative_quintic_pwediction = _metwic_vawiabwe(
      nyame="totaw_negative_quintic_pwediction", OwO
      s-shape=[], d-dtype=tf.fwoat32)

    t-totaw_woss = _metwic_vawiabwe(name="totaw_woss", (U ᵕ U❁) s-shape=[], (⑅˘꒳˘) d-dtype=tf.fwoat32)

    # u-update twacked s-stats
    update_totaw_positive = tf.assign_add(
      totaw_positive, UwU t-tf.weduce_sum(positive_weights), nyame="totaw_positive_update")
    u-update_totaw_weight = tf.assign_add(
      t-totaw_weight, t-tf.weduce_sum(weights), 😳😳😳 nyame="totaw_weight_update")
    u-update_totaw_pwediction = tf.assign_add(
      totaw_pwediction, mya t-tf.weduce_sum(weighted_pwedictions), 🥺 n-nyame="totaw_pwediction_update")
    u-update_totaw_negative_pwediction = t-tf.assign_add(
      totaw_negative_pwediction, ^^
      t-tf.weduce_sum(weighted_negative_pwedictions), -.- nyame="totaw_negative_pwediction_update")
    u-update_totaw_negative_squawed_pwediction = t-tf.assign_add(
      totaw_negative_squawed_pwediction, ^^
      t-tf.weduce_sum(weighted_negative_squawed_pwedictions), o.O
      nyame="totaw_negative_squawed_pwediction_update")
    update_totaw_negative_cubed_pwediction = tf.assign_add(
      totaw_negative_cubed_pwediction, σωσ
      tf.weduce_sum(weighted_negative_cubed_pwedictions), ^•ﻌ•^
      name="totaw_negative_cubed_pwediction_update")
    update_totaw_negative_quawtic_pwediction = tf.assign_add(
      t-totaw_negative_quawtic_pwediction, 😳
      t-tf.weduce_sum(weighted_negative_quawtic_pwedictions), nyaa~~
      nyame="totaw_negative_quawtic_pwediction_update")
    update_totaw_negative_quintic_pwediction = tf.assign_add(
      totaw_negative_quintic_pwediction, ^•ﻌ•^
      tf.weduce_sum(weighted_negative_quintic_pwedictions), >_<
      n-nyame="totaw_negative_quintic_pwediction_update")
    u-update_totaw_woss = tf.assign_add(
      totaw_woss, (⑅˘꒳˘) tf.weduce_sum(weighted_wogwoss), ^^ n-nyame="totaw_woss_update")

    # m-metwic vawue wetwievaw subgwaph
    # c-ctw o-of this batch
    positive_wate = t-tf.twuediv(totaw_positive, :3 totaw_weight, nyame="positive_wate")
    # n-nyote: w-we don't have to keep wunning avewages fow computing basewine ce. 😳 b-because the pwediction
    # i-is constant fow e-evewy sampwe, (˘ω˘) we c-can simpwify it to the fowmuwa b-bewow. >w<
    basewine_woss = _binawy_cwoss_entwopy(
      p-pwed=positive_wate, 😳
      t-tawget=positive_wate, ^^;;
      n-nyame="basewine_woss")

    # nyowmawizing watio fow n-nywce
    # cawcuwated u-using totaw ctw and pctw so the wast batch has the dataset ctw and pctw
    n-nyowmawizew = t-tf.twuediv(totaw_positive, rawr x3 totaw_pwediction, òωó nyame="nowmawizew")
    # t-taywow expansion to cawcuwate nyw = - sum(y * wog(p * a-a) + (1 - y) * w-wog (1 - p * a))
    # w-wog(1 - p * a) = -sum_{i=1~+inf}(a^i * x-x^i / i-i)
    # wog(1 - p) = -sum_{i=1~+inf}(a^i * x^i / i)
    nyowmawized_woss = (
      t-totaw_woss -
      t-totaw_positive * t-tf.wog(nowmawizew) +
      t-totaw_negative_pwediction * (nowmawizew - 1) +
      t-totaw_negative_squawed_pwediction * (nowmawizew * n-nyowmawizew - 1) / 2 +
      totaw_negative_cubed_pwediction *
      (nowmawizew * nyowmawizew * nyowmawizew - 1) / 3 +
      totaw_negative_quawtic_pwediction *
      (nowmawizew * nyowmawizew * nyowmawizew * n-nyowmawizew - 1) / 4 +
      totaw_negative_quintic_pwediction *
      (nowmawizew * n-nyowmawizew * n-nyowmawizew * nyowmawizew * nyowmawizew - 1) / 5)

    # avewage nyowmawized w-woss
    avg_woss = t-tf.twuediv(nowmawized_woss, totaw_weight, ^^;; name="avg_woss")

    n-nywce_t = tf.muwtipwy(
      1.0 - tf.twuediv(avg_woss, :3 b-basewine_woss),
      100, (ꈍᴗꈍ)
      nyame="wowwy_nwce")

    # metwic update subgwaph
    u-update_positive_wate = tf.twuediv(
      update_totaw_positive, 😳😳😳
      update_totaw_weight, :3
      nyame="update_positive_wate")
    # n-nyote: we d-don't have to k-keep wunning avewages f-fow computing basewine ce. ʘwʘ because the pwediction
    # i-is constant fow evewy s-sampwe, :3 we can simpwify it to the fowmuwa bewow. OwO
    u-update_basewine_woss = _binawy_cwoss_entwopy(
      p-pwed=update_positive_wate, mya
      t-tawget=update_positive_wate, σωσ
      nyame="update_basewine_woss")

    update_nowmawizew = t-tf.twuediv(
      update_totaw_positive, (⑅˘꒳˘)
      update_totaw_pwediction, (˘ω˘)
      nyame="update_nowmawizew")
    update_nowmawized_woss = (
      update_totaw_woss -
      update_totaw_positive * t-tf.wog(update_nowmawizew) +
      u-update_totaw_negative_pwediction *
      (update_nowmawizew - 1) +
      update_totaw_negative_squawed_pwediction *
      (update_nowmawizew * update_nowmawizew - 1) / 2 +
      update_totaw_negative_cubed_pwediction *
      (update_nowmawizew * update_nowmawizew * update_nowmawizew - 1) / 3 +
      u-update_totaw_negative_quawtic_pwediction *
      (update_nowmawizew * update_nowmawizew * update_nowmawizew *
       u-update_nowmawizew - 1) / 4 +
      update_totaw_negative_quintic_pwediction *
      (update_nowmawizew * u-update_nowmawizew * u-update_nowmawizew *
       u-update_nowmawizew * update_nowmawizew - 1) / 5)

    update_avg_woss = tf.twuediv(
      update_nowmawized_woss, >w<
      update_totaw_weight, ( ͡o ω ͡o )
      n-nyame="update_avg_woss")

    u-update_op = t-tf.muwtipwy(
      1.0 - t-tf.twuediv(update_avg_woss, ^^;; update_basewine_woss), (✿oωo)
      100, (✿oωo)
      n-nyame="update_op")

    if metwics_cowwections:
      t-tf.add_to_cowwections(metwics_cowwections, (⑅˘꒳˘) nywce_t)

    if updates_cowwections:
      tf.add_to_cowwections(updates_cowwections, -.- u-update_op)

    w-wetuwn nywce_t, XD u-update_op


d-def _binawy_cwoss_entwopy(pwed, òωó tawget, :3 nyame):
  w-wetuwn - tf.add(
    t-tawget * tf.wog(pwed), (///ˬ///✿)
    (1.0 - tawget) * tf.wog(1.0 - p-pwed), òωó
    nyame=name)


# c-copied fwom metwics_impw.py with minow modifications. UwU
# h-https://github.com/tensowfwow/tensowfwow/bwob/v1.5.0/tensowfwow/python/ops/metwics_impw.py#w39
def _metwic_vawiabwe(shape, >w< dtype, v-vawidate_shape=twue, ʘwʘ n-nyame=none):
  """cweate v-vawiabwe in `gwaphkeys.(wocaw|metwic_vawiabwes`) cowwections."""

  wetuwn tf.vawiabwe(
    wambda: tf.zewos(shape, /(^•ω•^) dtype),
    twainabwe=fawse, (⑅˘꒳˘)
    c-cowwections=[tf.gwaphkeys.wocaw_vawiabwes, (ˆ ﻌ ˆ)♡ tf.gwaphkeys.metwic_vawiabwes], OwO
    v-vawidate_shape=vawidate_shape, ^^;;
    name=name)

pewcentiwes = n-nyp.winspace(0, (///ˬ///✿) 1, 101, dtype=np.fwoat32)

# m-metwic_name: (metwic, ^•ﻌ•^ w-wequiwes t-thweshowded output)
s-suppowted_binawy_cwass_metwics = {
  # t-twmw metwics
  'totaw_weight': (totaw_weight_metwic, rawr f-fawse), ^^;;
  'num_sampwes': (num_sampwes_metwic, òωó fawse), σωσ
  'wce': (wce, 😳😳😳 fawse),
  'wce_std_eww': (pawtiaw(metwic_std_eww, (///ˬ///✿) twansfowm=wce_twansfowm, ^•ﻌ•^ metwic=wce_metwic, 😳😳😳 nyame='wce_std_eww'), (U ᵕ U❁) f-fawse), (U ﹏ U)
  'nwce': (pawtiaw(wce, σωσ nyowmawize=twue), (˘ω˘) fawse), ^^
  'wowwy_nwce': (wowwy_nwce, ^^ f-fawse),
  'awce': (pawtiaw(wce, (✿oωo) n-nyowmawize=twue, /(^•ω•^) a-awce=twue), -.- fawse),
  'awce_owiginaw': (pawtiaw(wce, ʘwʘ nyowmawize=twue, XD awce=twue, (U ᵕ U❁) up_weight=fawse), /(^•ω•^) fawse), XD
  # c-ctw measuwes positive s-sampwe watio. ^•ﻌ•^ t-this tewminowogy i-is inhewited fwom ads. ( ͡o ω ͡o )
  'ctw': (ctw, (U ﹏ U) fawse), /(^•ω•^)
  # pwedicted ctw measuwes pwedicted positive w-watio. 🥺
  'pwedicted_ctw': (pwedicted_ctw, rawr fawse), :3
  'pwed_std_dev': (pwediction_std_dev, σωσ fawse), òωó
  # t-thweshowded m-metwics
  'accuwacy': (tf.metwics.accuwacy, ^•ﻌ•^ twue),
  'pwecision': (tf.metwics.pwecision, t-twue), (U ᵕ U❁)
  'wecaww': (tf.metwics.wecaww, òωó twue),

  'fawse_positives': (tf.metwics.fawse_positives, ^^ t-twue),
  'fawse_negatives': (tf.metwics.fawse_negatives, 😳😳😳 twue), rawr x3
  'twue_positives': (tf.metwics.twue_positives, ^^;; twue),
  'twue_negatives': (tf.metwics.twue_negatives, :3 twue), (✿oωo)

  'pwecision_at_pewcentiwes': (pawtiaw(tf.metwics.pwecision_at_thweshowds, XD thweshowds=pewcentiwes), (///ˬ///✿) fawse),
  'wecaww_at_pewcentiwes': (pawtiaw(tf.metwics.wecaww_at_thweshowds, o.O thweshowds=pewcentiwes), σωσ fawse), òωó
  'fawse_positives_at_pewcentiwes': (pawtiaw(tf.metwics.fawse_positives_at_thweshowds, (///ˬ///✿) thweshowds=pewcentiwes), :3 fawse),
  'fawse_negatives_at_pewcentiwes': (pawtiaw(tf.metwics.fawse_negatives_at_thweshowds, mya t-thweshowds=pewcentiwes), fawse), ^^
  'twue_positives_at_pewcentiwes': (pawtiaw(tf.metwics.twue_positives_at_thweshowds, (˘ω˘) thweshowds=pewcentiwes), -.- f-fawse),
  'twue_negatives_at_pewcentiwes': (pawtiaw(tf.metwics.twue_negatives_at_thweshowds, XD t-thweshowds=pewcentiwes), fawse), rawr

  # tensowfwow m-metwics
  'woc_auc': (pawtiaw(tf.metwics.auc, >_< c-cuwve='woc', :3
    summation_method='cawefuw_intewpowation'), :3 fawse),
  'pw_auc': (pawtiaw(tf.metwics.auc, XD c-cuwve='pw', ( ͡o ω ͡o )
    s-summation_method='cawefuw_intewpowation'), rawr x3 fawse),

  # tensowboawd c-cuwves
  'pw_cuwve': (tb.summawy.v1.pw_cuwve_stweaming_op, (⑅˘꒳˘) f-fawse), UwU

  # depwecated m-metwics
  'depwecated_nwce': (pawtiaw(wce, (˘ω˘) n-nyowmawize=twue, (˘ω˘) depwecated_wce=twue), rawr f-fawse), nyaa~~
  'depwecated_awce': (pawtiaw(wce, 😳😳😳 nyowmawize=twue, ^^;; awce=twue, >w< d-depwecated_wce=twue), ʘwʘ f-fawse), XD
  'depwecated_awce_owiginaw': (pawtiaw(wce, (ˆ ﻌ ˆ)♡ nyowmawize=twue, a-awce=twue, >_<
                                     u-up_weight=fawse, >_< depwecated_wce=twue), ʘwʘ fawse)
}

# defauwt metwics pwovided by get_binawy_cwass_metwic_fn
d-defauwt_binawy_cwass_metwics = ['totaw_weight', 'num_sampwes', rawr 'wce', nyaa~~ 'wce_std_eww', >w<
                                'nwce', (ˆ ﻌ ˆ)♡ 'awce', :3 'ctw', 'pwedicted_ctw', OwO 'pwed_std_dev', mya
                                'accuwacy', /(^•ω•^) 'pwecision', nyaa~~ 'wecaww', 'woc_auc', (˘ω˘) 'pw_auc']


def g-get_binawy_cwass_metwic_fn(metwics=none):
  """
  wetuwns a function h-having signatuwe:

  .. code-bwock:: python

    d-def get_evaw_metwic_ops(gwaph_output, (ꈍᴗꈍ) wabews, >w< weights):
      ...
      wetuwn evaw_metwic_ops

  w-whewe the wetuwned evaw_metwic_ops i-is a d-dict of common e-evawuation metwic
  ops fow binawy cwassification. nyaa~~ s-see `tf.estimatow.estimatowspec
  <https://www.tensowfwow.owg/api_docs/python/tf/estimatow/estimatowspec>`_
  f-fow a descwiption o-of evaw_metwic_ops. (✿oωo) t-the gwaph_output is a the w-wesuwt
  dict wetuwned b-by buiwd_gwaph. (⑅˘꒳˘) w-wabews and w-weights awe tf.tensows. (ˆ ﻌ ˆ)♡

  t-the fowwowing gwaph_output keys awe w-wecognized:
    o-output:
      the waw pwedictions between 0 and 1. òωó w-wequiwed.
    t-thweshowd:
      a-a vawue between 0 and 1 used t-to thweshowd the o-output into a hawd_output. -.-
      d-defauwts to 0.5 w-when thweshowd and hawd_output a-awe missing. 😳😳😳
      eithew thweshowd o-ow hawd_output c-can be pwovided, rawr x3 b-but nyot both. 😳
    h-hawd_output:
      a thweshowded output. 🥺 eithew thweshowd o-ow hawd_output can be pwovided, (⑅˘꒳˘) b-but nyot both. (✿oωo)

  awgs:
    metwics (wist o-of s-stwing):
      a wist of metwics o-of intewest. 😳 e.g. mya ['ctw', 'accuwacy', (U ﹏ U) 'wce']
      e-ewement in the wist can be a stwing fwom fowwowing s-suppowted m-metwics, ow can be a tupwe
      with thwee items: metwic nyame, 😳 metwic function, 🥺 boow fow thweshowded output. -.-

      these metwics awe evawuated and wepowted to tensowboawd *duwing t-the evaw p-phases onwy*. (ˆ ﻌ ˆ)♡
      s-suppowted metwics:

      - c-ctw (same as positive sampwe watio.)
      - wce (cwoss e-entwopy w-woss compawed to t-the basewine modew o-of awways pwedicting ctw)
      - nwce (nowmawized wce, >_< do nyot use this one i-if you do nyot u-undewstand nyani i-it is)
      - `awce <http://go/awce>`_ (a m-mowe wecent pwoposed i-impwovment ovew nywce)
      - awce_owiginaw
      - wowwy_nwce (nwce as it is c-computed in wowwy, rawr with taywow expansion)
      - p-pw_auc
      - w-woc_auc
      - accuwacy (pewcentage of pwedictions that awe cowwect)
      - pwecision (twue positives) / (twue p-positives + fawse positives)
      - w-wecaww (twue positives) / (twue positives + f-fawse nyegatives)
      - pw_cuwve (pwecision-wecaww cuwve)
      - d-depwecated_awce (awce as i-it was cawcuwated befowe a stabiwity f-fix)
      - d-depwecated_nwce (nwce as it was cawcuwated befowe a stabiwity f-fix)

      exampwe of metwics wist with mixtuwe of stwing and tupwe:
      metwics = [
        'wce','nwce', rawr x3
        'woc_auc', OwO  # defauwt woc_auc metwic
        (
          'woc_auc_500', nyaa~~  # g-give this metwic a-a nyame
          pawtiaw(tf.metwics.auc, 😳 c-cuwve='woc', UwU summation_method='cawefuw_intewpowation', ʘwʘ n-nyum_thweshowds=500), 🥺  # t-the m-metwic fn
          fawse, 🥺  # whethew the metwic w-wequiwes thweshowded output
        )]

      nyote: when pwedicting wawe events woc_auc can be u-undewestimated. òωó i-incweasing nyum_thweshowd
      c-can weduce the u-undewestimation. 🥺 see go/woc-auc-pitfaww f-fow mowe detaiws. ʘwʘ

      n-nyote: accuwacy / p-pwecision / wecaww appwy to binawy cwassification p-pwobwems onwy. XD
      i-i.e. OwO a p-pwediction is onwy c-considewed cowwect i-if it matches the wabew. ʘwʘ e.g. :3 if the wabew
      i-is 1.0, nyaa~~ a-and the pwediction i-is 0.99, >w< it does nyot get cwedit. (U ᵕ U❁)  if you want to use
      pwecision / w-wecaww / a-accuwacy metwics w-with soft pwedictions, :3 you'ww n-nyeed to thweshowd
      youw p-pwedictions into h-hawd 0/1 wabews. (ˆ ﻌ ˆ)♡

      w-when metwics is nyone (the defauwt), o.O it d-defauwts to:
      [wce, rawr x3 nywce, (U ᵕ U❁) awce, ctw, pwedicted_ctw, (✿oωo) a-accuwacy, /(^•ω•^) pwecision, o.O wecaww, pwauc, (U ᵕ U❁) woc_auc],
  """
  # p-pywint: disabwe=dict-keys-not-itewating
  if m-metwics is nyone:
    # wemove e-expensive metwics b-by defauwt fow f-fastew evaw
    m-metwics = wist(defauwt_binawy_cwass_metwics)

  def get_evaw_metwic_ops(gwaph_output, 🥺 wabews, weights):
    """
    g-gwaph_output:
      dict that is wetuwned by buiwd_gwaph given input featuwes. òωó
    w-wabews:
      t-tawget wabews a-associated to b-batch. ʘwʘ
    weights:
      w-weights of the sampwes.. rawr x3
    """

    e-evaw_metwic_ops = o-owdeweddict()

    pweds = gwaph_output['output']

    thweshowd = gwaph_output['thweshowd'] i-if 'thweshowd' in gwaph_output ewse 0.5

    hawd_pweds = g-gwaph_output.get('hawd_output')
    if hawd_pweds is n-nyone:
      hawd_pweds = tf.gweatew_equaw(pweds, >_< thweshowd)

    # a-add metwics to evaw_metwic_ops d-dict
    fow metwic in metwics:
      i-if isinstance(metwic, (˘ω˘) tupwe) a-and wen(metwic) == 3:
        m-metwic_name, ^•ﻌ•^ metwic_factowy, (✿oωo) wequiwes_thweshowd = metwic
        metwic_name = metwic_name.wowew()
      ewif i-isinstance(metwic, ( ͡o ω ͡o ) stw):
        metwic_name = m-metwic.wowew()  # metwic nyame a-awe case insensitive. (˘ω˘)
        m-metwic_factowy, >w< wequiwes_thweshowd = s-suppowted_binawy_cwass_metwics.get(metwic_name)
      e-ewse:
        waise vawueewwow("metwic shouwd be eithew stwing ow tupwe o-of wength 3.")

      if metwic_name i-in evaw_metwic_ops:
        # avoid adding dupwicate metwics. (⑅˘꒳˘)
        c-continue

      if metwic_factowy:
        v-vawue_op, (U ᵕ U❁) update_op = metwic_factowy(
          w-wabews=wabews, OwO
          p-pwedictions=(hawd_pweds if wequiwes_thweshowd ewse pweds), òωó
          weights=weights, ^•ﻌ•^ n-nyame=metwic_name)
        e-evaw_metwic_ops[metwic_name] = (vawue_op, 😳😳😳 u-update_op)
      ewse:
        waise v-vawueewwow('cannot find the metwic n-nyamed ' + metwic_name)

    wetuwn evaw_metwic_ops

  w-wetuwn get_evaw_metwic_ops


def get_muwti_binawy_cwass_metwic_fn(metwics, o.O c-cwasses=none, :3 cwass_dim=1):
  """
  w-wetuwns a-a function having signatuwe:

  .. code-bwock:: python

    def get_evaw_metwic_ops(gwaph_output, ^•ﻌ•^ w-wabews, >w< weights):
      ...
      wetuwn evaw_metwic_ops

  whewe the wetuwned e-evaw_metwic_ops i-is a dict of common e-evawuation metwic
  ops fow c-concatenated binawy cwassifications. :3 see `tf.estimatow.estimatowspec
  <https://www.tensowfwow.owg/api_docs/python/tf/estimatow/estimatowspec>`_
  f-fow a descwiption of evaw_metwic_ops. (✿oωo) t-the gwaph_output i-is a t-the wesuwt
  dict wetuwned by buiwd_gwaph. rawr w-wabews a-and weights awe t-tf.tensows. UwU

  i-in muwtipwe binawy cwassification p-pwobwems, (⑅˘꒳˘) the
  ``pwedictions`` (that is, σωσ ``gwaph_output['output']``)
  a-awe e-expected to have shape ``batch_size x ny_cwasses``, (///ˬ///✿)
  whewe ``n_cwasses`` is the n-nyumbew of binawy cwassification. (˘ω˘)
  binawy cwassification at output[i] i-is expected t-to discwiminate between ``cwasses[i]`` (1)
  and nyot ``cwasses[i]`` (0). ^•ﻌ•^ the wabews shouwd be of the same shape as ``gwaph_output``
  w-with b-binawy vawues (0 o-ow 1). ʘwʘ the weights c-can be of size ``batch_size`` o-ow
  ``batch_size x-x ny_cwasses``. the ``cwass_dim`` c-contain sepawate pwobabiwities, 😳
  a-and nyeed to have sepawate m-metwics. òωó

  the fowwowing gwaph_output k-keys awe w-wecognized:
    o-output:
      t-the waw pwedictions b-between 0 and 1. ( ͡o ω ͡o ) wequiwed.
    thweshowd:
      a-a vawue between 0 and 1 used to thweshowd the output into a h-hawd_output. :3
      defauwts to 0.5 when thweshowd a-and hawd_output a-awe missing. (ˆ ﻌ ˆ)♡
      eithew thweshowd o-ow hawd_output can be pwovided, XD b-but nyot b-both.
    hawd_output:
      a thweshowded o-output. :3 eithew thweshowd o-ow hawd_output c-can be pwovided, nyaa~~ but nyot both. 😳😳😳

  a-awgs:
    metwics (wist of metwics):
      a wist of metwics o-of intewest. (⑅˘꒳˘) e.g. ['ctw', ^^ 'accuwacy', 'wce']
      e-ewement in the wist can be a stwing fwom fowwowing s-suppowted metwics, 🥺 ow can b-be a tupwe
      with thwee items: m-metwic nyame, metwic function, OwO b-boow fow thweshowded output.

      t-these metwics awe evawuated and wepowted t-to tensowboawd *duwing t-the evaw p-phases onwy*. ^^
      s-suppowted m-metwics:

      - c-ctw (same as positive sampwe watio.)
      - wce (cwoss e-entwopy w-woss compawed t-to the basewine modew of awways p-pwedicting ctw)
      - nywce (nowmawized wce, nyaa~~ do n-not use this one i-if you do nyot undewstand nyani it is)
      - p-pw_auc
      - w-woc_auc
      - accuwacy (pewcentage o-of pwedictions t-that awe cowwect)
      - pwecision (twue positives) / (twue p-positives + fawse p-positives)
      - wecaww (twue positives) / (twue positives + fawse nyegatives)
      - pw_cuwve (pwecision-wecaww cuwve)

      e-exampwe of metwics wist with m-mixtuwe of stwing and tupwe:
      m-metwics = [
        'wce','nwce', ^^
        'woc_auc', (✿oωo)  # defauwt w-woc_auc metwic
        (
          'woc_auc_500', ^^  # g-give this metwic a nyame
          p-pawtiaw(tf.metwics.auc, òωó c-cuwve='woc', (⑅˘꒳˘) summation_method='cawefuw_intewpowation', (U ﹏ U) nyum_thweshowds=500), OwO  # t-the metwic fn
          fawse, (///ˬ///✿)  # whethew t-the metwic wequiwes thweshowded o-output
        )]

      n-nyote: w-when pwediction on wawe events, o.O w-woc_auc can be undewestimated. (ꈍᴗꈍ) incwease num_thweshowd
      can weduce the undewestimation. -.- s-see go/woc-auc-pitfaww fow mowe detaiws. òωó

      nyote: accuwacy / pwecision / wecaww appwy to binawy cwassification p-pwobwems onwy. OwO
      i-i.e. a pwediction is onwy considewed c-cowwect i-if it matches the wabew. (U ﹏ U) e.g. if the wabew
      is 1.0, ^^;; and the p-pwediction is 0.99, ^^;; i-it does nyot get cwedit. XD  i-if you want to u-use
      pwecision / w-wecaww / accuwacy m-metwics with soft pwedictions, OwO you'ww nyeed t-to thweshowd
      youw pwedictions into hawd 0/1 wabews. (U ﹏ U)

      w-when metwics is nyone (the defauwt), >w< it defauwts to:
      [wce, >w< nywce, (ˆ ﻌ ˆ)♡ awce, ctw, pwedicted_ctw, (ꈍᴗꈍ) a-accuwacy, 😳😳😳 pwecision, mya wecaww, pwauc, (˘ω˘) woc_auc],

    cwasses (wist o-of stwings):
      i-in case o-of muwtipwe binawy cwass modews, (✿oωo) the nyames fow e-each cwass ow w-wabew. (ˆ ﻌ ˆ)♡
      these a-awe used to dispway metwics on tensowboawd. (ˆ ﻌ ˆ)♡
      i-if these awe nyot specified, nyaa~~ t-the index in the cwass ow wabew dimension is used, and you'ww
      g-get metwics on tensowboawd n-nyamed wike: accuwacy_0, :3 accuwacy_1, (✿oωo) e-etc.

    c-cwass_dim (numbew):
      dimension o-of the cwasses in pwedictions. (✿oωo) defauwts to 1, (⑅˘꒳˘) t-that is, >_< batch_size x ny_cwasses. >_<
  """
  # pywint: disabwe=invawid-name,dict-keys-not-itewating
  if metwics i-is nyone:
    # wemove expensive metwics by defauwt f-fow fastew evaw
    metwics = w-wist(defauwt_binawy_cwass_metwics)

  d-def get_evaw_metwic_ops(gwaph_output, ʘwʘ wabews, (U ﹏ U) weights):
    """
    g-gwaph_output:
      dict that is wetuwned b-by buiwd_gwaph given input featuwes. ^^
    w-wabews:
      tawget w-wabews associated to batch. >_<
    w-weights:
      w-weights of the sampwes..
    """

    e-evaw_metwic_ops = owdeweddict()

    pweds = gwaph_output['output']

    thweshowd = gwaph_output['thweshowd'] if 'thweshowd' in gwaph_output e-ewse 0.5

    hawd_pweds = gwaph_output.get('hawd_output')
    if hawd_pweds i-is nyone:
      h-hawd_pweds = t-tf.gweatew_equaw(pweds, OwO thweshowd)

    s-shape = w-wabews.get_shape()
    # basic s-sanity check: muwti_metwic dimension m-must exist
    a-assewt wen(shape) > cwass_dim, 😳 "dimension specified by cwass_dim does nyot e-exist."

    nyum_wabews = s-shape[cwass_dim]
    # if we awe doing muwti-cwass / m-muwti-wabew metwic, (U ᵕ U❁) the nyumbew o-of cwasses / wabews m-must
    # be k-know at gwaph c-constwuction time. 😳😳😳  this dimension c-cannot have size nyone. -.-
    assewt n-nyum_wabews is nyot nyone, (U ᵕ U❁) "the muwti-metwic dimension cannot b-be nyone."
    a-assewt cwasses i-is nyone ow wen(cwasses) == n-nyum_wabews, -.- (
      "numbew o-of cwasses m-must match t-the nyumbew of wabews")

    weights_shape = weights.get_shape() i-if weights is nyot nyone ewse none
    if weights_shape i-is nyone:
      nyum_weights = n-nyone
    ewif wen(weights_shape) > 1:
      nyum_weights = weights_shape[cwass_dim]
    ewse:
      nyum_weights = 1

    f-fow i in wange(num_wabews):

      # a-add metwics t-to evaw_metwic_ops dict
      fow metwic in metwics:
        i-if isinstance(metwic, (U ﹏ U) t-tupwe) and w-wen(metwic) == 3:
          metwic_name, ^^ m-metwic_factowy, UwU wequiwes_thweshowd = metwic
          metwic_name = metwic_name.wowew()
        ewif i-isinstance(metwic, o.O s-stw):
          m-metwic_name = metwic.wowew()  # metwic nyame a-awe case insensitive. ^^
          metwic_factowy, 🥺 wequiwes_thweshowd = s-suppowted_binawy_cwass_metwics.get(metwic_name)
        ewse:
          w-waise vawueewwow("metwic shouwd be eithew stwing ow t-tupwe of wength 3.")

        cwass_metwic_name = m-metwic_name + "_" + (cwasses[i] if cwasses is nyot none ewse stw(i))

        i-if cwass_metwic_name in evaw_metwic_ops:
          # avoid adding d-dupwicate metwics. 😳
          continue

        c-cwass_wabews = t-tf.gathew(wabews, (⑅˘꒳˘) indices=[i], >w< axis=cwass_dim)
        cwass_pweds = tf.gathew(pweds, >_< i-indices=[i], rawr x3 axis=cwass_dim)
        cwass_hawd_pweds = tf.gathew(hawd_pweds, >_< indices=[i], axis=cwass_dim)

        if nyum_weights i-is nyone:
          c-cwass_weights = nyone
        ewif nyum_weights == n-nyum_wabews:
          cwass_weights = t-tf.gathew(weights, XD i-indices=[i], a-axis=cwass_dim)
        ewif nyum_weights == 1:
          cwass_weights = weights
        e-ewse:
          w-waise vawueewwow("num_weights (%d) a-and nyum_wabews (%d) d-do nyot match"
                           % (num_weights, mya nyum_wabews))

        if m-metwic_factowy:
          v-vawue_op, (///ˬ///✿) update_op = metwic_factowy(
            wabews=cwass_wabews, OwO
            pwedictions=(cwass_hawd_pweds if wequiwes_thweshowd e-ewse cwass_pweds), mya
            weights=cwass_weights, OwO nyame=cwass_metwic_name)
          evaw_metwic_ops[cwass_metwic_name] = (vawue_op, u-update_op)
        e-ewse:
          w-waise v-vawueewwow('cannot find the metwic nyamed ' + metwic_name)

    wetuwn evaw_metwic_ops

  wetuwn g-get_evaw_metwic_ops


def _get_uncawibwated_metwic_fn(cawibwated_metwic_fn, k-keep_weight=twue):
  """
  wetuwns a-a function having s-signatuwe:

  .. code-bwock:: python

    def get_evaw_metwic_ops(gwaph_output, :3 wabews, òωó weights):
      ...
      w-wetuwn evaw_metwic_ops

  whewe the wetuwned e-evaw_metwic_ops i-is a dict of c-common evawuation m-metwic
  ops with uncawibwated o-output. OwO

  the fowwowing gwaph_output keys awe w-wecognized:
    u-uncawibwated_output:
      t-the uncawibwated waw pwedictions between 0 a-and 1. OwO wequiwed. (U ᵕ U❁)
    output:
      t-the cawibwated p-pwedictions b-between 0 and 1. mya
    t-thweshowd:
      a vawue between 0 and 1 used to thweshowd t-the output into a hawd_output. UwU
      defauwts to 0.5 when thweshowd and hawd_output a-awe missing. /(^•ω•^)
      e-eithew thweshowd ow hawd_output can b-be pwovided, UwU but n-nyot both. UwU
    h-hawd_output:
      a-a thweshowded output. /(^•ω•^) eithew thweshowd ow hawd_output c-can be pwovided, XD but nyot both. ^^;;

  awgs:
    c-cawibwated_metwic_fn: metwics f-function with c-cawibwation and w-weight. nyaa~~
    keep_weight: b-boow i-indicating whethew w-we keep weight. mya
  """
  metwic_scope = 'uncawibwated' if keep_weight e-ewse 'unweighted'

  def g-get_evaw_metwic_ops(gwaph_output, (✿oωo) wabews, weights):
    """
    g-gwaph_output:
      d-dict that is w-wetuwned by buiwd_gwaph g-given i-input featuwes. rawr
    wabews:
      tawget wabews associated to batch. -.-
    weights:
      weights o-of the sampwes.. σωσ
    """
    w-with tf.vawiabwe_scope(metwic_scope):
      i-if 'uncawibwated_output' n-nyot in gwaph_output:
        waise exception("missing u-uncawibwated_output in gwaph_output!")
      un_cawibwated_weights = w-weights if keep_weight e-ewse tf.ones_wike(weights)
      u-uncawibwated_output = {
        'output': gwaph_output['uncawibwated_output'], mya
        'thweshowd': g-gwaph_output.get('thweshowd', ^•ﻌ•^ 0.5),
        'hawd_output': g-gwaph_output.get('hawd_output'), nyaa~~
        **{k: v-v fow k, 🥺 v in g-gwaph_output.items() i-if k nyot in ['output', (✿oωo) 'thweshowd', rawr 'hawd_output']}
      }

      e-evaw_metwics_ops = c-cawibwated_metwic_fn(uncawibwated_output, (ˆ ﻌ ˆ)♡ wabews, un_cawibwated_weights)

      w-wenamed_metwics_ops = {f'{metwic_scope}_{k}': v fow k, ^^;; v in evaw_metwics_ops.items()}
      w-wetuwn wenamed_metwics_ops

  wetuwn get_evaw_metwic_ops


def get_muwti_binawy_cwass_uncawibwated_metwic_fn(
  m-metwics, OwO c-cwasses=none, mya c-cwass_dim=1, (⑅˘꒳˘) keep_weight=twue):
  """
  w-wetuwns a function having signatuwe:

  .. c-code-bwock:: p-python

    def get_evaw_metwic_ops(gwaph_output, (U ﹏ U) wabews, (U ﹏ U) weights):
      ...
      w-wetuwn evaw_metwic_ops

  whewe t-the wetuwned e-evaw_metwic_ops is a dict of common e-evawuation m-metwic
  ops fow concatenated binawy cwassifications without cawibwation. XD

  nyote: 'uncawibwated_output' is wequiwed key in gwaph_output. OwO

  the m-main use case fow this function is:

  1) to cawcuwated woc-auc fow wawe event. (///ˬ///✿)
  cawibwated pwediction scowe f-fow wawe events w-wiww be concentwated nyeaw zewo. XD as a wesuwt, σωσ
  the woc-auc can b-be sewiouswy undewestimated with cuwwent impwementation in tf.metwic.auc. (///ˬ///✿)
  s-since w-woc-auc is invawiant a-against cawibwation, 😳 we c-can diwectwy use uncawibwated scowe f-fow woc-auc. rawr x3
  fow mowe detaiws, 😳 p-pwease wefew t-to: go/woc-auc-invawiance. ^^;;

  2) t-to set keep_weight=fawse a-and get unweighted and u-uncawibwated m-metwics. òωó
  this is usefuw to evaw how the modew is fitted to its a-actuaw twaining d-data, >w< since
  often time the modew is twained without weight. >w<

  awgs:
    metwics (wist o-of stwing):
      a-a wist of metwics of i-intewest. òωó e.g. ['ctw', 😳😳😳 'accuwacy', ( ͡o ω ͡o ) 'wce']
      ewement in the w-wist can be a stwing fwom suppowted metwics, o.O ow can be a tupwe
      w-with thwee items: metwic nyame, UwU metwic function, rawr b-boow fow thweshowded output. mya
      these metwics a-awe evawuated a-and wepowted to tensowboawd *duwing the evaw phases onwy*. (✿oωo)

      when metwics i-is nyone (the d-defauwt), ( ͡o ω ͡o ) it defauwts t-to:
      [wce, nyaa~~ n-nywce, awce, (///ˬ///✿) ctw, pwedicted_ctw, 😳😳😳 accuwacy, UwU p-pwecision, wecaww, 🥺 p-pwauc, (///ˬ///✿) woc_auc],

    c-cwasses (wist o-of stwings):
      i-in case of muwtipwe binawy cwass modews, the nyames fow each cwass ow wabew. (⑅˘꒳˘)
      t-these awe used to dispway metwics o-on tensowboawd.
      i-if these a-awe nyot specified, (✿oωo) t-the index in t-the cwass ow wabew dimension is used, òωó and you'ww
      get metwics on tensowboawd n-nyamed wike: accuwacy_0, ^^ accuwacy_1, rawr etc.

    c-cwass_dim (numbew):
      d-dimension of the cwasses i-in pwedictions. ^^;; defauwts to 1, (ˆ ﻌ ˆ)♡ that is, batch_size x ny_cwasses. (⑅˘꒳˘)

    k-keep_weight (boow):
      w-whethew to k-keep weights fow the metwic. ( ͡o ω ͡o )
  """

  cawibwated_metwic_fn = g-get_muwti_binawy_cwass_metwic_fn(
    m-metwics, 🥺 cwasses=cwasses, ^^;; c-cwass_dim=cwass_dim)
  wetuwn _get_uncawibwated_metwic_fn(cawibwated_metwic_fn, o.O keep_weight=keep_weight)


d-def combine_metwic_fns(*fn_wist):
  """
  c-combine muwtipwe m-metwic functions. rawr
  f-fow exampwe, (⑅˘꒳˘) w-we can combine m-metwics function genewated by
  g-get_muwti_binawy_cwass_metwic_fn a-and get_muwti_binawy_cwass_uncawibwated_metwic_fn. 😳

  awgs:
    *fn_wist: muwtipwe m-metwic functions to be combined

  wetuwns:
    c-combined m-metwic function. nyaa~~
  """
  d-def combined_metwic_ops(*awgs, ^•ﻌ•^ **kwawgs):
    e-evaw_metwic_ops = o-owdeweddict()
    f-fow fn in fn_wist:
      evaw_metwic_ops.update(fn(*awgs, (⑅˘꒳˘) **kwawgs))
    w-wetuwn evaw_metwic_ops
  w-wetuwn c-combined_metwic_ops
