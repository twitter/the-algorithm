require 'spec_helper'

describe PotentialMatch do

  before do
    @potential_match = create(:potential_match)
    @collection = @potential_match.collection
    @first_signup = @collection.signups.first
    @last_signup = @collection.signups.last
  end

  it "should have a progress key" do
    expect(PotentialMatch.progress_key(@collection)).to include("#{@collection.id}")
  end
  
  it "should have a signup key" do
    expect(PotentialMatch.signup_key(@collection)).to include("#{@collection.id}")
  end

  describe "when matches are being generated" do
    before do
      PotentialMatch.set_up_generating(@collection)
    end
  
    it "should report progress" do 
      expect(PotentialMatch.in_progress?(@collection)).to be_truthy
      expect(PotentialMatch.progress(@collection)).to be == "0.0"
    end
  end  
end
