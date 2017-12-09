package Controlers;

import Databases.AuctionDataBase;
import Helper.FileOperations;
import models.Auction;
import models.User;
import views.AuctionView;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;

public class BidControler {
    public boolean bidAuction(User buyer, Auction auction, BigDecimal price, AuctionDataBase auctionDataBase) {
        boolean flag = auction.bidPrice(price);
        auction.setActualWinnerOfAuction(buyer);
        auctionDataBase.updateWinnerOfAuction(auction);
        return flag;
    }

    public void messageWhenNoSuchAuctionToBid() {
        AuctionView auctionView = new AuctionView();
        System.out.println(auctionView.printErrorWhenWrongAuctionChosenToBid());
    }
}
