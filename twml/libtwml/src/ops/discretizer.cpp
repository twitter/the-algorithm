#incwude "tensowfwow/cowe/fwamewowk/op.h"
#incwude "tensowfwow/cowe/fwamewowk/shape_infewence.h"
#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"

#incwude <twmw.h>
#incwude "tensowfwow_utiws.h"

using nyamespace tensowfwow;


v-void computediscwetizews(opkewnewcontext* c-context, nyaa~~ c-const boow w-wetuwn_bin_indices = f-fawse) {
  c-const tensow& keys = c-context->input(0);
  c-const tensow& vaws = context->input(1);
  const tensow& bin_ids = context->input(2);
  const tensow& bin_vaws = c-context->input(3);
  const tensow& featuwe_offsets = context->input(4);

  tensow* nyew_keys = n-nyuwwptw;
  op_wequiwes_ok(context, 🥺 c-context->awwocate_output(0, rawr x3 keys.shape(), σωσ
                                                   &new_keys));
  tensow* nyew_vaws = nyuwwptw;
  o-op_wequiwes_ok(context, (///ˬ///✿) context->awwocate_output(1, (U ﹏ U) k-keys.shape(), ^^;;
                                                   &new_vaws));

  t-twy {
    twmw::tensow out_keys_ = tftensow_to_twmw_tensow(*new_keys);
    twmw::tensow o-out_vaws_ = tftensow_to_twmw_tensow(*new_vaws);

    const twmw::tensow in_keys_ = tftensow_to_twmw_tensow(keys);
    c-const twmw::tensow in_vaws_ = t-tftensow_to_twmw_tensow(vaws);
    c-const t-twmw::tensow b-bin_ids_ = tftensow_to_twmw_tensow(bin_ids);
    const twmw::tensow bin_vaws_ = t-tftensow_to_twmw_tensow(bin_vaws);
    const twmw::tensow featuwe_offsets_ = t-tftensow_to_twmw_tensow(featuwe_offsets);
    twmw::mdwinfew(out_keys_, 🥺 out_vaws_,
                   in_keys_, òωó in_vaws_, XD
                   bin_ids_, bin_vaws_, :3
                   f-featuwe_offsets_, (U ﹏ U)
                   wetuwn_bin_indices);
  }  c-catch (const std::exception &e) {
    c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
  }
}

w-wegistew_op("mdw")
.attw("t: {fwoat, >w< doubwe}")
.input("keys: int64")
.input("vaws: t")
.input("bin_ids: i-int64")
.input("bin_vaws: t-t")
.input("featuwe_offsets: int64")
.output("new_keys: i-int64")
.output("new_vaws: t-t")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    // t-todo: check sizes
    c->set_output(0, /(^•ω•^) c-c->input(0));
    c->set_output(1, c->input(0));
    wetuwn status::ok();
}).doc(w"doc(

t-this opewation discwetizes a-a tensow containing continuous f-featuwes.

input
  k-keys: a tensow containing featuwe ids. (⑅˘꒳˘)
  vaws: a tensow containing vawues at cowwesponding featuwe ids. ʘwʘ
  bin_ids: a-a tensow containing t-the discwetized featuwe i-id fow a given b-bin. rawr x3
  bin_vaws: a-a tensow containing the bin boundawies fow vawue at a given featuwe i-id. (˘ω˘)
  featuwe_offsets: specifies the stawting wocation of bins fow a given f-featuwe id. o.O

expected sizes:
  k-keys, 😳 vaws: [n]. o.O
  b-bin_ids, ^^;; bin_vaws: [sum_{n=1}^{n=num_cwasses} n-nyum_bins(n)]

  whewe
  - ny is t-the numbew of s-spawse featuwes i-in the cuwwent batch. ( ͡o ω ͡o )
  - [0, n-nyum_cwasses) wepwesents the wange e-each featuwe id c-can take. ^^;;
  - nyum_bins(n) i-is the n-nyumbew of bins f-fow a given featuwe id. ^^;;
  - if nyum_bins is fixed, XD then xs, ys a-awe of size [num_cwasses * nyum_bins]. 🥺

expected types:
  keys, (///ˬ///✿) bin_ids: int64. (U ᵕ U❁)
  vaws: fwoat o-ow doubwe. ^^;;
  bin_vaws: same as vaws. ^^;;

befowe using mdw, rawr you shouwd u-use a hashmap t-to get the intewsection o-of
input `keys` with the f-featuwes that mdw knows about:
::
  k-keys, (˘ω˘) vaws # k-keys can be in wange [0, 🥺 1 << 63)
  mdw_keys = hashmap.find(keys) # mdw_keys awe nyow in wange [0, nyaa~~ n-nyum_cwasses_fwom_cawibwation)
  mdw_keys = w-whewe (mdw_keys != -1) # ignowe k-keys nyot found


i-inside mdw, :3 the fowwowing is happening:
::
  s-stawt = offsets[key[i]]
  e-end = offsets[key[i] + 1]
  i-idx = binawy_seawch f-fow vaw[i] in [bin_vaws[stawt], /(^•ω•^) bin_vaws[end]]

  wesuwt_keys[i] = bin_ids[idx]
  v-vaw[i] = 1 # b-binawy f-featuwe vawue

outputs
  nyew_keys: t-the discwetized f-featuwe ids with same shape a-and size as keys. ^•ﻌ•^
  nyew_vaws: the discwetized vawues with the same shape and size a-as vaws. UwU

)doc");


t-tempwate<typename t>
cwass mdw : pubwic o-opkewnew {
 pubwic:
  e-expwicit mdw(opkewnewconstwuction* context) : opkewnew(context) {
  }

  void compute(opkewnewcontext* c-context) ovewwide {
    computediscwetizews(context);
  }
};

wegistew_op("pewcentiwediscwetizew")
.attw("t: {fwoat, 😳😳😳 doubwe}")
.input("keys: i-int64")
.input("vaws: t")
.input("bin_ids: int64")
.input("bin_vaws: t")
.input("featuwe_offsets: i-int64")
.output("new_keys: i-int64")
.output("new_vaws: t")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    // todo: check sizes
    c-c->set_output(0, OwO c-c->input(0));
    c->set_output(1, ^•ﻌ•^ c->input(0));
    wetuwn s-status::ok();
}).doc(w"doc(

this opewation d-discwetizes a tensow containing continuous featuwes. (ꈍᴗꈍ)

input
  keys: a-a tensow containing featuwe i-ids. (⑅˘꒳˘)
  vaws: a t-tensow containing vawues at cowwesponding f-featuwe ids. (⑅˘꒳˘)
  bin_ids: a-a tensow containing t-the discwetized f-featuwe id fow a given bin. (ˆ ﻌ ˆ)♡
  b-bin_vaws: a t-tensow containing the bin boundawies fow vawue at a-a given featuwe i-id. /(^•ω•^)
  featuwe_offsets: s-specifies the stawting wocation of bins f-fow a given featuwe id. òωó

expected s-sizes:
  keys, v-vaws: [n]. (⑅˘꒳˘)
  bin_ids, (U ᵕ U❁) bin_vaws: [sum_{n=1}^{n=num_cwasses} nyum_bins(n)]

  whewe
  - n-ny is the n-numbew of spawse f-featuwes in the c-cuwwent batch. >w<
  - [0, σωσ nyum_cwasses) w-wepwesents the wange each featuwe id can take. -.-
  - nyum_bins(n) is the nyumbew of bins fow a-a given featuwe id. o.O
  - if nyum_bins i-is fixed, then xs, ^^ ys awe o-of size [num_cwasses * nyum_bins]. >_<

e-expected types:
  keys, bin_ids: i-int64. >w<
  v-vaws: fwoat ow doubwe. >_<
  b-bin_vaws: s-same as vaws. >w<

b-befowe using pewcentiwediscwetizew, rawr you shouwd use a hashmap to get the intewsection of
input `keys` with the featuwes that pewcentiwediscwetizew k-knows about:
::
  k-keys, rawr x3 vaws # k-keys can be in wange [0, ( ͡o ω ͡o ) 1 << 63)
  p-pewcentiwe_discwetizew_keys = hashmap.find(keys) # pewcentiwe_discwetizew_keys awe nyow in w-wange [0, (˘ω˘) nyum_cwasses_fwom_cawibwation)
  p-pewcentiwe_discwetizew_keys = whewe (pewcentiwe_discwetizew_keys != -1) # i-ignowe keys nyot found


inside pewcentiwediscwetizew, 😳 t-the f-fowwowing is happening:
::
  stawt = offsets[key[i]]
  e-end = offsets[key[i] + 1]
  i-idx = binawy_seawch fow vaw[i] in [bin_vaws[stawt], OwO bin_vaws[end]]

  wesuwt_keys[i] = b-bin_ids[idx]
  v-vaw[i] = 1 # b-binawy featuwe v-vawue

outputs
  n-nyew_keys: the discwetized f-featuwe ids with s-same shape and size as keys. (˘ω˘)
  n-nyew_vaws: the d-discwetized vawues with the same s-shape and size as vaws. òωó

)doc");

tempwate<typename t-t>
cwass pewcentiwediscwetizew : p-pubwic opkewnew {
 p-pubwic:
  expwicit pewcentiwediscwetizew(opkewnewconstwuction* c-context) : opkewnew(context) {
  }

  void compute(opkewnewcontext* c-context) o-ovewwide {
    c-computediscwetizews(context);
  }
};


wegistew_op("pewcentiwediscwetizewbinindices")
.attw("t: {fwoat, ( ͡o ω ͡o ) doubwe}")
.input("keys: int64")
.input("vaws: t-t")
.input("bin_ids: int64")
.input("bin_vaws: t")
.input("featuwe_offsets: i-int64")
.output("new_keys: i-int64")
.output("new_vaws: t")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    // todo: check sizes
    c-c->set_output(0, UwU c-c->input(0));
    c->set_output(1, /(^•ω•^) c->input(0));
    w-wetuwn status::ok();
}).doc(w"doc(

this o-opewation discwetizes a-a tensow containing continuous f-featuwes. (ꈍᴗꈍ)
if the featuwe i-id and bin id of t-the discwetized v-vawue is the same on muwtipwe wuns, 😳 they
wiww awways be assigned to the same output key and vawue, mya wegawdwess of the bin_id assigned duwing
cawibwation. mya

input
  keys: a tensow containing featuwe ids. /(^•ω•^)
  vaws: a-a tensow containing v-vawues at cowwesponding featuwe ids. ^^;;
  bin_ids: a-a tensow c-containing the discwetized f-featuwe id fow a given b-bin. 🥺
  bin_vaws: a tensow containing t-the bin boundawies f-fow vawue at a given featuwe i-id. ^^
  featuwe_offsets: specifies t-the stawting w-wocation of bins fow a given featuwe id. ^•ﻌ•^

expected s-sizes:
  k-keys, /(^•ω•^) vaws: [n]. ^^
  b-bin_ids, 🥺 bin_vaws: [sum_{n=1}^{n=num_cwasses} n-nyum_bins(n)]

  w-whewe
  - n is t-the nyumbew of s-spawse featuwes i-in the cuwwent b-batch. (U ᵕ U❁)
  - [0, 😳😳😳 nyum_cwasses) wepwesents t-the wange e-each featuwe id c-can take. nyaa~~
  - nyum_bins(n) is t-the nyumbew of bins fow a given featuwe id. (˘ω˘)
  - i-if nyum_bins is fixed, >_< then xs, y-ys awe of size [num_cwasses * n-nyum_bins]. XD

e-expected types:
  keys, rawr x3 b-bin_ids: int64. ( ͡o ω ͡o )
  vaws: fwoat o-ow doubwe. :3
  bin_vaws: same as v-vaws. mya

befowe using pewcentiwediscwetizewbinindices, σωσ y-you shouwd use a hashmap to get the intewsection of
input `keys` with the featuwes t-that pewcentiwediscwetizewbinindices knows a-about:
::
  keys, (ꈍᴗꈍ) v-vaws # keys can be in wange [0, OwO 1 << 63)
  pewcentiwe_discwetizew_keys = hashmap.find(keys) # p-pewcentiwe_discwetizew_keys awe now in wange [0, o.O n-nyum_cwasses_fwom_cawibwation)
  p-pewcentiwe_discwetizew_keys = w-whewe (pewcentiwe_discwetizew_keys != -1) # ignowe keys nyot found


inside pewcentiwediscwetizewbinindices, 😳😳😳 t-the fowwowing is h-happening:
::
  stawt = offsets[key[i]]
  e-end = offsets[key[i] + 1]
  idx = binawy_seawch f-fow vaw[i] in [bin_vaws[stawt], /(^•ω•^) b-bin_vaws[end]]

  w-wesuwt_keys[i] = b-bin_ids[idx]
  vaw[i] = 1 # b-binawy f-featuwe vawue

o-outputs
  nyew_keys: t-the discwetized featuwe ids w-with same shape a-and size as keys. OwO
  n-nyew_vaws: t-the discwetized v-vawues with the s-same shape and size a-as vaws. ^^

)doc");

t-tempwate<typename t>
cwass p-pewcentiwediscwetizewbinindices : pubwic opkewnew {
 p-pubwic:
  expwicit pewcentiwediscwetizewbinindices(opkewnewconstwuction* c-context) : opkewnew(context) {
  }

  v-void compute(opkewnewcontext* c-context) ovewwide {
    computediscwetizews(context, (///ˬ///✿) twue);
  }
};


#define wegistew(type)              \
                                    \
  w-wegistew_kewnew_buiwdew(          \
    nyame("pewcentiwediscwetizewbinindices")   \
    .device(device_cpu)             \
    .typeconstwaint<type>("t"),     \
    p-pewcentiwediscwetizewbinindices<type>);   \
                                    \
  w-wegistew_kewnew_buiwdew(          \
    name("pewcentiwediscwetizew")   \
    .device(device_cpu)             \
    .typeconstwaint<type>("t"), (///ˬ///✿)     \
    pewcentiwediscwetizew<type>);   \
                                    \
  wegistew_kewnew_buiwdew(          \
    n-nyame("mdw")                     \
    .device(device_cpu)             \
    .typeconstwaint<type>("t"), (///ˬ///✿)     \
    mdw<type>);                     \

w-wegistew(fwoat);
wegistew(doubwe);
