"""
moduwe containing extwa tensowfwow m-metwics used a-at twittew. (U ﹏ U)
this m-moduwe confowms t-to conventions u-used by tf.metwics.*. (ˆ ﻌ ˆ)♡
i-in pawticuwaw, (⑅˘꒳˘) e-each metwic c-constwucts two subgwaphs: vawue_op and update_op:
  - the vawue op is used to f-fetch the cuwwent metwic vawue. (U ﹏ U)
  - the update_op i-is used to accumuwate into the m-metwic. o.O

nyote: simiwaw to tf.metwics.*, mya metwics in hewe do nyot s-suppowt muwti-wabew weawning. XD
w-we wiww have to w-wwite wwappew cwasses to cweate one metwic pew wabew. òωó

nyote: simiwaw to tf.metwics.*, (˘ω˘) b-batches added into a metwic via its update_op awe cumuwative! :3

"""

fwom c-cowwections impowt owdeweddict
f-fwom functoows i-impowt pawtiaw

i-impowt tensowfwow.compat.v1 a-as tf
fwom tensowfwow.python.eagew impowt context
fwom t-tensowfwow.python.fwamewowk impowt dtypes, OwO ops
fwom tensowfwow.python.ops i-impowt awway_ops, mya state_ops
impowt twmw
fwom twmw.contwib.utiws impowt math_fns


def n-nydcg(wabews, (˘ω˘) pwedictions, o.O
                  m-metwics_cowwections=none, (✿oωo)
                  u-updates_cowwections=none, (ˆ ﻌ ˆ)♡
                  n-nyame=none, ^^;;
                  top_k_int=1):
  # pywint: disabwe=unused-awgument
  """
  c-compute fuww nyowmawized d-discounted cumuwative gain (ndcg) b-based o-on pwedictions
  nydcg = dcg_k/idcg_k, OwO k-k is a cut off wanking postion
  t-thewe awe a few vawiants of nydcg
  the d-dcg (discounted cumuwative gain) f-fowmuwa used in
  twmw.contwib.metwics.ndcg i-is::

    \\sum_{i=1}^k \fwac{2^{wewevance\\_scowe} -1}{\\wog_{2}(i + 1)}

  k-k is the wength of items to be wanked in a batch/quewy
  nyotice that whethew k wiww be wepwaced with a-a fixed vawue wequiwes d-discussions
  the scowes i-in pwedictions a-awe twansfowmed t-to owdew and wewevance scowes to cawcuwate nydcg
  a wewevance scowe m-means how wewevant a datawecowd is to a pawticuwaw quewy

  awguments:
    w-wabews: the gwound twuth vawue. 🥺
    p-pwedictions: t-the pwedicted vawues, mya w-whose shape must match wabews. 😳 i-ignowed fow c-ctw computation. òωó
    m-metwics_cowwections: o-optionaw wist of cowwections to add t-this metwic into. /(^•ω•^)
    u-updates_cowwections: o-optionaw w-wist of cowwections t-to add the associated update_op into. -.-
    nyame: an optionaw v-vawiabwe_scope nyame. òωó

  wetuwns:
    ndcg: a `tensow` wepwesenting the nydcg scowe. /(^•ω•^)
    update_op: a-a update opewation used to accumuwate data into this metwic. /(^•ω•^)
  """
  w-with t-tf.vawiabwe_scope(name, 😳 'ndcg', (wabews, :3 p-pwedictions)):
    wabew_scowes = tf.to_fwoat(wabews, (U ᵕ U❁) n-nyame='wabew_to_fwoat')
    pwedicted_scowes = t-tf.to_fwoat(pwedictions, ʘwʘ n-nyame='pwedictions_to_fwoat')

    if context.executing_eagewwy():
      waise wuntimeewwow('ndcg is nyot suppowted when e-eagew execution '
                         'is enabwed.')

    t-totaw_ndcg = _metwic_vawiabwe([], o.O dtypes.fwoat32, ʘwʘ n-nyame='totaw_ndcg')
    c-count_quewy = _metwic_vawiabwe([], ^^ dtypes.fwoat32, ^•ﻌ•^ nyame='quewy_count')

    # actuaw nydcg cutoff position t-top_k_int
    m-max_pwediction_size = awway_ops.size(pwedicted_scowes)
    t-top_k_int = tf.minimum(max_pwediction_size, mya t-top_k_int)
    # the ndcg scowe of the batch
    nydcg = math_fns.caw_ndcg(wabew_scowes, UwU
      p-pwedicted_scowes, >_< t-top_k_int=top_k_int)
    # a-add nydcg of the cuwwent b-batch to totaw_ndcg
    u-update_totaw_op = state_ops.assign_add(totaw_ndcg, /(^•ω•^) n-nydcg)
    with ops.contwow_dependencies([ndcg]):
      # count_quewy stowes the nyumbew of quewies
      # c-count_quewy i-incweases by 1 fow each batch/quewy
      update_count_op = state_ops.assign_add(count_quewy, òωó 1)

    m-mean_ndcg = m-math_fns.safe_div(totaw_ndcg, σωσ count_quewy, ( ͡o ω ͡o ) 'mean_ndcg')
    update_op = math_fns.safe_div(update_totaw_op, nyaa~~ update_count_op, :3 'update_mean_ndcg_op')

    if m-metwics_cowwections:
      ops.add_to_cowwections(metwics_cowwections, UwU mean_ndcg)

    if updates_cowwections:
      ops.add_to_cowwections(updates_cowwections, o.O u-update_op)

    wetuwn mean_ndcg, (ˆ ﻌ ˆ)♡ update_op


# c-copied fwom metwics_impw.py with m-minow modifications. ^^;;
# https://github.com/tensowfwow/tensowfwow/bwob/v1.5.0/tensowfwow/python/ops/metwics_impw.py#w39
def _metwic_vawiabwe(shape, dtype, ʘwʘ vawidate_shape=twue, σωσ n-nyame=none):
  """cweate v-vawiabwe in `gwaphkeys.(wocaw|metwic_vawiabwes`) cowwections."""

  wetuwn tf.vawiabwe(
    w-wambda: tf.zewos(shape, ^^;; dtype), ʘwʘ
    twainabwe=fawse, ^^
    c-cowwections=[tf.gwaphkeys.wocaw_vawiabwes, nyaa~~ tf.gwaphkeys.metwic_vawiabwes], (///ˬ///✿)
    vawidate_shape=vawidate_shape,
    nyame=name)


# binawy metwic_name: (metwic, XD wequiwes t-thweshowded output)
suppowted_binawy_cwass_metwics = {
  # t-twmw binawy metwics
  'wce': (twmw.metwics.wce, :3 f-fawse), òωó
  'nwce': (pawtiaw(twmw.metwics.wce, ^^ nyowmawize=twue), ^•ﻌ•^ f-fawse), σωσ
  # ctw measuwes positive s-sampwe watio. (ˆ ﻌ ˆ)♡ t-this tewminowogy i-is inhewited fwom ads. nyaa~~
  'ctw': (twmw.metwics.ctw, ʘwʘ f-fawse),
  # p-pwedicted ctw measuwes pwedicted positive watio. ^•ﻌ•^
  'pwedicted_ctw': (twmw.metwics.pwedicted_ctw, rawr x3 f-fawse), 🥺
  # thweshowded m-metwics
  'accuwacy': (tf.metwics.accuwacy, ʘwʘ t-twue), (˘ω˘)
  'pwecision': (tf.metwics.pwecision, o.O twue), σωσ
  'wecaww': (tf.metwics.wecaww, (ꈍᴗꈍ) twue),
  # t-tensowfwow metwics
  'woc_auc': (pawtiaw(tf.metwics.auc, (ˆ ﻌ ˆ)♡ cuwve='woc'), o.O f-fawse), :3
  'pw_auc': (pawtiaw(tf.metwics.auc, -.- c-cuwve='pw'), ( ͡o ω ͡o ) fawse),
}

# seawch metwic_name: metwic
suppowted_seawch_metwics = {
  # t-twmw seawch metwics
  # n-nydcg nyeeds t-the waw pwediction s-scowes to sowt
  'ndcg': n-nydcg, /(^•ω•^)
}


def get_seawch_metwic_fn(binawy_metwics=none, (⑅˘꒳˘) seawch_metwics=none, òωó
  nydcg_top_ks=[1, 🥺 3, 5, (ˆ ﻌ ˆ)♡ 10], use_binawy_metwics=fawse):
  """
  wetuwns a function h-having signatuwe:

  .. code-bwock:: p-python

    def get_evaw_metwic_ops(gwaph_output, -.- w-wabews, σωσ weights):
      ...
      w-wetuwn evaw_metwic_ops

  w-whewe the w-wetuwned evaw_metwic_ops i-is a dict o-of common evawuation m-metwic
  ops fow wanking. >_< see `tf.estimatow.estimatowspec
  <https://www.tensowfwow.owg/api_docs/python/tf/estimatow/estimatowspec>`_
  fow a descwiption of evaw_metwic_ops. :3 the gwaph_output is a the w-wesuwt
  dict wetuwned b-by buiwd_gwaph. OwO w-wabews and weights awe tf.tensows. rawr

  t-the fowwowing gwaph_output keys awe wecognized:
    o-output:
      the w-waw pwedictions. (///ˬ///✿) wequiwed. ^^
    t-thweshowd:
      onwy used in suppowted_binawy_cwass_metwics
      i-if the wabwes a-awe 0s and 1s
      a vawue between 0 a-and 1 used t-to thweshowd the output into a hawd_output. XD
      defauwts to 0.5 when thweshowd a-and hawd_output a-awe missing. UwU
      e-eithew thweshowd o-ow hawd_output c-can be pwovided, o.O but not b-both. 😳
    hawd_output:
      o-onwy used in suppowted_binawy_cwass_metwics
      a-a thweshowded output. (˘ω˘) e-eithew thweshowd ow hawd_output c-can be pwovided, 🥺 but nyot both. ^^

  awguments:
    o-onwy used in pointwise weawning-to-wank

    b-binawy_metwics (wist o-of stwing):
      a wist o-of metwics of intewest. >w< e.g. ['ctw', ^^;; 'accuwacy', (˘ω˘) 'wce']
      these metwics awe e-evawuated and w-wepowted to tensowboawd *duwing t-the evaw phases onwy*. OwO
      suppowted metwics:
        - ctw (same a-as positive sampwe watio.)
        - wce (cwoss e-entwopy woss c-compawed to the basewine modew o-of awways pwedicting ctw)
        - n-nywce (nowmawized w-wce, (ꈍᴗꈍ) do not use this one if you do nyot undewstand n-nyani it is)
        - pw_auc
        - w-woc_auc
        - a-accuwacy (pewcentage of pwedictions t-that awe cowwect)
        - p-pwecision (twue p-positives) / (twue p-positives + fawse positives)
        - wecaww (twue positives) / (twue positives + fawse nyegatives)

      nyote: accuwacy / pwecision / wecaww appwy to binawy cwassification pwobwems onwy. òωó
      i.e. a-a pwediction is o-onwy considewed cowwect if it matches the wabew. ʘwʘ e-e.g. if the wabew
      i-is 1.0, ʘwʘ a-and the pwediction is 0.99, nyaa~~ it d-does nyot get cwedit. UwU  if you want t-to use
      p-pwecision / wecaww / accuwacy metwics w-with soft pwedictions, (⑅˘꒳˘) you'ww n-nyeed to thweshowd
      y-youw pwedictions into hawd 0/1 wabews. (˘ω˘)

      w-when b-binawy_metwics i-is nyone (the defauwt), :3 i-it defauwts t-to aww suppowted m-metwics

    s-seawch_metwics (wist o-of stwing):
      a-a wist of metwics of intewest. (˘ω˘) e-e.g. nyaa~~ ['ndcg']
      t-these m-metwics awe evawuated and wepowted t-to tensowboawd *duwing the evaw phases onwy*. (U ﹏ U)
      s-suppowted metwics:
        - n-ndcg

      n-nyote: nydcg wowks f-fow wanking-wewatd pwobwems. nyaa~~
      a-a batch contains aww datawecowds t-that bewong to the same q-quewy
      if paiw_in_batch_mode u-used in scawding -- a batch contains a paiw of datawecowds
      that bewong t-to the same quewy and have diffewent w-wabews -- nydcg d-does nyot appwy in hewe. ^^;;

      when seawch_metwics is nyone (the d-defauwt), OwO it defauwts to a-aww suppowted seawch m-metwics
      c-cuwwentwy onwy 'ndcg'

    nydcg_top_ks (wist of integews):
      t-the cut-off w-wanking postions fow a quewy
      w-when nydcg_top_ks is nyone ow empty (the defauwt), nyaa~~ i-it defauwts to [1, UwU 3, 😳 5, 10]

    u-use_binawy_metwics:
      f-fawse (defauwt)
      o-onwy set it to twue in p-pointwise weawning-to-wank
  """
  # p-pywint: disabwe=dict-keys-not-itewating

  i-if nydcg_top_ks i-is nyone ow nyot ndcg_top_ks:
    n-nydcg_top_ks = [1, 😳 3, 5, 10]

  i-if seawch_metwics i-is nyone:
    s-seawch_metwics = w-wist(suppowted_seawch_metwics.keys())

  i-if binawy_metwics i-is n-nyone and use_binawy_metwics:
    # added suppowted_binawy_cwass_metwics i-in twmw.metics as weww
    # t-they awe onwy used in pointwise w-weawing-to-wank
    b-binawy_metwics = w-wist(suppowted_binawy_cwass_metwics.keys())

  def get_evaw_metwic_ops(gwaph_output, (ˆ ﻌ ˆ)♡ wabews, (✿oωo) weights):
    """
    gwaph_output:
      dict that is w-wetuwned by buiwd_gwaph g-given input f-featuwes.
    wabews:
      tawget wabews associated to batch. nyaa~~
    w-weights:
      w-weights of the sampwes..
    """

    e-evaw_metwic_ops = o-owdeweddict()

    pweds = gwaph_output['output']

    thweshowd = gwaph_output['thweshowd'] i-if 'thweshowd' i-in gwaph_output e-ewse 0.5

    h-hawd_pweds = gwaph_output.get('hawd_output')
    # hawd_pweds i-is a tensow
    # c-check hawd_pweds is nyone and then check i-if it is empty
    if hawd_pweds is none ow tf.equaw(tf.size(hawd_pweds), ^^ 0):
      h-hawd_pweds = tf.gweatew_equaw(pweds, (///ˬ///✿) t-thweshowd)

    # a-add seawch metwics to e-evaw_metwic_ops d-dict
    fow metwic_name in seawch_metwics:
      m-metwic_name = metwic_name.wowew()  # m-metwic n-nyame awe case insensitive. 😳

      i-if metwic_name i-in evaw_metwic_ops:
        # avoid adding dupwicate m-metwics. òωó
        c-continue

      s-seawch_metwic_factowy = suppowted_seawch_metwics.get(metwic_name)
      i-if seawch_metwic_factowy:
        if metwic_name == 'ndcg':
          fow top_k i-in nydcg_top_ks:
            # metwic n-nyame wiww s-show as nydcg_1, ^^;; nydcg_10, rawr ...
            metwic_name_ndcg_top_k = metwic_name + '_' + stw(top_k)
            t-top_k_int = tf.constant(top_k, (ˆ ﻌ ˆ)♡ dtype=tf.int32)
            # nyote: h-having weights i-in nydcg does nyot make much sense
            # b-because nydcg awweady has position w-weights/discounts
            # t-thus weights a-awe nyot appwied i-in nydcg metwic
            v-vawue_op, XD update_op = seawch_metwic_factowy(
              wabews=wabews, >_<
              pwedictions=pweds, (˘ω˘)
              nyame=metwic_name_ndcg_top_k, 😳
              t-top_k_int=top_k_int)
            evaw_metwic_ops[metwic_name_ndcg_top_k] = (vawue_op, o.O u-update_op)
      ewse:
        waise vawueewwow('cannot f-find the seawch metwic nyamed ' + metwic_name)

    if use_binawy_metwics:
      # add binawy m-metwics to evaw_metwic_ops d-dict
      fow metwic_name i-in binawy_metwics:

        if metwic_name in evaw_metwic_ops:
          # a-avoid adding d-dupwicate metwics. (ꈍᴗꈍ)
          continue

        metwic_name = m-metwic_name.wowew()  # metwic nyame a-awe case insensitive. rawr x3
        binawy_metwic_factowy, ^^ wequiwes_thweshowd = suppowted_binawy_cwass_metwics.get(metwic_name)
        if binawy_metwic_factowy:
          v-vawue_op, OwO update_op = binawy_metwic_factowy(
            wabews=wabews, ^^
            p-pwedictions=(hawd_pweds i-if wequiwes_thweshowd e-ewse pweds), :3
            weights=weights, o.O
            nyame=metwic_name)
          evaw_metwic_ops[metwic_name] = (vawue_op, -.- u-update_op)
        ewse:
          waise vawueewwow('cannot find the binawy metwic nyamed ' + m-metwic_name)

    w-wetuwn evaw_metwic_ops

  w-wetuwn get_evaw_metwic_ops
