require 'spec_helper'

describe GiftExchange do

  describe "a gift exchange challenge" do
    before do
      @collection = FactoryBot.create(:collection)
      @collection.challenge = GiftExchange.new
      @challenge = @collection.challenge
    end

    it "should save" do
      expect(@challenge.save).to be_truthy
    end

  end

end
