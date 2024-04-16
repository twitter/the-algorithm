#[cfg(feature = "torch")]
pub mod torch {
    use std::fmt;
    use std::fmt::D splay;
    use std::str ng::Str ng;

    use crate::TensorReturnEnum;
    use crate::Ser al zed nput;
    use crate::bootstrap::Tensor nput;
    use crate::cl _args::{Args, ARGS, MODEL_SPECS};
    use crate:: tr cs;
    use crate:: tr cs::{
         NFERENCE_FA LED_REQUESTS_BY_MODEL, NUM_REQUESTS_FA LED, NUM_REQUESTS_FA LED_BY_MODEL,
    };
    use crate::pred ct_serv ce::Model;
    use anyhow::Result;
    use dr_transform::converter::BatchPred ct onRequestToTorchTensorConverter;
    use dr_transform::converter::Converter;
    use serde_json::Value;
    use tch::Tensor;
    use tch::{k nd, CModule,  Value};

    #[der ve(Debug)]
    pub struct TorchModel {
        pub model_ dx: us ze,
        pub vers on:  64,
        pub module: CModule,
        pub export_d r: Str ng,
        // F XME: make t  Box<Opt on<..>> so  nput converter can be opt onal.
        // Also cons der add ng output_converter.
        pub  nput_converter: Box<dyn Converter>,
    }

     mpl D splay for TorchModel {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            wr e!(
                f,
                " dx: {}, torch model_na :{}, vers on:{}",
                self.model_ dx, MODEL_SPECS[self.model_ dx], self.vers on
            )
        }
    }

     mpl TorchModel {
        pub fn new( dx: us ze, vers on: Str ng, _model_conf g: &Value) -> Result<TorchModel> {
            let export_d r = format!("{}/{}/model.pt", ARGS.model_d r[ dx], vers on);
            let model = CModule::load(&export_d r).unwrap();
            let torch_model = TorchModel {
                model_ dx:  dx,
                vers on: Args::vers on_str_to_epoch(&vers on)?,
                module: model,
                export_d r,
                //TODO: move converter lookup  n a reg stry.
                 nput_converter: Box::new(BatchPred ct onRequestToTorchTensorConverter::new(
                    &ARGS.model_d r[ dx].as_str(),
                    vers on.as_str(),
                    vec![],
                    So (& tr cs::reg ster_dynam c_ tr cs),
                )),
            };

            torch_model.warmup()?;
            Ok(torch_model)
        }
        #[ nl ne(always)]
        pub fn decode_to_ nputs(bytes: Ser al zed nput) -> Vec<Tensor> {
            //F XME: for now   generate 4 random tensors as  nputs to unblock end to end test ng
            //w n Shajan's decoder  s ready   w ll swap
            let row = bytes.len() as  64;
            let t1 = Tensor::randn(&[row, 5293], k nd::FLOAT_CPU); //cont nuous
            let t2 = Tensor::rand nt(10, &[row, 149], k nd:: NT64_CPU); //b nary
            let t3 = Tensor::rand nt(10, &[row, 320], k nd:: NT64_CPU); //d screte
            let t4 = Tensor::randn(&[row, 200], k nd::FLOAT_CPU); //user_embedd ng
            let t5 = Tensor::randn(&[row, 200], k nd::FLOAT_CPU); //user_eng_embedd ng
            let t6 = Tensor::randn(&[row, 200], k nd::FLOAT_CPU); //author_embedd ng

            vec![t1, t2, t3, t4, t5, t6]
        }
        #[ nl ne(always)]
        pub fn output_to_vec(res:  Value, dst: &mut Vec<f32>) {
            match res {
                 Value::Tensor(tensor) => TorchModel::tensors_to_vec(&[tensor], dst),
                 Value::Tuple( values) => {
                    TorchModel::tensors_to_vec(&TorchModel:: values_to_tensors( values), dst)
                }
                _ => pan c!("  only support output as a s ngle tensor or a vec of tensors"),
            }
        }
        #[ nl ne(always)]
        pub fn tensor_flatten_s ze(t: &Tensor) -> us ze {
            t.s ze(). nto_ er().fold(1, |acc, x| acc * x) as us ze
        }
        #[ nl ne(always)]
        pub fn tensor_to_vec<T: k nd::Ele nt>(res: &Tensor) -> Vec<T> {
            let s ze = TorchModel::tensor_flatten_s ze(res);
            let mut res_f32: Vec<T> = Vec::w h_capac y(s ze);
            unsafe {
                res_f32.set_len(s ze);
            }
            res.copy_data(res_f32.as_mut_sl ce(), s ze);
            // pr ntln!("Cop ed tensor:{}, {:?}", res_f32.len(), res_f32);
            res_f32
        }
        #[ nl ne(always)]
        pub fn tensors_to_vec(tensors: &[Tensor], dst: &mut Vec<f32>) {
            let mut offset = dst.len();
            tensors. er().for_each(|t| {
                let s ze = TorchModel::tensor_flatten_s ze(t);
                let next_s ze = offset + s ze;
                unsafe {
                    dst.set_len(next_s ze);
                }
                t.copy_data(&mut dst[offset..], s ze);
                offset = next_s ze;
            });
        }
        pub fn  values_to_tensors( values: Vec< Value>) -> Vec<Tensor> {
             values
                . nto_ er()
                .map(|t| {
                     f let  Value::Tensor(van lla_t) = t {
                        van lla_t
                    } else {
                        pan c!("not a tensor")
                    }
                })
                .collect::<Vec<Tensor>>()
        }
    }

     mpl Model for TorchModel {
        fn warmup(&self) -> Result<()> {
            Ok(())
        }
        //TODO: torch runt   needs so  refactor to make   a gener c  nterface
        #[ nl ne(always)]
        fn do_pred ct(
            &self,
             nput_tensors: Vec<Vec<Tensor nput>>,
            total_len: u64,
        ) -> (Vec<TensorReturnEnum>, Vec<Vec<us ze>>) {
            let mut buf: Vec<f32> = Vec::w h_capac y(10_000);
            let mut batch_ends = vec![0us ze;  nput_tensors.len()];
            for ( , batch_bytes_ n_request)  n  nput_tensors. nto_ er().enu rate() {
                for _  n batch_bytes_ n_request. nto_ er() {
                    //F XME: for now use so  hack
                    let model_ nput = TorchModel::decode_to_ nputs(vec![0u8; 30]); //self. nput_converter.convert(bytes);
                    let  nput_batch_tensors = model_ nput
                        . nto_ er()
                        .map(|t|  Value::Tensor(t))
                        .collect::<Vec< Value>>();
                    // match self.module.forward_ s(& nput_batch_tensors) {
                    match self.module. thod_ s("forward_serve", & nput_batch_tensors) {
                        Ok(res) => TorchModel::output_to_vec(res, &mut buf),
                        Err(e) => {
                            NUM_REQUESTS_FA LED. nc_by(total_len);
                            NUM_REQUESTS_FA LED_BY_MODEL
                                .w h_label_values(&[&MODEL_SPECS[self.model_ dx]])
                                . nc_by(total_len);
                             NFERENCE_FA LED_REQUESTS_BY_MODEL
                                .w h_label_values(&[&MODEL_SPECS[self.model_ dx]])
                                . nc_by(total_len);
                            pan c!("{model}: {e:?}", model = MODEL_SPECS[self.model_ dx], e = e);
                        }
                    }
                }
                batch_ends[ ] = buf.len();
            }
            (
                vec![TensorReturnEnum::FloatTensorReturn(Box::new(buf))],
                vec![batch_ends],
            )
        }
        #[ nl ne(always)]
        fn model_ dx(&self) -> us ze {
            self.model_ dx
        }
        #[ nl ne(always)]
        fn vers on(&self) ->  64 {
            self.vers on
        }
    }
}
