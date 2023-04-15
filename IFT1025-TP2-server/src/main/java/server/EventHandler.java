package server;

/**
 * L'interface EventHandler crée une fonction qui sera définie ultérieurement dans la classe Server
 */
@FunctionalInterface
public interface EventHandler {
    void handle(String cmd, String arg);
}
