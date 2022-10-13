package com.cryptotelegram.service;

public interface HttpRequestService {

    void sendGet(String address);
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        HttpGet httpGet = new HttpGet(address);
//
//        HttpResponse httpResponse = httpClient.execute(httpGet);
//
//        Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
//        String text = "";
//        System.out.println("IN");
//        while (scanner.hasNext()){
//            text = text + scanner.nextLine();
//            System.out.println("OUT");
//        }
//        return text;

    ;


    String sendPost(String address);
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        StringEntity requestEntity = new StringEntity(
//                "{\"name\":\"Alexey\" , \"surname\":\"Baranov\"}",
//                ContentType.APPLICATION_JSON);
//        HttpPost postMethod = new HttpPost(address);
//        postMethod.setEntity(requestEntity);
//        HttpResponse httpResponse = httpClient.execute(postMethod);
//        Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
//        System.out.println("IN");
//        while (scanner.hasNext()){
//            System.out.println(scanner.nextLine());
//            System.out.println("OUT");
//        }
//        System.out.println(httpResponse.getStatusLine().getStatusCode());


}
