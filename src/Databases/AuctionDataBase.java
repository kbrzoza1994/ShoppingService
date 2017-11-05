package Databases;

import Helper.FileOperations;

import models.Auction;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class AuctionDataBase implements Serializable {

    private int indexAuction = 1;
    private ArrayList<Auction> listOfAllAuction;

    public int getIndexAuction() {
        if (!listOfAllAuction.isEmpty())
        return (listOfAllAuction.get(listOfAllAuction.size()-1).getAuctionIndex() +1 );
        else
            return indexAuction;
    }

    public AuctionDataBase() {
        listOfAllAuction = FileOperations.loadAuctionList("Auction.bin");
    }


    public ArrayList<Auction> getListOfAllAuction() {
        return listOfAllAuction;
    }


    public boolean addAuction(Auction auction) {
        try {
            listOfAllAuction.add(auction);
            indexAuction++;
            FileOperations.saveAuctionList(listOfAllAuction, "Auction.bin");
        } catch (IOException e) {
            return false;
        }
        return true;
    }


    public boolean removeAuction(Auction auction) {
        try {
            auction.setActive(false);
            FileOperations.saveAuctionList(listOfAllAuction,"Auction.bin");
            return true;
        } catch (IOException e) {
            return false;
        }
    }


}
