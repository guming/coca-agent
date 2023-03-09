package org.coca.agent.sample;

public class ExampleHttp {
    public static void main(String[] args) throws Exception {
//        HttpURLConnection urlConnection = (HttpURLConnection) new URL("http://www.google.com").openConnection();
//        System.out.println(urlConnection.getRequestMethod());
        DemoService demoService = new DemoService();
        System.out.println(demoService.report("mgu",2 ));
    }
}
