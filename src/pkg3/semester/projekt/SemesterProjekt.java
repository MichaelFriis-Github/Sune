package pkg3.semester.projekt;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Lars Mortensen
 */
public class SemesterProjekt {

    static int port = 8080;
    static String ip = "127.0.0.1";

    public static void main(String[] args) throws Exception {
        if (args.length == 2) {
            port = Integer.parseInt(args[0]);
            ip = args[0];
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);
        server.createContext("/welcome", new RequestHandlerWelcome());
        server.createContext("/headers", new RequestHandlerHeaders());
        server.createContext("/form", new RequestHandlerForm());
        server.createContext("/parameters", new RequestHandlerParameters());
        server.setExecutor(null); // Use the default executor
        server.start();
        System.out.println("Server started, listening on port: " + port);
    }

    static class RequestHandlerWelcome implements HttpHandler {

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

    static class RequestHandlerHeaders implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response = "Welcome to headers)";
            Map m = he.getResponseHeaders();
            Headers h = he.getResponseHeaders();
            String lol = "Hej";

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

            for (String key : he.getRequestHeaders().keySet()) {
                sb.append("<tr>");
                sb.append("<td>" + key + "</td>");
                sb.append("<td>" + he.getRequestHeaders().get(key) + "</td>");
            }
            sb.append("</body>\n");
            sb.append("</html>\n");
            response = sb.toString();
            h.add("Content-type", "text/html");
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response); //What happens if we use a println instead of print --> Explain
            }
        }
    }

    static class RequestHandlerForm implements HttpHandler {

        String contentFolder = "public/tables.html";

        public void handle(HttpExchange he) throws IOException {
            File file = new File(contentFolder);
            byte[] bytesToSend = new byte[(int) file.length()];
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                bis.read(bytesToSend, 0, bytesToSend.length);

            } catch (IOException ie) {
                ie.printStackTrace();
            }
            he.sendResponseHeaders(200, bytesToSend.length);
            try (OutputStream os = he.getResponseBody()) {
                os.write(bytesToSend, 0, bytesToSend.length);
            }
        }
    }

    static class RequestHandlerParameters implements HttpHandler {

        public void handle(HttpExchange he) throws IOException {

            String response = "";

            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>My fancy Web Site</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<h2>Headers\n");
            sb.append("</h2>\n");
            sb.append("<body>\n");
            sb.append("<p>Method is: " + he.getRequestMethod() + "</p>\n");
            sb.append("<p>Get-Parameters: " + he.getRequestURI().getQuery() + "</p>\n");
            sb.append(" <form action=\"/parameters\" method=\"GET\">\n"
                    + "            <input type=\"text\" name=\"first\" placeholder=\"First name\"/>\n"
                    + "            <input type=\"text\" name=\"last\" placeholder=\"Last name\"/>\n"
                    + "            <input type=\"submit\" />\n"
                    + "        </form>");
            Scanner scan = new Scanner(he.getRequestBody());
            while (scan.hasNext()) {
                sb.append("<p>Request body, with Post-parameters: " + scan.nextLine() + "</p>\n");
                sb.append("</br>");
            }
            sb.append("</body>");
            sb.append("</html>");
            response = sb.toString();

            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response); //What happens if we use a println instead of print --> Explain
            }
        }

    }
}
