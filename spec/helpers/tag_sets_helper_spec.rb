require 'spec_helper'

describe TagSetsHelper do
  describe 'nomination_notes' do
    before(:each) do
      @limit = HashWithIndifferentAccess.new
      @limit[:fandom] = 3
      @limit[:character] = 3
      @limit[:relationship] = 3
      @limit[:freeform] = 3
    end

    context 'for nominations allowing only freeforms' do
      it 'should say you can nominate up to a certain amount' do
        @limit[:fandom] = 0
        @limit[:character] = 0
        @limit[:relationship] = 0
        expect(helper.nomination_notes(@limit)).
          to eq('You can nominate up to 3 additional tags.')
      end
    end

    context 'for nominations allowing relationships' do
      it 'should have relationships info listed last' do
        expect(helper.nomination_notes(@limit)).
          to match('characters and 3 relationships for each one.')
      end
    end

    context 'for nominations allowing NO relationships' do
      it 'should not mention relationships' do
        @limit[:relationship] = 0
        expect(helper.nomination_notes(@limit)).
          to match('3 fandoms and up to 3 characters for each one.')
      end
    end

    context 'for nominations allowing fandoms, NO characters,
        and NO relationships' do
      it 'should mention only fandoms' do
        @limit[:relationship] = 0
        @limit[:character] = 0
        expect(helper.nomination_notes(@limit)).
          to match('You can nominate up to 3 fandoms.')
      end
    end

    context 'for nominations allowing fandoms, relationships,
        and NO characters' do
      it 'should mentions fandoms and relationships' do
        @limit[:character] = 0
        expect(helper.nomination_notes(@limit)).
          to match('3 fandoms and up to 3 relationships for each one.')
      end
    end

    context 'for nominations allowing characters, relationships, NO fandoms' do
      it 'should mention characters and relationships' do
        @limit[:fandom] = 0
        expect(helper.nomination_notes(@limit)).
          to match('You can nominate up to 3 characters and 3 relationships.')
      end
    end

    context 'for nominations allowing characters, NO relationships,
        NO fandoms' do
      it 'should mention only characters' do
        @limit[:fandom] = 0
        @limit[:relationship] = 0
        @limit[:freeform] = 0
        expect(helper.nomination_notes(@limit)).
          to eq('You can nominate up to 3 characters.')
      end
    end

    context 'for nominations allowing relationships, NO characters,
        NO fandoms' do
      it 'should mention only relationships' do
        @limit[:fandom] = 0
        @limit[:character] = 0
        @limit[:freeform] = 0
        expect(helper.nomination_notes(@limit)).
          to eq('You can nominate up to 3 relationships.')
      end
    end
  end

  describe 'nomination_status' do
    before(:each) do
      @nomination = FactoryBot.create(:tag_nomination)
    end

    context 'for valid nominations' do
      it 'should show basic information' do
        expect(helper.nomination_status(@fake_nomination)).
          to include('unreviewed').and include('?!').
          and include('This nomination has not been reviewed yet.')
      end
    end

    context 'for approved nominations' do
      it 'should show correct class information' do
        @nomination.approved = true
        expect(helper.nomination_status(@nomination)).
          to include('approved').
          and include('This nomination has been approved!').
          and include('&#10004;')
      end
    end

    context 'for rejected nominations' do
      it 'should show correct class information' do
        @nomination.rejected = true
        @nomination.approved = false
        expect(helper.nomination_status(@nomination)).
          to include('rejected').
          and include('This nomination was rejected').
          and include('&#10006;')
      end
    end

    context 'for unreviewed nominations' do
      it 'should show correct class information' do
        @nomination.owned_tag_set.nominated = true
        @nomination.approved = ''
        @nomination.rejected = ''
        @tag_set = FactoryBot.create(:owned_tag_set)
        expect(helper.nomination_status(@nomination)).
          to include('unreviewed').
          and include('has not been reviewed yet and can still be changed.').
          and include('?!')
      end
    end
  end

  describe 'nomination_tag_information' do
    before(:each) do
      @nominated_tag = FactoryBot.create(:tag_nomination)
    end

    context 'for valid information' do
      it 'should show basic information' do
        @nominated_tag.canonical = false
        @nominated_tag.exists = false
        expect(helper.nomination_tag_information(@nominated_tag)).
          to include('nonexistent').
          and include('nonexistent tag').
          and include('This tag has never been used before.')
      end
    end

    context 'for canonical tags' do
      it 'should show correct tag information for parented tags' do
        @nominated_tag.canonical = true
        @nominated_tag.parented = true
        expect(helper.nomination_tag_information(@nominated_tag)).
          to include('This is a canonical tag.').
          and include("title=\"canonical tag\"").
          and include('canonical nomination')
      end

      it 'should show correct tag information for unparented tags' do
        @nominated_tag.canonical = true
        @nominated_tag.parented = false
        expect(helper.nomination_tag_information(@nominated_tag)).
          to include('but not associated with the').
          and include('tag without parent')
      end
    end

    context 'for synonym tags' do
      xit 'should show correct tag information for synonym tags' do
        # I've left this here so others can see what I was attempting to do.
        # It looks like the Fandom object is changing (being recreated) between
        # what it looks like here in this test, and when it shows up in the
        # 'nomination_tag_information' method in the tags_helper.rb file.
        @synonym = FactoryBot.create(:tag_nomination)
        @synonym.synonym = true
        @synonym.canonical = false
        @nominated_tag.canonical = true
        @synonym.owned_tag_set.
          tags.last.merger = @nominated_tag.owned_tag_set.tags.last
        @synonym.tagname = @synonym.owned_tag_set.tags.last.name

        expect(helper.nomination_tag_information(@synonym)).
          to include("title=\"synonym\"").
          and include('This is a synonym of a canonical tag.').
          and include('non-canonical tag')
      end
    end

    context 'for exists tags' do
      it 'should show correct tag information for tags that exist' do
        @nominated_tag.exists = true
        @nominated_tag.canonical = false

        expect(helper.nomination_tag_information(@nominated_tag)).
          to include("title=\"non-canonical tag\"").
          and include('This is not a canonical tag.').
          and include('unwrangled')
      end
    end
  end

  describe 'tag_relation_to_list' do
    context 'for a single type of tag' do
      it 'should return a list of single-type tags' do
        @owned_tag_set = FactoryBot.create(:owned_tag_set)
        expect(helper.tag_relation_to_list(@owned_tag_set.with_type(Fandom))).
          to include("<li>#{@owned_tag_set.tags.last.name}</li>")
      end
    end
  end
end
