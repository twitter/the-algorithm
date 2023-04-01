require "spec_helper"

describe TagSetAssociationsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:owned_tag_set) { create(:owned_tag_set) }
  let(:mod_pseud) {
    create(:pseud) do |pseud|
      owned_tag_set.add_moderator(pseud)
      owned_tag_set.save!
    end
  }
  let(:moderator) { mod_pseud.user }

  describe "PUT update_multiple" do
    context "when user is not logged in" do
      it "redirects and returns an error message" do
        put :update_multiple, params: { tag_set_id: owned_tag_set.id }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you " \
          "were trying to reach. Please log in.")
      end
    end

    context "when logged in user is moderator of tag set" do
      before do
        fake_login_known_user(moderator.reload)
      end

      context "no tag associations are saved" do
        it "redirects and returns a notice" do
          put :update_multiple, params: { tag_set_id: owned_tag_set.id }
          it_redirects_to_simple(tag_set_path(owned_tag_set))
          expect(flash[:notice]).to include("Nominated associations were added.")
        end
      end

      shared_examples "new tag association" do
        before do
          params = {
            tag_set_id: owned_tag_set.id,
            "create_association_#{tag.id}_#{parent_tag.name}": "1",
          }
          put :update_multiple, params: params
        end

        it "creates the new tag association" do
          expect(TagSetAssociation.count).to eq(1)
          assoc = TagSetAssociation.last
          expect(assoc.owned_tag_set).to eq(owned_tag_set)
          expect(assoc.tag).to eq tag
          expect(assoc.parent_tag).to eq parent_tag
        end

        it "redirects and returns a notice" do
          it_redirects_to_simple(tag_set_path(owned_tag_set))
          expect(flash[:notice]).to include("Nominated associations were added.")
        end
      end

      describe "for a non-fandom parent tag" do
        include_examples "new tag association" do
          let(:parent_tag) { create(:character) }
          let(:tag) { create(:character) }
        end
      end

      describe "for a character child tag" do
        include_examples "new tag association" do
          let(:parent_tag) { create(:fandom) }
          let(:tag) { create(:character) }
        end
      end

      describe "for a fandom child tag" do
        include_examples "new tag association" do
          let(:parent_tag) { create(:fandom) }
          let(:tag) { create(:fandom) }
        end
      end

      describe "for a relationship child tag" do
        include_examples "new tag association" do
          let(:parent_tag) { create(:fandom) }
          let(:tag) { create(:relationship) }
        end
      end

      context "multiple tag associations" do
        let(:parent_tag) { create(:fandom) }
        let(:child_tag_1) { create(:relationship) }
        let(:child_tag_2) { create(:relationship) }
        let(:child_tag_3) { create(:relationship) }

        before do
          params = {
            tag_set_id: owned_tag_set.id,
            "create_association_#{child_tag_1.id}_#{parent_tag.name}": "1",
            "create_association_#{child_tag_2.id}_#{parent_tag.name}": "1",
            "create_association_#{child_tag_3.id}_#{parent_tag.name}": "",
          }
          put :update_multiple, params: params
        end

        it "creates the new tag association" do
          expect(TagSetAssociation.count).to eq(2)

          assoc = TagSetAssociation.first
          expect(assoc.owned_tag_set).to eq(owned_tag_set)
          expect(assoc.tag).to eq child_tag_1
          expect(assoc.parent_tag).to eq parent_tag

          assoc = TagSetAssociation.last
          expect(assoc.owned_tag_set).to eq(owned_tag_set)
          expect(assoc.tag).to eq child_tag_2
          expect(assoc.parent_tag).to eq parent_tag
        end

        it "redirects and returns a notice" do
          it_redirects_to_simple(tag_set_path(owned_tag_set))
          expect(flash[:notice]).to include("Nominated associations were added.")
        end
      end

      context "some tag associations cannot be saved" do
        it "redirects and returns an error message" do
          params = {
            tag_set_id: owned_tag_set.id,
            "create_association_99999999_Hawaii+Seven-0+(2022)": "1",
          }
          put :update_multiple, params: params
          expect(response).to render_template("index")
          expect(flash[:error]).to include("We couldn't add all of your specified associations.")
        end
      end
    end
  end
end
