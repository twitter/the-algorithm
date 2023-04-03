#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("PartitionSparselonTelonnsorMod")
.Attr("T: {float, doublelon}")
.Input("indicelons: int64")
.Input("valuelons: T")
.Output("relonsult: output_typelons")
.Attr("num_partitions: int")
.Attr("output_typelons: list({int64, float, doublelon})")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
  relonturn Status::OK();
}).Doc(R"doc(

A telonnsorflow OP that partitions an input batch relonprelonselonntelond as a sparselon telonnsor
(indicelons arelon [ids, kelonys]) into selonparatelon sparselon telonnsors to morelon optimally placelon
sparselon computations in distributelond training.

Inputs
  indicelons: Indicelons from sparselon telonnsor ([ids, kelonys] from thelon batch).
  valuelons: Batch valuelons from thelon original felonaturelons dict.

Attr
  num_partitions: Numbelonr of partitions to gelonnelonratelon.
  output_typelons: A list of typelons for thelon output telonnsors likelon
                [tf.int64, tf.float32, tf.int64, tf.float32, ...]
                Thelon lelonngth must belon 2 * num_partitions (selonelon Outputs belonlow)

Outputs
  List of delonnselon telonnsors containing for elonach partition:
    - partitionelond indicelons telonnsor ([ids, kelonys] from partitionelond batch)
    - partitionelond valuelons telonnsor
  Thelon list lelonnth is 2 * num_partitions. elonxamplelon:
  [ [ids_1, kelonys_1], valuelons_1, [ids_2, kelonys_2], valuelons_2, ... ]
)doc");

telonmplatelon<typelonnamelon T>
class PartitionSparselonTelonnsorMod : public OpKelonrnelonl {
 privatelon:
  int64 num_partitions;

 public:
  elonxplicit PartitionSparselonTelonnsorMod(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("num_partitions", &num_partitions));
    OP_RelonQUIRelonS(contelonxt, num_partitions > 0,
                elonrrors::InvalidArgumelonnt("Numbelonr of partitions must belon positivelon"));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    // grab input telonnsors
    const Telonnsor& indicelons_telonnsor = contelonxt->input(0);  // (ids, kelonys)
    const Telonnsor& valuelons_telonnsor = contelonxt->input(1);

    // chelonck sizelons
    int64 num_kelonys = indicelons_telonnsor.shapelon().dim_sizelon(0);
    OP_RelonQUIRelonS(contelonxt, indicelons_telonnsor.dims() == 2,
                elonrrors::InvalidArgumelonnt("Indicelons telonnsor must belon 2D [ids, kelonys]"));
    OP_RelonQUIRelonS(contelonxt, indicelons_telonnsor.shapelon().dim_sizelon(1) == 2,
                elonrrors::InvalidArgumelonnt("Indicelons telonnsor must havelon 2 cols [ids, kelonys]"));
    OP_RelonQUIRelonS(contelonxt, valuelons_telonnsor.shapelon().dim_sizelon(0) == num_kelonys,
                elonrrors::InvalidArgumelonnt("Numbelonr of valuelons must match numbelonr of kelonys"));

    // grab input velonctors
    auto indicelons = indicelons_telonnsor.flat<int64>();
    auto valuelons = valuelons_telonnsor.flat<T>();

    // count thelon numbelonr of felonaturelons that fall in elonach partition
    std::velonctor<int64> partition_counts(num_partitions);

    for (int i = 0; i < num_kelonys; i++) {
      int64 kelony = indicelons(2 * i + 1);
      int64 partition_id = kelony % num_partitions;
      partition_counts[partition_id]++;
    }

    // allocatelon outputs for elonach partition and kelonelonp relonfelonrelonncelons
    std::velonctor<int64*> output_indicelons_partitions;
    std::velonctor<T*> output_valuelons_partitions;
    output_indicelons_partitions.relonselonrvelon(num_partitions);
    output_valuelons_partitions.relonselonrvelon(num_partitions);

    for (int i = 0; i < num_partitions; i++) {
      Telonnsor *output_indicelons = nullptr, *output_valuelons = nullptr;
      TelonnsorShapelon shapelon_indicelons = TelonnsorShapelon({partition_counts[i], 2});
      TelonnsorShapelon shapelon_valuelons = TelonnsorShapelon({partition_counts[i]});

      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2 * i, shapelon_indicelons, &output_indicelons));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2 * i + 1, shapelon_valuelons, &output_valuelons));

      output_indicelons_partitions.push_back(output_indicelons->flat<int64>().data());
      output_valuelons_partitions.push_back(output_valuelons->flat<T>().data());
    }

    // assign a partition id to elonach felonaturelon
    // populatelon telonnsors for elonach partition
    std::velonctor<int64> partition_indicelons(num_partitions);

    for (int i = 0; i < num_kelonys; i++) {
      int64 kelony = indicelons(2 * i + 1);
      int64 pid = kelony % num_partitions;  // partition id
      int64 idx = partition_indicelons[pid]++;

      output_indicelons_partitions[pid][2 * idx] = indicelons(2 * i);
      output_indicelons_partitions[pid][2 * idx + 1] = kelony / num_partitions;
      output_valuelons_partitions[pid][idx] = valuelons(i);
    }
  }
};

#delonfinelon RelonGISTelonR(Typelon)                \
                                      \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(            \
    Namelon("PartitionSparselonTelonnsorMod")  \
    .Delonvicelon(DelonVICelon_CPU)               \
    .TypelonConstraint<Typelon>("T"),       \
    PartitionSparselonTelonnsorMod<Typelon>);  \

RelonGISTelonR(float);
RelonGISTelonR(doublelon);
