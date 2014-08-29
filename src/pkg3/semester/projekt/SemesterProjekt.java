package pkg3.semester.projekt;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

/**
 * @author Lars Mortensen
 */
public class SemesterProjekt {
  static int port = 8080;
  static String ip = "127.0.0.1";
  public static void main(String[] args) throws Exception {
    if(args.length == 2){
      port = Integer.parseInt(args[0]);
      ip = args[0];
    }
    HttpServer server = HttpServer.create(new InetSocketAddress(ip,port), 0);
   // server.createContext("/welcome", new RequestHandler());
    server.createContext("/headers", new RequestHandler());
    server.setExecutor(null); // Use the default executor
    server.start();
    System.out.println("Server started, listening on port: "+port);
  }
  
 
  static class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
      String response = "Welcome to my very first almost home made Web Server :-)";
      StringBuilder sb = new StringBuilder();
      sb.append("<!DOCTYPE html>\n");
      sb.append("<html>\n");
      sb.append("<head>\n");
      sb.append("<title>My fancy Web Site</title>\n");
      sb.append("<meta charset='UTF-8'>\n");
      sb.append("</head>\n");
      sb.append("<body>\n");
      sb.append("<h2>Welcome to my very first home made Web Server :-)</h2>\n");
      sb.append("</body>\n");
      sb.append("</html>\n");
      response = sb.toString(); 
      Headers h = he.getResponseHeaders();
      h.add("Content-type", "text/html"); 
      he.sendResponseHeaders(200, response.length());
      try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
        pw.print(response); //What happens if we use a println instead of print --> Explain
      }
    }
  }
    static class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
      String response = "Welcome to headers)";
      StringBuilder sb = new StringBuilder();
      sb.append("<!DOCTYPE html>\n");
      sb.append("<html>\n");
      sb.append("<head>\n");
      sb.append("<title>My fancy Web Site</title>\n");
      sb.append("<meta charset='UTF-8'>\n");
      sb.append("</head>\n");
      sb.append("<body>\n");
      sb.append("<h2>Headers\n");
      sb.append("<table border=\"1\" style=\"width:300px\">\n");
      sb.append("<tr>");
      sb.append("<td>Jill</td>");
      sb.append("<td>Smith</td>");
      sb.append("<td>50</td>");
      
      sb.append("</body>\n");
      sb.append("</html>\n");
      response = sb.toString(); 
      Headers h = he.getResponseHeaders();
      h.add("Content-type", "text/html"); 
      he.sendResponseHeaders(200, response.length());
      try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
        pw.print(response); //What happens if we use a println instead of print --> Explain
      }
    }
  }
  
}