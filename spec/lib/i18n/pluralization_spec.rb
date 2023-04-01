require "spec_helper"

describe "Pluralization rule for" do
  let(:plural_keys) do |example|
    I18n.t("i18n.plural.keys", locale: example.metadata[:locale], resolve: false)
  end

  let(:rule) do |example|
    I18n.t("i18n.plural.rule", locale: example.metadata[:locale], resolve: false)
  end

  describe "Croatian", locale: "hr" do
    it "has 'one', 'few', and 'other' plural keys" do
      expect(plural_keys).to contain_exactly(:one, :few, :other)
    end

    [1, 21, 31, 41, 51, 101, 1001].each do |count|
      it "puts #{count} in category 'one'" do
        expect(rule.call(count)).to eq(:one)
      end
    end

    [2, 3, 4, 22, 23, 24, 102, 103, 104].each do |count|
      it "puts #{count} in category 'few'" do
        expect(rule.call(count)).to eq(:few)
      end
    end

    [0, 5, 9, 10, 11, 12, 13, 14, 15, 19, 20, 25, 29, 30].each do |count|
      it "puts #{count} in category 'other'" do
        expect(rule.call(count)).to eq(:other)
      end
    end
  end
end
