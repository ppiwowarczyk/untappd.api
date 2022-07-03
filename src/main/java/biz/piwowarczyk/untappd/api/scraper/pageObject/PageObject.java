package biz.piwowarczyk.untappd.api.scraper.pageObject;

import org.jsoup.nodes.Document;

abstract class PageObject {
    protected final Document document;

    public PageObject(Document document) {
        this.document = document;
    }

    public String getTitle() {
        return document.title();
    }
}
