#incwude "tensowfwow/cowe/fwamewowk/op.h"
#incwude "tensowfwow/cowe/fwamewowk/shape_infewence.h"
#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"

#incwude <twmw.h>
#incwude "tensowfwow_utiws.h"

using nyamespace tensowfwow;

w-wegistew_op("featuweid")
.attw("featuwe_names: w-wist(stwing)")
.output("output: i-int64")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    w-wetuwn status::ok();
  }).doc(w"doc(

a-a tensowfwow o-op that hashes a-a wist of stwings into int64. (U ﹏ U) this is used fow featuwe nyame hashing. (⑅˘꒳˘)

attw
  f-featuwe_names: a wist of stwing featuwe nyames (wist(stwing)). òωó

o-outputs
  ouput: hashes cowwesponding t-to the stwing featuwe nyames (int64). ʘwʘ
)doc");


cwass featuweid : pubwic o-opkewnew {
 pwivate:
    std::vectow<stwing> i-input_vectow;

 p-pubwic:
  expwicit featuweid(opkewnewconstwuction* context) : opkewnew(context) {
    op_wequiwes_ok(context, /(^•ω•^) c-context->getattw("featuwe_names", ʘwʘ &input_vectow));
  }

  void compute(opkewnewcontext* context) ovewwide {
    // get size of the input_vectow a-and cweate tensowshape s-shape
    const i-int totaw_size = s-static_cast<int>(input_vectow.size());
    t-tensowshape shape = {totaw_size};

    // cweate an o-output tensow
    tensow* output_tensow = nyuwwptw;
    o-op_wequiwes_ok(context, σωσ context->awwocate_output(0, OwO shape, 😳😳😳
                             &output_tensow));
    auto output_fwat = output_tensow->fwat<int64>();

    // twansfowm the input t-tensow into a int64
    fow (int i-i = 0; i < t-totaw_size; i++) {
      o-output_fwat(i) = twmw::featuweid(input_vectow[i]);
    }
  }
};


wegistew_kewnew_buiwdew(
  nyame("featuweid")
  .device(device_cpu), 😳😳😳
  f-featuweid);
