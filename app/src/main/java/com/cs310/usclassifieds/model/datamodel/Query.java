package com.cs310.usclassifieds.model.datamodel;

import java.util.ArrayList;
import java.util.List;

public class Query {
    public List<Integer> sellerIds;
    public List<String> searchTerms;
    public int radius;
    public Location origin;

    Query() {
        this.sellerIds = null;
        this.searchTerms = null;
        this.radius = -1;
        this.origin = null;
    }

    public void addOrigin(Location origin) {
        this.origin = origin;
    }

    public void addSellerId(Integer sellerId) {
        if(sellerIds == null) {
            sellerIds = new ArrayList<>();
        }

        sellerIds.add(sellerId);
    }

    public void addRadius(int radius) {
        this.radius = radius;
    }

    public void addSearchTerm(String searchTerm) {
        if(searchTerms == null) {
            searchTerms = new ArrayList<>();
        }

        searchTerms.add(searchTerm);
    }

    public void setSellerIds(List<Integer> sellerIds) {
        this.sellerIds = sellerIds;
    }

    public void setSearchTerms(List<String> searchTerms) {
        this.searchTerms = searchTerms;
    }
}
