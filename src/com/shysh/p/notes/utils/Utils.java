package com.shysh.p.notes.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;

public class Utils {

	public static String getFileFromAssetAsString(String fileName, Context context) {
		String result = null;
		AssetManager assetManager = context.getAssets();
		try {
			InputStream is = assetManager.open(fileName);
			result = convertInputStreamToSrting(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String convertInputStreamToSrting(InputStream is) {
		if (is == null) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader bufferedReader = new BufferedReader(isr);
		String line = null;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return stringBuilder.toString();
	}
}
