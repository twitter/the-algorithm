require 'spec_helper'

describe Work do
  # see lib/collectible_spec for collection-related tests

  it "creates a minimal work" do
    expect(create(:work)).to be_valid
  end

  context "when posted" do
    it "posts the first chapter" do
      work = create(:work)
      work.first_chapter.posted.should == true
    end
  end

  context "create_stat_counter" do
    it "creates a stat counter for that work id" do
      expect {
        @work = build(:work)
        @work.save!
      }.to change{ StatCounter.all.count }.by(1)
      expect(StatCounter.where(work_id: @work.id)).to exist
    end
  end

  context "invalid title" do
    it { expect(build(:work, title: nil)).to be_invalid }

    it "cannot be shorter than ArchiveConfig.TITLE_MIN" do
      expect(build(:work, title: Faker::Lorem.characters(number: ArchiveConfig.TITLE_MIN - 1))).to be_invalid
    end

    it "cannot be longer than ArchiveConfig.TITLE_MAX" do
      expect(build(:work, title: Faker::Lorem.characters(number: ArchiveConfig.TITLE_MAX + 1))).to be_invalid
    end
  end

  context "clean_and_validate_title" do
    before do
      ArchiveConfig.TITLE_MIN = 10
    end
    it "strips out leading spaces from the title" do
      @work = create(:work, title: "    Has Leading Spaces")
      @work.reload
      expect(@work.title).to eq("Has Leading Spaces")
    end

    let(:too_short) { ArchiveConfig.TITLE_MIN - 1 }
    it "errors if the title without leading spaces is shorter than #{ArchiveConfig.TITLE_MIN}" do
      expect {
        @work = create(:work, title: "     #{too_short}")
        @work.reload
      }.to raise_error(ActiveRecord::RecordInvalid,"Validation failed: Title must be at least #{ArchiveConfig.TITLE_MIN} characters long without leading spaces.")
    end

    # Reset the min characters in the title, so that the factory is valid
    after do
      ArchiveConfig.TITLE_MIN = 1
    end
  end

  context "invalid summary" do
    it "cannot be longer than ArchiveConfig.SUMMARY_MAX" do
      expect(build(:work, title: Faker::Lorem.characters(number: ArchiveConfig.SUMMARY_MAX + 1))).to be_invalid
    end
  end

  context "invalid notes" do
    it "cannot be longer than ArchiveConfig.NOTES_MAX" do
      expect(build(:work, title: Faker::Lorem.characters(number: ArchiveConfig.NOTES_MAX + 1))).to be_invalid
    end
  end

  context "invalid endnotes" do
    it "cannot be longer than ArchiveConfig.NOTES_MAX" do
      expect(build(:work, title: Faker::Lorem.characters(number: ArchiveConfig.NOTES_MAX + 1))).to be_invalid
    end
  end

  context "invalid language" do
    let(:deleted_language_id) do
      briefly_lived_language = create(:language)
      deleted_language_id = briefly_lived_language.id
      briefly_lived_language.destroy
      deleted_language_id
    end

    it "is valid with a supported language" do
      work = build(:work, language_id: Language.default.id)
      expect(work).to be_valid
    end

    it "is not valid with a language we don't support" do
      work = build(:work, language_id: deleted_language_id)

      expect(work).not_to be_valid
      expect(work.errors.messages[:base]).to include("Language cannot be blank.")
    end

    it "is not valid without a language" do
      work = build(:work, language_id: "")

      expect(work).not_to be_valid
      expect(work.errors.messages[:base]).to include("Language cannot be blank.")
    end
  end

  context "validate authors" do
    let(:invalid_work) { build(:no_authors) }

    it "does not save if author is blank" do
      expect(invalid_work.save).to be_falsey
      expect(invalid_work.errors[:base]).to include "Work must have at least one creator."
    end
  end

  describe "#crossover" do
    it "is not crossover with one fandom" do
      fandom = create(:canonical_fandom, name: "nge")
      work = create(:work, fandoms: [fandom])
      expect(work.crossover).to be_falsy
    end

    it "is not crossover with one fandom and one of its synonyms" do
      rel = create(:canonical_fandom, name: "evanescence")
      syn = create(:fandom, name: "can't wake up (wake me up inside)", merger: rel)
      work = create(:work, fandoms: [rel, syn])
      expect(work.crossover).to be_falsy
    end

    it "is not crossover with multiple synonyms of one fandom" do
      rel = create(:canonical_fandom, name: "nge")
      syn1 = create(:fandom, name: "eva", merger: rel)
      syn2 = create(:fandom, name: "end of eva", merger: rel)
      work = create(:work, fandoms: [syn1, syn2])
      expect(work.crossover).to be_falsy
    end

    it "is not crossover with fandoms sharing a direct meta tag" do
      rel1 = create(:canonical_fandom, name: "rebuild")
      rel2 = create(:canonical_fandom, name: "campus apocalypse")
      meta_tag = create(:canonical_fandom, name: "nge")
      meta_tag.update_attribute(:sub_tag_string, "#{rel1.name},#{rel2.name}")
      rel1.reload
      rel2.reload

      work = create(:work, fandoms: [rel1, rel2])
      expect(work.crossover).to be_falsy
    end

    it "is not a crossover between fandoms sharing an indirect meta tag" do
      grand = create(:canonical_fandom)
      parent1 = create(:canonical_fandom)
      parent2 = create(:canonical_fandom)
      child1 = create(:canonical_fandom)
      child2 = create(:canonical_fandom)

      grand.update_attribute(:sub_tag_string, "#{parent1.name},#{parent2.name}")
      child1.update_attribute(:meta_tag_string, parent1.name)
      child2.update_attribute(:meta_tag_string, parent2.name)

      work = create(:work, fandom_string: "#{child1.name},#{child2.name}")
      expect(work.crossover).to be_falsey
    end

    it "is crossover with fandoms in different meta tag trees" do
      rel1 = create(:canonical_fandom, name: "rebuild again eventually")
      rel2 = create(:canonical_fandom, name: "evanescence")
      meta_tag = create(:canonical_fandom, name: "rebuild")
      meta_tag.update_attribute(:sub_tag_string, rel1.name)
      super_meta_tag = create(:canonical_fandom, name: "nge")
      super_meta_tag.update_attribute(:sub_tag_string, meta_tag.name)

      rel1.reload
      rel2.reload
      meta_tag.reload
      super_meta_tag.reload

      work = create(:work, fandoms: [rel1, rel2])
      expect(work.crossover).to be_truthy

      work = create(:work, fandoms: [meta_tag, super_meta_tag])
      expect(work.crossover).to be_falsy
    end

    it "is crossover with unrelated fandoms" do
      ships = [create(:canonical_fandom, name: "nge"), create(:canonical_fandom, name: "evanescence")]
      work = create(:work, fandoms: ships)
      expect(work.crossover).to be_truthy
    end

    it "is a crossover when missing meta-taggings" do
      f1 = create(:canonical_fandom)
      f2 = create(:canonical_fandom)
      f3 = create(:canonical_fandom)
      unrelated = create(:canonical_fandom)

      f2.update_attribute(:meta_tag_string, f3.name)
      f2.update_attribute(:sub_tag_string, f1.name)
      f1.meta_tags.delete(f3)

      work = create(:work, fandom_string: "#{f1.name}, #{unrelated.name}")
      expect(work.crossover).to be_truthy
    end

    context "when one tagged fandom has two unrelated meta tags" do
      let(:meta1) { create(:canonical_fandom) }
      let(:meta2) { create(:canonical_fandom) }
      let(:fandom) { create(:canonical_fandom) }

      before do
        fandom.update_attribute(:meta_tag_string, "#{meta1.name},#{meta2.name}")
      end

      it "is not a crossover with the fandom's synonym" do
        syn = create(:fandom, merger: fandom)
        work = create(:work, fandom_string: "#{fandom.name},#{syn.name}")
        expect(work.crossover).to be_falsey
      end

      it "is not a crossover with the fandom's meta tag" do
        work = create(:work, fandom_string: "#{fandom.name},#{meta1.name}")
        expect(work.crossover).to be_falsey
      end

      it "is not a crossover with another subtag of the fandom's meta tag" do
        sub = create(:canonical_fandom)
        sub.update_attribute(:meta_tag_string, meta1.name)
        work = create(:work, fandom_string: "#{fandom.name},#{sub.name}")
        expect(work.crossover).to be_falsey
      end

      it "is not a crossover with another fandom sharing the same two meta tags" do
        other = create(:canonical_fandom)
        other.update_attribute(:meta_tag_string, "#{meta1.name},#{meta2.name}")
        work = create(:work, fandom_string: "#{fandom.name},#{other.name}")
        expect(work.crossover).to be_falsey
      end

      it "is not a crossover with another fandom with two unrelated meta tags, only one of which is shared by both fandoms" do
        # The tag fandom and the tag other share one meta tag (meta2), but
        # fandom has a meta tag meta1 completely unrelated to other, and other
        # has a meta tag meta3 completely unrelated to fandom. However, the
        # shared meta tag means that they are related, and thus a work tagged
        # with both is not a crossover.
        meta3 = create(:canonical_fandom)
        other = create(:canonical_fandom)
        other.update_attribute(:meta_tag_string, "#{meta2.name},#{meta3.name}")
        work = create(:work, fandom_string: "#{fandom.name},#{other.name}")
        expect(work.crossover).to be_falsey
      end

      it "is a crossover with another fandom with two unrelated meta tags, when none of the meta tags are shared" do
        meta3 = create(:canonical_fandom)
        meta4 = create(:canonical_fandom)
        other = create(:canonical_fandom)
        other.update_attribute(:meta_tag_string, "#{meta3.name},#{meta4.name}")
        work = create(:work, fandom_string: "#{fandom.name},#{other.name}")
        expect(work.crossover).to be_truthy
      end
    end
  end

  describe "#otp" do
    it "is not otp with no relationship" do
      work = create(:work)
      expect(work.relationships).to be_empty
      expect(work.otp).to be_falsy
    end

    it "is otp with only one relationship" do
      rel = create(:relationship, name: "asushin")
      work = create(:work, relationships: [rel])
      expect(work.otp).to be_truthy
    end

    it "is otp with one canonical relationship and one of its synonyms" do
      rel = create(:canonical_relationship, name: "kawoshin")
      syn = create(:relationship, name: "shinkawo", merger: rel)
      work = create(:work, relationships: [rel, syn])
      expect(work.otp).to be_truthy
    end

    it "is otp with multiple synonyms of the same canonical relationship" do
      rel = create(:canonical_relationship, name: "kawoshin")
      syn1 = create(:relationship, name: "shinkawo", merger: rel)
      syn2 = create(:relationship, name: "kaworu/shinji", merger: rel)
      work = create(:work, relationships: [syn1, syn2])
      expect(work.otp).to be_truthy
    end

    it "is not otp with unrelated relationships, one of which is canonical" do
      ships = [create(:relationship, name: "shinrei"), create(:canonical_relationship, name: "asurei")]
      work = create(:work, relationships: ships)
      expect(work.otp).to be_falsy
    end

    it "is not otp with unrelated relationships" do
      ships = [create(:relationship, name: "asushin"), create(:relationship, name: "asurei")]
      work = create(:work, relationships: ships)
      expect(work.otp).to be_falsy
    end

    it "is not otp with relationships sharing a meta tag" do
      rel1 = create(:canonical_relationship, name: "shinrei")
      rel2 = create(:canonical_relationship, name: "asurei")
      meta_tag = create(:canonical_relationship)
      meta_tag.update_attribute(:sub_tag_string, "#{rel1.name},#{rel2.name}")
      rel1.reload
      rel2.reload

      work = create(:work, relationships: [rel1, rel2])
      expect(work.otp).to be_falsy
    end
  end

  describe "#authors_to_sort_on" do
    let(:work) { build(:work) }

    context "when the pseuds start with special characters" do
      it "removes those characters" do
        allow(work).to receive(:pseuds).and_return [Pseud.new(name: "-jolyne")]
        expect(work.authors_to_sort_on).to eq "jolyne"

        allow(work).to receive(:pseuds).and_return [Pseud.new(name: "_hermes")]
        expect(work.authors_to_sort_on).to eq "hermes"
      end
    end

    context "when the pseuds start with numbers" do
      it "does not remove numbers" do
        allow(work).to receive(:pseuds).and_return [Pseud.new(name: "007james")]
        expect(work.authors_to_sort_on).to eq "007james"
      end
    end

    context "when the work is anonymous" do
      it "returns Anonymous" do
        work.in_anon_collection = true
        allow(work).to receive(:pseuds).and_return [Pseud.new(name: "stealthy")]
        expect(work.authors_to_sort_on).to eq "Anonymous"
      end
    end

    context "when the work has multiple pseuds" do
      it "sorts them like the byline then joins them with commas" do
        allow(work).to receive(:pseuds).and_return [Pseud.new(name: "diavolo"), Pseud.new(name: "doppio")]
        expect(work.authors_to_sort_on).to eq "diavolo,  doppio"

        allow(work).to receive(:pseuds).and_return [Pseud.new(name: "Tiziano"), Pseud.new(name: "squalo")]
        expect(work.authors_to_sort_on).to eq "squalo,  tiziano"
      end
    end
  end

  describe "work_skin_allowed" do
    context "public skin"

    context "private skin" do
      before :each do
        @skin_author = create(:user)
        @second_author = create(:user)
        @private_skin = create(:work_skin, :private, author_id: @skin_author.id)
      end

      let(:work_author) { @skin_author }
      let(:work) { build(:custom_work_skin, authors: [work_author.pseuds.first], work_skin_id: @private_skin.id) }
      it "can be used by the work skin author" do
        expect(work.save).to be_truthy
      end

      let(:work) { build(:custom_work_skin, authors: [@second_author.pseuds.first], work_skin_id: @private_skin.id) }
      it "cannot be used by another user" do
        work.work_skin_allowed
        expect(work.errors[:base]).to include("You do not have permission to use that custom work stylesheet.")
      end
    end
  end

  describe "new gifts virtual attribute" do
    let(:recipient1) { create(:user).pseuds.first.name }
    let(:recipient2) { create(:user).pseuds.first.name }
    let(:recipient3) { create(:user).pseuds.first.name }

    let(:work) { build(:work) }

    before do
      work.recipients = recipient1 + "," + recipient2
    end

    it "contains gifts for the same recipients when they are first added" do
      expect(work.new_gifts.collect(&:recipient)).to eq([recipient1, recipient2])
    end

    it "only contains a gift for the new recipient if replacing the previous recipients" do
      work.recipients = recipient3
      expect(work.new_gifts.collect(&:recipient)).to eq([recipient3])
    end

    it "simple assignment works" do
      work.recipients = recipient2
      expect(work.new_gifts.collect(&:recipient)).to eq([recipient2])
    end

    it "only contains one gift if the same recipient is entered twice" do
      work.recipients = recipient2 + "," + recipient2
      expect(work.new_gifts.collect(&:recipient)).to eq([recipient2])
    end
  end

  describe "#find_by_url" do
    it "should find imported works with various URL formats" do
      [
        'http://foo.com/bar.html',
        'http://foo.com/bar',
        'http://lj-site.com/bar/foo?color=blue',
        'http://www.foo.com/bar'
      ].each do |url|
        work = create(:work, imported_from_url: url)
        expect(Work.find_by_url(url)).to eq(work)
        work.destroy
      end
    end

    it "should not mix up imported works with similar URLs or significant query parameters" do
      {
        'http://foo.com/12345' => 'http://foo.com/123',
        'http://efiction-site.com/viewstory.php?sid=123' => 'http://efiction-site.com/viewstory.php?sid=456',
        'http://www.foo.com/i-am-something' => 'http://foo.com/i-am-something/else'
      }.each do |import_url, find_url|
        work = create(:work, imported_from_url: import_url)
        expect(Work.find_by_url(find_url)).to_not eq(work)
        work.destroy
      end
    end

    it "should find works imported with irrelevant query parameters" do
      work = create(:work, imported_from_url: "http://lj-site.com/thing1?style=mine")
      expect(Work.find_by_url("http://lj-site.com/thing1?style=other")).to eq(work)
      work.destroy
    end

    it "gets the work from cache when searching for an imported work by URL" do
      url = "http://lj-site.com/thing2"
      work = create(:work, imported_from_url: url)
      expect(Rails.cache.read(Work.find_by_url_cache_key(url))).to be_nil
      expect(Work.find_by_url(url)).to eq(work)
      expect(Rails.cache.read(Work.find_by_url_cache_key(url))).to eq(work)
      work.destroy
    end
  end

  describe "#update_complete_status" do
    it "marks a work complete when it's been completed" do
      work = create(:work, expected_number_of_chapters: 1)
      expect(work.complete).to be_truthy
    end

    it "marks a work incomplete when it's no longer completed" do
      work = create(:work, expected_number_of_chapters: 1)
      work.update!(expected_number_of_chapters: nil)
      expect(work.reload.complete).to be_falsey
    end
  end

  describe "#wip_length" do
    it "updating chapter count via wip_length sets a sensible expected_number_of_chapters value" do
      work = create(:work)
      create(:chapter, work: work)
      work.reload

      work.wip_length = 1
      expect(work.expected_number_of_chapters).to be_nil
      expect(work.wip_length).to eq("?")

      work.wip_length = 2
      expect(work.expected_number_of_chapters).to eq(2)
      expect(work.wip_length).to eq(work.expected_number_of_chapters)

      work.wip_length = 3
      expect(work.expected_number_of_chapters).to eq(3)
      expect(work.wip_length).to eq(work.expected_number_of_chapters)
    end
  end

  describe "#hide_spam" do
    before do
      @admin_setting = AdminSetting.first || AdminSetting.create
      @work = create(:work)
    end
    context "when the admin setting is enabled" do
      before do
        @admin_setting.update_attribute(:hide_spam, true)
      end
      it "automatically hides spam works and sends an email" do
        expect { @work.update!(spam: true) }.
          to change { ActionMailer::Base.deliveries.count }.by(1)
        expect(@work.reload.hidden_by_admin).to be_truthy
        expect(ActionMailer::Base.deliveries.last.subject).to eq("[AO3] Your work was hidden as spam")
      end
    end
    context "when the admin setting is disabled" do
      before do
        @admin_setting.update_attribute(:hide_spam, false)
      end
      it "does not automatically hide spam works and does not send an email" do
        expect { @work.update!(spam: true) }.
          not_to change { ActionMailer::Base.deliveries.count }
        expect(@work.reload.hidden_by_admin).to be_falsey
      end
    end
  end

  describe "co-creator permissions" do
    let(:creator) { create(:user) }
    let(:co_creator) { create(:user) }
    let(:no_co_creator) { create(:user) }

    before do
      # In order to enable co-creator checks (instead of just having everything
      # be automatically approved), we need to make sure that User.current_user
      # is not nil.
      User.current_user = creator
      co_creator.preference.update(allow_cocreator: true)
      no_co_creator.preference.update(allow_cocreator: false)
    end

    it "allows normal users to invite others as chapter co-creators" do
      work = create(:work, authors: creator.pseuds)
      work.author_attributes = { byline: co_creator.login }
      expect(work).to be_valid
      expect(work.save).to be_truthy
      expect(work.user_has_creator_invite?(co_creator)).to be_truthy
    end

    it "doesn't allow users to invite others who disallow co-creators" do
      work = create(:work, authors: creator.pseuds)
      work.author_attributes = { byline: no_co_creator.login }
      expect(work).to be_invalid
      expect(work.save).to be_falsey
      expect(work.user_has_creator_invite?(no_co_creator)).to be_falsey
    end
  end

  describe "#remove_author" do
    let(:to_remove) { create(:user) }
    let(:other) { create(:user) }

    context "when all the pseuds on the work are owned by one user" do
      let(:pseud1) { create(:pseud, user: to_remove) }
      let(:pseud2) { create(:pseud, user: to_remove) }
      let(:pseud3) { create(:pseud, user: to_remove) }

      let!(:work) do
        create(:work, authors: [pseud1, pseud2, pseud3])
      end

      let!(:solo1) { create(:chapter, work: work, authors: [pseud1]) }
      let!(:solo2) { create(:chapter, work: work, authors: [pseud2]) }
      let!(:solo3) { create(:chapter, work: work, authors: [pseud3]) }

      before { work.reload }

      it "raises an error" do
        expect { work.remove_author(to_remove) }.to raise_exception(
          "Sorry, we can't remove all creators of a work."
        )
      end
    end

    context "when the work has a chapter whose sole creator is being removed" do
      let!(:work) do
        create(:work, authors: [to_remove.default_pseud, other.default_pseud])
      end

      let!(:solo_chapter) do
        create(:chapter, work: work, authors: [to_remove.default_pseud])
      end

      # Make sure we see the newest chapter:
      before { work.reload }

      it "sets the chapter's creators equal to the work's" do
        work.remove_author(to_remove)
        expect(work.pseuds.reload).to contain_exactly(other.default_pseud)
        expect(solo_chapter.pseuds.reload).to contain_exactly(other.default_pseud)
      end
    end
  end

  describe "#destroy" do
    let(:work) { create(:work) }

    it "does not save an original creator record" do
      expect { work.destroy }.not_to change { WorkOriginalCreator.count }
    end

    context "when an original creator exists" do
      let!(:original_creator) { create(:work_original_creator, work: work) }

      it "deletes the original creator" do
        work.destroy
        expect { original_creator.reload }.to raise_error(ActiveRecord::RecordNotFound)
      end
    end
  end

  describe "#allow_collection_invitation?" do
    let(:creator1) { create(:user) }
    let(:creator2) { create(:user) }
    let(:work) { create(:work, authors: [creator1.default_pseud, creator2.default_pseud]) }

    context "when all creators allow collection invitations" do
      before do
        creator1.preference.update(allow_collection_invitation: true)
        creator2.preference.update(allow_collection_invitation: true)
      end

      it "returns true" do
        expect(work.allow_collection_invitation?).to be true
      end
    end

    context "when all creators disallow collection invitations" do
      it "returns false" do
        expect(work.allow_collection_invitation?).to be false
      end
    end

    context "when creators have a mix of collection invitation preferences" do
      before do
        creator1.preference.update(allow_collection_invitation: true)
      end

      it "returns true" do
        expect(work.allow_collection_invitation?).to be true
      end
    end
  end
end
