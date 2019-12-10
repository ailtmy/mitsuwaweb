package com.example.demo.soft;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCreate {

	public static void compressDirectory(final File destination, final File dir)
			throws IOException {

		// 変数宣言
		byte[] buf = new byte[1024];
		ZipOutputStream zos = null;
		InputStream is = null;

		// ZIP対象フォルダ配下の全ファイルを取得
		List<File> files = new ArrayList<File>();
		getFiles(dir, files);

		try {

			// ZIP出力オブジェクトを取得（日本語の文字化けに対応するために文字コードは Shift-JIS を指定）
			zos = new ZipOutputStream(
					new BufferedOutputStream(new FileOutputStream(destination)), Charset.forName("Shift-JIS"));

			// 全ファイルをZIPに格納
			for (File file : files) {

				// ZIP化実施ファイルの情報をオブジェクトに設定
				ZipEntry entry = new ZipEntry(
						file.getAbsolutePath().replace(dir.getAbsolutePath() + File.separator, ""));
				zos.putNextEntry(entry);


				// ZIPファイルに情報を書き込む
				is = new BufferedInputStream(new FileInputStream(file));
				int len = 0;
				while ((len = is.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}

				// ストリームを閉じる
				is.close();
			}

			// 処理の最後にストリームは常に閉じる
		} finally {
			if (zos != null) {
				zos.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * 指定したフォルダ配下の全ファイルを取得
	 * @param parentDir ファイル取得対象フォルダ
	 * @param files     ファイル一覧
	 */
	public static void getFiles(final File parentDir, final List<File> files) {

		// ファイル取得対象フォルダ直下のファイル,ディレクトリを走査
		for (File f : parentDir.listFiles()) {

			// ファイルの場合はファイル一覧に追加
			if (f.isFile()) {
				files.add(f);

			} else if(f.isDirectory() && f.listFiles() == null) {

				files.add(f);


				// ディレクトリの場合は再帰処理
			} else if (f.isDirectory() ) {
				getFiles(f, files);

			}

		}
	}
}
