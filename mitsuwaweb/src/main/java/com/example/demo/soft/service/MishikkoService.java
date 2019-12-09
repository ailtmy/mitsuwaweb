package com.example.demo.soft.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.demo.soft.repository.MishikkoRepository;

@Service
public class MishikkoService {

	@Autowired
	MishikkoRepository repository;

	public Element xmlFileGet() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		// 1. DocumentBuilderFactoryのインスタンスを取得する
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// 2. DocumentBuilderのインスタンスを取得する
		DocumentBuilder builder = factory.newDocumentBuilder();
		// 3. DocumentBuilderにXMLを読み込ませ、Documentを作る
		Document document = builder.parse(Paths.get("./src/main/resources/hinagata/mishikko/syomei/HM0504000300001.xml").toFile());
		Document xsldocument = builder.parse(Paths.get("./src/main/resources/hinagata/mishikko/syomei/HM0504000300001.xsl").toFile());
		Document rdfdocument = builder.parse(Paths.get("./src/main/resources/hinagata/mishikko/syomei/index.rdf").toFile());
		Document infodocument = builder.parse(Paths.get("./src/main/resources/hinagata/mishikko/info.xml").toFile());
		// 4. Documentから、ルート要素(mishikkoElement)を取得する
		Element mishikkoElement = document.getDocumentElement();

		NodeList toiawase = mishikkoElement.getElementsByTagName("年月日");
		toiawase.item(0).setTextContent("申請した日を変数で入力");

		NodeList tokisyoName = mishikkoElement.getElementsByTagName("宛先登記所名");
		NodeList teisyutusakiName = mishikkoElement.getElementsByTagName("提出先名称");
		tokisyoName.item(0).setTextContent("大阪法務局");
		teisyutusakiName.item(0).setTextContent("大阪法務局");
		NodeList tokisyoCode = mishikkoElement.getElementsByTagName("登記所コード");
		NodeList teisyutusakiCode = mishikkoElement.getElementsByTagName("提出先コード");
		tokisyoCode.item(0).setTextContent("1200");
		teisyutusakiCode.item(0).setTextContent("1200");


		//物件追加の場合
		NodeList bukken = mishikkoElement.getElementsByTagName("対象物件情報");
		Node bukkenClone = bukken.item(0).cloneNode(true);
		mishikkoElement.getElementsByTagName("問合書情報").item(0).appendChild(bukkenClone);


		NodeList bukkenKubun = mishikkoElement.getElementsByTagName("物件区分");
		NodeList chibanKuiki = mishikkoElement.getElementsByTagName("地番区域情報");
		NodeList chibanKaokubango = mishikkoElement.getElementsByTagName("地番家屋番号情報");
		NodeList mokuteki = mishikkoElement.getElementsByTagName("登記の目的");
		NodeList yoshiKubun = mishikkoElement.getElementsByTagName("用紙区分");
		NodeList uketsukeDate = mishikkoElement.getElementsByTagName("年月日");
		NodeList uketsukeBango = mishikkoElement.getElementsByTagName("本番");

		for(int i = 0; i < bukkenKubun.getLength(); i++) {
			bukkenKubun.item(i).setTextContent("建物");
			chibanKuiki.item(i).setTextContent("大阪市中央区備後町四丁目");
			chibanKaokubango.item(i).setTextContent("１－１");
			mokuteki.item(i).setTextContent("抵当権設定");
			yoshiKubun.item(i).setTextContent("乙区");
			uketsukeDate.item(i+1).setTextContent("令和１年１２月１日");
			uketsukeBango.item(i).setTextContent("99999");
		}

		TransformerFactory tfFactory = TransformerFactory.newInstance();
        Transformer tf = tfFactory.newTransformer();

        tf.setOutputProperty("indent", "yes");
        tf.setOutputProperty("encoding", "UTF-8");

        File export = new File("./export");
        File kanri = new File("./export/署名・送信");
        File oshirase = new File("./export/お知らせ");
        File kobunsyo = new File("./export/取得公文書");
        File htmlFile = new File("./export/HM0504000300001.html");
        export.mkdir();
        kanri.mkdir();
        oshirase.mkdir();
        kobunsyo.mkdir();
        htmlFile.createNewFile();
        tf.transform(new DOMSource(document), new StreamResult(new File("./export/署名・送信/HM0504000300001.xml")));
        tf.transform(new DOMSource(xsldocument), new StreamResult(new File("./export/署名・送信/HM0504000300001.xsl")));
        tf.transform(new DOMSource(rdfdocument), new StreamResult(new File("./export/署名・送信/index.rdf")));
        tf.transform(new DOMSource(infodocument), new StreamResult(new File("./export/info.xml")));



		return mishikkoElement;
	}
}
