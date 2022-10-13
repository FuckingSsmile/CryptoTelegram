//package com.cryptotelegram.entity;
//
//import java.util.Objects;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class BdCoins {
//
//    private long ID;
//    private long coinRank;
//    private String coinId;
//    private String coinName;
//    private String coinSymbol;
//    private String coinPair;
//    private String dateAth;
//    private double current_price;
//    private double maxPrice;
//    private double maxPrice24h;
//    private double minimumPrice24h;
//    private double changingPrice24h;
//    private double changingPriceInPercentage24h;
//    private String coinImage;
//    private String coinUrl;
//    private String sparkLine7d;
//    public static CopyOnWriteArrayList<BdCoins> bdCoinsList = new CopyOnWriteArrayList<>();
//
//    public BdCoins() {
//        bdCoinsList.add(this);
//    }
//
//    public long getID() {
//        return ID;
//    }
//
//    public void setID(long ID) {
//        this.ID = ID;
//    }
//
//    public long getCoinRank() {
//        return coinRank;
//    }
//
//    public void setCoinRank(long coinRank) {
//        this.coinRank = coinRank;
//    }
//
//    public String getCoinId() {
//        return coinId;
//    }
//
//    public void setCoinId(String coinId) {
//        this.coinId = coinId;
//    }
//
//    public String getCoinName() {
//        return coinName;
//    }
//
//    public void setCoinName(String coinName) {
//        this.coinName = coinName;
//    }
//
//    public String getCoinSymbol() {
//        return coinSymbol;
//    }
//
//    public void setCoinSymbol(String coinSymbol) {
//        this.coinSymbol = coinSymbol;
//    }
//
//    public String getCoinPair() {
//        return coinPair;
//    }
//
//    public void setCoinPair(String coinPair) {
//        this.coinPair = coinPair;
//    }
//
//    public String getDateAth() {
//        return dateAth;
//    }
//
//    public void setDateAth(String dateAth) {
//        this.dateAth = dateAth;
//    }
//
//    public double getCurrent_price() {
//        return current_price;
//    }
//
//    public void setCurrent_price(double current_price) {
//        this.current_price = current_price;
//    }
//
//    public double getMaxPrice() {
//        return maxPrice;
//    }
//
//    public void setMaxPrice(double maxPrice) {
//        this.maxPrice = maxPrice;
//    }
//
//    public double getMaxPrice24h() {
//        return maxPrice24h;
//    }
//
//    public void setMaxPrice24h(double maxPrice24h) {
//        this.maxPrice24h = maxPrice24h;
//    }
//
//    public double getMinimumPrice24h() {
//        return minimumPrice24h;
//    }
//
//    public void setMinimumPrice24h(double minimumPrice24h) {
//        this.minimumPrice24h = minimumPrice24h;
//    }
//
//    public double getChangingPrice24h() {
//        return changingPrice24h;
//    }
//
//    public void setChangingPrice24h(double changingPrice24h) {
//        this.changingPrice24h = changingPrice24h;
//    }
//
//    public double getChangingPriceInPercentage24h() {
//        return changingPriceInPercentage24h;
//    }
//
//    public void setChangingPriceInPercentage24h(double changingPriceInPercentage24h) {
//        this.changingPriceInPercentage24h = changingPriceInPercentage24h;
//    }
//
//    public String getCoinImage() {
//        return coinImage;
//    }
//
//    public void setCoinImage(String coinImage) {
//        this.coinImage = coinImage;
//    }
//
//    public String getCoinUrl() {
//        return coinUrl;
//    }
//
//    public void setCoinUrl(String coinUrl) {
//        this.coinUrl = coinUrl;
//    }
//
//    public String getSparkLine7d() {
//        return sparkLine7d;
//    }
//
//    public void setSparkLine7d(String sparkLine7d) {
//        this.sparkLine7d = sparkLine7d;
//    }
//
//    @Override
//    public String toString() {
//        return
//                "Rank: " + coinRank + '\n'+
//                "Id: " + coinId + '\n' +
//                "Name: " + coinName + '\n' +
//                "Symbol: " + coinSymbol + '\n' +
//                "Pair: " + coinPair + '\n' +
//                "Date ATH: " + dateAth + '\n' +
//                "Current price: " + current_price +'\n'+
//                "Maximum price: " + maxPrice +'\n'+
//                "Maximum price 24h: " + maxPrice24h +'\n'+
//                "Minimum price 24h: " + minimumPrice24h +'\n'+
//                "Changing price 24h: " + changingPrice24h +'\n'+
//                "Changing price in percentage 24h: " + changingPriceInPercentage24h +'\n'+
//                coinUrl;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        BdCoins bdCoins = (BdCoins) o;
//        return ID == bdCoins.ID && coinRank == bdCoins.coinRank && Objects.equals(coinId, bdCoins.coinId) && Objects.equals(coinName, bdCoins.coinName) && Objects.equals(coinSymbol, bdCoins.coinSymbol) && Objects.equals(coinPair, bdCoins.coinPair) && Objects.equals(coinImage, bdCoins.coinImage) && Objects.equals(coinUrl, bdCoins.coinUrl);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(ID, coinRank, coinId, coinName, coinSymbol, coinPair, coinImage, coinUrl);
//    }
//}
