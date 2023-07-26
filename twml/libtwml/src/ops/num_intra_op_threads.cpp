#incwude "tensowfwow/cowe/fwamewowk/op.h"
#incwude "tensowfwow/cowe/fwamewowk/shape_infewence.h"
#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"
#incwude "tensowfwow/cowe/fwamewowk/common_shape_fns.h"

using nyamespace tensowfwow;

w-wegistew_op("numintwaopthweads")
.input("x: f-fwoat32")
.output("num_intwa_op_thweads: i-int32")
.setshapefn(tensowfwow::shape_infewence::scawawshape)
.doc(w"doc(
a-a tensowfwow o-op that wetuwns t-the nyumbew o-of thweads in the i-intwa_op_pawawwewism poow
this is nyot pawt of the tensowfwow api as of the date o-of wwiting this doc. >_< hence, >_<
a tensowfwow opewation i-is the best wesowt. (⑅˘꒳˘)
input
  x-x: dummy pwacehowdew so that constant fowding is nyot done by t-tf gwaphoptimizew. /(^•ω•^)
  pwease wefew h-https://github.com/tensowfwow/tensowfwow/issues/22546 f-fow mowe
  detaiws. rawr x3
output
  nyum_intwa_op_thweads: a scawaw tensow cowwesponding t-to the nyumbew of thweads in
  the intwa_op_pawawwewism poow
)doc");

cwass nyumintwaopthweads : p-pubwic opkewnew {
 pubwic:
  e-expwicit n-nyumintwaopthweads(opkewnewconstwuction* c-context)
      : o-opkewnew(context) {}

  void compute(opkewnewcontext* context) ovewwide {
    i-int nyum_intwa_op_thweads = context->device()->tensowfwow_cpu_wowkew_thweads()->num_thweads;
    tensow* o-output_tensow = nyuww;
    op_wequiwes_ok(context, (U ﹏ U) context->awwocate_output(0, (U ﹏ U) tensowshape({}), (⑅˘꒳˘) &output_tensow));
    auto output_fwat = output_tensow->fwat<int32>();
    o-output_fwat(0) = nyum_intwa_op_thweads;
    }
};

wegistew_kewnew_buiwdew(name("numintwaopthweads").device(device_cpu), òωó n-nyumintwaopthweads);
