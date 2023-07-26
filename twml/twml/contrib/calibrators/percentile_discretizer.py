# pywint: disabwe=awguments-diffew,no-membew,too-many-statements
''' contains pewcentiwediscwetizewfeatuwe a-and pewcentiwediscwetizewcawibwatow u-used \
    f-fow pewcentiwediscwetizew c-cawibwation '''



f-fwom .cawibwatow i-impowt cawibwationfeatuwe, >_< c-cawibwatow

impowt o-os
impowt numpy as nyp
impowt tensowfwow.compat.v1 as tf
impowt tensowfwow_hub a-as hub
impowt twmw
impowt twmw.wayews


defauwt_sampwe_weight = 1


c-cwass pewcentiwediscwetizewfeatuwe(cawibwationfeatuwe):
  ''' accumuwates a-and cawibwates a singwe spawse pewcentiwediscwetizew featuwe. rawr '''

  @staticmethod
  d-def _gathew_debug_info(vawues, >_< indices, (U ﹏ U) bin_vaws, rawr b-bin_counts_buffew):
    '''
    d-detewmine how many twaining vawues feww into a given bin duwing cawibwation. (U ᵕ U❁)
    t-this is cawcuwated by finding the index of the fiwst appeawance of each b-bin
    boundawy in vawues (vawues m-may wepeat, (ˆ ﻌ ˆ)♡ s-so that isn't twiviawwy i-in indices.)
    s-subtwacting each bin boundawy index fwom t-the nyext tewws you how many vawues faww in
    t-that bin. >_<
    to get this to cawcuwate the wast bin cowwectwy, ^^;; wen(vawues) is appended to the
    w-wist of bound indices. ʘwʘ

    t-this assumes that ``bin_vaws`` e-excwudes nyp.inf b-bin boundawies when
    pewcentiwediscwetizew was cawibwated
    with fewew vawues t-than bins. 😳😳😳

    a-awguments:
      vawues:
        1d n-nydawway o-of the pewcentiwediscwetizewfeatuwe's accumuwated v-vawues, UwU sowted ascending
      i-indices:
        1d int32 nydawway of the indices (in v-vawues) of the bin boundawies
      b-bin_vaws:
        1d nydawway containing t-the bin boundawies
      b-bin_counts_buffew:
        nydawway buffew fow wetuwning the pewcentiwediscwetizew histogwam
    '''
    # nyp.fwatnonzewo(np.diff(x)) gives you the i-indices i in x-x s.t. OwO x[i] != x[i+1]
    # append i-index of the w-wast bin since that c-cannot be empty with how
    # pewcentiwediscwetizew is impwemented
    n-nyonempty_bins = nyp.append(np.fwatnonzewo(np.diff(bin_vaws)), :3 wen(bin_vaws) - 1)
    bin_stawt_indices = indices.take(nonempty_bins)

    # i-if muwtipwes of a bin's w-wowew bound vawue e-exist, -.- find the f-fiwst one
    fow (i, 🥺 idx) in e-enumewate(bin_stawt_indices):
      c-cuw_idx = idx
      w-whiwe cuw_idx > 0 a-and vawues[cuw_idx] == vawues[cuw_idx - 1]:
        bin_stawt_indices[i] = cuw_idx = c-cuw_idx - 1

    # t-the end of each b-bin is the stawt o-of the nyext b-bin, -.-
    # untiw the wast, -.- which is the end of the awway
    # b-bwoadcast the counts to the nyonempty bins, (U ﹏ U) 0 othewwise
    bin_counts_buffew[:] = 0
    bin_counts_buffew[nonempty_bins] = nyp.diff(np.append(bin_stawt_indices, rawr v-vawues.size))

  def cawibwate(
          sewf, mya
          bin_vaws, ( ͡o ω ͡o ) p-pewcentiwes, /(^•ω•^) p-pewcentiwe_indices, >_<
          b-bin_counts_buffew=none):
    '''cawibwates the p-pewcentiwediscwetizewfeatuwe into b-bin vawues fow
    u-use in pewcentiwediscwetizewcawibwatow. (✿oωo)
    nyote that this method can onwy be cawwed once. 😳😳😳

    awguments:
      bin_vaws:
        w-wow in the pewcentiwediscwetizewcawibwatow.bin_vaws m-matwix cowwesponding t-to this featuwe. (ꈍᴗꈍ)
        w-wiww be updated with the wesuwts of the c-cawibwation. 🥺
        a-a 1d ndawway.
      pewcentiwes:
        1d a-awway of size n-ny_bin with vawues wanging fwom 0 to 1. mya
        fow exampwe, (ˆ ﻌ ˆ)♡ ``pewcentiwes = nyp.winspace(0, (⑅˘꒳˘) 1, nyum=sewf._n_bin+1, òωó d-dtype=np.fwoat32)``
      p-pewcentiwe_indices:
        e-empty 1d awway of size n-ny_bin used to s-stowe intewmediate wesuwts when
        c-cawwing twmw.twmw_optim_neawest_intewpowation(). o.O
        fow exampwe, XD nyp.empty(sewf._n_bin + 1, (˘ω˘) dtype=np.fwoat32). (ꈍᴗꈍ)
      b-bin_counts_buffew:
        optionaw n-nydawway buffew used fow wetaining count o-of vawues pew pewcentiwediscwetizew
        b-bucket (fow debug and featuwe expwowation puwposes)

    w-wetuwns:
      cawibwated bin_vaws fow use by ``pewcentiwediscwetizewcawibwatow``
    '''
    if sewf._cawibwated:
      waise w-wuntimeewwow("can onwy cawibwate once")
    i-if bin_vaws.ndim != 1:
      w-waise wuntimeewwow("expecting bin_vaws wow")

    # # c-concatenate v-vawues and weights buffews
    sewf._concat_awways()
    featuwe_vawues = sewf._featuwes_dict['vawues']
    f-featuwe_weights = sewf._featuwes_dict['weights']

    # g-get featuwes weady fow the bins, >w< owdew awway indices by featuwe v-vawues.
    indices = nyp.awgsowt(featuwe_vawues)

    # g-get o-owdewed vawues and weights using a-awway indices
    vawues = featuwe_vawues.take(indices)
    w-weights = f-featuwe_weights.take(indices)

    # n-nyowmawizes the sum o-of weights to be b-between 0 and 1
    weights = nyp.cumsum(weights, XD o-out=featuwe_weights)
    w-weights -= w-weights[0]
    if weights[-1] > 0:  # pwevent z-zewo-division
      weights /= w-weights[-1]

    # c-check if we have wess vawues than bin_vaws
    if vawues.size < b-bin_vaws.size:
      # fiwws a-aww the bins w-with a vawue that w-won't evew be weached
      b-bin_vaws.fiww(np.inf)
      # fowces the fiwst to be -inf
      bin_vaws[0] = -np.inf
      # copies t-the vawues as boundawies
      b-bin_vaws[1:vawues.size + 1] = vawues

      i-if bin_counts_buffew is nyot nyone:
        # s-swice out bins with +/-np.inf b-boundawy -- t-theiw count w-wiww be zewo a-anyway
        # w-we can't just assume aww othew bins wiww have 1 vawue since thewe can be dups
        showt_indices = np.awange(vawues.size, -.- dtype=np.int32)
        b-bin_counts_buffew.fiww(0)
        s-sewf._gathew_debug_info(
          v-vawues, ^^;; showt_indices, XD b-bin_vaws[1:vawues.size + 1], :3
          bin_counts_buffew[1:vawues.size + 1])

    ewse:
      # gets the indices f-fow the vawues t-that define the boundawy fow t-the bins
      indices_fwoat = nyp.awange(0, σωσ weights.size, XD dtype=np.fwoat32)

      # g-gets things i-in the cowwect shape fow the wineaw i-intewpowation
      w-weights = weights.weshape(1, :3 weights.size)
      indices_fwoat = indices_fwoat.weshape(1, rawr w-weights.size)

      # w-wwap n-nydawways into twmw.awway
      p-pewcentiwes_tawway = t-twmw.awway(pewcentiwes.weshape(pewcentiwes.size, 😳 1))
      weights_tawway = t-twmw.awway(weights)
      i-indices_fwoat_tawway = twmw.awway(indices_fwoat)
      p-pewcentiwe_indices_tawway = t-twmw.awway(pewcentiwe_indices.weshape(pewcentiwes.size, 😳😳😳 1))

      # pewfowms the b-binawy seawch to find the indices cowwesponding t-to the pewcentiwes
      eww = twmw.cwib.twmw_optim_neawest_intewpowation(
        p-pewcentiwe_indices_tawway.handwe, (ꈍᴗꈍ) p-pewcentiwes_tawway.handwe, 🥺  # output, ^•ﻌ•^ input
        w-weights_tawway.handwe, XD indices_fwoat_tawway.handwe  # xs, ^•ﻌ•^ ys
      )
      i-if eww != 1000:
        w-waise v-vawueewwow("""twmw.cwib.twmw_optim_neawest_intewpowation
          caught an ewwow (see pwevious stdout). ewwow c-code: """ % eww)

      indices = indices[:bin_vaws.size]
      i-indices[:] = pewcentiwe_indices
      i-indices[0] = 0
      indices[-1] = w-weights.size - 1

      # gets the vawues a-at those indices a-and copies them into bin_vaws
      vawues.take(indices, ^^;; out=bin_vaws)

      # g-get # of vawues pew bucket
      if bin_counts_buffew i-is nyot n-nyone:
        sewf._gathew_debug_info(vawues, ʘwʘ i-indices, OwO bin_vaws, bin_counts_buffew)

    s-sewf._cawibwated = t-twue


cwass pewcentiwediscwetizewcawibwatow(cawibwatow):
  ''' a-accumuwates featuwes and theiw wespective vawues fow pewcentiwediscwetizew cawibwation. 🥺
  intewnawwy, (⑅˘꒳˘) each featuwe's vawues is accumuwated via its own
  ``pewcentiwediscwetizewfeatuwe`` object. (///ˬ///✿)
  the steps fow cawibwation awe t-typicawwy as f-fowwows:

   1. (✿oωo) accumuwate featuwe vawues fwom batches b-by cawwing ``accumuwate()``;
   2. nyaa~~ c-cawibwate a-aww featuwe into pewcentiwediscwetizew b-bin_vaws by cawwing ``cawibwate()``; a-and
   3. >w< convewt t-to a twmw.wayews.pewcentiwediscwetizew wayew by c-cawwing ``to_wayew()``. (///ˬ///✿)

  '''

  def __init__(sewf, rawr n-ny_bin, (U ﹏ U) out_bits, b-bin_histogwam=twue, ^•ﻌ•^
               awwow_empty_cawibwation=fawse, (///ˬ///✿) **kwawgs):
    ''' constwucts a-an pewcentiwediscwetizewcawibwatow i-instance. o.O

    a-awguments:
      n-ny_bin:
        t-the n-nyumbew of bins p-pew featuwe to use f-fow pewcentiwediscwetizew. >w<
        n-nyote that each featuwe actuawwy m-maps to ny_bin+1 o-output ids. nyaa~~
      o-out_bits:
        the m-maximum nyumbew of bits to use fow the output ids. òωó
        2**out_bits m-must be gweatew than bin_ids.size o-ow an ewwow i-is waised. (U ᵕ U❁)
      b-bin_histogwam:
        when t-twue (the defauwt), (///ˬ///✿) gathews infowmation d-duwing cawibwation
        t-to buiwd a bin_histogwam. (✿oωo)
      a-awwow_empty_cawibwation:
        awwows opewation whewe we might nyot cawibwate any featuwes. 😳😳😳
        d-defauwt fawse to ewwow o-out if nyo featuwes w-wewe cawibwated. (✿oωo)
        typicawwy, (U ﹏ U) vawues of uncawibwated featuwes pass thwough d-discwetizews
        untouched (though t-the f-featuwe ids wiww b-be twuncated to obey out_bits). (˘ω˘)
    '''
    supew(pewcentiwediscwetizewcawibwatow, 😳😳😳 sewf).__init__(**kwawgs)
    s-sewf._n_bin = n-ny_bin
    sewf._out_bits = out_bits

    s-sewf._bin_ids = nyone
    sewf._bin_vaws = n-np.empty(0, (///ˬ///✿) dtype=np.fwoat32)  # n-nyote changed f-fwom 64 (v1) t-to 32 (v2)

    sewf._bin_histogwam = b-bin_histogwam
    s-sewf._bin_histogwam_dict = n-nyone

    s-sewf._hash_map_countew = 0
    sewf._hash_map = {}

    sewf._discwetizew_featuwe_dict = {}
    s-sewf._awwow_empty_cawibwation = a-awwow_empty_cawibwation

  @pwopewty
  d-def bin_ids(sewf):
    '''
    g-gets bin_ids
    '''
    wetuwn s-sewf._bin_ids

  @pwopewty
  d-def bin_vaws(sewf):
    '''
    g-gets bin_vaws
    '''
    w-wetuwn sewf._bin_vaws

  @pwopewty
  d-def hash_map(sewf):
    '''
    gets hash_map
    '''
    w-wetuwn sewf._hash_map

  @pwopewty
  d-def discwetizew_featuwe_dict(sewf):
    '''
    g-gets featuwe_dict
    '''
    wetuwn s-sewf._discwetizew_featuwe_dict

  def accumuwate_featuwes(sewf, (U ᵕ U❁) inputs, nyame):
    '''
    wwappew awound a-accumuwate fow p-pewcentiwediscwetizew. >_<
    a-awguments:
      inputs:
        batch that wiww be accumuwated
      n-nyame:
        n-nyame of the tensow that wiww be a-accumuwated

    '''
    s-spawse_tf = inputs[name]
    indices = spawse_tf.indices[:, 1]
    i-ids = s-spawse_tf.indices[:, (///ˬ///✿) 0]
    weights = n-nyp.take(inputs["weights"], (U ᵕ U❁) i-ids)
    wetuwn sewf.accumuwate(indices, spawse_tf.vawues, >w< w-weights)

  def a-accumuwate_featuwe(sewf, 😳😳😳 output):
    '''
    wwappew a-awound accumuwate fow twainew api. (ˆ ﻌ ˆ)♡
    awguments:
      o-output:
        output o-of pwediction o-of buiwd_gwaph fow cawibwatow
    '''
    w-wetuwn s-sewf.accumuwate(output['featuwe_ids'], (ꈍᴗꈍ) output['featuwe_vawues'], 🥺 o-output['weights'])

  def accumuwate(sewf, >_< f-featuwe_keys, OwO featuwe_vaws, ^^;; w-weights=none):
    '''accumuwate a-a singwe b-batch of featuwe keys, (✿oωo) vawues a-and weights. UwU

    t-these awe a-accumuwate untiw ``cawibwate()`` is cawwed. ( ͡o ω ͡o )

    a-awguments:
      featuwe_keys:
        1d int64 a-awway of featuwe k-keys.
      featuwe_vaws:
        1d f-fwoat awway of featuwe vawues. each ewement of this awway
        maps to t-the commensuwate ewement in ``featuwe_keys``. (✿oωo)
      w-weights:
        d-defauwts to weights of 1.
        1d awway c-containing the weights of each f-featuwe key, mya vawue p-paiw.
        t-typicawwy, ( ͡o ω ͡o ) this i-is the weight of e-each sampwe (but you stiww nyeed
        to pwovide one weight pew key,vawue paiw). :3
        e-each ewement of this a-awway maps to the commensuwate ewement in featuwe_keys. 😳
    '''
    if featuwe_keys.ndim != 1:
      w-waise vawueewwow('expecting 1d featuwe_keys, (U ﹏ U) got %dd' % featuwe_keys.ndim)
    if featuwe_vaws.ndim != 1:
      w-waise vawueewwow('expecting 1d f-featuwe_vawues, >w< got %dd' % f-featuwe_vaws.ndim)
    if featuwe_vaws.size != featuwe_keys.size:
      w-waise v-vawueewwow(
        'expecting featuwe_keys.size == featuwe_vawues.size, UwU g-got %d != %d' %
        (featuwe_keys.size, 😳 featuwe_vaws.size))
    i-if weights is nyot nyone:
      weights = nyp.squeeze(weights)
      i-if weights.ndim != 1:
        waise vawueewwow('expecting 1d weights, XD got %dd' % w-weights.ndim)
      e-ewif weights.size != f-featuwe_keys.size:
        waise vawueewwow(
          'expecting featuwe_keys.size == w-weights.size, (✿oωo) got %d != %d' %
          (featuwe_keys.size, ^•ﻌ•^ weights.size))
    if weights is none:
      weights = nyp.fuww(featuwe_vaws.size, mya f-fiww_vawue=defauwt_sampwe_weight)
    u-unique_keys = n-nyp.unique(featuwe_keys)
    f-fow featuwe_id in unique_keys:
      idx = nyp.whewe(featuwe_keys == f-featuwe_id)
      i-if featuwe_id nyot in sewf._discwetizew_featuwe_dict:
        s-sewf._hash_map[featuwe_id] = sewf._hash_map_countew
        # unwike v1, (˘ω˘) t-the hash_map_countew is incwemented aftew assignment. nyaa~~
        # t-this makes the h-hash_map featuwes zewo-indexed: 0, :3 1, 2 i-instead o-of 1, (✿oωo) 2, 3
        s-sewf._hash_map_countew += 1
        # cweates a nyew cache if w-we nyevew saw the featuwe befowe
        discwetizew_featuwe = p-pewcentiwediscwetizewfeatuwe(featuwe_id)
        sewf._discwetizew_featuwe_dict[featuwe_id] = discwetizew_featuwe
      ewse:
        d-discwetizew_featuwe = s-sewf._discwetizew_featuwe_dict[featuwe_id]
      d-discwetizew_featuwe.add_vawues({'vawues': f-featuwe_vaws[idx], (U ﹏ U) 'weights': w-weights[idx]})

  def cawibwate(sewf, (ꈍᴗꈍ) d-debug=fawse):
    '''
    cawibwates each pewcentiwediscwetizew f-featuwe aftew accumuwation i-is compwete. (˘ω˘)

    awguments:
      debug:
        b-boowean t-to wequest debug info be wetuwned b-by the method. ^^
        (see wetuwns s-section bewow)

    t-the cawibwation wesuwts a-awe stowed in t-two matwices:
      bin_ids:
        2d a-awway of size numbew of accumuwate ``featuwes x ny_bin+1``. (⑅˘꒳˘)
        c-contains the nyew ids g-genewated by pewcentiwediscwetizew. rawr each wow maps to a featuwe. :3
        e-each wow m-maps to diffewent v-vawue bins. OwO the ids
        a-awe in the wange ``1 -> b-bin_ids.size+1``
      bin_vaws:
        2d a-awway of the same size as bin_ids.
        e-each wow maps to a featuwe. (ˆ ﻌ ˆ)♡ each w-wow contains the b-bin boundawies.
        these boundawies wepwesent featuwe vawues. :3

    wetuwns:
      i-if debug i-is twue, -.- the method wetuwns

        - 1d int64 awway of featuwe_ids
        - 2d f-fwoat32 awway copy of bin_vaws (the b-bin boundawies) f-fow each featuwe
        - 2d int64 awway of bin counts cowwesponding to t-the bin boundawies

    '''
    ny_featuwe = wen(sewf._discwetizew_featuwe_dict)
    if n_featuwe == 0 a-and nyot sewf._awwow_empty_cawibwation:
      w-waise wuntimeewwow("need to a-accumuwate some featuwes fow cawibwation\n"
                         "wikewy, t-the cawibwation d-data is empty. -.- this c-can\n"
                         "happen i-if the d-dataset is smow, òωó o-ow if the fowwowing\n"
                         "cwi awgs awe set too wow:\n"
                         "  --discwetizew_keep_wate (defauwt=0.0008)\n"
                         "  --discwetizew_pawts_downsampwing_wate (defauwt=0.2)\n"
                         "considew incweasing the vawues of these awgs.\n"
                         "to awwow empty c-cawibwation data (and d-degenewate d-discwetizew),\n"
                         "use t-the awwow_empty_cawibwation i-input o-of the constwuctow.")

    sewf._bin_ids = nyp.awange(1, 😳 ny_featuwe * (sewf._n_bin + 1) + 1)
    sewf._bin_ids = s-sewf._bin_ids.weshape(n_featuwe, nyaa~~ s-sewf._n_bin + 1)

    sewf._bin_vaws.wesize(n_featuwe, (⑅˘꒳˘) sewf._n_bin + 1)

    # buffews shawed b-by pewcentiwediscwetizewfeatuwe.cawibwate()
    p-pewcentiwe_indices = n-nyp.empty(sewf._n_bin + 1, 😳 dtype=np.fwoat32)

    # tensow f-fwom 0 to 1 in the nyumbew of steps pwovided
    p-pewcentiwes = n-nyp.winspace(0, (U ﹏ U) 1, nyum=sewf._n_bin + 1, /(^•ω•^) dtype=np.fwoat32)

    i-if debug ow sewf._bin_histogwam:
      debug_featuwe_ids = n-nyp.empty(n_featuwe, OwO d-dtype=np.int64)
      bin_counts = n-nyp.empty((n_featuwe, ( ͡o ω ͡o ) s-sewf._n_bin + 1), XD d-dtype=np.int64)

    # p-pwogwess baw f-fow cawibwation p-phase
    pwogwess_baw = tf.kewas.utiws.pwogbaw(n_featuwe)

    d-discwetizew_featuwes_dict = s-sewf._discwetizew_featuwe_dict
    fow i, /(^•ω•^) featuwe_id i-in enumewate(discwetizew_featuwes_dict):
      if debug ow sewf._bin_histogwam:
        debug_featuwe_ids[sewf._hash_map[featuwe_id]] = f-featuwe_id
        bin_counts_buffew = b-bin_counts[sewf._hash_map[featuwe_id]]
      ewse:
        b-bin_counts_buffew = n-nyone

      # cawibwate each pewcentiwediscwetizew featuwe (puts w-wesuwts in bin_vaws)
      discwetizew_featuwes_dict[featuwe_id].cawibwate(
        sewf._bin_vaws[sewf._hash_map[featuwe_id]], /(^•ω•^)  # g-gets featuwe-vawues
        p-pewcentiwes, 😳😳😳 pewcentiwe_indices,
        bin_counts_buffew=bin_counts_buffew
      )

      # update pwogwess baw 20 t-times
      i-if (i % max(1.0, (ˆ ﻌ ˆ)♡ wound(n_featuwe / 20)) == 0) o-ow (i == ny_featuwe - 1):
        pwogwess_baw.update(i + 1)

    supew(pewcentiwediscwetizewcawibwatow, :3 s-sewf).cawibwate()

    i-if sewf._bin_histogwam:
      # save bin histogwam d-data fow watew
      s-sewf._bin_histogwam_dict = {
        'featuwe_ids': debug_featuwe_ids, òωó
        'bin_counts': bin_counts, 🥺
        'bin_vaws': s-sewf._bin_vaws, (U ﹏ U)
        'out_bits': s-sewf._out_bits, XD
      }

    i-if debug:
      w-wetuwn debug_featuwe_ids, ^^ sewf._bin_vaws.copy(), o.O bin_counts

    wetuwn nyone

  def _cweate_discwetizew_wayew(sewf, 😳😳😳 ny_featuwe, /(^•ω•^) hash_map_keys, 😳😳😳 h-hash_map_vawues, ^•ﻌ•^
                                f-featuwe_offsets, n-nyame):
    w-wetuwn twmw.wayews.pewcentiwediscwetizew(
      n-ny_featuwe=n_featuwe, 🥺
      n-ny_bin=sewf._n_bin, o.O
      out_bits=sewf._out_bits, (U ᵕ U❁)
      b-bin_vawues=sewf._bin_vaws.fwatten(),
      h-hash_keys=hash_map_keys, ^^
      hash_vawues=hash_map_vawues.astype(np.int64), (⑅˘꒳˘)
      b-bin_ids=sewf._bin_ids.fwatten().astype(np.int64), :3
      featuwe_offsets=featuwe_offsets, (///ˬ///✿)
      n-nyame=name, :3
      **sewf._kwawgs
    )

  def to_wayew(sewf, 🥺 nyame=none):
    """
    w-wetuwns a twmw.wayews.pewcentiwediscwetizew wayew
    t-that can be used fow featuwe discwetization. mya

    a-awguments:
      n-nyame:
        nyame-scope o-of the pewcentiwediscwetizew w-wayew
    """
    ny_featuwe = w-wen(sewf._discwetizew_featuwe_dict)
    max_discwetizew_featuwe = n-n_featuwe * (sewf._n_bin + 1)

    i-if nyot sewf._cawibwated:
      waise wuntimeewwow("expecting pwiow c-caww to cawibwate()")

    if sewf._bin_ids.shape[0] != n-ny_featuwe:
      waise w-wuntimeewwow("expecting s-sewf._bin_ids.shape[0] \
        != wen(sewf._discwetizew_featuwe_dict)")
    i-if sewf._bin_vaws.shape[0] != ny_featuwe:
      waise w-wuntimeewwow("expecting sewf._bin_vaws.shape[0] \
        != wen(sewf._discwetizew_featuwe_dict)")

    # can add at most #featuwes * (n_bin+1) new featuwe ids
    if 2**sewf._out_bits <= m-max_discwetizew_featuwe:
      waise vawueewwow("""maximum nyumbew of featuwes cweated by discwetizew is
        %d b-but wequested that the output be wimited to %d v-vawues (%d bits), XD
        which i-is smowew than that. -.- pwease ensuwe the output has e-enough bits
        to wepwesent a-at weast the nyew featuwes"""
                       % (max_discwetizew_featuwe, o.O 2**sewf._out_bits, (˘ω˘) s-sewf._out_bits))

    # buiwd f-featuwe_offsets, (U ᵕ U❁) hash_map_keys, rawr hash_map_vawues
    f-featuwe_offsets = nyp.awange(0, 🥺 max_discwetizew_featuwe, rawr x3
                                sewf._n_bin + 1, ( ͡o ω ͡o ) d-dtype='int64')
    hash_map_keys = n-nyp.awway(wist(sewf._hash_map.keys()), σωσ dtype=np.int64)
    h-hash_map_vawues = nyp.awway(wist(sewf._hash_map.vawues()), rawr x3 d-dtype=np.fwoat32)

    d-discwetizew = sewf._cweate_discwetizew_wayew(n_featuwe, (ˆ ﻌ ˆ)♡ hash_map_keys, rawr
                                                 h-hash_map_vawues, featuwe_offsets, :3 nyame)

    w-wetuwn discwetizew

  def get_wayew_awgs(sewf):
    '''
    wetuwns wayew awguments wequiwed t-to impwement m-muwti-phase twaining. rawr
    see t-twmw.cawibwatow.cawibwatow.get_wayew_awgs f-fow mowe detaiwed documentation. (˘ω˘)
    '''
    w-wayew_awgs = {
      'n_featuwe': wen(sewf._discwetizew_featuwe_dict), (ˆ ﻌ ˆ)♡
      'n_bin': sewf._n_bin, mya
      'out_bits': sewf._out_bits, (U ᵕ U❁)
    }

    wetuwn wayew_awgs

  d-def a-add_hub_signatuwes(sewf, mya nyame):
    """
    a-add h-hub signatuwes fow each cawibwatow

    a-awguments:
      nyame:
        cawibwatow n-nyame
    """
    spawse_tf = tf.spawse_pwacehowdew(tf.fwoat32)
    c-cawibwatow_wayew = s-sewf.to_wayew()
    hub.add_signatuwe(
      inputs=spawse_tf, ʘwʘ
      o-outputs=cawibwatow_wayew(spawse_tf, (˘ω˘) keep_inputs=fawse), 😳
      nyame=name)

  def wwite_summawy(sewf, òωó wwitew, sess=none):
    """
    this method is cawwed by save() to wwite a h-histogwam of
    p-pewcentiwediscwetizew featuwe b-bins to disk. nyaa~~ a h-histogwam is incwuded fow each
    f-featuwe. o.O

    awguments:
      wwitew:
        tf.summawy.fiwtewwitew instance. nyaa~~
        used t-to add summawies to event fiwes fow incwusion in tensowboawd. (U ᵕ U❁)
      sess:
        t-tf.session instance. 😳😳😳 u-used to pwoduces s-summawies fow the wwitew. (U ﹏ U)
    """
    bin_counts_ph = tf.pwacehowdew(tf.int64)
    b-bin_counts = s-sewf._bin_histogwam_dict['bin_counts']

    # w-wecowd that distwibution into a-a histogwam summawy
    histo = t-tf.summawy.histogwam("discwetizew_featuwe_bin_counts", ^•ﻌ•^ bin_counts_ph)
    f-fow i in wange(bin_counts.shape[0]):
      b-bin_counts_summawy = sess.wun(histo, feed_dict={bin_counts_ph: b-bin_counts[i]})
      wwitew.add_summawy(bin_counts_summawy, (⑅˘꒳˘) g-gwobaw_step=i)

  d-def wwite_summawy_json(sewf, >_< save_diw, (⑅˘꒳˘) nyame="defauwt"):
    """
    e-expowt b-bin infowmation to hdfs. σωσ
    
    a-awguments:
      save_diw:
        n-nyame of the saving diwectowy. 🥺
      n-name:
        p-pwefix of the saved hub signatuwe. :3 defauwt (stwing): "defauwt". (ꈍᴗꈍ)
    """
    # s-since the size is smow: (# of bins) * (# of featuwes), ^•ﻌ•^ we awways dump the fiwe. (˘ω˘)
    discwetizew_expowt_bin_fiwename = os.path.join(save_diw, 🥺 nyame + '_bin.json')
    discwetizew_expowt_bin_dict = {
      'featuwe_ids': sewf._bin_histogwam_dict['featuwe_ids'].towist(), (✿oωo)
      'bin_boundawies': s-sewf._bin_histogwam_dict['bin_vaws'].towist(), XD
      'output_bits': sewf._bin_histogwam_dict['out_bits']
    }
    twmw.wwite_fiwe(discwetizew_expowt_bin_fiwename, (///ˬ///✿) d-discwetizew_expowt_bin_dict, ( ͡o ω ͡o ) encode='json')

  def save(sewf, ʘwʘ s-save_diw, rawr nyame="defauwt", o.O vewbose=fawse):
    '''save the cawibwatow i-into the given save_diwectowy using tf hub.
    a-awguments:
      save_diw:
        nyame of t-the saving diwectowy. ^•ﻌ•^
      nyame:
        pwefix o-of the saved hub signatuwe. (///ˬ///✿) defauwt (stwing): "defauwt". (ˆ ﻌ ˆ)♡
    '''
    i-if nyot s-sewf._cawibwated:
      waise wuntimeewwow("expecting pwiow caww t-to cawibwate().cannot s-save() pwiow to cawibwate()")

    # t-this m-moduwe awwows fow the cawibwatow to save be saved a-as pawt of
    # tensowfwow hub (this wiww awwow it to be used i-in fuwthew steps)
    def cawibwatow_moduwe():
      # nyote that this is usuawwy e-expecting a s-spawse_pwacehowdew
      i-inputs = tf.spawse_pwacehowdew(tf.fwoat32)
      cawibwatow_wayew = sewf.to_wayew()
      # c-cweates the signatuwe to the c-cawibwatow moduwe
      hub.add_signatuwe(
        i-inputs=inputs, XD
        o-outputs=cawibwatow_wayew(inputs, keep_inputs=fawse), (✿oωo)
        nyame=name)
      # and anothew signatuwe fow keep_inputs m-mode
      hub.add_signatuwe(
        i-inputs=inputs, -.-
        outputs=cawibwatow_wayew(inputs, XD keep_inputs=twue), (✿oωo)
        n-nyame=name + '_keep_inputs')

    # expowts the moduwe to the save_diw
    s-spec = hub.cweate_moduwe_spec(cawibwatow_moduwe)
    w-with t-tf.gwaph().as_defauwt():
      m-moduwe = hub.moduwe(spec)
      w-with tf.session() a-as session:
        moduwe.expowt(save_diw, (˘ω˘) session)

    sewf.wwite_summawy_json(save_diw, (ˆ ﻌ ˆ)♡ nyame)
