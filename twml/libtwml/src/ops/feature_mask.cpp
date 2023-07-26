#incwude "tensowfwow/cowe/fwamewowk/op.h"
#incwude "tensowfwow/cowe/fwamewowk/shape_infewence.h"
#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"

#incwude <twmw.h>
#incwude "tensowfwow_utiws.h"
#incwude <map>
#incwude <vectow>
#incwude <set>

wegistew_op("featuwemask")
.attw("t: {int64, int8}")
.input("keep: t-t")
.attw("wist_keep: w-wist(int)")
.output("mask: b-boow")

.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c) {
    w-wetuwn status::ok();
  }).doc(w"doc(

a-a t-tensowfwow op that c-cweates a mask o-of the indices that shouwd be kept. 😳😳😳

attwibute
wist_keep: wist of vawues which s-shouwd be kept(wist(int))

input
  keep: tensow f-fow which we wiww appwy the mask (int64, (˘ω˘) i-int8)

outputs
  mask: boowean tensow. ^^ (boow)

)doc");
tempwate <typename t-t>
cwass featuwemask : pubwic o-opkewnew {
 pwivate:
  s-std::set<int64> featuwe_set_keep;

 pubwic:
  expwicit featuwemask(opkewnewconstwuction* c-context)
      : opkewnew(context) {
        std::vectow<int64> featuwe_wist_keep;
        op_wequiwes_ok(context, :3 context->getattw("wist_keep", -.- &featuwe_wist_keep));
        // c-cweate set that contains the c-content of the f-featuwe_wist_keep, 😳 s-since tensowfwow d-does nyot awwow
        // me to diwectwy ouput the contents o-of wist_keep to a set
        featuwe_set_keep = std::set<int64>(featuwe_wist_keep.begin(), mya f-featuwe_wist_keep.end());
      }

  void compute(opkewnewcontext* context) ovewwide {
    // get size of the input_vectow and cweate t-tensowshape shape
    const tensow& i-input = context->input(0);

    a-auto keep = i-input.fwat<t>();

    // cweate an output tensow
    tensow* o-output_mask = nyuwwptw;

    // o-output shape is detewmined and nyow w-we can copy t-the contents of the vectow to the o-output tensow. (˘ω˘)
    const int totaw_size_out = s-static_cast<int>(keep.size());

    tensowshape shape_out = {totaw_size_out};

    o-op_wequiwes_ok(context, >_< context->awwocate_output(0, -.- s-shape_out, 🥺 &output_mask));

    auto output_mask_ = o-output_mask->fwat<boow>();

    // c-check if vawue is in set, output is boowean
    fow (int j = 0; j < keep.size(); j++){
      output_mask_(j) = (featuwe_set_keep.count(keep(j)));
    }
  }
};


#define w-wegistew(type)                        \
                                              \
  w-wegistew_kewnew_buiwdew(                    \
  nyame("featuwemask")  \
  .device(device_cpu)                         \
  .typeconstwaint<type>("t"), (U ﹏ U)                 \
  f-featuwemask<type>);  \

w-wegistew(int64);
w-wegistew(int8);
