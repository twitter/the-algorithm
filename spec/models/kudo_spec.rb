require "spec_helper"

describe Kudo do
  describe "#save" do
    context "for a registered kudos giver" do
      let(:user) { create(:user) }

      context "when kudos from the same user already exist" do
        let(:old_kudo) { create(:kudo, user: user) }
        let(:new_kudo) { build(:kudo, user: user, commentable: old_kudo.commentable) }

        it "does not save" do
          expect(new_kudo.save).to be_falsy
          expect(new_kudo.errors[:ip_address]).to be_empty
          expect(new_kudo.errors[:user_id].first).to include("already left kudos")
        end
      end

      context "when kudos from another user already exist" do
        let(:another_user) { create(:user) }
        let(:old_kudo) { create(:kudo, user: another_user) }
        let(:new_kudo) { build(:kudo, user: user, commentable: old_kudo.commentable) }

        it "saves" do
          expect(new_kudo.save).to be_truthy
          expect(new_kudo.errors).to be_empty
        end
      end
    end

    context "for a guest kudos giver" do
      let(:ip_address) { Faker::Internet.ip_v4_address }

      context "when kudos from the same IP already exist" do
        let(:old_kudo) { create(:kudo, ip_address: ip_address) }
        let(:new_kudo) { build(:kudo, ip_address: ip_address, commentable: old_kudo.commentable) }

        it "does not save" do
          expect(new_kudo.save).to be_falsy
          expect(new_kudo.errors[:ip_address].first).to include("already left kudos")
          expect(new_kudo.errors[:user_id]).to be_empty
        end
      end

      context "when kudos from a different IP already exist" do
        let(:old_kudo) { create(:kudo, ip_address: Faker::Internet.ip_v4_address) }
        let(:new_kudo) { build(:kudo, ip_address: ip_address, commentable: old_kudo.commentable) }

        it "saves" do
          expect(new_kudo.save).to be_truthy
          expect(new_kudo.errors).to be_empty
        end
      end
    end
  end
end
