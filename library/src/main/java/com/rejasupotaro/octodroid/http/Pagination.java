package com.rejasupotaro.octodroid.http;

public class Pagination {
    private Link[] links = new Link[]{};

    public boolean hasPrev() {
        return (getLinkByRel(Link.Rel.PREV) != null);
    }

    public boolean hasNext() {
        Link nextLink = getLinkByRel(Link.Rel.NEXT);
        return (nextLink != null) && (nextLink.getPage() > 0);
    }

    public boolean hasLast() {
        return (getLinkByRel(Link.Rel.LAST) != null);
    }

    public Link getFirstLink() {
        return getLinkByRel(Link.Rel.FIRST);
    }

    public Link getPrevLink() {
        return getLinkByRel(Link.Rel.PREV);
    }

    public Link getNextLink() {
        return getLinkByRel(Link.Rel.NEXT);
    }

    public Link getLastLink() {
        return getLinkByRel(Link.Rel.LAST);
    }

    private Link getLinkByRel(Link.Rel rel) {
        if (links == null) return null;

        for (Link link : links) {
            if (link.getRel() == rel) {
                return link;
            }
        }
        return null;
    }

    public Pagination() {}

    public Pagination(Link[] links) {
        this.links = links;
    }
}

