//package com.cryptotelegram.serviceImpl;
//
//import com.cryptotelegram.entity.BdCoins;
//import com.cryptotelegram.service.HttpRequestService;
//import com.google.gson.Gson;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//
//import java.io.IOException;
//
//public class HttpRequestImpl implements HttpRequestService {
//
//    private CloseableHttpClient httpClient = HttpClients.createDefault();
//    private HttpGet httpGet;
//    private HttpResponse httpResponse;
//
//    public HttpRequestImpl() {
//    }
//
//    @Override
//    public void sendGet(String address) {
//
//        httpGet = new HttpGet(address);
//
//        try {
//            httpResponse = httpClient.execute(httpGet);
//
//            Gson g = new Gson();
//            BdCoins coin = g.fromJson(httpResponse.getEntity().getContent().toString(), BdCoins.class);
//            System.out.println(coin.toString());
//
////            String text = "";
////            System.out.println("IN");
////            while (scanner.hasNext()){
////                text = text + scanner.nextLine();
////                System.out.println("OUT");
////            }
////        return null;
//
//        } catch (IOException e) {
//            e.printStackTrace();
////            return null;
////        }
//
//        }
//    }
//
//    @Override
//     public String sendPost(String address) {
////        StringEntity requestEntity = new StringEntity(
////                "{\"id\":\"1\"}",
////                ContentType.APPLICATION_JSON);
////
////        postMethod = new HttpPost(address);
////
////        postMethod.setEntity(requestEntity);
////
////        String text="";
////        try {
////            httpResponse = httpClient.execute(postMethod);
////
////        Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
////        System.out.println("IN");
////        while (scanner.hasNext()){
////            text = text + scanner.nextLine();
////            System.out.println("OUT");
////        }
////        return text;
////        } catch (IOException e) {
////            e.printStackTrace();
//            return "";
////        }
//    }
//
//}
