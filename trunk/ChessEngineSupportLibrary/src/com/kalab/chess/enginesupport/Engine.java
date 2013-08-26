/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kalab.chess.enginesupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;

public class Engine {

	private static final String TAG = Engine.class.getSimpleName();

	private String name;
	private String fileName;
	private String authority;

	public Engine(String name, String fileName, String authority) {
		this.name = name;
		this.fileName = fileName;
		this.authority = authority;
	}

	public String getName() {
		return this.name;
	}

	public String getFileName() {
		return this.fileName;
	}

	public Uri getUri() {
		return Uri.parse("content://" + authority + "/" + fileName);
	}

	public File copyToFiles(ContentResolver contentResolver, File destination)
			throws FileNotFoundException, IOException {
		Uri uri = getUri();
		File output = new File(destination, uri.getPath().toString());
		copyUri(contentResolver, uri, output.getAbsolutePath());
		return output;
	}

	public void copyUri(final ContentResolver contentResolver,
			final Uri source, String targetFilePath) throws IOException,
			FileNotFoundException {
		InputStream istream = contentResolver.openInputStream(source);
		copyFile(istream, targetFilePath);
		setExecutablePermission(targetFilePath);
	}

	private void copyFile(InputStream istream, String targetFilePath)
			throws FileNotFoundException, IOException {
		FileOutputStream fout = new FileOutputStream(targetFilePath);
		byte[] b = new byte[1024];
		int noOfBytes = 0;
		while ((noOfBytes = istream.read(b)) != -1) {
			fout.write(b, 0, noOfBytes);
		}
		istream.close();
		fout.close();
	}

	private void setExecutablePermission(String engineFileName) throws IOException {
		String cmd[] = { "chmod", "744", engineFileName };
		Process process = Runtime.getRuntime().exec(cmd);
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
}
