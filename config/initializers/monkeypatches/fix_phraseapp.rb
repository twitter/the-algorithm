module FixPhraseapp
  def translate(*args)
    if to_be_translated_without_phraseapp?(args)
      kw_args = args.last.is_a?(Hash) ? args.pop : {}
      I18n.translate_without_phraseapp(*args, **kw_args)
    else
      phraseapp_delegate_for(args)
    end
  end
end

if PhraseApp::VERSION == "1.6.0"
  PhraseApp::InContextEditor::BackendService.prepend(FixPhraseapp)
else
  puts "WARNING: The monkeypatch #{__FILE__} was written for version 1.6.0 of the phraseapp-in-context-editor-ruby gem, but you are running #{PhraseApp::VERSION}. Please update or remove the monkeypatch."
end
