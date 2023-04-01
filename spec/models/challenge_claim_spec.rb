require 'spec_helper'

describe ChallengeClaim do

  describe '#claim_byline' do
    it "should return the claiming user's default pseud byline" do
      user = create(:user)
      claim = ChallengeClaim.new(claiming_user_id: user.id)
      expect(claim.claim_byline).to eq(user.default_pseud.byline)
    end
    it "should tell you when there's no user instead of erroring" do
      expect(ChallengeClaim.new.claim_byline).to eq("deleted user")
    end
  end
end