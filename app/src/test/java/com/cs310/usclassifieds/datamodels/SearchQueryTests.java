package com.cs310.usclassifieds.datamodels;

import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.SearchQuery;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.cs310.usclassifieds.TestData.ALL_SEARCH_QUERIES;
import static com.cs310.usclassifieds.TestData.SEARCH_QUERY_1;
import static com.cs310.usclassifieds.TestData.SEARCH_QUERY_2;
import static com.cs310.usclassifieds.TestData.LOCATION_USC_HOTEL;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotSame;


public class SearchQueryTests {
    @Before
    public void setup() {
        for (SearchQuery sq : ALL_SEARCH_QUERIES) {
            // Default values after search query constructor
            sq.sellerIds = null;
            sq.searchTerms = null;
            sq.radius = -1;
            sq.origin = null;
        }
    }

    // tests addOrigin
    @Test
    public void testAddOrigin() {
        for (SearchQuery sq : ALL_SEARCH_QUERIES) {
            sq.addOrigin(LOCATION_USC_HOTEL);
            assertEquals(sq.origin, LOCATION_USC_HOTEL);
        }

        System.out.println("Add origin passed");
    }

    @Test
    public void testAddSellerId() {
        Integer sellerId1 = 12;
        Integer sellerId2 = 6;
        Integer sellerId3 = 0;
        for (SearchQuery sq : ALL_SEARCH_QUERIES) {
            sq.addSellerId(sellerId1);
            assertTrue(sq.sellerIds.contains(sellerId1));

            sq.addSellerId(sellerId2);
            assertTrue(sq.sellerIds.contains(sellerId2));

            assertFalse(sq.sellerIds.contains(sellerId3));
        }

        System.out.println("addSellerId passed");
    }

    // tests addRadius method
    @Test
    public void testAddRadius() {
        int radius1 = 3;
        int radius2 = 5;
        int radius3 = 6;

        for (SearchQuery sq : ALL_SEARCH_QUERIES) {
            assertEquals(-1, sq.radius); // Defaults to -1

            sq.addRadius(radius1);
            assertEquals(radius1, sq.radius);

            sq.addRadius(radius2);
            assertEquals(radius2, sq.radius);

            assertNotSame(radius3, sq.radius);
        }
    }

    // tests addSearchTerm method
    @Test
    public void testAddSearchTerm() {
        String searchTerm1 = "term1";
        String searchTerm2 = "term2";

        SEARCH_QUERY_1.addSearchTerm(searchTerm1);
        assertEquals(1, SEARCH_QUERY_1.searchTerms.size());
        assertEquals(searchTerm1, SEARCH_QUERY_1.searchTerms.get(0));


        SEARCH_QUERY_2.addSearchTerm(searchTerm1);
        SEARCH_QUERY_2.addSearchTerm(searchTerm2);

        assertEquals(2, SEARCH_QUERY_2.searchTerms.size());
        assertEquals(searchTerm1, SEARCH_QUERY_2.searchTerms.get(0));
        assertEquals(searchTerm2, SEARCH_QUERY_2.searchTerms.get(1));

        System.out.println("addSearchTerm passed");
    }

    @Test
    public void testSetSellerIds() {

        List<Integer> sellerIds1 = new ArrayList<Integer>();
        sellerIds1.add(1);
        sellerIds1.add(2);
        sellerIds1.add(3);

        List<Integer> sellerIds2 = new ArrayList<Integer>();
        sellerIds2.add(4);
        sellerIds2.add(5);

        SEARCH_QUERY_1.setSellerIds(sellerIds1);
        assertEquals(sellerIds1, SEARCH_QUERY_1.sellerIds);

        SEARCH_QUERY_1.setSellerIds(sellerIds2);
        assertEquals(sellerIds2, SEARCH_QUERY_1.sellerIds);

        System.out.println("setSellerIds passed");
    }

    @Test
    public void testSetSearchTerms() {
        List<String> searchTerms = new ArrayList<>();
        searchTerms.add("term1");
        searchTerms.add("term2");
        searchTerms.add("term3");

        SEARCH_QUERY_1.setSearchTerms(searchTerms);
        assertEquals(searchTerms, SEARCH_QUERY_1.searchTerms);

        System.out.println("setSearchTerms passed");
    }
}
