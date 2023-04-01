require 'spec_helper'

describe TagSetNomination do

  describe "save" do

    before(:each) do
      @tag_set_nomination = FactoryBot.create(:tag_set_nomination)
    end

    it "should save a basic tag set nomination" do
      expect(@tag_set_nomination.save).to be_truthy
    end

  end

end
