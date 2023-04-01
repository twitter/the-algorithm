require "spec_helper"

describe KudoMailer do
  describe "batch_kudo_notification" do
    subject(:email) { KudoMailer.batch_kudo_notification(creator.id, kudos_json) }

    let!(:creator) { work.users.first }
    let(:work) { create(:work) }

    context "when there is one work" do
      context "with one guest kudos" do
        let(:kudos_json) do
          hash = {}
          hash["#{work.class.name}_#{work.id}"] = { guest_count: 1, names: [] }
          hash.to_json
        end

        it_behaves_like "an email with a valid sender"

        it "has the correct subject line" do
          subject = "[#{ArchiveConfig.APP_SHORT_NAME}] You've got kudos!"
          expect(email).to have_subject(subject)
        end

        # Test both body contents
        it_behaves_like "a multipart email"

        it_behaves_like "a translated email"

        describe "HTML version" do
          it "has the correct content" do
            expect(email).to have_html_part_content("<b style=\"color:#990000\">A guest</b>")
            expect(email).to have_html_part_content("left kudos on <")
          end
        end

        describe "text version" do
          it "has the correct content" do
            expect(email).to have_text_part_content("A guest left kudos on \"#{work.title}\"")
          end
        end
      end
    end
  end
end
