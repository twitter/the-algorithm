# encoding: UTF-8
# Almost entirely taken from http://thewebfellas.com/blog/2010/8/22/revisited-roll-your-own-pagination-links-with-will_paginate-and-rails-3
#
# <h4 class="landmark heading">Pages Navigation</h4>
# <ol class="navigation pagination actions" title="pagination" role="navigation">
# <li title="previous">&#8592;</li>
# <li><a href="">1</a></li>
# <li><span class="current" title="current">2</span><li>
# </ol>

class PaginationListLinkRenderer < WillPaginate::ActionView::LinkRenderer

  def to_html
    html = pagination.map do |item|
      if item.is_a?(Integer)
        page_number(item, @options[:remote])
      else
        send(item)
      end
    end.join(@options[:link_separator])

    @options[:container] ? html_container(html) : html
  end

  protected

    def gap
      tag(:li, "…", class: "gap")
    end

    def page_number(page, remote = nil)
      unless page == current_page
        tag(:li, link(page, page, {rel: rel_value(page)}.merge(:"data-remote" => remote)))
      else
        tag(:li, tag(:span, page, class: "current"))
      end
    end

    def previous_page
      num = @collection.current_page > 1 && @collection.current_page - 1
      previous_or_next_page(num, @options[:previous_label], 'previous', @options[:remote])
    end

    def next_page
      num = @collection.current_page < @collection.total_pages && @collection.current_page + 1
      previous_or_next_page(num, @options[:next_label], 'next', @options[:remote])
    end

    def previous_or_next_page(page, text, classname, remote = nil)
      if page
        tag(:li, link(text, page, {:"data-remote" => remote}), class: classname, title: classname)
      else
        tag(:li, tag(:span, text, class: "disabled"), class: classname, title: classname)
      end
    end

    def html_container(html)
      tag(:h4, "Pages Navigation", class: "landmark heading") +
        tag(:ol, html, container_attributes.merge(class: "pagination actions", role: "navigation", title: "pagination"))
    end

end
