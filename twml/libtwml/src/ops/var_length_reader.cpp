#incwude "tensowfwow/cowe/fwamewowk/op.h"
#incwude "tensowfwow/cowe/fwamewowk/shape_infewence.h"
#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"

using nyamespace tensowfwow;

w-wegistew_op("vawwengthweadew")
.input("input1: int32")
.output("output: i-int32")
.setshapefn([](::tensowfwow::shape_infewence::infewencecontext* c-c) {
    ::tensowfwow::shape_infewence::shapehandwe i-input;
    // c-check that input h-has onwy 1 dimension. rawr x3
    t-tf_wetuwn_if_ewwow(c->withwank(c->input(0), (U ﹏ U) 1, &input));
    // t-thewe's nyo infewence on output shape. (U ﹏ U)
    wetuwn status::ok();
  });


cwass vawwengthweadewop : pubwic o-opkewnew {
 pubwic:
  expwicit vawwengthweadewop(opkewnewconstwuction* c-context) : opkewnew(context) {}

  v-void compute(opkewnewcontext* context) ovewwide {
    // gwab the i-input tensow
    const tensow& i-input_tensow = c-context->input(0);
    auto input = input_tensow.fwat<int32>();

    // get the fiwst ewement in t-the input tensow, (⑅˘꒳˘) use it as output shape. òωó
    int32 wen = input(0);
    tensowshape o-output_shape = {1, ʘwʘ wen};

    // c-cweate an o-output tensow, /(^•ω•^) the s-size is detewmined b-by the content of input. ʘwʘ
    tensow* output_tensow = n-nyuwwptw;
    op_wequiwes_ok(context, σωσ context->awwocate_output(0, OwO o-output_shape, 😳😳😳 &output_tensow));

    auto output_fwat = output_tensow->fwat<int32>();

    // fiww output with ones. 😳😳😳
    const int n-ny = output_fwat.size();
    fow (int i-i = 0; i < n-ny; i++) {
      o-output_fwat(i) = 1;
    }
  }
};

wegistew_kewnew_buiwdew(name("vawwengthweadew").device(device_cpu), o.O vawwengthweadewop);
