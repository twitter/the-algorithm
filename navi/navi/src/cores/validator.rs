pub mod val dat or {
    pub mod cl _val dator {
        use crate::cl _args::{ARGS, MODEL_SPECS};

        pub fn val date_ nput_args() {
            assert_eq!(MODEL_SPECS.len(), ARGS. nter_op_parallel sm.len());
            assert_eq!(MODEL_SPECS.len(), ARGS. ntra_op_parallel sm.len());
            //TODO for now  ,   assu  each model's output has only 1 tensor.
            //t  w ll be l fted once tf_model properly  mple nts mtl outputs
            //assert_eq!(OUTPUTS.len(), OUTPUTS. er().fold(0us ze, |a, b| a+b.len()));
        }

        pub fn val date_ps_model_args() {
            assert!(ARGS.vers ons_per_model <= 2);
            assert!(ARGS.vers ons_per_model >= 1);
            assert_eq!(MODEL_SPECS.len(), ARGS. nput.len());
            assert_eq!(MODEL_SPECS.len(), ARGS.model_d r.len());
            assert_eq!(MODEL_SPECS.len(), ARGS.max_batch_s ze.len());
            assert_eq!(MODEL_SPECS.len(), ARGS.batch_t  _out_m ll s.len());
        }
    }
}
