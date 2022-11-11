package biz.piwowarczyk.untappd.api.scraper.jsoupExtension;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

public class ElementsWrapper {

    private Elements currentElements;
    private Element currentElement;

    public ElementsWrapper(Elements currentElements) {
        this.currentElements = currentElements;
    }

    public ElementsWrapper(Element currentElement) {
        this.currentElement = currentElement;
    }

    public ElementsWrapper getFirst() {
        currentElement = CollectionUtils.isEmpty(currentElements) ? null : currentElements.get(0);
        return this;
    }

    public ElementsWrapper select(String selector) {
        currentElements = currentElement != null ? currentElement.select(selector) : null;
        return this;
    }

    public String attr(String selector) {
        return currentElement != null ? currentElement.attr(selector) : null;
    }
}
