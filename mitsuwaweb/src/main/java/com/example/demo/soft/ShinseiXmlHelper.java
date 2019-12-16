package com.example.demo.soft;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ShinseiXmlHelper {

	/**
	 * DocumentList作成
	 * @param builder DocumentBuilderFactory.newInstance().newDocumentBuilder
	 * @param strList fileParh
	 * @return List<Document>
	 * @throws SAXException
	 * @throws IOException
	 */
	public static List<Document> getDocument(DocumentBuilder builder, String str) throws SAXException, IOException {

		List<Document> docList = new ArrayList<Document>();
		docList.add(builder.parse(Paths.get("./src/main/resources/hinagata/" + str + "/syomei/" + str + ".xml").toFile()));
		docList.add(builder.parse(Paths.get("./src/main/resources/hinagata/" + str + "/syomei/" + str + ".xsl").toFile()));
		docList.add(builder.parse(Paths.get("./src/main/resources/hinagata/" + str + "/syomei/index.rdf").toFile()));
		docList.add(builder.parse(Paths.get("./src/main/resources/hinagata/" + str + "/info.xml").toFile()));
		docList.add(builder.parse(Paths.get("./src/main/resources/hinagata/" + str + "/export.xml").toFile()));
		return docList;
	}

	/**
	 * docListのindex0は申請書のxmlファイル想定
	 * @param docList
	 * @return
	 */
	public static Element xmlElement(List<Document> docList) {
		return docList.get(0).getDocumentElement();
	}

	public static Element infoElement(List<Document> docList) {
		return docList.get(3).getDocumentElement();
	}

	public static Element exportElement(List<Document> docList) {
		return docList.get(4).getDocumentElement();
	}

	/**
	 * 指定タグにtextContentをセット
	 * @param element
	 * @param tagName
	 * @param contentSet
	 */
	public static void elementTextset(Element element, String tagName, String contentSet, int count) {

		element.getElementsByTagName(tagName).item(count).setTextContent(contentSet);
	}

	/**
	 * 指定タグ内の子要素を含めてクローン作成
	 * @param element
	 * @param findTagName 指定タグ
	 * @param addTagName このタグの直前に要素を追加
	 */
	public static void elementAdd(Element element, String findTagName, String addTagName) {

		element.getElementsByTagName(addTagName).item(0).appendChild(
				element.getElementsByTagName(findTagName).item(0).cloneNode(true));
	}

	/**
	 * 申請用フォルダ作成
	 * @param fileName 手続ファイル名(ex.HM0504000300001)
	 * @throws IOException
	 */
	public static void makeFolder(String fileName, String folderName) throws IOException {

		File togo = new File("./" + folderName + "s/");
		File export = new File("./" + folderName + "/");
		File kanri = new File("./" + folderName + "/署名・送信/");
		File oshirase = new File("./" + folderName + "/お知らせ/");
		File kobunsyo = new File("./" + folderName + "/取得公文書/");
		File htmlFile = new File("./" + folderName + "/" + fileName + ".html");
		File oshiraseDummy = new File("./" + folderName + "/お知らせ/dummy.txt");
		File kobunsyoDummy = new File("./" + folderName + "/取得公文書/dummy.txt");

		togo.mkdir();
		export.mkdir();
		kanri.mkdir();
		oshirase.mkdir();
		kobunsyo.mkdir();
		htmlFile.createNewFile();
		oshiraseDummy.createNewFile();
		kobunsyoDummy.createNewFile();

	}

	public static void makeShinseiFile(List<Document> doc, String fileName, String folderName) throws TransformerException {

		TransformerFactory tfFactory = TransformerFactory.newInstance();
        Transformer tf = tfFactory.newTransformer();

        tf.setOutputProperty("indent", "yes");
        tf.setOutputProperty("encoding", "UTF-8");

        tf.transform(new DOMSource(doc.get(0)), new StreamResult(new File("./" + folderName + "/署名・送信/" + fileName + ".xml")));
        tf.transform(new DOMSource(doc.get(1)), new StreamResult(new File("./" + folderName + "/署名・送信/" + fileName + ".xsl")));
        tf.transform(new DOMSource(doc.get(2)), new StreamResult(new File("./" + folderName + "/署名・送信/index.rdf")));
        tf.transform(new DOMSource(doc.get(3)), new StreamResult(new File("./" + folderName + "/info.xml")));
        tf.transform(new DOMSource(doc.get(4)), new StreamResult(new File("./" + folderName + "s/export.xml")));

	}

}
