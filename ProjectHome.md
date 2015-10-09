# About #

This Android library offers a **standardized way** for app developers to

- **provide chess engines to other chess apps**

- **use provided chess engines in other chess apps**


# Engine authors: how to provide a chess engine using this library #

  * Checkout the code from https://chessenginesupport-androidlib.googlecode.com/svn/trunk/ChessEngineSupportLibrary into your workspace
  * Add the ChessEngineSupportLibrary as an Android library to your project (in Eclipse go to the Properties of the project - Android - Add... library)
  * Add the following lines to your AndroidManifest.xml:

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
  >
...


    <application
    ...
	    >
        <activity
	... >
            <intent-filter>
                <action android:name="intent.chess.provider.ENGINE" />
            </intent-filter>
            <meta-data
                android:name="chess.provider.engine.authority"
                android:value="your.package.name.YourEngineProvider" />
        </activity>

        <provider
            android:name="your.package.name.YourEngineProvider"
            android:authorities="your.package.name.YourEngineProvider"
            android:exported="true" />
    </application>

</manifest>
```
  * add YourEngineProvider.java to your project:
```
package your.package.name;

import com.kalab.chess.enginesupport.ChessEngineProvider;

public class YourEngineProvider extends ChessEngineProvider {
}
```
  * add the file enginelist.xml to your project under res/xml/ for an example file see https://code.google.com/p/chessenginesupport-androidlib/source/browse/trunk/StockfishChessEngine/res/xml/enginelist.xml
  * make sure your engine looks like a library, e.g. name your engine executable libXXX.so where XXX is the name of your engine
  * put the engine executables for the various targets under libs/armeabi, libs/armeabi-v7a, libs/x86
  * That's it! The source code of an example engine integration (Stockfish 4) is provided here: https://code.google.com/p/chessenginesupport-androidlib/source/browse/trunk/StockfishChessEngine

# GUI authors: how to support the open exchange format #

  * Checkout the code from https://chessenginesupport-androidlib.googlecode.com/svn/trunk/ChessEngineSupportLibrary into your workspace
  * Add the ChessEngineSupportLibrary as an Android library to your project (in Eclipse go to the Properties of the project - Android - Add... library)
  * use something like:
```
EngineResolver resolver = new EngineResolver(context);
List<Engine> engines = resolver.resolveEngines();
```
> engines is now a list of ChessEngines for the current target. You can use:
```
Engine firstEngine = engines.get(0);
File copiedEngine = firstEngine.copyToFiles(this.getActivity().getContentResolver(), this.getActivity().getFilesDir());
```
> ...to copy the first engine to your app files directory. The executable flag is already set. Save the engine file name, engine package name and engine version (see getters) in your preferences to check them later if they're current.
  * to check if the engine you're using is up-to-date use this method:
```
ChessEngineResolver resolver = new ChessEngineResolver(context);
int newVersionCode = resolver.ensureEngineVersion(engineFileName, enginePackageName, currentVersionCode, this.getActivity().getFilesDir());
```