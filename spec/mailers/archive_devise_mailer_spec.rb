require "spec_helper"

describe ArchiveDeviseMailer do
  describe "reset_password_instructions" do
    subject(:email) { ArchiveDeviseMailer.reset_password_instructions(user, token) }

    context "when user is User" do
      let(:user) { create(:user) }
      let(:token) { user.reset_password_token }

      # Test the headers
      it_behaves_like "an email with a valid sender"

      it "has the correct subject line" do
        subject = "[#{ArchiveConfig.APP_SHORT_NAME}] Reset your password"
        expect(email.subject).to eq(subject)
      end

      # Test both body contents
      it_behaves_like "a multipart email"

      it_behaves_like "a translated email"

      describe "HTML version" do
        it "has the correct content" do
          expect(email).to have_html_part_content("Dear <")
          expect(email).to have_html_part_content("Change my password.")
        end
      end

      describe "text version" do
        it "has the correct content" do
          expect(email).to have_text_part_content("Dear #{user.login},")
          expect(email).not_to have_text_part_content("Change my password.")
        end
      end
    end

    context "when user is Admin" do
      let(:user) { create(:admin) }
      let(:token) { user.reset_password_token }

      # Test the headers
      it_behaves_like "an email with a valid sender"

      it "has the correct subject line" do
        subject = "[#{ArchiveConfig.APP_SHORT_NAME}] Reset your admin password"
        expect(email.subject).to eq(subject)
      end

      # Test both body contents
      it_behaves_like "a multipart email"

      it_behaves_like "a translated email"

      describe "HTML version" do
        it "has the correct content" do
          expect(email).to have_html_part_content("Dear <")
          expect(email).to have_html_part_content("Change my password.")
        end
      end

      describe "text version" do
        it "has the correct content" do
          expect(email).to have_text_part_content("Dear #{user.login},")
          expect(email).not_to have_text_part_content("Change my password.")
        end
      end
    end
  end
end
