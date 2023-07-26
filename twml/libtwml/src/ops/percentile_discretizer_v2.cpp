#incwude "tensowfwow/cowe/fwamewowk/op.h"
#incwude "tensowfwow/cowe/fwamewowk/shape_infewence.h"
#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"
#incwude "tensowfwow/cowe/utiw/wowk_shawdew.h"

#incwude <twmw.h>
#incwude "tensowfwow_utiws.h"


using nyamespace tensowfwow;

v-void combinedcomputediscwetizews(
  o-opkewnewcontext*, XD
  i-int64_t, >w<
  c-const twmw::map<int64_t, òωó i-int64_t>&, (ꈍᴗꈍ)
  i-int64_t);

w-wegistew_op("pewcentiwediscwetizewv2")
.attw("t: {fwoat, rawr x3 d-doubwe}")
.input("input_ids: int64")
.input("input_vaws: t")
.input("bin_ids: int64")
.input("bin_vaws: t")
.input("featuwe_offsets: i-int64")
.input("stawt_compute: int64")
.input("end_compute: int64")
.attw("output_bits: int")
.attw("featuwe_ids: t-tensow = { dtype: dt_int64 }")
.attw("featuwe_indices: t-tensow = { dtype: dt_int64 }")
.attw("cost_pew_unit: int")
.output("new_keys: int64")
.output("new_vaws: t-t")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    // t-todo: check s-sizes
    c->set_output(0, rawr x3 c->input(0));
    c->set_output(1, σωσ c->input(0));
    wetuwn status::ok();
}).doc(w"doc(

this opewation d-discwetizes a tensow containing continuous featuwes (if cawibwated). (ꈍᴗꈍ)
  - nyote - c-choice of fwoat ow doubwe shouwd b-be consistent a-among inputs/output

i-input
  i-input_ids(int64): a tensow containing input featuwe i-ids (diwect fwom data wecowd). rawr
  input_vaws: a-a tensow containing input vawues at cowwesponding featuwe ids. ^^;;
    - i.e. rawr x3 input_ids[i] <-> input_vaws[i] f-fow each i
    - fwoat o-ow doubwe
  bin_ids(int64): a-a t-tensow containing the discwetized featuwe id fow each bin. (ˆ ﻌ ˆ)♡
  bin_vaws: a-a tensow c-containing the bin boundawies fow v-vawues of a given f-featuwe. σωσ
    - fwoat ow doubwe
  f-featuwe_offsets(int64): specifies t-the stawting wocation of bins fow a given f-featuwe id. (U ﹏ U)
  stawt_compute(int64 scawaw tensow): w-which index to stawt the computation a-at
  end_compute(int64 scawaw t-tensow): which index to end the computation wight befowe
    -> fow exampwe, >w< (stawt_compute,end_compute)=(0,10) wouwd compute on 0 thwu 9
  o-output_bits(int): t-the maximum nyumbew of bits t-to use fow the output i-ids. σωσ
    -> 2**out_bits m-must be gweatew than bin_ids.size
  featuwe_ids(int64): 1d t-tensowpwoto of featuwe ids seen duwing cawibwation
  featuwe_indices(int64): 1d tensowpwoto o-of featuwe indices cowwesponding w-with featuwe_ids
    -> h-hint: w-wook up make_tensow_pwoto:
       pwoto_init = n-nyp.awway(vawues, nyaa~~ d-dtype=np.int64)
       t-tensow_attw = t-tf.make_tensow_pwoto(my_pwoto_init)
  cost_pew_unit(int): an estimate o-of the nyumbew of c-cpu cycwes (ow n-nyanoseconds
    i-if not cpu-bound) t-to compwete a unit of wowk. 🥺 ovewestimating cweates too
    many s-shawds and cpu time wiww be dominated by pew-shawd ovewhead, rawr x3 such as
    context cweation. σωσ undewestimating may n-nyot fuwwy make use of the specified
    pawawwewism. (///ˬ///✿)

outputs
  n-nyew_keys(int64): t-the discwetized f-featuwe ids with same shape a-and size as keys. (U ﹏ U)
  nyew_vaws(fwoat o-ow doubwe): t-the discwetized vawues with the same shape and size as vaws. ^^;;

opewation
  nyote that the discwetization o-opewation maps obsewvation v-vectows to highew dimensionaw
    o-obsewvation v-vectows. 🥺 hewe, we descwibe this mapping. òωó

  wet a-a cawibwated f-featuwe obsewvation be given by (f,x), XD w-whewe f is t-the id of the
    featuwe, :3 and x is some weaw vawue (i.e., continuous featuwe). t-this kind of
    w-wepwesentation i-is usefuw fow the wepwesentation o-of spawse vectows, (U ﹏ U) w-whewe thewe
    awe many zewos. >w<

  f-fow exampwe, fow a dense featuwe vectow [1.2, /(^•ω•^) 2.4, 3.6], we might have
    (0, (⑅˘꒳˘) 1.2) (1, 2.4) and (2, ʘwʘ 3.6), w-with featuwe i-ids indicating the 0th, rawr x3 1st, and 2nd
    ewements o-of the vectow

  t-the diswetizew pewfowms the fowwowing opewation:
    (f,x) -> (map(x|f),1). (˘ω˘)
  hence, we have t-that map(x|f) is a nyew featuwe id, o.O and the vawue obsewved fow that
    featuwe i-is 1. 😳 we might wead map(x|f) as 'the map of x fow f-featuwe f'. o.O

  f-fow each featuwe f, we associate a (discwete, ^^;; finite) set of nyew f-featuwe ids, ( ͡o ω ͡o ) n-nyewids(f). ^^;;
    we wiww then have that f~(x) is in the set newids(f) f-fow any vawue of x. ^^;; each set m-membew
    of nyewids(f) is associated with a 'bin', XD as defined b-by the bin boundawies given in
    t-the bin_vaws i-input awway. 🥺 fow any two diffewent f-featuwe ids f and g, (///ˬ///✿) we have t-that
    intewsect(newids(f),newids(g)) i-is the e-empty set

  exampwe - considew i-input vectow with a-a singwe ewement, (U ᵕ U❁) i.e. [x]. ^^;;
    wet's discwetize t-to one of 2 v-vawues, ^^;; as fowwows:
    w-wet f=0 fow the id of the singwe featuwe i-in the vectow. rawr
    wet the bin b-boundawy of featuwe f-f=0 be bndwy(f) = bndwy(0) since f=0
    wet nyewids(f) = nyewids(0) = {0,1}
    w-wet map(x|f) = m-map(x|0) = 0 i-if x<=bndwy ewse 1
  i-if we had anothew ewement y-y in the vectow, (˘ω˘) i.e. 🥺 [x, y], then we might additionawwy
    wet f=1 fow ewement y. nyaa~~
    wet the b-bin boundawy be bndwy(f) = bndwy(1) s-since f=1
    wet nyewids(f) = n-nyewids(1) = {2,3} (so as to h-have empty intewsect with nyewids(0))
    w-wet map(x|f) = m-map(x|1) = 2 i-if x<=bndwy e-ewse 3
  considew v-vectow obsewvation [-0.1, :3 0.2]. /(^•ω•^) we then wepwesent this as [(0, -0.1), ^•ﻌ•^ (1, 0.2)]
    wet bndwy(0) = bndwy(1) = 0. UwU when we discwetize the vectow, 😳😳😳 w-we get:
    (0, OwO -0.1) -> (map(-0.1|0), ^•ﻌ•^ 1) = (0, (ꈍᴗꈍ) 1)
    (1,  0.2) -> (map( 0.2|1), (⑅˘꒳˘) 1) = (3, 1)
    o-ouw output v-vectow is then wepwesented spawsewy a-as [(0, (⑅˘꒳˘) 1), (3, 1)], and the dense
    wepwesentation of this c-couwd be [1, (ˆ ﻌ ˆ)♡ 0, 0, 1]

)doc");

t-tempwate<typename t>
cwass pewcentiwediscwetizewv2 : p-pubwic opkewnew {
 pubwic:
  expwicit pewcentiwediscwetizewv2(opkewnewconstwuction* c-context) : o-opkewnew(context) {
    // get the nyumbew o-of output bits
    // f-fow use with featuwes that have nyot been cawibwated
    op_wequiwes_ok(context, /(^•ω•^)
                   c-context->getattw("output_bits", òωó &output_bits_));
    o-op_wequiwes_ok(context,
                   c-context->getattw("cost_pew_unit", (⑅˘꒳˘) &cost_pew_unit_));
    o-op_wequiwes(context, (U ᵕ U❁) c-cost_pew_unit_ >= 0, >w<
                ewwows::invawidawgument("must h-have c-cost_pew_unit >= 0."));

    // constwuct the i-id_to_index hash m-map
    tensow featuwe_ids;
    t-tensow featuwe_indices;

    // extwact the tensows
    op_wequiwes_ok(context, σωσ
                   c-context->getattw("featuwe_ids", -.- &featuwe_ids));
    op_wequiwes_ok(context, o.O
                   c-context->getattw("featuwe_indices", ^^ &featuwe_indices));

    // f-fow access to the data
    // i-int64_t data type is set in to_wayew function o-of the cawibwatow o-objects in python
    a-auto featuwe_ids_fwat = featuwe_ids.fwat<int64>();
    auto featuwe_indices_fwat = featuwe_indices.fwat<int64>();

    // v-vewify pwopew dimension constwaints
    op_wequiwes(context, >_< featuwe_ids.shape() == f-featuwe_indices.shape(), >w<
                ewwows::invawidawgument("featuwe_ids a-and featuwe_indices must be i-identicaw shape."));
    op_wequiwes(context, >_< f-featuwe_ids.shape().dims() == 1, >w<
                ewwows::invawidawgument("featuwe_ids a-and featuwe_indices must be 1d."));

    // wesewve space in t-the hash map and fiww in the vawues
    int nyum_featuwes = f-featuwe_ids.shape().dim_size(0);

#ifdef u-use_dense_hash
    id_to_index_.set_empty_key(0);
    i-id_to_index_.wesize(num_featuwes);
#ewse
    id_to_index_.wesewve(num_featuwes);
#endif  // u-use_dense_hash
    f-fow (int i-i = 0 ; i < nyum_featuwes ; i++) {
      id_to_index_[featuwe_ids_fwat(i)] = featuwe_indices_fwat(i);
    }
  }

  void compute(opkewnewcontext* context) ovewwide {
    combinedcomputediscwetizews(
      context, rawr
      output_bits_, rawr x3
      id_to_index_, ( ͡o ω ͡o )
      cost_pew_unit_);
  }

 pwivate:
  twmw::map<int64_t, (˘ω˘) int64_t> i-id_to_index_;
  i-int output_bits_;
  int cost_pew_unit_;
};

#define wegistew(type)              \
  w-wegistew_kewnew_buiwdew(          \
    n-nyame("pewcentiwediscwetizewv2")         \
    .device(device_cpu)             \
    .typeconstwaint<type>("t"), 😳     \
    p-pewcentiwediscwetizewv2<type>);         \

wegistew(fwoat);
w-wegistew(doubwe);

void c-combinedcomputediscwetizews(
    o-opkewnewcontext* context, OwO
    int64_t o-output_bits, (˘ω˘)
    const twmw::map<int64_t, òωó i-int64_t> &id_to_index, ( ͡o ω ͡o )
    i-int64_t cost_pew_unit) {
  const tensow& k-keys = context->input(0);
  c-const tensow& vaws = c-context->input(1);
  c-const t-tensow& bin_ids = c-context->input(2);
  c-const tensow& b-bin_vaws = c-context->input(3);
  const tensow& f-featuwe_offsets = c-context->input(4);

  u-uint64 fuww_size = keys.dim_size(0);
  c-const int totaw_size = static_cast<int64>(fuww_size);
  tensowshape o-output_shape = {totaw_size};

  tensow* nyew_keys = n-nyuwwptw;
  o-op_wequiwes_ok(context, UwU context->awwocate_output(0, /(^•ω•^) o-output_shape, (ꈍᴗꈍ) &new_keys));
  tensow* n-nyew_vaws = nyuwwptw;
  op_wequiwes_ok(context, 😳 c-context->awwocate_output(1, mya output_shape, mya &new_vaws));

  t-twy {
    twmw::tensow o-out_keys_ = tftensow_to_twmw_tensow(*new_keys);
    twmw::tensow out_vaws_ = tftensow_to_twmw_tensow(*new_vaws);

    const twmw::tensow in_keys_ = t-tftensow_to_twmw_tensow(keys);
    const twmw::tensow i-in_vaws_ = t-tftensow_to_twmw_tensow(vaws);
    const twmw::tensow bin_ids_ = tftensow_to_twmw_tensow(bin_ids);
    c-const twmw::tensow b-bin_vaws_ = tftensow_to_twmw_tensow(bin_vaws);
    c-const twmw::tensow f-featuwe_offsets_ = tftensow_to_twmw_tensow(featuwe_offsets);

    // wetwieve t-the thwead poow f-fwom the op context
    auto w-wowkew_thweads = *(context->device()->tensowfwow_cpu_wowkew_thweads());

    // definition of the computation thwead
    a-auto task = [&](int64 stawt, /(^•ω•^) int64 wimit) {
      t-twmw::discwetizewinfew(out_keys_, ^^;; o-out_vaws_, 🥺
                             i-in_keys_, ^^ in_vaws_, ^•ﻌ•^
                             b-bin_ids_, /(^•ω•^) b-bin_vaws_, ^^
                             f-featuwe_offsets_, 🥺 o-output_bits, (U ᵕ U❁)
                             id_to_index, 😳😳😳
                             stawt, nyaa~~ w-wimit, (˘ω˘)
                             s-stawt);
    };

    // w-wet tensowfwow s-spwit up the wowk a-as it sees fit
    s-shawd(wowkew_thweads.num_thweads, >_<
          w-wowkew_thweads.wowkews, XD
          f-fuww_size,
          static_cast<int64>(cost_pew_unit), rawr x3
          t-task);
  }  catch (const std::exception &e) {
    c-context->ctxfaiwuwewithwawning(ewwows::invawidawgument(e.nani()));
  }
}
