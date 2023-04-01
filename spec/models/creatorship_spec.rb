require "spec_helper"

describe Creatorship do
  describe "#destroy" do
    context "when the creation is a work" do
      let(:creation) { create(:work, authors: [build(:pseud), build(:pseud)]) }
      let(:creatorship) { creation.creatorships.first }
      
      context "when the creatorship is approved" do
        before do
          creatorship.accept!
        end
  
        it "saves the original creator" do
          original_creator = creatorship.pseud.user
          creatorship.destroy!
          expect(creation.original_creators.length).to eq(1)
          expect(creation.original_creators.first.user).to eq(original_creator)
        end
      end
  
      context "when the creatorship is not approved" do
        before do
          creatorship.approved = false
          creatorship.save!(validate: false)
        end

        it "does not save the original creator" do
          expect { creatorship.destroy! }.not_to change { creation.original_creators }
        end
      end
    end
  end
end
