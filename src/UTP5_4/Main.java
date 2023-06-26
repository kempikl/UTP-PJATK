package UTP5_4;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Main {
  public static final int PORT = 12345;

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Serwer uruchomiony na porcie: " + PORT);

      ExecutorService executor = Executors.newCachedThreadPool();

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Nawiązano połączenie z klientem: " + clientSocket);

        Callable<String> requestHandler = new RequestHandler(clientSocket);
        FutureTask<String> futureTask = new FutureTask<>(requestHandler);

        executor.execute(futureTask);
      }
    } catch (IOException e) {
      System.err.println("Błąd serwera: " + e.getMessage());
    }
  }

  static class RequestHandler implements Callable<String> {
    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
      this.clientSocket = clientSocket;
    }

    @Override
    public String call() throws IOException {
      try (InputStream inputStream = clientSocket.getInputStream();
           OutputStream outputStream = clientSocket.getOutputStream()) {

        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String message = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
        System.out.println("Otrzymano wiadomość: " + message);

        String response = "Odpowiedź serwera: " + message;
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();

        return response;
      } catch (IOException e) {
        System.err.println("Błąd obsługi żądania: " + e.getMessage());
        return null;
      } finally {
        clientSocket.close();
      }
    }
  }
}

