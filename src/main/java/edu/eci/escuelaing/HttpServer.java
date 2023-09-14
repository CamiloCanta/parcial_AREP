package edu.eci.escuelaing;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;


public class HttpServer {


    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        while (!serverSocket.isClosed()) {
            try {
                System.out.println("Operando Parcial EN INTERNET ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean firstLine = true;
            String uriString = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firstLine) {
                    firstLine = false;
                    uriString = inputLine.split(" ")[1];

                }
                if (!in.ready()) {
                    break;
                }
            }
            System.out.println("URI: " + uriString);
            String responseBody = "";

            if (uriString != null && uriString.equals("/")) {
                responseBody = getIndexResponse();
                outputLine = getResponse(responseBody);
            } else {
                outputLine = getIndexResponse();
            }
            out.println(outputLine);
            out.close();
            in.close();
        }
        clientSocket.close();
        serverSocket.close();
    }




    private static String getIndexResponse() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>calculadora</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>calculadora</h1>\n" +
                "        <form id=\"redirectForm\">\n" +
                "            <input type=\"text\" id=\"urlInput\" placeholder=\"Introduce la operacion\">\n" +
                "            <button type=\"button\" onclick=\"redirectToURL()\">resolver</button>\n" +
                "        </form>\n" +
                "        <script>\n" +
                "            function redirectToURL() {\n" +
                "                var url = document.getElementById(\"urlInput\").value;\n" +
                "                window.location.href = url;\n" +
                "            }\n" +
                "        </script>\n" +
                "    </body>\n" +
                "</html>";
    }

    private static String getResponse(String responseBody) {
        return  "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>calculadora</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "</body>\n"
                + "</html>\n"
                + responseBody;
    }


}
