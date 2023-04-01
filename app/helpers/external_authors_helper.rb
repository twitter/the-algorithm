module ExternalAuthorsHelper

  def add_name_link(form_builder)
    link_to_function 'add name' do |page|
      form_builder.fields_for :external_author_names, ExternalAuthorName.new, child_index: 'NEW_RECORD' do |f|
        html = render(partial: 'external_author_name', locals: { form: f })
        page << "$('external_author_names').insert({ bottom: '#{escape_javascript(html)}'.replace(/NEW_RECORD/g, new Date().getTime()) });"
      end
    end
  end


  def remove_name_link(form_builder)
    if form_builder.object.new_record?
       # If the task is a new record, we can just remove the div from the dom
       link_to_function("remove", "$(this).up('.external_author_name').remove();");
     else
       # However if it's a "real" record it has to be deleted from the database,
       # for this reason the new fields_for, accept_nested_attributes helpers give us _delete,
       # a virtual attribute that tells rails to delete the child record.
       form_builder.hidden_field("_destroy") +
       link_to_function("remove", "$(this).up('.external_author_name').hide(); $(this).previous().value = '1'")
     end    
  end

end
