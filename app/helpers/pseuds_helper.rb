module PseudsHelper
  
  # Prints array of pseuds with links to user pages
  # used on Profile page
  def print_pseud_list(pseuds)
    pseuds.includes(:user).collect { |pseud| span_if_current(pseud.name, [pseud.user, pseud]) }.join(", ").html_safe
  end
  
  # used in the sidebar
  def print_pseud_selector(pseuds)
    pseuds -= [@pseud] if @pseud && @pseud.new_record?
    list = pseuds.sort.collect {|pseud| "<li>" + span_if_current(pseud.name, [pseud.user, pseud]) + "</li>"}.join("").html_safe
  end
end
