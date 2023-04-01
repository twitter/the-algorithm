require 'spec_helper'
describe InviteRequest do
  it "has a valid factory" do
    expect(build(:invite_request)).to be_valid
  end

  describe "Validation" do
    context "Invalid email" do
      it "invitation is not created for a blank email" do
        @invite = build(:invite_request, email: nil)
        expect(@invite.valid?).to be_falsey
        expect(@invite.errors[:email]).not_to be_empty
      end

      BAD_EMAILS.each do |email|
        it "cannot be created if the email does not pass veracity check" do
          bad_email = build(:user, email: email)
          expect(bad_email.valid?).to be_falsey
          expect(bad_email.errors[:email]).to include("should look like an email address.")
          expect(bad_email.errors[:email]).to include("does not seem to be a valid address.")
        end
      end
    end

    context "Duplicate email" do
      before :all do
        @original_request = create(:invite_request, email: "thegodthor@gmail.com")
      end

      it "should not let you sign up again with the same email" do
        dup_request = build(:invite_request, email: @original_request.email)
        expect(dup_request.valid?).to be_falsey
        expect(dup_request.errors[:email]).to include("is already part of our queue.")
      end

      it "should not allow duplicates with periods and plus signs" do
        dup_request = build(:invite_request, email: "the.god.thor+marvel@gmail.com")
        expect(dup_request.valid?).to be_falsey
        expect(dup_request.errors[:email]).to include("is already part of our queue.")
      end
    end
  end
end
