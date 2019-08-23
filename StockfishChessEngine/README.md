# Example Stockfish engine app #

This example app provides the Stockfish Chess engine to other Android apps.

# How to provide a chess engine using the chess engine support library #

  * Look at the code of this example app
  * Add the ChessEngineSupportLibrary as a dependency to your project (build.gradle):

```groovy
dependencies {
    implementation project(':chessEngineSupportLibrary')
}  
```
  
  * Add the following intent-filter, meta-data and provider to your AndroidManifest.xml:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application>
        <activity>
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

```java
package your.package_name;

import com.kalab.chess.enginesupport.ChessEngineProvider;

public class YourEngineProvider extends ChessEngineProvider {
}
```
  * add the file enginelist.xml to your project under res/xml/ (see example enginelist.xml of this project)
  * make sure your engine looks like a library, e.g. name your engine executable libXXX.so where XXX is the name of your engine
  * put the engine executables for the various targets under src/main/jniLibs/armeabi, src/main/jniLibs/armeabi-v7a, src/main/jniLibs/x86, etc.
  * you can put more than one engine into one app (demonstrated here with asmFish for x86_64)