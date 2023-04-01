# frozen_string_literal: true
require "spec_helper"

describe WorksController do
  include LoginMacros

  describe "import" do
    context "should return the right error messages" do
      let(:user) { create(:user) }

      before do
        fake_login_known_user(user)
      end

      it "when urls are empty" do
        params = { urls: "" }
        get :import, params: params
        expect(flash[:error]).to eq "Did you want to enter a URL?"
      end

      it "there is an external author name but importing_for_others is NOT turned on" do
        params = {
          urls: "url1, url2",
          language_id: "en",
          external_author_name: "Foo",
          importing_for_others: false
        }
        get :import, params: params
        expect(flash[:error]).to start_with "You have entered an external author name"
      end

      it "there is an external author email but importing_for_others is NOT turned on" do
        params = {
          urls: "url1, url2",
          language_id: "en",
          external_author_email: "Foo",
          importing_for_others: false
        }
        get :import, params: params
        expect(flash[:error]).to start_with "You have entered an external author name"
      end

      context "the current user is NOT an archivist" do
        it "should error when importing_for_others is turned on" do
          params = { urls: "url1, url2", language_id: "en", importing_for_others: true }
          get :import, params: params
          expect(flash[:error]).to start_with "You may not import stories by other users"
        end

        it "should error when importing over the maximum number of works" do
          max = ArchiveConfig.IMPORT_MAX_WORKS
          urls = Array.new(max + 1) { |i| "url#{i}" }.join(", ")
          params = {
            urls: urls,
            language_id: "en",
            importing_for_others: false,
            import_multiple: "works"
          }
          get :import, params: params
          expect(flash[:error]).to start_with "You cannot import more than #{max}"
        end
      end

      context "the current user is an archivist" do
        it "should error when importing over the maximum number of works" do
          max = ArchiveConfig.IMPORT_MAX_WORKS_BY_ARCHIVIST
          urls = Array.new(max + 1) { |i| "url#{i}" }.join(", ")
          params = {
            urls: urls,
            language_id: "en",
            importing_for_others: false,
            import_multiple: "works"
          }
          allow_any_instance_of(User).to receive(:is_archivist?).and_return(true)

          get :import, params: params
          expect(flash[:error]).to start_with "You cannot import more than #{max}"

          allow_any_instance_of(User).to receive(:is_archivist?).and_call_original
        end

        it "should error when importing over the maximum number of chapters" do
          max = ArchiveConfig.IMPORT_MAX_CHAPTERS
          urls = Array.new(max + 1) { |i| "url#{i}" }.join(", ")
          params = {
            urls: urls,
            language_id: "en",
            importing_for_others: false,
            import_multiple: "chapters"
          }
          allow_any_instance_of(User).to receive(:is_archivist?).and_return(true)

          get :import, params: params
          expect(flash[:error]).to start_with "You cannot import more than #{max}"

          allow_any_instance_of(User).to receive(:is_archivist?).and_call_original
        end
      end
    end
  end

  # note: this non-action method renders a template before returning which Rspec can't process,
  # so calling the method directly requires a rescue to swallow the Rspec error
  describe "import_single" do
    it "should display the correct error when a timeout occurs" do
      allow_any_instance_of(StoryParser).to receive(:download_and_parse_story).and_raise(Timeout::Error)
      expect(controller.send(:import_single, ["url1"], {})).to rescue # stop it whingeing about the response
      expect(flash[:error]).to start_with "Import has timed out"
      allow_any_instance_of(StoryParser).to receive(:download_and_parse_story).and_call_original
    end

    it "should display the correct error when a StoryParser error occurs" do
      allow_any_instance_of(StoryParser).to receive(:download_and_parse_story).and_raise(StoryParser::Error.new("message"))
      expect(controller.send(:import_single, ["url1"], {})).to rescue # stop it whingeing about the response
      expect(flash[:error]).to start_with "We couldn't successfully import that work, sorry: message"
      allow_any_instance_of(StoryParser).to receive(:download_and_parse_story).and_call_original
    end
  end

  # note: this non-action method renders a template before returning which Rspec can't process,
  # so calling the method directly requires a rescue to swallow the Rspec error
  describe "import_multiple" do
    it "should display the correct error when an error occurs" do
      works_failed_errors = [[], ["url1"], ["error 1", "error 2"]]
      allow_any_instance_of(StoryParser).to receive(:import_from_urls).and_return(works_failed_errors)
      expect(controller.send(:import_multiple, ["url1"], {})).to rescue # stop it whingeing about the response
      expect(flash[:error]).to eq "<h3>Failed Imports</h3><dl><dt>url1</dt><dd>error 1</dd>\n<dt></dt><dd>error 2</dd></dl>"
      allow_any_instance_of(StoryParser).to receive(:import_from_urls).and_call_original
    end
  end
end
