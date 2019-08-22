This Android library offers a **standardized way** for app developers to

- **provide chess engines to other chess apps**

- **use provided chess engines in other chess apps**


# Engine authors: how to provide a chess engine using this library #

  * See the StockfishChessEngine directory for an example of an app which provides Stockfish to other apps

# GUI authors: how to support the open exchange format #

  * Import the library. Android Studio: File - New - Import Module (select the chessEngineSupportLibrary directory)
  * use something like:
```java
EngineResolver resolver = new EngineResolver(context);
List<Engine> engines = resolver.resolveEngines();
```
> engines is now a list of ChessEngines for the current target. E.g.:
```java
TextView text = findViewById(R.id.exampleText);
for (ChessEngine engine : engines) {
    text.append(engine.getName() + ": " + engine.getEnginePath() + "\n");
}
```
> you can directly execute the engine from the engine path
