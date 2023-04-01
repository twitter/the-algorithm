require 'spec_helper'
require 'json'

describe ValidationHelper do
  # NOTE: I only tested the method I modified. -@duckinator

  describe '.live_validation_for_field' do
    it 'should generate the appropriate JavaScript function calls' do
      options = {
        presence: true,
        failureMessage: "failure message.",
        validMessage: "valid message.",

        maximum_length: 10,
        tooLongMessage: "too long message.",

        minimum_length: 5,
        tooShortMessage: "too short message.",

        numericality: true,
        notANumberMessage: "not a number message.",

        exclusion: ['one', 'two', 'three']
      }

      expected = <<-EOF
      | <script>
      | //<![CDATA[
      | var validation_for_10 = new LiveValidation('10', { wait: 500, onlyOnBlur: false });
      | validation_for_10.add(Validate.Presence, {"failureMessage":"failure message.","validMessage":"valid message."});
      | validation_for_10.add(Validate.Length, {"maximum":"10","tooLongMessage":"too long message."});
      | validation_for_10.add(Validate.Length, {"minimum":"5","tooShortMessage":"too short message."});
      | validation_for_10.add(Validate.Numericality, {"notANumberMessage":"not a number message.","validMessage":"valid message."});
      | validation_for_10.add(Validate.Exclusion, {"within":["one","two","three"],"failureMessage":"failure message.","validMessage":"valid message."});
      | //]]>
      | </script>
      EOF

      expected.gsub!(/^\s*\|( |$)/, '').strip!

      output = helper.live_validation_for_field(10, options)

      expect(output).to eq(expected)
    end

    it 'should generate JavaScript for default options' do
      # NOTE: defaults to:
      # {
      #   presence: true,
      #   failureMessage: 'Must be present.',
      #   validMessage: ''
      # }
      options = {}

      expected = <<-EOF
      | <script>
      | //<![CDATA[
      | var validation_for_10 = new LiveValidation('10', { wait: 500, onlyOnBlur: false });
      | validation_for_10.add(Validate.Presence, {"failureMessage":"Must be present.","validMessage":""});
      | //]]>
      | </script>
      EOF

      expected.gsub!(/^\s*\|( |$)/, '').strip!

      output = helper.live_validation_for_field(10, options)

      expect(output).to eq(expected)
    end

    it 'should generate nothing if presence set to false' do
      options = {
        presence: false
      }

      expected = <<-EOF
      | <script>
      | //<![CDATA[
      | var validation_for_10 = new LiveValidation('10', { wait: 500, onlyOnBlur: false });
      |
      | //]]>
      | </script>
      EOF

      expected.gsub!(/^\s*\|( |$)/, '').strip!

      output = helper.live_validation_for_field(10, options)

      expect(output).to eq(expected)
    end
  end
end
