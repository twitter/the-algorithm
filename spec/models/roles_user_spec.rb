require "spec_helper"

describe RolesUser do
  describe "remove role" do
    context "tag_wrangler" do
      let(:user) { create(:tag_wrangler) }
      let!(:activity) { create(:last_wrangling_activity, user: user) }

      it "clears last wrangler activity" do
        user.roles = []
        expect(LastWranglingActivity.find_by(user: user)).to be_nil
      end

      it "does not clear last wrangler activity for a different role" do
        other_role = Role.find_or_create_by(name: "archivist")
        user.roles.push(other_role)
        user.roles.delete(other_role)
        expect(user.last_wrangling_activity).not_to be_nil
      end
    end
  end
end
