pub mod validatior {
    pub mod cli_validator {
        use crate::cli_args::{ARGS, MODEL_SPECS};

        pub fn validate_input_args() {
            assert_eq!(MODEL_SPECS.len(), ARGS.inter_op_parallelism.len());
            assert_eq!(MODEL_SPECS.len(), ARGS.intra_op_parallelism.len());
            //TODO for now we, we assume each model's output has only 1 tensor.
            //this will be lifted once tf_model properly implements mtl outputs
            //assert_eq!(OUTPUTS.len(), OUTPUTS.iter().fold(0usize, |a, b| a+b.len()));
        }

        pub fn validate_ps_model_args() {
            assert!(ARGS.versions_per_model <= 2);
            assert!(ARGS.versions_per_model >= 1);
            assert_eq!(MODEL_SPECS.len(), ARGS.input.len());
            assert_eq!(MODEL_SPECS.len(), ARGS.model_dir.len());
            assert_eq!(MODEL_SPECS.len(), ARGS.max_batch_size.len());
            assert_eq!(MODEL_SPECS.len(), ARGS.batch_time_out_millis.len());
        }
    }
}
