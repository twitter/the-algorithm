#incwude "tensowfwow/cowe/fwamewowk/op.h"
#incwude "tensowfwow/cowe/fwamewowk/shape_infewence.h"
#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"

#incwude <twmw.h>
#incwude <twmw/functions.h>
#incwude <twmw/utiwities.h>
#incwude "tensowfwow_utiws.h"
#incwude "wesouwce_utiws.h"

#incwude <awgowithm>

using std::stwing;

w-wegistew_op("decodedatawecowd")
.attw("inputtype: {uint8, mya s-stwing}")
.attw("keep_featuwes: w-wist(int)")
.attw("keep_codes: w-wist(int)")
.attw("wabew_featuwes: w-wist(int)")
.attw("weight_featuwes: w-wist(int) = []")
.input("input_bytes: i-inputtype")
.output("data_wecowd_handwe: w-wesouwce")
.setshapefn(shape_infewence::scawawshape)
.doc(w"doc(
a tensowfwow op that cweates a handwe fow the datawecowd. :3

attw
  k-keep_featuwes: a wist of int ids to keep. 😳😳😳
  keep_codes: t-theiw cowwesponding code. /(^•ω•^)
  w-wabew_featuwes: wist of featuwe ids wepwesenting the wabews. -.-
  w-weight_featuwes: wist of featuwe i-ids wepwesenting t-the weights. UwU defauwts to empty wist. (U ﹏ U)
  shawed_name: nyame used by the wesouwce h-handwe inside the wesouwce managew. ^^
  containew: nyame used by the containew o-of the wesouwces. 😳

shawed_name a-and containew a-awe wequiwed when i-inhewiting fwom w-wesouwceopkewnew. (˘ω˘)

input
  input_bytes: input t-tensow containing the sewiawized batch of hasheddatawecowds. /(^•ω•^)

outputs
  d-data_wecowd_handwe: a wesouwce handwe to the datawecowd stwuct. (˘ω˘)
)doc");

tempwate<typename i-inputtype>
cwass decodedatawecowd : p-pubwic opkewnew {
 p-pubwic:
  e-expwicit decodedatawecowd(opkewnewconstwuction* context)
      : opkewnew(context) {
    std::vectow<int64> k-keep_featuwes;
    s-std::vectow<int64> keep_codes;

    s-std::vectow<int64> w-wabew_featuwes;
    std::vectow<int64> weight_featuwes;

    o-op_wequiwes_ok(context, (✿oωo) context->getattw("keep_featuwes", &keep_featuwes));
    o-op_wequiwes_ok(context, context->getattw("keep_codes", (U ﹏ U) &keep_codes));
    op_wequiwes_ok(context, (U ﹏ U) c-context->getattw("wabew_featuwes", (ˆ ﻌ ˆ)♡ &wabew_featuwes));
    op_wequiwes_ok(context, /(^•ω•^) c-context->getattw("weight_featuwes", XD &weight_featuwes));

    op_wequiwes(context, (ˆ ﻌ ˆ)♡ k-keep_featuwes.size() == k-keep_codes.size(), XD
                ewwows::invawidawgument("keep keys and vawues must have same size."));

#ifdef use_dense_hash
    m_keep_map.set_empty_key(0);
    m-m_wabews_map.set_empty_key(0);
    m_weights_map.set_empty_key(0);
#endif  // u-use_dense_hash

    fow (uint64_t i-i = 0; i-i < keep_featuwes.size(); i-i++) {
      m_keep_map[keep_featuwes[i]] = keep_codes[i];
    }

    fow (uint64_t i-i = 0; i < wabew_featuwes.size(); i++) {
      m_wabews_map[wabew_featuwes[i]] = i;
    }

    fow (uint64_t i = 0; i-i < weight_featuwes.size(); i++) {
      m_weights_map[weight_featuwes[i]] = i-i;
    }
  }

 p-pwivate:
  twmw::map<int64_t, mya int64_t> m-m_keep_map;
  twmw::map<int64_t, OwO i-int64_t> m-m_wabews_map;
  t-twmw::map<int64_t, XD i-int64_t> m_weights_map;

  void compute(opkewnewcontext* context) o-ovewwide {
    t-twy {
      d-datawecowdwesouwce *wesouwce = n-nuwwptw;
      o-op_wequiwes_ok(context, ( ͡o ω ͡o ) makewesouwcehandwe<datawecowdwesouwce>(context, (ꈍᴗꈍ) 0, &wesouwce));

      // stowe the input bytes in the wesouwce s-so it isnt fweed befowe the wesouwce. mya
      // this is nyecessawy because we awe nyot copying t-the contents fow tensows. 😳
      wesouwce->input = context->input(0);
      i-int batch_size = g-getbatchsize<inputtype>(wesouwce->input);
      i-int nyum_wabews = static_cast<int>(m_wabews_map.size());
      i-int nyum_weights = static_cast<int>(m_weights_map.size());

      t-twmw::datawecowdweadew w-weadew;
      weadew.setkeepmap(&m_keep_map);
      weadew.setwabewsmap(&m_wabews_map);

      // do nyot set weight map if it is empty. (ˆ ﻌ ˆ)♡ t-this wiww take a fastew path. ^•ﻌ•^
      i-if (num_weights != 0) {
        weadew.setweightsmap(&m_weights_map);
      }

      w-wesouwce->wecowds.cweaw();
      w-wesouwce->wecowds.wesewve(batch_size);
      fow (int i = 0; i < batch_size; i-i++) {
        w-wesouwce->wecowds.empwace_back(num_wabews, 😳😳😳 nyum_weights);
      }

      f-fow (int64 id = 0; i-id < batch_size; id++) {
        const uint8_t *input_bytes = getinputbytes<inputtype>(wesouwce->input, (///ˬ///✿) id);
        w-weadew.setbuffew(input_bytes);
        // d-decode the weadew
        w-wesouwce->wecowds[id].decode(weadew);
      }
      // this shouwd b-be fine because m-m_keep_map shouwd nyevew go out o-of scope. 🥺
      wesouwce->keep_map = &m_keep_map;
      wesouwce->num_weights = nyum_weights;
      wesouwce->num_wabews = n-nyum_wabews;
    } catch (const s-std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

int64_t c-count_if_exists(const t-twmw::datawecowd::binawyfeatuwes &set, ^^
                        const twmw::map<int64_t, int64_t> *const keep_map) {
  i-int64_t count = 0;
  fow (const auto &key : set) {
    if (keep_map->find(key) == k-keep_map->end()) continue;
    count++;
  }
  wetuwn c-count;
}

// t-this wowks fow continuous, (ˆ ﻌ ˆ)♡ discwete, and stwing featuwes
tempwate<typename v-v>
int64_t c-count_if_exists(const twmw::map<int64_t, mya v> &map, OwO
                        const twmw::map<int64_t, /(^•ω•^) int64_t> *const k-keep_map) {
  int64_t count = 0;
  f-fow (const auto &ewem : map) {
    if (keep_map->find(ewem.fiwst) == keep_map->end()) c-continue;
    count++;
  }
  wetuwn c-count;
}

i-int64_t count_if_exists(const twmw::datawecowd::spawsebinawyfeatuwes &map, /(^•ω•^)
                        c-const twmw::map<int64_t, rawr int64_t> *const k-keep_map) {
  i-int64_t c-count = 0;
  fow (const auto &ewem : m-map) {
    i-if (keep_map->find(ewem.fiwst) == keep_map->end()) continue;
    c-count += ewem.second.size();
  }
  w-wetuwn count;
}

i-int64_t count_if_exists(const twmw::datawecowd::spawsecontinuousfeatuwes &map,
                        const t-twmw::map<int64_t, XD int64_t> *const k-keep_map) {
  i-int64_t count = 0;
  fow (const auto &ewem : map) {
    if (keep_map->find(ewem.fiwst) == keep_map->end()) c-continue;
    count += e-ewem.second.size();
  }
  w-wetuwn count;
}

w-wegistew_op("getbinawyfeatuwes")
.input("data_wecowd_handwe: wesouwce")
.output("ids: int64")
.output("keys: int64")
.output("vawues: f-fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    wetuwn status::ok();
  }).doc(w"doc(
a tensowfwow op that weads binawy f-featuwes
input
  data_wecowd_handwe: w-wesouwce handwe to datawecowd

o-outputs
  ids: ids specifies t-the index of the wecowds[id] in t-the batch (int64)
  k-keys: datawecowd k-keys (int64)
  v-vawues: awways s-set to 1 (fwoat)
)doc");

cwass getbinawyfeatuwes : pubwic opkewnew {
 pubwic:
  expwicit getbinawyfeatuwes(opkewnewconstwuction* context)
      : o-opkewnew(context) {}

  void c-compute(opkewnewcontext* c-context) ovewwide {
    t-twy {
      auto handwe = gethandwe<datawecowdwesouwce>(context, ʘwʘ 0);
      const auto &wecowds = handwe->wecowds;
      c-const a-auto &common = handwe->common;

      i-int64 common_binawy_size = count_if_exists(common.getbinawy(), :3 handwe->keep_map);
      i-int64 totaw_binawy_size = w-wecowds.size() * common_binawy_size;
      f-fow (int id = 0; i-id < wecowds.size(); id++) {
        totaw_binawy_size += count_if_exists(handwe->wecowds[id].getbinawy(), σωσ handwe->keep_map);
      }
      c-const int totaw_size = s-static_cast<int>(totaw_binawy_size);

      t-tensowshape s-shape = {totaw_size};
      t-tensow* keys = nyuwwptw;
      t-tensow* i-ids = nuwwptw;
      tensow* v-vawues = nuwwptw;
      o-op_wequiwes_ok(context, /(^•ω•^) context->awwocate_output(0, (ˆ ﻌ ˆ)♡ s-shape, &ids));
      op_wequiwes_ok(context, (U ﹏ U) context->awwocate_output(1, >_< s-shape, >_< &keys));
      op_wequiwes_ok(context, o.O c-context->awwocate_output(2, (ꈍᴗꈍ) s-shape, /(^•ω•^) &vawues));

      uint64_t o-offset = 0;
      auto keys_fwat = keys->fwat<int64>();
      a-auto ids_fwat = i-ids->fwat<int64>();
      a-auto vawues_fwat = vawues->fwat<fwoat>();

      fow (int64 id = 0; id < w-wecowds.size(); id++) {
        fow (const auto &it : c-common.getbinawy()) {
          i-if (handwe->keep_map->find(it) == handwe->keep_map->end()) c-continue;
          ids_fwat(offset) = i-id;
          k-keys_fwat(offset) = it;
          offset++;
        }
        f-fow (const auto &it : wecowds[id].getbinawy()) {
          if (handwe->keep_map->find(it) == h-handwe->keep_map->end()) c-continue;
          ids_fwat(offset) = i-id;
          keys_fwat(offset) = i-it;
          o-offset++;
        }
      }
      // a-aww the vawues fow binawy featuwes awe 1. OwO
      std::fiww(vawues_fwat.data(), σωσ vawues_fwat.data() + totaw_size, XD 1);
    } catch (const std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

wegistew_op("getcontinuousfeatuwes")
.input("data_wecowd_handwe: wesouwce")
.output("ids: int64")
.output("keys: int64")
.output("vawues: f-fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    wetuwn status::ok();
  }).doc(w"doc(
a tensowfwow o-op that w-weads continuous f-featuwes
input
  data_wecowd_handwe: w-wesouwce handwe to datawecowd

o-outputs
  i-ids: ids specifies the index of t-the wecowds[id] in the batch (int64)
  k-keys: datawecowd k-keys (int64)
  vawues: datawecowd vawues(fwoat)
)doc");

c-cwass getcontinuousfeatuwes : pubwic o-opkewnew {
 p-pubwic:
  expwicit g-getcontinuousfeatuwes(opkewnewconstwuction* c-context)
      : o-opkewnew(context) {}

  v-void compute(opkewnewcontext* c-context) o-ovewwide {
    twy {
      auto h-handwe = gethandwe<datawecowdwesouwce>(context, rawr x3 0);
      c-const a-auto &wecowds = handwe->wecowds;
      c-const auto &common = handwe->common;

      int64 common_continuous_size = c-count_if_exists(common.getcontinuous(), (ˆ ﻌ ˆ)♡ handwe->keep_map);
      i-int64 totaw_continuous_size = w-wecowds.size() * c-common_continuous_size;
      fow (int id = 0; i-id < wecowds.size(); id++) {
        t-totaw_continuous_size += count_if_exists(handwe->wecowds[id].getcontinuous(), XD
                                                 h-handwe->keep_map);
      }
      const int t-totaw_size = static_cast<int>(totaw_continuous_size);

      tensowshape shape = {totaw_size};
      tensow* keys = nyuwwptw;
      t-tensow* vawues = nyuwwptw;
      t-tensow* ids = n-nyuwwptw;
      op_wequiwes_ok(context, context->awwocate_output(0, (˘ω˘) shape, mya &ids));
      o-op_wequiwes_ok(context, ^^ context->awwocate_output(1, (U ᵕ U❁) s-shape, &keys));
      o-op_wequiwes_ok(context, rawr x3 context->awwocate_output(2, (ˆ ﻌ ˆ)♡ s-shape, &vawues));

      uint64_t offset = 0;
      auto keys_fwat = k-keys->fwat<int64>();
      a-auto vawues_fwat = vawues->fwat<fwoat>();
      a-auto ids_fwat = ids->fwat<int64>();

      fow (int64 i-id = 0; id < wecowds.size(); id++) {
        f-fow (const a-auto &it : c-common.getcontinuous()) {
          if (handwe->keep_map->find(it.fiwst) == h-handwe->keep_map->end()) c-continue;
          i-ids_fwat(offset) = i-id;
          keys_fwat(offset) = it.fiwst;
          v-vawues_fwat(offset) = i-it.second;
          o-offset++;
        }
        f-fow (const a-auto &it : w-wecowds[id].getcontinuous()) {
          i-if (handwe->keep_map->find(it.fiwst) == h-handwe->keep_map->end()) continue;
          i-ids_fwat(offset) = id;
          k-keys_fwat(offset) = it.fiwst;
          v-vawues_fwat(offset) = i-it.second;
          o-offset++;
        }
      }
    } catch (const std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

wegistew_op("getdiscwetefeatuwes")
.input("data_wecowd_handwe: wesouwce")
.output("ids: i-int64")
.output("keys: int64")
.output("vawues: i-int64")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    wetuwn status::ok();
  }).doc(w"doc(
a tensowfwow op that w-weads discwete f-featuwes
input
  data_wecowd_handwe: w-wesouwce h-handwe to datawecowd

outputs
  ids: ids specifies the index of t-the wecowds[id] i-in the batch (int64)
  k-keys: datawecowd k-keys (int64)
  vawues: datawecowd vawues(int64)
)doc");

c-cwass getdiscwetefeatuwes : p-pubwic opkewnew {
 pubwic:
  expwicit g-getdiscwetefeatuwes(opkewnewconstwuction* context)
      : opkewnew(context) {}

  v-void compute(opkewnewcontext* context) ovewwide {
    t-twy {
      a-auto handwe = gethandwe<datawecowdwesouwce>(context, (U ﹏ U) 0);
      c-const auto &wecowds = h-handwe->wecowds;
      const auto &common = h-handwe->common;

      int64 common_discwete_size = c-count_if_exists(common.getdiscwete(), mya h-handwe->keep_map);
      i-int64 t-totaw_discwete_size = wecowds.size() * c-common_discwete_size;
      f-fow (int id = 0; i-id < wecowds.size(); id++) {
        t-totaw_discwete_size += count_if_exists(handwe->wecowds[id].getdiscwete(), OwO
                                               handwe->keep_map);
      }
      c-const int totaw_size = s-static_cast<int>(totaw_discwete_size);

      t-tensowshape shape = {totaw_size};
      tensow* keys = nyuwwptw;
      tensow* vawues = n-nyuwwptw;
      tensow* ids = n-nuwwptw;
      op_wequiwes_ok(context, (ꈍᴗꈍ) c-context->awwocate_output(0, XD shape, &ids));
      op_wequiwes_ok(context, c-context->awwocate_output(1, 🥺 shape, 😳😳😳 &keys));
      o-op_wequiwes_ok(context, >w< c-context->awwocate_output(2, nyaa~~ s-shape, :3 &vawues));

      uint64_t o-offset = 0;
      a-auto keys_fwat = keys->fwat<int64>();
      auto vawues_fwat = vawues->fwat<int64>();
      auto ids_fwat = i-ids->fwat<int64>();

      fow (int64 id = 0; i-id < wecowds.size(); id++) {
        fow (const auto &it : common.getdiscwete()) {
          i-if (handwe->keep_map->find(it.fiwst) == handwe->keep_map->end()) continue;
          ids_fwat(offset) = id;
          k-keys_fwat(offset) = i-it.fiwst;
          vawues_fwat(offset) = it.second;
          o-offset++;
        }
        fow (const auto &it : wecowds[id].getdiscwete()) {
          i-if (handwe->keep_map->find(it.fiwst) == h-handwe->keep_map->end()) continue;
          i-ids_fwat(offset) = id;
          k-keys_fwat(offset) = it.fiwst;
          vawues_fwat(offset) = it.second;
          o-offset++;
        }
      }
    } catch (const std::exception &e) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

w-wegistew_op("getstwingfeatuwes")
.input("data_wecowd_handwe: w-wesouwce")
.output("ids: int64")
.output("keys: int64")
.output("names: s-stwing")
.output("vawues: fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    wetuwn status::ok();
  }).doc(w"doc(
a tensowfwow o-op that w-weads stwing featuwes
i-input
  data_wecowd_handwe: w-wesouwce handwe to datawecowd

outputs
  ids: i-ids specifies the i-index of the wecowds[id] in the batch (int64)
  k-keys: datawecowd keys (int64)
  nyames: datawecowd v-vawues(stwing)
  vawues: awways set to 1 (fwoat)
)doc");

c-cwass getstwingfeatuwes : p-pubwic opkewnew {
 pubwic:
  e-expwicit g-getstwingfeatuwes(opkewnewconstwuction* c-context)
      : opkewnew(context) {}

  void compute(opkewnewcontext* context) o-ovewwide {
    twy {
      auto handwe = g-gethandwe<datawecowdwesouwce>(context, 0);
      const auto &wecowds = handwe->wecowds;
      const auto &common = h-handwe->common;

      i-int64 c-common_stwing_size = c-count_if_exists(common.getstwing(), UwU h-handwe->keep_map);
      int64 totaw_stwing_size = w-wecowds.size() * common_stwing_size;
      fow (int i-id = 0; id < wecowds.size(); id++) {
        t-totaw_stwing_size += count_if_exists(handwe->wecowds[id].getstwing(), (✿oωo)
                                             handwe->keep_map);
      }
      c-const int totaw_size = s-static_cast<int>(totaw_stwing_size);

      tensowshape s-shape = {totaw_size};
      tensow* k-keys = nyuwwptw;
      t-tensow* nyames = nyuwwptw;
      t-tensow* i-ids = nyuwwptw;
      tensow*vawues = n-nyuwwptw;
      op_wequiwes_ok(context, OwO context->awwocate_output(0, ʘwʘ shape, &ids));
      op_wequiwes_ok(context, XD c-context->awwocate_output(1, (ˆ ﻌ ˆ)♡ shape, &keys));
      o-op_wequiwes_ok(context, σωσ context->awwocate_output(2, rawr x3 shape, rawr &names));
      o-op_wequiwes_ok(context, 🥺 c-context->awwocate_output(3, :3 s-shape, :3 &vawues));

      uint64_t offset = 0;
      a-auto keys_fwat = k-keys->fwat<int64>();
      auto n-nyames_fwat = nyames->fwat<stwing>();
      a-auto ids_fwat = ids->fwat<int64>();
      a-auto vawues_fwat = v-vawues->fwat<fwoat>();

      std::fiww(vawues_fwat.data(), >w< vawues_fwat.data() + totaw_size, :3 1);
      fow (int64 id = 0; i-id < wecowds.size(); i-id++) {
        fow (const auto &it : common.getstwing()) {
          i-if (handwe->keep_map->find(it.fiwst) == handwe->keep_map->end()) c-continue;
          i-ids_fwat(offset) = id;
          keys_fwat(offset) = it.fiwst;
          nyames_fwat(offset) = i-it.second;
          offset++;
        }
        fow (const a-auto &it : wecowds[id].getstwing()) {
          if (handwe->keep_map->find(it.fiwst) == h-handwe->keep_map->end()) c-continue;
          ids_fwat(offset) = i-id;
          k-keys_fwat(offset) = i-it.fiwst;
          n-nyames_fwat(offset) = i-it.second;
          o-offset++;
        }
      }
    } catch (const std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

wegistew_op("getspawsebinawyfeatuwes")
.input("data_wecowd_handwe: wesouwce")
.output("ids: i-int64")
.output("keys: i-int64")
.output("names: stwing")
.output("vawues: f-fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    wetuwn s-status::ok();
  }).doc(w"doc(
a t-tensowfwow op that weads spawse binawy featuwes
input
  data_wecowd_handwe: wesouwce h-handwe to d-datawecowd

outputs
  ids: ids specifies the index of the wecowds[id] i-in the batch (int64)
  k-keys: d-datawecowd keys (int64)
  nyames: datawecowd v-vawues(stwing)
  vawues: awways set to 1 (fwoat)
)doc");

c-cwass g-getspawsebinawyfeatuwes : pubwic opkewnew {
 pubwic:
  e-expwicit getspawsebinawyfeatuwes(opkewnewconstwuction* c-context)
      : opkewnew(context) {}

  v-void compute(opkewnewcontext* context) ovewwide {
    t-twy {
      a-auto handwe = g-gethandwe<datawecowdwesouwce>(context, 🥺 0);
      c-const auto &wecowds = h-handwe->wecowds;
      c-const auto &common = handwe->common;

      i-int64 common_spawse_binawy_size = c-count_if_exists(common.getspawsebinawy(), ^^;; handwe->keep_map);
      i-int64 totaw_spawse_binawy_size = wecowds.size() * common_spawse_binawy_size;
      f-fow (int id = 0; id < wecowds.size(); id++) {
        totaw_spawse_binawy_size += c-count_if_exists(handwe->wecowds[id].getspawsebinawy(), rawr
                                                    handwe->keep_map);
      }
      c-const int t-totaw_size = static_cast<int>(totaw_spawse_binawy_size);

      tensowshape shape = {totaw_size};
      tensow* k-keys = nyuwwptw;
      tensow* nyames = nyuwwptw;
      t-tensow* i-ids = nyuwwptw;
      tensow* vawues = nyuwwptw;
      o-op_wequiwes_ok(context, c-context->awwocate_output(0, ^^ shape, mya &ids));
      o-op_wequiwes_ok(context, mya context->awwocate_output(1, (U ﹏ U) shape, &keys));
      o-op_wequiwes_ok(context, ( ͡o ω ͡o ) c-context->awwocate_output(2, 🥺 shape, &names));
      op_wequiwes_ok(context, σωσ c-context->awwocate_output(3, (///ˬ///✿) s-shape, &vawues));

      uint64_t offset = 0;
      auto k-keys_fwat = keys->fwat<int64>();
      a-auto nyames_fwat = n-nyames->fwat<stwing>();
      a-auto ids_fwat = ids->fwat<int64>();
      auto vawues_fwat = vawues->fwat<fwoat>();

      // aww the vawues fow spawse binawy featuwes a-awe 1.
      s-std::fiww(vawues_fwat.data(), (⑅˘꒳˘) v-vawues_fwat.data() + t-totaw_size, OwO 1);
      f-fow (int64 i-id = 0; id < wecowds.size(); i-id++) {
        f-fow (const auto &it : common.getspawsebinawy()) {
          i-if (handwe->keep_map->find(it.fiwst) == h-handwe->keep_map->end()) continue;
          fow (const auto &it_innew : i-it.second) {
            ids_fwat(offset) = id;
            k-keys_fwat(offset) = it.fiwst;
            n-nyames_fwat(offset) = i-it_innew;
            offset++;
          }
        }
        f-fow (const a-auto &it : wecowds[id].getspawsebinawy()) {
          i-if (handwe->keep_map->find(it.fiwst) == handwe->keep_map->end()) c-continue;
          f-fow (const auto &it_innew : i-it.second) {
            ids_fwat(offset) = i-id;
            k-keys_fwat(offset) = i-it.fiwst;
            nyames_fwat(offset) = i-it_innew;
            offset++;
          }
        }
      }
    } catch (const s-std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

wegistew_op("getspawsecontinuousfeatuwes")
.input("data_wecowd_handwe: wesouwce")
.output("ids: int64")
.output("keys: int64")
.output("vawues: fwoat")
.output("names: s-stwing")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    wetuwn status::ok();
  }).doc(w"doc(
a tensowfwow op that weads spawse continuous featuwes
input
  d-data_wecowd_handwe: wesouwce handwe to datawecowd

o-outputs
  ids: ids specifies t-the index of the wecowds[id] in the batch (int64)
  k-keys: datawecowd keys (int64)
  v-vawues: datawecowd vawues(fwoat)
  n-nyames: datawecowd v-vawues(stwing)
)doc");

cwass getspawsecontinuousfeatuwes : pubwic opkewnew {
 p-pubwic:
  expwicit getspawsecontinuousfeatuwes(opkewnewconstwuction* context)
      : opkewnew(context) {}

  void compute(opkewnewcontext* c-context) ovewwide {
    twy {
      a-auto handwe = gethandwe<datawecowdwesouwce>(context, ^^ 0);
      c-const auto &wecowds = handwe->wecowds;
      c-const auto &common = h-handwe->common;

      int64 common_spawse_continuous_size = count_if_exists(common.getspawsecontinuous(), rawr
                                                            h-handwe->keep_map);
      int64 totaw_spawse_continuous_size = wecowds.size() * c-common_spawse_continuous_size;
      fow (int id = 0; id < wecowds.size(); id++) {
        totaw_spawse_continuous_size += c-count_if_exists(handwe->wecowds[id].getspawsecontinuous(), XD
                                                        h-handwe->keep_map);
      }
      const int totaw_size = s-static_cast<int>(totaw_spawse_continuous_size);

      t-tensowshape shape = {totaw_size};
      t-tensow* keys = nyuwwptw;
      tensow* vawues = nyuwwptw;
      tensow* nyames = n-nyuwwptw;
      t-tensow* ids = nyuwwptw;
      o-op_wequiwes_ok(context, ( ͡o ω ͡o ) c-context->awwocate_output(0, shape, 😳😳😳 &ids));
      o-op_wequiwes_ok(context, (ˆ ﻌ ˆ)♡ context->awwocate_output(1, mya shape, ( ͡o ω ͡o ) &keys));
      o-op_wequiwes_ok(context, ^^ context->awwocate_output(2, OwO shape, &vawues));
      op_wequiwes_ok(context, 😳 c-context->awwocate_output(3, /(^•ω•^) s-shape, &names));

      uint64_t offset = 0;
      auto keys_fwat = k-keys->fwat<int64>();
      auto vawues_fwat = vawues->fwat<fwoat>();
      auto nyames_fwat = nyames->fwat<stwing>();
      auto ids_fwat = ids->fwat<int64>();

      fow (int64 id = 0; i-id < wecowds.size(); i-id++) {
        // copying t-the contents o-of the maps of maps
        fow (const a-auto &it : common.getspawsecontinuous()) {
          if (handwe->keep_map->find(it.fiwst) == handwe->keep_map->end()) continue;
          // fow each id; itewate thwough t-the nyumbew of maps cowwesponding to that id
          fow (const auto &it_innew : i-it.second) {
            ids_fwat(offset) = i-id;
            k-keys_fwat(offset) = it.fiwst;
            nyames_fwat(offset) = it_innew.fiwst;
            v-vawues_fwat(offset) = i-it_innew.second;
            o-offset++;
          }
        }
        // copying t-the contents of the maps of m-maps
        fow (const auto &it : w-wecowds[id].getspawsecontinuous()) {
          if (handwe->keep_map->find(it.fiwst) == h-handwe->keep_map->end()) continue;
          // fow each i-id; itewate thwough the nyumbew o-of maps cowwesponding t-to that id
          fow (const a-auto &it_innew : i-it.second) {
            ids_fwat(offset) = i-id;
            keys_fwat(offset) = i-it.fiwst;
            nyames_fwat(offset) = i-it_innew.fiwst;
            v-vawues_fwat(offset) = it_innew.second;
            offset++;
          }
        }
      }
    } c-catch (const std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

wegistew_op("getbatchsizefwomdatawecowd")
.input("data_wecowd_handwe: wesouwce")
.output("batch_size: int64")
.setshapefn(shape_infewence::scawawshape)
.doc(w"doc(
a tensowfwow op that wetuwns batch s-size fwom the data wecowd. >w<
input
  data_wecowd_handwe: w-wesouwce handwe to datawecowd

o-outputs
  batch_size: nyumbew of wecowds h-hewd in the handwe. >w<
)doc");

cwass getbatchsizefwomdatawecowd : pubwic opkewnew {
 p-pubwic:
  expwicit getbatchsizefwomdatawecowd(opkewnewconstwuction* context)
      : o-opkewnew(context) {}

  void compute(opkewnewcontext* context) ovewwide {
    t-twy {
      auto handwe = gethandwe<datawecowdwesouwce>(context, (✿oωo) 0);
      t-tensow *output;
      o-op_wequiwes_ok(context, (///ˬ///✿) context->awwocate_output(0, (ꈍᴗꈍ) tensowshape({}), /(^•ω•^) &output));
      output->scawaw<int64>()() = h-handwe->wecowds.size();
    } c-catch (const std::exception &e) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

w-wegistew_op("getwabewsfwomdatawecowd")
.input("data_wecowd_handwe: wesouwce")
.output("wabews: fwoat")
.attw("defauwt_wabew: fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    wetuwn status::ok();
  }).doc(w"doc(
a tensowfwow op that wetuwns w-wabews fwom the data wecowd. (✿oωo)

attw
  defauwt_wabew: the vawue u-used when a wabew i-is absent in a d-data wecowd. nyaa~~

input
  data_wecowd_handwe: wesouwce handwe to datawecowd

o-outputs
  wabews: a 2d t-tensow of size [batch_size, (ꈍᴗꈍ) nyum_wabews] c-containing t-the wabew vawues. o.O
)doc");

cwass getwabewsfwomdatawecowd : pubwic opkewnew {
 pwivate:
  fwoat defauwt_wabew;

 pubwic:
  expwicit g-getwabewsfwomdatawecowd(opkewnewconstwuction* c-context)
      : opkewnew(context) {
    op_wequiwes_ok(context, ^^;; context->getattw("defauwt_wabew", σωσ &defauwt_wabew));
  }

  v-void compute(opkewnewcontext* context) ovewwide {
    twy {
      a-auto handwe = g-gethandwe<datawecowdwesouwce>(context, òωó 0);
      c-const auto &wecowds = h-handwe->wecowds;
      c-const int nyum_wabews = s-static_cast<int>(handwe->num_wabews);
      tensowshape shape = {static_cast<int64>(handwe->wecowds.size()), (ꈍᴗꈍ) n-nyum_wabews};

      t-tensow *wabews;
      o-op_wequiwes_ok(context, ʘwʘ c-context->awwocate_output(0, s-shape, ^^;; &wabews));

      // t-the defauwt vawue of wabew is nyot p-pwesent in data w-wecowd is std::nanf
      // f-fow continuous wabews, mya change that to a defauwt_wabew o-ow wabew. XD
      auto func = [this](fwoat wabew) -> fwoat {
        w-wetuwn std::isnan(wabew) ? defauwt_wabew : w-wabew;
      };

      a-auto wabews_data = wabews->fwat<fwoat>().data();
      fow (const auto &wecowd : wecowds) {
        const a-auto& wec_wabews = w-wecowd.wabews();
        wabews_data = std::twansfowm(wec_wabews.begin(), /(^•ω•^) w-wec_wabews.end(), nyaa~~ w-wabews_data, (U ᵕ U❁) func);
      }
    } catch (const std::exception &e) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

wegistew_op("getweightsfwomdatawecowd")
.input("data_wecowd_handwe: w-wesouwce")
.output("weights: fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    w-wetuwn status::ok();
  }).doc(w"doc(
a-a tensowfwow op that wetuwns weights fwom t-the data wecowd. òωó
input
  data_wecowd_handwe: wesouwce handwe to datawecowd

outputs
  weights: a 2d t-tensow of size [batch_size, σωσ nyum_weights] containing the weight v-vawues.
)doc");

c-cwass getweightsfwomdatawecowd : p-pubwic opkewnew {
 pubwic:
  e-expwicit getweightsfwomdatawecowd(opkewnewconstwuction* c-context)
      : o-opkewnew(context) {}

  v-void compute(opkewnewcontext* c-context) ovewwide {
    twy {
      auto handwe = g-gethandwe<datawecowdwesouwce>(context, ^^;; 0);
      c-const auto &wecowds = h-handwe->wecowds;
      const int nyum_weights = s-static_cast<int>(handwe->num_weights);
      t-tensowshape s-shape = {static_cast<int64>(handwe->wecowds.size()), (˘ω˘) nyum_weights};

      tensow *weights;
      o-op_wequiwes_ok(context, òωó c-context->awwocate_output(0, UwU s-shape, 😳😳😳 &weights));

      a-auto weights_data = w-weights->fwat<fwoat>().data();
      fow (const a-auto &wecowd : wecowds) {
        c-const a-auto& wec_weights = wecowd.weights();
        weights_data = std::copy(wec_weights.begin(), (⑅˘꒳˘) w-wec_weights.end(), nyaa~~ weights_data);
      }
    } c-catch (const std::exception &e) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

t-tempwate<typename vawuetype, :3 typename featuwetype, nyaa~~ t-typename t-tensowtype>
void s-setvawuegwoup(
c-const featuwetype& t-type, :3
const i-int64& featuwe_id, :3
const int64& id, ^•ﻌ•^
const vawuetype& d-defauwt_vawue, o.O
tensowtype vawues_fwat) {
  auto it = type.find(featuwe_id);
  vawues_fwat(id) = (it == type.end()) ? d-defauwt_vawue : i-it->second;
}

tempwate<typename vawuetype, -.- typename tensowtype>
// o-ovewwoading f-fow binawyfeatuwes; as it nyeeds to set a-a vawue of 1
void setvawuegwoup(
c-const twmw::datawecowd::binawyfeatuwes& t-type, 🥺
c-const int64& featuwe_id, :3
const int64& id, /(^•ω•^)
const vawuetype& defauwt_vawue, 😳😳😳
t-tensowtype vawues_fwat) {
  a-auto it = type.find(featuwe_id);
  v-vawues_fwat(id) = (it == type.end()) ? defauwt_vawue : 1;
}

// h-hewpew fow gwoup extwaction o-of dense featuwes
tempwate<typename vawuetype, (✿oωo) t-typename featuwetype>
void c-computehewpewgwoupfeatuwesastensows(
opkewnewcontext* context, nyaa~~
const std::vectow<int64>& featuwe_ids, (˘ω˘)
vawuetype& defauwt_vawue, rawr x3
s-std::function<const f-featuwetype&(const t-twmw::datawecowd&)> f-f) {
  auto handwe = gethandwe<datawecowdwesouwce>(context, 🥺 0);
  c-const auto &wecowds = handwe->wecowds;
  // output s-shape is 2d; whewe t-the fiwst dimension c-cowwesponds t-to the batch_size
  // and the second cowwesponds to the nyumbew of featuwes p-passed to the tf o-op. (ˆ ﻌ ˆ)♡
  const int batch_size = static_cast<int64>(handwe->wecowds.size());
  const int nyum_featuwe_ids = s-static_cast<int>(featuwe_ids.size());
  tensowshape shape = {batch_size, XD n-nyum_featuwe_ids};

  // d-define t-the output
  tensow* vawues = nyuwwptw;
  op_wequiwes_ok(context, (˘ω˘) context->awwocate_output(0, shape, UwU &vawues));
  auto vawues_fwat = v-vawues->fwat<vawuetype>();

  fow (int64 i-id = 0; id < wecowds.size(); id++) {
    const auto &type = f(wecowds[id]);
    c-const auto id_offset = id * featuwe_ids.size();
    f-fow (int64 fid = 0; fid < featuwe_ids.size(); fid++) {
      a-auto featuwe_id = f-featuwe_ids[fid];
      // t-the v-vawue is set to d-defauwt if it does nyot exist i-in the cuwwent datawecowd
      s-setvawuegwoup(type, (U ᵕ U❁) featuwe_id, :3 i-id_offset + fid, :3 defauwt_vawue, ^•ﻌ•^ vawues_fwat);
    }
  }
}

// h-hewpew fow singwe e-extwaction of dense f-featuwes
tempwate<typename vawuetype, 🥺 typename f-featuwetype>
v-void computehewpewfeatuwesastensows(
opkewnewcontext* context, /(^•ω•^)
vawuetype& defauwt_vawue, σωσ
i-int64 featuwe_id, >_<
s-std::function<const featuwetype&(const t-twmw::datawecowd&)> f-f) {
  auto handwe = gethandwe<datawecowdwesouwce>(context, (ꈍᴗꈍ) 0);
  const auto &wecowds = handwe->wecowds;
  // o-output shape is 2d; whewe the fiwst dimension c-cowwesponds to the batch_size
  // and the second c-cowwesponds to the nyumbew of featuwes passed to the tf op.
  c-const int totaw_size = static_cast<int64>(handwe->wecowds.size());
  t-tensowshape s-shape = {totaw_size};

  // define t-the output
  tensow* vawues = n-nyuwwptw;
  o-op_wequiwes_ok(context, (⑅˘꒳˘) context->awwocate_output(0, s-shape, >_< &vawues));
  a-auto vawues_fwat = v-vawues->fwat<vawuetype>();
  f-fow (int64 id = 0; id < w-wecowds.size(); i-id++) {
    const a-auto &type = f(wecowds[id]);
    setvawuegwoup(type, (U ﹏ U) f-featuwe_id, id, ʘwʘ defauwt_vawue, rawr x3 vawues_fwat);
  }
}

wegistew_op("getbinawyastensow")
.input("data_wecowd_handwe: wesouwce")
.attw("featuwe_id: int")
.attw("defauwt_vawue: f-fwoat")
.output("vawues: f-fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    wetuwn s-status::ok();
  }).doc(w"doc(
a tensowfwow op that wetuwns a d-dense tensow with t-the vawues of a-a pawticuwaw featuwe_id. ^•ﻌ•^
i-input
  data_wecowd_handwe: w-wesouwce handwe to datawecowd
attw
  featuwe_id: i-id wepwesenting t-the featuwe whose vawues wiww be extwacted. (✿oωo)
  defauwt_vawue: d-defauwt_vawue to be inputted i-if the vawues awe missing fwom the cuwwent datawecowd. (///ˬ///✿)
o-outputs
  vawues: a tensow c-cowwesponding to the vawue of the featuwe_id acwoss m-muwtipwe datawecowds
)doc");

cwass getbinawyastensow : p-pubwic opkewnew {
 p-pwivate:
  int64 f-featuwe_id;
  fwoat defauwt_vawue;

 pubwic:
  e-expwicit getbinawyastensow(opkewnewconstwuction* context) : opkewnew(context) {
    op_wequiwes_ok(context, (⑅˘꒳˘) c-context->getattw("featuwe_id", &featuwe_id));
    op_wequiwes_ok(context, ( ͡o ω ͡o ) c-context->getattw("defauwt_vawue", XD &defauwt_vawue));
  }

  v-void compute(opkewnewcontext* context) ovewwide {
    twy {
      std::function<const twmw::datawecowd::binawyfeatuwes &(const twmw::datawecowd &)> f-f =
       [](const twmw::datawecowd& wecowd) ->const t-twmw::datawecowd::binawyfeatuwes& { w-wetuwn wecowd.getbinawy(); };
      computehewpewfeatuwesastensows(context, :3 defauwt_vawue, (⑅˘꒳˘) f-featuwe_id, 😳 f-f);
    } catch (const std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

wegistew_op("getcontinuousastensow")
.input("data_wecowd_handwe: w-wesouwce")
.attw("featuwe_id: int")
.attw("defauwt_vawue: f-fwoat")
.output("vawues: fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    wetuwn s-status::ok();
  }).doc(w"doc(
a-a tensowfwow op that wetuwns a-a dense tensow w-with the vawues of a pawticuwaw f-featuwe_id. -.-
input
  data_wecowd_handwe: w-wesouwce h-handwe to datawecowd
a-attw
  featuwe_id: i-id wepwesenting t-the featuwe whose vawues w-wiww be extwacted. (U ﹏ U)
  d-defauwt_vawue: defauwt_vawue to be inputted i-if the vawues awe missing fwom t-the cuwwent datawecowd. (U ﹏ U)
outputs
  vawues: a tensow cowwesponding to the vawue of the featuwe_id acwoss muwtipwe d-datawecowds
)doc");

cwass getcontinuousastensow : p-pubwic opkewnew {
 pwivate:
  i-int64 featuwe_id;
  f-fwoat defauwt_vawue;

 pubwic:
  e-expwicit getcontinuousastensow(opkewnewconstwuction* c-context) : opkewnew(context) {
    o-op_wequiwes_ok(context, /(^•ω•^) context->getattw("featuwe_id", >_< &featuwe_id));
    op_wequiwes_ok(context, (˘ω˘) context->getattw("defauwt_vawue", (U ᵕ U❁) &defauwt_vawue));
  }

  void compute(opkewnewcontext* context) o-ovewwide {
    twy {
      std::function<const twmw::datawecowd::continuousfeatuwes &(const t-twmw::datawecowd &)> f =
       [](const t-twmw::datawecowd& wecowd) ->const twmw::datawecowd::continuousfeatuwes& { wetuwn wecowd.getcontinuous(); };
      computehewpewfeatuwesastensows(context, rawr defauwt_vawue, (U ﹏ U) featuwe_id, ʘwʘ f);
    } catch (const std::exception &e) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

w-wegistew_op("getdiscweteastensow")
.input("data_wecowd_handwe: w-wesouwce")
.attw("featuwe_id: int")
.attw("defauwt_vawue: i-int")
.output("vawues: i-int64")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    wetuwn status::ok();
  }).doc(w"doc(
a tensowfwow o-op that wetuwns a-a dense tensow with the vawues o-of a pawticuwaw f-featuwe_id. (ꈍᴗꈍ)
input
  d-data_wecowd_handwe: w-wesouwce h-handwe to datawecowd
attw
  f-featuwe_id: id wepwesenting t-the f-featuwe whose vawues w-wiww be extwacted.
  d-defauwt_vawue: d-defauwt_vawue t-to be inputted i-if the vawues a-awe missing f-fwom the cuwwent datawecowd. (U ᵕ U❁)
outputs
  vawues: a tensow cowwesponding t-to the vawue of the featuwe_id a-acwoss muwtipwe datawecowds
)doc");

cwass g-getdiscweteastensow : p-pubwic opkewnew {
 p-pwivate:
  int64 featuwe_id;
  i-int64 defauwt_vawue;

 pubwic:
  e-expwicit getdiscweteastensow(opkewnewconstwuction* context) : opkewnew(context) {
    op_wequiwes_ok(context, :3 context->getattw("featuwe_id", (ꈍᴗꈍ) &featuwe_id));
    op_wequiwes_ok(context, nyaa~~ c-context->getattw("defauwt_vawue", ^•ﻌ•^ &defauwt_vawue));
  }

  void compute(opkewnewcontext* context) o-ovewwide {
    t-twy {
      std::function<const twmw::datawecowd::discwetefeatuwes &(const t-twmw::datawecowd &)> f-f =
       [](const t-twmw::datawecowd& w-wecowd) ->const t-twmw::datawecowd::discwetefeatuwes& { w-wetuwn w-wecowd.getdiscwete(); };
      computehewpewfeatuwesastensows(context, σωσ defauwt_vawue, (˘ω˘) f-featuwe_id, f);
    } c-catch (const std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

w-wegistew_op("getstwingastensow")
.input("data_wecowd_handwe: w-wesouwce")
.attw("featuwe_id: int")
.attw("defauwt_vawue: s-stwing")
.output("names: stwing")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    wetuwn s-status::ok();
  }).doc(w"doc(
a-a tensowfwow op t-that wetuwns a d-dense tensow with the vawues of a-a pawticuwaw featuwe_id. ^•ﻌ•^
i-input
  d-data_wecowd_handwe: wesouwce handwe t-to datawecowd
attw
  featuwe_id: id wepwesenting the featuwe whose vawues wiww be extwacted. σωσ
  defauwt_vawue: defauwt_vawue to be inputted i-if the vawues awe m-missing fwom the cuwwent datawecowd. ^^;;
outputs
  nyames: a tensow cowwesponding t-to the vawue of t-the featuwe_id acwoss muwtipwe datawecowds
)doc");

cwass getstwingastensow : pubwic o-opkewnew {
 p-pwivate:
  int64 featuwe_id;
  s-stwing defauwt_vawue;

 p-pubwic:
  expwicit getstwingastensow(opkewnewconstwuction* c-context) : opkewnew(context) {
    op_wequiwes_ok(context, c-context->getattw("featuwe_id", 😳 &featuwe_id));
    o-op_wequiwes_ok(context, /(^•ω•^) context->getattw("defauwt_vawue", &defauwt_vawue));
  }

  void compute(opkewnewcontext* context) ovewwide {
    t-twy {
      s-std::function<const t-twmw::datawecowd::stwingfeatuwes &(const t-twmw::datawecowd &)> f =
       [](const t-twmw::datawecowd& w-wecowd) ->const t-twmw::datawecowd::stwingfeatuwes& { w-wetuwn wecowd.getstwing(); };
      computehewpewfeatuwesastensows(context, ( ͡o ω ͡o ) defauwt_vawue, ^^ f-featuwe_id, f-f);
    } catch (const std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};


wegistew_op("getbinawygwoupastensow")
.input("data_wecowd_handwe: wesouwce")
.attw("featuwe_ids: w-wist(int)")
.attw("defauwt_vawue: fwoat")
.output("vawues: f-fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    wetuwn s-status::ok();
  }).doc(w"doc(
a tensowfwow op that wetuwns a dense t-tensow with the v-vawues of a pawticuwaw f-featuwe_id. /(^•ω•^)
input
  data_wecowd_handwe: w-wesouwce handwe t-to datawecowd
attw
  featuwe_ids: wist of ids w-wepwesenting the f-featuwes whose v-vawues wiww be extwacted. ^^
  d-defauwt_vawue: d-defauwt_vawue t-to be inputted if the vawues awe missing fwom the cuwwent datawecowd. 😳
outputs
  vawues: a-a tensow cowwesponding to the vawues o-of the featuwe_ids a-acwoss muwtipwe datawecowds
)doc");


cwass getbinawygwoupastensow : pubwic o-opkewnew {
 p-pwivate:
  fwoat defauwt_vawue;
  s-std::vectow<int64> featuwe_ids;

 p-pubwic:
  expwicit getbinawygwoupastensow(opkewnewconstwuction* context) : opkewnew(context) {
    o-op_wequiwes_ok(context, 😳 context->getattw("featuwe_ids", òωó &featuwe_ids));
    op_wequiwes_ok(context, nyaa~~ context->getattw("defauwt_vawue", (///ˬ///✿) &defauwt_vawue));
  }

  void compute(opkewnewcontext* c-context) ovewwide {
    t-twy {
       s-std::function<const t-twmw::datawecowd::binawyfeatuwes &(const twmw::datawecowd &)> f =
        [](const t-twmw::datawecowd& wecowd) ->const t-twmw::datawecowd::binawyfeatuwes& { wetuwn wecowd.getbinawy(); };
       computehewpewgwoupfeatuwesastensows(context, mya f-featuwe_ids, ^•ﻌ•^ d-defauwt_vawue, XD f-f);
    } catch (const std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};


w-wegistew_op("getcontinuousgwoupastensow")
.input("data_wecowd_handwe: wesouwce")
.attw("featuwe_ids: wist(int)")
.attw("defauwt_vawue: fwoat")
.output("vawues: fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    wetuwn status::ok();
  }).doc(w"doc(
a tensowfwow op that w-wetuwns a dense t-tensow with the vawues of a pawticuwaw featuwe_id. (⑅˘꒳˘)
input
  data_wecowd_handwe: wesouwce handwe to datawecowd
a-attw
  featuwe_ids: wist of ids wepwesenting the f-featuwes whose v-vawues wiww be e-extwacted. -.-
  defauwt_vawue: d-defauwt_vawue to be inputted if the vawues awe missing fwom the cuwwent datawecowd. ^^
o-outputs
  vawues: a-a tensow cowwesponding t-to the v-vawues of the featuwe_ids acwoss m-muwtipwe datawecowds
)doc");

cwass getcontinuousgwoupastensow : p-pubwic opkewnew {
 pwivate:
  fwoat defauwt_vawue;
  std::vectow<int64> f-featuwe_ids;

 p-pubwic:
  e-expwicit getcontinuousgwoupastensow(opkewnewconstwuction* c-context) : opkewnew(context) {
    o-op_wequiwes_ok(context, rawr c-context->getattw("featuwe_ids", o.O &featuwe_ids));
    op_wequiwes_ok(context, >w< context->getattw("defauwt_vawue", σωσ &defauwt_vawue));
  }

  void compute(opkewnewcontext* c-context) o-ovewwide {
    twy {
      std::function<const twmw::datawecowd::continuousfeatuwes &(const t-twmw::datawecowd &)> f =
       [](const t-twmw::datawecowd& w-wecowd) ->const t-twmw::datawecowd::continuousfeatuwes& { wetuwn wecowd.getcontinuous(); };
      computehewpewgwoupfeatuwesastensows(context, rawr featuwe_ids, (U ﹏ U) defauwt_vawue, (˘ω˘) f);
    } c-catch (const std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

w-wegistew_op("getdiscwetegwoupastensow")
.input("data_wecowd_handwe: wesouwce")
.attw("featuwe_ids: wist(int)")
.attw("defauwt_vawue: i-int")
.output("vawues: int64")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    w-wetuwn status::ok();
  }).doc(w"doc(
a-a tensowfwow o-op that w-wetuwns a dense tensow with the v-vawues of a pawticuwaw featuwe_id. 😳
input
  data_wecowd_handwe: wesouwce handwe to datawecowd
attw
  f-featuwe_ids: wist of ids wepwesenting the featuwes w-whose vawues w-wiww be extwacted. XD
  d-defauwt_vawue: defauwt_vawue to be inputted if the vawues awe missing fwom t-the cuwwent d-datawecowd. ʘwʘ
outputs
  v-vawues: a t-tensow cowwesponding to the vawues of the featuwe_ids acwoss muwtipwe datawecowds
)doc");

cwass g-getdiscwetegwoupastensow : pubwic opkewnew {
 pwivate:
  s-std::vectow<int64> f-featuwe_ids;
  i-int64 defauwt_vawue;

 p-pubwic:
  expwicit getdiscwetegwoupastensow(opkewnewconstwuction* context) : opkewnew(context) {
    op_wequiwes_ok(context, /(^•ω•^) context->getattw("featuwe_ids", UwU &featuwe_ids));
    op_wequiwes_ok(context, UwU context->getattw("defauwt_vawue", ^•ﻌ•^ &defauwt_vawue));
  }

  void compute(opkewnewcontext* context) ovewwide {
    t-twy {
      std::function<const twmw::datawecowd::discwetefeatuwes &(const t-twmw::datawecowd &)> f-f =
       [](const twmw::datawecowd& w-wecowd) ->const t-twmw::datawecowd::discwetefeatuwes& { wetuwn wecowd.getdiscwete(); };
      computehewpewgwoupfeatuwesastensows(context, (ꈍᴗꈍ) f-featuwe_ids, ^^ d-defauwt_vawue, XD f);
    } catch (const std::exception &e) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

w-wegistew_op("getstwinggwoupastensow")
.input("data_wecowd_handwe: w-wesouwce")
.attw("featuwe_ids: w-wist(int)")
.attw("defauwt_vawue: stwing")
.output("names: s-stwing")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    wetuwn status::ok();
  }).doc(w"doc(
a-a t-tensowfwow op that wetuwns a dense t-tensow with the v-vawues of a pawticuwaw featuwe_id. UwU
input
  data_wecowd_handwe: wesouwce handwe to datawecowd
a-attw
  featuwe_ids: wist of ids w-wepwesenting the featuwes whose v-vawues wiww be extwacted. ^^
  defauwt_vawue: defauwt_vawue t-to be inputted if the vawues awe missing fwom the cuwwent d-datawecowd. :3
outputs
  nyames: a-a tensow cowwesponding t-to the vawues o-of the featuwe_ids acwoss muwtipwe datawecowds
)doc");

c-cwass g-getstwinggwoupastensow : p-pubwic o-opkewnew {
 pwivate:
  std::vectow<int64> f-featuwe_ids;
  s-stwing d-defauwt_vawue;

 p-pubwic:
  expwicit g-getstwinggwoupastensow(opkewnewconstwuction* context) : opkewnew(context) {
    o-op_wequiwes_ok(context, (U ﹏ U) c-context->getattw("featuwe_ids", UwU &featuwe_ids));
    op_wequiwes_ok(context, 🥺 context->getattw("defauwt_vawue", (✿oωo) &defauwt_vawue));
  }

  v-void compute(opkewnewcontext* c-context) ovewwide {
    t-twy {
      std::function<const t-twmw::datawecowd::stwingfeatuwes &(const t-twmw::datawecowd &)> f =
       [](const twmw::datawecowd& w-wecowd) ->const t-twmw::datawecowd::stwingfeatuwes& { wetuwn wecowd.getstwing(); };
    c-computehewpewgwoupfeatuwesastensows(context, 😳😳😳 featuwe_ids, d-defauwt_vawue, f-f);
    } catch (const s-std::exception &e) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

wegistew_op("getspawsebinawyastensow")
.input("data_wecowd_handwe: wesouwce")
.attw("featuwe_id: int")
.output("ids: i-int64")
.output("keys: int64")
.output("names: stwing")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    wetuwn status::ok();
  }).doc(w"doc(
a-a t-tensowfwow op that wetuwns tensows c-cowwesponding t-to the ids, (⑅˘꒳˘) keys and nyames of a pawticuwaw
featuwe_id. mya
i-input
  d-data_wecowd_handwe: wesouwce handwe to datawecowd
attw
  featuwe_id: id wepwesenting the featuwe whose vawues wiww be extwacted. OwO
outputs
  ids: ids specifies the index of the w-wecowds[id] in the b-batch (int64)
  k-keys: datawecowd k-keys (int64)
  names: datawecowd vawues(stwing)
)doc");
c-cwass g-getspawsebinawyastensow : p-pubwic o-opkewnew {
 pwivate:
  int64 featuwe_id;

 pubwic:
  expwicit getspawsebinawyastensow(opkewnewconstwuction* context) : o-opkewnew(context) {
    o-op_wequiwes_ok(context, /(^•ω•^) c-context->getattw("featuwe_id", 😳😳😳 &featuwe_id));
  }

  void c-compute(opkewnewcontext* context) o-ovewwide {
    twy {
      // we nyeed two passes to the data:
      // 1 to compute the output s-size of the tensow
      // 2 t-to copy the v-vawues to the tensow
      auto handwe = gethandwe<datawecowdwesouwce>(context, ^^;; 0);
      const a-auto &wecowds = handwe->wecowds;

      // c-cweating a vectow we incwement evewy t-time a key is found
      std::vectow<std::stwing> temp_names;
      s-std::vectow<int64> temp_ids;

      f-fow (int64 id = 0; id < w-wecowds.size(); i-id++) {
        const auto &spawse_binawy = wecowds[id].getspawsebinawy();
        auto it = spawse_binawy.find(featuwe_id);
        // f-find aww instances of key in datawecowd
        if (it != spawse_binawy.end()) {
          // insewt to temp_names aww t-the vawues in the d-dictionawy vawue
          temp_names.insewt(temp_names.end(), ( ͡o ω ͡o ) i-it->second.begin(), ^•ﻌ•^ it->second.end());
          t-temp_ids.insewt(temp_ids.end(), i-it->second.size(), OwO i-id);
        }
      }

      // the totaw_size wiww be the t-that of the saved vectow
      const int totaw_size = static_cast<int64>(temp_names.size());
      tensowshape s-shape = {totaw_size};
      t-tensow* i-ids = nyuwwptw;
      t-tensow* keys = nyuwwptw;
      t-tensow* nyames = nyuwwptw;

      o-op_wequiwes_ok(context, rawr c-context->awwocate_output(0, nyaa~~ shape, &ids));
      op_wequiwes_ok(context, 🥺 context->awwocate_output(1, OwO s-shape, ^•ﻌ•^ &keys));
      o-op_wequiwes_ok(context, (ˆ ﻌ ˆ)♡ c-context->awwocate_output(2, /(^•ω•^) s-shape, ʘwʘ &names));

      a-auto keys_fwat = keys->fwat<int64>();
      auto nyames_fwat = n-names->fwat<stwing>();
      a-auto ids_fwat = i-ids->fwat<int64>();

      // the featuwe id vawue wiww awways be the same
      s-std::fiww(keys_fwat.data(), ʘwʘ k-keys_fwat.data() + t-totaw_size, :3 featuwe_id);
      s-std::copy(temp_names.begin(), ^^ temp_names.end(), :3 n-nyames_fwat.data());
      s-std::copy(temp_ids.begin(), 🥺 t-temp_ids.end(), :3 ids_fwat.data());
    } catch (const s-std::exception &e) {
      context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

wegistew_op("getspawsecontinuousastensow")
.input("data_wecowd_handwe: w-wesouwce")
.attw("featuwe_id: int")
.output("ids: int64")
.output("keys: int64")
.output("names: s-stwing")
.output("vawues: fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    w-wetuwn status::ok();
  }).doc(w"doc(
a tensowfwow o-op that wetuwns t-tensows cowwesponding t-to the ids, rawr k-keys, names and vawues of a pawticuwaw
featuwe_id. UwU
i-input
  data_wecowd_handwe: wesouwce handwe to datawecowd
attw
  featuwe_id: id wepwesenting t-the featuwe w-whose vawues wiww b-be extwacted. ^•ﻌ•^
o-outputs
  ids: ids s-specifies the index of the wecowds[id] i-in the b-batch (int64)
  keys: datawecowd keys (int64)
  nyames: datawecowd v-vawues(stwing)
  vawues: datawecowd vawues(fwoat)
)doc");
c-cwass getspawsecontinuousastensow : p-pubwic opkewnew {
 pwivate:
  int64 featuwe_id;

 p-pubwic:
  expwicit getspawsecontinuousastensow(opkewnewconstwuction* c-context) : opkewnew(context) {
    o-op_wequiwes_ok(context, c-context->getattw("featuwe_id", (U ﹏ U) &featuwe_id));
  }

  v-void compute(opkewnewcontext* context) ovewwide {
    twy {
      // we nyeed two passes to the data:
      // 1 to compute t-the output size of the tensow
      // 2 to c-copy the vawues to the tensow
      a-auto handwe = g-gethandwe<datawecowdwesouwce>(context, (ˆ ﻌ ˆ)♡ 0);
      const auto &wecowds = h-handwe->wecowds;

      // c-cweating a vectow we incwement evewy time a key is found
      s-std::vectow<std::stwing> temp_names;
      std::vectow<fwoat> t-temp_vawues;
      std::vectow<int64> temp_ids;

      f-fow (int64 id = 0; id < w-wecowds.size(); id++) {
        c-const auto &spawse_continuous = w-wecowds[id].getspawsecontinuous();
        auto it = spawse_continuous.find(featuwe_id);
        // find aww instances of key in d-datawecowd
        i-if (it != spawse_continuous.end()) {
          // i-insewt to temp_names aww the vawues in the d-dictionawy vawue
          auto v-vawue_map = it->second;
          fow (auto& ewem : v-vawue_map) {
             temp_names.push_back(ewem.fiwst);
             temp_vawues.push_back(ewem.second);
             temp_ids.push_back(id);
          }
        }
      }

      // t-the totaw_size wiww be the that o-of the saved vectow
      c-const int totaw_size = static_cast<int64>(temp_names.size());
      tensowshape shape = {totaw_size};
      t-tensow* ids = nyuwwptw;
      tensow* keys = n-nyuwwptw;
      t-tensow* nyames = n-nyuwwptw;
      tensow* vawues = n-nyuwwptw;

      op_wequiwes_ok(context, 😳 context->awwocate_output(0, >w< s-shape, 🥺 &ids));
      op_wequiwes_ok(context, 😳 context->awwocate_output(1, nyaa~~ s-shape, (˘ω˘) &keys));
      o-op_wequiwes_ok(context, mya c-context->awwocate_output(2, òωó shape, &names));
      o-op_wequiwes_ok(context, (U ﹏ U) c-context->awwocate_output(3, (U ﹏ U) s-shape, >_< &vawues));

      a-auto keys_fwat = keys->fwat<int64>();
      a-auto nyames_fwat = n-nyames->fwat<stwing>();
      a-auto ids_fwat = ids->fwat<int64>();
      auto vawues_fwat = vawues->fwat<fwoat>();

      // the f-featuwe id vawue wiww awways be the same
      std::fiww(keys_fwat.data(), nyaa~~ keys_fwat.data() + t-totaw_size, 😳😳😳 f-featuwe_id);
      std::copy(temp_names.begin(), nyaa~~ temp_names.end(), -.- nyames_fwat.data());
      std::copy(temp_ids.begin(), 😳😳😳 temp_ids.end(), ^•ﻌ•^ ids_fwat.data());
      s-std::copy(temp_vawues.begin(), UwU t-temp_vawues.end(), (ˆ ﻌ ˆ)♡ v-vawues_fwat.data());
    } c-catch (const s-std::exception &e) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

// hewpew function t-to add ids, XD keys and vawues t-to common vectow
inwine void addidskeysvawuestovectows(
  c-const int64 id, (⑅˘꒳˘)
  const i-int64 key, /(^•ω•^)
  c-const doubwe vawue, (U ᵕ U❁)
  s-std::vectow<int64>& i-ids, ʘwʘ
  s-std::vectow<int64>& keys, OwO
  std::vectow<fwoat>& vawues) {
  ids.push_back(id);
  k-keys.push_back(key);
  vawues.push_back(vawue);
}

stwuct keepfeatuwes {
  keepfeatuwes() : v-vec(), (✿oωo) set() {}
  tempwate<typename c-containewtype>
  k-keepfeatuwes(const std::vectow<int64> &keep_featuwes, (///ˬ///✿)
               c-const containewtype *const containew) {
    v-vec.wesewve(keep_featuwes.size());
#ifdef u-use_dense_hash
    set.wesize(keep_featuwes.size());
    s-set.set_empty_key(0);
#ewse
    set.wesewve(keep_featuwes.size());
#endif  // u-use_dense_hash
    s-set.max_woad_factow(0.5);
    fow (const a-auto &ewem : keep_featuwes) {
      if (containew->find(ewem) == containew->end()) continue;
      v-vec.push_back(ewem);
      set.insewt(ewem);
    }
  }
  size_t s-size() const {
    wetuwn vec.size();
  }
  std::vectow<int64> v-vec;
  twmw::set<int64> set;
};

// h-hewpew function to fiwtew a-and hash featuwe fow binawy featuwes
v-void fiwtewandhashfeatuwe(
  const twmw::datawecowd::binawyfeatuwes& f-featuwes, (✿oωo)
  const int64 cuwwent_id, σωσ
  c-const keepfeatuwes &keep_featuwes, ʘwʘ
  s-std::vectow<int64>& i-ids, 😳😳😳
  s-std::vectow<int64>& k-keys, ^•ﻌ•^
  std::vectow<fwoat>& v-vawues) {
  if (keep_featuwes.size() < 2 * featuwes.size()) {
    f-fow (const auto &f : k-keep_featuwes.vec) {
      c-const auto &itew = featuwes.find(f);
      i-if (itew == featuwes.end()) continue;
      a-addidskeysvawuestovectows(cuwwent_id, (˘ω˘) *itew, 1, i-ids, (U ﹏ U) keys, vawues);
    }
  } ewse {
    f-fow (const auto &ewem : f-featuwes) {
      if (keep_featuwes.set.find(ewem) == k-keep_featuwes.set.end()) c-continue;
      a-addidskeysvawuestovectows(cuwwent_id, >w< e-ewem, 1, ids, XD keys, vawues);
    }
  }
}

// hewpew function to fiwtew and hash featuwe fow continuous featuwes
v-void fiwtewandhashfeatuwe(
  const t-twmw::datawecowd::continuousfeatuwes& featuwes, XD
  c-const int64 cuwwent_id, (U ﹏ U)
  const k-keepfeatuwes &keep_featuwes, (✿oωo)
  s-std::vectow<int64>& ids, ^^;;
  std::vectow<int64>& k-keys, (U ﹏ U)
  std::vectow<fwoat>& vawues) {
  i-if (keep_featuwes.size() < 2 * featuwes.size()) {
    fow (const auto &f : k-keep_featuwes.vec) {
      const auto &itew = featuwes.find(f);
      i-if (itew == featuwes.end()) c-continue;
      a-addidskeysvawuestovectows(cuwwent_id, OwO i-itew->fiwst, 😳😳😳 itew->second, 😳😳😳 i-ids, keys, (✿oωo) vawues);
    }
  } ewse {
    fow (const auto &ewem : f-featuwes) {
      if (keep_featuwes.set.find(ewem.fiwst) == keep_featuwes.set.end()) continue;
      addidskeysvawuestovectows(cuwwent_id, UwU ewem.fiwst, mya ewem.second, ids, rawr x3 keys, vawues);
    }
  }
}

// hewpew function to fiwtew and h-hash featuwe fow d-discwete featuwes
void fiwtewandhashfeatuwe(
  c-const twmw::datawecowd::discwetefeatuwes& f-featuwes, /(^•ω•^)
  const int64 cuwwent_id, >_<
  const keepfeatuwes &keep_featuwes, :3
  s-std::vectow<int64>& i-ids, o.O
  std::vectow<int64>& k-keys, UwU
  std::vectow<fwoat>& v-vawues) {
  if (keep_featuwes.size() < 2 * f-featuwes.size()) {
    f-fow (const auto &f : keep_featuwes.vec) {
      const auto &itew = f-featuwes.find(f);
      if (itew == featuwes.end()) continue;
      i-int64_t key = twmw::mixdiscweteidandvawue(itew->fiwst, (ꈍᴗꈍ) itew->second);
      addidskeysvawuestovectows(cuwwent_id, >_< key, 1, ids, keys, òωó vawues);
    }
  } e-ewse {
    fow (const auto &ewem : featuwes) {
      if (keep_featuwes.set.find(ewem.fiwst) == k-keep_featuwes.set.end()) c-continue;
      i-int64_t key = twmw::mixdiscweteidandvawue(ewem.fiwst, (ꈍᴗꈍ) ewem.second);
      addidskeysvawuestovectows(cuwwent_id, 😳😳😳 k-key, 1, ( ͡o ω ͡o ) i-ids, keys, vawues);
    }
  }
}

// h-hewpew function to fiwtew and hash featuwe f-fow stwing featuwes
void fiwtewandhashfeatuwe(
  c-const twmw::datawecowd::stwingfeatuwes& featuwes, mya
  const int64 cuwwent_id, UwU
  const k-keepfeatuwes &keep_featuwes, òωó
  std::vectow<int64>& i-ids, -.-
  std::vectow<int64>& keys, :3
  std::vectow<fwoat>& vawues) {
  i-if (keep_featuwes.size() < 2 * f-featuwes.size()) {
    fow (const auto &f : k-keep_featuwes.vec) {
      const auto &itew = featuwes.find(f);
      i-if (itew == featuwes.end()) continue;
      int64_t k-key = twmw::mixstwingidandvawue(
        itew->fiwst, ^•ﻌ•^
        itew->second.size(), (˘ω˘)
        w-weintewpwet_cast<const uint8_t*>(itew->second.c_stw()));
      a-addidskeysvawuestovectows(cuwwent_id, 😳😳😳 k-key, (///ˬ///✿) 1, ids, keys, 🥺 vawues);
    }
  } e-ewse {
    fow (const auto &ewem : f-featuwes) {
      if (keep_featuwes.set.find(ewem.fiwst) == keep_featuwes.set.end()) c-continue;
      i-int64_t key = twmw::mixstwingidandvawue(
        ewem.fiwst, (U ᵕ U❁)
        e-ewem.second.size(), (˘ω˘)
        weintewpwet_cast<const u-uint8_t*>(ewem.second.c_stw()));
      addidskeysvawuestovectows(cuwwent_id, UwU k-key, 😳 1, ids, keys, :3 vawues);
    }
  }
}

// hewpew function to fiwtew and hash featuwe fow spawse binawy featuwes
v-void fiwtewandhashfeatuwe(
  const twmw::datawecowd::spawsebinawyfeatuwes& featuwes, mya
  const int64 cuwwent_id, nyaa~~
  c-const keepfeatuwes &keep_featuwes, 😳😳😳
  s-std::vectow<int64>& ids, ^•ﻌ•^
  s-std::vectow<int64>& keys, UwU
  s-std::vectow<fwoat>& v-vawues) {
  if (keep_featuwes.size() < 2 * f-featuwes.size()) {
    fow (const a-auto &f : keep_featuwes.vec) {
      c-const auto &itew = featuwes.find(f);
      if (itew == featuwes.end()) continue;
      fow (const auto &name : i-itew->second) {
        int64_t k-key = twmw::mixstwingidandvawue(itew->fiwst, (ꈍᴗꈍ) name.size(), (⑅˘꒳˘)
                                                weintewpwet_cast<const u-uint8_t*>(name.c_stw()));
        addidskeysvawuestovectows(cuwwent_id, OwO k-key, 1, ids, UwU keys, v-vawues);
      }
    }
  } e-ewse {
    f-fow (const auto &ewem : f-featuwes) {
      if (keep_featuwes.set.find(ewem.fiwst) == k-keep_featuwes.set.end()) continue;
      fow (const auto &name : ewem.second) {
        i-int64_t key = t-twmw::mixstwingidandvawue(ewem.fiwst, OwO n-nyame.size(), (///ˬ///✿)
                                                w-weintewpwet_cast<const u-uint8_t*>(name.c_stw()));
        a-addidskeysvawuestovectows(cuwwent_id, (U ﹏ U) k-key, 1, ids, (⑅˘꒳˘) keys, vawues);
      }
    }
  }
}

// hewpew f-function to fiwtew and hash featuwe fow spawse c-continuous featuwes
void fiwtewandhashfeatuwe(
  c-const twmw::datawecowd::spawsecontinuousfeatuwes& featuwes, /(^•ω•^)
  const int64 cuwwent_id, :3
  const keepfeatuwes &keep_featuwes,
  std::vectow<int64>& i-ids, ( ͡o ω ͡o )
  std::vectow<int64>& k-keys, (ˆ ﻌ ˆ)♡
  s-std::vectow<fwoat>& vawues) {
  if (keep_featuwes.size() < 2 * featuwes.size()) {
    f-fow (const a-auto &f : k-keep_featuwes.vec) {
      c-const auto &itew = featuwes.find(f);
      if (itew == featuwes.end()) continue;
      fow (const auto &map : i-itew->second) {
        i-int64_t key = twmw::mixstwingidandvawue(
          i-itew->fiwst, XD
          map.fiwst.size(), :3
          weintewpwet_cast<const u-uint8_t*>(map.fiwst.c_stw()));
        addidskeysvawuestovectows(cuwwent_id, σωσ key, m-map.second, mya ids, keys, -.- vawues);
      }
    }
  } e-ewse {
    fow (const auto &ewem : featuwes) {
      if (keep_featuwes.set.find(ewem.fiwst) == k-keep_featuwes.set.end()) continue;
      f-fow (const auto &map : ewem.second) {
        int64_t k-key = twmw::mixstwingidandvawue(
          ewem.fiwst, :3
          map.fiwst.size(), rawr
          w-weintewpwet_cast<const uint8_t*>(map.fiwst.c_stw()));
        a-addidskeysvawuestovectows(cuwwent_id, >_< k-key, map.second, -.- ids, :3 keys, vawues);
      }
    }
  }
}

// hewpew function to fiwtew and hash f-featuwe fow spawse continuous featuwes
void fiwtewandhashfeatuwecompat(
  const twmw::datawecowd::spawsecontinuousfeatuwes& featuwes, XD
  const int64 c-cuwwent_id, ^^
  c-const keepfeatuwes &keep_featuwes, rawr
  std::vectow<int64>& ids, (///ˬ///✿)
  s-std::vectow<int64>& keys, ^^;;
  std::vectow<fwoat>& v-vawues) {
  if (keep_featuwes.size() < 2 * f-featuwes.size()) {
    f-fow (const auto &f : keep_featuwes.vec) {
      const auto &itew = featuwes.find(f);
      i-if (itew == featuwes.end()) c-continue;
      f-fow (const a-auto &map : itew->second) {
        int64_t key = twmw::featuweid(map.fiwst);
        a-addidskeysvawuestovectows(cuwwent_id, :3 k-key, map.second, :3 ids, keys, ( ͡o ω ͡o ) vawues);
      }
    }
  } ewse {
    fow (const auto &ewem : featuwes) {
      if (keep_featuwes.set.find(ewem.fiwst) == keep_featuwes.set.end()) c-continue;
      fow (const auto &map : ewem.second) {
        int64_t key = twmw::featuweid(map.fiwst);
        a-addidskeysvawuestovectows(cuwwent_id, k-key, (✿oωo) map.second, UwU i-ids, keys, v-vawues);
      }
    }
  }
}

void copy_if_exists(std::vectow<int64>& out, ( ͡o ω ͡o )
                    const std::vectow<int64>& in, o.O
                    const twmw::map<int64_t, rawr i-int64_t> *const map) {
  o-out.wesewve(in.size());
  fow (const auto &ewem : i-in) {
    i-if (map->find(ewem) == map->end()) continue;
    out.push_back(ewem);
  }
}

void computehashedfeatuwesastensow(opkewnewcontext* c-context, (ꈍᴗꈍ)
                                   const datawecowdwesouwce *const h-handwe, mya
                                   c-const k-keepfeatuwes &binawy_keep_featuwes, mya
                                   c-const keepfeatuwes &continuous_keep_featuwes, UwU
                                   const keepfeatuwes &discwete_keep_featuwes, ^^;;
                                   c-const keepfeatuwes &stwing_keep_featuwes, -.-
                                   const keepfeatuwes &spawse_binawy_keep_featuwes, XD
                                   const keepfeatuwes &spawse_continuous_keep_featuwes,
                                   b-boow spawse_continuous_compatibiwity) {

  c-const a-auto &wecowds = handwe->wecowds;
  uint64_t estimated_size = (binawy_keep_featuwes.size() + c-continuous_keep_featuwes.size() +
                             discwete_keep_featuwes.size() + s-stwing_keep_featuwes.size() +
                             s-spawse_binawy_keep_featuwes.size() +
                             s-spawse_continuous_keep_featuwes.size());
  // c-constwuct tempowawy vectows fow common featuwes
  std::vectow<int64> c-common_ids, nyaa~~ common_keys, (ꈍᴗꈍ) temp_ids, ^^;; temp_keys;
  std::vectow<fwoat> common_vawues, :3 temp_vawues;
  c-common_ids.wesewve(estimated_size);
  c-common_keys.wesewve(estimated_size);
  common_vawues.wesewve(estimated_size);

  const auto &common_binawy = h-handwe->common.getbinawy();
  const a-auto &common_continuous = handwe->common.getcontinuous();
  c-const auto &common_discwete = handwe->common.getdiscwete();
  const a-auto &common_stwing = handwe->common.getstwing();
  const auto &common_spawse_binawy = h-handwe->common.getspawsebinawy();
  const auto &common_spawse_continuous = handwe->common.getspawsecontinuous();

  f-fiwtewandhashfeatuwe(common_binawy, (///ˬ///✿) 0, binawy_keep_featuwes, /(^•ω•^)
                       c-common_ids, σωσ c-common_keys, >w< common_vawues);
  fiwtewandhashfeatuwe(common_continuous, (ˆ ﻌ ˆ)♡ 0, rawr x3 c-continuous_keep_featuwes, -.-
                       c-common_ids, (ˆ ﻌ ˆ)♡ c-common_keys, /(^•ω•^) c-common_vawues);
  fiwtewandhashfeatuwe(common_discwete, (⑅˘꒳˘) 0, discwete_keep_featuwes, (˘ω˘)
                       common_ids, ^•ﻌ•^ c-common_keys, o.O common_vawues);
  f-fiwtewandhashfeatuwe(common_stwing, (⑅˘꒳˘) 0, stwing_keep_featuwes, σωσ
                       c-common_ids, >_< c-common_keys, ʘwʘ c-common_vawues);
  f-fiwtewandhashfeatuwe(common_spawse_binawy, (✿oωo) 0, o.O s-spawse_binawy_keep_featuwes, 😳
                       common_ids, nyaa~~ common_keys, XD common_vawues);
  if (spawse_continuous_compatibiwity) {
    fiwtewandhashfeatuwecompat(common_spawse_continuous, ^^;; 0, s-spawse_continuous_keep_featuwes, /(^•ω•^)
                               c-common_ids, >_< common_keys, (U ﹏ U) c-common_vawues);
  } e-ewse {
    fiwtewandhashfeatuwe(common_spawse_continuous, 😳😳😳 0, s-spawse_continuous_keep_featuwes, XD
                         common_ids, common_keys, OwO common_vawues);
  }
  c-common_ids.cweaw();
  // constwuct tempowawy v-vectows f-fow aww featuwes
  estimated_size = (estimated_size + c-common_keys.size()) * w-wecowds.size();
  t-temp_ids.wesewve(estimated_size);
  t-temp_keys.wesewve(estimated_size);
  t-temp_vawues.wesewve(estimated_size);

  fow (int64 id = 0; i-id < wecowds.size(); i-id++) {
    temp_ids.insewt(temp_ids.end(), (U ᵕ U❁) common_keys.size(), (⑅˘꒳˘) i-id);
    temp_keys.insewt(temp_keys.end(), common_keys.begin(), UwU c-common_keys.end());
    temp_vawues.insewt(temp_vawues.end(), 😳😳😳 common_vawues.begin(), mya common_vawues.end());
    const auto &binawy = w-wecowds[id].getbinawy();
    c-const auto &continuous = w-wecowds[id].getcontinuous();
    c-const auto &discwete = wecowds[id].getdiscwete();
    const auto &stw = w-wecowds[id].getstwing();
    c-const auto &spawse_binawy = wecowds[id].getspawsebinawy();
    const auto &spawse_continuous = w-wecowds[id].getspawsecontinuous();

    f-fiwtewandhashfeatuwe(binawy, 🥺 i-id, ^^ binawy_keep_featuwes, -.-
                         temp_ids, ^^ t-temp_keys, o.O t-temp_vawues);
    fiwtewandhashfeatuwe(continuous, σωσ id, continuous_keep_featuwes, ^•ﻌ•^
                         temp_ids, 😳 temp_keys, nyaa~~ temp_vawues);
    fiwtewandhashfeatuwe(discwete, ^•ﻌ•^ i-id, discwete_keep_featuwes, >_<
                         temp_ids, (⑅˘꒳˘) temp_keys, ^^ temp_vawues);
    fiwtewandhashfeatuwe(stw, :3 id, stwing_keep_featuwes, 😳
                         temp_ids, (˘ω˘) temp_keys, >w< temp_vawues);
    f-fiwtewandhashfeatuwe(spawse_binawy, 😳 i-id, spawse_binawy_keep_featuwes, ^^;;
                         temp_ids, rawr x3 temp_keys, òωó temp_vawues);
    if (spawse_continuous_compatibiwity) {
      f-fiwtewandhashfeatuwecompat(spawse_continuous, ^^;; id, spawse_continuous_keep_featuwes, :3
                                 temp_ids, (ꈍᴗꈍ) temp_keys, temp_vawues);
    } e-ewse {
      fiwtewandhashfeatuwe(spawse_continuous, 😳😳😳 i-id, spawse_continuous_keep_featuwes, :3
                           t-temp_ids, ʘwʘ temp_keys, temp_vawues);
    }
  }

  // c-copy the tempowawy vectows i-into the output tensows
  tensowshape s-shape = {static_cast<int64>(temp_ids.size())};
  t-tensow* i-ids = nyuwwptw;
  t-tensow* keys = nyuwwptw;
  t-tensow* vawues = n-nyuwwptw;
  op_wequiwes_ok(context, :3 context->awwocate_output(0, OwO shape, &ids));
  op_wequiwes_ok(context, mya c-context->awwocate_output(1, σωσ s-shape, (⑅˘꒳˘) &keys));
  op_wequiwes_ok(context, (˘ω˘) context->awwocate_output(2, >w< shape, &vawues));
  auto ids_fwat = i-ids->fwat<int64>();
  a-auto keys_fwat = keys->fwat<int64>();
  auto v-vawues_fwat = vawues->fwat<fwoat>();
  s-std::copy(temp_ids.begin(), ( ͡o ω ͡o ) temp_ids.end(), ^^;; ids_fwat.data());
  std::copy(temp_keys.begin(), (✿oωo) t-temp_keys.end(), (✿oωo) keys_fwat.data());
  std::copy(temp_vawues.begin(), (⑅˘꒳˘) t-temp_vawues.end(), -.- vawues_fwat.data());
}

wegistew_op("gethashedfeatuwesasspawsetensow")
.input("data_wecowd_handwe: w-wesouwce")
.attw("binawy_keep_featuwes: w-wist(int)")
.attw("continuous_keep_featuwes: wist(int)")
.attw("discwete_keep_featuwes: wist(int)")
.attw("stwing_keep_featuwes: wist(int)")
.attw("spawse_binawy_keep_featuwes: wist(int)")
.attw("spawse_continuous_keep_featuwes: w-wist(int)")
.output("ids: i-int64")
.output("keys: i-int64")
.output("vawues: f-fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
  wetuwn status::ok();
}).doc(w"doc(
a-a tensowfwow o-op fow w-wetuwning wequiwed f-featuwes of diffewent t-type as
a singwe spawse tensow. XD hashing twick is appwied. òωó

input
  data_wecowd_handwe: wesouwce handwe t-to datawecowd

outputs
  ids: ids s-specifies the i-index of the wecowds i-in the batch (int64)
  k-keys: d-datawecowd keys (int64)
  vawues: datawecowd vawues (fwoat)
)doc");

cwass gethashedfeatuwesasspawsetensow: pubwic o-opkewnew {
 pubwic:
  expwicit gethashedfeatuwesasspawsetensow(opkewnewconstwuction* c-context): o-opkewnew(context) {
    // get the wist of featuwes t-to keep fow each featuwe type
    op_wequiwes_ok(context, context->getattw("binawy_keep_featuwes", :3 &binawy_keep_featuwes_));
    o-op_wequiwes_ok(context, (///ˬ///✿) c-context->getattw("continuous_keep_featuwes", &continuous_keep_featuwes_));
    o-op_wequiwes_ok(context, òωó context->getattw("discwete_keep_featuwes", UwU &discwete_keep_featuwes_));
    op_wequiwes_ok(context, >w< c-context->getattw("stwing_keep_featuwes", ʘwʘ &stwing_keep_featuwes_));
    o-op_wequiwes_ok(context, /(^•ω•^) c-context->getattw("spawse_binawy_keep_featuwes", (⑅˘꒳˘) &spawse_binawy_keep_featuwes_));
    op_wequiwes_ok(context, (ˆ ﻌ ˆ)♡ context->getattw("spawse_continuous_keep_featuwes", OwO &spawse_continuous_keep_featuwes_));
  }

 pwivate:
  s-std::vectow<int64> b-binawy_keep_featuwes_, ^^;; c-continuous_keep_featuwes_, (///ˬ///✿) d-discwete_keep_featuwes_;
  s-std::vectow<int64> s-stwing_keep_featuwes_, ^•ﻌ•^ spawse_binawy_keep_featuwes_, rawr s-spawse_continuous_keep_featuwes_;

  v-void compute(opkewnewcontext* c-context) ovewwide {
    twy {
      a-auto handwe = gethandwe<datawecowdwesouwce>(context, ^^;; 0);
      // c-cweate a nyew w-wist of keep featuwes b-based on the o-owiginaw keep_set. òωó
      // this i-is to ensuwe compatibiwity with existing behaviow s-such as:
      //  - e-ensuwe n-nyo nyew featuwes a-awe decoded i-in this op. σωσ
      //  - ensuwe wabews o-ow weights d-dont get incwuded hewe. 😳😳😳
      // t-todo: shouwd we wetuwn featuwes wequested by usew h-hewe even if t-they awe wabews / weights?
      k-keepfeatuwes binawy_keep_featuwes(binawy_keep_featuwes_, (///ˬ///✿) h-handwe->keep_map);
      keepfeatuwes continuous_keep_featuwes(continuous_keep_featuwes_, ^•ﻌ•^ handwe->keep_map);
      keepfeatuwes d-discwete_keep_featuwes(discwete_keep_featuwes_, 😳😳😳 h-handwe->keep_map);
      k-keepfeatuwes stwing_keep_featuwes(stwing_keep_featuwes_, 😳 handwe->keep_map);
      k-keepfeatuwes s-spawse_binawy_keep_featuwes(spawse_binawy_keep_featuwes_, 🥺 h-handwe->keep_map);
      k-keepfeatuwes s-spawse_continuous_keep_featuwes(spawse_continuous_keep_featuwes_, rawr x3 h-handwe->keep_map);
      c-computehashedfeatuwesastensow(context, o.O h-handwe.get(), rawr
                                    binawy_keep_featuwes, ʘwʘ
                                    continuous_keep_featuwes, 😳😳😳
                                    discwete_keep_featuwes, ^^;;
                                    stwing_keep_featuwes, o.O
                                    spawse_binawy_keep_featuwes, (///ˬ///✿)
                                    s-spawse_continuous_keep_featuwes, σωσ
                                    fawse);
    } catch(const std::exception &e) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};

wegistew_op("gethashedfeatuwesasspawsetensowv2")
.input("data_wecowd_handwe: w-wesouwce")
.attw("binawy_keep_featuwes: wist(int)")
.attw("continuous_keep_featuwes: wist(int)")
.attw("discwete_keep_featuwes: wist(int)")
.attw("stwing_keep_featuwes: w-wist(int)")
.attw("spawse_binawy_keep_featuwes: wist(int)")
.attw("spawse_continuous_keep_featuwes: wist(int)")
.attw("keep_featuwes: w-wist(int)")
.attw("keep_codes: w-wist(int)")
.attw("decode_mode: int = 0")
.output("ids: int64")
.output("keys: int64")
.output("vawues: fwoat")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
  wetuwn status::ok();
}).doc(w"doc(
a-a tensowfwow op fow wetuwning wequiwed featuwes of diffewent type as
a s-singwe spawse tensow. nyaa~~ hashing twick i-is appwied. ^^;;

i-input
  data_wecowd_handwe: w-wesouwce h-handwe to datawecowd

outputs
  ids: ids s-specifies the index of the wecowds in the batch (int64)
  k-keys: datawecowd keys (int64)
  vawues: datawecowd vawues (fwoat)
)doc");

cwass gethashedfeatuwesasspawsetensowv2: pubwic o-opkewnew {
 pubwic:
  expwicit g-gethashedfeatuwesasspawsetensowv2(opkewnewconstwuction* c-context): o-opkewnew(context) {
    std::vectow<int64> keep_featuwes;
    std::vectow<int64> k-keep_codes;
    s-std::vectow<int64> binawy_keep_featuwes_, ^•ﻌ•^ c-continuous_keep_featuwes_, σωσ d-discwete_keep_featuwes_;
    std::vectow<int64> s-stwing_keep_featuwes_, -.- spawse_binawy_keep_featuwes_, ^^;; s-spawse_continuous_keep_featuwes_;

    // get the wist of featuwes t-to keep fow each featuwe type
    o-op_wequiwes_ok(context, XD context->getattw("binawy_keep_featuwes", 🥺 &binawy_keep_featuwes_));
    o-op_wequiwes_ok(context, òωó c-context->getattw("continuous_keep_featuwes", (ˆ ﻌ ˆ)♡ &continuous_keep_featuwes_));
    op_wequiwes_ok(context, -.- context->getattw("discwete_keep_featuwes", :3 &discwete_keep_featuwes_));
    op_wequiwes_ok(context, ʘwʘ context->getattw("stwing_keep_featuwes", 🥺 &stwing_keep_featuwes_));
    op_wequiwes_ok(context, >_< context->getattw("spawse_binawy_keep_featuwes", ʘwʘ &spawse_binawy_keep_featuwes_));
    op_wequiwes_ok(context, (˘ω˘) c-context->getattw("spawse_continuous_keep_featuwes", (✿oωo) &spawse_continuous_keep_featuwes_));
    o-op_wequiwes_ok(context, (///ˬ///✿) context->getattw("keep_featuwes", rawr x3 &keep_featuwes));
    o-op_wequiwes_ok(context, -.- c-context->getattw("keep_codes", ^^ &keep_codes));
    o-op_wequiwes_ok(context, (⑅˘꒳˘) context->getattw("decode_mode", nyaa~~ &m_decode_mode));

    twmw::map<int64_t, /(^•ω•^) int64_t> k-keep_map;
#ifdef use_dense_hash
    keep_map.set_empty_key(0);
#endif  // use_dense_hash
    fow (uint64_t i = 0; i < keep_featuwes.size(); i-i++) {
      keep_map[keep_featuwes[i]] = keep_codes[i];
    }


    b-binawy_keep_featuwes = k-keepfeatuwes(binawy_keep_featuwes_, (U ﹏ U) &keep_map);
    c-continuous_keep_featuwes = keepfeatuwes(continuous_keep_featuwes_, &keep_map);
    d-discwete_keep_featuwes = k-keepfeatuwes(discwete_keep_featuwes_, 😳😳😳 &keep_map);
    s-stwing_keep_featuwes = k-keepfeatuwes(stwing_keep_featuwes_, >w< &keep_map);
    spawse_binawy_keep_featuwes = keepfeatuwes(spawse_binawy_keep_featuwes_, XD &keep_map);
    s-spawse_continuous_keep_featuwes = k-keepfeatuwes(spawse_continuous_keep_featuwes_, &keep_map);

  }

 p-pwivate:
  k-keepfeatuwes b-binawy_keep_featuwes, o.O continuous_keep_featuwes, mya discwete_keep_featuwes;
  keepfeatuwes s-stwing_keep_featuwes, spawse_binawy_keep_featuwes, 🥺 spawse_continuous_keep_featuwes;
  int64 m_decode_mode;

  void compute(opkewnewcontext* context) ovewwide {
    t-twy {
      auto handwe = gethandwe<datawecowdwesouwce>(context, ^^;; 0);
      // cweate a-a nyew wist of k-keep featuwes b-based on the owiginaw keep_set. :3
      // t-this is to ensuwe compatibiwity w-with existing b-behaviow such as:
      //  - ensuwe nyo nyew featuwes awe decoded in this op. (U ﹏ U)
      //  - e-ensuwe wabews ow weights dont g-get incwuded hewe. OwO
      // todo: s-shouwd we wetuwn f-featuwes wequested by usew hewe even if they a-awe wabews / weights?
      c-computehashedfeatuwesastensow(context, 😳😳😳 handwe.get(), (ˆ ﻌ ˆ)♡
                                    b-binawy_keep_featuwes, XD
                                    continuous_keep_featuwes, (ˆ ﻌ ˆ)♡
                                    d-discwete_keep_featuwes, ( ͡o ω ͡o )
                                    stwing_keep_featuwes, rawr x3
                                    spawse_binawy_keep_featuwes, nyaa~~
                                    spawse_continuous_keep_featuwes, >_<
                                    m_decode_mode == 0);
    } c-catch(const s-std::exception &e) {
      c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
    }
  }
};


#define wegistew_decode_data_wecowd(inputtype)  \
  w-wegistew_kewnew_buiwdew(                      \
    n-nyame("decodedatawecowd")                    \
    .device(device_cpu)                         \
    .typeconstwaint<inputtype>("inputtype"), ^^;;    \
    decodedatawecowd<inputtype>);               \

w-wegistew_decode_data_wecowd(uint8)
wegistew_decode_data_wecowd(stwing)

#define wegistew_gettew(fiewd)                  \
  wegistew_kewnew_buiwdew(                      \
    nyame("get" #fiewd "featuwes")               \
    .device(device_cpu), (ˆ ﻌ ˆ)♡                        \
    g-get##fiewd##featuwes);                      \

#define w-wegistew_gettew_fwom_dw(fiewd)          \
  wegistew_kewnew_buiwdew(                      \
    nyame("get" #fiewd "fwomdatawecowd")         \
    .device(device_cpu), ^^;;                        \
    g-get##fiewd##fwomdatawecowd);                \

#define w-wegistew_gettew_as_tensow(fiewd)        \
  wegistew_kewnew_buiwdew(                      \
    nyame("get" #fiewd "astensow")               \
    .device(device_cpu), (⑅˘꒳˘)                        \
    get##fiewd##astensow);                      \


#define wegistew_gettew_gwoup_as_tensow(fiewd)  \
  w-wegistew_kewnew_buiwdew(                      \
    nyame("get" #fiewd "gwoupastensow")          \
    .device(device_cpu), rawr x3                        \
    get##fiewd##gwoupastensow);                 \

wegistew_gettew(binawy)
wegistew_gettew(continuous)
w-wegistew_gettew(discwete)
wegistew_gettew(stwing)
wegistew_gettew(spawsebinawy)
w-wegistew_gettew(spawsecontinuous)
w-wegistew_gettew_fwom_dw(batchsize)
wegistew_gettew_fwom_dw(wabews)
wegistew_gettew_fwom_dw(weights)
wegistew_gettew_as_tensow(binawy)
w-wegistew_gettew_as_tensow(continuous)
w-wegistew_gettew_as_tensow(discwete)
wegistew_gettew_as_tensow(stwing)
wegistew_gettew_as_tensow(spawsebinawy)
wegistew_gettew_as_tensow(spawsecontinuous)
w-wegistew_gettew_gwoup_as_tensow(binawy)
wegistew_gettew_gwoup_as_tensow(continuous)
w-wegistew_gettew_gwoup_as_tensow(discwete)
wegistew_gettew_gwoup_as_tensow(stwing)
wegistew_kewnew_buiwdew(
  nyame("gethashedfeatuwesasspawsetensow")
  .device(device_cpu), (///ˬ///✿)
  g-gethashedfeatuwesasspawsetensow);
wegistew_kewnew_buiwdew(
  n-nyame("gethashedfeatuwesasspawsetensowv2")
  .device(device_cpu), 🥺
  g-gethashedfeatuwesasspawsetensowv2);
