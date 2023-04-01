require 'spec_helper'
require 'support/login_macros'

describe ExternalAuthor do
  include LoginMacros

  describe "find_or_invite" do
    let(:unclaimed_user) { create(:user) }
    let(:claimed_user) { create(:user) }
    let(:unclaimed_ext_author) { create(:external_author, is_claimed: false) }
    let(:unclaimed_user_author) { create(:external_author, email: unclaimed_user.email, is_claimed: false) }
    let(:claimed_ext_author) { create(:external_author, user_id: claimed_user.id, is_claimed: true) }
    let(:archivist) { create(:archivist) }

    context "a user with a matching email exists" do
      subject { unclaimed_user_author }

      it "is automatically claimed by the user" do
        subject.find_or_invite(archivist)
        expect(subject.claimed?).to eq(true)
        expect(subject.user_id).to eq(unclaimed_user.id)
      end
    end

    context "a claimed external user with a matching email exists" do
      subject { claimed_ext_author }

      it "is automatically claimed by the corresponding user" do
        expect(subject).to receive(:claim!).with(claimed_user)
        subject.find_or_invite(archivist)
      end

      it "does NOT generate an invitation email" do
        expect(Invitation).to_not receive(:new)
        subject.find_or_invite(archivist)
      end
    end

    context "no external author or user with a matching email exists" do
      subject { unclaimed_ext_author }

      it "generates an invitation email" do
        expect(Invitation).to receive(:new).and_call_original
        subject.find_or_invite(archivist)
      end
    end

  end
end
